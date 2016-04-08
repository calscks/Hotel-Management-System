package application.assets.checkin;

import application.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import application.DBConnection.*;
import javafx.scene.control.TableColumn;
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
    private ComboBox<String> Cbox_ciCountry;
    @FXML
    private TableColumn ciroomno;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection c = new DBConnection("Data.sqlite");
        button.setOnAction(e-> {
            try {
                String sql ="select * from Reservation rsv " +
                        "Inner join Customer cust on rsv.custid = cust.custid " +
                        "Inner join CustAddress address on cust.custid = address.custid " +
                        "Inner join checkinout cio on address.custid = cio.custid " +
                        "Inner join room room on cio.roomno = room.roomno " +
                        "inner join roomtype rtype on room.roomtypeid = rtype.typeid ";
                ResultSet data = c.executeQuery(sql);
                data.next();
                ObservableList<String> roomtable = FXCollections.observableArrayList();

                String firstname = data.getString("CustFName");
                String lastname = data.getString("CustLName");
                String address = data.getString("Address");
                String postcode = data.getString("PostCode");
                String city = data.getString("City");
                String country = data.getString("Country");
                String idtype = data.getString("CustID_Type");
                String idno = data.getString("CustID");
                String roomno = data.getString("roomno");
                String rtype = data.getString("typename");
                String cidate = data.getString("checkindate");
                String codate = data.getString("checkoutdate");


                tf_ciFirstName.setText(firstname);
                tf_ciLastName.setText(lastname);
                tf_ciAddress.setText(address);
                tf_ciCity.setText(city);
                tf_ciIDNo.setText(idno);
                tf_ciIDType.setText(idtype);
                tf_ciPostCode.setText(postcode);
                //roomtable.set(ciroomno.setText(roomno));
                //cbox_ciCountry.setText(country);

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
}



