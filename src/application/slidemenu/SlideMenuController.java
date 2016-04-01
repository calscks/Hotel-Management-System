package application.slidemenu;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SlideMenuController implements Initializable {

    @FXML
    private ToggleButton btn_Menu;
    @FXML
    private AnchorPane leftMenu;
    @FXML
    private StackPane mainContent;
    @FXML
    private Button menu_ResvAdd;
    @FXML
    private Button menu_ResvMod;

    private Boolean addResvLoaded;
    private Boolean modResvLoaded;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Content();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SlideMenuAnimate();
    }

    private void Content() throws IOException {
        mainContent.getChildren().clear();
        mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/reservation" +
                "/reservation.fxml")));
        addResvLoaded = true;
        modResvLoaded = false;

        menu_ResvAdd.setOnAction((ActionEvent event) -> {
            if (!addResvLoaded) {
                mainContent.getChildren().clear();
                try {
                    mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/reservation" +
                            "/reservation.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.print(mainContent.getChildren());
            }
            addResvLoaded = true;
            modResvLoaded = false;
        });

        menu_ResvMod.setOnAction((ActionEvent event) -> {
            if (!modResvLoaded) {
                mainContent.getChildren().clear();
                try {
                    mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/reservation" +
                            "/resvedit.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            modResvLoaded = true;
            addResvLoaded = false;
        });
    }

    private void SlideMenuAnimate() {
        TranslateTransition openMenu = new TranslateTransition(new Duration(250), leftMenu);
        openMenu.setToX(0);
        TranslateTransition closeMenu = new TranslateTransition(new Duration(250), leftMenu);
        TranslateTransition closeMenuQuick = new TranslateTransition(new Duration(50), leftMenu);

        btn_Menu.setOnAction((ActionEvent evt) -> {
            if (leftMenu.getTranslateX() != 0) {
                openMenu.play();
                addBlur(mainContent);
                mainContent.setOnMouseClicked((MouseEvent me) -> {
                    closeMenuQuick.setToX(-(leftMenu.getWidth()));
                    closeMenuQuick.play();
                    removeBlur(mainContent);
                });
            } else {
                closeMenu.setToX(-(leftMenu.getWidth()));
                closeMenu.play();
                removeBlur(mainContent);
            }
        });
    }

    private static void removeBlur(Node node) {
        GaussianBlur blur = (GaussianBlur) node.getEffect();
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(blur.radiusProperty(), 0.0);
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(actionEvent -> node.setEffect(null));
        timeline.play();
    }

    private static void addBlur(Node node) {
        GaussianBlur blur = new GaussianBlur(0.0);
        node.setEffect(blur);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(blur.radiusProperty(), 15.0);
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}
