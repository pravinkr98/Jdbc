package JavaProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExcelToOracleTest {
	private static final String Insert_Oracle_Query="INSERT INTO STUDENT VALUES(?,?,?,?)";

	public static void main(String[] args) {
		Connection conExcel=null,conOracle=null;
		Statement st=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int cnt=0;
		try {
			//register jdbc Excel driver s/w
			Class.forName("com.hxtt.sql.excel.ExcelDriver");
			// establish the connection
			conExcel= DriverManager.getConnection("jdbc:Excel:///E:\\ExcelTest");
			//register Oracle jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			conOracle=DriverManager.getConnection("Jdbc:Oracle:thin:@localhost:1521:xe","system","manager");
			//create statement 
			if(conExcel!=null)
				st=conExcel.createStatement();
			//create PreparedStatement
			if(conOracle!=null)
				ps=conOracle.prepareStatement(Insert_Oracle_Query);
			//send and execute SQL query in Database software
			if(st!=null)
				rs=st.executeQuery("SELECT * FROM BOOK1.SHEET1");
			//process the Result and copy to Oracle database			
			if(rs!=null && ps!=null) {
				while(rs.next()) {
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
					//set values to params
					ps.setInt(1, rs.getInt(1));
					ps.setString(2, rs.getString(2));
					ps.setString(3, rs.getString(3));
					ps.setFloat(4, rs.getFloat(4));
					//send and execute SQL Query
					cnt=ps.executeUpdate();
				}//while
				System.out.println("These all record are inserted from Excel to Oracle");
			}//if
			if(cnt==0)
				System.out.println("Record not Inserted.");
			else
				System.out.println("Record inserted.");
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
				if(ps!=null)
					ps.close();
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
				if(conOracle!=null)
					conOracle.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(conExcel!=null)
					conExcel.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class