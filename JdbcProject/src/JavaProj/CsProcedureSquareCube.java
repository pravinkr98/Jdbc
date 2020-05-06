package JavaProj;

/*CREATE OR REPLACE PROCEDURE P_SQUARE_CUBE (
  X IN NUMBER, Y OUT NUMBER, Z OUT NUMBER ) AS 
BEGIN
  y:=x*x;
  z:=x*x*x;
END P_SQUARE_CUBE*/

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

public class CsProcedureSquareCube {
	private static final String Call_Procedure = "{call P_SQUARE_CUBE(?,?,?) }";

	public static void main(String[] args) {
		Scanner sc = null;
		int no=0;
		Connection con = null;
		CallableStatement cs = null;
		int result1 = 0,result2=0;

		try {
			sc=new Scanner(System.in);
			// read inputs
			if (sc != null) {
				System.out.println("Enter  number::");
				no = sc.nextInt();
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
				cs.setInt(1, no);
				// register OUT param with jdbc types
				cs.registerOutParameter(2, Types.INTEGER);
				cs.registerOutParameter(3, Types.INTEGER);
			}
			// execute the PL/SQL procedure
			if (cs != null) {
				cs.execute();
				// gather result from OUT parameter
				result1 = cs.getInt(2);
				result2 = cs.getInt(3);
				System.out.println("square of "+no+" = " + result1);
				System.out.println("cube of "+no+" = " + result2);
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
