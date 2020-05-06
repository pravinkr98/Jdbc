package JavaProj;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import oracle.jdbc.rowset.OracleWebRowSet;

public class WebRowsetDemo {

	public static void main(String[] args) {
		OracleWebRowSet wrowset=null;
		try {
			wrowset=new OracleWebRowSet();
			//set JDBC properties
			wrowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			wrowset.setUsername("scott");
			wrowset.setPassword("tiger");
			wrowset.setCommand("SELECT EMPNO,ENAME,JOB,SAL FROM EMP");
			wrowset.execute();
			System.out.println("command executed");
			//process the Rowset
			while(wrowset.next()) {
				System.out.println(wrowset.getInt(1)+"  "+wrowset.getString(2)+"  "+wrowset.getString(3)+"  "+wrowset.getFloat(4));
			}
			//create or locate file
			File myFile=new File("empXml.xml");
			FileWriter fw=new FileWriter(myFile);
			System.out.println("Writing Database to file"+myFile.getAbsolutePath());
			wrowset.writeXml(fw);//write the records of Rowset to file
			//convert xml to a String object for display purpose
			StringWriter sw=new StringWriter();
			wrowset.writeXml(sw);
			System.out.println(sw.toString());
			fw.flush();
			fw.close();		
			wrowset.close();
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
