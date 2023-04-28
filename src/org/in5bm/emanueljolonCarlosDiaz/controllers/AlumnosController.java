package org.in5bm.emanueljolonCarlosDiaz.controllers;

import java.sql.ResultSet;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.sql.SQLException;
import org.in5bm.emanueljolonCarlosDiaz.system.Principal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.in5bm.emanueljolonCarlosDiaz.db.Conexion;
import org.in5bm.emanueljolonCarlosDiaz.models.Alumnos;
import org.in5bm.emanueljolonCarlosDiaz.models.Salones;
import org.in5bm.emanueljolonCarlosDiaz.reports.GenerarReporte;




/**
 *
 * @author etzul
 */
public class AlumnosController implements Initializable {
    
   //Tipo de dato seguido del nombre de la variable o id
   //SE LE AGREGA EL @FXML PARA PODE HACER LA INYECCION DE DEPENDENCIAS
   //1) UNA FORMA ES ESCRIBIRLA
   //2) OTRA ES YENDO AL MENU DE SCENE BUILDER Y CEAR LAS DEPENDENCIAS
   //3) SELLECIONAR LA VISTA EN NETBEANS Y DARLE MAKE CONTROLLER
   
   @FXML 
   private TextField txtRegistros;
   @FXML
   private TextField txtCarnet;
    
   @FXML
   private TextField txtNombre1;

   @FXML
   private TextField txtNombre2;

   @FXML
   private TextField txtNombre3;

   @FXML
   private TextField txtApellido1;

   @FXML
   private TextField txtApellido2;
   
   @FXML
   private ImageView imgNuevo;
   
   @FXML
    private ImageView imgEditar;
   
   @FXML
   private Button btnNuevo;
   
   @FXML
   private Button btnEditar;
   
   @FXML
   private Button btnEliminar;
   
   @FXML
   private ImageView imgEliminar;
   
   @FXML
   private Button btnReporte;
   
   @FXML
   private TableView tblAlumnos;
   
   @FXML
   private TableColumn colCarne;
   
   @FXML
   private TableColumn colNombre1;
   
   @FXML
   private TableColumn colNombre2;
   
   @FXML
   private TableColumn colNombre3;
   
   @FXML
   private TableColumn colApellido1;
   
   @FXML
   private TableColumn colApellido2;
   
   @FXML
   private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
   
   private final  String PAQUETE_IMAGES = "org/in5bm/emanueljolonCarlosDiaz/resources/images/";
   
   private ObservableList<Alumnos>ListaAlumnos;
   
   private enum Operacion{
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    
    private Operacion operacion = Operacion.NINGUNO;
    
    
    private Principal escenarioPrincipal;
    
    private int num=6;
   
   
    @Override
    public void initialize(URL location, ResourceBundle resources){
        getcargarDatos();
    }
    
