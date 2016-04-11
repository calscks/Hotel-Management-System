package application.assets.checkin;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Junming Tan on 10/04/2016.
 */
public class Room {
    private SimpleStringProperty roomno = new SimpleStringProperty();
    private SimpleStringProperty rtype = new SimpleStringProperty();
    private SimpleStringProperty cidate = new SimpleStringProperty();
    private SimpleStringProperty codate = new SimpleStringProperty();

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

    //these setters are important to set database column value into each properties of this data model class

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

    //if u don't understand getters and setters then ggwp
}

