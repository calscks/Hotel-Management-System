package application.assets.reservation;

import application.assets.AutoCompleteCBoxListener;
import application.assets.ModelGroupMember;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import application.Validation;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
public class ResvController implements Initializable{

    @FXML private AnchorPane resvPane;
    @FXML private TextField tf_fname;
    @FXML private TextField tf_lname;
    @FXML private TextField tf_phoneno;
    @FXML private TextField tf_address;
    @FXML private TextField tf_postcode;
    @FXML private TextField tf_city;
    @FXML private TextField tf_idno;
    @FXML private ComboBox<String> cbox_country;
    @FXML private ComboBox<String> cbox_idtype;

    @FXML private Button btn_resvNext;
    @FXML private Button btn_addguest;
    @FXML private Button btn_delguest;
    @FXML private Button btn_addroom;
    @FXML private Button btn_delroom;
    @FXML private Button btn_addfac;
    @FXML private Button btn_delfac;

    @FXML private CheckBox chkbox_gmembers;

    @FXML private TableView<ModelGroupMember> table_gmembers;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memfname;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memlname;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memidno;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memroomno;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        validations();

        addGuest();

        addRoom();

        //store short form of countries in array
        String [] locale = Locale.getISOCountries();
        //loop the array
        for (String countryCode: locale){
            Locale country = new Locale("", countryCode);
            //System.out.println(country.getDisplayCountry());
            //add countries into cbox!
            cbox_country.getItems().add(country.getDisplayCountry());
        }

        new AutoCompleteCBoxListener<>(cbox_country);

        //bottom onwards are how I access back button from the payment controller (of payment fxml)
        FXMLLoader loadpayment = new FXMLLoader(getClass().getResource("/application/assets/" +
                "reservation/resvpay.fxml")); //create a fxmlLoader
        AnchorPane payment = null;
        try {
            payment = loadpayment.load(); //load it into an anchorPane
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane finalPayment = payment; //set as another anchorPane called finalPayment
        btn_resvNext.setOnMouseClicked(me->{
            FadeTransition ft = new FadeTransition(Duration.millis(320), finalPayment);
            ft.setFromValue(0.0); //add a simple fade in transition
            ft.setToValue(1.0);
            ft.play();
            resvPane.getChildren().add(finalPayment); //add as children of resvPane
        });

        //get the controller of the fxmlLoader (which is the payment controller)
        ResvPayController controller = loadpayment.getController();
        controller.getBtn_resvBack().setOnMouseClicked(me->{ //getter from the payment controller
            Timeline timeline = new Timeline(); //set fade out
            assert finalPayment != null;
            KeyFrame kf = new KeyFrame(Duration.millis(320), new KeyValue(finalPayment.opacityProperty(), 0));
            timeline.getKeyFrames().add(kf);
            //when the timeline is finished (finished fade out) then invoke remove the finalPayment
            timeline.setOnFinished(se-> resvPane.getChildren().removeAll(finalPayment));
            timeline.play();
        });
    }

    public void addGuest(){
        FXMLLoader loadGuest = new FXMLLoader(getClass().getResource("/application/assets" +
                "/reservation/resvaddgroup.fxml"));

        btn_addguest.setOnMouseClicked(me->{
            Stage guestStage = new Stage();
            StackPane guestPane = new StackPane();
            ScrollPane rootPane = new ScrollPane(guestPane);
            try {
                Parent root = loadGuest.load();
                guestPane.getChildren().addAll(root);
                Scene guestScene = new Scene(rootPane);
                guestStage.setScene(guestScene);
                //line below blocks all inputs to the stage behind
                guestStage.initModality(Modality.APPLICATION_MODAL);
                guestStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            guestStage.setResizable(false); //disable resize
            guestStage.setAlwaysOnTop(true); //set topmost
        });
    }

    public void addRoom(){
        FXMLLoader loadRoom = new FXMLLoader(getClass().getResource("/application/assets" +
                "/reservation/resvroom.fxml"));
        btn_addroom.setOnMouseClicked( me->{
            Stage roomStage = new Stage();
            StackPane roomPane = new StackPane();
            ScrollPane rootPane = new ScrollPane(roomPane);
            Parent root = null;
            try {
                root = loadRoom.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            roomPane.getChildren().addAll(root);
            Scene guestScene = new Scene(rootPane);
            roomStage.setScene(guestScene);
            roomStage.initModality(Modality.APPLICATION_MODAL);
            roomStage.show();
            roomStage.setResizable(false);
            roomStage.setAlwaysOnTop(true);
        });
    }

    private void validations(){
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_phoneno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(15));
        tf_address.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoCommaDot(50));
        tf_postcode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(12));
        tf_city.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }

}
