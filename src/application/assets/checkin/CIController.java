package application.assets.checkin;

import application.Validation;
import application.assets.*;
import application.assets.reservation.ResvPayController;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;

public class CIController implements Initializable {

    @FXML private AnchorPane ciPane;
    @FXML private Button btn_ciNext;
    @FXML private TextField tf_ciResvNum;
    @FXML private TextField tf_ciFirstName;
    @FXML private TextField tf_ciLastName;
    @FXML private TextField tf_ciCountry;
    @FXML private TextField tf_ciAddress;
    @FXML private TextField tf_ciPostCode;
    @FXML private TextField tf_ciCity;
    @FXML private TextField tf_ciIDType;
    @FXML private TextField tf_ciIDNo;
    @FXML private TextField tf_ciState;

    @FXML private TableView<ModelRoom> table_room;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomcat;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomtype;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomno;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomci;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomco;
    @FXML private TableColumn<ModelRoom, String> tbcol_xtrabed;
    @FXML private TableColumn<ModelRoom, String> tbcol_rprice;

    @FXML private TableView<ModelFacility> table_fac;
    @FXML private TableColumn<ModelFacility, String> tbcol_fac;
    @FXML private TableColumn<ModelFacility, String> tbcol_facno;
    @FXML private TableColumn<ModelFacility, String> tbcol_facbookdate;
    @FXML private TableColumn<ModelFacility, String> tbcol_facprice;
    @FXML private TableColumn<ModelFacility, String> tbcol_faccomment;

    @FXML private TableView<ModelGroupMember> table_memgroup;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memfname;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memlname;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memidno;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memidtype;

    @FXML private TableView<ModelCIToday> table_ciToday;
    @FXML private TableColumn<ModelCIToday, String> tbcol_resvno;
    @FXML private TableColumn<ModelCIToday, String> tbcol_fname;
    @FXML private TableColumn<ModelCIToday, String> tbcol_lname;
    @FXML private TableColumn<ModelCIToday, String> tbcol_co;

