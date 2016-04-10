package application.assets.admin;

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
        //need to do like this to set the cell value factory.
        //For example the "empId" must be presented as a property inside my Employee data model class
        //inside the <>, I have alrd defined in the TableColumn instance variables, pls check above, e.g.
        //<Employee, Integer>
        // datamodel   type
        tbcol_id.setCellValueFactory(new PropertyValueFactory<>("empId"));
        tbcol_uname.setCellValueFactory(new PropertyValueFactory<>("empUname"));
        tbcol_pwd.setCellValueFactory(new PropertyValueFactory<>("empPwd"));
        tbcol_auth.setCellValueFactory(new PropertyValueFactory<>("empAuth"));
        table_empList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            System.out.println(newValue.getEmpUname());
            //for debugging purpose, can get the value
        });

        table_empList.setRowFactory(tv -> {
            TableRow<Employee> selRow = new TableRow<>();
            selRow.setOnMouseClicked(me -> {
                if (me.getClickCount() == 2 && (!selRow.isEmpty())){ //detects double-click!!
                    Employee empdata = table_empList.getSelectionModel().getSelectedItem();
                    System.out.print(empdata.getEmpId()); //after double-click can print!
                }
            });
            return selRow; //have to return for setRowFactory
        });


    }

    public void insertData() throws SQLException {
        DBConnection db = new DBConnection("Data.sqlite");
        ObservableList<Employee> data = FXCollections.observableArrayList();
        String selEmp = "SELECT * FROM Employee;";
        ResultSet rs = db.executeQuery(selEmp);
        while (rs.next()){ //do this pls
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
    }

}
