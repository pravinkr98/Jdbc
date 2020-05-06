package JavaProj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScrollableTest {
	private static final String GET_STUDENT_QUERY="SELECT SNO,NAME,ADDRS,AVG FROM STUDENT";

	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		//Statement st=null;
		ResultSet rs=null;
		try {
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create PreparedStatement object with type,mode volue
			if(con!=null) {
				//ps=con.prepareStatement(GET_STUDENT_QUERY,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ps=con.prepareStatement(GET_STUDENT_QUERY,1005,1008);
			}
			//create ResultSet object
			if(ps!=null)
				rs=ps.executeQuery(GET_STUDENT_QUERY);
			//display records(top-bottom)
			if(rs!=null) {
				System.out.println("Top---Bottom");
				while(rs.next()) {
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				}
				System.out.println("Bottom---Top");
				while(rs.previous()) {
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				}
				System.out.println("-------------------------------------------------");
				//first Record
				rs.first();
				System.out.println(rs.getRow()+"------> "+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				//last Record
				rs.last();
				System.out.println(rs.getRow()+"------> "+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				//3rd record from top
				rs.absolute(3);
				System.out.println(rs.getRow()+"------> "+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				//3rd record from bottom
				rs.absolute(-3);
				System.out.println(rs.getRow()+"------> "+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				//relative
				rs.relative(2);
				System.out.println(rs.getRow()+"------> "+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				//relative
				rs.relative(-4);
				System.out.println(rs.getRow()+"------> "+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
			}//if
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
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(ps!=null)
					ps.close();
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