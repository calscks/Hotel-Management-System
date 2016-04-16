package application.assets.reservation;

import application.Validation;
import application.assets.ModelFacility;
import application.assets.ModelGroupMember;
import application.assets.ModelRoom;
import application.assets.ModelResvSearch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;

public class ResvEditController implements Initializable {

    @FXML
    private TextField tf_search;
    @FXML
    private TextField tf_resvno;
    @FXML
    private TextField tf_fname;
    @FXML
    private TextField tf_lname;
    @FXML
    private TextField tf_address;
    @FXML
    private TextField tf_postcode;
    @FXML
    private TextField tf_city;
    @FXML
    private TextField tf_state;
    @FXML
    private TextField tf_idno;
    @FXML
    private ComboBox<String> cbox_country;
    @FXML
    private ComboBox<String> cbox_idtype;

    @FXML
    private ToggleGroup ResvSearchGroup;
    @FXML
    private RadioButton rb_resvno;
    @FXML
    private RadioButton rb_lname;
    @FXML
    private RadioButton rb_fname;

    @FXML
    private Button btn_resvcancel;
    @FXML
    private Button btn_addfac;
    @FXML
    private Button btn_delfac;
    @FXML
    private Button btn_addroom;
    @FXML
    private Button btn_delroom;
    @FXML
    private Button btn_addguest;
    @FXML
    private Button btn_delguest;
    @FXML
    private Button btn_next;

    @FXML
    private TableView<ModelGroupMember> table_memgroup;
    @FXML
    private TableColumn<ModelGroupMember, String> tbcol_memfname;
    @FXML
    private TableColumn<ModelGroupMember, String> tbcol_memlname;
    @FXML
    private TableColumn<ModelGroupMember, String> tbcol_memidtype;
    @FXML
    private TableColumn<ModelGroupMember, String> tbcol_memidno;

    @FXML
    private TableView<ModelRoom> table_room;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomcat;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomtype;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomno;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomci;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_roomco;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_xtrabed;
    @FXML
    private TableColumn<ModelRoom, String> tbcol_rprice;

    @FXML
    private TableView<ModelFacility> table_fac;
    @FXML
    private TableColumn<ModelFacility, String> tbcol_fac;
    @FXML
    private TableColumn<ModelFacility, String> tbcol_facno;
    @FXML
    private TableColumn<ModelFacility, String> tbcol_facprice;
    @FXML
    private TableColumn<ModelFacility, String> tbcol_facbookdate;
    @FXML
    private TableColumn<ModelFacility, String> tbcol_faccomment;

    @FXML
    private TableView<ModelResvSearch> table_sresult;
    @FXML
    private TableColumn<ModelResvSearch, String> tbcol_sresv;
    @FXML
    private TableColumn<ModelResvSearch, String> tbcol_sfname;
    @FXML
    private TableColumn<ModelResvSearch, String> tbcol_slname;

