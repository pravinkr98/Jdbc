package JavaProj;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import oracle.jdbc.rowset.OracleCachedRowSet;

public class CachedRowSetDemo {

	public static void main(String[] args) {
		CachedRowSet crowset=null;
		try {
			crowset=new OracleCachedRowSet();
			crowset.setUrl("Jdbc:oracle:thin:@localhost:1521:xe");
			crowset.setUsername("system");
			crowset.setPassword("manager");
			crowset.setCommand("SELECT * FROM STUDENT");
			//execute Query
			crowset.execute();
			//process the Rowset
			System.out.println("before modification.");
			while(crowset.next()) {
				System.out.println(crowset.getInt(1)+"  "+crowset.getString(2)+"  "+crowset.getString(3)+"  "+crowset.getFloat(4));
			}
			System.out.println("stop db s/w");
			Thread.sleep(60000); 
			crowset.absolute(3);
			crowset.updateInt(1,5555);
			crowset.updateString(2,"mukesh");
			crowset.updateString(3,"M.P.");
			crowset.updateFloat(4, 77);
			crowset.updateRow();
			System.out.println("Rowset updated in offline mode");
			System.out.println("restart DB s/w here");
			Thread.sleep(60000);
			crowset.acceptChanges();
			while(crowset.next()) {
				System.out.println(crowset.getInt(1)+"  "+crowset.getString(2)+"  "+crowset.getString(3)+"  "+crowset.getFloat(4));
			}
			System.out.println("after modification..");			
		}//try
		catch(SQLException | InterruptedException ie) {
			ie.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//main
}//class