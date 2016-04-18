package application.assets.records;

import application.assets.ModelFacility;
import application.assets.ModelGroupMember;
import application.assets.ModelRoom;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static application.slidemenu.SlideMenuController.db;

public class SCIController implements Initializable {
    @FXML private TextField tf_search;
    @FXML private TextField tf_resvno;
    @FXML private TextField tf_fname;
    @FXML private TextField tf_lname;
    @FXML private TextField tf_address;
    @FXML private TextField tf_postcode;
    @FXML private TextField tf_city;
    @FXML private TextField tf_state;
    @FXML private TextField tf_country;
    @FXML private TextField tf_idtype;
    @FXML private TextField tf_idno;
    @FXML private TextField tf_cioid;

    @FXML private ToggleGroup SearchGroup;
    @FXML private RadioButton rb_resvno;
    @FXML private RadioButton rb_lname;
    @FXML private RadioButton rb_fname;
    @FXML private RadioButton rb_cioid;

    @FXML private TableView<ModelGroupMember> table_memgroup;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memfname;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memlname;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memidtype;
    @FXML private TableColumn<ModelGroupMember, String> tbcol_memidno;

    @FXML private TableView<ModelRoom> table_room;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomcat;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomtype;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomno;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomci;
    @FXML private TableColumn<ModelRoom, String> tbcol_roomco;
    @FXML private TableColumn<ModelRoom, String> tbcol_xtrabed;
    @FXML private TableColumn<ModelRoom, String> tbcol_rprice;

    @FXML private TableView<ModelFacility> table_fac;
    @FXML private TableColumn<ModelFacility, String> tbcol_fac;
    @FXML private TableColumn<ModelFacility, String> tbcol_facno;
    @FXML private TableColumn<ModelFacility, String> tbcol_facprice;
    @FXML private TableColumn<ModelFacility, String> tbcol_facbookdate;
    @FXML private TableColumn<ModelFacility, String> tbcol_faccomment;

