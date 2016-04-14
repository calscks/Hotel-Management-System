package application.assets.reservation;

import application.DBConnection;
import application.Validation;
import application.assets.AutoCompleteCBoxListener;
import application.assets.CIODateDisabler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ResvFacilityController implements Initializable{

    @FXML private TextField tf_facno;
    @FXML private TextArea tf_comment;
    @FXML private DatePicker date_bookdate;
    @FXML private ComboBox<String> cbox_fac1;
    @FXML private ComboBox<String> cbox_booktime;
    @FXML private Button btn_addfac;
    @FXML private Button btn_search;

    @FXML private TableView table_fac;
    @FXML private TableColumn tbcol_fac;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DBConnection db = new DBConnection("Data.sqlite");
        validation();

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

            table_fac.getItems().clear();
            tf_facno.setText(null);
            tf_comment.setText(null);
            cbox_booktime.getItems().clear();

            String query = "SELECT f.FacNo FROM FacType f WHERE f.FacNo NOT IN " +
                    "(SELECT FacNo FROM FacBookedDate WHERE Time = 'whole day' AND " +
                    "Time = 'night' OR Time = 'morning')";
        });

    }

    private void validation() {
        tf_facno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
    }
}
