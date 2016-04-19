package application.assets.checkout;

import application.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;


public class COController implements Initializable {

    @FXML private TextField tf_coRoomNo;
    @FXML private TextField tf_coFirstName;
    @FXML private TextField tf_coLastName;
    @FXML private TextField tf_coIDNo;
    @FXML private TextField tf_coAddress;
    @FXML private TextField tf_coPostCode;
    @FXML private TextField tf_coCity;
    @FXML private TextField tf_missingprice;
    @FXML private TextField tf_bottleprice;
    @FXML private TextField tf_damageprice;
    @FXML private TextField tf_coState;
    @FXML private TextField tf_coCountry;

    @FXML private Label label_coDeposit;
    @FXML private Label label_coExtra;
    @FXML private Label label_coPayAmt;
    @FXML private Label label_coReturn;

    @FXML  private ComboBox<String> cbox_coIDType;

    @FXML private Button btn_coCheckout;
    @FXML private Button btn_missing;
    @FXML private Button btn_bottle;
    @FXML private Button btn_damage;
    @FXML private Button btn_reset;

    @FXML private TableView<ModelCheckOut> table_co;
    @FXML private TableColumn<ModelCheckOut,String> table_coRoomNo;
    @FXML private TableColumn<ModelCheckOut,String> table_coCustID;
    @FXML private TableColumn<ModelCheckOut,String> table_coFname;
    @FXML private TableColumn<ModelCheckOut,String> table_coLname;

    @FXML private CheckBox check_coBlacklist;
    @FXML private CheckBox check_coBottle;
    @FXML private CheckBox check_coDamaged;
    @FXML private CheckBox check_coMissing;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filltb();
        checkboxes();
        validation();
        check_coBlacklist.setOnAction(event -> {
            if(check_coBlacklist.isSelected()){
                Alert prompt = new Alert(Alert.AlertType.CONFIRMATION);
                prompt.setContentText("Confirm to Black List?");
                Optional<ButtonType> result = prompt.showAndWait();
                if (result.get() == ButtonType.OK){
                    prompt.close();
                } else {
                    check_coBlacklist.setSelected(false);
                    prompt.close();
                }
            }
        });
        tf_coFirstName.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (tf_coFirstName.getText().equals("")){
                btn_coCheckout.setDisable(true);
            }
            else{
                btn_coCheckout.setDisable(false);
            }
        }));
        btn_missing.setOnAction(event -> {
                addpay(Double.parseDouble(tf_missingprice.getText().trim()));
        });
        btn_damage.setOnAction(event -> {
            addpay( Double.parseDouble(tf_damageprice.getText().trim()));
        });
        btn_bottle.setOnAction(event -> {
            addpay(Double.parseDouble(tf_bottleprice.getText().trim()));
        });


        label_coExtra.textProperty().addListener((observable, oldValue, newValue) -> {
           double amount = Double.parseDouble( label_coDeposit.getText().trim());
            double extraamt = Double.parseDouble(label_coExtra.getText().trim());
            double v = extraamt - amount;
            label_coPayAmt.setText(Double.toString(v));
        });
        label_coPayAmt.textProperty().addListener((observable, oldValue, newValue) -> {
            double depo = Double.parseDouble(label_coDeposit.getText().trim());
            double paidamt = Double.parseDouble(label_coExtra.getText().trim());

    double returnto = depo- paidamt;
    label_coReturn.setText(Double.toString(returnto));

        });


        table_co.setRowFactory(action->{
            TableRow<ModelCheckOut> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())){
                    ModelCheckOut rowData = row.getItem();
                     String RoomNo = rowData.getRoomno();

                        tf_coRoomNo.setText(RoomNo);
                }
            });
            return row;
        });

        tf_coRoomNo.textProperty().addListener((observable, oldValue, newValue) -> {
            autofill(tf_coRoomNo.getText());

        });

