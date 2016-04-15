package application.assets.reservation;

import application.assets.*;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import application.Validation;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static application.slidemenu.SlideMenuController.db;

public class ResvController implements Initializable {

    @FXML private AnchorPane resvPane;
    @FXML private TextField tf_resvno;
    @FXML private TextField tf_fname;
    @FXML private TextField tf_lname;
    @FXML private TextField tf_phoneno;
    @FXML private TextField tf_address;
    @FXML private TextField tf_postcode;
    @FXML private TextField tf_city;
    @FXML private TextField tf_state;
    @FXML private TextField tf_idno;
    @FXML private ComboBox<String> cbox_country;
    @FXML private ComboBox<String> cbox_idtype;

    @FXML private Button btn_resvNext;
    @FXML private Button btn_addguest;
    @FXML private Button btn_delguest;
    @FXML private Button btn_addroom;
    @FXML private Button btn_delroom;
    @FXML private Button btn_addfac;
    @FXML private Button btn_delfac;

    @FXML private TableView<ModelGroupMember> table_gmembers;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memfname;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memlname;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memidtype;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memidno;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memroomno;

    @FXML private TableView<ModelRoom> table_resvRoom;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomcat;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomtype;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomno;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomci;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomco;
    @FXML private TableColumn<ModelRoom, String> tbcol_xtrabed;
    @FXML private TableColumn<ModelRoom, String> tbcol_rprice;

    @FXML private TableView<ModelFacility> table_resvFac;
    @FXML private TableColumn<ModelFacility, String> tbcol_fac;
    @FXML private TableColumn<ModelFacility, String> tbcol_facno;
    @FXML private TableColumn<ModelFacility, String> tbcol_facbookdate;
    @FXML private TableColumn<ModelFacility, String> tbcol_facprice;
    @FXML private TableColumn<ModelFacility, String> tbcol_faccomment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cellValueFactory();

        validations();

        addGuest();

        addRoom();

        delRoom();

        addFac();

        delFac();

        resvPay();

        //store short form of countries in array
        String[] locale = Locale.getISOCountries();
        //loop the array
        for (String countryCode : locale) {
            Locale country = new Locale("", countryCode);
            //add countries into cbox!
            cbox_country.getItems().add(country.getDisplayCountry());
        }

        new AutoCompleteCBoxListener<>(cbox_country); //use auto complete (cbox field is set editable)

