package application.assets.reservation;

import application.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 5/4/2016.
 */
public class ResvEditRoomController implements Initializable{

    @FXML private TextField tf_roomno;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validation();
    }

    private void validation() {
        tf_roomno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(10));
    }
}
