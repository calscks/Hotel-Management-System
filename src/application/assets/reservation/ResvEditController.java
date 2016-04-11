package application.assets.reservation;

import application.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ResvEditController implements Initializable{

    @FXML private TextField tf_search;
    @FXML private TextField tf_resvno;
    @FXML private TextField tf_fname;
    @FXML private TextField tf_lname;
    @FXML private TextField tf_phoneno;
    @FXML private TextField tf_address;
    @FXML private TextField tf_postcode;
    @FXML private TextField tf_city;
    @FXML private TextField tf_idtype;
    @FXML private TextField tf_idno;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validation();
    }

    private void validation() {
        tf_search.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(20));
        tf_resvno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(10));
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_phoneno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(15));
        tf_address.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoCommaDot(50));
        tf_postcode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(12));
        tf_city.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_idtype.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(10));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }
}
