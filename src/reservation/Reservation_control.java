package reservation;/**
 * Created by User on 21/3/2016.
 */


import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class Reservation_control {
    ObservableList<String> gtitle = FXCollections.observableArrayList("Mr.", "Mrs.");
    @FXML private ComboBox titlecombo;
    @FXML private ComboBox idcombo;
    @FXML private ComboBox nextbtn;
    @FXML
    private void initialize() {
    titlecombo.setItems(gtitle);

    }
    @FXML private void nextbtn(ActionEvent next){
       Parent root = FXMLLoader.load(getClass().getResource(""));
        Scene scene = new Scene(root);
       .setScene(scene);
    }
}
