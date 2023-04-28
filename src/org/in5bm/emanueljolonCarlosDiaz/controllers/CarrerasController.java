package org.in5bm.emanueljolonCarlosDiaz.controllers;

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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.sql.SQLException;
import org.in5bm.emanueljolonCarlosDiaz.system.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.in5bm.emanueljolonCarlosDiaz.db.Conexion;
import org.in5bm.emanueljolonCarlosDiaz.models.Alumnos;
import org.in5bm.emanueljolonCarlosDiaz.models.Carreras;


/**
 *
 * @author etzul
 */
public class CarrerasController implements Initializable {
    
   //Tipo de dato seguido del nombre de la variable o id
   //SE LE AGREGA EL @FXML PARA PODE HACER LA INYECCION DE DEPENDENCIAS
   //1) UNA FORMA ES ESCRIBIRLA
   //2) OTRA ES YENDO AL MENU DE SCENE BUILDER Y CEAR LAS DEPENDENCIAS
   //3) SELLECIONAR LA VISTA EN NETBEANS Y DARLE MAKE CONTROLLER
    
    private Principal escenarioPrincipal;

    
    @FXML
    private TextField txtCodigo;
    
    @FXML
    private TextField txtCarrera;

    @FXML
    private TextField txtGrado;

    @FXML
    private TextField txtSeccion;

    @FXML
    private TextField txtJornada;

    
    
    
    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;
    
    
   
   @FXML
   private TableView tblCarreras;
   
   @FXML
   private TableColumn colCodigo;
   
   @FXML
   private TableColumn colCarrera;
   
   @FXML
   private TableColumn colGrado;
   
   @FXML
   private TableColumn colSeccion;
   
   @FXML
   private TableColumn colJornada;
   
   @FXML
   private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
   private final  String PAQUETE_IMAGES = "org/in5bm/emanueljolonCarlosDiaz/resources/images/";
   
   //CREANDO OBSERVABLELIST
   private ObservableList<Carreras>ListaCarreras;
   
   @Override
   public void initialize(URL location, ResourceBundle resources){
        cargarDatos();
   }

   
   public void cargarDatos(){
        tblCarreras.setItems(getCarreras());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Carreras, String>("codigo"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<Carreras, String>("carrera"));
        colGrado.setCellValueFactory(new PropertyValueFactory<Carreras, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<Carreras, String>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<Carreras, String>("jornada"));        
    }
   
   
   
   
   public ObservableList getCarreras(){
        
        ArrayList<Carreras> lista = new ArrayList<>();
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_read()");
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                Carreras carrera = new Carreras();
                carrera.setCodigo(rs.getString(1));
                carrera.setCarrera(rs.getString(2));
                carrera.setGrado(rs.getString(3));
                carrera.setSeccion(rs.getString(4));
                carrera.setJornada(rs.getString(5));
                
                lista.add(carrera);
                System.out.println(carrera.toString());
            }
            
            ListaCarreras = FXCollections.observableArrayList(lista);
            
        }catch(SQLException e){
            System.err.println("Se produjo un error al intentar consultar la vista de carreras");
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
        return ListaCarreras;
    }

public void habilitarCampos(){
    txtCodigo.setEditable(true);
    txtCarrera.setEditable(true);
    txtGrado.setEditable(true);
    txtSeccion.setEditable(true);
    txtJornada.setEditable(true);
    
 
    
    txtCodigo.setDisable(false);
    txtCarrera.setDisable(false);
    txtGrado.setDisable(false);
    txtSeccion.setDisable(false);
    txtJornada.setDisable(false);
    
    
    
}

private void limpiarCampos(){
    txtCodigo.setText("");
    txtCarrera.setText("");
    txtGrado.setText("");
    txtSeccion.setText("");
    txtJornada.setText("");
 
}


@FXML
private void clicNuevo(){
   limpiarCampos(); 
   habilitarCampos();
   
   
    
}

@FXML
private void clicEditar(){
    habilitarCampos();
   
}

@FXML
private void clicReporte()throws URISyntaxException{
    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setTitle("ALERTA!!!!");
    alerta.setHeaderText("MOUNTAIN CONTROL");
    alerta.setHeaderText(null);
    alerta.setContentText("COntraseña o usuario Incorrecto");
    Stage stage=(Stage) alerta.getDialogPane().getScene().getWindow();
    Image ico=new Image(getClass().getResource("../resources/images/advertencia.png").toURI().toString());
    stage.getIcons().add(ico);
    alerta.show();
}




public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }




}
    

