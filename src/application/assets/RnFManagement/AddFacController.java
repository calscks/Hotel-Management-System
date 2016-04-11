package application.assets.RnFManagement;

import application.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;

/**
 * Created by User on 28/3/2016.
 */
public class AddFacController implements Initializable{

    @FXML private TextField tf_facno;
    @FXML private TextField tf_facname;
    @FXML private TextField tf_morning;
    @FXML private TextField tf_night;
    @FXML private TextField tf_wholeday;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validation();
    }

    private void validation() {
        tf_facno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_facname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_morning.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_night.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_wholeday.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
    }
}
