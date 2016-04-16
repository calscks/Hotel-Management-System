package application.assets;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ModelGroupMember {
    private SimpleStringProperty memFName = new SimpleStringProperty();
    private SimpleStringProperty memLName = new SimpleStringProperty();
    private SimpleStringProperty idType = new SimpleStringProperty();
    private SimpleStringProperty idNo = new SimpleStringProperty();
    private SimpleStringProperty gCustId = new SimpleStringProperty();
    private SimpleStringProperty icNum = new SimpleStringProperty();


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

    public SimpleStringProperty icNumProperty() {
        return icNum;
    }

    public SimpleStringProperty gCustIdProperty() {return gCustId;}


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

    public String geticNum() {
        return icNum.get();
    }

    public String getgCustId() {
        return gCustId.get();
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

    public void setgCustId(String gCustId) {
        this.gCustId.set(gCustId);
    }

    public void seticNum(String icNum) {
        this.icNum.set(icNum);
    }


}
