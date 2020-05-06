package JavaProj;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BlobReterive {
	private static final String Blob_Query="SELECT * FROM MATERIMONY_PROFILE WHERE APPID=?";

	public static void main(String[] args) {
		Scanner sc=null;
		int id=0,bytesRead=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		InputStream is=null;
		OutputStream os=null;
		byte[] buffer=null;
		
		try {
			sc=new Scanner(System.in);
			//read input from user
			System.out.println("Enter applicant id :: ");
			id=sc.nextInt();
			/*//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");*/
			
			// register jdbc driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:mysql:///PraveenDB", "root", "root");
			
			//create preparedstatement
			if(con!=null)
				ps=con.prepareStatement(Blob_Query);
			//set value to param
			if(ps!=null)
				ps.setInt(1, id);
			//execute the query
			if(ps!=null)
				rs=ps.executeQuery();
			//process the resultset (read BLOB value)
			if(rs!=null) {
				if(rs.next()) {
					is=rs.getBinaryStream(5);
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
				}
				else
					System.out.println("Record not Found.");
			}
				//create outputStream
				os=new FileOutputStream("new_img.jpg");
				//create buffer of 4KB size
				buffer=new byte[4096];
				//write buffer based logic to copy file content
				if(is!=null&&os!=null) {
					while((bytesRead=is.read(buffer))!=-1) {
						os.write(buffer,0,bytesRead);
					}//while
					System.out.println("Photo retrived succesfully.");
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
			catch(Exception e) {
				e.printStackTrace();
			}
			try {
				if(os!=null)
					os.close();
				}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
			try {
				if(os!=null)
					os.close();
				}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}//finally

	}//main

}//class
