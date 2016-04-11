package application.assets.reservation;/**
 * Created by User on 31/3/2016.
 */

import application.Validation;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.awt.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ResvRoomController extends Application implements Initializable{

    @FXML private TextField tf_roomno;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validation();
    }

    private void validation() {
        tf_roomno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(10));
    }
}
