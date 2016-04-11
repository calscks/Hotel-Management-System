package application.assets.checkin;

import application.DBConnection;
import application.assets.admin.Employee;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import application.DBConnection.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.table.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private TableView<Room> roomtable;
    @FXML
    private TableColumn<Room, String> ciroomno;
    @FXML
    private TableColumn<Room, String> ciroomtype;
    @FXML
    private TableColumn<Room, String> cicid;
    @FXML
    private TableColumn<Room, String> cicod;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection c = new DBConnection("Data.sqlite");
        button.setOnAction(e-> {
            try {
                String sql ="select * from Reservation rsv " +
                        "Inner join Customer cust on rsv.custid = cust.custid " +
                        "Inner join CustAddress address on cust.custid = address.custid " +
                        "Inner join CheckInOut cio on address.custid = cio.custid " +
                        "Inner join Room room on cio.roomno = room.roomno " +
                        "inner join RoomType rtype on room.roomtypeid = rtype.typeid " +
                        "inner join facbookeddate fbd on rsv.resvno = fbd.resvno " +
                        "inner join factype ftype on fbd.facno = ftype.facno " +
                        "where resvno =" +tf_ciResvNum.getText();
                ResultSet data = c.executeQuery(sql);
                ObservableList<Room> rtable = FXCollections.observableArrayList();

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
                Room rm = new Room(); //create an object for Employee everytime a next occurs
                rm.setRoomno(data.getString("roomno"));
                rm.setRtype(data.getString("typename"));
                rm.setCidate(data.getString("checkindate"));
                rm.setCodate(data.getString("checkoutdate"));
                rtable.add(rm);
                System.out.println(data.toString()); //for debugging, it prints the memory location of Employee class
                System.out.println(rm.getroomno()); //for debugging, confirm works, can get the username

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
}



