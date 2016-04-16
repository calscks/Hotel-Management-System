package application.assets.checkin;

import application.DBConnection;
import application.Validation;
import application.assets.*;
import application.assets.reservation.ResvAddGroupController;
import application.assets.reservation.ResvFacilityController;
import application.assets.reservation.ResvRoomController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;

public class CIController implements Initializable{

    @FXML
    private Button btn_ciNext;
    @FXML
    private TextField tf_ciResvNum;
    @FXML
    private TextField tf_ciFirstName;
    @FXML
    private Button button;
    @FXML
    private TextField tf_ciLastName;
    @FXML
    private TextField tf_ciPhoneNo;
    @FXML
    private ComboBox cbox_ciCountry;
    @FXML
    private TextField tf_ciAddress;
    @FXML
    private TextField tf_ciPostCode;
    @FXML
    private TextField tf_ciCity;
    @FXML
    private TextField tf_ciIDType;
    @FXML
    private TextField tf_ciIDNo;
    @FXML
    private Button btn_ciAddGroupMember;
    @FXML
    private Button btn_ciAddRoom;
    @FXML
    private Button btn_ciAddFacility;
    @FXML
    private TableView<ModelRoom> roomtable;
    @FXML
    private TableColumn<ModelRoom, String> ciroomno;
    @FXML
    private TableColumn<ModelRoom, String> ciroomtype;
    @FXML
    private TableColumn<ModelRoom, String> cicid;
    @FXML
    private TableColumn<ModelRoom, String> cicod;
    @FXML
    private TableColumn<ModelRoom, String> roomprice;
    @FXML
    private TableColumn<ModelRoom, String> roomcategory;

    @FXML
    private TableView<ModelFacility> cifactable;
    @FXML
    private TableColumn<ModelFacility, String> bookfaccol;
    @FXML
    private TableColumn<ModelFacility, String> facno;
    @FXML
    private TableColumn<ModelFacility, String> facdate;
    @FXML
    private TableColumn<ModelFacility, String> facprice;
    @FXML
    private TableView<ModelGroupMember> grouptable;
    @FXML
    private TableColumn<ModelGroupMember, String> tableC_ciGroupFirstName;
    @FXML
    private TableColumn<ModelGroupMember, String> tableC_ciGroupLastName;
    @FXML
    private TableColumn<ModelGroupMember, String> tableC_ciIcno;
    @FXML
    private TableColumn<ModelGroupMember, String> tableC_ciGroupRoomNo;
    @FXML
    private TableColumn<ModelGroupMember, String> tableC_ciGroupIdType;
    @FXML
    private TableView<ModelCIToday> table_ciToday;
    @FXML
    private TableColumn<ModelCIToday, String> tbcol_resvno;
    @FXML
    private TableColumn<ModelCIToday, String> tbcol_fname;
    @FXML
    private TableColumn<ModelCIToday, String> tbcol_lname;
    @FXML
    private TableColumn<ModelCIToday, String> tbcol_co;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addGuest();
        addRoom();
        addFacility();
        validation();

