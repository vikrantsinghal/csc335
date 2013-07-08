package tests;

import static org.junit.Assert.*;
import model.TransactionType;

import org.junit.Test;

/*
 * It has tests for TransactionType enum.
 * 
 * @author Vikrant Singhal
 */
public class TransactionTypeTest {

	@Test
	public void test() {
		TransactionType d = TransactionType.Deposit;
		assertEquals("D", d.toString());
		TransactionType w = TransactionType.Withdraw;
		assertEquals("W", w.toString());

	}
}
