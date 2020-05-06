package JavaProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParameterMetadataApp {

	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		ParameterMetaData pmd=null;
		int count=0;
		
		try {
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create PreparedStatement object
			if(con!=null)
				ps=con.prepareStatement("INSER INTO STUDENT VALUES(?,?,?,?)");
			//create ParameterMetaData object
			if(ps!=null)
				pmd=ps.getParameterMetaData();
			//Display various details about the params
			if(pmd!=null) {
				count=pmd.getParameterCount();
				for(int i=1;i<=count;i++) {
					System.out.println("Parameter number "+i);
					System.out.println("Parameter mode "+pmd.getParameterMode(i));
					System.out.println("Parameter type name "+pmd.getParameterTypeName(i));
					System.out.println("Parameter is signed "+pmd.isSigned(i));
					System.out.println("Parameter scale area "+pmd.getScale(i));
					System.out.println("Parameter precision area "+pmd.getPrecision(i));
					}
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
				if(ps!=null)
					ps.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}			
		}//finally
	}//main
}//class