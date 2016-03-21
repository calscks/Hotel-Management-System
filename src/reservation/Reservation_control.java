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

import java.io.IOException;


public class Reservation_control {
    ObservableList<String> gtitle = FXCollections.observableArrayList("Mr.", "Mrs.");
    ObservableList<String> roomtype = FXCollections.observableArrayList("Commercial","Suite","President");

    @FXML private ComboBox titlecombo;
    @FXML private ComboBox sidcombo;
    @FXML private ComboBox nextbtn;
    @FXML private ComboBox roomtypecombo;

    @FXML
    private void initialize() {
    titlecombo.setItems(gtitle);
    roomtypecombo.setItems(roomtype);
    }

    @FXML private void nextbtn(ActionEvent next) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("R_roominfo.fxml"));
        Scene scene = new Scene(root);

    }
}
