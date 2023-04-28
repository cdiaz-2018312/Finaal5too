
package org.in5bm.emanueljolonCarlosDiaz.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.in5bm.emanueljolonCarlosDiaz.models.Usuarios;
import org.in5bm.emanueljolonCarlosDiaz.system.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import org.in5bm.emanueljolonCarlosDiaz.db.Conexion;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
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
 * @author carlos diaz
 */
public class LoginController implements Initializable{

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private Button btnIngresar;
    
     ArrayList<Usuarios> lista= new ArrayList<>();
    
    private Principal escenarioPrincipal;
    
     public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
     @Override
    public void initialize(URL location, ResourceBundle resources){ 
        getUsuarios();
    }
    
    public void getUsuarios(){
    
    PreparedStatement pstmt = null;
    ResultSet rs=null;
    
    try{
            pstmt= Conexion.getInstance().getConexion().prepareCall("{CALL sp_login_read()}");
            rs= pstmt.executeQuery();
            while(rs.next()){
            Usuarios usu= new Usuarios();
            usu.setUsuario(rs.getString(1));
            usu.setPass(rs.getString(2));
            usu.setNombre(rs.getString(3));
            usu.setRol(rs.getInt(4));
            lista.add(usu);
            }
            
   
   
    }catch(SQLException e){
         System.err.println("Se produjo un error intentando consultar los datos para el login");
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
    
}
    String usuario1= "root";
    String Contraseña1="admin";
    String usuario2="kinal";
    String Contraseña2="12345";
@FXML
private void clicIngresar(){
    
  String  user=txtUsuario.getText();
    String contra=txtContraseña.getText();
   
 if(usuario1== user &&(Contraseña1==contra)){
     escenarioPrincipal.mostrarEscenaMenuPrincipal();
    
 } else if (usuario2== user && Contraseña2==contra){
     escenarioPrincipal.mostrarEscenaMenuPrincipal2();
        }else {
  Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setTitle("ALERTA!!!!");
    alerta.setHeaderText("MOUNTAIN CONTROL");
    alerta.setHeaderText(null);
    alerta.setContentText("Contraseña o usuario Incorrecto");
    Stage stage=(Stage) alerta.getDialogPane().getScene().getWindow();
   alerta.show(); 
 }
    
}
}