        tf_ciResvNum.textProperty().addListener((observable, oldValue,newValue)-> {
            try {
                String sql = "select * from Reservation rsv " +
                        "Inner join Customer cust on rsv.custid = cust.custid " +
                        "Inner join CustAddress address on cust.custid = address.custid " +
                        "Inner join CheckInOut cio on address.custid = cio.custid " +
                        "Inner join roombooking rbk on rsv.resvno = rbk.resvno  " +
                        "Inner join roomtype rtype on rbk.RoomTypeName = rtype.TypeName " +
                        "where rsv.ResvNo = " +tf_ciResvNum.getText();
                String sql2 = "select * from FacBookedDate fbd " +
                        "INNER JOIN FacType ftype on fbd.FacNo = ftype.FacNo " +
                        "where ResvNo =" +tf_ciResvNum.getText();
                String sql3 =" select * from CustomerGroup custgroup " +
                        "inner join customer cust on custgroup.G_CustID = cust.CustID " +
                        "INNER JOIN Reservation rsv on cust.CustID = rsv.CustID " +
                        "where rsv.ResvNo ="+tf_ciResvNum.getText() ;
                ObservableList<ModelRoom> rtable = FXCollections.observableArrayList();
                ObservableList<ModelFacility> ftable = FXCollections.observableArrayList();
                ObservableList<ModelGroupMember> gtable = FXCollections.observableArrayList();
                ResultSet data = db.executeQuery(sql);

                String firstname = data.getString("CustFName");
                String lastname = data.getString("CustLName");
                String address = data.getString("Address");
                String postcode = data.getString("PostCode");
                String city = data.getString("City");
                String country = data.getString("Country");
                String idtype = data.getString("CustID_Type");
                String idno = data.getString("CustID");

                tf_ciFirstName.setText(firstname);
                tf_ciLastName.setText(lastname);
                tf_ciAddress.setText(address);
                tf_ciCity.setText(city);
                tf_ciIDNo.setText(idno);
                tf_ciIDType.setText(idtype);
                tf_ciPostCode.setText(postcode);
                while (data.next()){
                    ModelRoom rm = new ModelRoom();
                    rm.setRoomno(data.getString("roomno"));
                    rm.setRtype(data.getString("roomtypename"));
                    rm.setRoomcat(data.getString("typegroup"));
                    rm.setRoomprice(data.getString("roomprice"));
                    rm.setCidate(data.getString("date_ci"));
                    rm.setCodate(data.getString("date_co"));
                    rtable.add(rm);

                    System.out.println(data.toString()); //for debugging, it prints the memory location of Employee class
                    System.out.println(rm.getRoomno()); //for debugging, confirm works, can get the usernameSystem.out.println(rm.getRoomno()); //for debugging, confirm works, can get the username
                    //System.out.println(fc.getfacname()); //for debugging, confirm works, can get the username

                }
                ResultSet data2 = db.executeQuery(sql2);
                while (data2.next()){
                    ModelFacility fc = new ModelFacility();
                    fc.setBookedfac(data2.getString("facname"));
                    fc.setFacno(data2.getString("facno"));
                    fc.setBookedfacdate(data2.getString("bookdate"));
                    fc.setFacprice(data2.getString("facprice"));
                    ftable.add(fc);
                }
                ResultSet data3 = db.executeQuery(sql3);
                while (data3.next()) {
                    ModelGroupMember gm = new ModelGroupMember();
                    gm.setMemFName(data3.getString("custfname"));
                    gm.setMemLName(data3.getString("custlname"));
                    gm.setIdType(data3.getString("custid_type"));
                    gm.seticNum(data3.getString("idno"));
                    gtable.add(gm);
                }
                tableC_ciGroupFirstName.setCellValueFactory(new PropertyValueFactory<>("memFName"));
                tableC_ciGroupLastName.setCellValueFactory(new PropertyValueFactory<>("memLName"));
                tableC_ciGroupIdType.setCellValueFactory(new PropertyValueFactory<>("idType"));
                tableC_ciIcno.setCellValueFactory(new PropertyValueFactory<>("icNum"));
                grouptable.setItems(gtable);
                ciroomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
                ciroomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
                roomcategory.setCellValueFactory(new PropertyValueFactory<>("roomcat"));
                roomprice.setCellValueFactory(new PropertyValueFactory<>("roomprice"));
                cicid.setCellValueFactory(new PropertyValueFactory<>("cidate"));
                cicod.setCellValueFactory(new PropertyValueFactory<>("codate"));
                roomtable.setItems(rtable);
                bookfaccol.setCellValueFactory(new PropertyValueFactory<>("bookedfac"));
                facno.setCellValueFactory(new PropertyValueFactory<>("facno"));
                facdate.setCellValueFactory(new PropertyValueFactory<>("bookedfacdate"));
                facprice.setCellValueFactory(new PropertyValueFactory<>("facprice"));
                cifactable.setItems(ftable);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    private void validation() {
        tf_ciResvNum.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(10));
        tf_ciFirstName.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_ciLastName.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_ciPhoneNo.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(15));
        tf_ciIDType.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(10));
        tf_ciIDNo.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }
    public void addGuest() {

        FXMLLoader loadGuest = new FXMLLoader(getClass().getResource("/application/assets" +
                "/reservation/resvaddgroup.fxml"));
        AnchorPane guestPane = new AnchorPane();
        try {
            guestPane = loadGuest.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResvAddGroupController cag = loadGuest.getController();

        AnchorPane finalGuestPane = guestPane;

        btn_ciAddGroupMember.setOnMouseClicked(me -> {
            Stage stage = new Stage();
            int rowCount = roomtable.getItems().size();

            if (finalGuestPane.getScene() != null) {
                stage.setScene(finalGuestPane.getScene());
            } else {
                Scene guestScene = new Scene(finalGuestPane);
                stage.setScene(guestScene);
            }

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
        });

        cag.getBtn_addmem().setOnMouseClicked(me->{

                ModelGroupMember mg = new ModelGroupMember();

                tf_ciAddress.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoCommaDot(50));
                tf_ciPostCode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(12));
                tf_ciCity.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
                mg.setMemFName(cag.getTf_fname().getText());
                mg.setMemLName(cag.getTf_lname().getText());
                mg.setIdType(cag.getCbox_idtype().getSelectionModel().getSelectedItem());
                mg.setIdNo(cag.getTf_idno().getText());

                grouptable.getItems().add(mg);

                cag.getTf_fname().setText(null);
                cag.getTf_lname().setText(null);
                cag.getTf_idno().setText(null);

                Stage stage = (Stage) cag.getBtn_addmem().getScene().getWindow();
                stage.close();
            });
        }
    //add guest ends
    public void addRoom() {
         //VERY IMPORTANT: please use like the below, because can retrieve controller from fxmlloader easily
        FXMLLoader loadRoom = new FXMLLoader(getClass().getResource("/application/assets" +
                "/reservation/resvroom.fxml"))
        AnchorPane roomPane = new AnchorPane();
        try {
            roomPane = loadRoom.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane finalRoomPane = roomPane;

        new ForAddButton(finalRoomPane, btn_ciAddRoom);

        ResvRoomController ciroom = loadRoom.getController();

        ciroom.getBtn_roomAdd().setOnMouseClicked(me -> {
            ModelRoom room = new ModelRoom();

            room.setRoomcat(ciroom.getRoomCat());
            room.setRtype(ciroom.getRoomType());
            room.setRoomno(ciroom.getRoomNo());
            room.setCidate(ciroom.getCI());
            room.setCodate(ciroom.getCO());
            room.setExtbedtype(ciroom.getExtBed());
            room.setRoomprice(ciroom.getTotal());

            roomtable.getItems().add(room);

            //manually clearing data from the add room stage after adding
            ciroom.getTable_rooms().getItems().clear();
            ciroom.getCbox_xtrabed().getItems().clear();
            ciroom.getLbl_extBedPrice().setText(null);
            ciroom.getLbl_roomPrice().setText(null);
            ciroom.getLbl_totalRoomPrice().setText(null);
            ciroom.getTf_roomno().setText(null);

            Stage stage = (Stage) ciroom.getBtn_roomAdd().getScene().getWindow();
            stage.close();
        });

    }
    public void addFacility(){
        FXMLLoader loadFac = new FXMLLoader(getClass().getResource("/application/assets" +
                "/reservation/resvfacility.fxml"));
        AnchorPane facPane = new AnchorPane();
        try {
            facPane = loadFac.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane finalFacPane = facPane;

        new ForAddButton(finalFacPane, btn_ciAddFacility);

        ResvFacilityController rsf = loadFac.getController();
        rsf.getBtn_addfac().setOnMouseClicked(me->{
            ModelFacility fac = new ModelFacility();
            fac.setFacno(rsf.getTf_facno().getText());
            fac.setFacname(rsf.getLbl_facname().getText());
            fac.setBookedfacdate(rsf.getLbl_date().getText());
            fac.setFacdesc(rsf.getTf_comment().getText());
            fac.setFacprice(rsf.getLbl_facprice().getText());

            cifactable.getItems().add(fac);

            rsf.getTable_fac().getItems().clear();
            rsf.getLbl_date().setText(null);
            rsf.getLbl_facname().setText(null);
            rsf.getLbl_facprice().setText(null);
            rsf.getTf_comment().setText(null);
            rsf.getTf_facno().setText(null);

            Stage stage = (Stage) rsf.getBtn_addfac().getScene().getWindow();
            stage.close();
        });
    }
}



