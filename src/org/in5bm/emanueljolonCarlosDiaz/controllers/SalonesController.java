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
import javafx.scene.control.ButtonType;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.in5bm.emanueljolonCarlosDiaz.db.Conexion;
import org.in5bm.emanueljolonCarlosDiaz.models.Salones;
import org.in5bm.emanueljolonCarlosDiaz.reports.GenerarReporte;




/**
 *
 * @author etzul-carlosdiaz
 */
public class SalonesController implements Initializable {
    
   //Tipo de dato seguido del nombre de la variable o id
   //SE LE AGREGA EL @FXML PARA PODE HACER LA INYECCION DE DEPENDENCIAS
   //1) UNA FORMA ES ESCRIBIRLA
   //2) OTRA ES YENDO AL MENU DE SCENE BUILDER Y CEAR LAS DEPENDENCIAS
   //3) SELLECIONAR LA VISTA EN NETBEANS Y DARLE MAKE CONTROLLER
    
   @FXML 
   private TextField txtRegistros;
   
    @FXML
    private TextField txtCodigo;
    
    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtCapacidad;

    @FXML
    private TextField txtEdificio;

    @FXML
    private TextField txtNivel;
   
   @FXML
   private Button btnNuevo;
   
   @FXML
   private Button btnEditar;
   
   @FXML
   private Button btnEliminar;
   
   @FXML
   private Button btnReporte;
   
   @FXML
   private TableView tblSalones;
   
   @FXML
   private TableColumn colCodigo;
   
   @FXML
   private TableColumn colDescripcion;
   
   @FXML
   private TableColumn colCapacidad;
   
   @FXML
   private TableColumn colEdificio;
   
   @FXML
   private TableColumn colNivel;
   
   private int currentValue;
   private int num=10;
   
   private Principal escenarioPrincipal;
   
   @FXML
   private ImageView imgReporte;
   
   @FXML
   private ImageView imgEditar;
   
   @FXML 
   private ImageView imgEliminar;

   @FXML  
   private ImageView imgNuevo;
   
    @FXML
   private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
   
   private final  String PAQUETE_IMAGES = "org/in5bm/emanueljolonCarlosDiaz/resources/images/";
   
   private ObservableList<Salones>ListaSalones;
   
   private enum Operacion{
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    
    private Operacion operacion = Operacion.NINGUNO;
    
    
 
   
    @Override
    public void initialize(URL location, ResourceBundle resources){
        getcargarDatos();
    }
    
