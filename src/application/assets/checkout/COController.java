package application.assets.checkout;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import application.DBConnection;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;



public class COController implements Initializable {

    Date currentdate = new Date();
    @FXML private TextField tf_coRoomNo;
    @FXML private TextField tf_coFirstName;
    @FXML private TextField tf_coLastName;
    @FXML private TextField tf_coIDNo;
    @FXML private TextField tf_coAddress;
    @FXML private TextField tf_coPostCode;
    @FXML private TextField tf_coCity;

    @FXML private Label label_coDeposit;
    @FXML private Label label_coOverdue;
    @FXML private Label label_coPayAmt;

    @FXML private ComboBox<String> cbox_coCountry;
    @FXML private ComboBox<String> cbox_coIDType;

    @FXML private Button btn_coCheckout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        tf_coRoomNo.textProperty().addListener((observable, oldValue, newValue)-> {

            try {
                String sql = "SELECT * FROM CheckInOut\n" +
                        "  INNER JOIN Room using (RoomNo)\n" +
                        "  INNER JOIN Roomtype on Room.RoomTypeID = RoomType.TypeID\n" +
                        "  INNER JOIN Customer USING (CustID)\n" +
                        "  INNER JOIN CustAddress USING (CustID)\n" +
                        " INNER JOIN Payment USING (CustID)\n" +
                        "  WHERE RoomNo =" + "'" + tf_coRoomNo.getText() + "'";
                DBConnection c = new DBConnection("Data.sqlite");
                ResultSet codata = c.executeQuery(sql);
                c.closeCon();

                String customerfname = codata.getString("CustFName");
                String customerlname = codata.getString("CustLName");
                String IDtype = codata.getString("CustID_Type");
                String customerid = codata.getString("CustID");
                String address = codata.getString("Address");
                String postcode = codata.getString("PostCode");
                String city = codata.getString("City");
                String Country = codata.getString("Country");
                String roomtype = codata.getString("RoomTypeID");
                double deposit = Double.parseDouble(codata.getString("Deposit").trim());
                String codate = codata.getString("CheckOutDate");
                double roomprice = Integer.parseInt(codata.getString("RoomPrice"));

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

                //OVERDUE LABEL CODE
                long overdate = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(codate));
                int overpay = (int) (overdate * roomprice);
                if (overdate >= 1) {
                    label_coOverdue.setText("RM" + String.valueOf(overpay));
                    label_coPayAmt.setTextFill(Color.web("#ff0000"));
                    btn_coCheckout.setDisable(true);
                    double Payamt = overpay - deposit;
                    label_coPayAmt.setText("RM" + String.valueOf(Payamt));
                } else {
                    btn_coCheckout.setDisable(false);
                    label_coPayAmt.setTextFill(Color.web("#000000"));
                }
                //AMOUNT TO BE PAID

                //END OF AMOUNT TO BE PAID
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
                label_coOverdue.setText("RM0.00");
                label_coPayAmt.setText("RM0.00");
            }

        });

        btn_coCheckout.setOnAction((e)->{

            try {

                DBConnection c = new DBConnection("Data.sqlite");
                String custid = tf_coIDNo.getText();
                String sql ="SELECT * FROM CheckInOut WHERE RoomNo=" +"'"+custid+"'";
                ResultSet rs = c.executeQuery(sql);

                String codate = rs.getString("CheckOutDate");
                String status= rs.getString("Status");
                if (status.equals ("checked in")) {
                 sql = "UPDATE CheckInOut SET Status ="+"'CHECKOUT'" +"WHERE CustID =" +'1';
                c.executeUpdate(sql);
                    c.closeCon();
                }
                else {
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

}

