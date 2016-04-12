package application.assets;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ModelFacility {
    private SimpleStringProperty bookedfac = new SimpleStringProperty();
    private SimpleStringProperty facprice = new SimpleStringProperty();
    private SimpleStringProperty bookedfacdate = new SimpleStringProperty();
    private SimpleStringProperty bookedfactime = new SimpleStringProperty();
    private SimpleStringProperty modfacno = new SimpleStringProperty();
    private SimpleStringProperty modfacname = new SimpleStringProperty();
    private SimpleStringProperty modfacdesc = new SimpleStringProperty();

    public SimpleStringProperty bookedfacProperty() {
        return bookedfac;
    }

    public SimpleStringProperty facpriceProperty() {
        return facprice;
    }

    public SimpleStringProperty bookedfacdateProperty() {
        return bookedfacdate;
    }

    public SimpleStringProperty bookedfactimeProperty() {
        return bookedfactime;
    }

    public SimpleStringProperty bookedfacnoProperty() {
        return modfacno;
    }

    public SimpleStringProperty bookedfacnameProperty() {
        return modfacname;
    }

    public SimpleStringProperty bookedfacdescProperty() {
        return modfacdesc;
    }

    public String getbookedfac() {
        return bookedfac.get();
    }

    public String getfacprice() {
        return facprice.get();
    }

    public String getbookedfacdate() {
        return bookedfacdate.get();
    }

    public String getbookedfactime() {
        return bookedfactime.get();
    }

    public String getfacno() {
        return modfacno.get();
    }

    public String getfacname() {
        return modfacname.get();
    }

    public String getfacdesc() {
        return modfacdesc.get();
    }

    //these setters are important to set database column value into each properties of this data model class


    public void setbookedfac(String bookedfac) {
        this.bookedfac.set(bookedfac);
    }

    public void setfacprice(String facprice) {
        this.facprice.set(facprice);
    }

    public void setbookedfacdate(String bookedfacdate){
        this.bookedfacdate.set(bookedfacdate);
    }

    public void setbookedfactime(String bookedfactime) {
        this.bookedfactime.set(bookedfactime);
    }

    public void setfacno(String facno) {
        this.modfacno.set(facno);
    }

    public void setfacname(String facname) {
        this.modfacname.set(facname);
    }

    public void setfacdesc(String facdesc) {
        this.modfacdesc.set(facdesc);
    }

    //if u don't understand getters and setters then ggwp
}
