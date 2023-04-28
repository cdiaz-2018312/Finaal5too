package org.in5bm.emanueljolonCarlosDiaz.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.in5bm.emanueljolonCarlosDiaz.system.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javax.swing.JOptionPane;
import org.in5bm.emanueljolonCarlosDiaz.reports.GenerarReporte;

/**
 *
 * @author informatica
 */

public class MenuPrincipalController implements Initializable{
  
    private Principal escenarioPrincipal;
    @FXML
    private MenuItem btnSobre;
    
    @FXML
    private MenuItem btnCerrar;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void clicAlumnos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAlumnos();
      
    }
    
    @FXML
    public void clicSalones(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaSalones();
      
    }
    
    @FXML
    public void clicCarreras(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaCarreras();
      
    }
    
    @FXML
    public void clicAsignacionAlumnos(ActionEvent event){
        escenarioPrincipal.mostrarEscenaAsignacionAlumnos();
    }
    
    @FXML 
    public void clicInstructores(ActionEvent event){
        escenarioPrincipal.mostrarEscenaInstructores();
    }

    @FXML
    private void clicSobre(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "\nCarlos Eduardo Diaz Chacón" +
                "\nLic. Jorge Perez "+
                "\nTaller"+
                "\ncarné: 2018312"+
                "\nGrupo 1- Matutina"
                );
    }
    @FXML
    private void clicCerrar(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void clicReporteAlumnos(ActionEvent event) {
     Map<String, Object> parametros = new HashMap<>();
    parametros.put("nombre","Carlos Diaz");
    GenerarReporte.getInstance().mostrarReporte("ReporteAlumnos.jasper",parametros, "Reporte De Alumnos");
    }

    @FXML
    private void clicReporteSalones(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre","Carlos Diaz");
        GenerarReporte.getInstance().mostrarReporte("ReporteSalones.jasper",parametros, "Reporte De los salones");
    }

    @FXML
    private void clicReporteInsructores(ActionEvent event) {
     Map<String, Object> parametros = new HashMap<>();
    parametros.put("nombre","Carlos Diaz");
    GenerarReporte.getInstance().mostrarReporte("ReporteIntructores.jasper",parametros, "Reporte Instructores");
    }

    @FXML
    private void clicReporteAsignacionAlumnos(ActionEvent event) {
     Map<String, Object> parametros = new HashMap<>();
    parametros.put("nombre","Carlos Diaz");
    GenerarReporte.getInstance().mostrarReporte("ReporteAsignacionAlumnosInnerJoin.jasper",parametros, "Reporte De Asignación");
    }
}



