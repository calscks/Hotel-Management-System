package application.assets.checkin;

import application.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import application.DBConnection.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CIController implements Initializable{

    @FXML
    private Button btn_ciNext;
    @FXML
    private TextField tf_ciResvNum;
    @FXML
    private TextField tf_ciFirstName;
    @FXML
    private Button button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection c = new DBConnection("Data.sqlite");

        button.setOnAction(e-> {
            try {
                String sql ="select * from Reservation";
                ResultSet data = c.executeQuery(sql);
                data.next();
                String firstname = data.getString("CustID");
                tf_ciFirstName.setText(firstname);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
}



