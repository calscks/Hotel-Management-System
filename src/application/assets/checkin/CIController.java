package application.assets.checkin;

import application.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import application.DBConnection.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CIController {

   @FXML
    private Button btn_ciNext;
    @FXML
    private TextField tf_ciResvNum;
    Connection c = DBConnection.getCon("Data.sqlite");
    Statement statement = null;
    private void initialize(){
        tf_ciResvNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String sql =""
            }
        });

    }
public static void main(String[] args){
}



}
