package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.ConexionAS400;
import db.ConexionBDOracle;
import db.ConexionBDPostgres;
import db.ConexionBDSQLServer;
import db.ConexionMySQL;

public class MainDBTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		oracleTest();
	}
	
	public static void oracleTest() throws ClassNotFoundException, SQLException {
		//Oracle test as service
		ConexionBDOracle conexion = new ConexionBDOracle("192.168.10.100", "1521", "my_user", "my_password", "GR", "service_name", true);
		
		ResultSet rs = conexion.consultar("Select name, phone From Person");
		while(rs != null && rs.next()) {
			System.out.println("Name: "+rs.getString("name") + "Phone: "+rs.getString("phone"));
		}
		
		//Oracle test with SID		
		ConexionBDOracle conexion2 = new ConexionBDOracle("192.168.10.100", "1521", "my_user", "my_password", "GR", "sid", false);
		ResultSet rs1 = conexion2.consultar("Select name, phone From Person");
		while(rs1 != null && rs1.next()) {
			System.out.println("Name: "+rs1.getString("name") + "Phone: "+rs1.getString("phone"));
		}

	}
	
	public static void db2Test() throws ClassNotFoundException, SQLException {
		//DB2 - AS400 test
		ConexionAS400 conexion = new ConexionAS400("192.168.0.110","MyUser", "My_Password");
		
		ResultSet rs = conexion.consultar("Select name, phone From fpoblib.afilia ");
		while(rs != null && rs.next()) {
			System.out.println("Name: "+rs.getString("name") + "Phone: "+rs.getString("phone"));
		}
		
	}
	
    public static void mySQLTest() throws ClassNotFoundException, SQLException{
        ConexionMySQL con = new ConexionMySQL("192.168.0.1","bd_name", "root","my_password");
        con.getConexion();
        
        ResultSet rs = con.consultar("Select name, phone From Person");
        
        while(rs.next()){
        	System.out.println("Name: "+rs.getString("name") + "Phone: "+rs.getString("phone"));
        }
        
    }

    public static void postgresTest() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        
    	ConexionBDPostgres conexion = new ConexionBDPostgres("192.168.200.100", "5433", "My_User", "My_Password", "name_DB");
        
        ResultSet rs = conexion.consultar("Select name, phone From Person");
        
        while(rs.next()){
        	System.out.println("Name: "+rs.getString("name") + "Phone: "+rs.getString("phone"));
        }
        
    }

    public static void sqlServerTest() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        
    	ConexionBDSQLServer conexion = new ConexionBDSQLServer("192.168.10.190", "1433", "Name_DB", "user", "password", "schema_name");
        
        ResultSet rs = conexion.consultar("Select name, phone From Person");
        
        while(rs.next()){
        	System.out.println("Name: "+rs.getString("name") + "Phone: "+rs.getString("phone"));
        }
        
    }
    
}
