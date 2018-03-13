/*
 * Proyecto Robot FX AS400 Propiedad de CHOUCAIR CARDENAS TESTING S. A.
 * el presente proyecto fue iniciativa del equipo de Migracion - BI
 * agradecimiento es pecial al colaborador Jaider Adriam Serrano Sepulveda.
 * Medellin - Colombia 2017.
 */
package db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import com.google.common.collect.Lists;

import util.ResultSetSP;
import util.TableWraper;

/**
 *
 * @author Jaider Adriam Serrano Sepulveda.
 */
public class ConexionAS400 {
    
    private String host;
    private String user;
    private String password;
    private Connection conexion;
    
    /**
     * 
     * @param host Nombre del equipo o direccion IP del servidor de base de datos.
     * @param user Nombre de usuario a la base de datos.
     * @param password Clave de acceso a la base de datos.
     */
    public ConexionAS400(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }
    /**
     * 
     * @return Retorna nombre o IP del servidor de base de datos.
     */
    public String getHost() {
        return host;
    }
    /**
     * 
     * @return Nombre de usuario de conexion a la base de datos.
     */
    public String getUser() {
        return user;
    }
    /**
     * 
     * @return Clave de acceso a la base de datos.
     */
    public String getPassword() {
        return password;
    }

    public String toString() {
        return "ConexionAS400{" + "host = " + host + ", user = " + user + ", ambiente = PROTEC4}";
    }

//*********************  SQL *********************\\
    /**
     * 
     * @return 
     * @throws java.lang.ClassNotFoundException 
     * @throws java.sql.SQLException 
     */
    public Connection getConexion() throws ClassNotFoundException, SQLException{
        DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver());
        conexion = DriverManager.getConnection("jdbc:as400://"+host, user, password);
        return this.conexion;
    }// Fin metodo getConexion.
    /**
     * Ejecuta una instruccion select SQL dada.
     * @param sql Instruccion select SQL a ejecutar.
     * @return Resultado de la consulta SQL.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException

     */        
    public ResultSet consultar(String sql) throws SQLException, ClassNotFoundException{
        if(conexion == null || conexion.isClosed()){
          getConexion();
        }        
        Statement st = conexion.createStatement();
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
    /**
     * Metodo utilizado para cerrar la conexion a la base de datos.
     * 
     * @throws SQLException 
     */    
    public void close() throws SQLException{
        if (this.conexion != null){
            this.conexion.close();
        }
    }
    //************************   METODOS IMPLEMENTADOS   ************************\\
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
     * @param sql Sentencia SQL a ejecutar.
     * @return Matriz con los resultados de la consulta SQL.
     * @throws ClassNotFoundException Exepcion de clase no encontrada. 
     * @throws SQLException Excepcion en la sentencia SQL.
     */
    public List<TableWraper> ejecutarSQL(String sql) throws ClassNotFoundException, SQLException{
        List<TableWraper> resultado = Lists.newArrayList();
        ResultSet rs = this.consultar(sql);
        TableWraper cabecera = new TableWraper();
        cabecera.setCampos(this.getNombreColumnas(rs, false));
        int numColumnas = cabecera.getCampos().size();
        resultado.add(cabecera);

        while(rs.next()){
            TableWraper fila = new TableWraper();
            for(int c = 1; c <= numColumnas; c++){
                fila.addCampo(rs.getString(c));					
            }
            resultado.add(fila);
            //System.out.println(fila.toString());
        }
        return resultado;
    }
    /**
     * Este metodo se encarga de llamar el procedimiento almacenado CALL AFFOCEPG.SP_CONSULTA_SDO_DIS
     * el cual me devuelve la informacion del saldo diponible y pignorado de la relacion afiliado empleador
     * consultada.
     * 
     * @return ResultSet con la informacion del saldo
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSetSP llamarSP(String tipoIdAfiliado, String numIdAfiliado, String tipoIdEmpleador, String numIdEmpleador) throws ClassNotFoundException, SQLException{

    	ResultSetSP result = new ResultSetSP();
    	if (this.conexion == null){
    		this.getConexion();
    	}
    	
    	java.sql.CallableStatement sentencia = this.conexion.prepareCall("{ CALL AFFOCEPG.SP_CONSULTA_SDO_DIS (?,?,?,?,?,?) }");

    	sentencia.setString(1, tipoIdEmpleador);
    	sentencia.setString(2, numIdEmpleador);
    	sentencia.setString(3, tipoIdAfiliado);
    	sentencia.setString(4, numIdAfiliado);
    	sentencia.registerOutParameter(5, Types.NVARCHAR);
    	sentencia.registerOutParameter(6, Types.NVARCHAR);   	
    	sentencia.execute();
    	
    	if(!sentencia.getString(5).equals("0")){
    		System.out.println("Error # "+sentencia.getString(5)+" en SP_CONSULTA_SDO_DIS -> "+sentencia.getString(6));
    		result.setCodigoError(Integer.parseInt(sentencia.getString(5)));
    		result.setMsnError(sentencia.getString(6));
    	}
    	else{
    		result.setCodigoError(0);
    		result.setResultSet(sentencia.getResultSet());
    	}

    	return result;
    }
    
}//Fin clase ConexionAS400
