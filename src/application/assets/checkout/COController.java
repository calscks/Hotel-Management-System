package application.assets.checkout;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import application.DBConnection;
import application.Validation;
import javafx.scene.control.*;
import application.assets.AutoCompleteCBoxListener;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class COController implements Initializable {

    @FXML private TextField tf_coRoomNo;
    @FXML private TextField tf_coFirstName;
    @FXML private TextField tf_coLastName;
    @FXML private TextField tf_coIDNo;
    @FXML private TextField tf_coAddress;
    @FXML private TextField tf_coPostCode;
    @FXML private TextField tf_coCity;


    @FXML private ComboBox<String> cbox_coCountry;
    @FXML private ComboBox<String> cbox_coIDType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new AutoCompleteCBoxListener(cbox_coCountry);
        String [] locale = Locale.getISOCountries();
        for(String countrycode: locale){
            Locale country = new Locale("", countrycode);
            cbox_coCountry.getItems().add(country.getDisplayCountry());

        }
         DBConnection c = new DBConnection("Data.sqlite");
        tf_coRoomNo.textProperty().addListener((observable, oldValue, newValue)-> {
        try {
            String sql = "SELECT * FROM CheckInOut\n" +
                    "  INNER JOIN Room using (RoomNo)\n" +
                    "  INNER JOIN Roomtype on Room.RoomTypeID = RoomType.TypeID\n" +
                    "  INNER JOIN Customer USING (CustID)\n" +
                    "  INNER JOIN CustAddress USING (CustID)\n" +
                    "  WHERE RoomNo ="+ "'"+ tf_coRoomNo.getText() + "'";

            ResultSet codata = c.executeQuery(sql);

            String customerfname = codata.getString("CustFName");
            String customerlname = codata.getString("CustLName");
            String IDtype =codata.getString("CustID_Type");
            String customerid = codata.getString("CustID");
            String address = codata.getString("Address");
            String postcode = codata.getString("PostCode");
            String city = codata.getString("City");
            String Country = codata.getString("Country");

            tf_coIDNo.setText(customerid);
            tf_coFirstName.setText(customerfname);
            tf_coLastName.setText(customerlname);
            cbox_coIDType.getItems().add(IDtype);
            cbox_coIDType.getSelectionModel().select(IDtype);
            tf_coPostCode.setText(postcode);
            tf_coCity.setText(city);
            tf_coAddress.setText(address);
            cbox_coCountry.getItems().add(Country);
            cbox_coCountry.getSelectionModel().select(Country);

        }
        catch(SQLException t1){
        t1.printStackTrace();
        }
        });


    }
}
