package application.assets.checkin;

import application.DBConnection;
import application.Validation;
import application.assets.*;
import application.assets.reservation.ResvAddGroupController;
import application.assets.reservation.ResvFacilityController;
import application.assets.reservation.ResvRoomController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;

public class CIController implements Initializable{

    @FXML
    private Button btn_ciNext;
    @FXML
    private TextField tf_ciResvNum;
    @FXML
    private TextField tf_ciFirstName;
    @FXML
    private Button button;
    @FXML
    private Label date;
    @FXML
    private TextField tf_ciLastName;
    @FXML
    private TextField tf_ciPhoneNo;
    @FXML
    private TextField tf_ciCountry;
    @FXML
    private TextField tf_ciAddress;
    @FXML
    private TextField tf_ciPostCode;
    @FXML
    private TextField tf_ciCity;
    @FXML
    private TextField tf_ciIDType;
    @FXML
    private TextField tf_ciIDNo;
    @FXML
    private TextField tf_ciState;
    @FXML
    private Button btn_ciAddGroupMember;
    @FXML
    private Button btn_ciAddRoom;
    @FXML
    private Button btn_ciAddFacility;
    @FXML
    private TableView<ModelRoom> roomtable;
    @FXML
    private TableColumn<ModelRoom, String> ciroomno;
    @FXML
    private TableColumn<ModelRoom, String> ciroomtype;
    @FXML
    private TableColumn<ModelRoom, String> cicid;
    @FXML
    private TableColumn<ModelRoom, String> cicod;
    @FXML
    private TableColumn<ModelRoom, String> roomprice;
    @FXML
    private TableColumn<ModelRoom, String> roomcategory;

    @FXML
    private TableView<ModelFacility> cifactable;
    @FXML
    private TableColumn<ModelFacility, String> bookfaccol;
    @FXML
    private TableColumn<ModelFacility, String> facno;
    @FXML
    private TableColumn<ModelFacility, String> facdate;
    @FXML
    private TableColumn<ModelFacility, String> facprice;
    @FXML
    private TableView<ModelGroupMember> grouptable;
    @FXML
    private TableColumn<ModelGroupMember, String> tableC_ciGroupFirstName;
    @FXML
    private TableColumn<ModelGroupMember, String> tableC_ciGroupLastName;
    @FXML
    private TableColumn<ModelGroupMember, String> tableC_ciIcno;
    @FXML
    private TableColumn<ModelGroupMember, String> tableC_ciGroupRoomNo;
    @FXML
    private TableColumn<ModelGroupMember, String> tableC_ciGroupIdType;
    @FXML
    private TableView<ModelCIToday> table_ciToday;
    @FXML
    private TableColumn<ModelCIToday, String> tbcol_resvno;
    @FXML
    private TableColumn<ModelCIToday, String> tbcol_fname;
    @FXML
    private TableColumn<ModelCIToday, String> tbcol_lname;
    @FXML
    private TableColumn<ModelCIToday, String> tbcol_co;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validation();
        initial()

