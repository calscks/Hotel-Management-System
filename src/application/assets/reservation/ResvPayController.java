package application.assets.reservation;

import application.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Calendar;
import java.util.Objects;
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
    @FXML private Button btn_reserve;
    @FXML private TextField tf_cardname;
    @FXML private TextField tf_cardno;
    @FXML private TextField tf_cvccode;
    @FXML private ComboBox<String> cbox_PayType;
    @FXML private ComboBox<Integer> cbox_Month;
    @FXML private ComboBox<Integer> cbox_Year;
    @FXML private Label lbl_switchable;
    @FXML private Label lbl_refno;
    @FXML private Label lbl_total;
    @FXML private Label lbl_tax;
    @FXML private Label lbl_deposit;
    @FXML private Label lbl_subtotal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validation();

        lbl_switchable.setText("Name on Card :");
        lbl_total.setText(null);
        lbl_tax.setText(null);
        lbl_deposit.setText(null);
        lbl_subtotal.setText(null);

        cbox_PayType.setItems(paymenttype);
        cbox_Month.setItems(month);
        cbox_Year.setItems(year);
        cbox_PayType.getSelectionModel().select(0);

        cbox_PayType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.equals(cbox_PayType.getSelectionModel().getSelectedItem(), "Credit Card")){
                tf_cardno.setDisable(false);
                tf_cvccode.setDisable(false);
                tf_cardname.setDisable(false);
                cbox_Month.setDisable(false);
                cbox_Year.setDisable(false);
                lbl_switchable.setText("Name on Card :");
            } else if (Objects.equals(cbox_PayType.getSelectionModel().getSelectedItem(), "Cheque")){
                tf_cardno.setDisable(true);
                tf_cvccode.setDisable(true);
                tf_cardname.setDisable(false);
                cbox_Month.setDisable(true);
                cbox_Year.setDisable(true);
                lbl_switchable.setText("Cheque No :");
            } else {
                tf_cardno.setDisable(true);
                tf_cvccode.setDisable(true);
                cbox_Month.setDisable(true);
                cbox_Year.setDisable(true);
                tf_cardname.setDisable(true);
            }
        });

    }

    private void validation() {
        tf_cardname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(30));
        tf_cardno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(19));
        tf_cvccode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(4));
    }

    //getter that mentioned in the reservation controller
    public Button getBtn_resvBack() {
        return btn_resvBack;
    }
}
