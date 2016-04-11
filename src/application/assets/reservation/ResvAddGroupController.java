package application.assets.reservation;



import application.DBConnection;
import application.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ResvAddGroupController implements Initializable {
    @FXML private TextField tf_fname;
    @FXML private TextField tf_lname;
    @FXML private TextField tf_idno;
    @FXML private ComboBox<String> cbox_idtype;
    @FXML private ComboBox<String> cbox_roomno;

    DBConnection db = new DBConnection("Data.sqlite");

    @Override
    public void initialize(URL url, ResourceBundle rb){
        ObservableList<String> room = FXCollections.observableArrayList();
        validation();
    }
    private void validation() {
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }
}
