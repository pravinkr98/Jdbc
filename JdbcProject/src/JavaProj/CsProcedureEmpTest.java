package JavaProj;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*CREATE TABLE "SYSTEM"."EMP" 
(	"EMPNO" NUMBER(4,0), 
	"ENAME" VARCHAR2(10 BYTE), 
	"JOB" VARCHAR2(9 BYTE), 
	"MGR" NUMBER(4,0), 
	"HIREDATE" DATE, 
	"SAL" NUMBER(7,2), 
	"COMM" NUMBER(7,2), 
	"DEPTNO" NUMBER(2,0), 
	 CONSTRAINT "PK_EMP" PRIMARY KEY ("EMPNO")*/

/*CREATE OR REPLACE PROCEDURE P_GETEMPDETAILS 
(
  ENO IN NUMBER 
, ENAME OUT VARCHAR2 
, DESG OUT VARCHAR2 
, SALARY OUT NUMBER 
) AS 
BEGIN
  SELECT ENAME,JOB,SAL INTO ENAME,DESG,SALARY FROM EMP WHERE EMPNO=ENO;
END P_GETEMPDETAILS*/

public class CsProcedureEmpTest {
	private static final String Call_Procedure="{call P_GETEMPDETAILS(?,?,?,?)}";

	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		CallableStatement cs=null;
		
		try {
			//read inputs from user
			sc=new Scanner(System.in);
			if(sc!=null) {
			System.out.println("Enter employee id:::");
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
			if(cs!=null) {
				cs.registerOutParameter(2,Types.VARCHAR);
				cs.registerOutParameter(3,Types.VARCHAR);
				cs.registerOutParameter(4,Types.INTEGER);
			}
			//set value to params
			if(cs!=null)
				cs.setInt(1,no);
			//execute callable statement
			if(cs!=null)
				cs.execute();
			//gather result from OUT params
			if(cs!=null)
				System.out.println("Details ::: "+cs.getString(2)+" "+cs.getString(3)+" "+cs.getInt(4));
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
