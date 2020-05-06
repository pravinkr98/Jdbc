package JavaProj;

/*CREATE OR REPLACE PROCEDURE P_ADD (
  X IN NUMBER, Y IN NUMBER, Z OUT NUMBER ) AS 
BEGIN
  z:=x+y;
END P_ADD*/

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

public class CsProcedure {
	private static final String Call_Procedure = "{call P_Add(?,?,?) }";

	public static void main(String[] args) {
		Scanner sc = null;
		int first = 0, second = 0;
		Connection con = null;
		CallableStatement cs = null;
		int result = 0;

		try {
			sc=new Scanner(System.in);
			// read inputs
			if (sc != null) {
				System.out.println("Enter first number::");
				first = sc.nextInt();
				System.out.println("Enter second number::");
				second = sc.nextInt();
			}
			// register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			// create callableStatement object
			if (con != null)
				cs = con.prepareCall(Call_Procedure);
			// set value to params
			if (cs != null) {
				cs.setInt(1, first);
				cs.setInt(2, second);
				// register OUT param with jdbc types
				cs.registerOutParameter(3, Types.INTEGER);
			}
			// execute the PL/SQL procedure
			if (cs != null) {
				cs.execute();
				// gather result from OUT parameter
				result = cs.getInt(3);
				System.out.println("sum of "+first+" + "+second+" = " + result);
			}
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
