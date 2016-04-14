package application.assets.reservation;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ResvEditController implements Initializable{

    @FXML
    private Button btn_ciNext;
    @FXML
    private TextField tf_search;
    @FXML
    private Button btn_search;
    @FXML
    private TextField tf_resvno;
    @FXML
    private TextField tf_fname;
    @FXML
    private Button button;
    @FXML
    private TextField tf_lname;
    @FXML
    private TextField tf_phoneno;
    @FXML
    private ComboBox resvIdType_CBox;
    @FXML
    private TextField tf_address;
    @FXML
    private TextField tf_postcode;
    @FXML
    private TextField tf_city;
    @FXML
    private TextField tf_idtype;
    @FXML
    private TextField tf_idno;
    @FXML
    private Button btn_ciAddGroupMember;
    @FXML
    private Button btn_ciAddRoom;
    @FXML
    private TableView<ModelGroupMember> guestTable;
    @FXML
    private TableColumn<ModelGroupMember, String> fname;
    @FXML
    private TableColumn<ModelGroupMember, String> lname;
    @FXML
    private TableColumn<ModelGroupMember, String> passporticno;
    @FXML
    private TableColumn<ModelGroupMember, String> roomno;
    @FXML
    private TableView<ModelRoom> roomTable;
    @FXML
    private TableColumn<ModelRoom, String> roomCat;
    @FXML
    private TableColumn<ModelRoom, String> roomType;
    @FXML
    private TableColumn<ModelRoom, String> bookDate;
    @FXML
    private TableColumn<ModelRoom, String> endDate;
    @FXML
    private TableView<ModelFacility> facilityTable;
    @FXML
    private TableColumn<ModelFacility, String> bookfac;
    @FXML
    private TableColumn<ModelFacility, String> facilityprice;
    @FXML
    private TableColumn<ModelFacility, String> bookdate;
    @FXML
    private TableColumn<ModelFacility, String> booktime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validation();

    DBConnection c = new DBConnection("Data.sqlite");

            btn_search.setOnAction((event)-> {
                try {
            String sql = "select * from Reservation rsv " +
                    "Inner join Customer cust on rsv.custid = cust.custid " +
                    "Inner join CustAddress address on cust.custid = address.custid " +
                    "Inner join CheckInOut cio on address.custid = cio.custid " +
                    "Inner join roombooking rbk on rsv.resvno = rbk.resvno  " +
                    "where rsv.ResvNo like '%" +tf_search.getText() +"'";
            String sql2 = "select * from FacBookedDate fbd " +
                    "INNER JOIN FacType ftype on fbd.FacNo = ftype.FacNo " +
                    "where ResvNo =" +tf_search.getText();
            String sql3 =" select * from CustomerGroup custgroup " +
                    "inner join customer cust on custgroup.G_CustID = cust.CustID " +
                    "INNER JOIN Reservation rsv on cust.CustID = rsv.CustID " +
                    "where rsv.ResvNo = '"+tf_search.getText() +"'";
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

            tf_fname.setText(firstname);
            tf_lname.setText(lastname);
            tf_address.setText(address);
            tf_city.setText(city);
            tf_idno.setText(idno);
            tf_idtype.setText(idtype);
            tf_postcode.setText(postcode);
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
                fc.setBookedfac(data2.getString("facno"));
                fc.setFacprice(data2.getString("facprice"));
                fc.setBookedfacdate(data2.getString("bookdate"));
                fc.setBookedfactime(data2.getString("facdesc"));
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
            fname.setCellValueFactory(new PropertyValueFactory<>("memFName"));
            lname.setCellValueFactory(new PropertyValueFactory<>("memLName"));
            passporticno.setCellValueFactory(new PropertyValueFactory<>("icNum"));
            roomno.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
            guestTable.setItems(gtable);
            roomCat.setCellValueFactory(new PropertyValueFactory<>("roomno"));
            roomType.setCellValueFactory(new PropertyValueFactory<>("rtype"));
            bookDate.setCellValueFactory(new PropertyValueFactory<>("cidate"));
            endDate.setCellValueFactory(new PropertyValueFactory<>("codate"));
            roomTable.setItems(rtable);
            bookfac.setCellValueFactory(new PropertyValueFactory<>("bookedfac"));
            facilityprice.setCellValueFactory(new PropertyValueFactory<>("facprice"));
            bookdate.setCellValueFactory(new PropertyValueFactory<>("bookedfacdate"));
            booktime.setCellValueFactory(new PropertyValueFactory<>("bookedfactime"));
            facilityTable.setItems(ftable);


                /*ciroomno.setCellValueFactory(new PropertyValueFactory("roomno"));
                ciroomtype.setCellValueFactory(new PropertyValueFactory("rtype"));
                cicid.setCellValueFactoryinner join factype ftype on fbd.FacNo = ftype.FacNonew PropertyValueFactory("cidate"));
                cicod.setCellValueFactory(new PropertyValueFactory("codate"));
*/
        }
        catch (SQLException e) {
            e.printStackTrace();
        };
    });}
    private void validation() {
        tf_search.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(20));
        tf_resvno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(10));
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_phoneno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(15));
        tf_address.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoCommaDot(50));
        tf_postcode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(12));
        tf_city.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_idtype.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(10));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }
}
