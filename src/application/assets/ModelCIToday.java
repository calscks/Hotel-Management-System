package application.assets;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Junming Tan on 16/04/2016.
 */
public class ModelCIToday {
    private SimpleStringProperty resv = new SimpleStringProperty();
    private SimpleStringProperty fname = new SimpleStringProperty();
    private SimpleStringProperty lname = new SimpleStringProperty();
    private SimpleStringProperty co = new SimpleStringProperty();

    public SimpleStringProperty resvProperty() {
        return resv;
    }

    public SimpleStringProperty lnameProperty() {
        return lname;
    }
    public SimpleStringProperty fnameProperty() {
        return fname;
    }


    public SimpleStringProperty coProperty() {
        return co;
    }

    public String getresv() {
        return resv.get();
    }

    public String getfname() {
        return fname.get();
    }

    public String getlname() {
        return lname.get();
    }

    public String getco() {return co.get();}

    public void setresv(String resv) {
        this.resv.set(resv);
    }

    public void setfname(String fname) {
        this.fname.set(fname);
    }

    public void setlname(String lname) {
        this.lname.set(lname);
    }

    public void setco(String co) {this.co.set(co);}

}
