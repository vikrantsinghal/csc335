package tests;

/**
 * A unit test for Transaction
 */
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import model.BankAccount;
import model.FeeAccount;
import model.SafeAccount;
import model.Transaction;
import model.TransactionType;

import org.junit.Test;

/*
 * It has tests for Transaction class.
 * 
 * @author Vikrant Singhal
 */
public class TransactionTest {

	@Test
	public void testTransactionGetIDandToStringSortOf1() {
		BankAccount anAcct = new FeeAccount("Rick", 500.00);
		Transaction tran1 = new Transaction(anAcct, 1.23,
				TransactionType.Withdraw);
		assertEquals("Rick", tran1.getAccountID());
		// Checks the toString has the expected values (the Date, time zone, and
		// currency symbol vary from day to day and country to country
		assertTrue(tran1.toString().contains("Rick"));
		assertTrue(tran1.toString().contains(" W "));
		assertTrue(tran1.toString().contains("1.23"));
		assertTrue(tran1.toString().contains("500.00"));

		Transaction tran2 = new Transaction(anAcct, 1.23,
				TransactionType.Deposit);
		assertEquals("Rick", tran2.getAccountID());
		assertTrue(tran2.toString().contains(" D "));
	}

	@Test
	public void testTransactionGetIDandToStringSortOf2() {
		BankAccount anAcct = new SafeAccount("Rick", 500.00);
		Transaction tran1 = new Transaction(anAcct, 1.23,
				TransactionType.Withdraw);
		assertEquals("Rick", tran1.getAccountID());
		// Checks the toString has the expected values (the Date, time zone, and
		// currency symbol vary from day to day and country to country
		assertTrue(tran1.toString().contains("Rick"));
		assertTrue(tran1.toString().contains(" W "));
		assertTrue(tran1.toString().contains("1.23"));
		assertTrue(tran1.toString().contains("500.00"));
		assertTrue(tran1.toString().contains("Loan Amount:"));
		assertTrue(tran1.toString().contains("0.00"));

		Transaction tran2 = new Transaction(anAcct, 1.23,
				TransactionType.Deposit);
		assertEquals("Rick", tran2.getAccountID());
		assertTrue(tran2.toString().contains(" D "));
	}
}