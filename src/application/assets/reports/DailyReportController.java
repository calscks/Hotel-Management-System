//package application.assets.reports;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.RadioButton;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import net.sf.jasperreports.view.JasperViewer;
//
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.ResourceBundle;
//
//
//
//public class DailyReportController implements Initializable{
//    ObservableList<String> cio =  FXCollections.observableArrayList("Check in","Check Out");
//
//    @FXML Button btn_print;
//    @FXML RadioButton radio_room;
//    @FXML RadioButton radio_fac;
//    @FXML RadioButton radio_daily;
//    @FXML RadioButton radio_resv;
//    @FXML ComboBox cbox_cio;
//    @Override
//    public void initialize(URL url, ResourceBundle rb){
//        btn_print.setDisable(true);
//        if(radio_room.isSelected()){
//            btn_print.setDisable(true);
//            cbox_cio.setDisable(true);
//            if (radio_daily.isSelected()){
//                btn_print.setDisable(true);
//                cbox_cio.setDisable(false);
//                if (cbox_cio.getValue().toString().equals("Check in") ){
//                    btn_print.setDisable(false);
//                    btn_print.setOnAction(event -> {
//                        getreport("application/assets/reports/JasperReports/room_dailyreport.jrxml");
//                    });
//                }else if (cbox_cio.getValue().toString().equals("Check Out")){
//                    btn_print.setDisable(false);
//                    btn_print.setOnAction(event -> {
//                        getreport("application/assets/reports/JasperReports/room_coDailyReport.jrxml");
//                    });
//                }
//            }
//            else if (radio_resv.isSelected()){
//                btn_print.setDisable(false);
//                cbox_cio.setDisable(true);
//                btn_print.setOnAction(event -> {
//                    getreport("application/assets/reports/JasperReports/room_reservationreport.jrxml");
//                });
//
//            }
//        } else if (radio_fac.isSelected()){
//            btn_print.setDisable(true);
//            cbox_cio.setDisable(true);
//            if(radio_daily.isSelected()){
//                cbox_cio.setDisable(true);
//                btn_print.setDisable(false);
//                btn_print.setOnAction(event -> {
//                    ;
//                    getreport("application/assets/reports/JasperReports/facility_dailyreport.jrxml");
//                });
//            }
//            else if (radio_resv.isSelected()){
//                cbox_cio.setDisable(true);
//                btn_print.setDisable(false);
//                btn_print.setOnAction(event -> {
//                    ;
//                    getreport("application/assets/reports/JasperReports/fac_reservationreport.jrxml");
//                });
//            }
//        }
//
//
//    }
//    protected void getreport(String reportpath){
//        try {
//            Class.forName("org.sqlite.JDBC");
//            Connection conn = DriverManager.getConnection("jdbc:sqlite:Data.sqlite");
//            try {
//                JasperDesign jd = JRXmlLoader.load(getClass().getResourceAsStream(reportpath));
//                JasperReport jasperreport = JasperCompileManager.compileReport(jd);
//                JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport,null,conn);
//                JasperViewer.viewReport(jasperprint, false);
//            } catch (JRException e) {
//                e.printStackTrace();
//            }
//            conn.close();
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
