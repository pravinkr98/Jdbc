package JavaProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetMetadataApp {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		int colCount=0;
		
		try {
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create statement object
			if(con!=null)
				st=con.createStatement();
			//sent and execute SQL Query in Database software
			if(st!=null)
				rs=st.executeQuery("SELECT * FROM STUDENT");
			if(rs!=null) {
				//create ResultSetMetaData
				rsmd=rs.getMetaData();
				//get column count
				colCount=rsmd.getColumnCount();
				//display col names
				for(int i=1;i<=colCount;++i) {
					System.out.print(rsmd.getColumnLabel(i)+"  ");
				}//for
				System.out.println();
				//display col types
				for(int i=1;i<=colCount;++i) {
					System.out.print(rsmd.getColumnTypeName(i)+"  ");
				}//for
				System.out.println();
				//process the ResultSet
				while(rs.next()) {
					for(int i=1;i<=colCount;++i) {
						System.out.print(rs.getString(i)+"  ");
					}//for
					System.out.println();
				}//while
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