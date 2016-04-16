package application.assets;
import javafx.beans.property.SimpleStringProperty;

public class ModelResvSearch {
    private SimpleStringProperty resvNo = new SimpleStringProperty();
    private SimpleStringProperty fName = new SimpleStringProperty();
    private SimpleStringProperty lName = new SimpleStringProperty();

    public SimpleStringProperty resvNoProperty() {
        return resvNo;
    }

    public SimpleStringProperty fNameProperty() {
        return fName;
    }

    public SimpleStringProperty lNameProperty() {
        return lName;
    }

    public String getResvNo() {
        return resvNo.get();
    }

    public String getfName() {
        return fName.get();
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


}
