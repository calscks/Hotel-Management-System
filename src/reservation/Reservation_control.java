package reservation;


import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.Calendar;


public class Reservation_control {

    private int year1 = Calendar.getInstance().get(Calendar.YEAR);
    private int year2 = Calendar.getInstance().get(Calendar.YEAR) + 1;
    private int year3 = Calendar.getInstance().get(Calendar.YEAR) + 2;
    private int year4 = Calendar.getInstance().get(Calendar.YEAR) + 3;
    private int year5 = Calendar.getInstance().get(Calendar.YEAR) + 4;
    private int year6 = Calendar.getInstance().get(Calendar.YEAR) + 5;
    private int year7 = Calendar.getInstance().get(Calendar.YEAR) + 6;

    ObservableList<String> gtitle = FXCollections.observableArrayList("Mr.", "Mrs.");
    ObservableList<String> roomtype = FXCollections.observableArrayList("Commercial","Suite","President");
    ObservableList<String> paymenttype = FXCollections.observableArrayList("Credit Card","Cash");
    ObservableList<Integer> month = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);
    ObservableList<Integer> year = FXCollections.observableArrayList(year1,year2,year3,year4,year5,year6,year7);

    @FXML private ComboBox titlecombo;
    @FXML private ComboBox sidcombo;
    @FXML private Button nextbtn;
    @FXML private ComboBox roomtypecombo;
    @FXML private ComboBox ptypecombo;
    @FXML private ComboBox monthcombo;
    @FXML private ComboBox yearcombo;


    @FXML
    private void initialize() {
    titlecombo.setItems(gtitle);
    roomtypecombo.setItems(roomtype);
    ptypecombo.setItems(paymenttype);
    monthcombo.setItems(month);
    yearcombo.setItems(year);
    }

    @FXML private void nextbtn(ActionEvent next) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("R_roominfo.fxml"));
        Scene scene = new Scene(root);

    }
}
