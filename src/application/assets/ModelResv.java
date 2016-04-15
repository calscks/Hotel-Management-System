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

    public String getresvNo() {
        return resvNo.get();
    }

    public String getfName() {
        return fName.get();
    }

    public String getlName() {
        return lName.get();
    }

    public String getroomNo() {return roomNo.get();}

    public void setresvNo(String resvNo) {
        this.resvNo.set(resvNo);
    }

    public void setfName(String fName) {
        this.fName.set(fName);
    }

    public void setlName(String lName) {
        this.lName.set(lName);
    }

    public void setroomNo(String roomNo) {this.roomNo.set(roomNo);}

}
