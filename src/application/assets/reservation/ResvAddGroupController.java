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

    @Override
    public void initialize(URL url, ResourceBundle rb){
        DBConnection db = new DBConnection("Data.sqlite");
        ObservableList<String> room = FXCollections.observableArrayList();
        ObservableList<String> idtype =
                FXCollections.observableArrayList("Identity Card", "Passport No");

        validation();

        cbox_idtype.setItems(idtype);



    }
    private void validation() {
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }

    public TextField getTf_fname() {
        return tf_fname;
    }

    public TextField getTf_lname() {
        return tf_lname;
    }

    public TextField getTf_idno() {
        return tf_idno;
    }

    public ComboBox<String> getCbox_roomno() {
        return cbox_roomno;
    }
}