//STATUS IS ONLY "Checked In" and "Checked Out" case sensitive? just follow//
         btn_coCheckout.setOnAction((event) -> {
            try {
                String sql = "SELECT * FROM RoomBooking\n" +
                        "INNER JOIN CheckInOut ON CheckInOut.ResvNo = RoomBooking.ResvNo\n" +
                        "INNER JOIN Reservation USING (ResvNo)\n" +
                        "INNER JOIN Customer using (CustID)\n" +
                        "WHERE RoomBooking.RoomNo = '"+tf_coRoomNo.getText()+"' AND CheckInOut.Status= 'Checked In'";
                ResultSet rs = db.executeQuery(sql);
                Alert checkout = new Alert(Alert.AlertType.CONFIRMATION);
                checkout.setTitle("Check Out");
                checkout.setContentText("Confirm Room"+ tf_coRoomNo.getText()+" Check Out?");
                Optional<ButtonType> result = checkout.showAndWait();
                if (result.get() == ButtonType.OK){
                    String custid = rs.getString("CustID");
                    int resno = rs.getInt("ResvNo");
                    sql = "UPDATE CheckInOut SET Status = 'Checked Out'WHERE CheckInOut.ResvNo ='"+resno+"'";
                    db.executeUpdate(sql);
                    if(check_coBlacklist.isSelected()){
                        sql= "UPDATE Customer SET Blacklisted='yes' WHERE Customer.CustID='"+custid+"'";
                        db.executeUpdate(sql);
                        ExtPayment();
                    }else
                         ExtPayment();
                } else {
                      checkout.close();
                }



            } catch (SQLException e1) {
                e1.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText("Room Not Found");
                alert.setContentText("There are no check-ins in this room");

            }

        });


    }

    private void validation() {
        tf_missingprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(5));
        tf_damageprice.addEventFilter(KeyEvent.KEY_TYPED,Validation.validNo(5));
        tf_bottleprice.addEventFilter(KeyEvent.KEY_TYPED,Validation.validNo(5));
    }

    private void checkboxes() {
        tf_missingprice.setEditable(false);
        tf_bottleprice.setEditable(false);
        tf_damageprice.setEditable(false);

        check_coMissing.setOnAction((event -> {

            if (check_coMissing.isSelected()) {

                tf_missingprice.setEditable(true);
            } else {
                tf_missingprice.setEditable(false);

            }
        }));
        check_coBottle.setOnAction((event -> {

            if(check_coBottle.isSelected()){tf_bottleprice.setEditable(true);}
            else{
            tf_bottleprice.setEditable(false);

        }
        }));
        check_coDamaged.setOnAction((event -> {

            if(check_coDamaged.isSelected()){

                tf_damageprice.setEditable(true);}
        else{
        tf_damageprice.setEditable(false);

        }
        }));
    }

    public void filltb(){
        try {
        String sql =" SELECT * FROM CheckInOut\n" +
                "  INNER JOIN Customer USING (CustID)\n" +
                "INNER JOIN RoomBooking USING (ResvNo)\n" +
                "WHERE RoomBooking.Date_CO ="+"'"+ LocalDate.now().toString() +"'" +"AND CheckInOut.Status= 'Checked In'";
            ResultSet todayco = db.executeQuery(sql);
            //test

        ObservableList<ModelCheckOut> cotable = FXCollections.observableArrayList();
        while(todayco.next()){
            ModelCheckOut co = new ModelCheckOut();
            co.setRoomno(todayco.getString("RoomNo"));
            co.setCustid(todayco.getString("CustID"));
            co.setFirstname(todayco.getString("CustFName"));
            co.setLastname(todayco.getString("CustLName"));
            cotable.add(co);
        }

            table_coCustID.setCellValueFactory(new PropertyValueFactory<>("custid"));
            table_coRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomno"));
            table_coFname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            table_coLname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            table_co.setItems(cotable);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public  void autofill(String Roomno){
        try {
            //AUTOFILLS THE SCENE
            String sql = "SELECT * FROM RoomBooking\n" +
                    "  INNER JOIN CheckInOut ON CheckInOut.ResvNo = RoomBooking.ResvNo\n" +
                    "  INNER JOIN Customer using (CustID)\n" +
                    "  inner join CustAddress using (CustID)\n" +
                    "  inner join Payment ON RoomBooking.ResvNo = Payment.ResvNo\n" +
                    "WHERE CheckInOut.Status ='Checked In' and RoomBooking.RoomNo ='" + Roomno + "'";

            ResultSet codata = db.executeQuery(sql);
            String customerid = null;
            String customerfname = null;
            String customerlname = null;
            String IDtype = null;
            String postcode = null;
            String city = null;
            String address = null;
            String Country = null;
            String State = null;
            double deposit = 0;
            String codate = null;
            double roomprice = 0;
            if (codata.next()) {
                customerfname = codata.getString("CustFName");
                customerlname = codata.getString("CustLName");
                IDtype = codata.getString("CustID_Type");
                customerid = codata.getString("CustID");
                address = codata.getString("Address");
                postcode = codata.getString("PostCode");
                city = codata.getString("City");
                Country = codata.getString("Country");
                State = codata.getString("State");
                deposit = Double.parseDouble(codata.getString("Deposit").trim());
                codate = codata.getString("Date_CO");
                roomprice = Double.parseDouble(codata.getString("Price").trim());
            }
            tf_coIDNo.setText(customerid);
            tf_coFirstName.setText(customerfname);
            tf_coLastName.setText(customerlname);
            cbox_coIDType.getItems().add(IDtype);
            cbox_coIDType.getSelectionModel().select(IDtype);
            tf_coPostCode.setText(postcode);
            tf_coCity.setText(city);
            tf_coAddress.setText(address);
            tf_coCountry.setText(Country);
            tf_coState.setText(State);
            label_coDeposit.setText(String.valueOf(deposit));
            btn_coCheckout.setDisable(false);


            //OVERDUE LABEL CODE
            long overdate = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(codate));
            double overpay = (double) (overdate * roomprice);
            if (overdate > 1) {
                label_coExtra.setText(Double.toString(overpay));
                check_coBlacklist.setTextFill(Color.web("#ff0000"));
            }


        } catch (SQLException t1) {
            //if fail reset the scene
            t1.printStackTrace();
            tf_coIDNo.setText("");
            tf_coFirstName.setText("");
            tf_coLastName.setText("");
            cbox_coIDType.getItems().removeAll(cbox_coIDType.getItems());
            tf_coPostCode.setText("");
            tf_coCity.setText("");
            tf_coAddress.setText("");
            tf_coState.setText("");
            tf_coCountry.setText("");
            label_coDeposit.setText("0.00");
            label_coExtra.setText("0.00");
            label_coPayAmt.setText("0.00");
            label_coReturn.setText("0.00");
            btn_coCheckout.setDisable(true);
            check_coBlacklist.setTextFill(Color.web("#000000"));


        }
    }


    public void ExtPayment(){
        try {

        String sql="SELECT * FROM RoomBooking\n" +
                "INNER JOIN CheckInOut ON CheckInOut.ResvNo = RoomBooking.ResvNo\n" +
                "INNER JOIN Reservation ON RoomBooking.ResvNo = Reservation.ResvNo\n" +
                "WHERE RoomNo='" + tf_coRoomNo.getText() + "'" +"and CheckInOut.Status = 'Checked In'";
            ResultSet rs = db.executeQuery(sql);
            int cioid = 0;
            if (rs.next()) {
                cioid = rs.getInt("CIO_ID");
            }
            sql="SELECT * FROM ExtPayment";
            ResultSet extpayment = db.executeQuery(sql);

            if (!extpayment.next()){
                sql = "INSERT INTO ExtPayment (ExtPaymentID,CIO_ID,ExtPaymentDetails, Total)\n" +
                        "    VALUES(2000000000,"+ cioid + ",'No Details',"+Double.parseDouble(label_coPayAmt.getText())+")";
                db.executeUpdate(sql);
                Alert complete = new Alert(Alert.AlertType.INFORMATION);
                complete.setTitle("Check Out Successful");
                complete.setContentText("Room "+ tf_coRoomNo.getText()+"has Checked Out");
                complete.showAndWait();
            }else{

                sql = "INSERT INTO ExtPayment (ExtPaymentID,CIO_ID,ExtPaymentDetails, Total)\n" +
                        "    VALUES(NULL," + cioid + ",'No Details'," + Double.parseDouble(label_coPayAmt.getText()) + ")";
                db.executeUpdate(sql);
                Alert complete = new Alert(Alert.AlertType.INFORMATION);
                complete.setTitle("Check Out Successful");
                complete.setContentText("Room "+ tf_coRoomNo.getText()+"has Checked Out");
                complete.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addpay(double prices){

        double extraprice = Double.parseDouble(label_coExtra.getText());
        double v = extraprice + prices;
        label_coExtra.setText(Double.toString(v));


    }

}



