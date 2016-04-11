package application.assets.checkin;

import application.DBConnection;
import application.Validation;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validation();
        DBConnection c = new DBConnection("Data.sqlite");
        button.setOnAction(e-> {
            try {
                String sql ="select * from Reservation rsv " +
                        "Inner join Customer cust on rsv.custid = cust.custid " +
                        "Inner join CustAddress address on cust.custid = address.custid " +
                        "Inner join CheckInOut cio on address.custid = cio.custid " +
                        "Inner join ModelRoom room on cio.roomno = room.roomno " +
                        "inner join RoomType rtype on room.roomtypeid = rtype.typeid " +
                        "inner join facbookeddate fbd on rsv.resvno = fbd.resvno " +
                        "inner join factype ftype on fbd.facno = ftype.facno " +
                        "where resvno =" +tf_ciResvNum.getText();
                ResultSet data = c.executeQuery(sql);
                ObservableList<ModelRoom> rtable = FXCollections.observableArrayList();

                data.next();
                //ObservableList<CIController> roomtable = FXCollections.observableArrayList();
                //table.setItems(roomtable);
                String firstname = data.getString("CustFName");
                String lastname = data.getString("CustLName");
                String address = data.getString("Address");
                String postcode = data.getString("PostCode");
                String city = data.getString("City");
                String country = data.getString("Country");
                String idtype = data.getString("CustID_Type");
                String idno= data.getString("CustID");
                /*String rooo mno = data.getString("roomno");
                String rtype = data.getString("typename");
                String cidate = data.getString("checkindate");
                String codate = data.getString("checkoutdate");*/


                tf_ciFirstName.setText(firstname);
                tf_ciLastName.setText(lastname);
                tf_ciAddress.setText(address);
                tf_ciCity.setText(city);
                tf_ciIDNo.setText(idno);
                tf_ciIDType.setText(idtype);
                tf_ciPostCode.setText(postcode);
                ModelRoom rm = new ModelRoom();
                rm.setRoomno(data.getString("roomno"));
                rm.setRtype(data.getString("typename"));
                rm.setCidate(data.getString("checkindate"));
                rm.setCodate(data.getString("checkoutdate"));
                rtable.add(rm);
                System.out.println(data.toString()); //for debugging, it prints the memory location of Employee class
                System.out.println(rm.getRoomno()); //for debugging, confirm works, can get the username

                ciroomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
                ciroomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
                cicid.setCellValueFactory(new PropertyValueFactory<>("cidate"));
                cicod.setCellValueFactory(new PropertyValueFactory<>("codate"));
                roomtable.setItems(rtable);


                /*ciroomno.setCellValueFactory(new PropertyValueFactory("roomno"));
                ciroomtype.setCellValueFactory(new PropertyValueFactory("rtype"));
                cicid.setCellValueFactory(new PropertyValueFactory("cidate"));
                cicod.setCellValueFactory(new PropertyValueFactory("codate"));
*/
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
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



