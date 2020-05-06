package JavaProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchProcessTest {

	public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		int result[]=null;
		int sum=0;
		try {
			//register jdbc driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create statement object
			if(con!=null)
				st=con.createStatement();
			//add queries to the batch
			if(st!=null) {
				st.addBatch("insert into student values(1213,'ramesh','hyd',89.5)");
				st.addBatch("update student set addrs='delhi' where sno>=10");
				st.addBatch("delete from student where sno=2");
				//execute the batch
				result=st.executeBatch();				
			}
			//process the results
			if(result!=null) {
				for(int i=0;i<result.length;++i) {
					sum=sum+result[i];
				}//for
				System.out.println("No. of records that are effected ::"+sum);
			}//if
		}//try
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close jdbc objects
			try {
				if(st!=null)
					st.close();
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
		}//finally
	}//main
}//class
