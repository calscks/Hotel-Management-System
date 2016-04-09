package application.assets;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

class Emp {
    private SimpleIntegerProperty emp_id = new SimpleIntegerProperty();
    private SimpleStringProperty emp_uname = new SimpleStringProperty();
    private SimpleStringProperty emp_pwd = new SimpleStringProperty();
    private SimpleStringProperty emp_auth = new SimpleStringProperty();

    //these are all getters
    public Integer getEmpId(){
        return emp_id.get();
    }

    public String getEmpUname(){
        return emp_uname.get();
    }

    public String getEmpPwd(){
        return emp_pwd.get();
    }

    public String getEmpAuth(){
        return emp_auth.get();
    }

    //these are all setters
    public void setEmp_id(int emp_id) {
        this.emp_id.set(emp_id);
    }

    public void setEmp_uname(String emp_uname){
        this.emp_uname.set(emp_uname);
    }

    public void setEmp_pwd(String emp_pwd) {
        this.emp_pwd.set(emp_pwd);
    }

    public void setEmp_auth(String emp_auth) {
        this.emp_auth.set(emp_auth);
    }
}
