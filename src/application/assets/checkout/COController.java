package application.assets.checkout;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import application.DBConnection;
import application.Validation;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class COController implements Initializable {

    @FXML private TextField tf_coRoomNo;
    @FXML private TextField tf_coFirstName;
    @FXML private

    @Override
    public void initialize(URL location, ResourceBundle resources) {
   DBConnection c = new DBConnection("Data.sqlite");
        tf_coRoomNo.textProperty().addListener((observable, oldValue, newValue)->{

        });


    }
}
