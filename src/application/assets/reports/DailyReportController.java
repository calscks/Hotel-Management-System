package application.assets.reports;

import javafx.fxml.Initializable;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class DailyReportController  implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Data.sqlite");
            try {
                JasperDesign jd = JRXmlLoader.load(getClass().getResourceAsStream("/application/assets/reports/daily_room.jrxml"));
                JasperReport jasperreport = JasperCompileManager.compileReport(jd);
                JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport,null,conn);
                JasperViewer.viewReport(jasperprint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
