package com.ihec.bankSys;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import com.SQLite.Statement.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextPane;

//the main class
public class BankSysMain {
//the var and object wanted out of this class
	private JFrame frame;
	static JTextField txtSearch;
	static JTextField txtAccount;
	static JTextField txtName;
	static JTextField txtAmount;
	static JTextField txtEnterAmount;
	static JButton btnSearch;
	static JButton btnDeposition;
	static JButton btnWithdraw;
	static CustomInfo CI;

	Operations op = new Operations();

	// Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankSysMain window = new BankSysMain();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create the application.
	public BankSysMain() {
		initialize();
		dbconnect();
	}

	// method for check the DB
	private void dbconnect() {
		String dbAnswer = SQLiteJDBCDriverConnection.connect();
		if (dbAnswer != "OK") {
			JOptionPane.showMessageDialog(frame, "There are an error" + "/n" + dbAnswer);
		}
	}

	// Initialize the contents of the frame.
	private void initialize() {
		frame = new JFrame("Mini Bank System");
		frame.setAlwaysOnTop(true);

		frame.getContentPane().setEnabled(false);
		frame.setBounds(100, 100, 522, 346);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		JLabel lblAccountSearch = new JLabel("Enter Account");
		lblAccountSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAccountSearch.setBounds(10, 12, 124, 25);
		frame.getContentPane().add(lblAccountSearch);
		lblAccountSearch.setToolTipText("Enter Account");

		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSearch.setBounds(150, 12, 120, 25);
		frame.getContentPane().add(txtSearch);
		txtSearch.setToolTipText("Enter Account");
		txtSearch.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		//button event
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validation.IsInteger(txtSearch.getText())) {
					if (getData()) {
						validation.EnableDisable(false, true);
					} else {
						JOptionPane.showMessageDialog(btnSearch, "The account not found !!!");
					}
				} else {
					JOptionPane.showMessageDialog(btnSearch, "The search value must be integer !!!");
				}
			}
		});
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setBackground(new Color(0, 0, 102));
		btnSearch.setBounds(150, 41, 120, 25);
		frame.getContentPane().add(btnSearch);
		btnSearch.setToolTipText("Search");

		JLabel lblAcount = new JLabel("The Account is :");
		lblAcount.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAcount.setBounds(10, 80, 124, 25);
		frame.getContentPane().add(lblAcount);
		lblAcount.setToolTipText("");

		txtAccount = new JTextField();
		txtAccount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtAccount.setBounds(150, 79, 120, 25);
		frame.getContentPane().add(txtAccount);
		txtAccount.setToolTipText("Account");
		txtAccount.setColumns(10);

		JLabel lblName = new JLabel("Customer Name is :");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblName.setBounds(10, 116, 171, 25);
		frame.getContentPane().add(lblName);
		lblName.setToolTipText("Customer Name");

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtName.setBounds(150, 116, 120, 25);
		frame.getContentPane().add(txtName);
		txtName.setToolTipText("Name");
		txtName.setColumns(10);

		JLabel lblAmount = new JLabel("The Amount is :");
		lblAmount.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAmount.setBounds(10, 153, 114, 25);
		frame.getContentPane().add(lblAmount);
		lblAmount.setToolTipText("Amount");

		txtAmount = new JTextField();
		txtAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtAmount.setBounds(150, 153, 120, 25);
		frame.getContentPane().add(txtAmount);
		txtAmount.setToolTipText("Amount");
		txtAmount.setColumns(10);

		JLabel lblEnterAmount = new JLabel("Enter Amount");
		lblEnterAmount.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblEnterAmount);
		lblEnterAmount.setBounds(10, 196, 114, 25);
		lblEnterAmount.setToolTipText("Enter Amount");

		txtEnterAmount = new JTextField();
		txtEnterAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtEnterAmount.setBounds(150, 196, 120, 25);
		frame.getContentPane().add(txtEnterAmount);
		txtEnterAmount.setToolTipText("Enter Amount");
		txtEnterAmount.setColumns(10);

		btnDeposition = new JButton("Deposition");
		btnDeposition.setFont(new Font("Tahoma", Font.BOLD, 14));
		//button event
		btnDeposition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//check the validation
					if (validation.check() && validation.IsDouble(txtEnterAmount.getText())) {
						int result = JOptionPane.showConfirmDialog(btnDeposition, "Are you sure...?");
						if (result == JOptionPane.YES_OPTION) {
							//will go to operation class
							String OpStatus = op.do_operation(2, CI, txtEnterAmount.getText());
							if (OpStatus == "Done") {
								//update the OpStatus after update on DB
								OpStatus = SQLiteJDBCDriverConnection.updateByAccountNo(CI);
								if (OpStatus == "Done") {
									//update the fields in the frame
									getData();
									//add the operation in the history
									SQLiteJDBCDriverConnection.AddToHistory(1, "Deposition", txtEnterAmount.getText());
									txtEnterAmount.setText("");
									JOptionPane.showMessageDialog(btnDeposition, "Operation Successfully");
								} else
									JOptionPane.showMessageDialog(btnDeposition, OpStatus);
							} else
								JOptionPane.showMessageDialog(btnDeposition, OpStatus);
						} else {
							txtEnterAmount.setText("");
						}
					} else {
						JOptionPane.showMessageDialog(btnDeposition,
								"Must be search an account or it must be double value...!");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(btnDeposition, "Error: " + ex.getMessage());
				}
			}
		});
		btnDeposition.setForeground(new Color(255, 255, 204));
		btnDeposition.setBackground(new Color(0, 102, 51));
		btnDeposition.setBounds(150, 233, 120, 25);
		frame.getContentPane().add(btnDeposition);
		btnDeposition.setToolTipText("Deposition");

		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setFont(new Font("Tahoma", Font.BOLD, 14));
		//button event
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//check the validation
					if (validation.check() && validation.IsDouble(txtEnterAmount.getText())) {
						int result = JOptionPane.showConfirmDialog(btnWithdraw, "Are you sure...?");
						if (result == JOptionPane.YES_OPTION) {
							//will go to operation class
							String OpStatus = op.do_operation(1, CI, txtEnterAmount.getText());
							if (OpStatus == "Done") {
								//update the OpStatus after update on DB
								OpStatus = SQLiteJDBCDriverConnection.updateByAccountNo(CI);
								if (OpStatus == "Done") {
									//update the fields in the frame
									getData();
									//add the operation in the history
									SQLiteJDBCDriverConnection.AddToHistory(1, "Withdraw", txtEnterAmount.getText());
									txtEnterAmount.setText("");
									JOptionPane.showMessageDialog(btnWithdraw, "Operation Successfully");
								} else
									JOptionPane.showMessageDialog(btnWithdraw, OpStatus);
							} else
								JOptionPane.showMessageDialog(btnWithdraw, OpStatus);
						} else {
							txtEnterAmount.setText("");
						}
					} else {
						JOptionPane.showMessageDialog(btnWithdraw,
								"Must be search an account or it must be double value");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(btnWithdraw, "Error: " + ex.getMessage());
				}
			}
		});
		btnWithdraw.setBackground(new Color(153, 0, 0));
		btnWithdraw.setForeground(new Color(255, 255, 153));
		btnWithdraw.setBounds(150, 271, 120, 25);
		frame.getContentPane().add(btnWithdraw);
		btnWithdraw.setToolTipText("Withdraw");

		//method in validation class for Enable or disable the tools
		validation.EnableDisable(false, false);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 71, 260, 15);
		frame.getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 190, 260, 8);
		frame.getContentPane().add(separator_1);
		
		JTextPane txtpnNoteCan = new JTextPane();
		txtpnNoteCan.setEditable(false);
		txtpnNoteCan.setBackground(new Color(238,238,238,238));
		txtpnNoteCan.setText("Note: can use the software by accounts as example (1234, 1235), with my greeting Nazar Ahmed");
		txtpnNoteCan.setBounds(282, 12, 224, 61);
		frame.getContentPane().add(txtpnNoteCan);
	}

	//method for get data from Database and put it in the CI object
	public boolean getData() {
		try {
			//search in the DB by account number and get the data as CI object
			CI = SQLiteJDBCDriverConnection.searchByAccountNo(txtSearch.getText());
			txtAccount.setText(Integer.toString(CI.get_accountNo()));
			txtName.setText(CI.get_fullName());
			txtAmount.setText(Double.toString(CI.get_amount()));
			return true;
		} catch (Exception ex) {
			// JOptionPane.showMessageDialog(btnSearch, "Error : " + ex.getMessage());
			return false;
		}

	}
}

//validation class
class validation extends BankSysMain {
	
	//method work after the search
	static boolean check() {
		if (CI != null) {
			if (CI.get_fullName() != "" || CI.get_fullName() != null)
				return true;
		}
		return false;
	}

	//method for enable and disable the tools 
	static void EnableDisable(boolean flag1, boolean flag2) {
		txtName.setEditable(flag1);
		txtAccount.setEditable(flag1);
		txtAmount.setEditable(flag1);

		txtEnterAmount.setEditable(flag2);
		btnDeposition.setEnabled(flag2);
		btnWithdraw.setEnabled(flag2);

		// txtSearch.setEnabled(!flag);
		// btnSearch.setEnabled(!flag);

	}

	//method for check integer value
	static boolean IsInteger(String value) {
		try {
			Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	//method for check double value
	static boolean IsDouble(String value) {
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}
}