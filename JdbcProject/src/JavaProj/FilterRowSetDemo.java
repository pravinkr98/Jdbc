package JavaProj;

import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.Predicate;

import oracle.jdbc.rowset.OracleFilteredRowSet;

class Filter1 implements Predicate{
		private String colName;
		public Filter1(String colName) {
			this.colName=colName;
		}
		@Override
		public boolean evaluate(RowSet rs) {
			System.out.println("evaluate");
			try {
				CachedRowSet crs=(CachedRowSet)rs;
				String object =crs.getString(colName);
				if(object!=null && (object.charAt(0)=='A' || object.charAt(0)=='a')) {
					return true;
				}
				else
					return false;
			}
			catch(Exception e) {
				return false;
			}
		}//method
		@Override
		public boolean evaluate(Object arg0, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean evaluate(Object arg0, String arg1) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}	
		
	}//Filter1 class

public class FilterRowSetDemo {

	public static void main(String[] args) throws Exception{
		//create Filtered Rowset
		OracleFilteredRowSet frs=new OracleFilteredRowSet();
		frs.setUsername("scott");
		frs.setPassword("tiger");
		frs.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		frs.setCommand("SELECT * FROM EMP");
		frs.setFilter(new Filter1("ename"));
		frs.execute();
		while(frs.next()) {
			System.out.println(frs.getInt(1)+"  "+frs.getString(2)+"  "+frs.getString(3));
		}//while
	}//main
}//class
