package application.assets.RnFManagement;

import application.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.awt.geom.Area;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Created by User on 28/3/2016.
 */
public class AddFacController implements Initializable{

    @FXML private TextField tf_facno;
    @FXML private TextField tf_facname;
    @FXML private TextArea ta_addfacdesc;
    @FXML private TextField tf_morning;
    @FXML private TextField tf_night;
    @FXML private TextField tf_wholeday;
    @FXML private Button btn_addfac;

    public Button getBtn_addfac(){return btn_addfac;}
    public TextField getTf_facno(){return tf_facno;}
    public TextField getTf_facname(){return tf_facname;}
    public TextArea getTa_addfacdesc(){return ta_addfacdesc;}
    public TextField getTf_morning(){return tf_morning;}
    public TextField getTf_night(){return tf_night;}
    public TextField getTf_wholeday(){return tf_wholeday;}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validation();
    }

    private void validation() {
        tf_facno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_facname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        ta_addfacdesc.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(50));
        tf_morning.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_night.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_wholeday.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
    }
}
