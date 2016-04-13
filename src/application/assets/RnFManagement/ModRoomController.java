package application.assets.RnFManagement;

import application.DBConnection;
import application.Validation;
import application.assets.ModelRoom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by User on 24/3/2016.
 */
public class ModRoomController implements Initializable{

    @FXML private TextField tf_roomno;
    @FXML private TextField tf_roomcategory;
    @FXML private TextField tf_roomno2;
    @FXML private TextField tf_roomtype;
    @FXML private TextField tf_paxperroom;
    @FXML private TextField tf_roomprice;
    @FXML private TextField tf_twinbedprice;
    @FXML private TextField tf_fullbedprice;
    @FXML private TextField tf_queenbedprice;
    @FXML private TextField tf_kingbedprice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validation();

        DBConnection c = new DBConnection("Data.sqlite");

        tf_roomno.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String sql = "select * from RoomType rt "+
                        "Inner join Room rm on rt.typeid = rm.roomtypeid"+
                        "where rm.roomno="+tf_roomno.getText();
                ResultSet data = c.executeQuery(sql);
                ObservableList<ModelRoom> rtable = FXCollections.observableArrayList();

                String modroomno = data.getString("RoomNo");
                String modroomtype = data.getString("TypeName");
                String modroompax = data.getString("MaxPax");
                String modroomprice = data.getString("RoomPrice");
                String modroomtwinprice = data.getString("Rate_extTwin");
                String modroomfullprice = data.getString("Rate_extFull");
                String modroomqueenprice = data.getString("Rate_extQueen");
                String modroomkingprice = data.getString("Rate_extKing");

                tf_roomno.setText(modroomno);
                tf_roomtype.setText(modroomtype);
                tf_paxperroom.setText(modroompax);
                tf_roomprice.setText(modroomprice);
                tf_twinbedprice.setText(modroomtwinprice);
                tf_fullbedprice.setText(modroomfullprice);
                tf_queenbedprice.setText(modroomqueenprice);
                tf_kingbedprice.setText(modroomkingprice);

            }
            catch (SQLException e){
                e.printStackTrace();
            }
        });
    }

    private void validation() {
        tf_roomno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
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
