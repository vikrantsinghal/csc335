package model;

/**
 * The type of transactions that can be stored at this bank.
 * 
 * @author Vikrant Singhal
 */
public enum TransactionType {

	// A pretty simple bank with only two types of Transactions

	Withdraw, Deposit;

	/**
	 * Provide a textual representation of a TransactionType.
	 */
	public String toString() {
		if (this == Withdraw)
			return "W";
		else
			return "D";
	}
}
