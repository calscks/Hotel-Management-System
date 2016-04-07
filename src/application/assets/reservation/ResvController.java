package application.assets.reservation;

//recreating ResvController to tidy up codes


import javafx.fxml.FXML;
import java.awt.*;
import application.Validation;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
public class ResvController implements Initializable{
    /*private int year1 = Calendar.getInstance().get(Calendar.YEAR);
    private int year2 = Calendar.getInstance().get(Calendar.YEAR) + 1;
    private int year3 = Calendar.getInstance().get(Calendar.YEAR) + 2;
    private int year4 = Calendar.getInstance().get(Calendar.YEAR) + 3;
    private int year5 = Calendar.getInstance().get(Calendar.YEAR) + 4;
    private int year6 = Calendar.getInstance().get(Calendar.YEAR) + 5;
    private int year7 = Calendar.getInstance().get(Calendar.YEAR) + 6;

    private ObservableList<String> gtitle = FXCollections.observableArrayList("Mr.", "Mrs.");
    private ObservableList<String> roomtype = FXCollections.observableArrayList("Commercial", "Suite", "President");
    private ObservableList<String> paymenttype = FXCollections.observableArrayList("Credit Card", "Cash");
    private ObservableList<Integer> month = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    private ObservableList<Integer> year = FXCollections.observableArrayList(year1, year2, year3, year4, year5, year6, year7);
*/
    @FXML
    private TextField tf_fname;

    @FXML
    private TextField tf_lname;

    @FXML
    private TextField tf_phoneno;

    @FXML
    private TextField tf_address;

    @FXML
    private TextField tf_postcode;

    @FXML
    private TextField tf_city;

    @FXML
    private TextField tf_idtype;

    @FXML
    private TextField tf_idno;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_phoneno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(15));
        tf_address.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoCommaDot(50));
        tf_postcode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(12));
        tf_city.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_idtype.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(10));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(10));
    }
}
