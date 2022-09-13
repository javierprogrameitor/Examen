package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.ConexionDB;


public class Propietario {

    private int dni_propietario;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String poblacion;
    private String cod_postal;

   private Vehiculo vehiculo;

    public Propietario() {
        this(0,"","","","","","","","","",0);
    }



    public Propietario(int dni_propietario, String nombre, String apellidos, String direccion, String poblacion, String cod_postal, Vehiculo vehiculo) {
        this(dni_propietario, nombre, apellidos, direccion, poblacion, cod_postal,
                vehiculo.getMatricula(),
                vehiculo.getBastidor(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getAnio_mat());

    }

        public Propietario(int dni_propietario, String nombre, String apellidos, String direccion, String poblacion, String cod_postal,
                String matricula, String bastidor, String marca, String modelo, int anio_mat) {
        this.dni_propietario = dni_propietario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.cod_postal = cod_postal;
        this.vehiculo = new Vehiculo(matricula, bastidor, marca, modelo, anio_mat);
    }

    public Propietario(String nombre, String apellidos, String direccion, String poblacion, String cod_postal) {
       
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.cod_postal = cod_postal;
    }

    public Propietario(int dni_propietario, String nombre, String apellidos, String direccion, String poblacion, String cod_postal) {
        this.dni_propietario = dni_propietario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.cod_postal = cod_postal;
    }
    
    
      
    public int getDni_propietario() {
        return dni_propietario;
    }

    public void setDni_propietario(int dni_propietario) {
        this.dni_propietario = dni_propietario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }

    @Override
    public String toString() {
        return nombre;
    }
    


    public ObservableList<Propietario> getPropietarios() {
        ObservableList<Propietario> prop = FXCollections.observableArrayList();
        try {

            // Abro la conexion
            ConexionDB conexion = new ConexionDB();

            // realizo la consulta
            String SQL = "";
            SQL += "SELECT * FROM propietarios";

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            while (rs.next()) {

                // Cojo los datos
                int dni = rs.getInt("dni_propietario");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String direccion = rs.getString("direccion");
                String poblacion = rs.getString("poblacion");
                String cod_postal = rs.getString("cod_postal");

                // Creo el cliente
                Propietario p = new Propietario(dni, nombre, apellidos, direccion, poblacion, cod_postal);

                prop.add(p);

            }

            // Cierro la conexion
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Propietario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prop;
    }

}