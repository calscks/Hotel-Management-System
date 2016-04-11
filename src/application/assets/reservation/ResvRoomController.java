package application.assets.reservation;

import application.DBConnection;
import application.assets.ModelRoom;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ResvRoomController implements Initializable{
    @FXML private TableView<ModelRoom> table_rooms;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomno;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomcat;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomtype;
    @FXML private ComboBox<String> cbox_roomcat;
    @FXML private ComboBox<String> cbox_roomtype;
    @FXML private DatePicker date_ci;
    @FXML private DatePicker date_co;

    DBConnection db = new DBConnection("Data.sqlite");

    @Override
    public void initialize(URL url, ResourceBundle rb){
        tbcol_roomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
        tbcol_roomcat.setCellValueFactory(new PropertyValueFactory<>("roomcat"));
        tbcol_roomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));

        ObservableList<String> roomCategory = FXCollections.observableArrayList();
        try {
            ResultSet rs = db.executeQuery("SELECT TypeGroup FROM RoomType GROUP BY TypeGroup;");
            while (rs.next()){
                roomCategory.add(rs.getString("TypeGroup"));
            }
            cbox_roomcat.setItems(roomCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
