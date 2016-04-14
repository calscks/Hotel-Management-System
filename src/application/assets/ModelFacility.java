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

    public SimpleStringProperty modfacnoProperty() {
        return modfacno;
    }

    public SimpleStringProperty modfacnameProperty() {
        return modfacname;
    }

    public SimpleStringProperty modfacdescProperty() {
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

    public String getfacno() {return modfacno.get();}

    public String getfacname() {
        return modfacname.get();
    }

    public String getfacdesc() {
        return modfacdesc.get();
    }


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

    public void setfacno(String modfacno) {
        this.modfacno.set(modfacno);
    }

    public void setfacname(String modfacname) {
        this.modfacname.set(modfacname);
    }

    public void setfacdesc(String modfacdesc) {
        this.modfacdesc.set(modfacdesc);
    }

}
