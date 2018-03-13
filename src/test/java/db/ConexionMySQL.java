package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jaider Serrano
 */
public class ConexionMySQL {

    private Connection conexion=null;
    private String servidor="";
    private String database="";
    private String usuario="";
    private String password="";
    private String url="";    
    
    public ConexionMySQL(String servidor, String database, String usuario, String password) throws ClassNotFoundException, SQLException{
            this.servidor = servidor;
            this.database = database;
 
            Class.forName("com.mysql.jdbc.Driver");
            url="jdbc:mysql://"+servidor+"/"+database;
            conexion=(Connection) DriverManager.getConnection(url, usuario, password);
    }
 
    public Connection getConexion(){
        return conexion;
    }
 
    public Connection cerrarConexion(){
        try {
            conexion.close();
             System.out.println("Cerrando conexion a "+url+" . . . . . Ok");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        conexion=null;
        return conexion;
    }   

    public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	/**
     * Ejecuta una instruccion select SQL dada.
     * @param sql Instruccion select SQL a ejecutar.
     * @return Resultado de la consulta SQL.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */    
    public ResultSet consultar(String sql)throws ClassNotFoundException, SQLException{
       conexion = getConexion();
        return conexion.prepareStatement(sql).executeQuery();
    }

    /**
     * Ejecuta una instruccion delete SQL dada.
     * @param sql Instruccion delete a ejecutar.
     * @return Numero de registros afectados por la sentencia delete.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */    
    public int eliminar(String sql) throws SQLException, ClassNotFoundException{
    	return this.executeUpdate(sql);
    }
    /**
     * Ejecuta una instruccion update SQL dada.
     * @param sql Instruccion update a ejecutar.
     * @return Numero de registros afectados por la sentencia update.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */    
    public int actualizar(String sql) throws SQLException, ClassNotFoundException{
    	return this.executeUpdate(sql);
    }
    /**
     * Ejecuta una instruccion insert SQL dada.
     * @param sql Instruccion insert a ejecutar.
     * @return Numero de registros afectados por la sentencia insert.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */    
    public int insertar(String sql) throws SQLException, ClassNotFoundException{
    	return this.executeUpdate(sql);
    }
    /**
     * 
     * @param sql
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private int executeUpdate(String sql) throws ClassNotFoundException, SQLException{
        Statement st = getStatement();
        return st.executeUpdate(sql);
    }
    /**
     * 
     * @return Statement object
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public Statement getStatement() throws SQLException, ClassNotFoundException{
        if(conexion == null || conexion.isClosed()){
          getConexion();
        }
        return conexion.createStatement();
    }
    
}
