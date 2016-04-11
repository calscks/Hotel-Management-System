package application.assets.reservation;



import java.net.URL;
import java.util.ResourceBundle;

public class ResvAddGroupController implements Initializable{
    @FXML private TextField tf_fname;
    @FXML private TextField tf_lname;
    @FXML private TextField tf_idno;
    @FXML private ComboBox<String> cbox_idtype;
    @FXML private ComboBox<String> cbox_roomno;

    DBConnection db = new DBConnection("Data.sqlite");

    @Override
    public void initialize(URL url, ResourceBundle rb){
        ObservableList<String> room = FXCollections.observableArrayList();
        validation();
    }
    private void validation() {
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }
}
