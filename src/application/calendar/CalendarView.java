package application.calendar;

//this is the second calender view that opens in a new stage but I still haven't figure out
//how to control it.

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class CalendarView{
    public void CalenderView(){
        try {
            Stage calstage = new Stage();
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 400, 400);
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
            Node popupContent = datePickerSkin.getPopupContent();

            root.setCenter(popupContent);

            calstage.setScene(scene);
            calstage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
