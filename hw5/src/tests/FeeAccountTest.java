package tests;

import static org.junit.Assert.*;

import model.BankAccount;
import model.FeeAccount;
import model.RegularAccount;

import org.junit.Test;

/*
 * It has tests for FeeAccount class.
 * 
 * @author Vikrant Singhal
 */
public class FeeAccountTest {

	@Test
	public void feeAccountConstructorTest() {
		BankAccount a = new FeeAccount("Gregory", 500.00);
		assertEquals("Gregory", a.getID());
		assertEquals(500.00, a.getBalance(), 0.000000001);
	}
	
	@Test
	public void depositTest() {
		BankAccount a = new FeeAccount("Gregory", 500.00);
		a.deposit(200.00);
		assertEquals(700.00, a.getBalance(), 0.000000001);
		a.deposit(-100.00);
		assertEquals(700.00, a.getBalance(), 0.000000001);
		a.deposit(0.00);
		assertEquals(700.00, a.getBalance(), 0.000000001);
	}
	
	@Test
	public void withdrawTest1() {
		BankAccount a = new FeeAccount("Gregory", 500.00);
		assertFalse(a.withdraw(-100.00));
		assertEquals(500.00, a.getBalance(), 0.000000001);
		assertFalse(a.withdraw(0.00));
		assertEquals(500.00, a.getBalance(), 0.000000001);
		assertTrue(a.withdraw(98.00));
		assertEquals(400.00, a.getBalance(), 0.000000001);
		assertTrue(a.withdraw(398.00));
		assertEquals(0.00, a.getBalance(), 0.000000001);
	}
	
	@Test
	public void withdrawTest2() {
		BankAccount a = new FeeAccount("Gregory", 500.00);
		assertTrue(a.withdraw(98.00));
		assertEquals(400.00, a.getBalance(), 0.000000001);
		assertFalse(a.withdraw(399.00));
		assertEquals(400.00, a.getBalance(), 0.000000001);
		assertFalse(a.withdraw(400.00));
		assertEquals(400.00, a.getBalance(), 0.000000001);
		System.out.println(a.toString());
	}
	
	@Test
	  public void testCompareTo() {
	    BankAccount a = new FeeAccount("Alice", 543.21);
	    BankAccount z = new FeeAccount("Zac", 123.45);

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
	    BankAccount a = new FeeAccount("Alice", 543.21);
	    BankAccount z = new FeeAccount("Zac", 123.45);
	    BankAccount anotherA = new FeeAccount("Alice", 123.45);
	    assertTrue(a.equals(a));
	    assertTrue(a.equals(anotherA));
	    assertTrue(anotherA.equals(a));
	    assertTrue(z.equals(z));
	    assertFalse(a.equals(z));
	    assertFalse(z.equals(a));
	    assertFalse(a.equals(543.21));
	  }


}
