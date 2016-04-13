package application.assets.checkin;

import application.DBConnection;
import application.Validation;
import application.assets.ModelFacility;
import application.assets.ModelGroupMember;
import application.assets.ModelRoom;
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
    private TextField tf_ciLastName;
    @FXML
    private TextField tf_ciPhoneNo;
    @FXML
    private ComboBox cbox_ciCountry;
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
    private TableView<ModelFacility> cifactable;
    @FXML
    private TableColumn<ModelFacility, String> bookfaccol;
    @FXML
    private TableColumn<ModelFacility, String> facprice;
    @FXML
    private TableColumn<ModelFacility, String> facdate;
    @FXML
    private TableColumn<ModelFacility, String> factime;
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validation();
        DBConnection c = new DBConnection("Data.sqlite");

        tf_ciResvNum.textProperty().addListener((observable, oldValue,newValue)-> {
            try {
                String sql = "select * from Reservation rsv " +
                        "Inner join Customer cust on rsv.custid = cust.custid " +
                        "Inner join CustAddress address on cust.custid = address.custid " +
                        "Inner join CheckInOut cio on address.custid = cio.custid " +
                        "Inner join roombooking rbk on rsv.resvno = rbk.resvno  " +
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
                ResultSet data = c.executeQuery(sql);

                String firstname = data.getString("CustFName");
                String lastname = data.getString("CustLName");
                String address = data.getString("Address");
                String postcode = data.getString("PostCode");
                String city = data.getString("City");
                String country = data.getString("Country");
                String idtype = data.getString("CustID_Type");
                String idno = data.getString("CustID");

                tf_ciFirstName.setText(firstname);
                tf_ciLastName.setText(lastname);
                tf_ciAddress.setText(address);
                tf_ciCity.setText(city);
                tf_ciIDNo.setText(idno);
                tf_ciIDType.setText(idtype);
                tf_ciPostCode.setText(postcode);
                while (data.next()){
                    ModelRoom rm = new ModelRoom();
                    rm.setRoomno(data.getString("roomno"));
                    rm.setRtype(data.getString("roomtypename"));
                    rm.setCidate(data.getString("date_ci"));
                    rm.setCodate(data.getString("date_co"));
                    rtable.add(rm);

                    System.out.println(data.toString()); //for debugging, it prints the memory location of Employee class
                    System.out.println(rm.getRoomno()); //for debugging, confirm works, can get the usernameSystem.out.println(rm.getRoomno()); //for debugging, confirm works, can get the username
                    //System.out.println(fc.getfacname()); //for debugging, confirm works, can get the username

                }
                ResultSet data2 = c.executeQuery(sql2);
                while (data2.next()){
                    ModelFacility fc = new ModelFacility();
                    fc.setbookedfac(data2.getString("facno"));
                    fc.setfacprice(data2.getString("facmornprice"));
                    fc.setbookedfacdate(data2.getString("date"));
                    fc.setbookedfactime(data2.getString("time"));
                    ftable.add(fc);
                }
                ResultSet data3 = c.executeQuery(sql3);
                while (data3.next()) {
                    ModelGroupMember gm = new ModelGroupMember();
                    gm.setMemFName(data3.getString("custfname"));
                    gm.setMemLName(data3.getString("custlname"));
                    gm.seticNum(data3.getString("passportno"));
                    gm.setRoomNo(data3.getString("blacklisted"));
                    gtable.add(gm);
                }
                tableC_ciGroupFirstName.setCellValueFactory(new PropertyValueFactory<>("memFName"));
                tableC_ciGroupLastName.setCellValueFactory(new PropertyValueFactory<>("memLName"));
                tableC_ciIcno.setCellValueFactory(new PropertyValueFactory<>("icNum"));
                tableC_ciGroupRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
                grouptable.setItems(gtable);
                ciroomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
                ciroomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
                cicid.setCellValueFactory(new PropertyValueFactory<>("cidate"));
                cicod.setCellValueFactory(new PropertyValueFactory<>("codate"));
                roomtable.setItems(rtable);
                bookfaccol.setCellValueFactory(new PropertyValueFactory<>("bookedfac"));
                facprice.setCellValueFactory(new PropertyValueFactory<>("facprice"));
                facdate.setCellValueFactory(new PropertyValueFactory<>("bookedfacdate"));
                factime.setCellValueFactory(new PropertyValueFactory<>("bookedfactime"));
                cifactable.setItems(ftable);


                /*ciroomno.setCellValueFactory(new PropertyValueFactory("roomno"));
                ciroomtype.setCellValueFactory(new PropertyValueFactory("rtype"));
                cicid.setCellValueFactoryinner join factype ftype on fbd.FacNo = ftype.FacNonew PropertyValueFactory("cidate"));
                cicod.setCellValueFactory(new PropertyValueFactory("codate"));
*/
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
        /*tf_ciIDNo.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String sql3 =" select * from Reservation rsv " +
                        "INNER JOIN Customer cust on rsv.CustID = cust.CustID " +
                        "INNER JOIN CustomerGroup custgroup on cust.CustID = custgroup.G_CustID " +
                        "where cust.CustID = "+tf_ciIDNo.getText();
                ResultSet data3 = c.executeQuery(sql3);
                ObservableList<ModelGroupMember> gtable = FXCollections.observableArrayList();
                while (data3.next()) {
                    ModelGroupMember gm = new ModelGroupMember();
                    gm.setMemFName(data3.getString("CustFname"));
                    gm.setMemLName(data3.getString("custlname"));
                    gm.setIdNo(data3.getString("G_custid"));
                    gm.setRoomNo(data3.getString("passportno"));
                    gtable.add(gm);
                }
                tableC_ciGroupFirstName.setCellValueFactory(new PropertyValueFactory<>("tableC_ciGroupFirstName"));
                tableC_ciGroupLastName.setCellValueFactory(new PropertyValueFactory<>("tableC_ciGroupLastName"));
                tableC_ciGroupIDno.setCellValueFactory(new PropertyValueFactory<>("tableC_ciGroupIDno"));
                tableC_ciGroupRoomNo.setCellValueFactory(new PropertyValueFactory<>("tableC_ciGroupRoomNo"));
                grouptable.setItems(gtable);
            }catch (SQLException e) {
                e.printStackTrace();
            }
        });*/
    }

    private void validation() {
        tf_ciResvNum.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(10));
        tf_ciFirstName.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_ciLastName.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_ciPhoneNo.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(15));
        tf_ciAddress.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoCommaDot(50));
        tf_ciPostCode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(12));
        tf_ciCity.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_ciIDType.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(10));
        tf_ciIDNo.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }
}



