package application.assets.RnFManagement;

import application.Validation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import javax.swing.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static application.slidemenu.SlideMenuController.db;

public class AddRoomController implements Initializable{

    ObservableList<String>extrabeditems = FXCollections.observableArrayList("Yes","No");

    @FXML private ComboBox<String> cbox_grouproomcategory;
    @FXML private TextField tf_roomtype;
    @FXML private TextField tf_paxperroom;
    @FXML private TextField tf_roomprice;
    @FXML private TextField tf_twinbedprice;
    @FXML private TextField tf_fullbedprice;
    @FXML private TextField tf_queenbedprice;
    @FXML private TextField tf_kingbedprice;
    @FXML private TextField tf_roomno;
    @FXML private Button btn_addroomtype;
    @FXML private Button btn_addroom;
    @FXML private ComboBox<String> cbox_extrabed;
    @FXML private ComboBox<String> cbox_roomcategory;
    @FXML private ComboBox<String> cbox_roomtype;
    @FXML private RadioButton rb_addnewroomtype;
    @FXML private RadioButton rb_addnewroom;

    public Button getbtn_addroom(){ return btn_addroom; }
    public String getcbox_roomcategory() { return cbox_roomcategory.getSelectionModel().getSelectedItem(); }
    public String getcbox_roomtype() { return  cbox_roomtype.getSelectionModel().getSelectedItem(); }
    public String getRoomNo() { return tf_roomno.getText(); }
    public Button getBtn_addroomtype() { return btn_addroomtype; }
    public String getcbox_grouproomcategory() { return cbox_grouproomcategory.getSelectionModel().getSelectedItem(); }
    public String getRoomType() { return tf_roomtype.getText(); }
    public String getPax() { return tf_paxperroom.getText(); }
    public String getRoomPrice() { return tf_roomprice.getText(); }
    public String getExtraBed() { return cbox_extrabed.getSelectionModel().getSelectedItem(); }
    public String gettwinprice() {return tf_twinbedprice.getText();}
    public String getfullprice() {return tf_fullbedprice.getText();}
    public String getqueenprice() {return tf_queenbedprice.getText();}
    public String getkingprice() {return tf_kingbedprice.getText();}

    public ComboBox<String> getCbox_roomcategory() { return cbox_roomcategory; }
    public ComboBox<String> getCbox_roomtype() { return cbox_roomtype; }
    public TextField getTf_roomno() { return tf_roomno; }
    public ComboBox<String> getCbox_grouproomcategory() { return cbox_grouproomcategory; }
    public TextField getTf_roomtype() { return tf_roomtype; }
    public TextField getTf_paxperroom() { return tf_paxperroom; }
    public TextField getTf_roomprice() { return tf_roomprice; }
    public ComboBox<String> getCbox_extrabed() { return cbox_extrabed; }
    public TextField getTf_twinbedprice() { return tf_twinbedprice; }
    public TextField getTf_fullbedprice() { return tf_fullbedprice; }
    public TextField getTf_queenbedprice() { return tf_queenbedprice; }
    public TextField getTf_kingbedprice() { return tf_kingbedprice; }


