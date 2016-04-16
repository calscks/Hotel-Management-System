package application.assets.reservation;

import application.Validation;
import application.assets.*;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;

public class ResvEditController implements Initializable {

    @FXML
    private AnchorPane resvEditPane;
    @FXML
    private TextField tf_search;
    @FXML
    private TextField tf_resvno;
    @FXML
    private TextField tf_fname;
    @FXML
    private TextField tf_lname;
    @FXML
    private TextField tf_address;
    @FXML
    private TextField tf_postcode;
    @FXML
    private TextField tf_city;
    @FXML
    private TextField tf_state;
    @FXML
    private TextField tf_idno;
    @FXML
    private ComboBox<String> cbox_country;
    @FXML
    private ComboBox<String> cbox_idtype;

    @FXML
    private ToggleGroup ResvSearchGroup;
    @FXML
    private RadioButton rb_resvno;
    @FXML
    private RadioButton rb_lname;
    @FXML
    private RadioButton rb_fname;

    @FXML
    private Button btn_resvcancel;
    @FXML
    private Button btn_addfac;
    @FXML
    private Button btn_delfac;
    @FXML
    private Button btn_addroom;
    @FXML
    private Button btn_delroom;
    @FXML
    private Button btn_addguest;
    @FXML
    private Button btn_delguest;
    @FXML
    private Button btn_next;

    @FXML
    private TableView<ModelGroupMember> table_memgroup;
    @FXML
    private TableColumn<ModelGroupMember, String> tbcol_memfname;
    @FXML
    private TableColumn<ModelGroupMember, String> tbcol_memlname;
    @FXML
    private TableColumn<ModelGroupMember, String> tbcol_memidtype;
    @FXML
    private TableColumn<ModelGroupMember, String> tbcol_memidno;

    @FXML
    private TableView<ModelRoom> table_room;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomcat;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomtype;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomno;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomci;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomco;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_xtrabed;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_rprice;

    @FXML
    private TableView<ModelFacility> table_fac;
    @FXML
    private TableColumn<ModelFacility, String> tbcol_fac;
    @FXML
    private TableColumn<ModelFacility, String> tbcol_facno;
    @FXML
    private TableColumn<ModelFacility, String> tbcol_facprice;
    @FXML
    private TableColumn<ModelFacility, String> tbcol_facbookdate;
    @FXML
    private TableColumn<ModelFacility, String> tbcol_faccomment;

    @FXML
    private TableView<ModelResvSearch> table_sresult;
    @FXML
    private TableColumn<ModelResvSearch, String> tbcol_sresv;
    @FXML
    private TableColumn<ModelResvSearch, String> tbcol_sfname;
    @FXML
    private TableColumn<ModelResvSearch, String> tbcol_slname;

    private ObservableList<String> idtype = FXCollections.observableArrayList("Identification Card", "Passport No");

