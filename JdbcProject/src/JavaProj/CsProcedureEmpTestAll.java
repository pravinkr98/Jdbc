package JavaProj;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

/*CREATE OR REPLACE PROCEDURE P_GETEMPDETAILS_ALL 
(
  DEPT_NO IN NUMBER 
, RESULT_EMP OUT SYS_REFCURSOR 
) AS 
BEGIN
OPEN RESULT_EMP FOR
  SELECT ENAME,JOB,SAL FROM EMP WHERE DEPTNO=DEPT_NO;
END P_GETEMPDETAILS_ALL*/

public class CsProcedureEmpTestAll {
	private static final String Call_Procedure="{call P_GETEMPDETAILS_ALL(?,?)}";

	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		CallableStatement cs=null;
		ResultSet rs=null;
		boolean flag=false;
		
		try {
			//read inputs from user
			sc=new Scanner(System.in);
			if(sc!=null) {
			System.out.println("Enter employee department:::");
			no=sc.nextInt();
			}
			//register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create callableStatement object
			if(con!=null)
				cs=con.prepareCall(Call_Procedure);
			//register OUT params to jdbc types
			if(cs!=null) 
				cs.registerOutParameter(2,OracleTypes.CURSOR);
				//set value to params
			if(cs!=null)
				cs.setInt(1,no);
			//execute PL/SQL Procedure
			if(cs!=null)
				cs.execute();
			//gather result from OUT params
			rs=(ResultSet)cs.getObject(2);
			if(rs!=null) {
				while(rs.next()) {
					flag=true;
				System.out.println("Details ::: "+rs.getString(1)+" "+rs.getString(2)+" "+rs.getInt(3));
				}//while
				if(flag==false)
					System.out.println("Record not found.");
			}//if
		}//try
		catch(SQLException se) {
			if(se.getErrorCode()==1403)
				System.out.println("Record not found.");
			else
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
				if(cs!=null)
					cs.close();
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
