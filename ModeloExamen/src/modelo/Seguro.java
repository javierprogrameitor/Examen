package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.ConexionDB;

/**
 *
 * @author javier
 */
public class Seguro {

    private int codigo;
    private int dni_propietario;
    private String matricula;
    private String compañia;
    private float pvp;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private String tipo;

    ///Atributos adicionales para las tablas..////
    private String nombre;
    private String apellidos;

    public Seguro(int codigo, int dni_propietario, String nombre, String apellidos, String matricula, float pvp, LocalDate fecha_inicio, LocalDate fecha_fin, String tipo) {
        this.codigo = codigo;
        this.dni_propietario = dni_propietario;
        this.matricula = matricula;
        this.pvp = pvp;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    // Constructor completo de la clase Seguro
    public Seguro(int codigo, int dni_propietario, String matricula, String compañia, float pvp, LocalDate fecha_inicio, LocalDate fecha_fin, String tipo) {
        this.codigo = codigo;
        this.dni_propietario = dni_propietario;
        this.matricula = matricula;
        this.compañia = compañia;
        this.pvp = pvp;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.tipo = tipo;
    }

    //Para poner los datos en la tabla
    public Seguro(int codigo, int dni_propietario, String matricula, String compañia, float pvp, LocalDate fecha_inicio, LocalDate fecha_fin, String tipo, String nombre, String apellidos) {
        this.codigo = codigo;
        this.dni_propietario = dni_propietario;
        this.matricula = matricula;
        this.compañia = compañia;
        this.pvp = pvp;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Seguro() {

    }



    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
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

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getDni_propietario() {
        return dni_propietario;
    }

    public void setDni_propietario(int dni_propietario) {
        this.dni_propietario = dni_propietario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCompañia() {
        return compañia;
    }

    public void setCompañia(String compañia) {
        this.compañia = compañia;
    }

    public float getPvp() {
        return pvp;
    }

    public void setPvp(float pvp) {
        this.pvp = pvp;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean insertarSeguro() throws SQLException {

        // Abro la conexion
        ConexionDB conexion = new ConexionDB();
        //cambiar nombre y apellidos por compañia
        // Sentencia para introducir un servicio
        String SQL = "INSERT INTO seguros (codigo, dni_propietario, matricula, compañia, pvp, fecha_inicio, fecha_fin, tipo) "
                + "values("
                + "'" + this.codigo + "', "
                + "'" + this.dni_propietario + "', "
                + "'" + this.matricula + "' "
                + "'" + this.compañia + "', "
                + "'" + this.pvp + "', "
                + "'" + this.fecha_inicio.toString() + "', "
                + "'" + this.fecha_fin.toString() + "', "
                + "'" + this.tipo + "')";

        // Devuelvo el numero de filas afectadas
        //   int filas = 
        conexion.ejecutarInstruccion(SQL);

        conexion.cerrarConexion();

        /*Si deuvelve mas de 0, es que hemos insertado registros
        if (filas > 0) {
            return true;
        } else {
            return false;
        }
         */
        return true;
    }

    public ObservableList<Seguro> getSeguros() {  // Para mostrar las tablas....
        ObservableList<Seguro> obs = FXCollections.observableArrayList();
        try {

            // Abro la conexion
            ConexionDB conexion = new ConexionDB();

            // realizo la consulta
            String SQL = "";
            SQL += "SELECT s.codigo, s.dni_propietario, s.matricula, s.fecha_inicio, s.fecha_fin, s.pvp, s.tipo, ";
            SQL += "p.dni_propietario, p.nombre, p.apellidos ";
            SQL += "FROM seguros s, propietarios p ";
            SQL += "WHERE s.dni_propietario = p.dni_propietario";

            ResultSet rs = conexion.ejecutarConsulta(SQL);

            // recorro los resultados
            while (rs.next()) {

                // Cojo los datos para la tabla
                int cod = rs.getInt("codigo");
                int dni_prop = rs.getInt("dni_propietario");
                String nom = rs.getString("nombre");
                String apell = rs.getString("apellidos");
                String mat = rs.getString("matricula");
                Float pvP = rs.getFloat("pvp");
                LocalDate fecha_ini = rs.getDate("fecha_inicio").toLocalDate();
                LocalDate fecha_final = rs.getDate("fecha_fin").toLocalDate();
                String tip = rs.getString("tipo");

                //Creo el tipo de Seguro para mostrar en la tabla
                Seguro s = new Seguro(cod, dni_prop, nom, apell, mat, pvP, fecha_ini, fecha_final, tip);

                //Añado le clase al Observablelist
                obs.add(s);
            }

            // Cierro la conexion
            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Seguro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }

}
