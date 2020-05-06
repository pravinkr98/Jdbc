package JavaProj;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertClobMysql {
	private static final String Insert_Query = "INSERT INTO JOBPORTAL_INFO(JSNAME,ADDRS,QLFY,RESUME) VALUES(?,?,?,?)";

	public static void main(String[] args) {
		Scanner sc = null;
		String name = null, addr = null, qlfy = null, resumePath = null;
		Connection con = null;
		PreparedStatement ps = null;
		Reader reader = null;
		File file = null;
		int result = 0;

		try {
			// read inputs from user
			sc = new Scanner(System.in);
			if (sc != null) {
				System.out.println("Enter Jobseeker name : ");
				name = sc.next();
				System.out.println("Enter Jobseeker address : ");
				addr = sc.next();
				System.out.println("Enter Jobseeker qualification : ");
				qlfy = sc.next();
				System.out.println("Enter resume path : ");
				resumePath = sc.next();
			}
			// register jdbc object
			Class.forName("com.mysql.cj.jdbc.Driver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:mysql:///praveendb", "root", "root");
			// create preparedStatement
			if (con != null)
				ps = con.prepareStatement(Insert_Query);
			// create reader object hold CLOB value
			file = new File(resumePath);
			reader = new FileReader(file);
			// set values in params
			if (ps != null) {
				ps.setString(1, name);
				ps.setString(2, addr);
				ps.setString(3, qlfy);
				ps.setCharacterStream(4, reader, (int) file.length());
			}
			// execute the query
			if (ps != null)
				result = ps.executeUpdate();
			// process the result
			if (result == 0)
				System.out.println("Record insertion failed.");
			else
				System.out.println("Record insertion successfully.");
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
		} // finally

	}// main

}// class