    private String inDate;
    private String outDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cellValueFacotry();
        tdCheckIn();
        doubleClick();
        ciPayment();
    }

    public void tdCheckIn() {
        try {
            //language=SQLite
            String query = "SELECT * FROM Reservation r " +
                    "INNER JOIN Customer c ON r.CustID = c.CustID WHERE r.CheckInDate = " +
                    "CURRENT_DATE";
            ResultSet rs = db.executeQuery(query);
            ObservableList<ModelCIToday> citable = FXCollections.observableArrayList();
            while (rs.next()) {
                ModelCIToday ci = new ModelCIToday();
                ci.setresv(rs.getString("ResvNo"));
                ci.setfname(rs.getString("CustFName"));
                ci.setlname(rs.getString("CustLName"));
                ci.setco(rs.getString("CheckOutDate"));
                citable.add(ci);
            }

            table_ciToday.setItems(citable);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void doubleClick(){

        table_ciToday.setRowFactory(tc->{

            TableRow<ModelCIToday> selRow = new TableRow<>();

            selRow.setOnMouseClicked(me->{
                if (me.getClickCount() == 2 && (!selRow.isEmpty())){

                    table_room.getItems().clear();
                    table_memgroup.getItems().clear();
                    table_fac.getItems().clear();

                    ModelCIToday tdci = table_ciToday.getSelectionModel().getSelectedItem();
                    try {
                        //language=SQLite
                        String query = "SELECT * FROM Reservation r INNER JOIN Customer c ON r.CustID = c.CustID INNER JOIN CustAddress ca ON ca.CustID = c.CustID WHERE r.ResvNo = " + tdci.getresv();
                        ResultSet rs = db.executeQuery(query);

                        tf_ciResvNum.setText(String.valueOf(rs.getInt("ResvNo")));
                        tf_ciFirstName.setText(rs.getString("CustFName"));
                        tf_ciLastName.setText(rs.getString("CustLName"));
                        tf_ciAddress.setText(rs.getString("Address"));
                        tf_ciPostCode.setText(rs.getString("PostCode"));
                        tf_ciCity.setText(rs.getString("City"));
                        tf_ciState.setText(rs.getString("State"));
                        tf_ciCountry.setText(rs.getString("Country"));
                        tf_ciIDType.setText(rs.getString("CustID_Type"));
                        tf_ciIDNo.setText(rs.getString("CustID"));

                        inDate = rs.getString("CheckInDate");
                        outDate = rs.getString("CheckOutDate");

                        query = "SELECT * FROM CustomerGroup WHERE G_CustID = '" +
                                tf_ciIDNo.getText() + "'";

                        rs = db.executeQuery(query);

                        while (rs.next()){
                            ModelGroupMember mg = new ModelGroupMember();
                            mg.setMemFName(rs.getString("CustFName"));
                            mg.setMemLName(rs.getString("CustLName"));
                            mg.setIdType(rs.getString("IDType"));
                            mg.setIdNo(rs.getString("IDNo"));
                            table_memgroup.getItems().add(mg);
                        }

                        query = "SELECT * FROM RoomBooking rb INNER JOIN Room r ON rb.RoomNo = r.RoomNo " +
                                "INNER JOIN RoomType rt ON r.RoomTypeID = rt.TypeID WHERE rb.ResvNo =" +
                                Integer.parseInt(tf_ciResvNum.getText()) + "";

                        rs = db.executeQuery(query);

                        while (rs.next()){
                            ModelRoom mr = new ModelRoom();
                            mr.setRoomcat(rs.getString("TypeGroup"));
                            mr.setRtype(rs.getString("TypeName"));
                            mr.setRoomno(rs.getString("RoomNo"));
                            mr.setCidate(rs.getString("Date_CI"));
                            mr.setCodate(rs.getString("Date_CO"));
                            mr.setExtbedtype(rs.getString("ExtBedType"));
                            mr.setRoomprice(rs.getString("Price"));

                            table_room.getItems().add(mr);
                        }

                        query = "SELECT * FROM FacBookedDate fbd INNER JOIN FacType ft ON fbd.FacNo" +
                                "= ft.FacNo WHERE fbd.ResvNo = " + Integer.parseInt(tf_ciResvNum.getText());

                        rs = db.executeQuery(query);

                        while (rs.next()){
                            ModelFacility mf = new ModelFacility();
                            mf.setFacname(rs.getString("FacName"));
                            mf.setFacno(rs.getString("FacNo"));
                            mf.setBookedfacdate(rs.getString("BookDate"));
                            mf.setFacprice(String.valueOf(rs.getFloat("FacPrice")));
                            mf.setFacdesc(rs.getString("Comment"));

                            table_fac.getItems().add(mf);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

            return selRow;
        });
    }

    public void ciPayment(){
        FXMLLoader loadpayment = new FXMLLoader(getClass().getResource("/application/assets/" +
                "reservation/resvpay.fxml"));
        AnchorPane payment = null;
        try {
            payment = loadpayment.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResvPayController rpc = loadpayment.getController();

        AnchorPane finalPayment = payment;

        btn_ciNext.setOnMouseClicked(me->{
            if (Objects.equals(tf_ciResvNum.getText(), "")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Reservation Not Loaded");
                alert.setHeaderText("No Reservation is loaded");
                alert.setContentText("Reservation has to be made before checking in. For direct check in, " +
                        "set the room check in date to today's date, proceed to reserve and then check in.");
                alert.showAndWait();
                return;
            }

            FadeTransition ft = new FadeTransition(Duration.millis(320), finalPayment);
            ft.setFromValue(0.0); //add a simple fade in transition
            ft.setToValue(1.0);
            ft.play();
            ciPane.getChildren().add(finalPayment); //add as children of resvEditPane

            rpc.getBtn_reserve().setText("Check In");

            //language=SQLite
            String query = "SELECT * FROM Payment WHERE ResvNo = " +
                    Integer.parseInt(tf_ciResvNum.getText());

            try {
                ResultSet rs = db.executeQuery(query);
                if (rs.next()){
                    Double ccardno = rs.getDouble("CCardNo");
                    if (!rs.wasNull()){
                        query = "SELECT * FROM Payment p INNER JOIN Pay_CCard pc " +
                                "ON p.CCardNo = pc.CCardNo WHERE ResvNo = " +
                                Integer.parseInt(tf_ciResvNum.getText());
                        rs = db.executeQuery(query);
                        rpc.getCbox_PayType().getSelectionModel().select("Credit Card");
                        rpc.getTf_cardname().setText(rs.getString("CCardName"));
                        rpc.getTf_cardno().setText(String.valueOf(rs.getDouble("CCardNo")));
                        rpc.getTf_cvccode().setText(String.valueOf(rs.getInt("CVC")));
                        rpc.getCbox_Month().getSelectionModel().select(rs.getInt("DOE_Month"));
                        rpc.getCbox_Year().getSelectionModel().select(rs.getInt("DOE_Year"));
                    } else if (rs.getString("ChequeNo") != null){
                        rpc.getCbox_PayType().getSelectionModel().select("Cheque");
                        rpc.getTf_cardname().setText(rs.getString("ChequeNo"));
                    } else {
                        rpc.getCbox_PayType().getSelectionModel().select("Cash");
                    }

                    rpc.getRb_deposit().setDisable(true);
                    rpc.getRb_full().setDisable(true);

                    rpc.getLbl_refno().setText(String.valueOf(rs.getInt("PaymentID")));
                    rpc.getLbl_deposit().setText(String.format(Locale.UK, "%.2f", rs.getFloat("Deposit")));
                    rpc.getLbl_paid().setText(String.format(Locale.UK, "%.2f", rs.getFloat("Paid")));
                    rpc.getLbl_switchbal().setText("Amount :");
                    rpc.getLbl_balance().setText(String.format(Locale.UK, "%.2f", rs.getFloat("Bal")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                ResultSet rs = db.executeQuery("SELECT deposit, taxrate FROM variables");
                if (rs.next()) {
                    rpc.getLbl_deposit().setText(String.format(Locale.UK, "%.2f", rs.getFloat("deposit")));
                    rpc.getLbl_tax().setText(String.valueOf(rs.getInt("taxrate")) + "%");
                } else {
                    rpc.getLbl_tax().setText(null);
                    rpc.getLbl_deposit().setText(null);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        rpc.getBtn_resvBack().setOnMouseClicked(me -> { //getter from the payment controller
            Timeline timeline = new Timeline(); //set fade out
            assert finalPayment != null;
            KeyFrame kf = new KeyFrame(Duration.millis(320), new KeyValue(finalPayment.opacityProperty(), 0));
            timeline.getKeyFrames().add(kf);
            //when the timeline is finished (finished fade out) then invoke remove the finalPayment
            timeline.setOnFinished(se -> ciPane.getChildren().removeAll(finalPayment));
            timeline.play();
        });

        rpc.getBtn_reserve().setOnMouseClicked(me -> {

            try {
                //language=SQLite
                String query = "INSERT INTO CheckInOut (CustID, CheckInDate, " +
                        "CheckOutDate, Status, ResvNo) VALUES ('" + tf_ciIDNo.getText() +
                        "','" + inDate + "','" + outDate +
                        "','Checked In', " + Integer.parseInt(tf_ciResvNum.getText()) + "";
                db.executeUpdate(query);

                query = "SELECT DISTINCT CIO_ID FROM CheckInOut WHERE ResvNo=" +
                        Integer.parseInt(tf_ciResvNum.getText());

                ResultSet rs = db.executeQuery(query);

                while (rs.next()){
                    query = "UPDATE Payment SET CIO_ID = " + rs.getInt("CIO_ID") +
                            ", Bal = 0, Paid = " + Float.parseFloat(rpc.getLbl_subtotal().getText()) +
                            "";
                    db.executeUpdate(query);
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void cellValueFacotry(){
        tbcol_resvno.setCellValueFactory(new PropertyValueFactory<>("resv"));
        tbcol_fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        tbcol_lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        tbcol_co.setCellValueFactory(new PropertyValueFactory<>("co"));

        //room booking
        tbcol_roomcat.setCellValueFactory(new PropertyValueFactory<>("roomcat"));
        tbcol_roomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
        tbcol_roomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
        tbcol_roomci.setCellValueFactory(new PropertyValueFactory<>("cidate"));
        tbcol_roomco.setCellValueFactory(new PropertyValueFactory<>("codate"));
        tbcol_xtrabed.setCellValueFactory(new PropertyValueFactory<>("extbedtype"));
        tbcol_rprice.setCellValueFactory(new PropertyValueFactory<>("roomprice"));

        //group member
        tbcol_memfname.setCellValueFactory(new PropertyValueFactory<>("memFName"));
        tbcol_memlname.setCellValueFactory(new PropertyValueFactory<>("memLName"));
        tbcol_memidtype.setCellValueFactory(new PropertyValueFactory<>("idType"));
        tbcol_memidno.setCellValueFactory(new PropertyValueFactory<>("idNo"));

        //fac booking
        tbcol_fac.setCellValueFactory(new PropertyValueFactory<>("facname"));
        tbcol_facno.setCellValueFactory(new PropertyValueFactory<>("facno"));
        tbcol_facbookdate.setCellValueFactory(new PropertyValueFactory<>("bookedfacdate"));
        tbcol_facprice.setCellValueFactory(new PropertyValueFactory<>("facprice"));
        tbcol_faccomment.setCellValueFactory(new PropertyValueFactory<>("facdesc"));
    }
}



