package JavaProj;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*CREATE TABLE "SYSTEM"."ALL_STUDENT" 
(	"SNO" NUMBER NOT NULL ENABLE, 
	"COLUMN1" VARCHAR2(20 BYTE), 
	"M1" NUMBER, 
	"M2" NUMBER, 
	"M3" NUMBER, 
	 CONSTRAINT "ALL_STUDENT_PK" PRIMARY KEY ("SNO")*/

/*CREATE OR REPLACE PROCEDURE P_FIND_PASS_FAIL 
(
  M1 IN NUMBER, M2 IN NUMBER, M3 IN NUMBER, RESULT OUT VARCHAR2) AS 
BEGIN
  IF(M1<35 OR M2<35 OR M3<35) THEN
  RESULT:='FAIL';
  ELSE 
  RESULT:='PASS';
  END IF;
END P_FIND_PASS_FAIL*/

public class AllStatementTestGUIWithChange extends JFrame implements ActionListener {
	private static final String St_Query_Call = "SELECT SNO FROM ALL_STUDENT";
	private static final String Ps_Query_Call = "SELECT * FROM ALL_STUDENT WHERE SNO=?";
	private static final String Cs_Query_Call = "{call P_FIND_PASS_FAIL(?,?,?,?) }";

	private JLabel lno, lname, lm1, lm2, lm3, lresult;
	private JTextField tname, tm1, tm2, tm3, tresult, tresult2;
	private JButton bDetails, bResult, bRefresh;
	private JComboBox cbno;
	private Connection con;
	private Statement st;
	private PreparedStatement ps;
	private CallableStatement cs;
	private ResultSet rs1, rs2;

	// constructor
	public AllStatementTestGUIWithChange() {
		System.out.println("AllStatementTestUI:0-param constructor");
		setTitle("Mini Project");
		setSize(370, 400);
		setBackground(Color.gray);
		setLayout(new FlowLayout());
		// add component
		lno = new JLabel("Student Id");
		add(lno);
		cbno = new JComboBox();
		add(cbno);
		bDetails = new JButton("Details");
		add(bDetails);
		bDetails.addActionListener(this);
		lname = new JLabel("Name");
		add(lname);
		tname = new JTextField(10);
		add(tname);
		lm1 = new JLabel("Marks1:");
		add(lm1);
		tm1 = new JTextField(10);
		add(tm1);
		lm2 = new JLabel("Marks2:");
		add(lm2);
		tm2 = new JTextField(10);
		add(tm2);
		lm3 = new JLabel("Marks3:");
		add(lm3);
		tm3 = new JTextField(10);
		add(tm3);
		bResult = new JButton("Result");
		add(bResult);
		bResult.addActionListener(this);
		lresult = new JLabel("Result");
		add(lresult);
		tresult = new JTextField(10);
		add(tresult);
		bRefresh = new JButton("Refresh");
		add(bRefresh);
		bRefresh.addActionListener(this);
		tresult2 = new JTextField(30);
		add(tresult2);
		// disable editing of comps
		tname.setEditable(false);
		tm1.setEditable(false);
		tm2.setEditable(false);
		tm3.setEditable(false);
		tresult.setEditable(false);
		tresult2.setEditable(false);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new MyWindowAdapter());
		// make a connection
		initializeJdbc();
	}

	private void initializeJdbc() {
		System.out.println("Making connection.");
		try {
			// register jdbc driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			// create statement object
			st = con.createStatement();
			// execute the query
			if (st != null)
				rs1 = st.executeQuery(St_Query_Call);
			// copy ResultSet sno values to Combo Box
			if (rs1 != null) {
				while (rs1.next()) {
					cbno.addItem(rs1.getInt(1));
				}
			}
			// create PreparedStatement object
			if (con != null)
				ps = con.prepareStatement(Ps_Query_Call);
			// create callable statement object
			if (con != null) {
				cs = con.prepareCall(Cs_Query_Call);
				cs.registerOutParameter(4, Types.VARCHAR);
			}
		} // try
		catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
	}// initializeJdbc

	public void actionPerformed(ActionEvent ae) {
		System.out.println("actionPerformed(-)");
		if (ae.getSource() == bDetails) {
			System.out.println("Details button is clicked");
			try {
				// get the selected value from comboBox
				int no = (Integer) cbno.getSelectedItem();
				// set value to query param
				if (ps != null) {
					ps.setInt(1, no);
					// execute the Query
					rs2 = ps.executeQuery();
				}
				// set ResultSet object record to textboxes
				if (rs2 != null) {
					if (rs2.next()) {
						tname.setText(rs2.getString(2));
						tm1.setText(rs2.getString(3));
						tm2.setText(rs2.getString(4));
						tm3.setText(rs2.getString(5));
					}
				}
				tresult.setText("");
				tresult2.setText("");
			} // try
			catch (SQLException se) {
				se.printStackTrace();
			}
		} // if
		else if (ae.getSource() == bResult) {
			System.out.println("Result button is clicked");
			try {
				if (rs2 != null) {
					int no = (Integer) cbno.getSelectedItem();
					if (no == rs2.getInt(1))
						checkResult();
					else {
						System.out.println("Please click on the Details button first");
						tresult2.setForeground(Color.RED);
						tresult2.setText("Please click on the Details button first");
						tresult.setText("");
					}
				}
				else {
					tresult2.setForeground(Color.RED);
					tresult2.setText("Please click on the Details button first");
					tresult.setText("");
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} else {
			System.out.println("Refresh button is clicked");
			tname.setText("");
			tm1.setText("");
			tm2.setText("");
			tm3.setText("");
			tresult.setText("");
		}
	}

	private void checkResult() {
		int m1 = 0, m2 = 0, m3 = 0;
		String result = null;
		try {
			// read text box values(m1,m2,m3)
			/*
			 * m1 = Integer.parseInt(tm1.getText()); m2 = Integer.parseInt(tm2.getText());
			 * m3 = Integer.parseInt(tm3.getText());
			 */
			m1=rs2.getInt(3);
			m2=rs2.getInt(4);
			m3=rs2.getInt(5);
			// set values to IN params
			if (cs != null) {
				cs.setInt(1, m1);
				cs.setInt(2, m2);
				cs.setInt(3, m3);
				// execute PL/SQL procedure
				cs.execute();
				// gather value from OUT params and get result
				result = cs.getString(4);
				// set result to Text box
				if (result.equalsIgnoreCase("pass")) {
					tresult.setForeground(Color.GREEN);
					tresult.setText(result);
				} else {
					tresult.setForeground(Color.RED);
					tresult.setText(result);
				}
				tresult2.setText("");
			} // if
		} // try
		catch (SQLException se) {
			se.printStackTrace();
		}

	}

	public static void main(String[] args) {
		System.out.println("Main starts.");
		new AllStatementTestGUIWithChange();
	}

	private class MyWindowAdapter extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.out.println("windowClosing");
			try {
				if (rs1 != null)
					rs1.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (rs2 != null)
					rs2.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (cs != null)
					cs.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (st != null)
					st.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}// method
	}// inner class
}// class