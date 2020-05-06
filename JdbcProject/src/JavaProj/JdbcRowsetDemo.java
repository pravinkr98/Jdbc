package JavaProj;

import java.sql.SQLException;

import javax.sql.RowSet;

import oracle.jdbc.rowset.OracleJDBCRowSet;

public class JdbcRowsetDemo {

	public static void main(String[] args) {
		RowSet jrowset=null;
		try {
			jrowset=new OracleJDBCRowSet();
			//set JDBC properties
			jrowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			jrowset.setUsername("scott");
			jrowset.setPassword("tiger");
			jrowset.setCommand("SELECT EMPNO,ENAME,JOB,SAL FROM EMP");
			jrowset.execute();
			System.out.println("command executed");
			//process the Rowset
			while(jrowset.next()) {
				System.out.println(jrowset.getInt(1)+"  "+jrowset.getString(2)+"  "+jrowset.getString(3)+"  "+jrowset.getFloat(4));
			}
			jrowset.close();
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			System.out.println(e.toString());
			//e.printStackTrace();
		}
	}//main
}//class
