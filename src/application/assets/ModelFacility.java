package application.assets;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ModelFacility {
    private SimpleStringProperty bookedfac = new SimpleStringProperty();
    private SimpleStringProperty facprice = new SimpleStringProperty();
    private SimpleStringProperty bookedfacdate = new SimpleStringProperty();
    private SimpleIntegerProperty bookedfactime = new SimpleIntegerProperty();

    public SimpleStringProperty bookedfacProperty() {
        return bookedfac;
    }

    public SimpleStringProperty facnameProperty() {
        return facprice;
    }

    public SimpleStringProperty bookedfacdateProperty() {
        return bookedfacdate;
    }

    public SimpleIntegerProperty bookedfactimeProperty() {
        return bookedfactime;
    }

    public String getbookedfac() {
        return bookedfac.get();
    }

    public String getfacname() {
        return facprice.get();
    }

    public String getbookedfacdate() {
        return bookedfacdate.get();
    }

    public Integer getbookedfactime() {
        return bookedfactime.get();
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

    public void setbookedfactime(Integer bookedfactime) {
        this.bookedfactime.set(bookedfactime);
    }


    //if u don't understand getters and setters then ggwp
}
