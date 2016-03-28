package application;

import application.assets.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main extends Application {
    @Override
    public void start(Stage P) throws Exception {
        if (!connStatus()){ //if connStatus() == false
            //do sth, haven't got the idea to do what first, but should be doing sql query to create db
        }
        P.setTitle("Hotel Management System");
        P.setScene(this.newScene());
        P.show();
    }

    private Boolean connStatus(){
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dsdswwData.sqlite");
            return true;
        } catch (Exception e) {
            Alert conAlert = new Alert(Alert.AlertType.ERROR);
            conAlert.setTitle("Error");
            conAlert.setHeaderText("Database Connection Problem");
            conAlert.setContentText(e.getClass().getName() + ": " + e.getMessage());
            conAlert.showAndWait();
            return false;
        }
    }

    private Scene newScene() {
        return new Scene(new Login().loginStage());
    }

    public static void main(String[] args) {
        launch(args);
    }//please don't care this main method
}

