package application.assets.RnFManagement;

import application.DBConnection;
import application.Validation;
import application.assets.ModelFacility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import javax.activation.CommandObject;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by User on 29/3/2016.
 */

public class ModFacController implements Initializable{

    ObservableList<String> selectbyitems = FXCollections.observableArrayList("FacilityID","FacilityName");

    @FXML private TextField tf_searchby;
    @FXML private TextField tf_facno;
    @FXML private TextField tf_facname;
    @FXML private TextArea ta_facdesc;
    @FXML private TextField tf_morning;
    @FXML private TextField tf_night;
    @FXML private TextField tf_wholeday;
    @FXML private Label lbl_cboxselection;
    @FXML private ComboBox cbox_searchby;
    @FXML private TableView<ModelFacility> modfactable;
    @FXML private TableColumn<ModelFacility, String> tb_facid;
    @FXML private TableColumn<ModelFacility, String> tb_facname;
    @FXML private TableColumn<ModelFacility, String> tb_facdesc;

    @FXML
    private void labelchange(){
        if (cbox_searchby.getValue().equals("FacilityID")){
            lbl_cboxselection.setText("FacilityID :");
        }
        else{
            lbl_cboxselection.setText("FacilityName :");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validation();

        cbox_searchby.setItems(selectbyitems);

        DBConnection c = new DBConnection("Data.sqlite");
        tf_searchby.textProperty().addListener((observable, oldValue,newValue)-> {
            if (lbl_cboxselection.getText().equals("FacilityID :")){
                try {
                    String sql = "select * from FacType" +
                            "where facno=" +tf_searchby.getText();
                    ResultSet data = c.executeQuery(sql);
                    ObservableList<ModelFacility> ftable = FXCollections.observableArrayList();

                    String facname = data.getString("FacName");
                    String facdesc = data.getString("FacDesc");
                    String facmornprice = data.getString("FacMornPrice");
                    String facnightprice = data.getString("FacNightPrice");
                    String facwholeprice = data.getString("FacWholePrice");

                    tf_facname.setText(facname);
                    ta_facdesc.setText(facdesc);
                    tf_morning.setText(facmornprice);
                    tf_night.setText(facnightprice);
                    tf_wholeday.setText(facwholeprice);
                    while (data.next()){
                        ModelFacility fac = new ModelFacility();
                        fac.setfacno(data.getString("facno"));
                        fac.setfacname(data.getString("facname"));
                        fac.setfacdesc(data.getString("facdesc"));
                        ftable.add(fac);

                    }
                    tb_facid.setCellValueFactory(new PropertyValueFactory<>("facno"));
                    tb_facname.setCellValueFactory(new PropertyValueFactory<>("facname"));
                    tb_facdesc.setCellValueFactory(new PropertyValueFactory<>("facdesc"));
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        });

    }

    private void validation() {
        tf_searchby.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_facno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_facname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_morning.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_night.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
        tf_wholeday.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
    }
}