    public void getcargarDatos(){
        actualizarRegistros();
        tblAlumnos.setItems(getAlumnos());
        colCarne.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("carne"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido2"));
        
        
    }

    
    //metodo para poder seleccionar un registro
    @FXML
    public void seleccionarElemento(){
       if(existeElementoSeleccionado()){
        txtCarnet.setText(((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem()).getCarne());
        txtNombre1.setText(((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem()).getNombre1());
        txtNombre2.setText(((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem()).getNombre2());
        txtNombre3.setText(((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem()).getNombre3());
        txtApellido1.setText(((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem()).getApellido1());
        txtApellido2.setText(((Alumnos)tblAlumnos.getSelectionModel().getSelectedItem()).getApellido2());
        
       }
    
    }   
    
    public boolean existeElementoSeleccionado(){
        return((tblAlumnos.getSelectionModel().getSelectedItem() != null));
       
    }
    
    
     public void actualizarRegistros(){
    txtRegistros.setText(String.valueOf(num));
    }
   
        
   public ObservableList getAlumnos(){
        
        ArrayList<Alumnos> lista = new ArrayList<>();
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_read()}");
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                Alumnos alumno = new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));
                
                lista.add(alumno);
                System.out.println(alumno.toString());
            }
            
            ListaAlumnos = FXCollections.observableArrayList(lista);
            
        }catch(SQLException e){
            System.err.println("Se produjo un error al intentar consultar la vista de alumnos");
            //Para mostrar el error especifico en la consola
            System.out.println("Message:"+ e.getMessage());
            System.out.println("Error code: " +e.getErrorCode());
            System.out.println("SQLSate: "+ e.getSQLState());
            e.printStackTrace();
        }catch(Exception e ){
            e.printStackTrace();
        } finally{
            try{
                if (rs != null){
                    rs.close();
                }
                if (pstmt != null){
                    pstmt.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return ListaAlumnos;
    }




private void habilitarCampos(){
    txtCarnet.setEditable(false);
    txtNombre1.setEditable(true);
    txtNombre2.setEditable(true);
    txtNombre3.setEditable(true);
    txtApellido1.setEditable(true);
    txtApellido2.setEditable(true);
    
    txtCarnet.setDisable(true);
    txtNombre1.setDisable(false);
    txtNombre2.setDisable(false);
    txtNombre3.setDisable(false);
    txtApellido1.setDisable(false);
    txtApellido2.setDisable(false);
    
    
}

private void desabilitarCampos(){
    txtCarnet.setEditable(false);
    txtNombre1.setEditable(false);
    txtNombre2.setEditable(false);
    txtNombre3.setEditable(false);
    txtApellido1.setEditable(false);
    txtApellido2.setEditable(false);
    
    txtCarnet.setDisable(true);
    txtNombre1.setDisable(true);
    txtNombre2.setDisable(true);
    txtNombre3.setDisable(true);
    txtApellido1.setDisable(true); 
    txtApellido2.setDisable(true);
    
    
}
private void limpiarCampos(){
    txtCarnet.setText("");
    txtNombre1.clear();
    txtNombre2.clear();
    txtNombre3.clear();
    txtApellido1.clear();
    txtApellido2.clear();
}
@FXML
private void clicNuevo(){

   switch(operacion){
       case NINGUNO:
           
           habilitarCampos();
           tblAlumnos.setDisable(true);
           txtCarnet.setEditable(true);
           txtCarnet.setDisable(false);
           limpiarCampos();
           btnNuevo.setText("Guardar");
           imgNuevo.setImage(new Image(PAQUETE_IMAGES + "guardar.png"));
           
           btnEditar.setText("Cancelar");
           imgEditar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png")); 
           
           btnEliminar.setDisable(true);
           btnEliminar.setVisible(false);
           
           btnReporte.setDisable(true);
           btnReporte.setVisible(false);
           
           operacion = Operacion.GUARDAR;
           break;
       case GUARDAR:
           if(agregarAlumno()){
              getcargarDatos();
              limpiarCampos();
              desabilitarCampos();
              
              btnNuevo.setText("Nuevo");
              imgNuevo.setImage(new Image(PAQUETE_IMAGES + "agregar-archivo.png"));
              
              btnEditar.setText("Editar");
              imgEditar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));
              
              btnEliminar.setDisable(false);
              btnEliminar.setVisible(true);
              
              btnReporte.setDisable(false);
              btnReporte.setVisible(true);
              
              tblAlumnos.setDisable(false);
              
             operacion = Operacion.NINGUNO;
             contarRegistrosMas();
             actualizarRegistros();
           }
           break;
    }    
   
}

private boolean agregarAlumno(){
    Alumnos alumno = new Alumnos();
    alumno.setCarne(txtCarnet.getText());
    alumno.setNombre1(txtNombre1.getText());
    alumno.setNombre2(txtNombre2.getText());
    alumno.setNombre3(txtNombre2.getText());
    alumno.setApellido1(txtApellido2.getText());
    alumno.setApellido2(txtApellido1.getText());
    
    PreparedStatement pstmt = null;
    
    try {
        pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_alumnos_create(?,?,?,?,?,?)}");
        pstmt.setString(1,alumno.getCarne());
        pstmt.setString(2, alumno.getNombre1());
        pstmt.setString(3, alumno.getNombre2());
        pstmt.setString(4, alumno.getNombre3());
        pstmt.setString(5, alumno.getApellido1());
        pstmt.setString(6, alumno.getApellido2());
        pstmt.execute();
        System.out.println(pstmt.toString());
        ListaAlumnos.add(alumno);
        
        return true;
        
        //sout para detectar errores
        
    } catch(SQLException e){
        System.err.println("\n se produjo un error al intentar insertar el siguiente alumno"+ alumno.toString());
        e.printStackTrace();
    } catch(Exception e){
        e.printStackTrace();
    }finally{
        try{
            if(pstmt != null){
                pstmt.close();
            }
            }catch(Exception e){
                    e.printStackTrace();
            }
    }
    return false;
}

@FXML
private void clicEditar(){
    switch(operacion){
        case NINGUNO:
          if(existeElementoSeleccionado()){
              
           habilitarCampos();
           
           btnNuevo.setDisable(true);
           btnNuevo.setVisible(false);
           
           btnReporte.setDisable(true);
           btnReporte.setVisible(false);
           
           
           btnEliminar.setText("Cancelar");
           imgEliminar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png"));
           
           btnEditar.setText("Guardar");
           imgEditar.setImage(new Image(PAQUETE_IMAGES + "guardar.png"));
           tblAlumnos.getSelectionModel().clearSelection();
           
           operacion = Operacion.ACTUALIZAR;
          }else{
          
              Alert alert = new Alert(Alert.AlertType.WARNING);
              alert.setTitle("Control Academico");
              alert.setHeaderText(null);
              alert.setContentText("Antes de continuar, seleccione un registro.");
              Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
              stage.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
              alert.show();
                      
          }
           break;
        case GUARDAR://Cancelar
            btnNuevo.setText("Nuevo");
            imgNuevo.setImage(new Image(PAQUETE_IMAGES + "agregar-archivo.png"));
            
            btnEditar.setText("Editar");
            imgEditar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));
            
            btnEliminar.setDisable(false);
            btnEliminar.setVisible(true);
           
            btnReporte.setDisable(false);
            btnReporte.setVisible(true);
            tblAlumnos.setDisable(false);
            limpiarCampos();
            desabilitarCampos();
            
            
            operacion = Operacion.NINGUNO;
            
            break;
        case ACTUALIZAR:
            if(existeElementoSeleccionado()){
              if(actualizarAlumno()){
                  
                  
                getcargarDatos();
                limpiarCampos();
                desabilitarCampos();
                
                tblAlumnos.getSelectionModel().clearSelection();
                
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);

                btnEditar.setText("Editar");
                imgEditar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));
              
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));
              
                btnReporte.setDisable(false);
                btnReporte.setVisible(true);
              
                tblAlumnos.setDisable(false);
              
                operacion = Operacion.NINGUNO;
                     
                }
            }
        break;
    } 
}

