package application.slidemenu;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
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
    @FXML
    private Button menu_CI;
    @FXML
    private Button menu_CO;
    @FXML
    private Button menu_ModRoom;
    @FXML
    private Button menu_ModFac;
    @FXML
    private Button menu_SRecord;
    @FXML
    private Button menu_Report;
    @FXML
    public TitledPane submenu_RNF;

    private Boolean addResvLoaded;
    private Boolean modResvLoaded;
    private Boolean CILoaded;
    private Boolean COLoaded;
    private Boolean modRoomLoaded;
    private Boolean modFacLoaded;
    private Boolean sRecLoaded;
    private Boolean ReportLoaded;

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
        mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/assets/reservation" +
                "/reservation.fxml")));
        addResvLoaded = true;
        modResvLoaded = false;
        CILoaded = false;
        COLoaded = false;
        modRoomLoaded = false;
        modFacLoaded = false;
        sRecLoaded = false;
        ReportLoaded = false;

        menu_ResvAdd.setOnAction((ActionEvent event) -> {
            if (!addResvLoaded) { //addResvLoaded == false
                mainContent.getChildren().clear();
                try {
                    mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/assets/reservation" +
                            "/reservation.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.print(mainContent.getChildren());
            }
            addResvLoaded = true;
            modResvLoaded = false;
            CILoaded = false;
            COLoaded = false;
            modRoomLoaded = false;
            modFacLoaded = false;
            sRecLoaded = false;
            ReportLoaded = false;

            slideMenuCompact();
            btn_Menu.setSelected(false);
        });

        menu_ResvMod.setOnAction((ActionEvent event) -> {
            if (!modResvLoaded) {
                mainContent.getChildren().clear();
                try {
                    mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/assets/" +
                            "reservation/resvedit.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            addResvLoaded = false;
            modResvLoaded = true;
            CILoaded = false;
            COLoaded = false;
            modRoomLoaded = false;
            modFacLoaded = false;
            sRecLoaded = false;
            ReportLoaded = false;

            slideMenuCompact();
            btn_Menu.setSelected(false);
        });

        menu_CI.setOnAction((ActionEvent event) -> {
            if (!CILoaded) {
                mainContent.getChildren().clear();
                try {
                    mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/assets/" +
                            "checkin/checkin.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            addResvLoaded = false;
            modResvLoaded = false;
            CILoaded = true;
            COLoaded = false;
            modRoomLoaded = false;
            modFacLoaded = false;
            sRecLoaded = false;
            ReportLoaded = false;

            slideMenuCompact();
            btn_Menu.setSelected(false);
        });

        menu_CO.setOnAction((ActionEvent event) -> {
            if (!COLoaded) {
                mainContent.getChildren().clear();
                try {
                    mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/assets/" +
                            "checkout/checkout.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            addResvLoaded = false;
            modResvLoaded = true;
            CILoaded = false;
            COLoaded = true;
            modRoomLoaded = false;
            modFacLoaded = false;
            sRecLoaded = false;
            ReportLoaded = false;

            slideMenuCompact();
            btn_Menu.setSelected(false);
        });

        menu_ModRoom.setOnAction((ActionEvent event) -> {
            if (!modRoomLoaded) {
                mainContent.getChildren().clear();
                try {
                    mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/assets/" +
                            "RnFManagement/modroom.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            addResvLoaded = false;
            modResvLoaded = true;
            CILoaded = false;
            COLoaded = false;
            modRoomLoaded = true;
            modFacLoaded = false;
            sRecLoaded = false;
            ReportLoaded = false;

            slideMenuCompact();
            btn_Menu.setSelected(false);
        });

        menu_ModFac.setOnAction((ActionEvent event) -> {
            if (!modFacLoaded) {
                mainContent.getChildren().clear();
                try {
                    mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/assets/" +
                            "RnFManagement/modfacility.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            addResvLoaded = false;
            modResvLoaded = true;
            CILoaded = false;
            COLoaded = false;
            modRoomLoaded = false;
            modFacLoaded = true;
            sRecLoaded = false;
            ReportLoaded = false;

            slideMenuCompact();
            btn_Menu.setSelected(false);
        });

        menu_SRecord.setOnAction((ActionEvent event) -> {
            if (!sRecLoaded) {
                mainContent.getChildren().clear();
                try {
                    mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/assets/" +
                            "records/records.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            addResvLoaded = false;
            modResvLoaded = true;
            CILoaded = false;
            COLoaded = false;
            modRoomLoaded = false;
            modFacLoaded = false;
            sRecLoaded = true;
            ReportLoaded = false;

            slideMenuCompact();
            btn_Menu.setSelected(false);
        });

        menu_Report.setOnAction((ActionEvent event) -> {
            if (!ReportLoaded) {
                mainContent.getChildren().clear();
                try {
                    mainContent.getChildren().add(FXMLLoader.load(getClass().getResource("/application/assets/" +
                            "reports/dailyreport.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            addResvLoaded = false;
            modResvLoaded = true;
            CILoaded = false;
            COLoaded = false;
            modRoomLoaded = false;
            modFacLoaded = false;
            sRecLoaded = false;
            ReportLoaded = true;

            slideMenuCompact();
            btn_Menu.setSelected(false);
        });
    }

    private void SlideMenuAnimate() {
        TranslateTransition openMenu = new TranslateTransition(new Duration(250), leftMenu);
        openMenu.setToX(0);
        TranslateTransition closeMenu = new TranslateTransition(new Duration(250), leftMenu);
        TranslateTransition closeMenuQuick = new TranslateTransition(new Duration(50), leftMenu);

        Pane clickPane = new Pane();

        clickPane.setOpacity(0);
        AnchorPane.setTopAnchor(clickPane, 50.0);
        AnchorPane.setLeftAnchor(clickPane, 0.0);
        AnchorPane.setRightAnchor(clickPane, 0.0);
        AnchorPane.setBottomAnchor(clickPane, 0.0);

        btn_Menu.setOnAction((ActionEvent evt) -> {
            if (leftMenu.getTranslateX() != 0) {
                openMenu.play();
                addBlur(mainContent);
                mainContent.getChildren().add(1, clickPane);

                clickPane.setOnMouseClicked((MouseEvent me) -> {
                    closeMenuQuick.setToX(-(leftMenu.getWidth()));
                    closeMenuQuick.play();
                    removeBlur(mainContent);
                    mainContent.getChildren().remove(clickPane);
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
            //mainContent.getChildren().remove(1);
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