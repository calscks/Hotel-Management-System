package application.assets.reports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class DailyReportController implements Initializable{
    ObservableList<String> cio =  FXCollections.observableArrayList("Check in","Check Out");

    @FXML Button btn_print;

    @FXML RadioButton radio_room;
    @FXML RadioButton radio_fac;
    @FXML RadioButton radio_daily;
    @FXML RadioButton radio_resv;
    @FXML RadioButton radio_payment;

    @FXML ComboBox cbox_cio;

    @Override
    public void initialize(URL Location, ResourceBundle resources){
        cbox_cio.setItems(cio);
        btn_print.setDisable(true);
        radio_daily.setDisable(true);
        radio_fac.setDisable(false);
        radio_resv.setDisable(true);
        radio_room.setDisable(false);
        radio_payment.setDisable(false);
        cbox_cio.setDisable(true);

        radio_room.setOnAction(event -> {
            if(radio_room.isSelected()) {
               superclass();
            }
        });
        radio_fac.setOnAction(e ->{
            if (radio_fac.isSelected()) {
                superclass();
            }
        });
        radio_payment.setOnAction(event -> {
            if(radio_payment.isSelected()){
                radio_daily.setDisable(true);
                radio_resv.setDisable(true);
                cbox_cio.setDisable(true);
                btn_print.setDisable(false);
                btn_print.setOnAction(e -> {
                    getreport("/application/assets/reports/JasperReports/payment.jrxml");
                });
            }
        });
        radio_daily.setOnAction(event -> {
            if(radio_daily.isSelected()){
                if(radio_room.isSelected()){
                    cbox_cio.setDisable(false);
                    btn_print.setDisable(true);
                    if(cbox_cio.getSelectionModel().getSelectedItem() =="Check in"){
                        btn_print.setDisable(false);
                        btn_print.setOnAction(event1 -> {
                            getreport("/application/assets/reports/JasperReports/room_dailyreport.jrxml");
                        });
                    }else if (cbox_cio.getSelectionModel().getSelectedItem() =="Check Out"){
                        btn_print.setDisable(false);
                        btn_print.setOnAction(event2 -> {
                            getreport("/application/assets/reports/JasperReports/room_coDailyReport.jrxml");
                        });
                    }
                }else if (radio_fac.isSelected()){
                    cbox_cio.setDisable(true);
                    btn_print.setDisable(false);
                    btn_print.setOnAction(event2 -> {

                        getreport("/application/assets/reports/JasperReports/facility_dailyreport.jrxml");
                    });


                }
            }
        });
        radio_resv.setOnAction(event -> {
            if(radio_resv.isSelected()){
              if(radio_room.isSelected()){
                cbox_cio.setDisable(true);
                    btn_print.setDisable(false);
                    btn_print.setOnAction(event2 -> {
                      getreport("/application/assets/reports/JasperReports/room_reservationreport.jrxml");
                  });
                }if(radio_fac.isSelected()){
                    cbox_cio.setDisable(true);
                    btn_print.setDisable(false);
                    btn_print.setOnAction(event2 -> {//for facility reservation
                        getreport("/application/assets/reports/JasperReports/fac_reservationreport.jrxml");
                    });
                }
            }
        });

    }

    protected void superclass(){
        radio_daily.setDisable(false);
        radio_resv.setDisable(false);
        cbox_cio.setDisable(true);
        btn_print.setDisable(true);
    }
    protected void getreport(String reportpath){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Data.sqlite");
            try {
                JasperDesign jd = JRXmlLoader.load(getClass().getResourceAsStream(reportpath));
                JasperReport jasperreport = JasperCompileManager.compileReport(jd);
                JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport,null,conn);
                JasperViewer.viewReport(jasperprint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
