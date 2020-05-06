package JavaProj;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SelectClobOracle {
	private static final String Clob_Query = "SELECT JSID,JSNAME,ADDRS,QLFY,RESUME FROM JOBPORTAL_INFO WHERE JSID=?";

	public static void main(String[] args) {
		Scanner sc = null;
		Connection con = null;
		PreparedStatement ps = null;
		Reader reader = null;
		Writer writer = null;
		int id = 0, charsRead = 0;
		ResultSet rs = null;
		char[] buffer = null;

		try {
			// read inputs from user
			sc = new Scanner(System.in);
			if (sc != null) {
				System.out.println("Enter Jobseeker ID : ");
				id = sc.nextInt();
			}
			// register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			// create preparedStatement
			if (con != null)
				ps = con.prepareStatement(Clob_Query);
			// set values in params
			if (ps != null) {
				ps.setInt(1, id);
			}
			// execute the query
			if (ps != null)
				rs = ps.executeQuery();
			// process the result
			if (rs.next()) {
				reader = rs.getCharacterStream(5);
				System.out
						.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
			} else
				System.out.println("Record insertion failed.");
			// create OutputStream for Dest file
			writer = new FileWriter("new_resume.txt");
			// write buffer based logic to copy file content
			buffer = new char[1024];
			if (reader != null && writer != null) {
				while ((charsRead = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, charsRead);
				} // while
				System.out.println("Clob file coppied successfully.");
			} // if
		} // try
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
			try {
				if (sc != null)
					sc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (reader != null)
					reader.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			try {
				if (writer != null)
					writer.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} // finally

	}// main

}// class
