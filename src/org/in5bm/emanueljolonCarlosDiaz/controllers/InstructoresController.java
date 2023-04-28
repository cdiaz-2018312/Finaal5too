package org.in5bm.emanueljolonCarlosDiaz.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.in5bm.emanueljolonCarlosDiaz.db.Conexion;
import org.in5bm.emanueljolonCarlosDiaz.models.Instructores;
import org.in5bm.emanueljolonCarlosDiaz.models.Instructores;
import org.in5bm.emanueljolonCarlosDiaz.reports.GenerarReporte;

/**
 *
 * @author charl
 */
public class InstructoresController implements Initializable {
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
    private TextField txtId;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtFecha;
    @FXML
    private TableView<Instructores> tblInstructores;
    @FXML
    private TableColumn<Instructores, Integer> colId;
    @FXML
    private TableColumn<Instructores, String> colNombre1;
    @FXML
    private TableColumn<Instructores, String> colNombre2;
    @FXML
    private TableColumn<Instructores, String> colNombre3;
    @FXML
    private TableColumn<Instructores, String> colApellido1;
    @FXML
    private TableColumn<Instructores, String> colApellido2;
    @FXML
    private TableColumn<Instructores, String> colDireccion;
    @FXML
    private TableColumn<Instructores, String> colEmail;
    @FXML
    private TableColumn<Instructores, String> colTelefono;
    @FXML
    private TableColumn<Instructores, String> colFecha;
    private final String PAQUETE_IMAGES = "org/in5bm/emanueljolonCarlosDiaz/resources/images/";
    private Principal escenarioPrincipal;
    private int num= 10;

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private ObservableList<Instructores> ListaInstructores;

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getcargarDatos();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------

    public void getcargarDatos() {
        actualizarRegistros();
        tblInstructores.setItems(getInstructores());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
    }

