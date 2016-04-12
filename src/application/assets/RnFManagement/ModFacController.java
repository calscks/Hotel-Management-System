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
                    String sql = "select * from FacType ft " +
                            "where ft.facno=" +tf_searchby.getText();
                    ResultSet data = c.executeQuery(sql);
                    ObservableList<ModelFacility> ftable = FXCollections.observableArrayList();

                    String modfacno = data.getString("FacNo");
                    String modfacname = data.getString("FacName");
                    String modfacdesc = data.getString("FacDesc");
                    String modfacmornprice = data.getString("FacMornPrice");
                    String modfacnightprice = data.getString("FacNightPrice");
                    String modfacwholeprice = data.getString("FacWholePrice");

                    tf_facno.setText(modfacno);
                    tf_facname.setText(modfacname);
                    ta_facdesc.setText(modfacdesc);
                    tf_morning.setText(modfacmornprice);
                    tf_night.setText(modfacnightprice);
                    tf_wholeday.setText(modfacwholeprice);
                    while (data.next()){
                        ModelFacility fac = new ModelFacility();
                        fac.setfacno(data.getString("FacNo"));
                        fac.setfacname(data.getString("FacName"));
                        fac.setfacdesc(data.getString("FacDesc"));
                        ftable.add(fac);
                        System.out.println(fac.getfacno());

                    }
                    tb_facid.setCellValueFactory(new PropertyValueFactory<>("modfacno"));
                    tb_facname.setCellValueFactory(new PropertyValueFactory<>("modfacname"));
                    tb_facdesc.setCellValueFactory(new PropertyValueFactory<>("modfacdesc"));
                    modfactable.setItems(ftable);
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
            /*else if (lbl_cboxselection.getText().equals("FacilityName :")){
                try {
                    String sql = "select * from FacType ft " +
                            "where ft.facname=" +tf_searchby.getText();
                    ResultSet data2 = c.executeQuery(sql);
                    ObservableList<ModelFacility> ftable = FXCollections.observableArrayList();

                    String modfacno = data2.getString("FacNo");
                    String modfacname = data2.getString("FacName");
                    String modfacdesc = data2.getString("FacDesc");
                    String modfacmornprice = data2.getString("FacMornPrice");
                    String modfacnightprice = data2.getString("FacNightPrice");
                    String modfacwholeprice = data2.getString("FacWholePrice");

                    tf_facno.setText(modfacno);
                    tf_facname.setText(modfacname);
                    ta_facdesc.setText(modfacdesc);
                    tf_morning.setText(modfacmornprice);
                    tf_night.setText(modfacnightprice);
                    tf_wholeday.setText(modfacwholeprice);
                    while (data2.next()){
                        ModelFacility fac = new ModelFacility();
                        fac.setfacno(data2.getString("facno"));
                        fac.setfacname(data2.getString("facname"));
                        fac.setfacdesc(data2.getString("facdesc"));
                        ftable.add(fac);

                    }
                    tb_facid.setCellValueFactory(new PropertyValueFactory<>("modfacno"));
                    tb_facname.setCellValueFactory(new PropertyValueFactory<>("modfacname"));
                    tb_facdesc.setCellValueFactory(new PropertyValueFactory<>("modfacdesc"));
                    modfactable.setItems(ftable);
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }*/


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
