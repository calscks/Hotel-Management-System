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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;

/**
 * Created by User on 24/3/2016.
 */
public class ModRoomController implements Initializable{

    ObservableList<String> selectbyitems = FXCollections.observableArrayList("RoomCategory","RoomNo");

    @FXML private TextField tf_searchby;
    @FXML private ComboBox cbox_searchby;
    @FXML private TextField tf_roomtypeid;
    @FXML private TextField tf_roomno2;
    @FXML private TextField tf_roomtype;
    @FXML private TextField tf_paxperroom;
    @FXML private TextField tf_roomprice;
    @FXML private TextField tf_groupsearchby;
    @FXML private TextField tf_grouproomcategory;
    @FXML private TextField tf_grouproomtype;
    @FXML private TextField tf_grouppaxperroom;
    @FXML private TextField tf_grouproomprice;
    @FXML private TextField tf_grouptwinbedprice;
    @FXML private TextField tf_groupfullbedprice;
    @FXML private TextField tf_groupqueenbedprice;
    @FXML private TextField tf_groupkingbedprice;
    @FXML private Button btn_search;
    @FXML private Button btn_groupsearch;
    @FXML private Button btn_modaddroom;
    @FXML private Button btn_delroom;
    @FXML private Button btn_delroomtype;
    @FXML private Button btn_editroom;
    @FXML private Button btn_editroomtype;
    @FXML private TableView<ModelRoom> tv_modroom;
    @FXML private TableView<ModelRoom> tv_groupmodroom;
    @FXML private TableColumn<ModelRoom, String> tc_modroomno;
    @FXML private TableColumn<ModelRoom, String> tc_modroomtype;
    @FXML private TableColumn<ModelRoom, String> tc_typeid;
    @FXML private TableColumn<ModelRoom, String> tc_groupmodroomcategory;
    @FXML private TableColumn<ModelRoom, String> tc_groupmodroomtype;
    @FXML private TableColumn<ModelRoom, String> tc_grouptypeid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validation();

        cbox_searchby.setItems(selectbyitems);
        addroom();
        delroom();
        delroomgroup();
        editroom();
        editroomcategory();
        searchroomcat();
        doubleclickroomcat();

        //textfield disable validation
        tf_paxperroom.setEditable(false);
        tf_roomprice.setEditable(false);
        tf_roomno2.setEditable(false);
        tf_roomtype.setEditable(false);

