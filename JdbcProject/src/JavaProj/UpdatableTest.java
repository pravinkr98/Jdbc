package JavaProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdatableTest {
	private static final String Get_Student_Query="SELECT SNO,NAME,ADDRS,AVG FROM STUDENT";

	
	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		
		try {
			//register driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create statement object
			if(con!=null)
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			//execute the Query
			if(st!=null)
				rs=st.executeQuery(Get_Student_Query);
			
			//Read records
			if(rs!=null) {
				while(rs.next()) {
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				}//while
				//updation operation
				/*
				 * rs.absolute(3); rs.updateString(2, "express raja"); rs.updateRow();
				 * System.out.println("3rd record updated");
				 */
				//To insert new record
				/*
				 * rs.moveToInsertRow(); rs.updateInt(1, 1212); rs.updateString(2, "Bachchan");
				 * rs.updateString(3, "vizag"); rs.updateFloat(4, 78.66f); rs.insertRow();
				 * System.out.println("Record inserted");
				 */
				//To delete record
				/*
				 * rs.absolute(10); rs.deleteRow(); System.out.println("Record deleted");
				 */
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
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(st!=null)
					st.close();
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