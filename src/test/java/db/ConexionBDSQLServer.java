/*
 * Proyecto Robot FX Propiedad de CHOUCAIR CARDENAS TESTING S. A.
 * el presente proyecto fue iniciativa del equipo de Migracion - BI
 * agradecimiento es pecial al colaborador Jaider Adriam Serrano Sepulveda.
 * Medellin - Colombia 2016.
 */
package db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.google.common.collect.Lists;

import util.Estructura;
/**
 *
 * @author Jaider Adriam Serrano Sepulveda.
 */
public class ConexionBDSQLServer {
    
    private final String user;
    private final String password;
    private final String host;
    private final String port;
    private final String nameDB;
    private final String schema; 
    private Connection conexion;
    
    /**
     * 
     * @param host Nombre del equipo o direccion IP del servidor de base de datos.
     * @param port Numero del puerto de la base de datos.
     * @param user Nombre de usuario a la base de datos.
     * @param password Clave de acceso a la base de datos.
     * @param schema Nombre del schema a conectar por lo general el mismo nombre de usuario.
     * @param nameDB Nombre de la base de datos.
     * @param esOrigen Indica si la base de datos a conectar es origen o destino.
     */
    public ConexionBDSQLServer(String host, String port, String nameDB, String user, String password, String schema){
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.nameDB = nameDB;
        this.schema = schema;
    }
    /**
     * 
     * @return Connection deacceso a la base de datos.
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public Connection getConexion() throws SQLException, ClassNotFoundException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conexion = DriverManager.getConnection(getURL());
        
		return conexion;
    }
    /**
     * 
     * @return Statement object.
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public Statement getStatement() throws SQLException, ClassNotFoundException {
        if(conexion == null || conexion.isClosed()){
          getConexion();
        }
        return conexion.createStatement();
    }
    /**
     * 
     * @return URL de conexion.
     */
    public String getURL() {
        return "jdbc:sqlserver://"+host+":"+port+";databaseName="+nameDB+";user="+user+";password="+password;
    }
    /**
     * 
     * @return Nombre de usuario de conexion a la base de datos.
     */
    public String getUser() {
        return this.user;
    }
    /**
     * 
     * @return Clave de acceso a la base de datos.
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * 
     * @return Retorna nombre o IP del servidor de base de datos.
     */
    public String getHost() {
        return this.host;
    }
    /**
     * 
     * @return Numero del puerto de acceso a la base de datos.
     */
    public String getPort() {
        return this.port;
    }
    /**
     * 
     * @return True si existe una conexion a la base de datos.
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public boolean isConnected() throws SQLException, ClassNotFoundException{
        return !this.getConexion().isClosed();
    }
    /**
     * Metodo utilizado para cerrar la conexion a la base de datos. 
     * @throws SQLException 
     */
    public void close() throws SQLException {
        if(this.conexion != null){
           this.conexion.close();
        }
    }

