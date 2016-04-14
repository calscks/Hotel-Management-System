package application.assets.reservation;

import application.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import javax.naming.InitialContext;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ResvPayController implements Initializable{
    private int year1 = Calendar.getInstance().get(Calendar.YEAR);
    private int year2 = Calendar.getInstance().get(Calendar.YEAR) + 1;
    private int year3 = Calendar.getInstance().get(Calendar.YEAR) + 2;
    private int year4 = Calendar.getInstance().get(Calendar.YEAR) + 3;
    private int year5 = Calendar.getInstance().get(Calendar.YEAR) + 4;
    private int year6 = Calendar.getInstance().get(Calendar.YEAR) + 5;
    private int year7 = Calendar.getInstance().get(Calendar.YEAR) + 6;
    private ObservableList<Integer> month = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    private ObservableList<Integer> year = FXCollections.observableArrayList(year1, year2, year3, year4, year5, year6, year7);
    private ObservableList<String> paymenttype = FXCollections.observableArrayList("Credit Card", "Cash", "Cheque");

    @FXML private Button btn_resvBack;
    @FXML private AnchorPane resvPayPane;
    @FXML private TextField tf_cardname;
    @FXML private TextField tf_cardno;
    @FXML private TextField tf_cvccode;
    @FXML private TextField tf_discountid;
    @FXML private ComboBox<String> resvCBox_PayType;
    @FXML private ComboBox<String> resvCBox_Month;
    @FXML private ComboBox<String> resvCBox_Year;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validation();

        resvCBox_PayType.setItems(paymenttype);
        resvCBox_PayType.getSelectionModel().select(0);



    }

    private void validation() {
        tf_cardname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(30));
        tf_cardno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(19));
        tf_cvccode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(4));
        tf_discountid.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
    }

    //getter that mentioned in the reservation controller
    public Button getBtn_resvBack() {
        return btn_resvBack;
    }
}
