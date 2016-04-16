package application.assets.reservation;

import application.Validation;
import application.assets.ModelFacility;
import application.assets.ModelGroupMember;
import application.assets.ModelRoom;
import application.assets.ModelResv;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;

public class ResvEditController implements Initializable {

    @FXML private TextField tf_search;
    @FXML private TextField tf_resvno;
    @FXML private TextField tf_fname;
    @FXML private TextField tf_lname;
    @FXML private TextField tf_address;
    @FXML private TextField tf_postcode;
    @FXML private TextField tf_city;
    @FXML private TextField tf_state;
    @FXML private TextField tf_idno;
    @FXML private ComboBox cbox_country;
    @FXML private ComboBox cbox_idtype;

    @FXML private ToggleGroup ResvSearchGroup;
    @FXML private RadioButton rb_resvno;
    @FXML private RadioButton rb_roomno;
    @FXML private RadioButton rb_lname;
    @FXML private RadioButton rb_fname;

    @FXML private Button btn_search;
    @FXML private Button btn_resvcancel;
    @FXML private Button btn_addfac;
    @FXML private Button btn_delfac;
    @FXML private Button btn_addroom;
    @FXML private Button btn_delroom;
    @FXML private Button btn_addguest;
    @FXML private Button btn_delguest;
    @FXML private Button btn_next;

    @FXML private TableView<ModelGroupMember> table_memgroup;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memfname;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memlname;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memidtype;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memidno;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memroomno;

    @FXML private TableView<ModelRoom> table_room;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomcat;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomtype;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomno;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomci;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomco;
    @FXML private TableColumn<ModelRoom, String> tbcol_xtrabed;
    @FXML private TableColumn<ModelRoom, String> tbcol_rprice;

    @FXML private TableView<ModelFacility> table_fac;
    @FXML private TableColumn<ModelFacility, String> tbcol_fac;
    @FXML private TableColumn<ModelFacility, String> tbcol_facno;
    @FXML private TableColumn<ModelFacility, String> tbcol_facprice;
    @FXML private TableColumn<ModelFacility, String> tbcol_facbookdate;
    @FXML private TableColumn<ModelFacility, String> tbcol_faccomment;

    @FXML private TableView<ModelResv> table_sresult;
    @FXML private TableColumn<ModelResv, String> tbcol_sresv;
    @FXML private TableColumn<ModelResv, String> tbcol_sfname;
    @FXML private TableColumn<ModelResv, String> tbcol_slname;
    @FXML private TableColumn<ModelResv, String> tbcol_sroomno;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validation();
    }

    public void searchResv(){
        //language=SQLite
        ResvSearchGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (rb_resvno.isSelected()){
                String query = "SELECT * FROM Reservation";
            }
        });
    }

    private void validation() {
        tf_search.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(20));
        tf_resvno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(10));
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_address.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoCommaDot(50));
        tf_postcode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(12));
        tf_city.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_state.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }

}
