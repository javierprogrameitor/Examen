package controlador;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Seguro;

import modelo.Vehiculo;

/**
 * FXML Controller class
 *
 * @author Javier
 */
public class VistaController implements Initializable {

    @FXML
    private ComboBox<Vehiculo> cmbVehiculos;
////////////////////////////////////////////
    @FXML
    private TextField txtNIF;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtPoblacion;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtCompañia;
    @FXML
    private TextField txtTipo;
/////////////////////////////////////////////

    @FXML
    private DatePicker dtkpInicio;
    @FXML
    private DatePicker dtkpFinal;

    ////////////////////////////////////////////
    @FXML
    private TableView<Seguro> tblwiSeguros;
    @FXML
    private TableColumn<Seguro, Integer> tblCodigo;
    @FXML
    private TableColumn<Seguro, Integer> tblNif;
    @FXML
    private TableColumn<Seguro, String> tblNombre;
    @FXML
    private TableColumn<Seguro, String> tblApellidos;
    @FXML
    private TableColumn<Seguro, String> tblMatricula;
    @FXML
    private TableColumn<Seguro, LocalDate> tblInicio;
    @FXML
    private TableColumn<Seguro, LocalDate> tblFin;
    @FXML
    private TableColumn<Seguro, Float> tblPvp;
    @FXML
    private TableColumn<Seguro, String> tblTipo;
    @FXML
    private Button btnGrabarSeguro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Vehiculo v = new Vehiculo();
        ObservableList<Vehiculo> obsVehiculos = v.getVehiculos();
        this.cmbVehiculos.setItems(obsVehiculos);

        Seguro s = new Seguro();
        ObservableList<Seguro> item = s.getSeguros();
        this.tblwiSeguros.setItems(item);

        this.tblCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        this.tblNif.setCellValueFactory(new PropertyValueFactory("dni_propietario"));
        this.tblNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tblApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        this.tblMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        this.tblPvp.setCellValueFactory(new PropertyValueFactory("pvp"));
        this.tblInicio.setCellValueFactory(new PropertyValueFactory("fecha_inicio"));
        this.tblFin.setCellValueFactory(new PropertyValueFactory("fecha_fin"));
        this.tblTipo.setCellValueFactory(new PropertyValueFactory("tipo"));

    }

    @FXML
    private void cargarVehiculos(ActionEvent event) {

        Vehiculo v = this.cmbVehiculos.getSelectionModel().getSelectedItem();

        this.txtMarca.setText(v.getMarca());
        this.txtModelo.setText(v.getModelo());
        this.txtNIF.setText(v.getDni() + "");
        this.txtNombre.setText(v.getNombre());
        this.txtApellidos.setText(v.getApellidos());
        this.txtPoblacion.setText(v.getPoblacion());

    }

    @FXML
    private void grabarSeguro(ActionEvent event) {

        Vehiculo v = this.cmbVehiculos.getValue();
        LocalDate fecha_inicio = this.dtkpInicio.getValue();
        LocalDate fecha_fin = this.dtkpFinal.getValue();
        float pvp = Float.parseFloat(this.txtPrecio.getText() + "");
        String compañia = this.txtCompañia.getText();
        String tipo = this.txtTipo.getText();

        String errores = "";

        if (v == null) {
            errores += " -Debes seleccionar un vehiculo\n";
        }
        if (fecha_inicio == null) {
            errores += " -Debes seleccionar una fecha de inicio\n";
        }
        if (fecha_fin == null) {
            errores += " -Debes seleccionar una fecha de fin\n";
        }
        if (pvp == 0) {
            errores += " -Debes indicar un precio\n";
        }
        if (compañia == null) {
            errores += " -Debes verificar la compañia de Seguro\n";
        }
        if (tipo == null) {
            errores += " -Debes indicar el tipo\n";
        }

        if (errores.isEmpty()) {

            try {
                int codigo = 0;
                
                Seguro s = new Seguro(codigo,v.getDni(), v.getMatricula(),compañia,  pvp, fecha_inicio, fecha_fin, tipo);

                if (s.insertarSeguro()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Exito");
                    alert.setContentText("Se insertó correctamente el nuevo Seguro");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Exito");
                    alert.setContentText("NO insertó correctamente el nuevo Seguro");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                Logger.getLogger(VistaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