        //searchroom
        btn_search.setOnMouseClicked(me -> {
            if (cbox_searchby.getSelectionModel().getSelectedItem().equals("RoomCategory")){
                try {
                    //language=SQLite
                    String sql = "SELECT * FROM Room rm " +
                            "INNER JOIN RoomType rt on rm.RoomTypeID = rt.TypeID " +
                            "WHERE rt.TypeGroup ='" + tf_searchby.getText() + "'";

                    ResultSet data = db.executeQuery(sql);
                    ObservableList<ModelRoom> rtable = FXCollections.observableArrayList();

                    String modroomtypeid = data.getString("RoomTypeID");
                    String modroomno = data.getString("RoomNo");
                    String modroomtype = data.getString("TypeName");
                    String modroompax = data.getString("MaxPax");
                    String modroomprice = data.getString("RoomPrice");

                    tf_roomtypeid.setText(modroomtypeid);
                    tf_roomno2.setText(modroomno);
                    tf_roomtype.setText(modroomtype);
                    tf_paxperroom.setText(modroompax);
                    tf_roomprice.setText(modroomprice);
                    while(data.next()){
                        ModelRoom rm = new ModelRoom();
                        rm.setRoomtypeid(data.getString("RoomTypeID"));
                        rm.setRoomno(data.getString("RoomNo"));
                        rm.setRtype(data.getString("TypeGroup"));
                        rm.setRoomtype(data.getString("TypeName"));
                        rm.setPaxperroom(data.getString("MaxPax"));
                        rm.setRoomprice(data.getString("RoomPrice"));
                        rtable.add(rm);
                    }
                    tc_modroomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
                    tc_modroomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
                    tc_typeid.setCellValueFactory(new PropertyValueFactory<>("roomtypeid"));
                    tv_modroom.setItems(rtable);
                    //data.close();
                    //db.closeCon();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }

            }
            else if (cbox_searchby.getSelectionModel().getSelectedItem().equals("RoomNo")){
                try {
                    String sql = "SELECT * FROM Room rm " +
                            "INNER JOIN RoomType rt on rm.RoomTypeID = rt.TypeID " +
                            "WHERE rm.RoomNo ='" + tf_searchby.getText() + "'";
                    ResultSet data2 = db.executeQuery(sql);
                    ObservableList<ModelRoom> rtable = FXCollections.observableArrayList();
                    while(data2.next()){

                        String modroomid = data2.getString("RoomTypeID");
                        String modroomno = data2.getString("RoomNo");
                        String modroomtype = data2.getString("TypeName");
                        String modroompax = data2.getString("MaxPax");
                        String modroomprice = data2.getString("RoomPrice");

                        tf_roomtypeid.setText(modroomid);
                        tf_roomno2.setText(modroomno);
                        tf_roomtype.setText(modroomtype);
                        tf_paxperroom.setText(modroompax);
                        tf_roomprice.setText(modroomprice);

                        ModelRoom rm = new ModelRoom();
                        rm.setRoomtypeid(data2.getString("RoomTypeID"));
                        rm.setRoomno(data2.getString("RoomNo"));
                        rm.setRtype(data2.getString("TypeGroup"));
                        rm.setRoomtype(data2.getString("TypeName"));
                        rm.setPaxperroom(data2.getString("MaxPax"));
                        rm.setRoomprice(data2.getString("RoomPrice"));
                        rtable.add(rm);
                    }
                    tc_modroomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
                    tc_modroomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
                    tc_typeid.setCellValueFactory(new PropertyValueFactory<>("roomtypeid"));
                    tv_modroom.setItems(rtable);
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
        //doubleclickroom
        tv_modroom.setRowFactory(tv ->{
            TableRow<ModelRoom> selRow = new TableRow<>();
            selRow.setOnMouseClicked(me ->{
                if (cbox_searchby.getValue().equals("RoomCategory")){
                    if (me.getClickCount() == 2 && (!selRow.isEmpty())){
                        ModelRoom mr = new ModelRoom();
                        mr = tv_modroom.getSelectionModel().getSelectedItem();

                        tf_roomtypeid.clear();
                        tf_roomno2.clear();
                        tf_roomtype.clear();
                        tf_paxperroom.clear();
                        tf_roomprice.clear();

                        String sql = "SELECT * FROM Room rm " +
                                "INNER JOIN RoomType rt on rm.RoomTypeID = rt.TypeID " +
                                "WHERE rt.TypeGroup ='" + tf_searchby.getText() + "'";
                        try {
                            ResultSet data = db.executeQuery(sql);
                            if (data.next()){
                                tf_roomtypeid.setText(mr.getRoomtypeid());
                                tf_roomno2.setText(mr.getRoomno());
                                tf_roomtype.setText(mr.getRoomtype());
                                tf_paxperroom.setText(mr.getPaxperroom());
                                tf_roomprice.setText(mr.getRoomprice());
                            }
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }
                else if (cbox_searchby.getValue().equals("RoomNo")){
                    if (me.getClickCount() == 2 && (!selRow.isEmpty())){
                        ModelRoom mr = new ModelRoom();
                        mr = tv_modroom.getSelectionModel().getSelectedItem();

                        tf_roomtypeid.clear();
                        tf_roomno2.clear();
                        tf_roomtype.clear();
                        tf_paxperroom.clear();
                        tf_roomprice.clear();

                        String sql = "SELECT * FROM Room rm " +
                                "INNER JOIN RoomType rt on rm.RoomTypeID = rt.TypeID " +
                                "WHERE rm.RoomNo ='" + tf_searchby.getText()  + "'";
                        try {
                            ResultSet data = db.executeQuery(sql);
                            if (data.next()){
                                tf_roomtypeid.setText(mr.getRoomtypeid());
                                tf_roomno2.setText(mr.getRoomno());
                                tf_roomtype.setText(mr.getRoomtype());
                                tf_paxperroom.setText(mr.getPaxperroom());
                                tf_roomprice.setText(mr.getRoomprice());
                            }
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }

            });
            return selRow;
        });

        //change other textfield based on one textfield
        tf_roomtypeid.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String sql = "SELECT * FROM RoomType "+
                        "WHERE TypeID = "+tf_roomtypeid.getText()+"";
                ResultSet data = db.executeQuery(sql);
                while (data.next()){
                    String roomtype = data.getString("TypeName");
                    String paxperroom = data.getString("MaxPax");
                    String roomprice = data.getString("RoomPrice");

                    tf_roomtype.setText(roomtype);
                    tf_paxperroom.setText(paxperroom);
                    tf_roomprice.setText(roomprice);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }

    private void searchroomcat() {
        btn_groupsearch.setOnMouseClicked(me ->{
            try {
                String sqll = "SELECT * FROM RoomType rt" +
                        " WHERE TypeGroup ='" + tf_groupsearchby.getText() + "'";

                ResultSet data = db.executeQuery(sqll);
                ObservableList<ModelRoom> rtable = FXCollections.observableArrayList();
                while(data.next()){
                    String grouproomcategory = data.getString("TypeGroup");
                    String grouproomtype = data.getString("TypeName");
                    String grouproompax = data.getString("MaxPax");
                    String grouproomprice = data.getString("RoomPrice");
                    String grouproomtwinprice = data.getString("Rate_extTwin");
                    String grouproomfullprice = data.getString("Rate_extFull");
                    String grouproomqueenprice = data.getString("Rate_extQueen");
                    String grouproomkingprice = data.getString("Rate_extKing");

                    tf_grouproomcategory.setText(grouproomcategory);
                    tf_grouproomtype.setText(grouproomtype);
                    tf_grouppaxperroom.setText(grouproompax);
                    tf_grouproomprice.setText(grouproomprice);
                    tf_grouptwinbedprice.setText(grouproomtwinprice);
                    tf_groupfullbedprice.setText(grouproomfullprice);
                    tf_groupqueenbedprice.setText(grouproomqueenprice);
                    tf_groupkingbedprice.setText(grouproomkingprice);

                    ModelRoom mr = new ModelRoom();
                    //mr.setRoomno(data.getString("RoomNo"));
                    mr.setRtype(data.getString("TypeGroup"));
                    mr.setRoomprice(data.getString("RoomPrice"));
                    mr.setRoomtype(data.getString("TypeName"));
                    mr.setPaxperroom(data.getString("MaxPax"));
                    mr.setRoomprice(data.getString("RoomPrice"));
                    mr.setTwinbedprice(data.getString("Rate_extTwin"));
                    mr.setFullbedprice(data.getString("Rate_extFull"));
                    mr.setQueenbedprice(data.getString("Rate_extQueen"));
                    mr.setKingbedprice(data.getString("Rate_extKing"));
                    mr.setTypeid(data.getString("TypeID"));
                    rtable.add(mr);
                }
                tc_groupmodroomcategory.setCellValueFactory(new PropertyValueFactory<>("rtype"));
                tc_groupmodroomtype.setCellValueFactory(new PropertyValueFactory<>("roomtype"));
                tc_grouptypeid.setCellValueFactory(new PropertyValueFactory<>("typeid"));
                tv_groupmodroom.setItems(rtable);

            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }


    private void doubleclickroomcat() {
        tv_groupmodroom.setRowFactory(tv ->{
            TableRow<ModelRoom> selRow = new TableRow<>();
            selRow.setOnMouseClicked(me ->{
                if (me.getClickCount() == 2 && (!selRow.isEmpty())){
                    ModelRoom mr = new ModelRoom();
                    mr = tv_groupmodroom.getSelectionModel().getSelectedItem();

                    tf_grouproomcategory.clear();
                    tf_grouproomtype.clear();
                    tf_grouppaxperroom.clear();
                    tf_grouproomprice.clear();
                    tf_grouptwinbedprice.clear();
                    tf_groupfullbedprice.clear();
                    tf_groupqueenbedprice.clear();
                    tf_groupkingbedprice.clear();

                    String sql = "SELECT * FROM RoomType rt " +
                            "WHERE rt.TypeGroup ='" + tf_groupsearchby.getText() + "'";
                    try {
                        ResultSet data = db.executeQuery(sql);
                        if (data.next()){
                            tf_grouproomcategory.setText(mr.getRtype());
                            tf_grouproomtype.setText(mr.getRoomtype());
                            tf_grouppaxperroom.setText(mr.getPaxperroom());
                            tf_grouproomprice.setText(mr.getRoomprice());
                            tf_grouptwinbedprice.setText(mr.getTwinbedprice());
                            tf_groupfullbedprice.setText(mr.getFullbedprice());
                            tf_groupqueenbedprice.setText(mr.getQueenbedprice());
                            tf_groupkingbedprice.setText(mr.getKingbedprice());
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            });
            return selRow;
        });
    }

    private void editroomcategory() {
        btn_editroomtype.setOnMouseClicked(me->{
            if (tf_grouproomcategory.getText().isEmpty() || tf_grouproomtype.getText().isEmpty()
                     || tf_grouppaxperroom.getText().isEmpty() || tf_grouproomprice.getText().isEmpty()
                     || tf_grouptwinbedprice.getText().isEmpty() || tf_groupfullbedprice.getText().isEmpty()
                     || tf_groupqueenbedprice.getText().isEmpty() || tf_groupkingbedprice.getText().isEmpty()){
                Alert emptyfield = new Alert(Alert.AlertType.WARNING);
                emptyfield.setTitle("Empty Field");
                emptyfield.setHeaderText("One of the field is empty");
                emptyfield.setContentText("Please make sure to fill in all the field");
                emptyfield.showAndWait();
            }
            else {
                int selRow = tv_groupmodroom.getSelectionModel().getSelectedIndex();
                if (selRow >= 0){
                    try{
                        String sql1 = "SELECT * FROM RoomType " +
                                "WHERE TypeGroup = '"+tf_groupsearchby.getText()+"'";

                        ResultSet data = db.executeQuery(sql1);
                        ModelRoom mr = new ModelRoom();
                        mr = tv_groupmodroom.getSelectionModel().getSelectedItem();
                        mr.setTypeid(data.getString("TypeID"));

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("Edit Room Category");
                        alert.setContentText("Are you sure you want to edit "+mr.getRtype()+" from the table?");

                        Optional<ButtonType> sel = alert.showAndWait();
                        if (sel.isPresent()){
                            if (sel.get() == ButtonType.OK){
                                String grouproomcat = tf_grouproomcategory.getText();
                                String grouproomtype = tf_grouproomtype.getText();
                                String grouppaxperroom = tf_grouppaxperroom.getText();
                                String grouproomprice = tf_grouproomprice.getText();
                                String grouptwinbedprice = tf_grouptwinbedprice.getText();
                                String groupfullbedprice = tf_groupfullbedprice.getText();
                                String groupqueenbedprice = tf_groupqueenbedprice.getText();
                                String groupkingbedprice = tf_groupkingbedprice.getText();

                                try {
                                    String sql="UPDATE RoomType " +
                                            "SET TypeGroup = '"+grouproomcat+"', "+
                                            "TypeName = '"+grouproomtype+"', "+
                                            "MaxPax = "+grouppaxperroom+", "+
                                            "Rate_extTwin = "+grouptwinbedprice+", "+
                                            "Rate_extFull = "+groupfullbedprice+", "+
                                            "Rate_extQueen = "+groupqueenbedprice+", "+
                                            "Rate_extKing = "+groupkingbedprice+", "+
                                            "RoomPrice = "+grouproomprice+" "+
                                            "WHERE TypeID = "+mr.getTypeid()+"";
                                    db.executeUpdate(sql);
                                }catch (SQLException e){
                                    e.printStackTrace();
                                }
                                tf_grouproomcategory.clear();
                                tf_grouproomtype.clear();
                                tf_grouppaxperroom.clear();
                                tf_grouproomprice.clear();
                                tf_grouptwinbedprice.clear();
                                tf_groupfullbedprice.clear();
                                tf_groupqueenbedprice.clear();
                                tf_groupkingbedprice.clear();
                            }
                            else {
                                alert.close();
                            }
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                }
                else {
                    Alert noSel = new Alert(Alert.AlertType.WARNING);
                    noSel.setTitle("No Selection");
                    noSel.setHeaderText("No Room is selected");
                    noSel.setContentText("Please select a room in the table to be edited");
                    noSel.showAndWait();
                }
            }

        });
    }

    private void editroom() {
        btn_editroom.setOnMouseClicked( me->{
            if (tf_roomtypeid.getText().isEmpty() || tf_roomno2.getText().isEmpty() ||
                     tf_roomtype.getText().isEmpty() || tf_paxperroom.getText().isEmpty() ||
                     tf_roomprice.getText().isEmpty()){
                Alert emptyfield = new Alert(Alert.AlertType.WARNING);
                emptyfield.setTitle("Empty Field");
                emptyfield.setHeaderText("One of the field is empty");
                emptyfield.setContentText("Please make sure to fill in all the table");
                emptyfield.showAndWait();
            }
            else {
                int selRow = tv_modroom.getSelectionModel().getSelectedIndex();
                if (selRow >= 0){
                    try {
                        String sql = "SELECT * FROM RoomType rt "+
                                "INNER JOIN Room rm ON rt.TypeID = rm.RoomTypeID " +
                                "WHERE rm.RoomNo = '"+tf_roomno2.getText()+"'";
                        ResultSet data = db.executeQuery(sql);
                        if (data.next()){
                            ModelRoom mr = new ModelRoom();
                            mr = tv_modroom.getSelectionModel().getSelectedItem();
                            mr.setTypeid(data.getString("TypeID"));
                            mr.setRoomtypeid(data.getString("RoomTypeID"));

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Edit Room");
                            alert.setContentText("Are you sure you want to edit "+mr.getRoomno()+" from the table?");

                            Optional<ButtonType> sel = alert.showAndWait();
                            if (sel.isPresent()){
                                if (sel.get() == ButtonType.OK){
                                    String roomid = tf_roomtypeid.getText();
                                    try {
                                        String sql1 = "UPDATE Room "+
                                                "SET RoomTypeID = "+roomid+"" +
                                                " WHERE RoomTypeID = "+mr.getRoomtypeid()+"";
                                        db.executeUpdate(sql1);
                                    }catch (SQLException e){
                                        e.printStackTrace();
                                    }
                                    tf_roomtypeid.clear();
                                    tf_roomno2.clear();
                                    tf_roomtype.clear();
                                    tf_paxperroom.clear();
                                    tf_roomprice.clear();
                                }
                                else {
                                    alert.close();
                                }
                            }
                        }


                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
                else {
                    Alert noSel = new Alert(Alert.AlertType.WARNING);
                    noSel.setTitle("No Selection");
                    noSel.setHeaderText("No Room is selected");
                    noSel.setContentText("Please select a room in the table to be edited");
                    noSel.showAndWait();
                }
            }

        });
    }

    private void delroomgroup() {
        btn_delroomtype.setOnMouseClicked(me->{
            int selRow = tv_groupmodroom.getSelectionModel().getSelectedIndex();
            if (selRow >= 0){
                try {
                    String sqll = "SELECT * FROM RoomType rt" +
                            " INNER JOIN Room rm ON rt.TypeID = rm.RoomTypeID" +
                            " WHERE TypeGroup ='" + tf_groupsearchby.getText() + "'";

                    ResultSet data = db.executeQuery(sqll);
                    ModelRoom mr = new ModelRoom();
                    mr = tv_groupmodroom.getSelectionModel().getSelectedItem();
                    mr.setRoomno(data.getString("RoomNo"));

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Delete room type");
                    alert.setContentText("All the room that you assigned to the selected room type will also be delete. Are you sure you want to delete " + mr.getRoomtype() + " from the table?");

                    Optional<ButtonType> sel = alert.showAndWait();
                    if (sel.isPresent()){
                        if (sel.get() == ButtonType.OK){
                            tv_groupmodroom.getItems().remove(selRow);
                                try {
                                    String sql = "DELETE FROM RoomType WHERE TypeName = '" + mr.getRoomtype() + "'";
                                    db.executeUpdate(sql);
                                }catch (SQLException e){
                                    e.printStackTrace();
                                }
                                try {
                                    String sql = "DELETE FROM Room WHERE RoomTypeID NOT IN (SELECT TypeID FROM RoomType)";
                                    db.executeUpdate(sql);
                                }catch (SQLException e){
                                    e.printStackTrace();
                                }

                            tf_grouproomcategory.clear();
                            tf_grouproomtype.clear();
                            tf_grouppaxperroom.clear();
                            tf_grouproomprice.clear();
                            tf_grouptwinbedprice.clear();
                            tf_groupfullbedprice.clear();
                            tf_groupqueenbedprice.clear();
                            tf_groupkingbedprice.clear();
                        }
                        else {
                            alert.close();
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            else {
                Alert noSel = new Alert(Alert.AlertType.WARNING);
                noSel.setTitle("No Selection");
                noSel.setHeaderText("No Room is selected");
                noSel.setContentText("Please select a room category in the table to be deleted");
                noSel.showAndWait();
            }
        });
    }

    private void delroom() {
        btn_delroom.setOnMouseClicked(me->{
            int selRow = tv_modroom.getSelectionModel().getSelectedIndex();
            if(selRow >= 0){
                ModelRoom mr = new ModelRoom();
                mr = tv_modroom.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete room");
                alert.setContentText("Are you sure you want to delete '" + mr.getRoomno() + "'from the table");

                Optional<ButtonType> sel = alert.showAndWait();
                if (sel.isPresent()){
                    if (sel.get() == ButtonType.OK){
                        tv_modroom.getItems().remove(selRow);
                        try {
                            String sql = "DELETE FROM Room WHERE RoomNo = '" + mr.getRoomno() + "'";
                            db.executeUpdate(sql);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                        tf_roomtypeid.clear();
                        tf_roomno2.clear();
                        tf_roomtype.clear();
                        tf_paxperroom.clear();
                        tf_roomprice.clear();
                    }
                    else {
                        alert.close();
                    }
                }
            }
            else {
                Alert noSel = new Alert(Alert.AlertType.WARNING);
                noSel.setTitle("No Selection");
                noSel.setHeaderText("No Room is selected");
                noSel.setContentText("Please select a room in the table to be deleted");
                noSel.showAndWait();
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
            if (arc.getCbox_roomcategory().getItems().isEmpty() || arc.getCbox_roomtype().getItems().isEmpty()
                    || arc.getTf_roomno().getText().isEmpty()){
                Stage stage = (Stage) arc.getbtn_addroom().getScene().getWindow();
                stage.close();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty Field");
                alert.setHeaderText("One of the field is empty");
                alert.setContentText("Please make sure to fill in all the field");
                alert.showAndWait();
            }
            else {
                String roomtype = arc.getCbox_roomtype().getSelectionModel().getSelectedItem();
                String roomno = arc.getTf_roomno().getText();

                try {
                    String sql = "INSERT INTO Room (RoomNo, RoomTypeID) "+
                            "VALUES ('"+ roomno +"',(SELECT TypeID FROM RoomType WHERE TypeName = '"+ roomtype+"' LIMIT 1))";

                    db.executeQuery(sql);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                //clear items
                arc.getCbox_roomcategory().getSelectionModel().clearSelection();
                arc.getCbox_roomtype().getSelectionModel().clearSelection();
                arc.getTf_roomno().clear();
                arc.getCbox_grouproomcategory().getSelectionModel().clearSelection();
                arc.getTf_roomtype().clear();
                arc.getTf_paxperroom().clear();
                arc.getTf_roomprice().clear();
                arc.getCbox_extrabed().getSelectionModel().clearSelection();
                arc.getTf_twinbedprice().clear();
                arc.getTf_fullbedprice().clear();
                arc.getTf_queenbedprice().clear();
                arc.getTf_kingbedprice().clear();
                //close addroom
                Stage stage = (Stage) arc.getbtn_addroom().getScene().getWindow();
                stage.close();
            }

        });

        arc.getBtn_addroomtype().setOnMouseClicked(me ->{

            ComboBox cboxextrabed = arc.getCbox_extrabed();

            if (cboxextrabed.getSelectionModel().getSelectedItem().equals("Yes")) {
                if (arc.getCbox_grouproomcategory().getItems().isEmpty() || arc.getTf_roomtype().getText().isEmpty() ||
                         arc.getTf_paxperroom().getText().isEmpty() || arc.getTf_roomprice().getText().isEmpty() ||
                         arc.getCbox_extrabed().getItems().isEmpty() || arc.getTf_twinbedprice().getText().isEmpty() ||
                         arc.getTf_fullbedprice().getText().isEmpty() || arc.getTf_queenbedprice().getText().isEmpty() ||
                         arc.getTf_kingbedprice().getText().isEmpty()){
                    Stage stage = (Stage) arc.getBtn_addroomtype().getScene().getWindow();
                    stage.close();
                    Alert emptyfield = new Alert(Alert.AlertType.WARNING);
                    emptyfield.setTitle("Empty Field");
                    emptyfield.setHeaderText("One of the field is empty");
                    emptyfield.setContentText("Please make sure to fill in all the field");
                    emptyfield.showAndWait();
                }
                else {
                    String grouproomcategory = arc.getCbox_grouproomcategory().getSelectionModel().getSelectedItem();
                    String roomtype = arc.getTf_roomtype().getText();
                    Integer maxpax = Integer.parseInt(arc.getTf_paxperroom().getText());
                    Integer roomprice = Integer.parseInt(arc.getTf_roomprice().getText());
                    Float twinprice = Float.parseFloat(arc.getTf_twinbedprice().getText());
                    Float fullprice = Float.parseFloat(arc.getTf_fullbedprice().getText());
                    Float queenprice = Float.parseFloat(arc.getTf_queenbedprice().getText());
                    Float kingprice = Float.parseFloat(arc.getTf_kingbedprice().getText());
                    try {
                        String sql1 = "INSERT INTO RoomType (TypeGroup,TypeName,MaxPax,RoomPrice,Rate_extTwin,Rate_extFull,Rate_extQueen,Rate_extKing) " +
                                "VALUES ('" + grouproomcategory + "','" + roomtype + "'," + maxpax + "," + roomprice + "," + twinprice + "," + fullprice + "," + queenprice + "," + kingprice + ")";

                        db.executeUpdate(sql1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (cboxextrabed.getSelectionModel().getSelectedItem().equals("No")){
                if (arc.getCbox_grouproomcategory().getItems().isEmpty() || arc.getTf_roomtype().getText().isEmpty() ||
                         arc.getTf_paxperroom().getText().isEmpty() || arc.getTf_roomprice().getText().isEmpty() ||
                        arc.getCbox_extrabed().getItems().isEmpty()){
                    Stage stage = (Stage) arc.getBtn_addroomtype().getScene().getWindow();
                    stage.close();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Empty Field");
                    alert.setHeaderText("One of the field is empty");
                    alert.setContentText("Please make sure to fill in all the field");
                    alert.showAndWait();
                }
                else {
                    String grouproomcategory = arc.getCbox_grouproomcategory().getSelectionModel().getSelectedItem();
                    String roomtype = arc.getTf_roomtype().getText();
                    Integer maxpax = Integer.parseInt(arc.getTf_paxperroom().getText());
                    Integer roomprice = Integer.parseInt(arc.getTf_roomprice().getText());

                    try {
                        String sql2 = "INSERT INTO RoomType (TypeGroup,TypeName,MaxPax,RoomPrice)" +
                                "VALUES ('" + grouproomcategory + "','" + roomtype + "'," + maxpax + "," + roomprice + ")";

                        db.executeQuery(sql2);
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }
            //clear items in modaddroom
            arc.getCbox_roomcategory().getSelectionModel().clearSelection();
            arc.getTf_roomtype().clear();
            arc.getTf_paxperroom().clear();
            arc.getTf_roomprice().clear();
            arc.getCbox_extrabed().getSelectionModel().clearSelection();
            arc.getTf_twinbedprice().clear();
            arc.getTf_fullbedprice().clear();
            arc.getTf_queenbedprice().clear();
            arc.getTf_kingbedprice().clear();

            Stage stage = (Stage) arc.getbtn_addroom().getScene().getWindow();
            stage.close();
        });


    }



    private void validation() {
        tf_searchby.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_roomtypeid.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(1));
        tf_roomno2.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_roomtype.addEventFilter(KeyEvent.KEY_TYPED, Validation.validForTypeName(50));
        tf_paxperroom.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_roomprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_groupsearchby.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_grouproomcategory.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(10));
        tf_grouproomtype.addEventFilter(KeyEvent.KEY_TYPED, Validation.validForTypeName(50));
        tf_grouppaxperroom.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_grouproomprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_grouptwinbedprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_groupfullbedprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_groupqueenbedprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_groupkingbedprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
    }
}