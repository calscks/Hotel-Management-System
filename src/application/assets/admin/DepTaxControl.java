package application.assets.admin;

import application.DBConnection;
import application.Validation;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


public class DepTaxControl implements Initializable{
    @FXML private TextField tf_setdep;
    @FXML private TextField tf_settax;
    @FXML private Button btn_set;

    //language=SQLite
    String query = "";

    @Override
    public void initialize(URL url, ResourceBundle rb){
        preload();

        btn_set.disableProperty().bind(Bindings.isEmpty(tf_setdep.textProperty()).or(
                Bindings.isEmpty(tf_settax.textProperty())
        ));

        tf_setdep.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(4));
        tf_settax.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(2));

        setVal();
    }

    private void preload(){
        query = "SELECT * FROM variables";
        try {
            DBConnection db = new DBConnection("Data.sqlite");
            ResultSet rs = db.executeQuery(query);

            while (rs.next()){
                tf_setdep.setText(String.format(Locale.UK, "%.2f", rs.getFloat("deposit")));
                tf_settax.setText(String.valueOf(rs.getInt("taxrate")));
            }
            db.closeCon();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setVal(){
        btn_set.setOnMouseClicked(me->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Set Deposit Amount and Tax Rate");
            alert.setContentText("Set Deposit = " + tf_setdep.getText() + "" +
                    " and Tax Rate = " + tf_settax.getText() + "%?");

            Optional<ButtonType> select = alert.showAndWait();
            if (select.isPresent()) {
                if (select.get() == ButtonType.CANCEL) {
                    return;
                }
            } else {
                return;
            }

            query = "UPDATE variables SET deposit = " + Float.parseFloat(tf_setdep.getText())
            + ", taxrate = " + Integer.parseInt(tf_settax.getText());

            try {
                DBConnection db = new DBConnection("Data.sqlite");
                db.executeUpdate(query);
                db.closeCon();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Stage stage = (Stage) btn_set.getScene().getWindow();
            stage.close();

            preload();
        });
    }
}
