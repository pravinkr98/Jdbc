package JavaProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TxMgmtTest {

	public static void main(String[] args) {
		Scanner sc=null;
		int srcNo=0,dstNo=0,amt=0;
		Connection con=null;
		Statement st=null;
		int result[]=null;
		boolean flag=false;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter source Account no::");
				srcNo=sc.nextInt();
				System.out.println("Enter destination Account no::");
				dstNo=sc.nextInt();
				System.out.println("Enter Amount to transfer::");
				amt=sc.nextInt();		
			}
			//register jdbc drive s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:oci8:@xe","system","manager");
			//Begin Transaction
			if(con!=null) {
				con.setAutoCommit(false);
				//create statement object
				st=con.createStatement();
			}
			//add queries to the batch
			if(st!=null) {
				//withdraw amount from source account
				st.addBatch("UPDATE JDBC_BANK_ACCOUNT SET BALANCE=BALANCE-"+amt+" WHERE ACCNO="+srcNo);
				//deposit amount into dest account
				st.addBatch("UPDATE JDBC_BANK_ACCOUNT SET BALANCE=BALANCE+"+amt+" WHERE ACCNO="+dstNo);
				//execute batch
				result=st.executeBatch();
			}//if
			//perform Tx mgmt(Commit or rollback
			if(result!=null) {
				for(int i=0;i<result.length;++i) {
					if(result[i]==0) {
						flag=true;
						break;
					}//if
				}//for
				if(flag==true) {
					con.rollback();
					System.out.println("Tx rolledback -- money not transfered");
				}
				else {
					con.commit();
					System.out.println("Tx committed --money transfered");
				}
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