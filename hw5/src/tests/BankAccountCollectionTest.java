package tests;

import static org.junit.Assert.*;
import model.BankAccount;
import model.BankAccountCollection;
import model.FeeAccount;
import model.RegularAccount;
import model.SafeAccount;

import org.junit.Test;

/*
 * It is the test class for the BankAccountCollection class.
 * 
 * @author Vikrant Singhal
 */

public class BankAccountCollectionTest {

	@Test
	public void testAddAndSize() {
		BankAccountCollection accounts = new BankAccountCollection();
		assertEquals(0, accounts.size());
		accounts.add(new RegularAccount("Dakota", 100.00));
		assertEquals(1, accounts.size());
		accounts.add(new SafeAccount("Devon", 200.00));
		assertEquals(2, accounts.size());
		accounts.add(new SafeAccount("Chris", 300.00));
		assertEquals(3, accounts.size());
		accounts.add(new FeeAccount("Ali", 400.00));
		assertEquals(4, accounts.size());
	}

	@Test
	public void testAddAndFindAccountWithID() {
		BankAccountCollection accounts = new BankAccountCollection();
		accounts.add(new RegularAccount("Dakota", 100.00));
		accounts.add(new SafeAccount("Devon", 200.00));
		accounts.add(new SafeAccount("Chris", 300.00));
		accounts.add(new FeeAccount("Ali", 400.00));

		BankAccount currentAccount = (BankAccount) accounts
				.findAccountWithID("Dakota");
		assertEquals("Dakota", currentAccount.getID());
		assertEquals(100.0, currentAccount.getBalance(), 1e-12);

		currentAccount = (BankAccount) accounts.findAccountWithID("Ali");
		assertEquals("Ali", currentAccount.getID());
		assertEquals(400.0, currentAccount.getBalance(), 1e-12);

		currentAccount = (BankAccount) accounts.findAccountWithID("Devon");
		assertEquals("Devon", currentAccount.getID());
		assertEquals(200.0, currentAccount.getBalance(), 1e-12);

		currentAccount = (BankAccount) accounts.findAccountWithID("Chris");
		assertEquals("Chris", currentAccount.getID());
		assertEquals(300.0, currentAccount.getBalance(), 1e-12);
	}

	@Test
	public void testAddAndFindAccountWithIDWhenNotThrere() {
		BankAccountCollection accounts = new BankAccountCollection();
		accounts.add(new RegularAccount("Dakota", 100.00));
		accounts.add(new SafeAccount("Devon", 200.00));
		accounts.add(new SafeAccount("Chris", 300.00));
		accounts.add(new FeeAccount("Ali", 400.00));

		assertNull(accounts.findAccountWithID("Not Here"));
		// Case sensitive
		assertNull(accounts.findAccountWithID("dakota"));

	}

	@Test
	public void testSetDefaultCollection() {
		BankAccountCollection accounts = new BankAccountCollection();
		accounts.setDefaultCollection();
		assertEquals(4, accounts.size());

		BankAccount currentAccount = (BankAccount) accounts
				.findAccountWithID("Dakota");
		assertEquals("Dakota", currentAccount.getID());
		assertEquals(100.0, currentAccount.getBalance(), 1e-12);

		currentAccount = (BankAccount) accounts.findAccountWithID("Ali");
		assertEquals("Ali", currentAccount.getID());
		assertEquals(400.0, currentAccount.getBalance(), 1e-12);

		currentAccount = (BankAccount) accounts.findAccountWithID("Devon");
		assertEquals("Devon", currentAccount.getID());
		assertEquals(200.0, currentAccount.getBalance(), 1e-12);

		currentAccount = (BankAccount) accounts.findAccountWithID("Chris");
		assertEquals("Chris", currentAccount.getID());
		assertEquals(300.0, currentAccount.getBalance(), 1e-12);
	}

	@Test
	public void testToString() {
		BankAccountCollection all = new BankAccountCollection();
		all.setDefaultCollection();
		String str = all.toString();
		assertTrue(all.toString().contains("Ali"));
		assertTrue(all.toString().contains("Dakota"));
		assertTrue(all.toString().contains("Chris"));
		assertTrue(all.toString().contains("Devon"));
		assertTrue(all.toString().contains("200.00"));
		assertTrue(all.toString().contains("400.00"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testUsingTheSameIdTwiceThrowsAnException() {
		BankAccountCollection all = new BankAccountCollection();
		all.setDefaultCollection();
		all.add(new FeeAccount("Ali", -9999));
	}
}
