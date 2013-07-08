package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.BankAccount;
import model.BankAccountCollection;
import model.SafeAccount;
import model.Transaction;
import model.TransactionList;
import model.TransactionType;

/*
 * This class is provides the UI for the bank.
 * 
 * @author Vikrant Singhal
 */
public class BankTeller extends JFrame {
	public static void main(String[] args) {
		// Give control to a BankTeller object that allows withdrawals to one
		// account
		BankTeller tellerWindow = new BankTeller();
		tellerWindow.setVisible(true);
	}

	// Class constants (might want to change MAX_TRANSACTIONS, so make it
	// public.
	public static final int MAX_TRANSACTIONS = 10;
	private static final String DEFAULT_BALANCE = "???.??";
	private static final String DEFAULT_LOAN = "";

	private JTextField newAccountField;
	private JLabel balanceLabel;
	private JLabel loanLabel;
	private JLabel loanCaptionLabel;
	private JTextField withdrawField;
	private JTextField depositField;
	private JButton XPress10Button;

	private NumberFormat currencyFormat;
	private BankAccount currentAccount;
	private TransactionList transactionList;
	private BankAccountCollection theAccounts;

	public static String baseDir = System.getProperty("user.dir")
			+ File.separator + "serializedObjects" + File.separator;

	public static final String fileNameWhereAccountsAreStored = baseDir
			+ "accountCollection.object";

	public static final String fileNameWhereTransactionsAreStored = baseDir
			+ "transactionCollection.object";

	public BankTeller() { // Begin to set up the window to this application
		this.setTitle("Bank Teller");
		this.setSize(300, 160);
		this.setResizable(false);

		// No longer maintains oneAccount. currentAccount can change.
		currentAccount = null;
		// Read the persistent objects from their disk files.
		readObjects();

		// The final phase of BankTeller will have the window to stay
		// visible even after the user attempts to close the window.
		// Instead, a WindowListener object handles the windowClosing event.
		// Therefore, the closing operation is now changed to do nothing.
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// Program now keeps running on windowClosing if the user so chooses.

		// Instead of terminating the program, when the user attempts to
		// close the window, the window remains visible. Instead of program
		// termination, the windowClosing method of the WindowListener will
		// prompt the user to do one of these three things:
		//
		// 0: Save objects and quit.
		// 1: Quit without saving the objects.
		// 2: The application keeps on running.
		//
		// To accomplish this different behavior, a WindowListener object
		// will be registered to wait for the window closing event and
		// handle all three possibilities. Another private inner class
		// is added to this JFrame to handle all windowClosing events.
		WindowClosingListener windowClosingListener = new WindowClosingListener();

		// JFrames generate WindowEvents and send messages to WindowListeners
		this.addWindowListener(windowClosingListener);

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		// Let the human bank teller enter new account IDs
		c.gridx = 0;
		c.gridy = 0;
		contentPane.add(new JLabel("  Account ID "), c);

		c.gridx = 1;
		c.gridy = 0;
		newAccountField = new JTextField();
		newAccountField.setColumns(10);
		contentPane.add(newAccountField, c);

		// Let the human bank teller enter a new withdrawal amount
		c.gridx = 0;
		c.gridy = 1;
		contentPane.add(new JLabel("Withdraw"), c);

		c.gridx = 1;
		c.gridy = 1;
		withdrawField = new JTextField();
		withdrawField.setColumns(10);
		contentPane.add(withdrawField, c);

		// Let the human bank teller enter a new deposit amount
		c.gridx = 0;
		c.gridy = 2;
		contentPane.add(new JLabel("Deposit"), c);

		c.gridx = 1;
		c.gridy = 2;
		depositField = new JTextField();
		depositField.setColumns(10);
		contentPane.add(depositField, c);

		// Let the human bank teller view number of transactions
		c.gridx = 0;
		c.gridy = 3;
		contentPane.add(new JLabel("Transactions: "), c);

		c.gridx = 1;
		c.gridy = 3;
		XPress10Button = new JButton("XPress 10");
		contentPane.add(XPress10Button, c);

		// Let the human bank teller see the balance of an account
		c.gridx = 0;
		c.gridy = 4;
		contentPane.add(new JLabel("Balance:"), c);

		c.gridx = 1;
		c.gridy = 4;
		balanceLabel = new JLabel(DEFAULT_BALANCE);
		contentPane.add(balanceLabel, c);

		// Let the safe account user see the loan amount
		c.gridx = 0;
		c.gridy = 5;
		loanCaptionLabel = new JLabel(" ");
		contentPane.add(loanCaptionLabel, c);

		c.gridx = 1;
		c.gridy = 5;
		loanLabel = new JLabel(DEFAULT_LOAN);
		contentPane.add(loanLabel, c);

		currencyFormat = NumberFormat.getCurrencyInstance(); // An instance
																// variable

		// Register a listener object to the withdraw text field
		WithdrawListener withdrawListener = new WithdrawListener();
		withdrawField.addActionListener(withdrawListener);

		// Code to register a listener to show the transactionList.
		TransactionButtonListener express10Listener = new TransactionButtonListener();
		XPress10Button.addActionListener(express10Listener);

		// Code to connect deposit
		DepositListener depositListener = new DepositListener();
		depositField.addActionListener(depositListener);

		// Code to connect New Account
		ChangeAccountListener newAccountListener = new ChangeAccountListener();
		newAccountField.addActionListener(newAccountListener);
	} // end constructor
	

