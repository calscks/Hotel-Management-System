package application.assets.checkout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @FXML private Label label_coDeposit;
    @FXML private Label label_coExtra;
    @FXML private Label label_coPayAmt;
    @FXML private Label label_coReturn;

    @FXML  private ComboBox<String> cbox_coCountry;
    @FXML  private ComboBox<String> cbox_coIDType;

    @FXML private Button btn_coCheckout;

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

        tf_missingprice.textProperty().addListener(((observable, oldValue, newValue) -> {
            addpay( Double.parseDouble(tf_missingprice.getText().trim()));
        }));
        tf_damageprice.textProperty().addListener(((observable, oldValue, newValue) -> {
            addpay( Double.parseDouble(tf_damageprice.getText().trim()));
        }));
        tf_bottleprice.textProperty().addListener(((observable, oldValue, newValue) -> {
            addpay(Double.parseDouble(tf_bottleprice.getText().trim()));
        }));
        label_coExtra.textProperty().addListener((observable, oldValue, newValue) -> {
           double amount = Double.parseDouble( label_coDeposit.getText().trim());
            double extraamt = Double.parseDouble(label_coExtra.getText().trim());
            double v = amount +extraamt;
            label_coReturn.setText(Double.toString(v));
        });

        table_co.setRowFactory(action->{
            TableRow<ModelCheckOut> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())){
                    ModelCheckOut rowData = row.getItem();
                     String RoomNo = rowData.getRoomno();
                        autofill(RoomNo);

                }
            });
            return row;
        });

        tf_coRoomNo.textProperty().addListener((observable, oldValue, newValue) -> {
            autofill(tf_coRoomNo.getText());

        });

        btn_coCheckout.setOnAction((event) -> {


            try {
                String custid = tf_coIDNo.getText();
                String sql = "SELECT * FROM CheckInOut WHERE RoomNo=" + "'" + custid + "'";
                ResultSet rs = db.executeQuery(sql);

                String codate = rs.getString("CheckOutDate");
                String status = rs.getString("Status");
                if (status.equals("CHECKEDIN")) {
                    sql = "UPDATE CheckInOut SET Status =" + "'CHECKOUT'" + "WHERE CustID =" + custid;
                    db.executeUpdate(sql);
                    if (check_coBlacklist.isSelected()){
                        sql= "UPDATE Customer SET Blacklisted= blacklisted WHERE CustID="+ custid ;
                        db.executeUpdate(sql);

                    }
                    ExtPayment();


                } else {
                    Alert notcheckedin = new Alert(Alert.AlertType.INFORMATION);
                    notcheckedin.setTitle("ERROR");
                    notcheckedin.setContentText("Customer has not check in yet!");
                    notcheckedin.showAndWait();

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

    private void checkboxes() {
        check_coMissing.setOnAction((event -> {if(check_coMissing.isSelected()){tf_missingprice.setEditable(true);}
        }));
        check_coBottle.setOnAction((event -> {if(check_coBottle.isSelected()){tf_bottleprice.setEditable(true);}
        }));
        check_coDamaged.setOnAction((event -> {if(check_coDamaged.isSelected()){tf_damageprice.setEditable(true);}}));
    }

    public void filltb(){
        try {
        String sql =" SELECT RoomNo, CustID,Customer.CustFName,Customer.CustLName FROM CheckInOut\n" +
                "INNER JOIN Customer USING (CustID)\n" +
                "WHERE CheckInOut.CheckOutDate ="+"'"+ LocalDate.now().toString() +"'" +"AND CheckInOut.Status= 'CHECKEDIN'";
            ResultSet todayco = db.executeQuery(sql);

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

            String sql = "SELECT * FROM CheckInOut\n" +
                    "  INNER JOIN Room using (RoomNo)\n" +
                    "  INNER JOIN Roomtype on Room.RoomTypeID = RoomType.TypeID\n" +
                    "  INNER JOIN Customer USING (CustID)\n" +
                    "  INNER JOIN CustAddress USING (CustID)\n" +
                    " INNER JOIN Payment USING (CustID)\n" +
                    "  WHERE RoomNo =" + "'" + Roomno  + "'";

            ResultSet codata = db.executeQuery(sql);
            String customerfname = codata.getString("CustFName");
            String customerlname = codata.getString("CustLName");
            String IDtype = codata.getString("CustID_Type");
            String customerid = codata.getString("CustID");
            String address = codata.getString("Address");
            String postcode = codata.getString("PostCode");
            String city = codata.getString("City");
            String Country = codata.getString("Country");
            String roomtype = codata.getString("RoomTypeID");
            String blacklist = codata.getString("Blacklisted");
            double deposit = Double.parseDouble(codata.getString("Deposit").trim());
            String codate = codata.getString("CheckOutDate");
            double roomprice = Integer.parseInt(codata.getString("RoomPrice").trim());

            tf_coIDNo.setText(customerid);
            tf_coFirstName.setText(customerfname);
            tf_coLastName.setText(customerlname);
            cbox_coIDType.getItems().add(IDtype);
            cbox_coIDType.getSelectionModel().select(IDtype);
            tf_coPostCode.setText(postcode);
            tf_coCity.setText(city);
            tf_coAddress.setText(address);
            cbox_coCountry.getItems().add(Country);
            cbox_coCountry.getSelectionModel().select(Country);
            label_coDeposit.setText("RM" + deposit);
            btn_coCheckout.setDisable(false);

            //blacklistcheck
            if (blacklist.equals("clean")){
                check_coBlacklist.setSelected(false);
            } else if(blacklist.equals("blacklisted")){
                check_coBlacklist.setSelected(true);
            }


            //OVERDUE LABEL CODE
            long overdate = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(codate));
            double overpay = (double) (overdate * roomprice);
            if (overdate >= 1) {
                label_coExtra.setText("RM" + Double.toString(overpay));
                label_coPayAmt.setTextFill(Color.web("#ff0000"));

                double Payamt = overpay - deposit;
                label_coPayAmt.setText("RM" + Double.toString(Payamt));
            } else {

                label_coPayAmt.setTextFill(Color.web("#000000"));
            }

        } catch (SQLException t1) {
            t1.printStackTrace();
            tf_coIDNo.setText("");
            tf_coFirstName.setText("");
            tf_coLastName.setText("");
            cbox_coIDType.getItems().removeAll(cbox_coIDType.getItems());
            tf_coPostCode.setText("");
            tf_coCity.setText("");
            tf_coAddress.setText("");
            cbox_coCountry.getItems().add("");
            cbox_coCountry.getSelectionModel().select("");
            label_coDeposit.setText("RM0.00");
            label_coExtra.setText("RM0.00");
            label_coPayAmt.setText("RM0.00");
        }
    }



    public void ExtPayment(){
        try {
        String sql="SELECT * FROM CheckInOut WHERE RoomNo="+"'"+tf_coRoomNo.getText()+"'";
            ResultSet rs = db.executeQuery(sql);
            String cioid = rs.getString("CIO_ID");
            sql="SELECT * FROM ExtPayment";
            ResultSet extpayment = db.executeQuery(sql);
            if (extpayment.wasNull()){
                sql = "INSERT INTO ExtPayment (ExtPaymentID, CIO_ID, ExtPaymentDetails, Total)\n" +
                        "    VALUES(2000000000,"+ cioid + "NULL"+ "'"+label_coPayAmt.getText()+"'";
                db.executeUpdate(sql);
            }else{

                sql = "INSERT INTO ExtPayment (ExtPaymentID, CIO_ID, ExtPaymentDetails, Total)\n" +
                        "    VALUES(NULL," + cioid + "NULL" + "'" + label_coPayAmt.getText() + "'";
                db.executeUpdate(sql);
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



