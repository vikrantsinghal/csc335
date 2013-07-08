package tests;

import static org.junit.Assert.*;

import model.BankAccount;
import model.FeeAccount;
import model.SafeAccount;

import org.junit.Test;

/*
 * It has tests for SafeAccount class.
 * 
 * @author Vikrant Singhal
 */
public class SafeAccountTest {

	/*
	 * Testing the constructor.
	 */
	@Test
	public void safeAccountConstructorTest() {
		BankAccount a = new SafeAccount("Gregory", 500.00);
		assertEquals("Gregory", a.getID());
		assertEquals(500.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
	}

	/*
	 * Just a random test to give a start.
	 */
	@Test
	public void depositAmountWhenNoLoanTest() {
		BankAccount a = new SafeAccount("Gregory", 500.00);
		a.deposit(200.00);
		assertEquals(700.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
		a.deposit(0.00);
		assertEquals(700.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
		a.deposit(-100.00);
		assertEquals(700.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
	}

	/*
	 * Withdrawing in such a way the loan amount remains 0 and the balance is
	 * greater than or equal to 0. Depositing money when balance is greater than
	 * or equal to 0.
	 */
	@Test
	public void depositAndWithdrawTest1() {
		BankAccount a = new SafeAccount("Gregory", 500.00);
		a.deposit(200.00);
		assertEquals(700.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		assertTrue(a.withdraw(200.00));
		assertEquals(500.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		assertTrue(a.withdraw(500.00));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		a.deposit(100.00);
		assertEquals(100.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
	}

	/*
	 * Withdrawing 0 and negative amounts of money.
	 */
	@Test
	public void depositAndWithdrawTest2() {
		BankAccount a = new SafeAccount("Gregory", 500.00);
		assertTrue(a.withdraw(200.00));
		assertEquals(300.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		assertFalse(a.withdraw(0.00));
		assertEquals(300.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		assertFalse(a.withdraw(-100.00));
		assertEquals(300.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
	}

	/*
	 * When balance is greater than 0, trying to withdraw money such that
	 * balance becomes 0 and the loan amount lies between 0 and 1000 excluding
	 * both limits. Depositing when balance is zero and there is non-zero loan
	 * such that in one deposit, loan would be repaid and the balance will be
	 * greater than or equal to 0.
	 */
	@Test
	public void depositAndWithdrawTest3() {
		BankAccount a = new SafeAccount("Gregory", 500.00);
		assertTrue(a.withdraw(600.00));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(100.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		a.deposit(200.00);
		assertEquals(100.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		assertTrue(a.withdraw(200.00));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(100.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		a.deposit(100.00);
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
	}

	/*
	 * Withdrawing all possible money such that balance becomes 0 and loan
	 * becomes 1000, which is the limit. Depositing in a way such that balance
	 * is still 0 but loan amount decreases but is still between 0 and 1000
	 * excluding the limits.
	 */
	@Test
	public void depositAndWithdrawTest4() {
		BankAccount a = new SafeAccount("Gregory", 500.00);
		assertTrue(a.withdraw(1500.00));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(1000.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		a.deposit(200.00);
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(800.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
	}

	/*
	 * When balance is greater than 0, trying to withdraw in a way that would
	 * make the balance 0 and also would exceed the loan limit.
	 */
	@Test
	public void depositAndWithdrawTest5() {
		BankAccount a = new SafeAccount("Gregory", 500.00);
		assertFalse(a.withdraw(1501.00));
		assertEquals(500.00, a.getBalance(), 0.00000001);
		assertEquals(0.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
	}

	/**
	 * Loan amount less than 1000 but greater than 0. Trying to withdraw in a
	 * way that would exceed the loan limit as well.
	 */
	@Test
	public void depositAndWithdrawTest6() {
		BankAccount a = new SafeAccount("Gregory", 500.00);
		assertTrue(a.withdraw(600.00));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(100.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		assertFalse(a.withdraw(901));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(100.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
	}

	/**
	 * Loan amount less than 1000 but greater than 0. Making loan amount equal
	 * to 1000.
	 */
	@Test
	public void depositAndWithdrawTest7() {
		BankAccount a = new SafeAccount("Gregory", 500.00);
		assertTrue(a.withdraw(600.00));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(100.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		assertTrue(a.withdraw(900));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(1000.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
	}

	/*
	 * Loan amount less than 1000 but greater than 0. Still withdrawing in a way
	 * the keeps the loan amount between 0 and 1000 excluding both limits.
	 */
	@Test
	public void depositAndWithdrawTest8() {
		BankAccount a = new SafeAccount("Gregory", 500.00);
		assertTrue(a.withdraw(600.00));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(100.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		assertTrue(a.withdraw(800));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(900.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
	}

	/*
	 * Loan amount less than 1000 but greater than 0. Making loan amount equal
	 * to 1000 and then trying to withdraw more money.
	 */
	@Test
	public void depositAndWithdrawTest9() {
		BankAccount a = new SafeAccount("Gregory", 500.00);
		assertTrue(a.withdraw(600.00));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(100.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		assertTrue(a.withdraw(900));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(1000.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);

		assertFalse(a.withdraw(1.00));
		assertEquals(0.00, a.getBalance(), 0.00000001);
		assertEquals(1000.00, ((SafeAccount) a).getLoanAmount(), 0.00000001);
	}

	@Test
	public void toStringTest() {
		BankAccount a = new SafeAccount("Gregory", 500.00);
		System.out.println(a.toString());
	}

	@Test
	public void testCompareTo() {
		BankAccount a = new SafeAccount("Alice", 543.21);
		BankAccount z = new SafeAccount("Zac", 123.45);

		assertTrue(a.compareTo(a) == 0);
		assertTrue(z.compareTo(z) == 0);
		assertTrue(a.compareTo(z) < 0);
		assertTrue(z.compareTo(a) > 0);
		assertTrue(a.compareTo(z) <= 0);
		assertTrue(z.compareTo(a) >= 0);
		assertTrue(z.compareTo(a) != 0);
		assertTrue(a.compareTo(z) != 0);
	}

	@Test
	public void testEquals() {
		BankAccount a = new SafeAccount("Alice", 543.21);
		BankAccount z = new SafeAccount("Zac", 123.45);
		BankAccount anotherA = new SafeAccount("Alice", 123.45);
		assertTrue(a.equals(a));
		assertTrue(a.equals(anotherA));
		assertTrue(anotherA.equals(a));
		assertTrue(z.equals(z));
		assertFalse(a.equals(z));
		assertFalse(z.equals(a));
		assertFalse(a.equals(543.21));
	}

}
