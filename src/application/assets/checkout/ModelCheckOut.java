package application.assets.checkout;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by User on 15/4/2016.
 */
public class ModelCheckOut {
    private SimpleStringProperty roomno = new SimpleStringProperty();
    private SimpleStringProperty custid = new SimpleStringProperty();
    private SimpleStringProperty firstname = new SimpleStringProperty();
    private SimpleStringProperty lastname = new SimpleStringProperty();

    public SimpleStringProperty roomnoProperty() {
        return roomno;
    }

    public SimpleStringProperty custidProperty() {
        return custid;
    }

    public SimpleStringProperty firstnameProperty() {
        return firstname;
    }

    public SimpleStringProperty lastnameProperty() {
        return lastname;
    }

    public String getRoomno() {
        return roomno.get();
    }

    public String getCustid() {
        return custid.get();
    }

    public String getFirstname() {
        return firstname.get();
    }

    public String getLastname() {
        return lastname.get();
    }

    public void setCustid(String custid) {
        this.custid.set(custid);
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public void setRoomno(String roomno) {
        this.roomno.set(roomno);
    }

}
