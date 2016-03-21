package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML private Label lbltest;

    @FXML private void btnLoginAct(ActionEvent event){

        lbltest.setText("Login button clicked");
        lbltest.setVisible(true);

    }
}
