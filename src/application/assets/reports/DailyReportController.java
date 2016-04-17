package application.assets.reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DailyReportController {

    Connection conn = DriverManager.getConnection("jdbc:sqlite:Data.sqlite");
            JasperDesign jasperdesign  = JRXmlLoader.load("daily_room.jrxml");
            JasperReport jasperreport = JasperCompileManager.compileReport(jasperdesign);
            JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport,null,conn);
    JasperViewer.viewReport();
    //test
    public DailyReportController() throws JRException, SQLException {
    }
}
