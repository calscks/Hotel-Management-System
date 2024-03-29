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
import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
    @FXML private Button btn_moddelfac;
    @FXML private Button btn_editfac;
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
        
        delfac();

        editfac();

        cbox_searchby.setItems(selectbyitems);
        //search function
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
                        fac.setFacprice(data.getString("FacPrice"));
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
                        fac.setFacprice(data2.getString("facprice"));
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
        //double click tableview
        modfactable.setRowFactory(tv->{
            TableRow<ModelFacility> selRow = new TableRow<>();
            selRow.setOnMouseClicked(me ->{
                if (me.getClickCount() == 2 && (!selRow.isEmpty())){
                    ModelFacility mf = new ModelFacility();
                    mf = modfactable.getSelectionModel().getSelectedItem();

                    tf_facno.clear();
                    tf_facname.clear();
                    ta_facdesc.clear();
                    tf_facresvprice.clear();

                    String sql = "SELECT * FROM FacType ft "+
                            "WHERE ft.FacName = '" + mf.getFacname() + "'";
                    try {
                        ResultSet data = db.executeQuery(sql);
                        if (data.next()){
                            tf_facno.setText(mf.getFacno());
                            tf_facname.setText(mf.getFacname());
                            ta_facdesc.setText(mf.getFacdesc());
                            tf_facresvprice.setText(mf.getFacprice());
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            });
            return selRow;
        });
    }

    private void editfac() {
        btn_editfac.setOnMouseClicked(me ->{
            if (tf_facno.getText().isEmpty() || tf_facname.getText().isEmpty() || ta_facdesc.getText().isEmpty() || tf_facresvprice.getText().isEmpty()){
                Alert emptyfield = new Alert(Alert.AlertType.WARNING);
                emptyfield.setTitle("Empty Field");
                emptyfield.setHeaderText("One of the field is empty");
                emptyfield.setContentText("Please make sure to fill in all the field");
                emptyfield.showAndWait();
            }
            else {
                int selRow = modfactable.getSelectionModel().getSelectedIndex();
                if (selRow >= 0){
                    ModelFacility mf = new ModelFacility();
                    mf = modfactable.getSelectionModel().getSelectedItem();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Edit Facility");
                    alert.setContentText("Are you sure you want to edit " + mf.getFacname() + " from the table?");

                    Optional<ButtonType> sel = alert.showAndWait();
                    if (sel.isPresent()){
                        if (sel.get() == ButtonType.OK){
                            String facno = tf_facno.getText();
                            String facname = tf_facname.getText();
                            String facdesc = ta_facdesc.getText();
                            String facprice = tf_facresvprice.getText();
                            try {
                                String sql = "UPDATE FacType" +
                                        " SET FacNo = '"+facno+"', FacName = '"+facname+"'" +
                                        ",FacDesc = '"+facdesc+"',FacPrice = "+facprice+"" +
                                        " WHERE FacNo='"+mf.getFacno()+"'";

                                db.executeUpdate(sql);
                            }catch (SQLException e){
                                e.printStackTrace();
                            }
                            tf_facno.clear();
                            tf_facname.clear();
                            ta_facdesc.clear();
                            tf_facresvprice.clear();
                        }
                        else {
                            alert.close();
                        }
                    }

                }
                else {
                    Alert noSel = new Alert(Alert.AlertType.WARNING);
                    noSel.setTitle("No Selection");
                    noSel.setHeaderText("No Facility is selected");
                    noSel.setContentText("Please select a facility in the table to be edited");
                    noSel.showAndWait();
                }
            }

        });
    }

    private void delfac() {
        btn_moddelfac.setOnMouseClicked(me ->{
            int selRow = modfactable.getSelectionModel().getSelectedIndex();
            if (selRow >= 0) {
                ModelFacility mf = new ModelFacility();
                mf = modfactable.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Facility");
                alert.setContentText("Are you sure you want to delete " + mf.getFacname() + " from the table?");

                Optional<ButtonType> sel = alert.showAndWait();
                if (sel.isPresent()) {
                    if (sel.get() == ButtonType.OK) {
                        modfactable.getItems().remove(selRow);
                        try {
                            String sql = "DELETE from FacType WHERE FacNo = '" + mf.getFacno() + "'";
                            db.executeUpdate(sql);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                        tf_facno.clear();
                        tf_facname.clear();
                        ta_facdesc.clear();
                        tf_facresvprice.clear();
                    }
                    else {
                        alert.close();
                    }
                }
            }
            else {
                Alert noSel = new Alert(Alert.AlertType.WARNING);
                noSel.setTitle("No Selection");
                noSel.setHeaderText("No Facility is selected");
                noSel.setContentText("Please select a facility in the table to be deleted");
                noSel.showAndWait();
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
            if (afc.getTf_facno().getText().isEmpty() || afc.getTf_facname().getText().isEmpty() ||
                     afc.getTa_addfacdesc().getText().isEmpty() || afc.getTf_addfacprice().getText().isEmpty()){
                Stage stage = (Stage)afc.getBtn_addfac().getScene().getWindow();
                stage.close();
                Alert emptyfield = new Alert(Alert.AlertType.WARNING);
                emptyfield.setTitle("Empty Field");
                emptyfield.setHeaderText("One of the field is empty");
                emptyfield.setContentText("Please make sure to fill in all the field");
                emptyfield.showAndWait();
            }
            else {
                //additem
                String facno = afc.getTf_facno().getText();
                String facname = afc.getTf_facname().getText();
                String facdesc = afc.getTa_addfacdesc().getText();
                Integer facprice = Integer.parseInt(afc.getTf_addfacprice().getText());

                try {
                    String sql = "INSERT INTO FacType (FacNo,FacName,FacDesc,FacPrice)"+
                            "VALUES ('"+ facno +"','"+ facname +"','"+ facdesc +"',"+ facprice +")";
                    db.executeUpdate(sql);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                //clear items
                afc.getTf_facno().clear();
                afc.getTf_facname().clear();
                afc.getTa_addfacdesc().clear();
                afc.getTf_addfacprice().clear();

                Stage stage = (Stage) afc.getBtn_addfac().getScene().getWindow();
                stage.close();
            }
        });
    }

    private void validation() {
        tf_searchby.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoSpace(20));
        tf_facno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
        tf_facname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoSpace(20));
        ta_facdesc.addEventFilter(KeyEvent.KEY_TYPED, Validation.validtxtareaCharNoSpace(50));
        tf_facresvprice.addEventFilter(KeyEvent.KEY_TYPED, Validation.validPrice(10));
    }
}
