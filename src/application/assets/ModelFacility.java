package application.assets;
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

    public SimpleStringProperty facnoProperty() {
        return facno;
    }

    public SimpleStringProperty facnameProperty() {
        return facname;
    }

    public SimpleStringProperty facdescProperty() {
        return facdesc;
    }

    public String getBookedfac() {
        return bookedfac.get();
    }

    public String getBookedfacdate() {
        return bookedfacdate.get();
    }

    public String getBookedfactime() {
        return bookedfactime.get();
    }

    public String getFacno() {
        return facno.get();
    }

    public String getFacname() {
        return facname.get();
    }

    public String getFacdesc() {
        return facdesc.get();
    }

    public String getFacprice() {
        return facprice.get();
    }

    public void setBookedfac(String bookedfac) {
        this.bookedfac.set(bookedfac);
    }

    public void setBookedfacdate(String bookedfacdate) {
        this.bookedfacdate.set(bookedfacdate);
    }

    public void setBookedfactime(String bookedfactime) {
        this.bookedfactime.set(bookedfactime);
    }

    public void setFacdesc(String facdesc) {
        this.facdesc.set(facdesc);
    }

    public void setFacname(String facname) {
        this.facname.set(facname);
    }

    public void setFacno(String facno) {
        this.facno.set(facno);
    }

    public void setFacprice(String facprice) {
        this.facprice.set(facprice);
    }
}