	/*
	 * Read both collection objects from their disk files.
	 * Both file names are stored as class constants.
	 */
	private void readObjects() {

		int option = JOptionPane.showConfirmDialog(null,
				"Start from existing serialized objects");
		theAccounts = new BankAccountCollection();
		if (option == 0) // User clicked Yes
			try {
				// Read the BankAccountCollection object from it disk file
				FileInputStream inFile = new FileInputStream(
						fileNameWhereAccountsAreStored);
				ObjectInputStream inputStream = new ObjectInputStream(inFile);
				theAccounts = (BankAccountCollection) inputStream.readObject();
				inputStream.close();

				// Use the same stream objects to read the other disk file
				inFile = new FileInputStream(fileNameWhereTransactionsAreStored);
				inputStream = new ObjectInputStream(inFile);
				transactionList = (TransactionList) inputStream.readObject();
				inputStream.close();
			} catch (Exception e) {
				String message = "Error reading serialzed objects\n";
				message += "Run tests.InitializeAccountAndTransactionCollections";
				JOptionPane.showMessageDialog(null, message);
			}

		else { // user click No or Cancel
			theAccounts.setDefaultCollection();
			transactionList = new TransactionList();
		}
	}
	

	/*
	 * This new type of Listener will offer three choices to any user that
	 * decides to close the window while this application is running (see the
	 * comments written in the constructor).
	 */
	private class WindowClosingListener extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			// This is the only method that does something
			// When the user closes the window, save the BankAccountCollection
			int choice = JOptionPane.showConfirmDialog(null,
					"Save data to disk?");
			if (choice == 0) { // Save the current state of the collections and
								// quit.
				saveObjects();
				System.exit(0);
			} else if (choice == 1) { // Quit without saving
				System.exit(0);
			} else if (choice == 2) { // Let the user continue to use the
										// program
				JOptionPane.showMessageDialog(null, "Carry on...");
			}
		}
	}// end class WindowClosingListener
	

	/*
	 * Save both collection objects to disk. The old files will be replaced with
	 * the updated state of the objects.
	 */
	private void saveObjects() {
		try {
			// Write the BankccountCollection
			FileOutputStream outFile = new FileOutputStream(
					fileNameWhereAccountsAreStored);
			ObjectOutputStream outputStream = new ObjectOutputStream(outFile);
			outputStream.writeObject(theAccounts);
			outputStream.close();

			// Save TransactionList in a different file
			outFile = new FileOutputStream(fileNameWhereTransactionsAreStored);
			outputStream = new ObjectOutputStream(outFile);
			outputStream.writeObject(transactionList);
			outputStream.close();
		} catch (IOException ioe) {
			String message = "Error writing objects to disk: " + "\n" + ioe
					+ "\nHope you had data backed up...";
			JOptionPane.showMessageDialog(null, message);
		}
	} // end saveObjects
	

	/*
	 * This actionPerformed method will execute when the user enters a new
	 * Account
	 */
	private class ChangeAccountListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String id = newAccountField.getText();

			if (id.compareTo("") != 0) {
				currentAccount = (BankAccount) theAccounts
						.findAccountWithID(id);
				if (currentAccount == null)
					JOptionPane.showMessageDialog(null, id + " not found");
				else {
					if (currentAccount instanceof SafeAccount) {
						loanCaptionLabel.setText("Loan Amount:");
						loanLabel.setText(currencyFormat
								.format(((SafeAccount) currentAccount)
										.getLoanAmount()));
					} else {
						loanCaptionLabel.setText(DEFAULT_LOAN);
						loanLabel.setText(" ");
					}
					balanceLabel.setText(currencyFormat.format(currentAccount
							.getBalance()));
				}
			}
		}
	} // end class ChangeAccountListener
	

	/*
	 * This actionPerformed method will execute when the user enters a deposit
	 * amount
	 */
	private class DepositListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// First check to make sure there is a valid currentAccount set
			if (currentAccount == null) {
				JOptionPane.showMessageDialog(null, "Need a valid account");
				depositField.setText("");
			} else {
				String numberAsString = depositField.getText();
				double amount = 0.0;

				if (numberAsString.compareTo("") != 0) {
					try {
						amount = Double.parseDouble(numberAsString);
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null,
								"Not a valid deposit amount" + numberAsString);
						return;
					}

					// Must have at least found a valid number, but it's not
					// positive
					if (amount <= 0) {
						JOptionPane.showMessageDialog(null,
								"Deposit amount must be > 0.00");
						return;
					}

					// Adjust the account
					currentAccount.deposit(amount);

					// Record the transaction (assume the teller gave money to
					// the customer
					transactionList.addTransaction(new Transaction(
							currentAccount, amount, TransactionType.Deposit));

					// Always clear the field, even if amount requested was too
					// much
					depositField.setText("");
					balanceLabel.setText(currencyFormat.format(currentAccount
							.getBalance()));
				}

				if (currentAccount instanceof SafeAccount) {
					loanLabel.setText(currencyFormat
							.format(((SafeAccount) currentAccount)
									.getLoanAmount()));
				}
			}
		}
	}
	

	/*
	 * This actionPerformed method will execute when the user enters a string
	 * into withdrawField.
	 */
	private class WithdrawListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			// First check to make sure there is a valid currentAccount set
			if (currentAccount == null) {
				JOptionPane.showMessageDialog(null, "Need a valid account");
				withdrawField.setText("");
			}

			// Get the use input
			String s = withdrawField.getText();

			// Do nothing if the user presses enter with no text int
			// withdrawField
			if (s.equals(""))
				return;

			// Now try to convert the text into a valid number
			double amount = 0.0;
			try {
				amount = Double.parseDouble(s);
			}
			// Bad number so just return (leave the bad input in place)
			catch (NumberFormatException e) { 
				JOptionPane.showMessageDialog(null, s
						+ " is not a valid number");
				return;
			}
			// Must have at least found a valid number, but it's not positive
			if (amount <= 0) {
				JOptionPane.showMessageDialog(null,
						"Withdrawal amount must be > 0.00");
				return;
			}

			// Now try to withdraw the positive number
			if (currentAccount.withdraw(amount)) {
				// Record the transaction (assume the teller gave money to the
				// customer
				transactionList.addTransaction(new Transaction(currentAccount,
						amount, TransactionType.Withdraw));

				// Clear the field
				withdrawField.setText("");
				String balanceAsString = currencyFormat.format(currentAccount
						.getBalance());
				balanceLabel.setText(balanceAsString);
			} else
				JOptionPane.showMessageDialog(null,
						"Amount requested exceeds account balance");

			if (currentAccount instanceof SafeAccount) {
				loanLabel
						.setText(currencyFormat
								.format(((SafeAccount) currentAccount)
										.getLoanAmount()));
			}

		} // end actionPerformed
	} // end class WithdrawListener

	/*
	 * This actionPerformed method will execute when the XPress10 button is
	 * clicked.
	 */
	private class TransactionButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if (currentAccount == null) {
				JOptionPane.showMessageDialog(null, "Need a valid account");
				return;
			}

			// Show all transactions as they were recorded. First create a big
			// String
			String currentID = currentAccount.getID();
			String message = transactionList.getMostRecent(currentID,
					MAX_TRANSACTIONS);

			// Then show a modal dialog box
			JOptionPane.showMessageDialog(null, message);
		}
	} // end class TransactionButtonListener
} // end class GraphicBankTeller