    @FXML private TableView<ModelSResult> table_sresult;
    @FXML private TableColumn<ModelSResult, String> tbcol_scioid;
    @FXML private TableColumn<ModelSResult, String> tbcol_sresvno;
    @FXML private TableColumn<ModelSResult, String> tbcol_sfname;
    @FXML private TableColumn<ModelSResult, String> tbcol_slname;
    @FXML private TableColumn<ModelSResult, String> tbcol_status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cellValueFactory();
        searchCICO();
        doubleClick();
    }

    private void searchCICO(){

        tf_search.setOnKeyReleased(event -> {
            //language=SQLite
            String query = "";
            table_sresult.getItems().clear();
            if (rb_resvno.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                query = "SELECT * FROM CheckInOut cio INNER JOIN Customer c ON " +
                        "cio.CustID = c.CustID WHERE cio.ResvNo LIKE '%" +
                        tf_search.getText() + "%'";
                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()){
                        ModelSResult msr = new ModelSResult();
                        msr.setsCioid(String.valueOf(rs.getInt("CIO_ID")));
                        msr.setsResvNo(String.valueOf(rs.getInt("ResvNo")));
                        msr.setsFname(rs.getString("CustFName"));
                        msr.setsLname(rs.getString("CustLName"));
                        msr.setsStatus(rs.getString("Status"));
                        table_sresult.getItems().add(msr);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (rb_fname.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                query = "SELECT * FROM CheckInOut cio INNER JOIN Customer c ON " +
                        "cio.CustID = c.CustID WHERE c.CustFName LIKE '%" +
                        tf_search.getText() + "%'";

                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()){
                        ModelSResult msr = new ModelSResult();
                        msr.setsCioid(String.valueOf(rs.getInt("CIO_ID")));
                        msr.setsResvNo(String.valueOf(rs.getInt("ResvNo")));
                        msr.setsFname(rs.getString("CustFName"));
                        msr.setsLname(rs.getString("CustLName"));
                        msr.setsStatus(rs.getString("Status"));
                        table_sresult.getItems().add(msr);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if (rb_lname.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                query = "SELECT * FROM CheckInOut cio INNER JOIN Customer c ON " +
                        "cio.CustID = c.CustID WHERE c.CustLName LIKE '%" +
                        tf_search.getText() + "%'";

                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()){
                        ModelSResult msr = new ModelSResult();
                        msr.setsCioid(String.valueOf(rs.getInt("CIO_ID")));
                        msr.setsResvNo(String.valueOf(rs.getInt("ResvNo")));
                        msr.setsFname(rs.getString("CustFName"));
                        msr.setsLname(rs.getString("CustLName"));
                        msr.setsStatus(rs.getString("Status"));
                        table_sresult.getItems().add(msr);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if (rb_cioid.isSelected() && !Objects.equals(tf_search.getText(), "")) {
                query = "SELECT * FROM CheckInOut cio INNER JOIN Customer c ON " +
                        "cio.CustID = c.CustID WHERE cio.CIO_ID LIKE '%" +
                        tf_search.getText() + "%'";

                try {
                    ResultSet rs = db.executeQuery(query);
                    while (rs.next()){
                        ModelSResult msr = new ModelSResult();
                        msr.setsCioid(String.valueOf(rs.getInt("CIO_ID")));
                        msr.setsResvNo(String.valueOf(rs.getInt("ResvNo")));
                        msr.setsFname(rs.getString("CustFName"));
                        msr.setsLname(rs.getString("CustLName"));
                        msr.setsStatus(rs.getString("Status"));
                        table_sresult.getItems().add(msr);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doubleClick(){
        table_sresult.setRowFactory(ts->{

            TableRow<ModelSResult> selRow = new TableRow<>();

            selRow.setOnMouseClicked(me->{
                if (me.getClickCount() == 2 && (!selRow.isEmpty())){
                    table_room.getItems().clear();
                    table_memgroup.getItems().clear();
                    table_fac.getItems().clear();

                    ModelSResult msr = table_sresult.getSelectionModel().getSelectedItem();

                    try {
                        //language=SQLite
                        String query = "SELECT * FROM CheckInOut cio INNER JOIN Customer c " +
                                "ON cio.CustID = c.CustID INNER JOIN CustAddress ca " +
                                "ON c.CustID = ca.CustID WHERE ResvNo=" +
                                Integer.parseInt(msr.getsResvNo());

                        ResultSet rs = db.executeQuery(query);

                        tf_resvno.setText(String.valueOf(rs.getInt("ResvNo")));
                        tf_fname.setText(rs.getString("CustFName"));
                        tf_lname.setText(rs.getString("CustLName"));
                        tf_address.setText(rs.getString("Address"));
                        tf_postcode.setText(rs.getString("PostCode"));
                        tf_city.setText(rs.getString("City"));
                        tf_state.setText(rs.getString("State"));
                        tf_country.setText(rs.getString("Country"));
                        tf_idtype.setText(rs.getString("CustID_Type"));
                        tf_idno.setText(rs.getString("CustID"));
                        tf_cioid.setText(rs.getString("CIO_ID"));

                        query = "SELECT * FROM CustomerGroup WHERE G_CustID = '" +
                                tf_idno.getText() + "'";

                        rs = db.executeQuery(query);

                        while (rs.next()){
                            ModelGroupMember mg = new ModelGroupMember();
                            mg.setMemFName(rs.getString("CustFName"));
                            mg.setMemLName(rs.getString("CustLName"));
                            mg.setIdType(rs.getString("IDType"));
                            mg.setIdNo(rs.getString("IDNo"));
                            table_memgroup.getItems().add(mg);
                        }

                        query = "SELECT * FROM RoomBooking rb INNER JOIN Room r ON rb.RoomNo = r.RoomNo " +
                                "INNER JOIN RoomType rt ON r.RoomTypeID = rt.TypeID WHERE rb.ResvNo =" +
                                Integer.parseInt(tf_resvno.getText()) + "";

                        rs = db.executeQuery(query);

                        while (rs.next()){
                            ModelRoom mr = new ModelRoom();
                            mr.setRoomcat(rs.getString("TypeGroup"));
                            mr.setRtype(rs.getString("TypeName"));
                            mr.setRoomno(rs.getString("RoomNo"));
                            mr.setCidate(rs.getString("Date_CI"));
                            mr.setCodate(rs.getString("Date_CO"));
                            mr.setExtbedtype(rs.getString("ExtBedType"));
                            mr.setRoomprice(rs.getString("Price"));

                            table_room.getItems().add(mr);
                        }

                        query = "SELECT * FROM FacBookedDate fbd INNER JOIN FacType ft ON fbd.FacNo" +
                                "= ft.FacNo WHERE fbd.ResvNo = " + Integer.parseInt(tf_resvno.getText());

                        rs = db.executeQuery(query);

                        while (rs.next()){
                            ModelFacility mf = new ModelFacility();
                            mf.setFacname(rs.getString("FacName"));
                            mf.setFacno(rs.getString("FacNo"));
                            mf.setBookedfacdate(rs.getString("BookDate"));
                            mf.setFacprice(String.valueOf(rs.getFloat("FacPrice")));
                            mf.setFacdesc(rs.getString("Comment"));

                            table_fac.getItems().add(mf);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });

            return selRow;
        });
    }

    private void cellValueFactory(){
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
        tbcol_scioid.setCellValueFactory(new PropertyValueFactory<>("sCioid"));
        tbcol_sresvno.setCellValueFactory(new PropertyValueFactory<>("sResvNo"));
        tbcol_sfname.setCellValueFactory(new PropertyValueFactory<>("sFname"));
        tbcol_slname.setCellValueFactory(new PropertyValueFactory<>("sLname"));
        tbcol_status.setCellValueFactory(new PropertyValueFactory<>("sStatus"));
    }

}
