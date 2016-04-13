package application.assets.reservation;

import application.DBConnection;
import application.assets.CIODateDisabler;
import application.assets.ModelRoom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;
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
    @FXML
    private Label lbl_roomPrice;


    DBConnection db = new DBConnection("Data.sqlite");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcol_roomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
        tbcol_roomcat.setCellValueFactory(new PropertyValueFactory<>("roomcat"));
        tbcol_roomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
        tbcol_roomprice.setCellValueFactory(new PropertyValueFactory<>("roomprice"));

        lbl_roomPrice.setText(null);
        lbl_extBedPrice.setText(null);
        lbl_totalRoomPrice.setText(null);

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
        }); //room category selection done

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
            lbl_extBedPrice.setText(null);
            lbl_totalRoomPrice.setText(null);

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

        }); //room search done

        //for row double clicking
        table_rooms.setRowFactory(tv->{
            TableRow<ModelRoom> selRow = new TableRow<>();
            selRow.setOnMouseClicked(me -> {
                if (me.getClickCount() == 2 && (!selRow.isEmpty())) {
                    ModelRoom room = new ModelRoom();
                    room = table_rooms.getSelectionModel().getSelectedItem();

                    tf_roomno.clear();
                    cbox_xtrabed.getItems().clear();


                    String query = "SELECT r.RoomNo, rt.TypeGroup, rt.TypeName, rt.RoomPrice, " +
                            "rt.Rate_extTwin, rt.Rate_extFull, rt.Rate_extQueen, rt.Rate_extKing " +
                            "FROM Room r " +
                            "INNER JOIN RoomType rt ON r.RoomTypeID = rt.TypeID " +
                            "WHERE r.RoomNo = '" + room.getRoomno() + "'";

                    //this for adding extra bed types into extra bed cbox
                    try {
                        ResultSet rs = db.executeQuery(query);
                        if (rs.next()){
                            //i re-set the resultSet rs with rs.getFloat(sth)! need to do like this
                            //if it gets a null value from certain column specified,
                            //the rs will be null.
                            Float twin = rs.getFloat("Rate_extTwin");
                            //so after re-setting, and see if rs right now is not null,
                            if (!rs.wasNull()){
                                cbox_xtrabed.getItems().add("Twin Bed");
                            }

                            Float full = rs.getFloat("Rate_extFull");
                            if (!rs.wasNull()){
                                cbox_xtrabed.getItems().add("Full Bed");
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

                    tf_roomno.setText(room.getRoomno());
                    lbl_roomPrice.setText(room.getRoomprice());

                    //set the total price, need to consider booking duration!
                    //bottom line means        condition
                    Float total = lbl_roomPrice.getText() != null ?
                            //            value if true                val if false
                            Float.parseFloat(lbl_roomPrice.getText()) : null;

                    //set date1 to value of chosen check in date (toString() converts to yyyy-mm-dd)
                    LocalDate date1 = LocalDate.parse(date_ci.getValue().toString());
                    //date 2 to value of chosen checkout date
                    LocalDate date2 = LocalDate.parse(date_co.getValue().toString());
                    //compare day difference between date1 and 2
                    Long dur = ChronoUnit.DAYS.between(date1, date2);
                    //convert long dur to integer
                    Integer duration = dur.intValue();
                    //total = total x duration
                    total *= duration;
                    //set total price with 2 d.p.
                    lbl_totalRoomPrice.setText(String.format(Locale.UK, "%.2f", total));

                }
            });
            return selRow;
        }); //double click row done

        //need to set extra bed price, means need a change listener! At the same time, total price
        cbox_xtrabed.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String roomNo = tf_roomno.getText();
            String query = "SELECT r.RoomNo, rt.Rate_extTwin, rt.Rate_extFull, " +
                    "rt.Rate_extQueen, rt.Rate_extKing FROM Room r " +
                    "INNER JOIN RoomType rt ON r.RoomTypeID = rt.TypeID " +
                    "WHERE r.RoomNo = '" + roomNo + "'";

            //if selected twin bed
            if (Objects.equals(cbox_xtrabed.getSelectionModel().getSelectedItem(), "Twin Bed")){
                try {
                    ResultSet rs = db.executeQuery(query);
                    lbl_extBedPrice.setText(String.format(Locale.UK, "%.2f", rs.getFloat("Rate_extTwin")));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            //same but different type, if selected full bed. Just copy pasta and edit a bit for the rest
            if (Objects.equals(cbox_xtrabed.getSelectionModel().getSelectedItem(), "Full Bed")){
                try {
                    ResultSet rs = db.executeQuery(query);
                    lbl_extBedPrice.setText(String.format(Locale.UK, "%.2f", rs.getFloat("Rate_extFull")));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (Objects.equals(cbox_xtrabed.getSelectionModel().getSelectedItem(), "Queen Bed")){
                try {
                    ResultSet rs = db.executeQuery(query);
                    lbl_extBedPrice.setText(String.format(Locale.UK, "%.2f", rs.getFloat("Rate_extQueen")));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (Objects.equals(cbox_xtrabed.getSelectionModel().getSelectedItem(), "King Bed")){
                try {
                    ResultSet rs = db.executeQuery(query);
                    lbl_extBedPrice.setText(String.format(Locale.UK, "%.2f", rs.getFloat("Rate_extKing")));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            //have to redo the price again to prevent keep on adding while changing different extbed type
            Float extPrice = Float.parseFloat(lbl_extBedPrice.getText());
            Float total = Float.parseFloat(lbl_roomPrice.getText());

            LocalDate date1 = LocalDate.parse(date_ci.getValue().toString());
            LocalDate date2 = LocalDate.parse(date_co.getValue().toString());
            Long dur = ChronoUnit.DAYS.between(date1, date2);
            Integer duration = dur.intValue();
            total *= duration;
            lbl_totalRoomPrice.setText(String.format(Locale.UK, "%.2f", total));

            total += extPrice;

            lbl_totalRoomPrice.setText(String.format(Locale.UK, "%.2f", total));

        }); //set xtra bed price and total price done
    }



    //button add (let resvController accesses what it will do), below are all the getter methods button add will use
    //from the resvController
    public Button getBtn_roomAdd() {
        return btn_roomAdd;
    }

    public String getRoomCat(){
        return cbox_roomcat.getSelectionModel().getSelectedItem();
    }

    public String getRoomType(){
        return cbox_roomtype.getSelectionModel().getSelectedItem();
    }

    public String getRoomNo(){
        return tf_roomno.getText();
    }

    public String getCI(){
        return date_ci.getValue().toString();
    }

    public String getCO(){
        return date_co.getValue().toString();
    }

    public String getExtBed(){
        return cbox_xtrabed.getSelectionModel().getSelectedItem();
    }

    public String getTotal(){
        return lbl_totalRoomPrice.getText();
    }

    //i guess i need these to clear the values after adding
    public ComboBox<String> getCbox_xtrabed() {
        return cbox_xtrabed;
    }

    public Label getLbl_extBedPrice() {
        return lbl_extBedPrice;
    }

    public Label getLbl_roomPrice() {
        return lbl_roomPrice;
    }

    public Label getLbl_totalRoomPrice() {
        return lbl_totalRoomPrice;
    }

    public TextField getTf_roomno() {
        return tf_roomno;
    }

    public TableView<ModelRoom> getTable_rooms() {
        return table_rooms;
    }
}
