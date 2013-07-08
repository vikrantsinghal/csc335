package model;

/**
 * This class models a minimal bank account to be used with textbook
 *
 * @author Vikrant Singhal
 */
import java.text.NumberFormat;
import java.lang.Comparable;
import java.io.*; // For serializable

public abstract class BankAccount implements Comparable<BankAccount>,
		Serializable {
	// Data fields that every BankAccount object will maintain
	private String my_ID;
	private double my_balance;

	/**
	 * Initialize private data fields with arguments during construction.
	 * 
	 * @param initID
	 *            A String meant to uniquely identify this BankAccount.
	 * @param initBalance
	 *            This BankAccount's starting balance.
	 */
	public BankAccount(String initID, double initBalance) {
		my_ID = initID;
		my_balance = initBalance;
	}

	/**
	 * Credit this account by depositAmount. Precondition: depositAmount >= 0.0.
	 * If negative, balance is DEBITed.
	 * 
	 * @param depositAmount
	 *            amount of money to credit to this BankAccount.
	 */
	public void deposit(double depositAmount) {
		if (depositAmount < 0)
			return;
		addToBalance(depositAmount);
	}

	/**
	 * Debit this account by withdrawalAmount if it is positive and also no
	 * greater than this account's current balance.
	 * 
	 * @param withdrawalAmount
	 *            The requested amount of money to withdraw
	 * @return true if 0 < withdrawalAmount <= the balance.
	 */
	public abstract boolean withdraw(double withdrawalAmount);

	/**
	 * Access this account's unique identifier.
	 * 
	 * @return The BankAccount key field that identifies this object.
	 */
	public String getID() {
		return my_ID;
	}

	/**
	 * Access this account's current balance.
	 * 
	 * @return the BankAccount's current balance.
	 */
	public double getBalance() {
		return my_balance;
	}

	/**
	 * Return the state of this object as a String. This method will be used by
	 * LinkedList.toString. You is also called in a print like this
	 * System.out.println( anInstanceOfBankAccount );
	 */
	public abstract String toString();

	/**
	 * This method allows for comparison of two BankAccount objects.
	 * 
	 * @param The
	 *            argument which must be an instance of BankAccount
	 * @return a negative int if this object has an ID that lexicographically
	 *         precedes argument, 0 if the IDs are equals and a positive if this
	 *         object would follow argument in the phone book.
	 */
	public int compareTo(BankAccount other) {
		String leftID = this.getID();
		String rightID = other.getID();
		return leftID.compareTo(rightID);
	}

	/**
	 * Override the Object equals method so BankAccount equals compares the
	 * state of two BankAccount objects rather than the references.
	 * 
	 * @return true if one BankAccount is has the same ID as the other.
	 */
	public boolean equals(Object argument) {
		if (argument instanceof BankAccount)
			return (this.compareTo((BankAccount) argument) == 0);
		else
			return false;
	}

	public void addToBalance(double amount) {
		my_balance += amount;
	}

	public void subtractFromBalance(double amount) {
		my_balance = my_balance - amount;
	}

} // end class BankAccount