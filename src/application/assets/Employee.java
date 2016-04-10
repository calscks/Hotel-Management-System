package application.assets;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee {
    private SimpleIntegerProperty empId = new SimpleIntegerProperty();
    private SimpleStringProperty empUname = new SimpleStringProperty();
    private SimpleStringProperty empPwd = new SimpleStringProperty();
    private SimpleStringProperty empAuth = new SimpleStringProperty();


    public SimpleIntegerProperty empIdProperty() {
        return empId;
    }

    public SimpleStringProperty empAuthProperty() {
        return empAuth;
    }

    public SimpleStringProperty empPwdProperty() {
        return empPwd;
    }

    public SimpleStringProperty empUnameProperty() {
        return empUname;
    }

    public String getEmpAuth() {
        return empAuth.get();
    }

    public int getEmpId() {
        return empId.get();
    }

    public String getEmpPwd() {
        return empPwd.get();
    }

    public String getEmpUname() {
        return empUname.get();
    }

    public void setEmpId(int empId) {
        this.empId.set(empId);
    }

    public void setEmpUname(String empUname){
        this.empUname.set(empUname);
    }

    public void setEmpPwd(String empPwd) {
        this.empPwd.set(empPwd);
    }

    public void setEmpAuth(String empAuth) {
        this.empAuth.set(empAuth);
    }
}
