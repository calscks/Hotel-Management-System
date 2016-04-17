package application.assets.records;

import javafx.beans.property.SimpleStringProperty;

public class ModelSResult {
    private SimpleStringProperty sCioid = new SimpleStringProperty();
    private SimpleStringProperty sResvNo = new SimpleStringProperty();
    private SimpleStringProperty sFname = new SimpleStringProperty();
    private SimpleStringProperty sLname = new SimpleStringProperty();
    private SimpleStringProperty sStatus = new SimpleStringProperty();

    public void setsCioid(String sCioid) {
        this.sCioid.set(sCioid);
    }

    public void setsFname(String sFname) {
        this.sFname.set(sFname);
    }

    public void setsLname(String sLname) {
        this.sLname.set(sLname);
    }

    public void setsResvNo(String sResvNo) {
        this.sResvNo.set(sResvNo);
    }

    public void setsStatus(String sStatus) {
        this.sStatus.set(sStatus);
    }

    public SimpleStringProperty sCioidProperty() {
        return sCioid;
    }

    public SimpleStringProperty sFnameProperty() {
        return sFname;
    }

    public SimpleStringProperty sLnameProperty() {
        return sLname;
    }

    public SimpleStringProperty sResvNoProperty() {
        return sResvNo;
    }

    public SimpleStringProperty sStatusProperty() {
        return sStatus;
    }

    public String getsCioid() {
        return sCioid.get();
    }

    public String getsFname() {
        return sFname.get();
    }

    public String getsLname() {
        return sLname.get();
    }

    public String getsResvNo() {
        return sResvNo.get();
    }

    public String getsStatus() {
        return sStatus.get();
    }
}
