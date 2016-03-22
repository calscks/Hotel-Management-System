package application;

import application.assets.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage P) throws Exception{
        new Login().loginStage();
        P.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

