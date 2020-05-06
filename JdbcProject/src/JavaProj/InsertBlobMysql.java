package JavaProj;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertBlobMysql {
	private static final String INSERT_QUERY = "INSERT INTO MATERIMONY_PROFILE(APPNAME,AGE,GENDER,PHOTO) VALUES(?,?,?,?)";

	public static void main(String[] args) {
		Scanner sc = null;
		String name = null, gender = null;
		String photopath = null;
		int age = 0;
		Connection con = null;
		PreparedStatement ps = null;
		File f = null;
		InputStream is = null;
		int result = 0;
		long length = 0;

		try {
			// read input from user
			sc = new Scanner(System.in);
			System.out.println("Enter Applicant name ::");
			name = sc.next();
			System.out.println("Enter Age :: ");
			age = sc.nextInt();
			System.out.println("Enter gender :: ");
			gender = sc.next();
			System.out.println("Enter photo path :: ");
			photopath = sc.next();
			// register jdbc driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:mysql:///PraveenDB", "root", "root");
			// create Prepared statement
			if (con != null)
				ps = con.prepareStatement(INSERT_QUERY);
			// create file location
			f = new File(photopath);
			length = f.length();
			// create InputStream
			is = new FileInputStream(f);
			// set values to params
			if (ps != null) {
				ps.setString(1, name);
				ps.setInt(2, age);
				ps.setString(3, gender);
				ps.setBlob(4, is, length);
			}
			// execute the query
			if (ps != null)
				result = ps.executeUpdate();
			// process the result
			if (result == 0)
				System.out.println("Record not inserted.");
			else
				System.out.println("Record inserted.");
		} // try
		catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Record not inserted.");
		} catch (ClassNotFoundException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close jdbc object
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
		}//finally

	}//main

}//class
