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
import javafx.scene.layout.Pane;
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

    private Boolean addResvLoaded; //these bools are used to check whether a specific fxml is loaded or not
    private Boolean modResvLoaded;

    @Override
    public void initialize(URL url, ResourceBundle rb) { //initialise when load
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
        addResvLoaded = true; //set true initially since add reservation UI is the first to be loaded
        modResvLoaded = false; //false because it isn't loaded initially

        menu_ResvAdd.setOnAction((ActionEvent event) -> {
            if (!addResvLoaded) {
                mainContent.getChildren().clear();
                try {
                    mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/reservation" +
                            "/reservation.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //set to true because it is loaded, so when menu_ResvAdd is clicked again, nothing happens
            addResvLoaded = true;
            //set to false so that menu_ResvMod action can be toggled when clicked
            modResvLoaded = false;

            slideMenuCompact();
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
            slideMenuCompact();
        });
    }

    private void SlideMenuAnimate() {
        TranslateTransition openMenu = new TranslateTransition(new Duration(250), leftMenu);
        openMenu.setToX(0);
        TranslateTransition closeMenu = new TranslateTransition(new Duration(250), leftMenu);
        TranslateTransition closeMenuQuick = new TranslateTransition(new Duration(50), leftMenu);

        //clickPane will appear when menu slides in, its function is to capture clicks outside the menu from user and
        //slide the menu out
        Pane clickPane = new Pane();

        clickPane.setOpacity(0);
        //if you notice in slidemenu.fxml there is an AnchorPane behind mainContent used to make setting sizes easier
        AnchorPane.setTopAnchor(clickPane, 50.0);
        AnchorPane.setLeftAnchor(clickPane, 0.0);
        AnchorPane.setRightAnchor(clickPane, 0.0);
        AnchorPane.setBottomAnchor(clickPane, 0.0);

        btn_Menu.setOnAction((ActionEvent evt) -> {
            if (leftMenu.getTranslateX() != 0) {
                openMenu.play();
                addBlur(mainContent);
                mainContent.getChildren().add(1, clickPane); //add the clickPane!

                clickPane.setOnMouseClicked((MouseEvent me) -> {
                    closeMenuQuick.setToX(-(leftMenu.getWidth()));
                    closeMenuQuick.play();
                    removeBlur(mainContent);
                    mainContent.getChildren().remove(clickPane); //remove the clickPane when it's being clicked on!
                    btn_Menu.setSelected(false);
                });
            } else {
                closeMenu.setToX(-(leftMenu.getWidth()));
                closeMenu.play();
                removeBlur(mainContent);
                mainContent.getChildren().remove(clickPane);
            }
        });
    }

    private void slideMenuCompact(){
        if (leftMenu.getTranslateX() == 0) {
            TranslateTransition closeMenuQuick = new TranslateTransition(new Duration(100), leftMenu);
            closeMenuQuick.setToX(-(leftMenu.getWidth()));
            closeMenuQuick.play();
            removeBlur(mainContent);
        }
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
