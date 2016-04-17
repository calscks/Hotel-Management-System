package application;

import application.assets.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage P) throws Exception {
        new Login();
    }

    public static void main(String[] args) {
        launch(args);
    }//please don't care this main method
}
