package model;

/** 
 * A Transaction class to allow a list of transactions. It's main
 * purpose is to store information about single transactions in such
 * a way that a  list of these can be maintained. Each transaction
 * would need to be  verified independently within the business day. 
 * 
 * @author Vikrant Singhal
 */
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.GregorianCalendar;

public class Transaction implements Serializable {

	private String accountID;
	private boolean isSafeAccount;
	private double loan;
	private double transactionAmount;
	private TransactionType transactionCode;
	private double balanceAfterTransaction;
	private GregorianCalendar transactionDate;
	private static NumberFormat formatter = NumberFormat.getCurrencyInstance();

	/**
	 * Construct a Transaction object for various types of transactions
	 * 
	 * @param theAccount
	 *            Who this transaction belongs to
	 * @param amountOfTransaction
	 *            The positive currency amount of the transaction
	 * @param code
	 * 
	 */
	public Transaction(BankAccount theAccount, double amountOfTransaction,
			TransactionType code) {
		loan = 0.0;
		isSafeAccount = false;
		if (theAccount instanceof SafeAccount) {
			loan = ((SafeAccount) theAccount).getLoanAmount();
			isSafeAccount = true;
		}
		accountID = theAccount.getID();
		balanceAfterTransaction = theAccount.getBalance();
		transactionAmount = amountOfTransaction;
		transactionCode = code;
		transactionDate = new GregorianCalendar(); // The current date
	}

	/**
	 * Return the account ID of this account. This is not a transaction number.
	 */
	public String getAccountID() {
		return accountID;
	}

	/**
	 * Return a textual representation of this transaction
	 */
	public String toString() {
		String temp = "";
		if (isSafeAccount) {
			temp += " Loan Amount: " + formatter.format(loan);
		}
		String dateAsString = transactionDate.getTime().toString();
		return dateAsString + " " + transactionCode.toString() + " "
				+ formatter.format(transactionAmount) + " " + accountID + " "
				+ formatter.format(balanceAfterTransaction) + temp;
	}

}