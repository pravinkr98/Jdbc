package JavaProj;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropTest {

	public static void main(String[] args) {
		InputStream is=null;
		Properties props=null;
		
		try {
			//locate file using inputStream
			is=new FileInputStream("src/commons/personalDetails.properties");
			//load Properties file data into java.util.Properties class object
			props=new Properties();
			props.load(is);
			System.out.println("props data name : "+props.getProperty("name"));
			System.out.println("props data age : "+props.getProperty("age"));
			System.out.println("props data address : "+props.getProperty("address"));
		}//try
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//main
}//class