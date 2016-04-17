package application.assets.records;

import javafx.beans.property.SimpleStringProperty;

public class ModelPayment {
    private SimpleStringProperty refno = new SimpleStringProperty();
    private SimpleStringProperty paycustid = new SimpleStringProperty();
    private SimpleStringProperty fname = new SimpleStringProperty();
    private SimpleStringProperty lname = new SimpleStringProperty();
    private SimpleStringProperty dep = new SimpleStringProperty();
    private SimpleStringProperty subtotal = new SimpleStringProperty();
    private SimpleStringProperty paid = new SimpleStringProperty();
    private SimpleStringProperty bal = new SimpleStringProperty();
    private SimpleStringProperty payresvno = new SimpleStringProperty();
    private SimpleStringProperty cioid = new SimpleStringProperty();

    public SimpleStringProperty balProperty() {
        return bal;
    }

    public SimpleStringProperty cioidProperty() {
        return cioid;
    }

    public SimpleStringProperty paycustidProperty() {
        return paycustid;
    }

    public SimpleStringProperty depProperty() {
        return dep;
    }

    public SimpleStringProperty fnameProperty() {
        return fname;
    }

    public SimpleStringProperty lnameProperty() {
        return lname;
    }

    public SimpleStringProperty paidProperty() {
        return paid;
    }

    public SimpleStringProperty refnoProperty() {
        return refno;
    }

    public SimpleStringProperty payresvnoProperty() {
        return payresvno;
    }

    public SimpleStringProperty subtotalProperty() {
        return subtotal;
    }

    public void setPaycustid(String custid) {
        this.paycustid.set(custid);
    }

    public void setBal(String bal) {
        this.bal.set(bal);
    }

    public void setCioid(String cioid) {
        this.cioid.set(cioid);
    }

    public void setDep(String dep) {
        this.dep.set(dep);
    }

    public void setFname(String fname) {
        this.fname.set(fname);
    }

    public void setLname(String lname) {
        this.lname.set(lname);
    }

    public void setPaid(String paid) {
        this.paid.set(paid);
    }

    public void setRefno(String refno) {
        this.refno.set(refno);
    }

    public void setPayresvno(String resvno) {
        this.payresvno.set(resvno);
    }

    public void setSubtotal(String subtotal) {
        this.subtotal.set(subtotal);
    }

    public String getDep() {
        return dep.get();
    }

    public String getFname() {
        return fname.get();
    }

    public String getBal() {
        return bal.get();
    }

    public String getCioid() {
        return cioid.get();
    }

    public String getLname() {
        return lname.get();
    }

    public String getPaid() {
        return paid.get();
    }

    public String getPaycustid() {
        return paycustid.get();
    }

    public String getPayresvno() {
        return payresvno.get();
    }

    public String getRefno() {
        return refno.get();
    }

    public String getSubtotal() {
        return subtotal.get();
    }
}