        //auto generate resv number
        try {
            ResultSet rs = db.executeQuery("SELECT ResvNo FROM Reservation ORDER BY ResvNo");
            if (!rs.next()){
                tf_resvno.setText("1000000000");
            } else {
                //get the last no. of resv no (max no)
                rs = db.executeQuery("SELECT ResvNo FROM Reservation ORDER BY ResvNo DESC LIMIT 1");
                Integer resvno = rs.getInt("ResvNo");
                resvno += 1;
                tf_resvno.setText(String.valueOf(resvno)); //convert resvno to string
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    //pressing add guest button
    public void addGuest() {

        FXMLLoader loadGuest = new FXMLLoader(getClass().getResource("/application/assets" +
                "/reservation/resvaddgroup.fxml"));
        AnchorPane guestPane = new AnchorPane();
        try {
            guestPane = loadGuest.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResvAddGroupController rag = loadGuest.getController();

        AnchorPane finalGuestPane = guestPane;

        /*i cannot use the ForAddButton here because add guest need to retrieve value from room tableView
        * ,means got extra functions inside the listener
        * when the room tableview is empty, roomno combobox of resvAddGroup will be empty, but if room tableView
        * has row(s), retrieve the room no, time to add into the room no combobox!
        */
        btn_addguest.setOnMouseClicked(me -> {
            Stage stage = new Stage();
            int rowCount = table_resvRoom.getItems().size();

            if (finalGuestPane.getScene() != null) {
                stage.setScene(finalGuestPane.getScene());
            } else {
                Scene guestScene = new Scene(finalGuestPane);
                stage.setScene(guestScene);
            }
            if (rowCount != 0) {
                rag.getCbox_roomno().getItems().clear();

                for (int i = 0; i < rowCount; i++) {
                    ModelRoom room = new ModelRoom();
                    room = table_resvRoom.getItems().get(i);
                    System.out.print(room.getRoomno());
                    rag.getCbox_roomno().getItems().add(room.getRoomno());
                }
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setResizable(false);
        });

        rag.getBtn_addmem().setOnMouseClicked(me->{
            if (rag.getCbox_roomno().getSelectionModel().getSelectedItem() != null) {
                ModelGroupMember mg = new ModelGroupMember();

                mg.setMemFName(rag.getTf_fname().getText());
                mg.setMemLName(rag.getTf_lname().getText());
                mg.setIdType(rag.getCbox_idtype().getSelectionModel().getSelectedItem());
                mg.setIdNo(rag.getTf_idno().getText());
                mg.setRoomNo(rag.getCbox_roomno().getSelectionModel().getSelectedItem());

                table_gmembers.getItems().add(mg);

                rag.getTf_fname().setText(null);
                rag.getTf_lname().setText(null);
                rag.getTf_idno().setText(null);

                Stage stage = (Stage) rag.getBtn_addmem().getScene().getWindow();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection in Room");
                alert.setHeaderText("No Room is Selected");
                alert.setContentText("Please select a room, and if it is empty, please add a room.");
                alert.showAndWait();
            }
        });

    }//add guest ends

    //pressing add button for room as well as in room popup
    public void addRoom() {
        //VERY IMPORTANT: please use like the below, because can retrieve controller from fxmlloader easily
        FXMLLoader loadRoom = new FXMLLoader(getClass().getResource("/application/assets" +
                "/reservation/resvroom.fxml"));
        AnchorPane roomPane = new AnchorPane();
        try {
            roomPane = loadRoom.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane finalRoomPane = roomPane;

        new ForAddButton(finalRoomPane, btn_addroom);

        ResvRoomController rc = loadRoom.getController();

        rc.getBtn_roomAdd().setOnMouseClicked(me -> {
            ModelRoom room = new ModelRoom();

            room.setRoomcat(rc.getRoomCat());
            room.setRtype(rc.getRoomType());
            room.setRoomno(rc.getRoomNo());
            room.setCidate(rc.getCI());
            room.setCodate(rc.getCO());
            room.setExtbedtype(rc.getExtBed());
            room.setRoomprice(rc.getTotal());

            table_resvRoom.getItems().add(room);

            //manually clearing data from the add room stage after adding
            rc.getTable_rooms().getItems().clear();
            rc.getCbox_xtrabed().getItems().clear();
            rc.getLbl_extBedPrice().setText(null);
            rc.getLbl_roomPrice().setText(null);
            rc.getLbl_totalRoomPrice().setText(null);
            rc.getTf_roomno().setText(null);

            Stage stage = (Stage) rc.getBtn_roomAdd().getScene().getWindow();
            stage.close();
        });

    }//add room resv ends

    //delete room resv
    public void delRoom() {
        btn_delroom.setOnMouseClicked(me -> {
            int selectedRow = table_resvRoom.getSelectionModel().getSelectedIndex();
            if (selectedRow >= 0) {
                ModelRoom roomNo = new ModelRoom();
                roomNo = table_resvRoom.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Room");
                alert.setContentText("Are you sure you want to delete room " + roomNo.getRoomno() + "?");
                //detect user presses ok or cancel (must do like this)
                Optional<ButtonType> select = alert.showAndWait();
                if (select.isPresent()) {
                    if (select.get() == ButtonType.OK) {
                        table_resvRoom.getItems().remove(selectedRow);
                    } else {
                        alert.close();
                    }
                }
            } else {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("No Selection");
                alert2.setHeaderText("No Room is Selected");
                alert2.setContentText("Please select a room in the table to be deleted.");
                alert2.showAndWait();
            }
        });
    } //delete room resv ends

    //add facility
    public void addFac(){
        FXMLLoader loadfac = new FXMLLoader(getClass().getResource("/application/assets/" +
                "reservation/resvfacility.fxml"));
        AnchorPane facPane = new AnchorPane();
        try {
            facPane = loadfac.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnchorPane finalFacPane = facPane;

        new ForAddButton(finalFacPane, btn_addfac);

        ResvFacilityController rsf = loadfac.getController();

        rsf.getBtn_addfac().setOnMouseClicked(me->{
            ModelFacility fac = new ModelFacility();
            fac.setFacno(rsf.getTf_facno().getText());
            fac.setFacname(rsf.getLbl_facname().getText());
            fac.setBookedfacdate(rsf.getLbl_date().getText());
            fac.setFacdesc(rsf.getTf_comment().getText());
            fac.setFacprice(rsf.getLbl_facprice().getText());

            table_resvFac.getItems().add(fac);

            rsf.getTable_fac().getItems().clear();
            rsf.getLbl_date().setText(null);
            rsf.getLbl_facname().setText(null);
            rsf.getLbl_facprice().setText(null);
            rsf.getTf_comment().setText(null);
            rsf.getTf_facno().setText(null);

            Stage stage = (Stage) rsf.getBtn_addfac().getScene().getWindow();
            stage.close();
        });
    }//add facility done

    //delete facility
    public void delFac(){
        btn_delfac.setOnMouseClicked(me->{
            int selRow = table_resvFac.getSelectionModel().getSelectedIndex();
            if(selRow >=0) {
                ModelFacility mf = new ModelFacility();
                mf = table_resvFac.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Facility Booking");
                alert.setContentText("Are you sure you want to delete " + mf.getFacname() + " from the table?");

                Optional<ButtonType> sel = alert.showAndWait();
                if (sel.isPresent()) {
                    if (sel.get() == ButtonType.OK) {
                        table_resvFac.getItems().remove(selRow);
                    } else {
                        alert.close();
                    }
                }
            } else {
                Alert noSel = new Alert(Alert.AlertType.WARNING);
                noSel.setTitle("No Selection");
                noSel.setHeaderText("No Facility is Selected");
                noSel.setContentText("Please select a facility in the table to be deleted.");
                noSel.showAndWait();
            }
        });
    }//delete fac done

    //payment stuffs
    public void resvPay(){
        //bottom onwards are how I access back button from the payment controller (of payment fxml)
        FXMLLoader loadpayment = new FXMLLoader(getClass().getResource("/application/assets/" +
                "reservation/resvpay.fxml"));
        AnchorPane payment = null;
        try {
            payment = loadpayment.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResvPayController rpc = loadpayment.getController();

        AnchorPane finalPayment = payment;
        //next button
        btn_resvNext.setOnMouseClicked(me -> {

            try {
                ResultSet rs = db.executeQuery("SELECT CustID, CustID_Type, Blacklisted " +
                        "FROM Customer WHERE CustID='" + tf_idno + "' AND CustID_Type='" +
                        cbox_idtype.getSelectionModel().getSelectedItem() + "'");
                if (rs.next() && (Objects.equals(rs.getString("Blacklisted"), "yes") ||
                        Objects.equals(rs.getString("Blacklisted"), "Yes"))){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Blacklist Warning");
                    alert.setHeaderText("This customer has been blacklisted");
                    alert.setContentText("This customer has been blacklisted!");
                    alert.showAndWait();
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            FadeTransition ft = new FadeTransition(Duration.millis(320), finalPayment);
            ft.setFromValue(0.0); //add a simple fade in transition
            ft.setToValue(1.0);
            ft.play();
            resvPane.getChildren().add(finalPayment); //add as children of resvPane

            String query = "SELECT PaymentID FROM Payment ORDER BY PaymentID";
            try {
                ResultSet rs = db.executeQuery(query);
                if (!rs.next()){
                    rpc.getLbl_refno().setText("1000000000");
                } else {
                    //get the last no. of payment id (max id)
                    rs = db.executeQuery("SELECT PaymentID FROM Payment ORDER BY PaymentID DESC LIMIT 1");
                    Integer refno = rs.getInt("PaymentID");
                    refno += 1;
                    rpc.getLbl_refno().setText(String.valueOf(refno)); //convert refno to string
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            float sum = 0.00f;
            float dep = 0.00f;
            float tax = 0.00f;
            float subtotal = 1.00f;
            if (table_resvRoom.getItems().size() > 0) {
                for (ModelRoom mr: table_resvRoom.getItems()){
                    float add = Float.parseFloat(tbcol_rprice.getCellObservableValue(mr).getValue());
                    sum += add;
                    System.out.println(sum);
                }
                rpc.getLbl_total().setText(String.format(Locale.UK, "%.2f", sum));
            }
            if (table_resvFac.getItems().size() > 0){
                for (ModelFacility mf: table_resvFac.getItems()){
                    float add = Float.parseFloat(tbcol_facprice.getCellObservableValue(mf).getValue());
                    sum += add;
                    System.out.println(sum);
                }
                rpc.getLbl_total().setText(String.format(Locale.UK, "%.2f", sum));
            }
            try {
                ResultSet rs = db.executeQuery("SELECT deposit, taxrate FROM variables");
                if (rs.next()) {
                    rpc.getLbl_deposit().setText(String.format(Locale.UK, "%.2f", rs.getFloat("deposit")));
                    dep = rs.getFloat("deposit");
                    rpc.getLbl_tax().setText(String.valueOf(rs.getInt("taxrate")) + "%");
                    tax = (rs.getInt("taxrate") / 100f) + 1f;
                } else {
                    rpc.getLbl_tax().setText(null);
                    rpc.getLbl_deposit().setText(null);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (rpc.getLbl_deposit().getText()!= null){
                rpc.getRb_deposit().setDisable(false);
                if (rpc.getLbl_tax().getText() != null) {
                    subtotal = sum * tax;
                    subtotal += dep;
                    rpc.getLbl_subtotal().setText(String.format(Locale.UK, "%.2f", subtotal));
                    rpc.getLbl_balance().setText(String.format(Locale.UK, "%.2f", subtotal));
                } else {
                    subtotal = sum + dep;
                    rpc.getLbl_subtotal().setText(String.format(Locale.UK, "%.2f", subtotal));
                    rpc.getLbl_balance().setText(String.format(Locale.UK, "%.2f", subtotal));
                }
            } else {
                subtotal = sum;
                rpc.getRb_deposit().setDisable(true);
            }

            if (rpc.getLbl_total().getText() == null){
                rpc.getLbl_subtotal().setText("0.00");
                rpc.getLbl_balance().setText("0.00");
                rpc.getRb_deposit().setDisable(true);
                rpc.getRb_full().setDisable(true);
            } else {
                rpc.getRb_deposit().setDisable(false);
                rpc.getRb_full().setDisable(false);
            }
        });

        rpc.getBtn_resvBack().setOnMouseClicked(me -> { //getter from the payment controller
            Timeline timeline = new Timeline(); //set fade out
            assert finalPayment != null;
            KeyFrame kf = new KeyFrame(Duration.millis(320), new KeyValue(finalPayment.opacityProperty(), 0));
            timeline.getKeyFrames().add(kf);
            //when the timeline is finished (finished fade out) then invoke remove the finalPayment
            timeline.setOnFinished(se -> resvPane.getChildren().removeAll(finalPayment));
            timeline.play();
        });

        //reserve button
        rpc.getBtn_reserve().setOnMouseClicked(me -> {

            if (Objects.equals(rpc.getCbox_PayType().getSelectionModel().getSelectedItem(), "Credit Card")){
                //language=SQLite
                String ex = "INSERT INTO Customer VALUES ('" + tf_idno.getText() +
                        "', '" + cbox_idtype.getSelectionModel().getSelectedItem() + "', '" +
                        tf_fname.getText() + "', '" + tf_lname.getText() + "', 'no')";
                String ex2 = "INSERT INTO CustomerGroup VALUES ('')";
            }
        });

    } //end payment stuffs

    private void validations() {
        tf_resvno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_phoneno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(15));
        tf_address.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoCommaDot(50));
        tf_postcode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(12));
        tf_city.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }

    public void cellValueFactory() {
        //room booking
        tbcol_roomcat.setCellValueFactory(new PropertyValueFactory<>("roomcat"));
        tbcol_roomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
        tbcol_roomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
        tbcol_roomci.setCellValueFactory(new PropertyValueFactory<>("cidate"));
        tbcol_roomco.setCellValueFactory(new PropertyValueFactory<>("codate"));
        tbcol_xtrabed.setCellValueFactory(new PropertyValueFactory<>("extbedtype"));
        tbcol_rprice.setCellValueFactory(new PropertyValueFactory<>("roomprice"));

        //group member
        tbcol_memfname.setCellValueFactory(new PropertyValueFactory<>("memFName"));
        tbcol_memlname.setCellValueFactory(new PropertyValueFactory<>("memLName"));
        tbcol_memidtype.setCellValueFactory(new PropertyValueFactory<>("idType"));
        tbcol_memidno.setCellValueFactory(new PropertyValueFactory<>("idNo"));
        tbcol_memroomno.setCellValueFactory(new PropertyValueFactory<>("roomNo"));

        //fac booking
        tbcol_fac.setCellValueFactory(new PropertyValueFactory<>("facname"));
        tbcol_facno.setCellValueFactory(new PropertyValueFactory<>("facno"));
        tbcol_facbookdate.setCellValueFactory(new PropertyValueFactory<>("bookedfacdate"));
        tbcol_facprice.setCellValueFactory(new PropertyValueFactory<>("facprice"));
        tbcol_faccomment.setCellValueFactory(new PropertyValueFactory<>("facdesc"));

        //trick: make cells editable
        tbcol_memfname.setCellFactory(TextFieldTableCell.forTableColumn());
        tbcol_memlname.setCellFactory(TextFieldTableCell.forTableColumn());
        tbcol_memidno.setCellFactory(TextFieldTableCell.forTableColumn());

        tbcol_faccomment.setCellFactory(TextFieldTableCell.forTableColumn());
    }


}