private boolean actualizarAlumno(){
    Alumnos alumno = new Alumnos();
    alumno.setCarne(txtCarnet.getText());
    alumno.setNombre1(txtNombre1.getText());
    alumno.setNombre2(txtNombre2.getText());
    alumno.setNombre3(txtNombre3.getText());
    alumno.setApellido1(txtApellido1.getText());
    alumno.setApellido2(txtApellido2.getText());
    
    PreparedStatement pstmt = null;
    
    try{
        pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_update(?,?,?,?,?,?)}");
        pstmt.setString(1, alumno.getCarne());
        pstmt.setString(2, alumno.getNombre1());
        pstmt.setString(3, alumno.getNombre2());
        pstmt.setString(4, alumno.getNombre3());
        pstmt.setString(5, alumno.getApellido1());
        pstmt.setString(6, alumno.getApellido2());
        System.out.println(pstmt.toString());
        pstmt.execute();
        return true;
    }catch(SQLException e){
        System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro: "+ alumno.toString());
        e.printStackTrace();
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        try{
            if(pstmt != null){
                pstmt.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    return false;
    
}
@FXML
private void clicEliminar() {
        switch (operacion) {
            case ACTUALIZAR: //Cancelar la actualizacion
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);

                btnEditar.setText("Editar");
                imgEditar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);
                tblAlumnos.getSelectionModel().clearSelection();
                limpiarCampos();
                desabilitarCampos();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    
                    Alert alertaC = new Alert(Alert.AlertType.CONFIRMATION);
                    alertaC.setTitle("Control Academico");
                    alertaC.setHeaderText(null);
                    alertaC.setContentText("¿Seguro que quieres eliminar el registro?");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alertaC.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "advertencia.png"));

                    Optional<ButtonType> botonC = alertaC.showAndWait();

                    if (botonC.get().equals(ButtonType.OK)) {
                        if (eliminarAlumno()) { 
                            ListaAlumnos.remove(tblAlumnos.getSelectionModel().getFocusedIndex());
                            getcargarDatos();
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Control Academico");
                            alerta.setHeaderText(null);
                            alerta.setContentText("Eliminacion Exitosa");
                            Stage stag = (Stage) alerta.getDialogPane().getScene().getWindow();
                            stag.getIcons().add(new Image(PAQUETE_IMAGES + "advertencia.png"));
                            alerta.show();
                            contarRegistrosMenos();
                            actualizarRegistros();
                        }else if(botonC.get().equals(ButtonType.CANCEL)){
                            System.out.println("Cancelando Operacion");
                                tblAlumnos.getSelectionModel().clearSelection();
                                limpiarCampos();
                        }
                    }
                    } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Mountain Control");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar selecciona un registro");
                    Stage dialog = new Stage();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "advertencia.png"));
                    alert.show();
                }
                break;
                }
        }

public boolean eliminarAlumno() {
        if (existeElementoSeleccionado()) {
            Alumnos alumno = (Alumnos) tblAlumnos.getSelectionModel().getSelectedItem();
            System.out.println(alumno.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_alumnos_delete(?)}");
                pstmt.setString(1, alumno.getCarne());
                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el alumno: " + alumno.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return false;
}
@FXML
private void clicReporte()throws URISyntaxException{
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("nombre","Carlos Diaz");
    GenerarReporte.getInstance().mostrarReporte("ReporteAlumnos.jasper",parametros, "Reporte De Alumnos");
   
    
}

public void  contarRegistrosMas() {
    num++;
    System.out.println("--------------------------------------------------------");
    System.out.println("Actualizacion en el numero de registros: "+ num);
     System.out.println("-------------------------------------------------------");
}
public void contarRegistrosMenos(){
    num--;
    System.out.println("--------------------------------------------------------");
    System.out.println("Actualizacion en el numero de registros: "+ num);
     System.out.println("-------------------------------------------------------");
}

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}