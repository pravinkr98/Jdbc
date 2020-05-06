package JavaProj;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class BoysRegisterGUI extends JFrame implements ActionListener {
	private static final String Insert_Query = "INSERT INTO BOYS_REGISTER VALUES(APPID_SEQ.NEXTVAL,?,?,?)";
	private Connection con = null;
	private PreparedStatement ps = null;
	private JLabel lname, laddrs, lcourse, lresult;
	private JTextField tname, taddrs, tcourse, tresult;
	private JButton register;

	public BoysRegisterGUI() {
		System.out.println("Constructor call");
		lname = new JLabel("Name");
		add(lname);
		tname = new JTextField(12);
		add(tname);
		laddrs = new JLabel("Address");
		add(laddrs);
		taddrs = new JTextField(12);
		add(taddrs);
		lcourse = new JLabel("Course");
		add(lcourse);
		tcourse = new JTextField(12);
		add(tcourse);
		register = new JButton("Register");
		add(register);
		register.addActionListener(this);
		lresult = new JLabel("result ::");
		add(lresult);
		
		//add windowListener
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ae) {
				//close jdbc objects
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
			}//windowClosing
		}//anonymous inner class
		);

		// initiallize the jdbc connection
		initializeJdbc();

		setSize(400, 200);
		setLayout(new FlowLayout());
		setTitle("Boys Registeration form");
		setBackground(Color.gray);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void initializeJdbc() {
		try {
			System.out.println("Making Connection.");
			// register jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			// create preparedStatement
			if (con != null)
				ps = con.prepareStatement(Insert_Query);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("Start of Main.");
		new BoysRegisterGUI();
		System.out.println("End of Main.");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name, addrs, course;
		int result = 0;
		System.out.println("Action Performed.");
		try {
			// read text box value
			name = tname.getText();
			addrs = taddrs.getText();
			course = tcourse.getText();
			// set value to params
			ps.setString(1, name);
			ps.setString(2, addrs);
			ps.setString(3, course);
			// execute the query
			result = ps.executeUpdate();
			if (result == 1) {
				lresult.setForeground(Color.GREEN);
				lresult.setText("Registration Successful.");
			} else
				lresult.setForeground(Color.RED);
		} // try
		catch (SQLException se) {
			lresult.setText("Registration failed.");
			se.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// empty text boxex
			tname.setText("");
			taddrs.setText("");
			tcourse.setText("");
		} // finally
	}// action performed

}// class