        tf_ciResvNum.textProperty().addListener((observable, oldValue,newValue)-> {
            try {
                String sql = "select * from Reservation rsv " +
                        "Inner join Customer cust on rsv.custid = cust.custid " +
                        "Inner join CustAddress address on cust.custid = address.custid " +
                        "Inner join CheckInOut cio on address.custid = cio.custid " +
                        "Inner join roombooking rbk on rsv.resvno = rbk.resvno  " +
                        "Inner join roomtype rtype on rbk.RoomTypeName = rtype.TypeName " +
                        "where rsv.ResvNo = " +tf_ciResvNum.getText();
                String sql2 = "select * from FacBookedDate fbd " +
                        "INNER JOIN FacType ftype on fbd.FacNo = ftype.FacNo " +
                        "where ResvNo =" +tf_ciResvNum.getText();
                String sql3 =" select * from CustomerGroup custgroup " +
                        "inner join customer cust on custgroup.G_CustID = cust.CustID " +
                        "INNER JOIN Reservation rsv on cust.CustID = rsv.CustID " +
                        "where rsv.ResvNo ="+tf_ciResvNum.getText() ;
                ObservableList<ModelRoom> rtable = FXCollections.observableArrayList();
                ObservableList<ModelFacility> ftable = FXCollections.observableArrayList();
                ObservableList<ModelGroupMember> gtable = FXCollections.observableArrayList();
                ResultSet data = db.executeQuery(sql);

                String firstname = data.getString("CustFName");
                String lastname = data.getString("CustLName");
                String address = data.getString("Address");
                String postcode = data.getString("PostCode");
                String city = data.getString("City");
                String country = data.getString("Country");
                String state = data.getString("state");
                String idtype = data.getString("CustID_Type");
                String idno = data.getString("CustID");

                tf_ciFirstName.setText(firstname);
                tf_ciLastName.setText(lastname);
                tf_ciAddress.setText(address);
                tf_ciCountry.setText(country);
                tf_ciCity.setText(city);
                tf_ciIDNo.setText(idno);
                tf_ciIDType.setText(idtype);
                tf_ciPostCode.setText(postcode);
                tf_ciState.setText(state);

                while (data.next()){
                    ModelRoom rm = new ModelRoom();
                    rm.setRoomno(data.getString("roomno"));
                    rm.setRtype(data.getString("roomtypename"));
                    rm.setRoomcat(data.getString("typegroup"));
                    rm.setRoomprice(data.getString("roomprice"));
                    rm.setCidate(data.getString("date_ci"));
                    rm.setCodate(data.getString("date_co"));
                    rtable.add(rm);

                    System.out.println(data.toString()); //for debugging, it prints the memory location of Employee class
                    System.out.println(rm.getRoomno()); //for debugging, confirm works, can get the usernameSystem.out.println(rm.getRoomno()); //for debugging, confirm works, can get the username
                    //System.out.println(fc.getfacname()); //for debugging, confirm works, can get the username

                }
                ResultSet data2 = db.executeQuery(sql2);
                while (data2.next()){
                    ModelFacility fc = new ModelFacility();
                    fc.setBookedfac(data2.getString("facname"));
                    fc.setFacno(data2.getString("facno"));
                    fc.setBookedfacdate(data2.getString("bookdate"));
                    fc.setFacprice(data2.getString("facprice"));
                    ftable.add(fc);
                }
                ResultSet data3 = db.executeQuery(sql3);
                while (data3.next()) {
                    ModelGroupMember gm = new ModelGroupMember();
                    gm.setMemFName(data3.getString("custfname"));
                    gm.setMemLName(data3.getString("custlname"));
                    gm.setIdType(data3.getString("custid_type"));
                    gm.seticNum(data3.getString("idno"));
                    gtable.add(gm);
                }
                tableC_ciGroupFirstName.setCellValueFactory(new PropertyValueFactory<>("memFName"));
                tableC_ciGroupLastName.setCellValueFactory(new PropertyValueFactory<>("memLName"));
                tableC_ciGroupIdType.setCellValueFactory(new PropertyValueFactory<>("idType"));
                tableC_ciIcno.setCellValueFactory(new PropertyValueFactory<>("icNum"));
                grouptable.setItems(gtable);
                ciroomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
                ciroomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
                roomcategory.setCellValueFactory(new PropertyValueFactory<>("roomcat"));
                roomprice.setCellValueFactory(new PropertyValueFactory<>("roomprice"));
                cicid.setCellValueFactory(new PropertyValueFactory<>("cidate"));
                cicod.setCellValueFactory(new PropertyValueFactory<>("codate"));
                roomtable.setItems(rtable);
                bookfaccol.setCellValueFactory(new PropertyValueFactory<>("bookedfac"));
                facno.setCellValueFactory(new PropertyValueFactory<>("facno"));
                facdate.setCellValueFactory(new PropertyValueFactory<>("bookedfacdate"));
                facprice.setCellValueFactory(new PropertyValueFactory<>("facprice"));
                cifactable.setItems(ftable);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    private void validation() {
        tf_ciResvNum.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(10));
        tf_ciFirstName.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_ciLastName.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_ciIDType.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(10));
        tf_ciIDNo.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }
    public void initial(){
        try {
            String sql4 = "select * from CheckInOut cio " +
                    "inner join Reservation rsv on cio.CustID = rsv.CustID " +
                    "INNER JOIN Customer cust on rsv.CustID = cust.CustID " +
                    "where cio.CheckInDate = CURRENT_DATE";
            ResultSet data4 = db.executeQuery(sql4);
            ObservableList<ModelCIToday> citable = FXCollections.observableArrayList();
            while (data4.next()){
                ModelCIToday ci = new ModelCIToday();
                ci.setresv(data4.getString("resvno"));
                ci.setfname(data4.getString("custfname"));
                ci.setlname(data4.getString("custlname"));
                ci.setco(data4.getString("checkoutdate"));
                citable.add(ci);
            }
            tbcol_resvno.setCellValueFactory(new PropertyValueFactory<>("resv"));
            tbcol_fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
            tbcol_lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
            tbcol_co.setCellValueFactory(new PropertyValueFactory<>("co"));
            table_ciToday.setItems(citable);
        }
        catch (SQLException e1){
            e1.printStackTrace();
        }
    }

}



