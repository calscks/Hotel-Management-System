package application;

import javafx.scene.control.Alert;

import java.sql.*;

/*
UPDATE:
A single static connection is made inside slidemenu controller. Simple use:

ResultSet anyname = db.executeQuery("your sql query") in your classes
and
(no resultset) db.executeUpdate("your sql query for create, insert, update, delete")

remember to import static connection of the slidemenu controller
 */

public class DBConnection {
    private String dbName;
    private Connection c = null;
    private Statement statement = null;

    public DBConnection(String dbName){ //this is a constructor
        this.dbName = dbName;
        try {
            setCon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCon(){
        String[] create = new String[]{
                "CREATE TABLE IF NOT EXISTS Admin\n" +
                        "(\n" +
                        "    AUName TEXT PRIMARY KEY,\n" +
                        "    APwd TEXT\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS CheckInOut\n" +
                        "(\n" +
                        "    CustID TEXT,\n" +
                        "    CheckInDate TEXT,\n" +
                        "    CheckOutDate TEXT,\n" +
                        "    Status TEXT,\n" +
                        "    CIO_ID INTEGER PRIMARY KEY,\n" +
                        "    ResvNo INTEGER\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS CustAddress\n" +
                        "(\n" +
                        "    CustID TEXT,\n" +
                        "    Address TEXT,\n" +
                        "    PostCode TEXT,\n" +
                        "    City TEXT,\n" +
                        "    State TEXT,\n" +
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
                        "    IDType TEXT,\n" +
                        "    IDNo TEXT,\n" +
                        "    G_CustID TEXT\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Employee\n" +
                        "(\n" +
                        "    EmpID INTEGER PRIMARY KEY,\n" +
                        "    EmpUName TEXT,\n" +
                        "    EmpPwd TEXT,\n" +
                        "    Authority TEXT\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS ExtPayment\n" +
                        "(\n" +
                        "    ExtPaymentID INTEGER PRIMARY KEY,\n" +
                        "    CIO_ID INTEGER,\n" +
                        "    ExtPaymentDetails TEXT,\n" +
                        "    Total REAL\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS FacBookedDate\n" +
                        "(\n" +
                        "    FacNo TEXT,\n" +
                        "    BookDate TEXT,\n" +
                        "    ResvNo INTEGER,\n" +
                        "    Comment TEXT\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS FacType\n" +
                        "(\n" +
                        "    FacNo TEXT PRIMARY KEY,\n" +
                        "    FacName TEXT,\n" +
                        "    FacDesc TEXT,\n" +
                        "    FacPrice REAL\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Pay_CCard\n" +
                        "(\n" +
                        "    CCardNo INTEGER PRIMARY KEY,\n" +
                        "    CCardName TEXT,\n" +
                        "    CVC INTEGER,\n" +
                        "    DOE_Month INTEGER,\n" +
                        "    DOE_Year INTEGER\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Payment\n" +
                        "(\n" +
                        "    PaymentID INTEGER PRIMARY KEY,\n" +
                        "    CustID TEXT,\n" +
                        "    Deposit REAL,\n" +
                        "    Subtotal REAL,\n" +
                        "    Bal REAL,\n" +
                        "    CCardNo INTEGER,\n" +
                        "    PayDate TEXT,\n" +
                        "    ResvNo INTEGER,\n" +
                        "    CIO_ID INTEGER,\n" +
                        "    ChequeNo TEXT,\n" +
                        "    Paid REAL\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Reservation\n" +
                        "(\n" +
                        "    CustID TEXT,\n" +
                        "    CheckInDate TEXT,\n" +
                        "    CheckOutDate TEXT,\n" +
                        "    ResvNo INTEGER,\n" +
                        "    ResvDate TEXT\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS Room\n" +
                        "(\n" +
                        "    RoomNo TEXT PRIMARY KEY,\n" +
                        "    RoomTypeID INTEGER\n" +
                        ");\n",
                "CREATE TABLE IF NOT EXISTS RoomBooking\n" +
                        "(\n" +
                        "    ResvNo INTEGER,\n" +
                        "    RoomNo TEXT,\n" +
                        "    ExtBedType TEXT,\n" +
                        "    Date_CI TEXT,\n" +
                        "    Date_CO TEXT,\n" +
                        "    RoomTypeName TEXT,\n" +
                        "    Price REAL\n" +
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
                        "    Rate_extKing REAL,\n" +
                        "    RoomPrice REAL\n" +
                        ");",
                "CREATE TABLE IF NOT EXISTS variables\n" +
                        "(\n" +
                        "    deposit REAL,\n" +
                        "    taxrate INTEGER\n" +
                        ");", "INSERT INTO Admin VALUES (UName = 'admin', Pwd = 'admin');"};
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
            c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            DatabaseMetaData meta = c.getMetaData();
            ResultSet chkTable = meta.getTables(null, null, "Employee", new String[]{"TABLE"});
            if (chkTable.next()) {
                System.out.print("Db exists\n");
            } else {
                System.out.print("Does not exists, creating a new db");
                for (String aCreate : create) { //loop the string[] create array
                    PreparedStatement tableQuery = c.prepareStatement(aCreate);
                    tableQuery.executeUpdate();
                }
            }
            try {
                statement = c.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    c.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException e) {
            Alert conAlert = new Alert(Alert.AlertType.ERROR);
            conAlert.setTitle("Error");
            conAlert.setHeaderText("Database Connection Problem");
            conAlert.setContentText(e.getClass().getName() + ": " + e.getMessage());
            conAlert.showAndWait();
        }

    }

    public void closeCon() {
        if (statement != null) try {
            statement.close();
            System.out.println("Statement closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (c != null) {
            try {
                c.close();
                System.out.println("con closed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public void executeUpdate(String query) throws SQLException{
        statement.executeUpdate(query);
    }
}
