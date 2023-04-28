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

public class MenuPrincipal2Controller implements Initializable{
  
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
    public void clicAsignacionAlumnos(ActionEvent event){
        escenarioPrincipal.mostrarEscenaAsignacionAlumnos();
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

   
}



