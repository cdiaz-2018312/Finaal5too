
package org.in5bm.emanueljolonCarlosDiaz.reports;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.in5bm.emanueljolonCarlosDiaz.db.Conexion;
/**
 *
 * @author charl
 * @date 
 * 
 */

public class GenerarReporte {
   
    private JasperViewer jasperViewer;
    private static GenerarReporte instance;    

    private GenerarReporte(){
        Locale locale = new Locale("es","GT");
        Locale.setDefault(locale);
    }
    
public static GenerarReporte getInstance() {
    if (instance== null){
    instance = new GenerarReporte();
    }
    return instance;
}
   
public void mostrarReporte(String nombreReporte, Map<String,Object> parametros,String titulo){
    
    try{
        System.out.println(nombreReporte);
        System.out.println(getClass().getResource(nombreReporte).toString());
        URL urlFile = new URL(getClass().getResource(nombreReporte).toString());
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(urlFile);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, Conexion.getInstance().getConexion());
        
        jasperViewer = new JasperViewer(jasperPrint, false);
        jasperViewer.setTitle(titulo);
        jasperViewer.setVisible(true);
        
    }catch(Exception e){
        e.printStackTrace();
    }
    
}
}
