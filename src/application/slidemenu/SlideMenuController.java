package application.slidemenu;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SlideMenuController implements Initializable {

    @FXML
    private Button btn_Menu;
    @FXML
    private AnchorPane leftMenu;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SlideMenuAnimate();
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
