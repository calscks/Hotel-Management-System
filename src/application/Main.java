package application;

import application.assets.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage P) throws Exception{
        P.setTitle("Hotel Management System");
        P.setScene(this.newScene());
        P.show();
    }

    private Scene newScene(){
        return new Scene(new Login().loginStage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