    public void getcargarDatos(){
        actualizarRegistros();
        tblSalones.setItems(getSalones());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Salones, String>("codigo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones, String>("descripcion"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("capacidad"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones, String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("nivel"));
        desabilitarCampos();
        
    }

    
    //metodo para poder seleccionar un registro
    @FXML
    public void seleccionarElemento(){
       if(existeElementoSeleccionado()){
      
        txtCodigo.setText(((Salones)tblSalones.getSelectionModel().getSelectedItem()).getCodigo());
        txtDescripcion.setText(((Salones)tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());
        txtCapacidad.setText(String.valueOf(((Salones)tblSalones.getSelectionModel().getSelectedItem()).getCapacidad()));
        txtEdificio.setText(((Salones)tblSalones.getSelectionModel().getSelectedItem()).getEdificio());
        txtNivel.setText(String.valueOf(((Salones)tblSalones.getSelectionModel().getSelectedItem()).getNivel()));
        
        //txtCapacidad.setText(String.valueOf(((Salones) 
        //tblSalones.getSelectionModel().getSelectedItem()).getCapacidadMaxima()));
        
        }
    
    }   
    
    public boolean existeElementoSeleccionado(){
        return((tblSalones.getSelectionModel().getSelectedItem() != null));
       
    }
    
    
    public void actualizarRegistros(){
    txtRegistros.setText(String.valueOf(num));
    }
   
        
   public ObservableList getSalones(){
        
        ArrayList<Salones> lista = new ArrayList<>();
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_read()}");
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                Salones salon = new Salones();
                salon.setCodigo(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidad(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));
                
                lista.add(salon);
                System.out.println(salon.toString());
            }
            
            ListaSalones = FXCollections.observableArrayList(lista);
            
        }catch(SQLException e){
            System.err.println("Se produjo un error al intentar consultar la vista de Salones");
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
        return ListaSalones;
    }




private void habilitarCampos(){
    txtCodigo.setEditable(false);
    txtDescripcion.setEditable(true);
    txtCapacidad.setEditable(true);
    txtEdificio.setEditable(true);
    txtNivel.setEditable(true);
    
    txtCodigo.setDisable(true);
    txtDescripcion.setDisable(false);
    txtCapacidad.setDisable(false);
    txtEdificio.setDisable(false);
    txtNivel.setDisable(false);
}

private void desabilitarCampos(){
    txtCodigo.setEditable(false);
    txtDescripcion.setEditable(false);
    txtCapacidad.setEditable(false);
    txtEdificio.setEditable(false);
    txtNivel.setEditable(false);
    
    txtCodigo.setDisable(true);
    txtDescripcion.setDisable(true);
    txtCapacidad.setDisable(true);
    txtEdificio.setDisable(true);
    txtNivel.setDisable(true);    
    
}





private void limpiarCampos(){
    txtCodigo.clear();
    txtDescripcion.clear();
    txtCapacidad.clear();
    txtEdificio.clear();
    txtNivel.clear();
}


@FXML
private void clicNuevo(){

   switch(operacion){
       case NINGUNO:
           
           habilitarCampos();
           tblSalones.setDisable(true);
           txtCodigo.setEditable(true);
           txtCodigo.setDisable(false);
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
           if(agregarSalon()){
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
              
              tblSalones.setDisable(false);
              
             operacion = Operacion.NINGUNO;
             
             contarRegistrosMas();
             actualizarRegistros();
           }
           break;
    }    
   
}



private boolean agregarSalon(){
    Salones salon = new Salones();
    salon.setCodigo(txtCodigo.getText());
    salon.setDescripcion(txtDescripcion.getText());
    salon.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
    salon.setEdificio(txtEdificio.getText());
    salon.setNivel(Integer.parseInt(txtNivel.getText()));
    PreparedStatement pstmt = null;
    
    try {
        pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_salones_create(?,?,?,?,?)}");
        pstmt.setString(1, salon.getCodigo());
        pstmt.setString(2, salon.getDescripcion());
        pstmt.setString(3, String.valueOf(salon.getCapacidad()));
        pstmt.setString(4, salon.getEdificio());
        pstmt.setString(5, String.valueOf(salon.getNivel()));
        pstmt.execute();
        System.out.println(pstmt.toString());
        ListaSalones.add(salon);
        return true;
        
        //sout para detectar errores
        
    } catch(SQLException e){
        System.err.println("\n se produjo un error al intentar insertar el siguiente Salon"+ salon.toString());
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
           tblSalones.getSelectionModel().clearSelection();
           
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
            tblSalones.setDisable(false);
            limpiarCampos();
            desabilitarCampos();
            
            
            operacion = Operacion.NINGUNO;
            
            break;
        case ACTUALIZAR:
            if(existeElementoSeleccionado()){
              if(actualizarSalon()){
                getcargarDatos();
                limpiarCampos();
                desabilitarCampos();
                
                tblSalones.getSelectionModel().clearSelection();
                
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);

                btnEditar.setText("Editar");
                imgEditar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));
              
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));
              
                btnReporte.setDisable(false);
                btnReporte.setVisible(true);
              
                tblSalones.setDisable(false);
              
                operacion = Operacion.NINGUNO;   
                }
            }
        break;
    }   
    
    
}
//  txtNivel.setText(String.valueOf(((Salones)tblSalones.getSelectionModel().getSelectedItem()).getNivel()));

private boolean actualizarSalon(){
    Salones salon = new Salones();
    salon.setCodigo(txtCodigo.getText());
    salon.setDescripcion(txtDescripcion.getText());
    salon.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
    salon.setEdificio(txtEdificio.getText());
    salon.setNivel(Integer.parseInt(txtNivel.getText()));
    
    PreparedStatement pstmt = null;
    
    try{
        pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_update(?,?,?,?,?)}");
        
        pstmt.setString(1, salon.getDescripcion());
        pstmt.setString(2, String.valueOf(salon.getCapacidad()));
        pstmt.setString(3, salon.getEdificio());
        pstmt.setString(4, String.valueOf(salon.getNivel()));
        pstmt.setString(5, salon.getCodigo());
        System.out.println(pstmt.toString());
        pstmt.execute();
        return true;
    }catch(SQLException e){
        System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro: "+ salon.toString());
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
                tblSalones.getSelectionModel().clearSelection();
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
                        if (eliminarSalon()) {
                            ListaSalones.remove(tblSalones.getSelectionModel().getFocusedIndex());
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
                                tblSalones.getSelectionModel().clearSelection();
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
    

public boolean eliminarSalon() {
        if (existeElementoSeleccionado()) {
            Salones salon = (Salones) tblSalones.getSelectionModel().getSelectedItem();
            System.out.println(salon.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_salones_delete(?)}");
                pstmt.setString(1, salon.getCodigo());
                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el salon: " + salon.toString());
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
    GenerarReporte.getInstance().mostrarReporte("ReporteSalones.jasper",parametros, "Reporte De los salones");
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
private void alertaCampos(){
   Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setTitle("ALERTA!!!!");
    alerta.setHeaderText("MOUNTAIN CONTROL");
    alerta.setHeaderText(null);
    alerta.setContentText("algun/os campo/s esta/n vacio/s, por favor llenelo/s");
    Stage stage=(Stage) alerta.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image(PAQUETE_IMAGES + "advertencia.png"));
    
    
    alerta.show();  
}

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    
    

}