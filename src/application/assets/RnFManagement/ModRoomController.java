package application.assets.RnFManagement;

import application.DBConnection;
import application.Validation;
import application.assets.ForAddButton;
import application.assets.ModelRoom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by User on 24/3/2016.
 */
public class ModRoomController implements Initializable{

    ObservableList<String> selectbyitems = FXCollections.observableArrayList("RoomCategory","RoomNo");

    @FXML private TextField tf_searchby;
    @FXML private Label lbl_cboxselection;
    @FXML private ComboBox cbox_searchby;
    @FXML private TextField tf_roomcategory;
    @FXML private TextField tf_roomno2;
    @FXML private TextField tf_roomtype;
    @FXML private TextField tf_paxperroom;
    @FXML private TextField tf_roomprice;
    @FXML private TextField tf_twinbedprice;
    @FXML private TextField tf_fullbedprice;
    @FXML private TextField tf_queenbedprice;
    @FXML private TextField tf_kingbedprice;
    @FXML private Button btn_modaddroom;
    @FXML private TableView<ModelRoom> tv_modroom;
    @FXML private TableColumn<ModelRoom, String> tc_modroomno;
    @FXML private TableColumn<ModelRoom, String> tc_modroomtype;

    @FXML private void lblchange(){
        if (cbox_searchby.getValue().equals("RoomCategory")){
            lbl_cboxselection.setText("Room Category :");
        }
        else{
            lbl_cboxselection.setText("Room No :");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validation();

        cbox_searchby.setItems(selectbyitems);

        addroom();

        DBConnection c = new DBConnection("Data.sqlite");

        tf_searchby.textProperty().addListener((observable, oldValue, newValue) -> {
            if (lbl_cboxselection.getText().equals("Room Category :")){
                try {
                    //language=SQLite
                    String sql = "SELECT * FROM Room rm " +
                            "INNER JOIN RoomType rt on rm.RoomTypeID = rt.TypeID " +
                            "WHERE rt.TypeGroup ='" + tf_searchby.getText()  + "'";

                    ResultSet data = c.executeQuery(sql);
                    ObservableList<ModelRoom> rtable = FXCollections.observableArrayList();

                    String modroomcategory = data.getString("TypeGroup");
                    String modroomno = data.getString("RoomNo");
                    String modroomtype = data.getString("TypeName");
                    String modroompax = data.getString("MaxPax");
                    String modroomprice = data.getString("RoomPrice");
                    String modroomtwinprice = data.getString("Rate_extTwin");
                    String modroomfullprice = data.getString("Rate_extFull");
                    String modroomqueenprice = data.getString("Rate_extQueen");
                    String modroomkingprice = data.getString("Rate_extKing");

                    tf_roomcategory.setText(modroomcategory);
                    tf_roomno2.setText(modroomno);
                    tf_roomtype.setText(modroomtype);
                    tf_paxperroom.setText(modroompax);
                    tf_roomprice.setText(modroomprice);
                    tf_twinbedprice.setText(modroomtwinprice);
                    tf_fullbedprice.setText(modroomfullprice);
                    tf_queenbedprice.setText(modroomqueenprice);
                    tf_kingbedprice.setText(modroomkingprice);
                    while(data.next()){
                        ModelRoom rm = new ModelRoom();
                        rm.setRoomno(data.getString("RoomNo"));
                        rm.setRtype(data.getString("TypeGroup"));
                        rtable.add(rm);
                    }
                    tc_modroomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
                    tc_modroomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
                    tv_modroom.setItems(rtable);
                }
                catch (SQLException e){
                    e.printStackTrace();
                }

            }
            else if (lbl_cboxselection.getText().equals("Room No :")){
                try {
                    //language=SQLite
                    String sql = "SELECT * FROM Room rm " +
                            "INNER JOIN RoomType rt on rm.RoomTypeID = rt.TypeID " +
                            "WHERE rm.RoomNo ='" + tf_searchby.getText()  + "'";

                    ResultSet data2 = c.executeQuery(sql);
                    ObservableList<ModelRoom> rtable = FXCollections.observableArrayList();

                    String modroomcategory = data2.getString("TypeGroup");
                    String modroomno = data2.getString("RoomNo");
                    String modroomtype = data2.getString("TypeName");
                    String modroompax = data2.getString("MaxPax");
                    String modroomprice = data2.getString("RoomPrice");
                    String modroomtwinprice = data2.getString("Rate_extTwin");
                    String modroomfullprice = data2.getString("Rate_extFull");
                    String modroomqueenprice = data2.getString("Rate_extQueen");
                    String modroomkingprice = data2.getString("Rate_extKing");

                    tf_roomcategory.setText(modroomcategory);
                    tf_roomno2.setText(modroomno);
                    tf_roomtype.setText(modroomtype);
                    tf_paxperroom.setText(modroompax);
                    tf_roomprice.setText(modroomprice);
                    tf_twinbedprice.setText(modroomtwinprice);
                    tf_fullbedprice.setText(modroomfullprice);
                    tf_queenbedprice.setText(modroomqueenprice);
                    tf_kingbedprice.setText(modroomkingprice);
                    while(data2.next()){
                        ModelRoom rm = new ModelRoom();
                        rm.setRoomno(data2.getString("roomno"));
                        rm.setRtype(data2.getString("typegroup"));
                        rtable.add(rm);
                    }
                    tc_modroomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
                    tc_modroomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
                    tv_modroom.setItems(rtable);
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }

        });
    }

    private void addroom() {
        FXMLLoader loadroom = new FXMLLoader(getClass().getResource("/application/assets/RnFManagement/addroom.fxml"));
        AnchorPane roomPane = new AnchorPane();
        try {
            roomPane = loadroom.load();
        }catch (IOException e){
            e.printStackTrace();
        }

        AnchorPane finalroompane = roomPane;

        new ForAddButton(finalroompane, btn_modaddroom);

        AddRoomController arc = loadroom.getController();

        arc.getbtn_addroom().setOnMouseClicked(me ->{
            ModelRoom room = new ModelRoom();
            ObservableList<ModelRoom> rd = FXCollections.observableArrayList();
            room.setRoomcat(arc.getRoomCat());
            room.setRoomno(arc.getRoomNo());

            rd.add(room);
            tv_modroom.getItems().add(room);

            //clear items in modaddroom
            arc.getTf_roomcategory().setText(null);
            arc.getTf_roomno().setText(null);
            arc.getTf_roomtype().setText(null);
            arc.getTf_paxperroom().setText(null);
            arc.getTf_roomprice().setText(null);
            arc.getCbox_extrabed().getItems().clear();
            arc.getTf_twinbedprice().setText(null);
            arc.getTf_fullbedprice().setText(null);
            arc.getTf_queenbedprice().setText(null);
            arc.getTf_kingbedprice().setText(null);

            Stage stage = (Stage) arc.getbtn_addroom().getScene().getWindow();
        });
    }

    private void validation() {
        tf_searchby.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_roomcategory.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(10));
        tf_roomno2.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_roomtype.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(10));
        tf_paxperroom.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_roomprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_twinbedprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_fullbedprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_queenbedprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_kingbedprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
    }
}
