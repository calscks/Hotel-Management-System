package application.assets;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Junming Tan on 16/04/2016.
 */
public class ModelCIToday {
    private SimpleStringProperty resvno = new SimpleStringProperty();
    private SimpleStringProperty fname = new SimpleStringProperty();
    private SimpleStringProperty lname = new SimpleStringProperty();
    private SimpleStringProperty checkout = new SimpleStringProperty();

    public SimpleStringProperty resvnoProperty() {
        return resvno;
    }

    public SimpleStringProperty fnameProperty() {
        return fname;
    }

    public SimpleStringProperty lnameProperty() {
        return lname;
    }

    public SimpleStringProperty checkoutProperty() {
        return checkout;
    }

    public String getresvno() {
        return resvno.get();
    }

    public String getfname() {
        return fname.get();
    }

    public String getlname() {
        return lname.get();
    }

    public String getcheckout() {return checkout.get();}

    public void setresvno(String resvno) {
        this.resvno.set(resvno);
    }

    public void setfname(String fname) {
        this.fname.set(fname);
    }

    public void setlname(String lname) {
        this.lname.set(lname);
    }

    public void setcheckout(String checkout) {this.checkout.set(checkout);}

}
