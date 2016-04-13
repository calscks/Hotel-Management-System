package application.assets.reservation;

import application.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class ResvFacilityController implements Initializable{

    @FXML private TextField tf_facno;
    @FXML private TextArea tf_comment;
    @FXML private ComboBox<String> cbox_fac1;
    @FXML private ComboBox<String> cbox_booktime;
    @FXML private Button btn_addfac;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validation();

    }

    private void validation() {
        tf_facno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
    }
}
