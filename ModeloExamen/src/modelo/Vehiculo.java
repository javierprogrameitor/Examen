package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.ConexionDB;

/**
 *
 * @author javier
 */
public class Vehiculo {

    String matricula;
    String bastidor;
    String marca;
    String modelo;
    int anio_mat;
    int dni_propietario;
    Propietario propietario;

  
    public Vehiculo() {
        this("", "", "", "", 0, 0, "", "", "", "", "");
    }

    public Vehiculo(String matricula, String bastidor, String marca, String modelo, int anio_mat, int dni_propietario, Propietario propietario) {
        this(matricula, bastidor, marca, modelo, anio_mat, dni_propietario,
                propietario.getNombre(),
                propietario.getApellidos(),
                propietario.getDireccion(),
                propietario.getPoblacion(),
                propietario.getCod_postal());
    }

    public Vehiculo(String matricula, String bastidor, String marca, String modelo, int anio_mat, int dni_propietario,
            String nombre, String apellidos, String direccion, String poblacion, String cod_postal) {
        this.matricula = matricula;
        this.bastidor = bastidor;
        this.marca = marca;
        this.modelo = modelo;
        this.anio_mat = anio_mat;
        this.propietario = new Propietario(nombre, apellidos, direccion, poblacion, cod_postal);
    }

    public Vehiculo(String matricula, String bastidor, String marca, String modelo, int anio_mat) {
        this.matricula = matricula;
        this.bastidor = bastidor;
        this.marca = marca;
        this.modelo = modelo;
        this.anio_mat = anio_mat;
    }

    //Setter y Getter de Propietarios_//////
    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public int getDni() {  // En los getter hay que indicar 'propietario' en este caso
        return this.propietario.getDni_propietario();  //para indicar en el controlador 
    }                                                // de quien es el modelo que no es               
                                                 // de esta clase.............Importante...
    public String getNombre() {
        return this.propietario.getNombre();
    }

    public String getApellidos() {
        return this.propietario.getApellidos();
    }

    public String getPoblacion() {
        return this.propietario.getPoblacion();
    }

    //Setter y Getter de Vehiculos___/////////////////////////////////////
    /////////////////////////////////////
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getBastidor() {
        return bastidor;
    }

    public void setBastidor(String bastidor) {
        this.bastidor = bastidor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio_mat() {
        return anio_mat;
    }

    public void setAnio_mat(int anio_mat) {
        this.anio_mat = anio_mat;
    }

    public int getDni_propietario() {
        return dni_propietario;
    }

    public void setDni_propietario(int dni_propietario) {
        this.dni_propietario = dni_propietario;
    }

    @Override
    public String toString() {
        return matricula;
    }

    public ObservableList<Vehiculo> getVehiculos() {
        ObservableList<Vehiculo> vhc = FXCollections.observableArrayList();
        try {

            // Abro la conexion
            ConexionDB conexion = new ConexionDB();

            // Ejcuto la consulta
            String SQL = "";
            SQL += "SELECT v.matricula, v.bastidor, v.marca, v.modelo, v.anio_mat, v.dni_propietario, ";
            SQL += "p.dni_propietario, p.nombre, p.apellidos, p.direccion, p.poblacion, p.cod_postal ";
            SQL += "FROM vehiculos v, propietarios p ";
            SQL += "WHERE  p.dni_propietario = v.dni_propietario";

            // Recorro los datos
            ResultSet rs = conexion.ejecutarConsulta(SQL);
            // Recorro los datos
            while (rs.next()) {

                // Obtengo los datos
                String matricula = rs.getString("matricula");
                String bastidor = rs.getString("bastidor");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                int anio_mat = rs.getInt("anio_mat");
                int dni = rs.getInt("dni_propietario");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String direccion = rs.getString("direccion");
                String poblacion = rs.getString("poblacion");
                String cod_postal = rs.getString("cod_postal");

                //Creo el propietario
                Propietario propietario = new Propietario(dni, nombre, apellidos, direccion, poblacion, cod_postal);

                //Creo los vehiculos
                Vehiculo v = new Vehiculo(matricula, bastidor, marca, modelo, anio_mat, dni, propietario);

                v.getPropietario().setDni_propietario(dni);

                vhc.add(v);

            }
            rs.close();
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Vehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vhc;
    }
}
