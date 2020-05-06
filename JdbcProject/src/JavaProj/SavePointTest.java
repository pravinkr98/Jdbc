package JavaProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Scanner;

public class SavePointTest {
	private static final String PRODUCT_QUERY = "UPDATE PRODUCT SET QTY=QTY-1 WHERE PID=?";
	private static final String PAYMENT_QUERY = "UPDATE BANKACCOUNT SET BALANCE=BALANCE-(SELECT PRICE FROM PRODUCT WHERE PID=?)WHERE ACNO=?";

	public static void main(String[] args) {
		Scanner sc = null;
		int id = 0, acid = 0;
		Connection con = null;
		PreparedStatement ps1=null, ps2=null;
		Savepoint sp = null;
		int count1 = 0, count2 = 0;

		try {
			sc = new Scanner(System.in);
			// read input from user
			if (sc != null) {
				System.out.println("Enter product id::");
				id = sc.nextInt();
				System.out.println("Enter account no::");
				acid = sc.nextInt();
			}
			// register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			// begin Tx
			if (con != null)
				con.setAutoCommit(false);
			// create prepare statement
			if (con != null) {
				ps1 = con.prepareStatement(PRODUCT_QUERY);
				ps2 = con.prepareStatement(PAYMENT_QUERY);
			}
			// set values to Query params
			if (ps1 != null && ps2 != null) {
				ps1.setInt(1, id);
				ps2.setInt(1, id);
				ps2.setInt(2, acid);
			}
			// send and execute query
			if (ps1 != null && ps2 != null) {
				count1 = ps1.executeUpdate();
				sp = con.setSavepoint("sp1");
				count2 = ps2.executeUpdate();
			}
			if (count1 != 0 && count2 != 0) {
				con.commit();
				System.out.println("Order Confirmed.");
			} else if (count1 == 0 && count2 == 0) {
				con.rollback();
				System.out.println("Product not booked and payment not done.");
			} else if (count1 == 0 && count2 != 0) {
				System.out.println("Booking not confirm because of product not available.");
			} else if (count1 != 0 && count2 == 0) {
				con.rollback(sp);
				con.commit();
				System.out.println("Order confirmed payment failed & it is eligible for cash on delivery.");
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
				if (ps1 != null)
					ps1.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (ps2 != null)
					ps2.close();
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