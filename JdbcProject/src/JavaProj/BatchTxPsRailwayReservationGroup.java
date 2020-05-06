package JavaProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class BatchTxPsRailwayReservationGroup {
	public static final String Insert_Query="INSERT INTO INDIAN_RAILWAY_RESERVATION VALUES(TKTID_SEQ.NEXTVAL,?,?,?,?)";

	public static void main(String[] args) {
		Scanner sc=null;
		int count=0;
		Connection con=null;
		PreparedStatement ps=null;
		String psgName,srcPlace=null,dstPlace=null;
		float fare=0.0f;
		int result[]=null;
		boolean flag=false;
		try {
			sc=new Scanner(System.in);
			//read input
			if(sc!=null) {
				System.out.println("Enter total number of passenger::");
				count=sc.nextInt();
				System.out.println("Enter source Place::");
				srcPlace=sc.next();
				System.out.println("Enter destination Place::");
				dstPlace=sc.next();
				System.out.println("Enter fare::");
				fare=sc.nextFloat();
			}
			//register jdbc drive s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//Begin Transaction
			if(con!=null) {
				con.setAutoCommit(false);
				//create prepared statement
				ps=con.prepareStatement(Insert_Query);
			}
			//set values to param and batch
			for(int i=0;i<count;++i) {
				System.out.println("Enter passenger name::");
				psgName=sc.next();
				//set value to param
				ps.setString(1, psgName);
				ps.setString(2, srcPlace);
				ps.setString(3, dstPlace);
				ps.setFloat(4, fare);
				//set value to batch
				ps.addBatch();
			}//for
			//execute batch
			if(ps!=null)
				result=ps.executeBatch();
			//process the result
			if(result!=null) {
				for(int i=0;i<result.length;++i) {
					if(result[i]==0) {
						flag=true;
						break;
					}
				}//for
			}//if
	}//try
		catch(SQLException se) {
			flag=true;
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			flag=true;
			cnf.printStackTrace();
		}
		catch(Exception e) {
			flag=true;
			e.printStackTrace();
		}
		finally {
			try {
				if(flag==true) {
					con.rollback();
					System.out.println("Ticket reservation failed.");
				}
				else {
					con.commit();
					System.out.println("Ticket Reservation successful.");
				}		
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
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
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}			
		}//finally			
	}//main
}//class