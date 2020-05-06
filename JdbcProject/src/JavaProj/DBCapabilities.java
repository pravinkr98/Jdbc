package JavaProj;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCapabilities {

	public static void main(String[] args) {
		Connection con=null;
		DatabaseMetaData dbmd=null;
		
		try {
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//get Metadata
			dbmd=con.getMetaData();
			//The database details are
			if(dbmd!=null) {
				System.out.println("database name "+dbmd.getDatabaseProductName());
				System.out.println("database version "+dbmd.getDatabaseProductVersion());
				System.out.println("database Major version "+dbmd.getDatabaseMajorVersion());
				System.out.println("database Minor version "+dbmd.getDatabaseMinorVersion());
				System.out.println("JDBC Major Version "+dbmd.getJDBCMajorVersion());
				System.out.println("JDBC Minor Version "+dbmd.getJDBCMinorVersion());
				System.out.println("JDBC Driver Major Version "+dbmd.getDriverMajorVersion());
				System.out.println("JDBC Driver Minor Version "+dbmd.getDriverMinorVersion());
				System.out.println("All SQL keywords "+dbmd.getSQLKeywords());
				System.out.println("All Numeric functions "+dbmd.getNumericFunctions());
				System.out.println("All System functions "+dbmd.getSystemFunctions());
				System.out.println("All String functions "+dbmd.getStringFunctions());
				System.out.println("Max Table name length "+dbmd.getMaxTableNameLength());
				System.out.println("Max column name length "+dbmd.getMaxColumnNameLength());
				System.out.println("Max Row Size "+dbmd.getMaxRowSize());
				System.out.println("Max cos in Select Query "+dbmd.getMaxColumnsInSelect());
				System.out.println("Max cols in Database table "+dbmd.getMaxColumnsInTable());
				System.out.println("Max Connections to database "+dbmd.getMaxConnections());
			}//if
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close jdbc objects
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException e ) {
				e.printStackTrace();
			}
		}//finally
	}//main
}//class
