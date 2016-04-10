package application.assets;

import application.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable{
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
    private TextField tf_empPwd;
    @FXML
    private ComboBox cbox_authority;
    @FXML
    private Button btn_addEmp;
    @FXML
    private Button btn_editEmp;
    @FXML
    private Button btn_delEmp;
    @FXML
    private Button btn_switchable;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            insertData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tbcol_id.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("empId"));
        tbcol_uname.setCellValueFactory(new PropertyValueFactory<Employee, String>("empUname"));
        tbcol_pwd.setCellValueFactory(new PropertyValueFactory<Employee, String>("empPwd"));
        tbcol_auth.setCellValueFactory(new PropertyValueFactory<Employee, String>("empAuth"));

        table_empList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            System.out.println(newValue.getEmpUname());
            //i can get the username of each rows after selecting the row!!
        });

    }

    public void insertData() throws SQLException {
        DBConnection db = new DBConnection("Data.sqlite");
        ObservableList<Employee> data = FXCollections.observableArrayList();
        String selEmp = "SELECT * FROM Employee;";
        ResultSet rs = db.executeQuery(selEmp);
        while (rs.next()){
            Employee emp = new Employee();
            emp.setEmpId(rs.getInt("EmpID"));
            emp.setEmpUname(rs.getString("EmpUName"));
            emp.setEmpPwd(rs.getString("EmpPwd"));
            emp.setEmpAuth(rs.getString("Authority"));
            data.add(emp); //dunno this works or not
            System.out.println(data.toString()); //it prints the memory location of Employee class
            System.out.println(emp.getEmpId()); //confirm works, can get the username, but iterate only once when empid has 2
        }
        table_empList.setItems(data);
    }

}
