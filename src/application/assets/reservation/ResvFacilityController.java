package application.assets.reservation;

import application.Validation;
import application.assets.AutoCompleteCBoxListener;
import application.assets.CIODateDisabler;
import application.assets.ModelFacility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;


public class ResvFacilityController implements Initializable{

    @FXML private TextField tf_facno;
    @FXML private TextArea tf_comment;
    @FXML private DatePicker date_bookdate;
    @FXML private Label lbl_facprice;
    @FXML private Label lbl_facname;
    @FXML private Label lbl_date;
    @FXML private ComboBox<String> cbox_fac1;
    @FXML private Button btn_addfac;
    @FXML private Button btn_search;

    @FXML private TableView<ModelFacility> table_fac;
    @FXML private TableColumn<ModelFacility, String> tbcol_facno;
    @FXML private TableColumn<ModelFacility, String> tbcol_desc;
    @FXML private TableColumn<ModelFacility, String> tbcol_fac;
    @FXML private TableColumn<ModelFacility, String> tbcol_price;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("resv fac controller loaded");

        validation();

        cellValueFactory();


        //for facility name cbox
        ObservableList<String> facName = FXCollections.observableArrayList();
        try {
            ResultSet rs = db.executeQuery("SELECT DISTINCT FacName FROM FacType ORDER BY  FacName");
            while (rs.next()){
                facName.add(rs.getString("FacName"));
            }
            cbox_fac1.setItems(facName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new AutoCompleteCBoxListener<>(cbox_fac1);
        //facility name cbox end

        new CIODateDisabler(date_bookdate); //for datepicker

        //for search button
        btn_search.setOnMouseClicked(me->{
            String facname = cbox_fac1.getSelectionModel().getSelectedItem();
            String date = date_bookdate.getValue().toString();
            ObservableList<ModelFacility> facdata = FXCollections.observableArrayList();

            table_fac.getItems().clear();
            tf_facno.setText(null);
            tf_comment.setText(null);

            String query = "SELECT FacNo, FacName, FacDesc, FacPrice FROM FacType WHERE FacNo NOT IN " +
                    "(SELECT FacNo FROM FacBookedDate WHERE BookDate = '" + date + "') AND FacName = '" +
                    facname + "'";

            try {
                ResultSet rs = db.executeQuery(query);
                while (rs.next()){
                    ModelFacility fac = new ModelFacility();
                    String fPrice = String.format(Locale.UK, "%.2f", rs.getFloat("FacPrice"));
                    fac.setFacno(rs.getString("FacNo"));
                    fac.setFacname(rs.getString("FacName"));
                    fac.setFacdesc(rs.getString("FacDesc"));
                    fac.setFacprice(fPrice);
                    facdata.add(fac);
                }
                table_fac.setItems(facdata);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }); //facility search done

        //for row double-clicking
        table_fac.setRowFactory(fc->{
            TableRow<ModelFacility> selRow = new TableRow<>();
            selRow.setOnMouseClicked(e->{
                if (e.getClickCount() == 2 && (!selRow.isEmpty())) {
                    ModelFacility mf = new ModelFacility();
                    mf = table_fac.getSelectionModel().getSelectedItem();

                    tf_facno.clear();
                    tf_comment.clear();
                    lbl_facprice.setText(null);
                    lbl_date.setText(null);
                    lbl_facname.setText(null);

                    String query = "SELECT FacNo, FacPrice FROM FacType WHERE FacNo = '" + mf.getFacno() + "'";
                    try {
                        ResultSet rs = db.executeQuery(query);
                        if (rs.next()){
                            tf_facno.setText(mf.getFacno());
                            lbl_facprice.setText(mf.getFacprice());
                            lbl_facname.setText(cbox_fac1.getSelectionModel().getSelectedItem());
                            lbl_date.setText(date_bookdate.getValue().toString());
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            return selRow;
        }); //end double-click row

        //let resvController controls the add button

    }

    private void validation() {
        tf_facno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
    }

    private void cellValueFactory(){
        tbcol_facno.setCellValueFactory(new PropertyValueFactory<>("facno"));
        tbcol_fac.setCellValueFactory(new PropertyValueFactory<>("facname"));
        tbcol_desc.setCellValueFactory(new PropertyValueFactory<>("facdesc"));
        tbcol_price.setCellValueFactory(new PropertyValueFactory<>("facprice"));
    }

    public Button getBtn_addfac() {
        return btn_addfac;
    }

    public Label getLbl_facname() {
        return lbl_facname;
    }

    public Label getLbl_date() {
        return lbl_date;
    }

    public Label getLbl_facprice() {
        return lbl_facprice;
    }

    public TextField getTf_facno() {
        return tf_facno;
    }

    public TextArea getTf_comment() {
        return tf_comment;
    }

    public TableView<ModelFacility> getTable_fac() {
        return table_fac;
    }
}
