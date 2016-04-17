package application.assets.reservation;

import application.Validation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
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
    @FXML private Label lbl_paid;
    @FXML private Label lbl_balance;
    @FXML private Label lbl_switchbal;
    @FXML private RadioButton rb_deposit;
    @FXML private RadioButton rb_full;
    @FXML private ToggleGroup paygroup;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validation();

        System.out.println("Payment ui loaded.\n");

        lbl_switchable.setText("Name on Card :");
        lbl_total.setText(null);
        lbl_tax.setText(null);
        lbl_deposit.setText(null);
        lbl_subtotal.setText(null);
        lbl_balance.setText(null);
        lbl_paid.setText(null);
        rb_deposit.setDisable(false);
        rb_full.setDisable(false);

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

        paygroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            float subtotal = Float.parseFloat(lbl_subtotal.getText());
            float dep = Float.parseFloat(lbl_deposit.getText());
            if (rb_deposit.isSelected()){
                float bal = subtotal - dep;
                lbl_paid.setText(String.format(Locale.UK, "%.2f", dep));
                lbl_balance.setText(String.format(Locale.UK, "%.2f", bal));
            } else if (rb_full.isSelected()){
                float bal = subtotal - subtotal;
                lbl_paid.setText(lbl_subtotal.getText());
                lbl_balance.setText(String.format(Locale.UK, "%.2f", bal));
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

    public Button getBtn_reserve() {
        return btn_reserve;
    }

    public Label getLbl_refno() {
        return lbl_refno;
    }

    public Label getLbl_total() {
        return lbl_total;
    }

    public Label getLbl_tax() {
        return lbl_tax;
    }

    public Label getLbl_deposit() {
        return lbl_deposit;
    }

    public Label getLbl_subtotal() {
        return lbl_subtotal;
    }

    public RadioButton getRb_deposit() {
        return rb_deposit;
    }

    public RadioButton getRb_full() {
        return rb_full;
    }

    public Label getLbl_paid() {
        return lbl_paid;
    }

    public Label getLbl_balance() {
        return lbl_balance;
    }

    public Label getLbl_switchbal() {
        return lbl_switchbal;
    }

    public ComboBox<Integer> getCbox_Month() {
        return cbox_Month;
    }

    public ComboBox<Integer> getCbox_Year() {
        return cbox_Year;
    }

    public ComboBox<String> getCbox_PayType() {
        return cbox_PayType;
    }

    public TextField getTf_cardname() {
        return tf_cardname;
    }

    public TextField getTf_cardno() {
        return tf_cardno;
    }

    public TextField getTf_cvccode() {
        return tf_cvccode;
    }
}
