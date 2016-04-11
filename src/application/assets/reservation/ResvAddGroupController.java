package application.assets.reservation;


import application.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class ResvAddGroupController implements Initializable{

    @FXML
    private TextField tf_fname;

    @FXML
    private TextField tf_lname;

    @FXML
    private TextField tf_idno;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validation();
    }

    private void validation() {
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }
}