    @FXML private void extrabed(){
        if (cbox_extrabed.getSelectionModel().getSelectedItem().equals("Yes")){
            tf_twinbedprice.setDisable(false);
            tf_fullbedprice.setDisable(false);
            tf_queenbedprice.setDisable(false);
            tf_kingbedprice.setDisable(false);
        }
        else if (cbox_extrabed.getSelectionModel().getSelectedItem().equals("No")){
            tf_twinbedprice.setDisable(true);
            tf_fullbedprice.setDisable(true);
            tf_queenbedprice.setDisable(true);
            tf_kingbedprice.setDisable(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validation();

        //extrabed combobox
        cbox_extrabed.setItems(extrabeditems);
        //roomcat and grouproomcat combobox
        ObservableList<String> roomcategory = FXCollections.observableArrayList();
        try {
            ResultSet rs = db.executeQuery("SELECT TypeGroup FROM RoomType GROUP BY TypeGroup ORDER BY TypeGroup;");
            while (rs.next()){
                roomcategory.add(rs.getString("TypeGroup"));
            }
            cbox_roomcategory.setItems(roomcategory);
            cbox_grouproomcategory.setItems(roomcategory);
        }catch (SQLException e){
            e.printStackTrace();
        }
        //room type combobox
        ObservableList<String> roomtype = FXCollections.observableArrayList();
        cbox_roomcategory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cbox_roomtype.getItems().clear();
            if (cbox_roomcategory.getSelectionModel().getSelectedItem().equals("President")){
                try{
                    ResultSet rs1 = db.executeQuery("SELECT TypeName from RoomType WHERE TypeName LIKE 'President%'");
                    while (rs1.next()){
                        roomtype.add(rs1.getString("TypeName"));
                    }
                    cbox_roomtype.setItems(roomtype);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            else if (cbox_roomcategory.getSelectionModel().getSelectedItem().equals("Commercial")){
                try {
                    ResultSet rs1 = db.executeQuery("SELECT TypeName FROM RoomType WHERE TypeName LIKE 'Commercial%'");
                    while (rs1.next()){
                        roomtype.add(rs1.getString("TypeName"));
                    }
                    cbox_roomtype.setItems(roomtype);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            else {
                try {
                    ResultSet rs1 = db.executeQuery("SELECT TypeName FROM RoomType WHERE TypeName LIKE 'Deluxe%'");
                    while (rs1.next()){
                        roomtype.add(rs1.getString("TypeName"));
                    }
                    cbox_roomtype.setItems(roomtype);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });


        //disable all field
        cbox_grouproomcategory.setDisable(true);
        tf_roomtype.setDisable(true);
        tf_paxperroom.setDisable(true);
        tf_roomprice.setDisable(true);
        cbox_extrabed.setDisable(true);
        tf_twinbedprice.setDisable(true);
        tf_fullbedprice.setDisable(true);
        tf_queenbedprice.setDisable(true);
        tf_kingbedprice.setDisable(true);
        btn_addroomtype.setDisable(true);
        cbox_roomcategory.setDisable(true);
        cbox_roomtype.setDisable(true);
        tf_roomno.setDisable(true);
        btn_addroom.setDisable(true);

        //enable field based on radiobtn
        ToggleGroup group = new ToggleGroup();
        rb_addnewroomtype.setToggleGroup(group);
        rb_addnewroom.setToggleGroup(group);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (rb_addnewroomtype.isSelected()){
                    cbox_grouproomcategory.setDisable(false);
                    tf_roomtype.setDisable(false);
                    tf_paxperroom.setDisable(false);
                    tf_roomprice.setDisable(false);
                    cbox_extrabed.setDisable(false);
                    tf_twinbedprice.setDisable(true);
                    tf_fullbedprice.setDisable(true);
                    tf_queenbedprice.setDisable(true);
                    tf_kingbedprice.setDisable(true);
                    btn_addroomtype.setDisable(false);
                    cbox_roomcategory.setDisable(true);
                    cbox_roomtype.setDisable(true);
                    tf_roomno.setDisable(true);
                    btn_addroom.setDisable(true);
                }
                else{
                    cbox_grouproomcategory.setDisable(true);
                    tf_roomtype.setDisable(true);
                    tf_paxperroom.setDisable(true);
                    tf_roomprice.setDisable(true);
                    cbox_extrabed.setDisable(true);
                    tf_twinbedprice.setDisable(true);
                    tf_fullbedprice.setDisable(true);
                    tf_queenbedprice.setDisable(true);
                    tf_kingbedprice.setDisable(true);
                    btn_addroomtype.setDisable(true);
                    cbox_roomcategory.setDisable(false);
                    cbox_roomtype.setDisable(false);
                    tf_roomno.setDisable(false);
                    btn_addroom.setDisable(false);
                }
            }
        });
    }

    private void validation() {
        cbox_grouproomcategory.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(10));
        tf_roomno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_roomtype.addEventFilter(KeyEvent.KEY_TYPED, Validation.validForTypeName(50));
        tf_paxperroom.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_roomprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_twinbedprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_fullbedprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_queenbedprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_kingbedprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
    }
}
