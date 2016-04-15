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
import java.util.Date;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;


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
    @FXML  private Label label_coPayAmt;

    @FXML  private ComboBox<String> cbox_coCountry;
    @FXML  private ComboBox<String> cbox_coIDType;

    @FXML private Button btn_coCheckout;

    @FXML private TableView<ModelCheckOut> table_co;
    @FXML private TableColumn<ModelCheckOut,String> table_coRoomNo;
    @FXML private TableColumn<ModelCheckOut,String> table_coCustID;
    @FXML private TableColumn<ModelCheckOut,String> table_coFname;
    @FXML private TableColumn<ModelCheckOut,String> table_coLname;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filltb();
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
                if (status.equals("checked in")) {
                    sql = "UPDATE CheckInOut SET Status =" + "'CHECKOUT'" + "WHERE CustID =" + custid;
                    db.executeUpdate(sql);

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
    public void filltb(){
        try {
        String sql =" SELECT RoomNo, CustID,Customer.CustFName,Customer.CustLName FROM CheckInOut\n" +
                "INNER JOIN Customer USING (CustID)\n" +
                "WHERE CheckInOut.CheckOutDate ="+"'"+ LocalDate.now().toString() +"'" +"AND CheckInOut.Status= 'checked in'";
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
            btn_coCheckout.setDisable(false);

            //OVERDUE LABEL CODE
            long overdate = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(codate));
            int overpay = (int) (overdate * roomprice);
            if (overdate >= 1) {
                label_coOverdue.setText("RM" + String.valueOf(overpay));
                label_coPayAmt.setTextFill(Color.web("#ff0000"));

                double Payamt = overpay - deposit;
                label_coPayAmt.setText("RM" + String.valueOf(Payamt));
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
            label_coOverdue.setText("RM0.00");
            label_coPayAmt.setText("RM0.00");
        }
    }

}



