package JavaProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PSBatch {

	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		int result[]=null;
		try {
			//register jdbc driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create prepared statement object
			if(con!=null)
				ps=con.prepareStatement("INSERT INTO STUDENT VALUES(?,?,?,?)");
			if(ps!=null) {
				//add multiple set of param value to the batch
				ps.setInt(1, 222);
				ps.setString(2, "Vinod");
				ps.setString(3, "Lucknow");
				ps.setFloat(4, 55.65f);
				ps.addBatch();//add 1st set values to batch
				
				ps.setInt(1, 224);
				ps.setString(2, "Tiwary");
				ps.setString(3, "Patna");
				ps.setFloat(4, 65.75f);
				ps.addBatch();//add 2nd set values to batch
				//execute batch
				result=ps.executeBatch();
			}//if
			//process the result
			if(result!=null)
				System.out.println("Record inserted.");
			else
				System.out.println("Record not inserted");
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
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