    private String inDate;
    private String outDate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validation();
        cbox_idtype.setItems(idtype);
        cellValueFactory();
        searchResv();
        cancelResv();
        doubleClick();
        addRoom();
        delRoom();
        addFac();
        delFac();
    }

    public void searchResv() {
        //language=SQLite
        tf_search.setOnKeyReleased(event -> {
            table_sresult.getItems().clear();
            if (rb_resvno.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                String query = "SELECT r.ResvNo, c.CustFName, c.CustLName FROM Reservation r " +
                        "INNER JOIN Customer c WHERE r.ResvNo LIKE '%" + tf_search.getText() +
                        "%' and r.CustID = c.CustID";
                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()) {
                        ModelResvSearch mrs = new ModelResvSearch();
                        mrs.setResvNo(String.valueOf(rs.getInt(1))); //rs index always start from 1
                        mrs.setfName(rs.getString(2));
                        mrs.setlName(rs.getString(3));
                        table_sresult.getItems().add(mrs);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (rb_fname.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                String query = "SELECT r.ResvNo, c.CustFName, c.CustLName FROM Reservation r " +
                        "INNER JOIN Customer c WHERE c.CustFName LIKE '%" + tf_search.getText() +
                        "%' and r.CustID = c.CustID";
                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()) {
                        ModelResvSearch mrs = new ModelResvSearch();
                        mrs.setResvNo(String.valueOf(rs.getInt(1)));
                        mrs.setfName(rs.getString(2));
                        mrs.setlName(rs.getString(3));
                        table_sresult.getItems().add(mrs);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (rb_lname.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                String query = "SELECT r.ResvNo, c.CustFName, c.CustLName FROM Reservation r " +
                        "INNER JOIN Customer c WHERE c.CustLName LIKE '%" + tf_search.getText() +
                        "%' and r.CustID = c.CustID";
                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()) {
                        ModelResvSearch mrs = new ModelResvSearch();
                        mrs.setResvNo(String.valueOf(rs.getInt(1)));
                        mrs.setfName(rs.getString(2));
                        mrs.setlName(rs.getString(3));
                        table_sresult.getItems().add(mrs);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void cancelResv(){
        btn_resvcancel.setOnMouseClicked(me->{
            int selected = table_sresult.getSelectionModel().getSelectedIndex();
            if (selected >= 0){
                ModelResvSearch mrs = new ModelResvSearch();
                mrs = table_sresult.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Room");
                alert.setContentText("Are you sure you want to delete reservation " + mrs.getResvNo() + "?");

                Optional<ButtonType> select = alert.showAndWait();
                if (select.isPresent()) {
                    if (select.get() == ButtonType.OK) {
                        int resvno = Integer.parseInt(mrs.getResvNo());

                        String s = "SELECT Subtotal, Bal FROM Payment WHERE ResvNo=" +
                                mrs.getResvNo();
                        try {
                            ResultSet rs = db.executeQuery(s);

                            Float amount = rs.getFloat("Subtotal") - rs.getFloat("Bal");

                            Alert payreturn = new Alert(Alert.AlertType.INFORMATION);
                            payreturn.setTitle("Return Amount");
                            payreturn.setHeaderText("Amount to be returned: " +
                                    String.format(Locale.UK, "%.2f", amount));
                            payreturn.showAndWait();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        try {
                            //language=SQLite
                            String query = "DELETE FROM Reservation WHERE ResvNo=" + resvno;
                            db.executeUpdate(query);
                            query = "DELETE FROM RoomBooking WHERE ResvNo NOT IN (SELECT " +
                                    "DISTINCT ResvNo FROM Reservation)";
                            db.executeUpdate(query);
                            query = "DELETE FROM FacBookedDate WHERE ResvNo NOT IN (" +
                                    "SELECT DISTINCT ResvNo FROM Reservation)";
                            db.executeUpdate(query);
                            query = "DELETE FROM Customer WHERE CustID NOT IN (" +
                                    "SELECT DISTINCT CustID FROM Reservation)";
                            db.executeUpdate(query);
                            query = "DELETE FROM CustAddress WHERE CustID NOT IN (" +
                                    "SELECT DISTINCT CustID FROM Customer)";
                            db.executeUpdate(query);
                            query = "DELETE FROM CustomerGroup WHERE G_CustID NOT IN (" +
                                    "SELECT DISTINCT CustID FROM Customer)";
                            db.executeUpdate(query);
                            query = "DELETE FROM Payment WHERE ResvNo NOT IN (" +
                                    "SELECT DISTINCT ResvNo FROM Reservation)";
                            db.executeUpdate(query);
                            query = "DELETE FROM Pay_CCard WHERE CCardNo NOT IN (" +
                                    "SELECT DISTINCT CCardNo FROM Payment)";
                            db.executeUpdate(query);

                            table_sresult.getItems().remove(selected);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        alert.close();
                    }
                }
            } else {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("No Selection");
                alert2.setHeaderText("No Reservation is Selected");
                alert2.setContentText("Please select a reservation in the table to be deleted.");
                alert2.showAndWait();
            }

        });
    }

    public void doubleClick(){
        table_sresult.setRowFactory(ts->{

            TableRow<ModelResvSearch> selRow = new TableRow<>();

            selRow.setOnMouseClicked(me->{

                if (me.getClickCount() == 2 && (!selRow.isEmpty())){
                    ModelResvSearch result = table_sresult.getSelectionModel().getSelectedItem();
                    try {
                        //language=SQLite
                        String query = "SELECT * FROM Reservation r INNER JOIN Customer c " +
                                "ON r.CustID = c.CustID INNER JOIN CustAddress ca ON " +
                                "ca.CustID = c.CustID WHERE r.ResvNo = " + result.getResvNo();
                        ResultSet rs = db.executeQuery(query);

                        tf_resvno.setText(String.valueOf(rs.getInt("ResvNo")));
                        tf_fname.setText(rs.getString("CustFName"));
                        tf_lname.setText(rs.getString("CustLName"));
                        tf_address.setText(rs.getString("Address"));
                        tf_postcode.setText(rs.getString("PostCode"));
                        tf_city.setText(rs.getString("State"));
                        cbox_country.getSelectionModel().select(rs.getString("Country"));
                        cbox_idtype.getSelectionModel().select(rs.getString("CustID_Type"));
                        tf_idno.setText(rs.getString("CustID"));

                        query = "SELECT * FROM CustomerGroup WHERE G_CustID = '" +
                                tf_idno.getText() + "'";

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
                                Integer.parseInt(tf_resvno.getText()) + "";

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
                                "= ft.FacNo WHERE fbd.ResvNo = " + Integer.parseInt(tf_resvno.getText());

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

    public void addRoom() {
        //VERY IMPORTANT: please use like the below, because can retrieve controller from fxmlloader easily
        FXMLLoader loadRoom = new FXMLLoader(getClass().getResource("/application/assets" +
                "/reservation/resvroom.fxml"));
        AnchorPane roomPane = new AnchorPane();
        try {
            roomPane = loadRoom.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane finalRoomPane = roomPane;

        new ForAddButton(finalRoomPane, btn_addroom, table_room);


        ResvRoomController rc = loadRoom.getController();

        rc.getBtn_roomAdd().setOnMouseClicked(me -> {
                ModelRoom room = new ModelRoom();

                room.setRoomcat(rc.getRoomCat());
                room.setRtype(rc.getRoomType());
                room.setRoomno(rc.getRoomNo());
                room.setCidate(rc.getCI());
                inDate = rc.getCI();
                room.setCodate(rc.getCO());
                outDate = rc.getCO();
                room.setExtbedtype(rc.getExtBed());
                room.setRoomprice(rc.getTotal());

                table_room.getItems().add(room);

                //manually clearing data from the add room stage after adding
                rc.getTable_rooms().getItems().clear();
                rc.getCbox_xtrabed().getItems().clear();
                rc.getLbl_extBedPrice().setText(null);
                rc.getLbl_roomPrice().setText(null);
                rc.getLbl_totalRoomPrice().setText(null);
                rc.getTf_roomno().setText("");

                Stage stage = (Stage) rc.getBtn_roomAdd().getScene().getWindow();
                stage.close();

        });
    }//add room resv ends

    public void delRoom() {
        btn_delroom.setOnMouseClicked(me -> {
            int selectedRow = table_room.getSelectionModel().getSelectedIndex();
            if (selectedRow >= 0) {
                ModelRoom roomNo = new ModelRoom();
                roomNo = table_room.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Room");
                alert.setContentText("Are you sure you want to delete room " + roomNo.getRoomno() + "?");
                //detect user presses ok or cancel (must do like this)
                Optional<ButtonType> select = alert.showAndWait();
                if (select.isPresent()) {
                    if (select.get() == ButtonType.OK) {
                        table_room.getItems().remove(selectedRow);
                    } else {
                        alert.close();
                    }
                }
            } else {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("No Selection");
                alert2.setHeaderText("No Room is Selected");
                alert2.setContentText("Please select a room in the table to be deleted.");
                alert2.showAndWait();
            }
        });
    } //delete room resv ends

    public void addFac() {
        FXMLLoader loadfac = new FXMLLoader(getClass().getResource("/application/assets/" +
                "reservation/resvfacility.fxml"));
        AnchorPane facPane = new AnchorPane();
        try {
            facPane = loadfac.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane finalFacPane = facPane;

        new ForAddButton(finalFacPane, btn_addfac, table_fac);

        ResvFacilityController rsf = loadfac.getController();

        rsf.getBtn_addfac().setOnMouseClicked(me -> {
            ModelFacility fac = new ModelFacility();
            fac.setFacno(rsf.getTf_facno().getText());
            fac.setFacname(rsf.getLbl_facname().getText());
            fac.setBookedfacdate(rsf.getLbl_date().getText());
            fac.setFacdesc(rsf.getTf_comment().getText());
            fac.setFacprice(rsf.getLbl_facprice().getText());

            table_fac.getItems().add(fac);

            rsf.getTable_fac().getItems().clear();
            rsf.getLbl_date().setText(null);
            rsf.getLbl_facname().setText(null);
            rsf.getLbl_facprice().setText(null);
            rsf.getTf_comment().setText(null);
            rsf.getTf_facno().setText(null);

            Stage stage = (Stage) rsf.getBtn_addfac().getScene().getWindow();
            stage.close();
        });
    }//add facility done

    //delete facility
    public void delFac() {
        btn_delfac.setOnMouseClicked(me -> {
            int selRow = table_fac.getSelectionModel().getSelectedIndex();
            if (selRow >= 0) {
                ModelFacility mf = new ModelFacility();
                mf = table_fac.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Facility Booking");
                alert.setContentText("Are you sure you want to delete " + mf.getFacname() + " from the table?");

                Optional<ButtonType> sel = alert.showAndWait();
                if (sel.isPresent()) {
                    if (sel.get() == ButtonType.OK) {
                        table_fac.getItems().remove(selRow);
                    } else {
                        alert.close();
                    }
                }
            } else {
                Alert noSel = new Alert(Alert.AlertType.WARNING);
                noSel.setTitle("No Selection");
                noSel.setHeaderText("No Facility is Selected");
                noSel.setContentText("Please select a facility in the table to be deleted.");
                noSel.showAndWait();
            }
        });
    }//delete fac done

    public void resvEditPay() {
        //bottom onwards are how I access back button from the payment controller (of payment fxml)
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

        btn_next.setOnMouseClicked(me->{
            FadeTransition ft = new FadeTransition(Duration.millis(320), finalPayment);
            ft.setFromValue(0.0); //add a simple fade in transition
            ft.setToValue(1.0);
            ft.play();
            resvEditPane.getChildren().add(finalPayment); //add as children of resvEditPane

            //language=SQLite
            String query = "SELECT * FROM Payment p INNER JOIN Pay_CCard pc ON " +
                    "p.CCardNo = pc.CCardNo WHERE ResvNo = " +
                    Integer.parseInt(tf_resvno.getText());

            try {
                ResultSet rs = db.executeQuery(query);
                if (rs.next()){
                    Double ccardno = rs.getDouble("CCardNo");
                    if (!rs.wasNull()){
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

                    rpc.getLbl_refno().setText(String.valueOf(rs.getInt("PaymentID")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void validation() {
        tf_search.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(20));
        tf_resvno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(10));
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_address.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoCommaDot(50));
        tf_postcode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(12));
        tf_city.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_state.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }

    private void cellValueFactory() {
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

        //search table
        tbcol_sresv.setCellValueFactory(new PropertyValueFactory<>("resvNo"));
        tbcol_sfname.setCellValueFactory(new PropertyValueFactory<>("fName"));
        tbcol_slname.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //trick: make cells editable
        tbcol_memfname.setCellFactory(TextFieldTableCell.forTableColumn());
        tbcol_memlname.setCellFactory(TextFieldTableCell.forTableColumn());
        tbcol_memidno.setCellFactory(TextFieldTableCell.forTableColumn());

        tbcol_faccomment.setCellFactory(TextFieldTableCell.forTableColumn());
    }

}
