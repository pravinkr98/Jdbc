package JavaProj;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ScrollFrameApp extends JFrame implements ActionListener {
	private JLabel lno, lname, ladd, lavg;
	private JTextField tno, tname, tadd, tavg;
	private JButton bFirst, bNext, bPrevious, bLast;
	Connection con;
	Statement st;
	ResultSet rs;

	public ScrollFrameApp() {
		System.out.println("0- param constructor");
		setTitle("ScrollFrameApp");
		setSize(370, 300);
		setLayout(new FlowLayout());
		// add component
		lno = new JLabel("Roll");
		add(lno);
		tno = new JTextField(10);
		add(tno);
		lname = new JLabel("Name");
		add(lname);
		tname = new JTextField(10);
		add(tname);
		ladd = new JLabel("Address");
		add(ladd);
		tadd = new JTextField(10);
		add(tadd);
		lavg = new JLabel("Average");
		add(lavg);
		tavg = new JTextField(10);
		add(tavg);
		bFirst = new JButton("First");
		add(bFirst);
		bFirst.addActionListener(this);
		bNext = new JButton("Next");
		add(bNext);
		bNext.addActionListener(this);
		bPrevious = new JButton("Previous");
		add(bPrevious);
		bPrevious.addActionListener(this);
		bLast = new JButton("Last");
		add(bLast);
		bLast.addActionListener(this);

		tno.setEditable(false);
		tname.setEditable(false);
		tadd.setEditable(false);
		tavg.setEditable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// initialize Jdbc
		initializeJdbc();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("window closing");
				// close jdbc objects
				try {
					if (rs != null)
						rs.close();
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
			}// window closing
		}// anonymous inner class
		);
	}// constructor

	public void initializeJdbc() {
		System.out.println("making connection.");
		try {
			// register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			// create statement object
			if (con != null)
				st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// create Scrollable ResultSet object
			if (st != null)
				rs = st.executeQuery("SELECT SNO,NAME,ADDRS,AVG FROM STUDENT");
		} // try
		catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("actionPerformed(-)");
		System.out.println(ae.getActionCommand());
		boolean flag = false;
		try {
			if (ae.getSource() == bFirst) {
				System.out.println("first button");
				rs.first();
				flag = true;
			} else if (ae.getSource() == bLast) {
				System.out.println("last button");
				rs.last();
				flag = true;
			} else if (ae.getSource() == bNext) {
				System.out.println("next button");
				if (!rs.isLast()) {
					rs.next();
					flag = true;
				}
			} else {
				System.out.println("previous button");
				if (!rs.isFirst()) {
					rs.previous();
					flag = true;
				}
			} // else
				// set record values to text boxes
			if (flag == true) {
				tno.setText(rs.getString(1));
				tname.setText(rs.getString(2));
				tadd.setText(rs.getString(3));
				tavg.setText(rs.getString(4));
			}
		} // try
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// actionPerformed

	public static void main(String[] args) {
		System.out.println("Start of main(-)");
		new ScrollFrameApp();
	}

}
