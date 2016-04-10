package application;

import application.assets.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage P) throws Exception {
        P.setTitle("Drizzle Hotel Management System");
        P.setScene(this.newScene());
        P.show();
    }

    private Scene newScene() throws SQLException {
        return new Scene(new Login().loginGrid());
        /**interpreted as:
         * Scene x = new Scene(new Login().loginGrid());
         * return x;
         */
    }

    public static void main(String[] args) {
        launch(args);
    }//please don't care this main method
}
