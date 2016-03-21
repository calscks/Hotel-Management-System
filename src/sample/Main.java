package sample;

//I tried using scene builder and fxml to design the login form.

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("R_guestinfo.fxml"));
        primaryStage.setTitle("Hotel Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
//blahhhh

    public static void main(String[] args) {
        launch(args);
    }
}
