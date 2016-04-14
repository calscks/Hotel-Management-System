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
    @FXML private TextField tf_addfacprice;
    @FXML private Button btn_addfac;

    public Button getBtn_addfac(){return btn_addfac;}
    public TextField getTf_facno(){return tf_facno;}
    public TextField getTf_facname(){return tf_facname;}
    public TextArea getTa_addfacdesc(){return ta_addfacdesc;}
    public TextField getTf_addfacprice(){return tf_addfacprice;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validation();
    }

    private void validation() {
        tf_facno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_facname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        ta_addfacdesc.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(50));
        tf_addfacprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
    }
}
