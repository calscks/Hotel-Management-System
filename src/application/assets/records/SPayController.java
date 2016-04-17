package application.assets.records;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;

public class SPayController implements Initializable{
    @FXML private TableView<ModelPayment> table_payment;
    @FXML private TableColumn<ModelPayment, String> tbcol_custid;
    @FXML private TableColumn<ModelPayment, String> tbcol_fname;
    @FXML private TableColumn<ModelPayment, String> tbcol_lname;
    @FXML private TableColumn<ModelPayment, String> tbcol_dep;
    @FXML private TableColumn<ModelPayment, String> tbcol_subtotal;
    @FXML private TableColumn<ModelPayment, String> tbcol_paid;
    @FXML private TableColumn<ModelPayment, String> tbcol_bal;
    @FXML private TableColumn<ModelPayment, String> tbcol_resvno;
    @FXML private TableColumn<ModelPayment, String> tbcol_cioid;
    @FXML private TableColumn<ModelPayment, String> tbcol_refno;

    @FXML private ToggleGroup SearchGroup;
    @FXML private RadioButton rb_resvno;
    @FXML private RadioButton rb_lname;
    @FXML private RadioButton rb_fname;
    @FXML private RadioButton rb_cioid;
    @FXML private RadioButton rb_refno;
    @FXML private RadioButton rb_custid;

