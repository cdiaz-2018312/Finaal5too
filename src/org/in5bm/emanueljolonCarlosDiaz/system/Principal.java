
package org.in5bm.emanueljolonCarlosDiaz.system;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.in5bm.emanueljolonCarlosDiaz.controllers.AlumnosController;
import org.in5bm.emanueljolonCarlosDiaz.controllers.SalonesController;
import org.in5bm.emanueljolonCarlosDiaz.controllers.CarrerasController;
import org.in5bm.emanueljolonCarlosDiaz.controllers.MenuPrincipalController;
import org.in5bm.emanueljolonCarlosDiaz.controllers.AsignacionAlumnosController;
import org.in5bm.emanueljolonCarlosDiaz.controllers.InstructoresController;
import org.in5bm.emanueljolonCarlosDiaz.controllers.LoginController;
import org.in5bm.emanueljolonCarlosDiaz.controllers.MenuPrincipal2Controller;

/**
 *
 * @author Emanuel Esaú Jolón Tzul Carlos Diaz
 */
//constantes todo en mayuscula usando snake case


public class Principal extends Application {
   
    public static void main(String[] args) {
        launch(args); 
    }

    
    private final  String PAQUETE_IMAGES = "org/in5bm/emanueljolonCarlosDiaz/resources/images/";
    private final  String PAQUETE_VIEW = "../views/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
   

    
    @Override
    public void start(Stage primaryStage)throws Exception {
        this.escenarioPrincipal = primaryStage;
        this.escenarioPrincipal.setTitle("Mountain Control");
        this.escenarioPrincipal.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();
        mostrarEscenaPrincipal();
        
        
    }

    public Initializable cambiarEscena(String vistaFxml, int ancho, int alto) throws IOException {
        System.out.println("Cambiando escena: "+ PAQUETE_VIEW + vistaFxml);
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEW + vistaFxml));
        Scene scene = new Scene((AnchorPane) cargadorFXML.load(), ancho, alto);
        this.escenarioPrincipal.setScene(scene);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        return (Initializable) cargadorFXML.getController();
    }
    
    
    public void mostrarEscenaPrincipal(){
        try {
            System.out.println("Se ingreso al login");
            LoginController loginController = (LoginController)cambiarEscena("LoginView.fxml", 369, 244);
            loginController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\n se produjo un eror al cargar el Login");
            ex.printStackTrace();
        }    
    }
    public void mostrarEscenaMenuPrincipal(){
        try {
            System.out.println("Se ingreso a menu principal");
            MenuPrincipalController menuController = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 1152, 648);
            menuController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\n se produjo un eror al cargar el menu principal");
            ex.printStackTrace();
        }    
    }
    public void mostrarEscenaMenuPrincipal2(){
        try {
            System.out.println("Se ingreso a menu principal2");
            MenuPrincipalController menuController = (MenuPrincipalController)cambiarEscena("MenuPrincipal2View.fxml", 1152, 648);
            menuController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\n se produjo un eror al cargar el menu principal");
            ex.printStackTrace();
        }    
    }
    
    public void mostrarEscenaAlumnos(){
        try{
            AlumnosController alumnosController = (AlumnosController) cambiarEscena("AlumnosView.fxml", 1152, 648);
            alumnosController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.println("\n se produjo un error en la vista alumnos");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaSalones(){
        try{
            SalonesController salonesController = (SalonesController) cambiarEscena("SalonesView.fxml", 1152, 648);
            salonesController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.println("\n se produjo un error en la vista Salones");
            ex.printStackTrace();
        }
        
        
    }
    
    
    public void mostrarEscenaCarreras(){
        try{
            CarrerasController carrerasController = (CarrerasController) cambiarEscena("CarrerasView.fxml", 1152, 648);
            carrerasController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.println("\n se produjo un error en la vista carreras");
            ex.printStackTrace();
        }
    }
        public void mostrarEscenaAsignacionAlumnos(){
        try{
            AsignacionAlumnosController asig = (AsignacionAlumnosController) cambiarEscena("AsignacionAlumnosView.fxml", 1152, 648);
            asig.setEscenarioPrincipal(this);
            }catch(Exception ex){
            System.err.println("\n se produjo un error en la vista AsigAlumnos");
            ex.printStackTrace();
            }
        }
    public void mostrarEscenaInstructores(){
        try{
            InstructoresController Ins = (InstructoresController)cambiarEscena("InstructoresView.fxml",1152, 648);
            Ins.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.err.println("\n se produjo un error en la vista Instructores");

            e.printStackTrace();
        }
        
    }
}
        
        
    





