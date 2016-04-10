package application.assets.reservation;

import application.assets.AutoCompleteCBoxListener;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import application.Validation;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
public class ResvController implements Initializable{
    /*private int year1 = Calendar.getInstance().get(Calendar.YEAR);
    private int year2 = Calendar.getInstance().get(Calendar.YEAR) + 1;
    private int year3 = Calendar.getInstance().get(Calendar.YEAR) + 2;
    private int year4 = Calendar.getInstance().get(Calendar.YEAR) + 3;
    private int year5 = Calendar.getInstance().get(Calendar.YEAR) + 4;
    private int year6 = Calendar.getInstance().get(Calendar.YEAR) + 5;
    private int year7 = Calendar.getInstance().get(Calendar.YEAR) + 6;

    private ObservableList<String> gtitle = FXCollections.observableArrayList("Mr.", "Mrs.");
    private ObservableList<String> roomtype = FXCollections.observableArrayList("Commercial", "Suite", "President");
    private ObservableList<String> paymenttype = FXCollections.observableArrayList("Credit Card", "Cash");
    private ObservableList<Integer> month = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    private ObservableList<Integer> year = FXCollections.observableArrayList(year1, year2, year3, year4, year5, year6, year7);
*/
    @FXML
    private AnchorPane resvPane;
    @FXML
    private TextField tf_fname;
    @FXML
    private TextField tf_lname;
    @FXML
    private TextField tf_phoneno;
    @FXML
    private TextField tf_address;
    @FXML
    private TextField tf_postcode;
    @FXML
    private TextField tf_city;
    @FXML
    private TextField tf_idtype;
    @FXML
    private TextField tf_idno;
    @FXML
    private ComboBox<String> cbox_country;

    @FXML
    private Button btn_resvNext;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validations();
        String [] locale = Locale.getISOCountries();
        //ObservableList<String> cList = FXCollections.observableArrayList();
        for (String countryCode: locale){
            Locale country = new Locale("", countryCode);
            //System.out.println(country.getDisplayCountry()); //for test if can print out or not
            cbox_country.getItems().add(country.getDisplayCountry());
        }
        new AutoCompleteCBoxListener<>(cbox_country);

        //bottom onwards are how I access back button from the payment controller (of payment fxml)
        FXMLLoader loadpayment = new FXMLLoader(getClass().getResource("/application/assets/" +
                "reservation/resvpay.fxml")); //create a fxmlloader
        AnchorPane payment = null;
        try {
            payment = loadpayment.load(); //load it
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane finalPayment = payment; //set as another anchorpane called finalpayment
        btn_resvNext.setOnMouseClicked(me->{
            FadeTransition ft = new FadeTransition(Duration.millis(320), finalPayment);
            ft.setFromValue(0.0); //add a simple fade in transition
            ft.setToValue(1.0);
            ft.play();
            resvPane.getChildren().add(finalPayment);
        });

        //get the controller of the fxmlloader (which is the payment controller)
        ResvPayController controller = loadpayment.getController();
        controller.getBtn_resvBack().setOnMouseClicked(me->{
            Timeline timeline = new Timeline(); //set fade out
            KeyFrame kf = new KeyFrame(Duration.millis(320), new KeyValue(finalPayment.opacityProperty(), 0));
            timeline.getKeyFrames().add(kf);
            //when the timeline is finished (finished fade out) then invoke remove the finalpayment
            timeline.setOnFinished(se-> resvPane.getChildren().removeAll(finalPayment));
            timeline.play();
        });
    }

    public void validations(){
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_phoneno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(15));
        tf_address.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoCommaDot(50));
        tf_postcode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(12));
        tf_city.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_idtype.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(10));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }

}
