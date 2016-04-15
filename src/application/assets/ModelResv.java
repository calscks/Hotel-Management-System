package application.assets;
import javafx.beans.property.SimpleStringProperty;

public class ModelResv {
    private SimpleStringProperty resvNo = new SimpleStringProperty();
    private SimpleStringProperty fName = new SimpleStringProperty();
    private SimpleStringProperty lName = new SimpleStringProperty();
    private SimpleStringProperty roomNo = new SimpleStringProperty();

    public SimpleStringProperty resvNoProperty() {
        return resvNo;
    }

    public SimpleStringProperty fNameProperty() {
        return fName;
    }

    public SimpleStringProperty lNameProperty() {
        return lName;
    }

    public SimpleStringProperty roomNoProperty() {
        return roomNo;
    }

    public String getResvNo() {
        return resvNo.get();
    }

    public String getfName() {
        return fName.get();
    }

    public String getRoomNo() {
        return roomNo.get();
    }

    public String getlName() {
        return lName.get();
    }

    public void setResvNo(String resvNo) {
        this.resvNo.set(resvNo);
    }

    public void setfName(String fName) {
        this.fName.set(fName);
    }

    public void setlName(String lName) {
        this.lName.set(lName);
    }

    public void setRoomNo(String roomNo) {this.roomNo.set(roomNo);}


}
