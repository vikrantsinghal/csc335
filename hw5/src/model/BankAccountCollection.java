package model;

/**
 * This class store a collection of accounts at a bank.  It can
 * only store any type that extends BankAccount.
 * 
 * The getID() of a BankAccount is used as the key.
 */
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class BankAccountCollection<E extends BankAccount> implements
		Serializable {

	private HashMap<String, BankAccount> accounts;

	/**
	 * Construct and empty collection of BankAccounts
	 */
	public BankAccountCollection() {
		accounts = new HashMap<String, BankAccount>();
	}

	public void setDefaultCollection() {
		add((BankAccount) new RegularAccount("Dakota", 100.00));
		add((BankAccount) new SafeAccount("Devon", 200.00));
		add((BankAccount) new SafeAccount("Chris", 300.00));
		add((BankAccount) new FeeAccount("Ali", 400.00));
	}

	/**
	 * This returns a reference to the BankAccount in the collection or null if
	 * there was no Account with the given accountID
	 */
	public BankAccount findAccountWithID(String accountID) {
		return accounts.get(accountID);
	}

	/**
	 * Return a textual representation of this collection.
	 */
	public String toString() {
		String result = "";
		Collection<BankAccount> all = accounts.values();
		// Concatenate all elements on separate lines except for the last
		for (BankAccount ba : all) {
			result += ba.toString() + "\n";
		}

		return result;
	}

	/**
	 * Add an account to this collection only if getID() does not already exist
	 * in this collection. Any such getID() will result in a thrown exception
	 * instead of adding accountToAdd.
	 * 
	 * @param accountToAdd
	 *            The account to become part of the bank.
	 */
	public void add(BankAccount accountToAdd) throws IllegalArgumentException {
		if (accounts.containsKey(((model.BankAccount) accountToAdd).getID()))
			throw new IllegalArgumentException(
					accountToAdd
							+ " has a non unique ID.  It ewas not added to thio collection.");
		accounts.put(((model.BankAccount) accountToAdd).getID(), accountToAdd);
	}

	/**
	 * Provide access to the number of accounts in the bank
	 * 
	 * @return The number of accounts at this time.
	 */
	public int size() {
		return accounts.size();
	}

}