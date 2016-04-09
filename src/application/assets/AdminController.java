package application.assets;

import application.DBConnection;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private TableView<Emp> table_empList;
    @FXML
    private TableColumn<Emp, Integer> tbcol_id;
    @FXML
    private TableColumn<Emp, String> tbcol_uname;
    @FXML
    private TableColumn<Emp, String> tbcol_pwd;
    @FXML
    private TableColumn<Emp, String> tbcol_auth;

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
        tbcol_id.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        tbcol_uname.setCellValueFactory(new PropertyValueFactory<>("emp_uname"));
        tbcol_pwd.setCellValueFactory(new PropertyValueFactory<>("emp_pwd"));
        tbcol_auth.setCellValueFactory(new PropertyValueFactory<>("emp_auth"));
        try {
            insertData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertData() throws SQLException {
        DBConnection db = new DBConnection("Data.sqlite");
        ObservableList<Emp> data = FXCollections.observableArrayList();
        String selEmp = "SELECT * FROM Employee";
        ResultSet rs = db.executeQuery(selEmp);
        while (rs.next()){
            Emp emp = new Emp();
            emp.setEmp_id(rs.getInt(1));
            emp.setEmp_uname(rs.getString(2));
            emp.setEmp_pwd(rs.getString(3));
            emp.setEmp_auth(rs.getString(4));
            data.add(emp);
            System.out.print(emp.getEmpUname());
        }
        table_empList.setItems(data);
    }

}
