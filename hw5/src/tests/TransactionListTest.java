package tests;

import static org.junit.Assert.*;
import model.BankAccount;
import model.RegularAccount;
import model.Transaction;
import model.TransactionList;
import model.TransactionType;

import org.junit.Test;

/*
 * It has tests for TransactionList class.
 * 
 * @author Vikrant Singhal
 */
public class TransactionListTest {

	@Test
	public void testEmptyTransactionList() {
		TransactionList all = new TransactionList();
		String str = all.getMostRecent("NotHere", 5);
		assertEquals("Transactions for NotHere\n\n", str.toString());
	}

	@Test
	public void testAddAndFindAccountWithIDWhenNotThrere() {
		TransactionList all = new TransactionList();
		BankAccount d = new RegularAccount("Dakota", 100.00);
		Transaction tran1 = new Transaction(d, 1.23, TransactionType.Withdraw);
		Transaction tran2 = new Transaction(d, 2.23, TransactionType.Deposit);
		Transaction tran3 = new Transaction(d, 3.23, TransactionType.Withdraw);
		Transaction tran4 = new Transaction(d, 4.23, TransactionType.Deposit);
		all.addTransaction(tran1);
		all.addTransaction(tran2);
		all.addTransaction(tran3);
		all.addTransaction(tran4);

		String str = all.getMostRecent("NotHere", 5);
		assertEquals("Transactions for NotHere\n\n", str.toString());

		str = all.getMostRecent("Dakota", 5);
		assertTrue(str.length() > 120);

		assertTrue(str.contains("1.23 "));
		assertTrue(str.contains("2.23 "));
		assertTrue(str.contains("3.23 "));
		assertTrue(str.contains("4.23 "));
		assertTrue(str.contains(" W "));
		assertTrue(str.contains(" D "));

	}

}