    @Override
    public String toString(){
        return "Driver = com.microsoft.sqlserver.jdbc.SQLServerDriver - Host: "+host+" - Puerto: "+port+" - Usuario: "+user+" - Nombre BD: "+nameDB;
    }
    /**
     * 
     * @return Nombre del schema de la base de datos.
     */
    public String getSchema() {
        return this.schema;
    }
//************************   CONSULTAS SQL   ************************\\
    /**
     * Ejecuta una instruccion select SQL dada.
     * @param sql Instruccion select SQL a ejecutar.
     * @return Resultado de la consulta SQL.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */    
    public ResultSet consultar(String sql) throws SQLException, ClassNotFoundException{
        Statement st = getStatement();
        return st.executeQuery(sql);
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
     * Obtiene el listado de las tablas de la BD conectada.
     * @return Lista con todas las tablas de la conexcion con SQLServer.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<String> getTablas() throws SQLException, ClassNotFoundException{
        List<String> tablas = Lists.newArrayList();
        getConexion();
        DatabaseMetaData metaDatos = conexion.getMetaData();
        String types[]={"TABLE"};
        ResultSet rs = metaDatos.getTables(nameDB, schema, "%", types);
        
        while(rs.next()){
            tablas.add(rs.getString(3));
        }
        rs.close();
        close();
        return tablas;
    }
    /**
     *
     * @param nomTabla Nombre de la tabla a consultar el nombre de sus campos
     * @return Lista con todas las columnas de la tabla dada.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */    
    public List<String> getCamposTabla(String nomTabla) throws SQLException, ClassNotFoundException {
        List<String> campos = Lists.newArrayList();
        getConexion();
        DatabaseMetaData metaDatos = conexion.getMetaData();
        ResultSet rs = metaDatos.getColumns(null, schema, nomTabla, null);
        while(rs.next()){
            campos.add(rs.getString(4));
        }
        rs.close();
        close();            
        return campos;
    }
    /**
     * Este metodo utiliza el API DatabaseMetaData para extraer la metadata de un campo
     * dado el nomde de la tabla y el campoa fin de extraer tipo de dato, longitud, etc.
     * 
     * @param tabla Nombre del tabla.
     * @param campo Nombre del campo a extraer los metadatos.
     * @return Estructura con la metadata del campo solicitado.
     * @throws ClassNotFoundException
     * @throws SQLException 
     */    
    public Estructura getDetalleCampoTabla(String tabla, String campo) throws ClassNotFoundException, SQLException{
        Estructura result = null;
        getConexion();
        DatabaseMetaData metaDatos = conexion.getMetaData();
        ResultSet rs = metaDatos.getColumns(null, schema, tabla, campo);
        while(rs.next()){
            result = new Estructura(rs.getString(3), rs.getString(4), rs.getString(6), rs.getInt(7), rs.getString(9), rs.getInt(5), rs.getString(12));
        }
        rs.close();
        close();            
        return result;
    }
    /**
     * 
     * @param nomTabla Nombre de l tabla a extraer los metadatos.
     * @return Lista con los metadata de cada campo de la tabla dada.
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public List<Estructura> getDetalleCamposTabla(String nomTabla) throws ClassNotFoundException, SQLException{
        List<Estructura> result = Lists.newArrayList();
        getConexion();
        DatabaseMetaData metaDatos = conexion.getMetaData();
        ResultSet rs = metaDatos.getColumns(null, schema, nomTabla, null);
        while(rs.next()){
            result.add(new Estructura(rs.getString(3), rs.getString(4), rs.getString(6), rs.getInt(7), rs.getString(9), rs.getInt(5), rs.getString(12)));
        }
        rs.close();
        close();            
        return result;
    }    
    /**
     * 
     * @param resultSet Objeto base para extraer los nombres de las columnas de la consulta contenida en este objeto.
     * @param conDetalle True si se quiere extraer el nombre de los campos con detalle del tipo de datos y cantidad de los campos.
     * @return Lista con los nombres de los campos de la consulta SQL con o sin detalles.
     * @throws SQLException 
     */
    public List<String> getNombreColumnas(ResultSet resultSet, boolean conDetalle) throws SQLException{
	List<String> columNames = Lists.newArrayList();
    	
    	if(resultSet != null) {
            ResultSetMetaData rsmd = resultSet.getMetaData();	
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                if(conDetalle)
                   columNames.add(rsmd.getColumnName(i)+" ["+rsmd.getColumnTypeName(i)+" "+rsmd.getPrecision(i)+"."+rsmd.getScale(i)+"]");	
                else
                   columNames.add(rsmd.getColumnName(i));				
            }
	}
        return columNames;
    }        
    /**
     * 
     * @return Lista de Schemas de la base de datos.
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public List<String> getSchemas() throws ClassNotFoundException, SQLException{
        List<String> schemas = Lists.newArrayList();
        getConexion();
        DatabaseMetaData metaDatos = conexion.getMetaData();
        ResultSet rs = metaDatos.getSchemas();        
        while(rs.next()){
            schemas.add(rs.getString(1));
        }
        rs.close();
        close();
        return schemas;        
    }
    /**
     * 
     * @param tabla Nombre de la tabla a consultar su cantidad de registros.
     * @return Cantdad de registros existentes en la tabla.
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public int getCount(String tabla) throws SQLException, ClassNotFoundException{
        int result = -1;
        ResultSet rs = consultar("SELECT COUNT(0) AS CANT FROM "+tabla);
        
        while(rs.next()){
            result = rs.getInt("CANT");
        }
        return result;
    }
    
}
