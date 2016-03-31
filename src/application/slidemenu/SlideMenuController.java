package application.slidemenu;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SlideMenuController implements Initializable {

    @FXML
    private Button btn_Menu;
    @FXML
    private AnchorPane leftMenu;
    @FXML
    private StackPane mainContent;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Content();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SlideMenuAnimate();
    }

    private void Content() throws IOException{
        mainContent.getChildren().clear();
        mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/reservation/reservation.fxml")));
    }

    private void SlideMenuAnimate(){
        TranslateTransition openMenu = new TranslateTransition(new Duration(350), leftMenu);
        openMenu.setToX(0);
        TranslateTransition closeMenu = new TranslateTransition(new Duration(350), leftMenu);
        btn_Menu.setOnAction((ActionEvent evt)->{
            if (leftMenu.getTranslateX()!=0) {
                openMenu.play();
            } else {
                closeMenu.setToX(-(leftMenu.getWidth()));
                closeMenu.play();
            }
        });
    }


}
