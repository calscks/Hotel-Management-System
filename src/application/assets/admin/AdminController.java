package application.assets.admin;

import application.DBConnection;
import application.Main;
import application.assets.Login;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TableView<Employee> table_empList;
    @FXML
    private TableColumn<Employee, Integer> tbcol_id;
    @FXML
    private TableColumn<Employee, String> tbcol_uname;
    @FXML
    private TableColumn<Employee, String> tbcol_pwd;
    @FXML
    private TableColumn<Employee, String> tbcol_auth;

    @FXML
    private TextField tf_uname;
    @FXML
    private TextField tf_empid;
    @FXML
    private TextField tf_emppwd;
    @FXML
    private ComboBox<String> cbox_auth;
    @FXML
    private Button btn_addEmp;
    @FXML
    private Button btn_editEmp;
    @FXML
    private Button btn_delEmp;
    @FXML
    private Button btn_switchable;

    @FXML private Label lbl_logout;

    private ObservableList<String> obs = FXCollections.observableArrayList("Staff", "Manager");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbox_auth.setItems(obs);
        try {
            insertData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        buttons();

        switchable();

        logout();

        //need to do like this to set the cell value factory.
        //For example the "empId" must be presented as a property inside my Employee data model class
        //inside the <>, I have alrd defined in the TableColumn instance variables, pls check above, e.g.
        //<Employee, Integer>
        // datamodel   type
        tbcol_id.setCellValueFactory(new PropertyValueFactory<>("empId"));
        tbcol_uname.setCellValueFactory(new PropertyValueFactory<>("empUname"));
        tbcol_pwd.setCellValueFactory(new PropertyValueFactory<>("empPwd"));
        tbcol_auth.setCellValueFactory(new PropertyValueFactory<>("empAuth"));
    }

    public void insertData() throws SQLException {
        table_empList.getItems().clear();
        DBConnection db = new DBConnection("Data.sqlite");
        ObservableList<Employee> data = FXCollections.observableArrayList();
        String selEmp = "SELECT * FROM Employee;";
        ResultSet rs = db.executeQuery(selEmp);
        while (rs.next()) { //do this pls
            Employee emp = new Employee(); //create an object for Employee everytime a next occurs
            emp.setEmpId(rs.getInt("EmpID"));
            emp.setEmpUname(rs.getString("EmpUName"));
            emp.setEmpPwd(rs.getString("EmpPwd"));
            emp.setEmpAuth(rs.getString("Authority"));
            data.add(emp);
            System.out.println(data.toString()); //for debugging, it prints the memory location of Employee class
            System.out.println(emp.getEmpId()); //for debugging, confirm works, can get the username
        }
        table_empList.setItems(data);

        db.closeCon();
    }

    public void buttons() {
        btn_addEmp.setOnMouseClicked(me -> {
            tf_empid.setDisable(false);
            tf_emppwd.setDisable(false);
            tf_uname.setDisable(false);
            cbox_auth.setDisable(false);

            btn_switchable.setDisable(false);
            btn_switchable.setText("Add");
        });

        btn_editEmp.setOnMouseClicked(me -> {
            int sel = table_empList.getSelectionModel().getSelectedIndex();
            if (sel >= 0) {
                tf_empid.setDisable(true);
                tf_emppwd.setDisable(false);
                tf_uname.setDisable(false);
                cbox_auth.setDisable(false);

                btn_switchable.setDisable(false);
                btn_switchable.setText("Edit");

                Employee emp = table_empList.getSelectionModel().getSelectedItem();

                tf_empid.setText(String.valueOf(emp.getEmpId()));
                tf_uname.setText(emp.getEmpUname());
                tf_emppwd.setText(emp.getEmpPwd());
                cbox_auth.getSelectionModel().select(emp.getEmpAuth());

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Employee is Selected");
                alert.setContentText("Please select an employee in the table to be edited.");
                alert.showAndWait();
            }
        });

        btn_delEmp.setOnMouseClicked(me -> {
            int sel = table_empList.getSelectionModel().getSelectedIndex();
            if (sel >= 0) {
                Employee emp = table_empList.getSelectionModel().getSelectedItem();
                if (Objects.equals(String.valueOf(emp.getEmpId()), tf_empid.getText())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Employee under edit");
                    alert.setContentText("Employee is under editing! Please cancel the edit before " +
                            "deleting the employee.");
                    alert.showAndWait();
                    return;
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Employee");
                alert.setContentText("Are you sure you want to delete employee ID: " + emp.getEmpId() + "?");

                Optional<ButtonType> select = alert.showAndWait();
                if (select.isPresent()) {
                    if (select.get() == ButtonType.CANCEL) {
                        return;
                    }
                } else {
                    return;
                }

                try {
                    //language=SQLite
                    String query = "DELETE FROM Employee WHERE EmpID=" + emp.getEmpId();
                    DBConnection conn = new DBConnection("Data.sqlite");
                    conn.executeUpdate(query);
                    conn.closeCon();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Employee is Selected");
                alert.setContentText("Please select an employee in the table to be deleted.");
                alert.showAndWait();
            }
        });
    }

    public void switchable() {
        btn_switchable.setOnMouseClicked(me -> {
            if (Objects.equals(btn_switchable.getText(), "Add")) {

                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirmation");
                confirm.setHeaderText("Add Employee");
                confirm.setContentText("Are you sure you want to add employee with ID: "
                        + tf_empid.getText() + "?");

                Optional<ButtonType> select = confirm.showAndWait();
                if (select.isPresent()) {
                    if (select.get() == ButtonType.CANCEL) {
                        return;
                    }
                } else {
                    return;
                }

                for (Employee emp : table_empList.getItems()) {
                    if (emp.getEmpId() == Integer.parseInt(tf_empid.getText())) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("ID Found");
                        alert.setHeaderText("ID found in the table");
                        alert.setContentText("A similar ID is found in the table. Please add an employee with " +
                                "different ID instead.");
                        alert.showAndWait();
                        return;
                    } else if (Objects.equals(emp.getEmpUname(), tf_uname.getText())){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Username Found");
                        alert.setHeaderText("Username found in the table");
                        alert.setContentText("Same username is found in the table of staffs and managers" +
                                ". Please add an employee with different ID instead.");
                        alert.showAndWait();
                        return;
                    }
                }

                try {
                    //language=SQLite
                    String query = "INSERT INTO Employee VALUES ( " + Integer.parseInt(tf_empid.getText()) +
                            ",'" + tf_uname.getText() + "','" + tf_emppwd.getText() +
                            "','" + cbox_auth.getSelectionModel().getSelectedItem() + "')";
                    DBConnection conn = new DBConnection("Data.sqlite");
                    conn.executeUpdate(query);
                    conn.closeCon();

                    insertData();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (Objects.equals(btn_switchable.getText(), "Edit")) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Employee");
                alert.setContentText("Are you sure you want to edit employee with ID: "
                        + tf_empid.getText() + "?");

                Optional<ButtonType> select = alert.showAndWait();
                if (select.isPresent()) {
                    if (select.get() == ButtonType.CANCEL) {
                        return;
                    }
                } else {
                    return;
                }
                //language=SQLite
                String query = "UPDATE Employee SET EmpUName = '" + tf_uname.getText() +
                        "', EmpPwd = '" + tf_emppwd.getText() +
                        "', Authority = '" + cbox_auth.getSelectionModel().getSelectedItem() +
                        "'";
                DBConnection conn = new DBConnection("Data.sqlite");
                try {
                    conn.executeUpdate(query);

                    conn.closeCon();
                    insertData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void logout(){
        lbl_logout.setOnMouseClicked(me->{
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Logout");
            confirm.setHeaderText("Logout");
            confirm.setContentText("Are you sure to log out?");

            Optional<ButtonType> select = confirm.showAndWait();
            if (select.isPresent()) {
                if (select.get() == ButtonType.CANCEL) {
                    return;
                }
            } else {
                return;
            }

            Stage stage = (Stage) table_empList.getScene().getWindow();
            stage.close();
            new Login();

        });
    }

}
