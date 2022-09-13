package util; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {

    private final Connection conexion;

    public ConexionDB() throws SQLException {

        //Adaptarlos a la conexion
        String host = "localhost";
        String baseDatos = "concesionario";  //Nombre de la base de datos
        String usuario = "root";
        String password = "";

        //Cadena de conexion para conectarme a la base de datos en MySQl
        String cadenaConexion = "jdbc:mysql://" + host + "/" + baseDatos;

        //Creo la conexion
        conexion = DriverManager.getConnection(cadenaConexion, usuario, password);

        //Hace commit automaticamente
        conexion.setAutoCommit(true);

    }

    public ResultSet ejecutarConsulta(String SQL) throws SQLException {

        Statement statement = this.conexion.createStatement();
        return statement.executeQuery(SQL);

    }

    public int ejecutarInstruccion(String SQL) throws SQLException {

        Statement statement = this.conexion.createStatement();
        return statement.executeUpdate(SQL);

    }

    public int ultimoID() throws SQLException {

        ResultSet rs = this.ejecutarConsulta("SELECT last_insert_id() as last_id;");
        rs.next();
        return rs.getInt("last_id");
    }

    public void cerrarConexion() throws SQLException {

        this.conexion.close();
    }

}
