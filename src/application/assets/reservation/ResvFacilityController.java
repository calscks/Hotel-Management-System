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

    }

    private void validation() {
        tf_facno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(10));
    }
}
