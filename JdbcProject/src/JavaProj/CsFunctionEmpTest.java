package JavaProj;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

/*CREATE OR REPLACE FUNCTION FX_GET_EMP_DETAILS 
(
  ENO IN NUMBER 
, ENAME OUT VARCHAR2 
, SALARY OUT NUMBER 
) RETURN VARCHAR2 AS 
    DESG VARCHAR2(5);
BEGIN
  SELECT ENAME,JOB,SAL INTO ENAME,DESG,SALARY FROM EMP WHERE EMPNO=ENO;
  RETURN DESG;
END FX_GET_EMP_DETAILS*/

public class CsFunctionEmpTest {
	private static final String Call_Function = "{?= call FX_GET_EMP_DETAILS(?,?,?)}";

	public static void main(String[] args) {
		Scanner sc = null;
		int eno = 0;
		Connection con = null;
		CallableStatement cs = null;

		try {
			// read inputs
			sc = new Scanner(System.in);
			if (sc != null) {
				System.out.println("Enter Employee number ::");
				eno = sc.nextInt();
			}
			// register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			// create callableStatement object
			if (con != null)
				cs = con.prepareCall(Call_Function);
			// register OUT params to jdbc types
			if (cs != null) {
				cs.registerOutParameter(1, Types.VARCHAR);
				cs.registerOutParameter(3, Types.VARCHAR);
				cs.registerOutParameter(4, Types.INTEGER);
			}
			// set value to IN params
			if (cs != null)
				cs.setInt(2, eno);
			// execute PL/SQL Procedure
			if (cs != null)
				cs.execute();
			// gather result from OUT params
			if (cs != null)
				System.out.println("Details ::: " + cs.getString(3) + " " + cs.getString(1) + " " + cs.getInt(4));
		} // try
		catch (SQLException se) {
			if (se.getErrorCode() == 1403)
				System.out.println("Record not found.");
			else
				se.printStackTrace();
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close jdbc objects
			try {
				if (cs != null)
					cs.close();
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
		} // finally
	}// main
}// class
