package application.assets.admin;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * <p>This class is a data model class! This data model class is used for employee's table inside admin menu. <br>
 * A data model for a tableview needs to have TypeProperty(), e.g. <code>SimpleStringProperty()</code>. <br>
 * I recommend SimpleTypeProperty because it's "simple".
 * </p>
 */

public class Employee {
    //defining properties we'll need in the admin table later (can considered as a data model for each columns)
    private SimpleIntegerProperty empId = new SimpleIntegerProperty();
    private SimpleStringProperty empUname = new SimpleStringProperty();
    private SimpleStringProperty empPwd = new SimpleStringProperty();
    private SimpleStringProperty empAuth = new SimpleStringProperty();

    //basically these returning properties let my admin table to observe the properties of this data model
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

    //these getters are used if my above property accessors are not present, and also i can do validation +
    //debugging with these gets!

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

    //these setters are important to set database column value into each properties of this data model class

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

    //if u don't understand getters and setters then ggwp
}
