package application.assets;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ModelFacility {
    private SimpleStringProperty bookedfac = new SimpleStringProperty();
    private SimpleStringProperty facprice = new SimpleStringProperty();
    private SimpleStringProperty bookedfacdate = new SimpleStringProperty();
    private SimpleStringProperty bookedfactime = new SimpleStringProperty();
    private SimpleStringProperty facno = new SimpleStringProperty();
    private SimpleStringProperty facname = new SimpleStringProperty();
    private SimpleStringProperty facdesc = new SimpleStringProperty();

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
        return facno;
    }

    public SimpleStringProperty bookedfacnameProperty() {
        return facname;
    }

    public SimpleStringProperty bookedfacdescProperty() {
        return facdesc;
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
        return facno.get();
    }

    public String getfacname() {
        return facname.get();
    }

    public String getfacdesc() {
        return facdesc.get();
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
        this.facno.set(facno);
    }

    public void setfacname(String facname) {
        this.facname.set(facname);
    }

    public void setfacdesc(String facdesc) {
        this.facdesc.set(facdesc);
    }

    //if u don't understand getters and setters then ggwp
}
