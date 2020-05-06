package JavaProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ExcelInsertTest {
	private static final String Insert_Text_Query = "INSERT INTO BOOK1.SHEET1 VALUES(?,?,?,?)";

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement ps = null;
		Scanner sc = null;
		int roll = 0,cnt=0;
		String name, addrs;
		float avg;
		try {
			sc = new Scanner(System.in);
			System.out.println("Enter roll no ::");
			roll = sc.nextInt();
			System.out.println("Enter student name ::");
			name = sc.next();
			System.out.println("Enter address ::");
			addrs = sc.next();
			System.out.println("Enter average marks ::");
			avg = sc.nextFloat();
			// register jdbc driver
			Class.forName("com.hxtt.sql.excel.ExcelDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:Excel:///E:\\ExcelTest");
			// create statement object
			if (con != null)
				ps = con.prepareStatement(Insert_Text_Query);
			// set value in params
			if (ps != null) {
				ps.setInt(1, roll);
				ps.setString(2, name);
				ps.setString(3, addrs);
				ps.setFloat(4, avg);
			}
			// send and execute SQL query in Database software		
			 if(ps!=null)
				 cnt=ps.executeUpdate(); 
			 if(cnt!=0)
				 System.out.println("Record inserted");
			 else
				 System.out.println("Record not inserted");
			 /*//process the ResultSet if(rs!=null) {
			 * while(rs.next()) {
			 * System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)
			 * +"  "+rs.getFloat(4)); }//while }//if
			 */ } // try
		catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close jdbc objects
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} // finally
	}// main
}// class