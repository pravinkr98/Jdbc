package JavaProj;

import java.sql.SQLException;

import oracle.jdbc.rowset.OracleCachedRowSet;
import oracle.jdbc.rowset.OracleJoinRowSet;

public class JoinRowsetDemo {

	public static void main(String[] args) {
		OracleJoinRowSet jrowset=null;
		OracleCachedRowSet crowset=null,crowset2=null;
		
		try {
			//crowset 1
			crowset=new OracleCachedRowSet();
			crowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crowset.setUsername("scott");
			crowset.setPassword("tiger");
			crowset.setCommand("SELECT EMPNO,ENAME,SAL,DEPTNO FROM EMP");
			crowset.setMatchColumn(4);
			crowset.execute();
			
			//crowset2
			crowset2=new OracleCachedRowSet();
			crowset2.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crowset2.setUsername("scott");
			crowset2.setPassword("tiger");
			crowset2.setCommand("SELECT DEPTNO,DNAME,LOC FROM DEPT");
			crowset2.setMatchColumn(1);
			crowset2.execute();
			
			//JoinRowSet
			jrowset=new OracleJoinRowSet();
			jrowset.addRowSet(crowset2);
			jrowset.addRowSet(crowset);
			
			//process JoinRowSet
			while(jrowset.next()) {
				System.out.println(jrowset.getString(1)+"  "+jrowset.getString(2)+"  "+jrowset.getString(3)+"  "+jrowset.getString(4)+"  "+jrowset.getString(5)+"  "+jrowset.getString(6));
			}//while
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(jrowset!=null)
					jrowset.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(crowset!=null)
					crowset.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(crowset2!=null)
					crowset2.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
