/**
 * Holds and manages the <code>Account</code>s. It is
 * serializable so it can save user account information
 * across sessions.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Model;

import java.io.Serializable;
import java.util.HashMap;

import Characters.Character;

@SuppressWarnings("serial")
public class AccountCollection implements Serializable {

	private HashMap<String, Account> accountCollection;

	public AccountCollection() {
		accountCollection = new HashMap<String, Account>();
		accountCollection.put("Josh", new Account("Josh", "1111"));
		accountCollection.put("Jimmy", new Account("Jimmy", "2222"));
		accountCollection.put("Kyle", new Account("Kyle", "3333"));
		accountCollection.put("Vikrant", new Account("Vikrant", "4444"));
		accountCollection.put("admin", new Account("admin", "admin"));
		this.getAcct("Josh").getCh().getInventory().setDefaultInventory();
		this.getAcct("Jimmy").getCh().getInventory().setDefaultInventory();
		this.getAcct("Kyle").getCh().getInventory().setDefaultInventory();
		this.getAcct("Vikrant").getCh().getInventory().setDefaultInventory();
		this.getAcct("admin").getCh().getInventory().setDefaultInventory();
	}

	public void add(String text, String pw) {
		accountCollection.put(text, new Account(text, pw));
		this.getAcct(text).getCh().getInventory().setDefaultInventory();
	}

	public boolean isTakenName(String userName){
		if (accountCollection.containsKey(userName))
			return true;
		else return false;

	}

	public boolean validUser(String text, String pw) {
		if (accountCollection.containsKey(text))
			if (accountCollection.get(text).getPw().equals(pw))
				return true;
			else
				return false;
		return false;
	}

	public Character getCharacter(String text) {
		return accountCollection.get(text).getCh();
	}

	public Account getAcct(String text) {
		return accountCollection.get(text);
	}

}
