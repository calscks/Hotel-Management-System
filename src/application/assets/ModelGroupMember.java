package application.assets;

import javafx.beans.property.SimpleStringProperty;

public class ModelGroupMember {
    private SimpleStringProperty memFName = new SimpleStringProperty();
    private SimpleStringProperty memLName = new SimpleStringProperty();
    private SimpleStringProperty idType = new SimpleStringProperty();
    private SimpleStringProperty idNo = new SimpleStringProperty();
    private SimpleStringProperty roomNo = new SimpleStringProperty();

    public SimpleStringProperty memFNameProperty() {
        return memFName;
    }

    public SimpleStringProperty memLNameProperty() {
        return memLName;
    }

    public SimpleStringProperty idTypeProperty() {
        return idType;
    }

    public SimpleStringProperty idNoProperty() {
        return idNo;
    }

    public SimpleStringProperty roomNoProperty() {
        return roomNo;
    }

    public String getMemFName() {
        return memFName.get();
    }

    public String getMemLName() {
        return memLName.get();
    }

    public String getIdType() {
        return idType.get();
    }

    public String getIdNo() {
        return idNo.get();
    }

    public String getRoomNo() {
        return roomNo.get();
    }

    public void setMemFName(String memFName) {
        this.memFName.set(memFName);
    }

    public void setMemLName(String memLName) {
        this.memLName.set(memLName);
    }

    public void setIdType(String idType) {
        this.idType.set(idType);
    }

    public void setIdNo(String idNo) {
        this.idNo.set(idNo);
    }

    public void setRoomNo(String roomNo) {
        this.roomNo.set(roomNo);
    }
}
