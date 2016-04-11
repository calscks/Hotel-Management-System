package application.assets.checkout;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import application.DBConnection;
import application.Validation;
import javafx.scene.control.*;
import application.assets.AutoCompleteCBoxListener;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class COController implements Initializable {

    @FXML private TextField tf_coRoomNo;
    @FXML private TextField tf_coFirstName;
    @FXML private TextField tf_coLastName;
    @FXML private ComboBox<String> cbox_coCountry;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new AutoCompleteCBoxListener(cbox_coCountry);
    String [] locale = Locale.getISOCountries();
        for(String countrycode: locale){
            Locale country = new Locale("", countrycode);
            cbox_coCountry.getItems().add(country.getDisplayCountry());

        }
   DBConnection c = new DBConnection("Data.sqlite");
        tf_coRoomNo.textProperty().addListener((observable, oldValue, newValue)->{
            String sql = "";
            try {
                c.executeQuery("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


    }
}
