package org.in5bm.emanueljolonCarlosDiaz.controllers;

import java.sql.ResultSet;
import java.net.URL;
import java.net.URISyntaxException;
import java.sql.Timestamp;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.in5bm.emanueljolonCarlosDiaz.db.Conexion;
import org.in5bm.emanueljolonCarlosDiaz.models.*;
import org.in5bm.emanueljolonCarlosDiaz.reports.GenerarReporte;

/**
 *
 * @author charly diaz
 */
public class AsignacionAlumnosController implements Initializable {
    @FXML
    private TextField txtRegistros;
    @FXML
    private Button btnNuevo;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private Button btnEditar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnReporte;

    @FXML
    private TextField txtId;
    @FXML
    private ComboBox cmbAlumno;
    @FXML
    private ComboBox cmbCurso;
    @FXML
    private DatePicker dtpFechaAsig;
    @FXML
    private TableColumn<AsignacionAlumnos, Integer> colId;
    @FXML
    private TableColumn<AsignacionAlumnos, String> colCarneAlumno;
    @FXML
    private TableColumn<AsignacionAlumnos, Integer> colIdCurso;
    @FXML
    private TableColumn<AsignacionAlumnos, LocalDate> colFecha;
    @FXML
    private TableView<AsignacionAlumnos> tblAsignacionAlumnos;
    private Principal escenarioPrincipal;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getcargarDatos();
    }
    private int num=10;
    // ------------------------------------------------------------------
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    //--------------------------------------------------------------------

    private final String PAQUETE_IMAGES = "org/in5bm/emanueljolonCarlosDiaz/resources/images/";

    private ObservableList<AsignacionAlumnos> ListaAsignacionAlumnos;
    private ObservableList<Alumnos> ListaObservableAlumnos;

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    private Operacion operacion = Operacion.NINGUNO;

    public void actualizarRegistros(){
        txtRegistros.setText(String.valueOf(num));
    }

    public void getcargarDatos() {
        actualizarRegistros();
        tblAsignacionAlumnos.setItems(getAsignacionAlumnos());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCarneAlumno.setCellValueFactory(new PropertyValueFactory<>("alumnoId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaAsignacion"));
        cmbAlumno.setItems(getAlumnos());
    }

    public ObservableList getAsignacionAlumnos() {

        ArrayList<AsignacionAlumnos> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_asignacion_alumnos_read()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AsignacionAlumnos Asignacion = new AsignacionAlumnos();
                Asignacion.setId(rs.getInt(1));
                Asignacion.setAlumnoId(rs.getString(2));
                Asignacion.setCursoId(rs.getInt(3));
                Asignacion.setFechaAsignacion(rs.getTimestamp(4).toLocalDateTime());

                lista.add(Asignacion);
                System.out.println(Asignacion);
            }

            ListaAsignacionAlumnos = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar consultar la vista de AsignacionAlumnos");
            //Para mostrar el error especifico en la consola
            System.out.println("Message:" + e.getMessage());
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQLSate: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ListaAsignacionAlumnos;
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
        txtId.setText(String.valueOf(((AsignacionAlumnos)tblAsignacionAlumnos.getSelectionModel().getSelectedItem()).getId()));
        cmbAlumno.getSelectionModel().select(buscarAlumnos(((AsignacionAlumnos) 
        tblAsignacionAlumnos.getSelectionModel().getSelectedItem()).getAlumnoId ()));

        }
    }
    
    private Alumnos buscarAlumnos(String id){
        PreparedStatement pstmt = null;
        ResultSet rs= null;
        Alumnos a = null;
        try{
            pstmt = Conexion.getInstance().getConexion().prepareCall("{ CALL sp_alumnos_read_id(?)}");
            pstmt.setString(1,id);
            
            System.out.println(pstmt.toString());
            
            rs = pstmt.executeQuery();
            while (rs.next()){
                a = new Alumnos();
                a.setCarne(rs.getString(1));
                a.setNombre1(rs.getString(2));
                a.setNombre2(rs.getString(3));
                a.setApellido1(rs.getString(4));
                a.setApellido2(rs.getString(5));
            }
        }catch (SQLException e){
            System.err.println("\nse produjo un error al intentar buscar al alumno");
            System.err.println("Message: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return a;
    }

    public boolean existeElementoSeleccionado() {
        return ((tblAsignacionAlumnos.getSelectionModel().getSelectedItem() != null));

    }

    private void habilitarCampos() {
        txtId.setEditable(false);
        dtpFechaAsig.setEditable(true);

        txtId.setDisable(true);
        cmbAlumno.setDisable(false);
        dtpFechaAsig.setDisable(false);
    }

    private void desabilitarCampos() {
        txtId.setEditable(false);
        cmbAlumno.setEditable(false);
        dtpFechaAsig.setEditable(false);
        
        txtId.setDisable(true);
        cmbAlumno.setDisable(true);
        dtpFechaAsig.setDisable(true);

    }

    private void limpiarCampos() {
        txtId.clear();
        cmbAlumno.valueProperty().set(null);
        dtpFechaAsig.getEditor().clear();
    }

    public ObservableList getAlumnos() {
        ArrayList<Alumnos> arrayListAlumnos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Alumnos alumno = new Alumnos();

                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));

                System.out.println(alumno.toString());
                arrayListAlumnos.add(alumno);
            }

            ListaObservableAlumnos = FXCollections.observableArrayList(arrayListAlumnos);
        } catch (SQLException e) {
            System.err.println("\nse produjo un error al intentar listar a la tabla alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return ListaObservableAlumnos;
    }

    @FXML
    private void clicNuevo() {

        switch (operacion) {
            case NINGUNO:

                habilitarCampos();
                tblAsignacionAlumnos.setDisable(true);
                txtId.setEditable(false);
                txtId.setDisable(true);
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
                if (AsignacionAlumnos()) {
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

                    tblAsignacionAlumnos.setDisable(false);

                    operacion = Operacion.NINGUNO;
                    contarRegistrosMas();
                    actualizarRegistros();
                }
                break;
        }

    }

    private boolean AsignacionAlumnos() {
        AsignacionAlumnos asignacion= new AsignacionAlumnos();
        asignacion.setAlumnoId(((Alumnos)cmbAlumno.getSelectionModel().getSelectedItem()).getCarne());
        asignacion.setFechaAsignacion(dtpFechaAsig.getValue().atStartOfDay());
        
        PreparedStatement pstmt= null;
    
    try {
        pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_Asignaciones_alumnos_create(?,?)}");
        pstmt.setString(1,asignacion.getAlumnoId());
        pstmt.setTimestamp(2, Timestamp.valueOf(asignacion.getFechaAsignacion()));
        pstmt.execute();
        System.out.println(pstmt.toString());
        ListaAsignacionAlumnos.add(asignacion);
        return true;
        
        //sout para detectar errores
        
    } catch(SQLException e){
        System.err.println("\n se produjo un error al intentar insertar el siguiente alumno"+ asignacion.toString());
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
                tblAsignacionAlumnos.getSelectionModel().clearSelection();
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
                        if (eliminarAsignacion()) {
                            ListaAsignacionAlumnos.remove(tblAsignacionAlumnos.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            getcargarDatos();
                            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Control Academico");
                            alerta.setHeaderText(null);
                            alerta.setContentText("Eliminacion Exitosa");
                            Stage stag = (Stage) alerta.getDialogPane().getScene().getWindow();
                            stag.getIcons().add(new Image(PAQUETE_IMAGES + "advertencia.png"));
                            alerta.show();
                            contarRegistrosMenos();
                        } else if (botonC.get().equals(ButtonType.CANCEL)) {
                            System.out.println("Cancelando Operacion");
                            tblAsignacionAlumnos.getSelectionModel().clearSelection();
                            limpiarCampos();
                            actualizarRegistros();
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

    public boolean eliminarAsignacion() {
        
    if (existeElementoSeleccionado()) {
            AsignacionAlumnos Asignacion = (AsignacionAlumnos) tblAsignacionAlumnos.getSelectionModel().getSelectedItem();
            System.out.println(Asignacion.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_asignacion_alumnos_delete(?)}");
                pstmt.setInt(1, Asignacion.getId());
                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el Asignacion: " + Asignacion.toString());
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
    private void clicReporte()throws URISyntaxException {

       Map<String, Object> parametros = new HashMap<>();
    parametros.put("nombre","Carlos Diaz");
    GenerarReporte.getInstance().mostrarReporte("ReporteAsignacionAlumnosInnerJoin.jasper",parametros, "Reporte De Asignación");
    }

    @FXML

    
    private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
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
}
