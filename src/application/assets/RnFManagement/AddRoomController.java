package application.assets.RnFManagement;

import application.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class AddRoomController implements Initializable{

    ObservableList<String>extrabeditems = FXCollections.observableArrayList("Yes","No");

    @FXML private TextField tf_roomcategory;
    @FXML private TextField tf_roomno;
    @FXML private TextField tf_roomtype;
    @FXML private TextField tf_paxperroom;
    @FXML private TextField tf_roomprice;
    @FXML private TextField tf_twinbedprice;
    @FXML private TextField tf_fullbedprice;
    @FXML private TextField tf_queenbedprice;
    @FXML private TextField tf_kingbedprice;
    @FXML private Button btn_addroom;
    @FXML private ComboBox<String> cbox_extrabed;

    public Button getbtn_addroom(){ return btn_addroom; }
    //not sure right or wrong!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public String getRoomCat() { return tf_roomcategory.getText(); }

    public String getRoomNo() { return tf_roomno.getText(); }

//    public String getRoomType() { return tf_roomtype.getText(); }
//
//    public String getPax() { return tf_paxperroom.getText(); }
//
//    public String getRoomPrice() { return tf_roomprice.getText(); }
//
//    public String getExtraBed() { return cbox_extrabed.getSelectionModel().getSelectedItem(); }
//
//    public String gettwinprice() {return tf_twinbedprice.getText();}
//
//    public String getfullprice() {return tf_fullbedprice.getText();}
//
//    public String getqueenprice() {return tf_queenbedprice.getText();}
//
//    public String getkingprice() {return tf_kingbedprice.getText();}

    public TextField getTf_roomcategory() { return tf_roomcategory; }

    public TextField getTf_roomno() { return tf_roomno; }

    public TextField getTf_roomtype() { return tf_roomtype; }

    public TextField getTf_paxperroom() { return tf_paxperroom; }

    public TextField getTf_roomprice() { return tf_roomprice; }

    public ComboBox<String> getCbox_extrabed() { return cbox_extrabed; }

    public TextField getTf_twinbedprice() { return tf_twinbedprice; }

    public TextField getTf_fullbedprice() { return tf_fullbedprice; }

    public TextField getTf_queenbedprice() { return tf_queenbedprice; }

    public TextField getTf_kingbedprice() { return tf_kingbedprice; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validation();

        cbox_extrabed.setItems(extrabeditems);

    }

    private void validation() {
        tf_roomcategory.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(10));
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
