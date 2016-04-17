package application.assets.checkin;

import application.Validation;
import application.assets.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;

public class CIController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cellValueFacotry();
        tdCheckIn();
        doubleClick();
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
                        String query = "SELECT * FROM Reservation r INNER JOIN Customer c " +
                                "ON r.CustID = c.CustID INNER JOIN CustAddress ca ON " +
                                "ca.CustID = c.CustID WHERE r.ResvNo = " + tdci.getresv();
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



