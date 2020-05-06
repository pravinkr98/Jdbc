package JavaProj;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

/*CREATE OR REPLACE FUNCTION GET_FX_STUDENT_FOR_DELETION 
(
  NO IN NUMBER 
, CNT OUT NUMBER 
) RETURN SYS_REFCURSOR AS 
DETAILS SYS_REFCURSOR;
BEGIN
OPEN DETAILS FOR
SELECT * FROM STUDENT WHERE SNO=NO;
DELETE FROM STUDENT WHERE SNO=NO;
CNT:=SQL%ROWCOUNT;
  RETURN DETAILS;
END GET_FX_STUDENT_FOR_DELETION*/

public class CsFunctionStudentDeletion {
	private static final String Call_Function = "{?= call GET_FX_STUDENT_FOR_DELETION (?,?)}";

	public static void main(String[] args) {
		Scanner sc = null;
		int eno = 0, result = 0;
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			// read inputs
			sc = new Scanner(System.in);
			if (sc != null) {
				System.out.println("Enter Student number ::");
				eno = sc.nextInt();
			}
			// register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			// create callableStatement object
			if (con != null)
				cs = con.prepareCall(Call_Function);
			// register OUT params with jdbc types
			if (cs != null) {
				cs.registerOutParameter(1, OracleTypes.CURSOR);
				cs.registerOutParameter(3, Types.INTEGER);
			}
			// set value to IN params
			if (cs != null)
				cs.setInt(2, eno);
			// execute PL/SQL Procedure
			if (cs != null)
				cs.execute();
			// gather result from OUT params
			if (cs != null)
				rs = (ResultSet) cs.getObject(1);
			// process the resultSet
			if (rs != null) {
				if (rs.next())
					System.out.println("Details ::: " + " " + rs.getInt(1) + " " + rs.getString(2)+" "+rs.getString(3)+" ");
			} // if
			result = cs.getInt(3);
			if (result == 0)
				System.out.println("Record not found.");
			else
				System.out.println("Record deleted.");
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
				if (rs != null)
					rs.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
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
