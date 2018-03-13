/*
 * Proyecto Robot FX Propiedad de CHOUCAIR CARDENAS TESTING S. A.
 * el presente proyecto fue iniciativa del equipo de Migracion - BI
 * agradecimiento es pecial al colaborador Jaider Adriam Serrano Sepulveda.
 * Medellin - Colombia 2016.
 */

package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Jaider Adriam Serrano Sepulveda
 */
public class ConexionBDPostgres {
    
    private final String user;
    private final String password;
    private final String host;
    private final String port;
    private final String nameDB;
    private Connection conexion;
    
    public ConexionBDPostgres(String host, String port, String user, String password, String nameDB){
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.nameDB = nameDB;
    }
    
    public Connection getConexion() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        Class.forName("org.postgresql.Driver").newInstance();
        conexion = DriverManager.getConnection(getURL(),user,password);
        
		return conexion;
    }
    
    public synchronized Statement getStatement() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if(conexion == null || conexion.isClosed()){
          getConexion();
        }
        return conexion.createStatement();
    }

    public String getURL() {
        return "jdbc:postgresql://"+host+":"+port+"/"+nameDB;
    }

    public String getHost() {
        return this.host;
    }

    public String getPort() {
        return this.port;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString(){
        return "Driver = org.postgresql.Driver - Host: "+host+" - Puerto: "+port+" - Usuario: "+user+" - Nombre BD: "+nameDB;
    }
    
    public void close() throws SQLException {
        if(this.conexion != null){
           this.conexion.close();
        }
    }
    
    public boolean isConnected() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        return !this.getConexion().isClosed();
    }

    //************************   SQL   ************************\\
    /**
     * Ejecuta una instruccion insert SQL dada.
     * @param sql Instruccion insert SQL a ejecutar.
     * @throws java.sql.SQLException Si existe algun problema a nivel de BD.
     * @throws java.lang.ClassNotFoundException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public void insertar(String sql) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        Connection connection = getConexion();
        Statement st = connection.createStatement();
        st.execute(sql);
        st.close();
        connection.close();
    }
    /**
     * Ejecuta una instruccion delete SQL dada.
     * @param sql Instruccion delete SQL a ejecutar.
     * @throws java.lang.ClassNotFoundException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws SQLException 
     */
    public void eliminar(String sql) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        Statement st = getStatement();
        st.execute(sql);
        st.close();
        close();
    }
    /**
     * Ejecuta una instruccion update SQL dada.
     * @param sql Instruccion update SQL a ejecutar.
     * @throws java.lang.ClassNotFoundException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws SQLException 
     */    
    public void actualizar(String sql) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        Statement st = getStatement();
        st.executeUpdate(sql);
        st.close();
        close();
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
    public ResultSet consultar(String sql) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        Statement st = getStatement();
        ResultSet result = st.executeQuery(sql);
        close();
        return result;
    }
   
}//Fin clase ConexcionBDPostgres
