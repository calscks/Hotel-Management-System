package application.assets.RnFManagement;

import application.DBConnection;
import application.Validation;
import application.assets.ForAddButton;
import application.assets.ModelFacility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static application.slidemenu.SlideMenuController.db;
import javax.activation.CommandObject;
import java.io.IOException;
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
    @FXML private TextField tf_facresvprice;
    @FXML private Label lbl_cboxselection;
    @FXML private ComboBox cbox_searchby;
    @FXML private Button btn_modaddfac;
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

        addfac();

        cbox_searchby.setItems(selectbyitems);

        tf_searchby.textProperty().addListener((observable, oldValue,newValue)-> {
            if (lbl_cboxselection.getText().equals("FacilityID :")){
                try {
                    String sql = "SELECT * FROM FacType ft "+
                            "WHERE ft.FacNo = '" + tf_searchby.getText() + "'";

                    ResultSet data = db.executeQuery(sql);
                    ObservableList<ModelFacility> ftable = FXCollections.observableArrayList();
                    while (data.next()){
                    String modfacno = data.getString("FacNo");
                    String modfacname = data.getString("FacName");
                    String modfacdesc = data.getString("FacDesc");
                    String modfacresvprice = data.getString("FacPrice");


                    tf_facno.setText(modfacno);
                    tf_facname.setText(modfacname);
                    ta_facdesc.setText(modfacdesc);
                    tf_facresvprice.setText(modfacresvprice);


                        ModelFacility fac = new ModelFacility();
                        fac.setFacno(data.getString("FacNo"));
                        fac.setFacname(data.getString("FacName"));
                        fac.setFacdesc(data.getString("FacDesc"));
                        ftable.add(fac);
                       // System.out.println(fac.getfacno());

                    }
                    tb_facid.setCellValueFactory(new PropertyValueFactory<>("facno"));
                    tb_facname.setCellValueFactory(new PropertyValueFactory<>("facname"));
                    tb_facdesc.setCellValueFactory(new PropertyValueFactory<>("facdesc"));
                    modfactable.setItems(ftable);
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
            else if (lbl_cboxselection.getText().equals("FacilityName :")){
                try {
                    String sql = "select * from FacType ft " +
                            "where ft.FacName='" +tf_searchby.getText()+ "'";
                    ResultSet data2 = db.executeQuery(sql);
                    ObservableList<ModelFacility> ftable = FXCollections.observableArrayList();
                    while (data2.next()){
                    String modfacno = data2.getString("FacNo");
                    String modfacname = data2.getString("FacName");
                    String modfacdesc = data2.getString("FacDesc");
                    String modfacresvprice = data2.getString("FacPrice");


                    tf_facno.setText(modfacno);
                    tf_facname.setText(modfacname);
                    ta_facdesc.setText(modfacdesc);
                    tf_facresvprice.setText(modfacresvprice);

                        ModelFacility fac = new ModelFacility();
                        fac.setFacno(data2.getString("facno"));
                        fac.setFacname(data2.getString("facname"));
                        fac.setFacdesc(data2.getString("facdesc"));
                        ftable.add(fac);

                    }
                    tb_facid.setCellValueFactory(new PropertyValueFactory<>("facno"));
                    tb_facname.setCellValueFactory(new PropertyValueFactory<>("facname"));
                    tb_facdesc.setCellValueFactory(new PropertyValueFactory<>("facdesc"));
                    modfactable.setItems(ftable);
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }


        });

    }

    private void addfac() {
        FXMLLoader loadfac = new FXMLLoader(getClass().getResource("/application/assets/RnFManagement/addfacility.fxml"));
        AnchorPane facpane = new AnchorPane();
        try {
            facpane = loadfac.load();
        }catch (IOException e){
            e.printStackTrace();
        }

        AnchorPane finalfacpane = facpane;

        new ForAddButton(finalfacpane, btn_modaddfac);

        AddFacController afc = loadfac.getController();

        afc.getBtn_addfac().setOnMouseClicked(me ->{

            //additem



            //clear items
            afc.getTf_facno().setText(null);
            afc.getTf_facname().setText(null);
            afc.getTa_addfacdesc().setText(null);
            afc.getTf_morning().setText(null);
            afc.getTf_night().setText(null);
            afc.getTf_wholeday().setText(null);

            Stage stage = (Stage) afc.getBtn_addfac().getScene().getWindow();
            stage.close();
        });
    }

    private void validation() {
        tf_searchby.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoSpace(20));
        tf_facno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_facname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_facresvprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
    }
}
