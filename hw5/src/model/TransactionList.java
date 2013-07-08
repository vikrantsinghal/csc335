package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Maintain a list of transactions for Moose Skinner Falls Bank of Winslow
 * British Columbia
 * 
 * @author Vikrant Singhal
 * 
 */
public class TransactionList implements Serializable {
	private ArrayList<Transaction> theTransactions;

	/**
	 * Construct an empty list of Transactions
	 */
	public TransactionList() {
		theTransactions = new ArrayList<Transaction>();
	}

	/**
	 * Add a transaction to the end of this list
	 * 
	 * @param aTransaction
	 *            the transaction to add
	 */
	public void addTransaction(Transaction aTransaction) {
		theTransactions.add(aTransaction);
	}

	/**
	 * Create a list of from 0 to maxTransactions of the most recent
	 * transactions. If there are no transactions, this method returns an empty
	 * String.
	 * 
	 * @param ID
	 *            The equivalent of the account number for whom we are creating
	 *            the list
	 * @param maxTransactions
	 *            The maximum number of transactions to include
	 * @return transactions as a string with new lines between each transaction
	 */
	public String getMostRecent(String ID, int maxTransactions) {
		// Get the most recent transactions from the end of the list to the
		// beginning
		ArrayList<Transaction> listForGivenID = new ArrayList<Transaction>();

		// Get up to the first maxTransactions transactions belonging to ID
		int numberOfTransactions = 0;
		ListIterator<Transaction> reverseIterator = theTransactions
				.listIterator(theTransactions.size());

		while (reverseIterator.hasPrevious()
				&& numberOfTransactions < maxTransactions) {
			// The first call to previous returns that last element in the list
			Transaction transaction = (Transaction) reverseIterator.previous();
			if (ID.equals(transaction.getAccountID())) {
				listForGivenID.add(transaction);
				numberOfTransactions++;
			}
		}
		return makeANiceList(listForGivenID, ID);
	}

	private String makeANiceList(ArrayList<Transaction> listForGivenID,
			String ID) {
		String result = "Transactions for " + ID + "\n\n";
		int transactionNumber = 1; // Number the transactions
		Iterator<Transaction> itr = listForGivenID.iterator();
		while (itr.hasNext()) {
			Object transaction = itr.next();
			result = result + transactionNumber + ") " + transaction.toString()
					+ "\n";
			transactionNumber++;
		}
		return result;
	}
}