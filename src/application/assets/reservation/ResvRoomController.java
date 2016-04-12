package application.assets.reservation;

import application.DBConnection;
import application.assets.CIODateDisabler;
import application.assets.ModelRoom;
import application.assets.admin.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.lang.model.type.NullType;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
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
    private TableColumn<ModelRoom, String> tbcol_roomprice;
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
    @FXML
    private Label lbl_extBedPrice;
    @FXML
    private Label lbl_totalRoomPrice;


    DBConnection db = new DBConnection("Data.sqlite");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcol_roomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
        tbcol_roomcat.setCellValueFactory(new PropertyValueFactory<>("roomcat"));
        tbcol_roomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
        tbcol_roomprice.setCellValueFactory(new PropertyValueFactory<>("roomprice"));

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

        //I created CIODateDisabler.java for check in and out! You can apply it like this.
        new CIODateDisabler(date_ci, date_co);

        //for search button
        btn_roomsearch.setOnMouseClicked(me -> {
            ObservableList<ModelRoom> data = FXCollections.observableArrayList();
            String roomCat = cbox_roomcat.getSelectionModel().getSelectedItem();
            String roomType = cbox_roomtype.getSelectionModel().getSelectedItem();
            String ciDate = date_ci.getValue().toString();

            table_rooms.getItems().clear(); //prevent keep on adding
            tf_roomno.clear(); //clear the field
            cbox_xtrabed.getItems().clear();

            //this query so hard
            //this query means to select a table with another table joined (on the 1st table's room type id column =
            //2nd table's room type id column, with the condition of the room no does not present inside
            // RoomBooking which the selected checkin date clashes with other cio dates of the
            // particular room no. This query also matches the chosen type catergory and room type.
            String query = "SELECT r.RoomNo, rt.TypeGroup, rt.TypeName, rt.RoomPrice, " +
                    "rt.Rate_extTwin, rt.Rate_extFull, rt.Rate_extQueen, rt.Rate_extKing " +
                    "FROM Room r " +
                    "INNER JOIN RoomType rt ON r.RoomTypeID = rt.TypeID " +
                    "WHERE (r.RoomNo NOT IN (SELECT RoomNo FROM RoomBooking WHERE " +
                    "date('" + ciDate + "') BETWEEN date(Date_CI) AND date(Date_CO))) AND " +
                    "rt.TypeGroup = '" + roomCat + "' AND rt.TypeName = '" + roomType + "'";
            try {
                ResultSet rs = db.executeQuery(query);
                while (rs.next()) {
                    ModelRoom mdRoom = new ModelRoom();
                    String rPrice = String.format(Locale.UK, "%.2f", rs.getFloat("RoomPrice"));
                    mdRoom.setRoomcat(rs.getString("TypeGroup"));
                    mdRoom.setRtype(rs.getString("TypeName"));
                    mdRoom.setRoomno(rs.getString("RoomNo"));
                    mdRoom.setRoomprice(rPrice);
                    data.add(mdRoom);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            table_rooms.setItems(data);

        });

        //for row double clicking
        table_rooms.setRowFactory(tv->{
            TableRow<ModelRoom> selRow = new TableRow<>();
            selRow.setOnMouseClicked(me -> {
                if (me.getClickCount() == 2 && (!selRow.isEmpty())) {
                    ModelRoom room = new ModelRoom();
                    room = table_rooms.getSelectionModel().getSelectedItem();
                    tf_roomno.clear();
                    cbox_xtrabed.getItems().clear();
                    tf_roomno.setText(room.getRoomno());

                    String query = "SELECT r.RoomNo, rt.TypeGroup, rt.TypeName, rt.RoomPrice, " +
                            "rt.Rate_extTwin, rt.Rate_extFull, rt.Rate_extQueen, rt.Rate_extKing " +
                            "FROM Room r " +
                            "INNER JOIN RoomType rt ON r.RoomTypeID = rt.TypeID " +
                            "WHERE r.RoomNo = '" + room.getRoomno() + "'";

                    try {
                        ResultSet rs = db.executeQuery(query);
                        if (rs.next()){
                            //i re-set the resultset rs with rs.getFloat(sth)!
                            //if it get a null value from certain column specified,
                            //the rs will be null.
                            Float twin = rs.getFloat("Rate_extTwin");
                            //so after re-setting, and if rs right now is not null,
                            if (!rs.wasNull()){
                                cbox_xtrabed.getItems().add("Twin Bed");
                            }

                            Float full = rs.getFloat("Rate_extFull");
                            if (!rs.wasNull()){
                                cbox_xtrabed.getItems().add("Full bed");
                            }

                            Float queen = rs.getFloat("Rate_extQueen");
                            if (!rs.wasNull()){
                                cbox_xtrabed.getItems().add("Queen Bed");
                            }

                            Float king = rs.getFloat("Rate_extKing");
                            if (!rs.wasNull()){
                                cbox_xtrabed.getItems().add("King Bed");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });
            return selRow;
        });

    }
}