    @FXML private TextField tf_search;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        cellValueFactory();
        searchPayment();
    }

    private void searchPayment(){
        tf_search.setOnKeyReleased(event -> {
            //language=SQLite
            String query = "";
            table_payment.getItems().clear();
            if (rb_refno.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                query = "SELECT * FROM Payment p INNER JOIN Customer c ON p.CustID = " +
                        "c.CustID WHERE p.PaymentID LIKE '%" + tf_search.getText() +
                        "%'";

                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()){
                        ModelPayment mp = new ModelPayment();
                        mp.setBal(String.format(Locale.UK, "%.2f", rs.getFloat("Bal")));
                        mp.setCioid(String.valueOf(rs.getInt("CIO_ID")));
                        mp.setDep(String.format(Locale.UK, "%.2f", rs.getFloat("Deposit")));
                        mp.setFname(rs.getString("CustFName"));
                        mp.setLname(rs.getString("CustLName"));
                        mp.setPaid(String.format(Locale.UK, "%.2f", rs.getFloat("Paid")));
                        mp.setPaycustid(rs.getString("CustID"));
                        mp.setPayresvno(String.valueOf(rs.getInt("ResvNo")));
                        mp.setRefno(String.valueOf(rs.getInt("PaymentID")));
                        mp.setSubtotal(String.format(Locale.UK, "%.2f", rs.getFloat("Subtotal")));
                        table_payment.getItems().add(mp);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (rb_cioid.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                query = "SELECT * FROM Payment p INNER JOIN Customer c ON p.CustID = " +
                        "c.CustID WHERE p.CIO_ID LIKE '%" + tf_search.getText() +
                        "%'";

                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()){
                        ModelPayment mp = new ModelPayment();
                        mp.setBal(String.format(Locale.UK, "%.2f", rs.getFloat("Bal")));
                        mp.setCioid(String.valueOf(rs.getInt("CIO_ID")));
                        mp.setDep(String.format(Locale.UK, "%.2f", rs.getFloat("Deposit")));
                        mp.setFname(rs.getString("CustFName"));
                        mp.setLname(rs.getString("CustLName"));
                        mp.setPaid(String.format(Locale.UK, "%.2f", rs.getFloat("Paid")));
                        mp.setPaycustid(rs.getString("CustID"));
                        mp.setPayresvno(String.valueOf(rs.getInt("ResvNo")));
                        mp.setRefno(String.valueOf(rs.getInt("PaymentID")));
                        mp.setSubtotal(String.format(Locale.UK, "%.2f", rs.getFloat("Subtotal")));
                        table_payment.getItems().add(mp);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (rb_resvno.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                query = "SELECT * FROM Payment p INNER JOIN Customer c ON p.CustID = " +
                        "c.CustID WHERE p.ResvNo LIKE '%" + tf_search.getText() +
                        "%'";

                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()){
                        ModelPayment mp = new ModelPayment();
                        mp.setBal(String.format(Locale.UK, "%.2f", rs.getFloat("Bal")));
                        mp.setCioid(String.valueOf(rs.getInt("CIO_ID")));
                        mp.setDep(String.format(Locale.UK, "%.2f", rs.getFloat("Deposit")));
                        mp.setFname(rs.getString("CustFName"));
                        mp.setLname(rs.getString("CustLName"));
                        mp.setPaid(String.format(Locale.UK, "%.2f", rs.getFloat("Paid")));
                        mp.setPaycustid(rs.getString("CustID"));
                        mp.setPayresvno(String.valueOf(rs.getInt("ResvNo")));
                        mp.setRefno(String.valueOf(rs.getInt("PaymentID")));
                        mp.setSubtotal(String.format(Locale.UK, "%.2f", rs.getFloat("Subtotal")));
                        table_payment.getItems().add(mp);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (rb_fname.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                query = "SELECT * FROM Payment p INNER JOIN Customer c ON p.CustID = " +
                        "c.CustID WHERE c.CustFName LIKE '%" + tf_search.getText() +
                        "%'";

                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()){
                        ModelPayment mp = new ModelPayment();
                        mp.setBal(String.format(Locale.UK, "%.2f", rs.getFloat("Bal")));
                        mp.setCioid(String.valueOf(rs.getInt("CIO_ID")));
                        mp.setDep(String.format(Locale.UK, "%.2f", rs.getFloat("Deposit")));
                        mp.setFname(rs.getString("CustFName"));
                        mp.setLname(rs.getString("CustLName"));
                        mp.setPaid(String.format(Locale.UK, "%.2f", rs.getFloat("Paid")));
                        mp.setPaycustid(rs.getString("CustID"));
                        mp.setPayresvno(String.valueOf(rs.getInt("ResvNo")));
                        mp.setRefno(String.valueOf(rs.getInt("PaymentID")));
                        mp.setSubtotal(String.format(Locale.UK, "%.2f", rs.getFloat("Subtotal")));
                        table_payment.getItems().add(mp);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (rb_lname.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                query = "SELECT * FROM Payment p INNER JOIN Customer c ON p.CustID = " +
                        "c.CustID WHERE c.CustLName LIKE '%" + tf_search.getText() +
                        "%'";

                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()){
                        ModelPayment mp = new ModelPayment();
                        mp.setBal(String.format(Locale.UK, "%.2f", rs.getFloat("Bal")));
                        mp.setCioid(String.valueOf(rs.getInt("CIO_ID")));
                        mp.setDep(String.format(Locale.UK, "%.2f", rs.getFloat("Deposit")));
                        mp.setFname(rs.getString("CustFName"));
                        mp.setLname(rs.getString("CustLName"));
                        mp.setPaid(String.format(Locale.UK, "%.2f", rs.getFloat("Paid")));
                        mp.setPaycustid(rs.getString("CustID"));
                        mp.setPayresvno(String.valueOf(rs.getInt("ResvNo")));
                        mp.setRefno(String.valueOf(rs.getInt("PaymentID")));
                        mp.setSubtotal(String.format(Locale.UK, "%.2f", rs.getFloat("Subtotal")));
                        table_payment.getItems().add(mp);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (rb_custid.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                query = "SELECT * FROM Payment p INNER JOIN Customer c ON p.CustID = " +
                        "c.CustID WHERE c.CustID LIKE '%" + tf_search.getText() +
                        "%'";

                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()){
                        ModelPayment mp = new ModelPayment();
                        mp.setBal(String.format(Locale.UK, "%.2f", rs.getFloat("Bal")));
                        mp.setCioid(String.valueOf(rs.getInt("CIO_ID")));
                        mp.setDep(String.format(Locale.UK, "%.2f", rs.getFloat("Deposit")));
                        mp.setFname(rs.getString("CustFName"));
                        mp.setLname(rs.getString("CustLName"));
                        mp.setPaid(String.format(Locale.UK, "%.2f", rs.getFloat("Paid")));
                        mp.setPaycustid(rs.getString("CustID"));
                        mp.setPayresvno(String.valueOf(rs.getInt("ResvNo")));
                        mp.setRefno(String.valueOf(rs.getInt("PaymentID")));
                        mp.setSubtotal(String.format(Locale.UK, "%.2f", rs.getFloat("Subtotal")));
                        table_payment.getItems().add(mp);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void cellValueFactory(){
        //for payment table
        tbcol_bal.setCellValueFactory(new PropertyValueFactory<>("bal"));
        tbcol_cioid.setCellValueFactory(new PropertyValueFactory<>("cioid"));
        tbcol_custid.setCellValueFactory(new PropertyValueFactory<>("paycustid"));
        tbcol_dep.setCellValueFactory(new PropertyValueFactory<>("dep"));
        tbcol_fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        tbcol_lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        tbcol_paid.setCellValueFactory(new PropertyValueFactory<>("paid"));
        tbcol_refno.setCellValueFactory(new PropertyValueFactory<>("refno"));
        tbcol_resvno.setCellValueFactory(new PropertyValueFactory<>("payresvno"));
        tbcol_subtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
    }
}
