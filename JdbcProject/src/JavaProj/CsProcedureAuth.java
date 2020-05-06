package JavaProj;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*CREATE OR REPLACE PROCEDURE P_AUTH 
(
  UNAME IN VARCHAR2 
, PWD IN VARCHAR2 
, RESULT OUT VARCHAR2 
) AS 
CNT NUMBER(4);
BEGIN
  SELECT COUNT(*) INTO CNT FROM USER_INFO WHERE USERNAME=UNAME AND PASSWORD=PWD;
  IF(CNT<>0) THEN
  RESULT:='Valid Credentials';
  ELSE
  RESULT:='Invalid Credentials';
  END IF;
END P_AUTH;*/

public class CsProcedureAuth {
	private static final String Call_Procedure="{call P_AUTH(?,?,?)}";

	public static void main(String[] args) {
		Scanner sc=null;
		String uname=null,pass=null;
		Connection con=null;
		CallableStatement cs=null;
		
		try {
			//read inputs from user
			sc=new Scanner(System.in);
			if(sc!=null) {
			System.out.println("Enter Username:::");
			uname=sc.nextLine();
			System.out.println("Enter Password:::");
			pass=sc.nextLine();
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
				cs.registerOutParameter(3,Types.VARCHAR);
			//set value to params
			if(cs!=null)
				cs.setString(1,uname);
				cs.setString(2,pass);
			//execute callable statement
			if(cs!=null)
				cs.execute();
			//gather result from OUT params
			if(cs!=null)
				System.out.println("Results ::: "+cs.getString(3));
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
