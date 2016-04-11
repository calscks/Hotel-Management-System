package application.assets.reservation;

import application.DBConnection;
import application.assets.ModelRoom;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class ResvRoomController implements Initializable {
    @FXML
    private TableView<ModelRoom> table_rooms;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomno;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomcat;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomtype;
    @FXML
    private ComboBox<String> cbox_roomcat;
    @FXML
    private ComboBox<String> cbox_roomtype;
    @FXML
    private ComboBox<String> cbox_xtrabed;
    @FXML
    private DatePicker date_ci;
    @FXML
    private DatePicker date_co;
    @FXML
    private Button btn_roomAdd;
    @FXML
    private Button btn_roomsearch;
    @FXML
    private TextField tf_roomno;


    DBConnection db = new DBConnection("Data.sqlite");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcol_roomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
        tbcol_roomcat.setCellValueFactory(new PropertyValueFactory<>("roomcat"));
        tbcol_roomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));

        //for room category combobox
        ObservableList<String> roomCategory = FXCollections.observableArrayList();
        try {
            ResultSet rs = db.executeQuery("SELECT TypeGroup FROM RoomType GROUP BY TypeGroup ORDER BY TypeGroup;");

            while (rs.next()) {
                roomCategory.add(rs.getString("TypeGroup"));
            }
            cbox_roomcat.setItems(roomCategory);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //for room type combobox
        //adding listener to selectedItemProperty is a must, so that listener can detect the selection
        //changes, hence can adjust the values inside room type combobox!
        cbox_roomcat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<String> roomType = FXCollections.observableArrayList();
            if (!cbox_roomcat.getItems().isEmpty()) {
                try {
                    cbox_roomtype.getItems().clear();
                    ResultSet rs = db.executeQuery("SELECT TypeName FROM RoomType WHERE TypeGroup = '" +
                            cbox_roomcat.getSelectionModel().getSelectedItem() + "';");
                    while (rs.next()) {
                        roomType.add(rs.getString("TypeName"));
                    }
                    cbox_roomtype.setItems(roomType);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //for check in datePicker
        date_ci.setValue(LocalDate.now());
        //obtained from oracle doc on how to disable date on datePicker, customised for my needs
        final Callback<DatePicker, DateCell> disableCiDate = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #c46067;");
                        }
                    }
                };
            }
        };
        //set the above dayCellFactory into date_ci datePicker
        date_ci.setDayCellFactory(disableCiDate);

        //for check out datePicker, disable dates before check in date
        final Callback<DatePicker, DateCell> disableCoDate = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(date_ci.getValue().plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ff9b83;");
                        }
                        long dayCount = ChronoUnit.DAYS.between(
                                date_ci.getValue(), item
                        );
                        setTooltip(new Tooltip(
                                "Staying for " + dayCount + " days")
                        );
                    }
                };
            }
        };
        date_co.setDayCellFactory(disableCoDate);


    }

}
