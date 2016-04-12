package application.assets;
import javafx.beans.property.SimpleStringProperty;

public class ModelRoom {
    private SimpleStringProperty roomcat = new SimpleStringProperty();
    private SimpleStringProperty roomno = new SimpleStringProperty();
    private SimpleStringProperty rtype = new SimpleStringProperty();
    private SimpleStringProperty cidate = new SimpleStringProperty();
    private SimpleStringProperty codate = new SimpleStringProperty();
    private SimpleStringProperty extbedtype = new SimpleStringProperty();
    private SimpleStringProperty roomprice = new SimpleStringProperty();

    public SimpleStringProperty roomcatProperty() {
        return roomcat;
    }

    public SimpleStringProperty roomnoProperty() {
        return roomno;
    }

    public SimpleStringProperty rtypeProperty() {
        return rtype;
    }

    public SimpleStringProperty cidateProperty() {
        return cidate;
    }

    public SimpleStringProperty codateProperty() { return codate;}

    public SimpleStringProperty extbedtypeProperty() {
        return extbedtype;
    }

    public SimpleStringProperty roompriceProperty() {
        return roomprice;
    }

    public String getRoomcat() {
        return roomcat.get();
    }

    public String getRoomno() {
        return roomno.get();
    }

    public String getRtype() {
        return rtype.get();
    }

    public String getCidate() {
        return cidate.get();
    }

    public String getCodate() {
        return codate.get();
    }

    public String getExtbedtype() {
        return extbedtype.get();
    }

    public String getRoomprice() {
        return roomprice.get();
    }

    //these setters are important to set database column value into each properties of this data model class


    public void setRoomcat(String roomcat) {
        this.roomcat.set(roomcat);
    }

    public void setRoomno(String roomno) {
        this.roomno.set(roomno);
    }

    public void setRtype(String rtype){
        this.rtype.set(rtype);
    }

    public void setCidate(String cidate) {
        this.cidate.set(cidate);
    }

    public void setCodate(String codate) {
        this.codate.set(codate);
    }

    public void setExtbedtype(String extbedtype) {
        this.extbedtype.set(extbedtype);
    }

    public void setRoomprice(String roomprice) {
        this.roomprice.set(roomprice);
    }

    //if u don't understand getters and setters then ggwp
}