    public boolean existeElementoSeleccionado() {
        return ((tblInstructores.getSelectionModel().getSelectedItem() != null));
    }
    public void actualizarRegistros(){
    txtRegistros.setText(String.valueOf(num));
    }

    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtId.setText(String.valueOf(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getId()));
            txtNombre1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido2());
            txtDireccion.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getDireccion());
            txtEmail.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getEmail());
            txtTelefono.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getTelefono());
            txtFecha.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getFecha());
        }
    }

    public ObservableList getInstructores() {

        ArrayList<Instructores> instructor = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_read()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Instructores ins = new Instructores();
                ins.setId(rs.getInt(1));
                ins.setNombre1(rs.getString(2));
                ins.setNombre2(rs.getString(3));
                ins.setNombre3(rs.getString(4));
                ins.setApellido1(rs.getString(5));
                ins.setApellido2(rs.getString(6));
                ins.setDireccion(rs.getString(7));
                ins.setEmail(rs.getString(8));
                ins.setTelefono(rs.getString(9));
                ins.setFecha(rs.getString(10));

                instructor.add(ins);
                System.out.println(ins.toString());
            }

            ListaInstructores = FXCollections.observableArrayList(instructor);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar consultar la vista de inss");
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
        return ListaInstructores;
    }

    private void habilitarCampos() {
        txtId.setEditable(false);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        txtTelefono.setEditable(true);
        txtFecha.setEditable(true);

        txtId.setDisable(true);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
        txtDireccion.setDisable(false);
        txtEmail.setDisable(false);
        txtTelefono.setDisable(false);
        txtFecha.setDisable(false);
    }

    private void desabilitarCampos() {
        txtId.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        txtTelefono.setEditable(false);
        txtFecha.setEditable(false);

        txtId.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
        txtDireccion.setDisable(true);
        txtEmail.setDisable(true);
        txtTelefono.setDisable(true);
        txtFecha.setDisable(true);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre1.clear();
        txtNombre2.clear();
        txtNombre3.clear();
        txtApellido1.clear();
        txtApellido2.clear();
        txtDireccion.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtFecha.clear();
    }

    @FXML
    private void clicNuevo(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:

                habilitarCampos();
                tblInstructores.setDisable(true);
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
                if (agregarInstructor()) {
                    if(emptyFields()){
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

                        tblInstructores.setDisable(false);

                        operacion = Operacion.NINGUNO;
                        contarRegistrosMas();
                        actualizarRegistros();
                    } else {
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Empty fields");
                        alerta.setHeaderText("MOUNTAIN CONTROL");
                        alerta.setContentText("Algunos campos importantes estan vacios");
                        Stage st = (Stage) alerta.getDialogPane().getScene().getWindow();
                        st.getIcons().add(new Image(PAQUETE_IMAGES + "advertencia.pgn"));
                        alerta.show();
                    }
                }
                break;
        }
    }

    private boolean agregarInstructor() {
        Instructores ins = new Instructores();

        ins.setNombre1(txtNombre1.getText());
        ins.setNombre2(txtNombre2.getText());
        ins.setNombre3(txtNombre2.getText());
        ins.setApellido1(txtApellido2.getText());
        ins.setApellido2(txtApellido1.getText());
        ins.setDireccion(txtDireccion.getText());
        ins.setEmail(txtEmail.getText());
        ins.setTelefono(txtTelefono.getText());
        ins.setFecha(txtFecha.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_Instructores_create(?,?,?,?,?,?,?,?,?)}");
            pstmt.setString(1, ins.getNombre1());
            pstmt.setString(2, ins.getNombre2());
            pstmt.setString(3, ins.getNombre3());
            pstmt.setString(4, ins.getApellido1());
            pstmt.setString(5, ins.getApellido2());
            pstmt.setString(6, ins.getDireccion());
            pstmt.setString(7, ins.getEmail());
            pstmt.setString(8, ins.getTelefono());
            pstmt.setString(9, ins.getFecha());
            pstmt.execute();
            System.out.println(pstmt.toString());
            ListaInstructores.add(ins);
            return true;

            //sout para detectar errores
        } catch (SQLException e) {
            System.err.println("\n se produjo un error al intentar insertar el siguiente ins" + ins.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @FXML
    private void clicEditar(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png"));

                    btnEditar.setText("Guardar");
                    imgEditar.setImage(new Image(PAQUETE_IMAGES + "guardar.png"));
                    tblInstructores.getSelectionModel().clearSelection();

                    operacion = Operacion.ACTUALIZAR;
                } else {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro.");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
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
                tblInstructores.setDisable(false);
                limpiarCampos();
                desabilitarCampos();

                operacion = Operacion.NINGUNO;

                break;
            case ACTUALIZAR:
                if (existeElementoSeleccionado()) {
                    if (actualizarInstructor()) {
                        if(emptyFields()){
                        getcargarDatos();
                        limpiarCampos();
                        desabilitarCampos();

                        tblInstructores.getSelectionModel().clearSelection();

                        btnNuevo.setDisable(false);
                        btnNuevo.setVisible(true);

                        btnEditar.setText("Editar");
                        imgEditar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                        btnReporte.setDisable(false);
                        btnReporte.setVisible(true);

                        tblInstructores.setDisable(false);

                        operacion = Operacion.NINGUNO;
                       
                        }
                    }
                }
                break;
        }
    }

    public boolean emptyFields() {
        if ((txtNombre1.getText() == "") || (txtApellido1.getText()
                == "") || (txtEmail.getText() == "") || (txtEmail.getText() == "") || (txtTelefono.getText() == "")) {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Empty fields");
            alerta.setHeaderText("MOUNTAIN CONTROL");
            alerta.setContentText("Algunos campos importantes estan vacios");
            Stage st = (Stage) alerta.getDialogPane().getScene().getWindow();
            st.getIcons().add(new Image(PAQUETE_IMAGES + "advertencia.pgn"));
            alerta.show();
            return false;
        } else {
        return true;
        }
        
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
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
                tblInstructores.getSelectionModel().clearSelection();
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
                        if (eliminarInstructores()) {
                            ListaInstructores.remove(tblInstructores.getSelectionModel().getFocusedIndex());
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
                        } else if (botonC.get().equals(ButtonType.CANCEL)) {
                            System.out.println("Cancelando Operacion");
                            tblInstructores.getSelectionModel().clearSelection();
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

    public boolean eliminarInstructores() {

        if (existeElementoSeleccionado()) {
            Instructores ins = (Instructores) tblInstructores.getSelectionModel().getSelectedItem();
            System.out.println(ins.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_instructores_delete(?)}");
                pstmt.setString(1, String.valueOf(ins.getId()));
                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el ins: " + ins.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
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
    @FXML
    private void clicReporte()throws URISyntaxException {

       Map<String, Object> parametros = new HashMap<>();
    parametros.put("nombre","Carlos Diaz");
    GenerarReporte.getInstance().mostrarReporte("ReporteIntructores.jasper",parametros, "Reporte Instructores");
    }

    @FXML
    private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    private boolean actualizarInstructor() {
        Instructores ins = new Instructores();
        ins.setId(Integer.valueOf(txtId.getText()));
        ins.setNombre1(txtNombre1.getText());
        ins.setNombre2(txtNombre2.getText());
        ins.setNombre3(txtNombre3.getText());
        ins.setApellido1(txtApellido1.getText());
        ins.setApellido2(txtApellido2.getText());
        ins.setDireccion(txtDireccion.getText());
        ins.setEmail(txtDireccion.getText());
        ins.setTelefono(txtTelefono.getText());
        ins.setFecha(txtFecha.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_update(?,?,?,?,?,?,?,?,?,?)}");

            pstmt.setString(1, ins.getNombre1());
            pstmt.setString(2, ins.getNombre2());
            pstmt.setString(3, ins.getNombre3());
            pstmt.setString(4, ins.getApellido1());
            pstmt.setString(5, ins.getApellido2());
            pstmt.setString(6, ins.getDireccion());
            pstmt.setString(7, ins.getEmail());
            pstmt.setString(8, ins.getTelefono());
            pstmt.setString(9, ins.getFecha());
            pstmt.setString(10, String.valueOf(ins.getId()));

            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro: " + ins.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;

    }

}