    private ObservableList<String> idtype = FXCollections.observableArrayList("Identification Card", "Passport No");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validation();
        cellValueFactory();
        searchResv();
        cancelResv();
        doubleClick();
        cbox_idtype.setItems(idtype);

    }

    public void searchResv() {
        //language=SQLite
        tf_search.setOnKeyReleased(event -> {
            table_sresult.getItems().clear();
            if (rb_resvno.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                String query = "SELECT r.ResvNo, c.CustFName, c.CustLName FROM Reservation r " +
                        "INNER JOIN Customer c WHERE r.ResvNo LIKE '%" + tf_search.getText() +
                        "%' and r.CustID = c.CustID";
                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()) {
                        ModelResvSearch mrs = new ModelResvSearch();
                        mrs.setResvNo(String.valueOf(rs.getInt(1))); //rs index always start from 1
                        mrs.setfName(rs.getString(2));
                        mrs.setlName(rs.getString(3));
                        table_sresult.getItems().add(mrs);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (rb_fname.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                String query = "SELECT r.ResvNo, c.CustFName, c.CustLName FROM Reservation r " +
                        "INNER JOIN Customer c WHERE c.CustFName LIKE '%" + tf_search.getText() +
                        "%' and r.CustID = c.CustID";
                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()) {
                        ModelResvSearch mrs = new ModelResvSearch();
                        mrs.setResvNo(String.valueOf(rs.getInt(1)));
                        mrs.setfName(rs.getString(2));
                        mrs.setlName(rs.getString(3));
                        table_sresult.getItems().add(mrs);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (rb_lname.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                String query = "SELECT r.ResvNo, c.CustFName, c.CustLName FROM Reservation r " +
                        "INNER JOIN Customer c WHERE c.CustLName LIKE '%" + tf_search.getText() +
                        "%' and r.CustID = c.CustID";
                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()) {
                        ModelResvSearch mrs = new ModelResvSearch();
                        mrs.setResvNo(String.valueOf(rs.getInt(1)));
                        mrs.setfName(rs.getString(2));
                        mrs.setlName(rs.getString(3));
                        table_sresult.getItems().add(mrs);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void cancelResv(){
        btn_resvcancel.setOnMouseClicked(me->{
            int selected = table_sresult.getSelectionModel().getSelectedIndex();
            if (selected >= 0){
                ModelResvSearch mrs = new ModelResvSearch();
                mrs = table_sresult.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Room");
                alert.setContentText("Are you sure you want to delete reservation " + mrs.getResvNo() + "?");

                Optional<ButtonType> select = alert.showAndWait();
                if (select.isPresent()) {
                    if (select.get() == ButtonType.OK) {
                        int resvno = Integer.parseInt(mrs.getResvNo());

                        String s = "SELECT Subtotal, Bal FROM Payment WHERE ResvNo=" +
                                mrs.getResvNo();
                        try {
                            ResultSet rs = db.executeQuery(s);

                            Float amount = rs.getFloat("Subtotal") - rs.getFloat("Bal");

                            Alert payreturn = new Alert(Alert.AlertType.INFORMATION);
                            payreturn.setTitle("Return Amount");
                            payreturn.setHeaderText("Amount to be returned: " +
                                    String.format(Locale.UK, "%.2f", amount));
                            payreturn.showAndWait();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        try {
                            //language=SQLite
                            String query = "DELETE FROM Reservation WHERE ResvNo=" + resvno;
                            db.executeUpdate(query);
                            query = "DELETE FROM RoomBooking WHERE ResvNo NOT IN (SELECT " +
                                    "DISTINCT ResvNo FROM Reservation)";
                            db.executeUpdate(query);
                            query = "DELETE FROM FacBookedDate WHERE ResvNo NOT IN (" +
                                    "SELECT DISTINCT ResvNo FROM Reservation)";
                            db.executeUpdate(query);
                            query = "DELETE FROM Customer WHERE CustID NOT IN (" +
                                    "SELECT DISTINCT CustID FROM Reservation)";
                            db.executeUpdate(query);
                            query = "DELETE FROM CustAddress WHERE CustID NOT IN (" +
                                    "SELECT DISTINCT CustID FROM Customer)";
                            db.executeUpdate(query);
                            query = "DELETE FROM CustomerGroup WHERE G_CustID NOT IN (" +
                                    "SELECT DISTINCT CustID FROM Customer)";
                            db.executeUpdate(query);
                            query = "DELETE FROM Payment WHERE ResvNo NOT IN (" +
                                    "SELECT DISTINCT ResvNo FROM Reservation)";
                            db.executeUpdate(query);
                            query = "DELETE FROM Pay_CCard WHERE CCardNo NOT IN (" +
                                    "SELECT DISTINCT CCardNo FROM Payment)";
                            db.executeUpdate(query);

                            table_sresult.getItems().remove(selected);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        alert.close();
                    }
                }
            } else {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("No Selection");
                alert2.setHeaderText("No Reservation is Selected");
                alert2.setContentText("Please select a reservation in the table to be deleted.");
                alert2.showAndWait();
            }

        });
    }

    public void doubleClick(){
        table_sresult.setRowFactory(ts->{

            TableRow<ModelResvSearch> selRow = new TableRow<>();

            selRow.setOnMouseClicked(me->{

                if (me.getClickCount() == 2 && (!selRow.isEmpty())){
                    ModelResvSearch result = table_sresult.getSelectionModel().getSelectedItem();
                    try {
                        //language=SQLite
                        String query = "SELECT * FROM Reservation r INNER JOIN Customer c " +
                                "ON r.CustID = c.CustID INNER JOIN CustAddress ca ON " +
                                "ca.CustID = c.CustID WHERE r.ResvNo = " + result.getResvNo();
                        ResultSet rs = db.executeQuery(query);

                        tf_resvno.setText(String.valueOf(rs.getInt("ResvNo")));
                        tf_fname.setText(rs.getString("CustFName"));
                        tf_lname.setText(rs.getString("CustLName"));
                        tf_address.setText(rs.getString("Address"));
                        tf_postcode.setText(rs.getString("PostCode"));
                        tf_city.setText(rs.getString("State"));
                        cbox_country.getSelectionModel().select(rs.getString("Country"));
                        cbox_idtype.getSelectionModel().select(rs.getString("CustID_Type"));
                        tf_idno.setText(rs.getString("CustID"));

                        query = "SELECT * FROM CustomerGroup WHERE G_CustID = '" +
                                tf_idno.getText() + "'";

                        rs = db.executeQuery(query);

                        while (rs.next()){
                            ModelGroupMember mg = new ModelGroupMember();

                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            });

            return selRow;
        });
    }

    private void validation() {
        tf_search.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNo(20));
        tf_resvno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(10));
        tf_fname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_lname.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(20));
        tf_address.addEventFilter(KeyEvent.KEY_TYPED, Validation.validCharNoCommaDot(50));
        tf_postcode.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(12));
        tf_city.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_state.addEventFilter(KeyEvent.KEY_TYPED, Validation.validChar(25));
        tf_idno.addEventFilter(KeyEvent.KEY_TYPED, Validation.validNo(20));
    }

    private void cellValueFactory() {
        //room booking
        tbcol_roomcat.setCellValueFactory(new PropertyValueFactory<>("roomcat"));
        tbcol_roomtype.setCellValueFactory(new PropertyValueFactory<>("rtype"));
        tbcol_roomno.setCellValueFactory(new PropertyValueFactory<>("roomno"));
        tbcol_roomci.setCellValueFactory(new PropertyValueFactory<>("cidate"));
        tbcol_roomco.setCellValueFactory(new PropertyValueFactory<>("codate"));
        tbcol_xtrabed.setCellValueFactory(new PropertyValueFactory<>("extbedtype"));
        tbcol_rprice.setCellValueFactory(new PropertyValueFactory<>("roomprice"));

        //group member
        tbcol_memfname.setCellValueFactory(new PropertyValueFactory<>("memFName"));
        tbcol_memlname.setCellValueFactory(new PropertyValueFactory<>("memLName"));
        tbcol_memidtype.setCellValueFactory(new PropertyValueFactory<>("idType"));
        tbcol_memidno.setCellValueFactory(new PropertyValueFactory<>("idNo"));

        //fac booking
        tbcol_fac.setCellValueFactory(new PropertyValueFactory<>("facname"));
        tbcol_facno.setCellValueFactory(new PropertyValueFactory<>("facno"));
        tbcol_facbookdate.setCellValueFactory(new PropertyValueFactory<>("bookedfacdate"));
        tbcol_facprice.setCellValueFactory(new PropertyValueFactory<>("facprice"));
        tbcol_faccomment.setCellValueFactory(new PropertyValueFactory<>("facdesc"));

        //search table
        tbcol_sresv.setCellValueFactory(new PropertyValueFactory<>("resvNo"));
        tbcol_sfname.setCellValueFactory(new PropertyValueFactory<>("fName"));
        tbcol_slname.setCellValueFactory(new PropertyValueFactory<>("lName"));

        //trick: make cells editable
        tbcol_memfname.setCellFactory(TextFieldTableCell.forTableColumn());
        tbcol_memlname.setCellFactory(TextFieldTableCell.forTableColumn());
        tbcol_memidno.setCellFactory(TextFieldTableCell.forTableColumn());

        tbcol_faccomment.setCellFactory(TextFieldTableCell.forTableColumn());
    }

}
