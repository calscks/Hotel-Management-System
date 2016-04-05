package application;

import application.assets.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {
    @Override
    public void start(Stage P) throws Exception {
        createTable();
        P.setTitle("Hotel Management System");
        P.setScene(this.newScene());
        P.show();
    }

    private Scene newScene() {
        return new Scene(new Login().loginGrid());
        /**interpreted as:
         * Scene x = new Scene(new Login().loginGrid());
         * return x;
         */
    }


    private void createTable() {
        Connection c = null;
        PreparedStatement tableQuery;
        String[] create;
        create = new String[]{"CREATE TABLE IF NOT EXISTS Admin\n" +
                "(\n" +
                "    AUName TEXT PRIMARY KEY,\n" +
                "    APwd TEXT\n" +
                ");\n",
                "CREATE TABLE IF NOT EXISTS CheckInOut\n" +
                        "(\n" +
                        "    CustID INTEGER,\n" +
                        "    CheckInDate TEXT,\n" +
                        "    CheckOutDate TEXT,\n" +
                        "    Status TEXT,\n" +
                        "    CIO_ID INTEGER PRIMARY KEY,\n" +
                        "    RoomNo TEXT\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS CustAddress\n" +
                        "(\n" +
                        "    CustID INTEGER PRIMARY KEY,\n" +
                        "    Address TEXT,\n" +
                        "    PostCode TEXT,\n" +
                        "    City TEXT,\n" +
                        "    Country TEXT\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Customer\n" +
                        "(\n" +
                        "    CustID TEXT PRIMARY KEY,\n" +
                        "    CustID_Type TEXT,\n" +
                        "    CustFName TEXT,\n" +
                        "    CustLName TEXT,\n" +
                        "    Blacklisted TEXT\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS CustomerGroup\n" +
                        "(\n" +
                        "    CustFName TEXT,\n" +
                        "    CustLName TEXT,\n" +
                        "    PassportNo TEXT PRIMARY KEY,\n" +
                        "    G_CustID INTEGER,\n" +
                        "    ICNo TEXT\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Employee\n" +
                        "(\n" +
                        "    EmpID INTEGER PRIMARY KEY,\n" +
                        "    EmpUName TEXT,\n" +
                        "    EmpPwd TEXT,\n" +
                        "    Authority TEXT\n" +
                        ");\n",
                "CREATE UNIQUE INDEX IF NOT EXISTS Employee_EmpID_uindex ON Employee (EmpID);\n",
                "CREATE UNIQUE INDEX IF NOT EXISTS Employee_EmpUname_uindex ON Employee (EmpUName);\n",
                "CREATE TABLE IF NOT EXISTS ExtPayList\n" +
                        "(\n" +
                        "    ID INTEGER,\n" +
                        "    ExtPaymentDetails TEXT,\n" +
                        "    Price REAL\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS ExtPayment\n" +
                        "(\n" +
                        "    ExtPaymentID INTEGER PRIMARY KEY,\n" +
                        "    CIO_ID INTEGER,\n" +
                        "    ExtPayListID INTEGER,\n" +
                        "    Total REAL\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS FacBookedDate\n" +
                        "(\n" +
                        "    FacNo TEXT,\n" +
                        "    Date TEXT,\n" +
                        "    Morning INTEGER,\n" +
                        "    Night INTEGER,\n" +
                        "    WholeDay INTEGER,\n" +
                        "    ResvNo INTEGER\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS FacType\n" +
                        "(\n" +
                        "    FacNo TEXT PRIMARY KEY,\n" +
                        "    FacName TEXT,\n" +
                        "    FacDesc TEXT,\n" +
                        "    FacPrice REAL\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Invoice\n" +
                        "(\n" +
                        "    InvoiceID INTEGER PRIMARY KEY,\n" +
                        "    CustID INTEGER,\n" +
                        "    Deposit REAL,\n" +
                        "    DiscountID TEXT,\n" +
                        "    Total REAL,\n" +
                        "    InvoiceDate TEXT,\n" +
                        "    IsPaid TEXT\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Pay_CCard\n" +
                        "(\n" +
                        "    CCardNo INTEGER PRIMARY KEY,\n" +
                        "    CCardName TEXT,\n" +
                        "    CVC INTEGER,\n" +
                        "    DOE_Month INTEGER,\n" +
                        "    DOE_Year INTEGER\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Pay_Discount\n" +
                        "(\n" +
                        "    DiscountID TEXT PRIMARY KEY,\n" +
                        "    DisDesc TEXT,\n" +
                        "    DisAmount REAL,\n" +
                        "    DisPercentage REAL\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Payment\n" +
                        "(\n" +
                        "    PaymentID INTEGER PRIMARY KEY,\n" +
                        "    CustID INTEGER,\n" +
                        "    Deposit REAL,\n" +
                        "    Total REAL,\n" +
                        "    Bal_Deposit REAL,\n" +
                        "    Bal_Total REAL,\n" +
                        "    CCardNo INTEGER,\n" +
                        "    DiscountID TEXT,\n" +
                        "    PayDate TEXT\n" +
                        "    ResvNo INTEGER,\n" +
                        "    CIO_ID INTEGER" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Reservation\n" +
                        "(\n" +
                        "    CustID INTEGER PRIMARY KEY,\n" +
                        "    CheckInDate TEXT,\n" +
                        "    CheckOutDate TEXT,\n" +
                        "    ResvNo INTEGER\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Room\n" +
                        "(\n" +
                        "    RoomNo TEXT,\n" +
                        "    RoomTypeID INTEGER\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS RoomAvailability\n" +
                        "(\n" +
                        "    RoomNo TEXT,\n" +
                        "    Date_CI TEXT,\n" +
                        "    Date_CO TEXT\n" +
                        ");",
                "CREATE TABLE IF NOT EXISTS RoomBooking\n" +
                        "(\n" +
                        "    ResvNo INTEGER,\n" +
                        "    RoomNo TEXT\n" +
                        ");",
                "CREATE TABLE IF NOT EXISTS RoomType\n" +
                        "(\n" +
                        "    TypeID INTEGER PRIMARY KEY,\n" +
                        "    TypeGroup TEXT,\n" +
                        "    TypeName TEXT,\n" +
                        "    MaxPax INTEGER,\n" +
                        "    Rate_extTwin REAL,\n" +
                        "    Rate_extFull REAL,\n" +
                        "    Rate_extQueen REAL,\n" +
                        "    Rate_extKing REAL\n" +
                        ");",
                "CREATE TABLE IF NOT EXISTS variables\n" +
                        "(\n" +
                        "    deposit REAL,\n" +
                        "    taxrate REAL\n" +
                        ");"}; //each comma separates between values in the
        // array in case you didn't notice
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
            c = DriverManager.getConnection("jdbc:sqlite:Data.sqlite");
            DatabaseMetaData meta = c.getMetaData();
            ResultSet chkTable = meta.getTables(null, null, "Employee", new String[]{"TABLE"});
            if (chkTable.next()) {
                System.out.print("Db exists");
            } else {
                System.out.print("Does not exists, creating a new db");
                for (String aCreate : create) {
                    tableQuery = c.prepareStatement(aCreate);
                    tableQuery.executeUpdate();
                }
            }
        } catch (Exception e) {
            Alert conAlert = new Alert(Alert.AlertType.ERROR);
            conAlert.setTitle("Error");
            conAlert.setHeaderText("Database Connection Problem");
            conAlert.setContentText(e.getClass().getName() + ": " + e.getMessage());
            conAlert.showAndWait();
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }//please don't care this main method
}
