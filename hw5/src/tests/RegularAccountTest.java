package tests;

import static org.junit.Assert.*;

import model.BankAccount;
import model.RegularAccount;

import org.junit.Test;

/*
 * It has tests for RegularAccount class.
 * 
 * @author Vikrant Singhal
 */
public class RegularAccountTest {

	@Test
	  public void testGetters() {
	    BankAccount anAcct = new RegularAccount("Zac", 1000.00);
	    assertEquals(1000.00, anAcct.getBalance(), 1e-12);
	    BankAccount anotherAcct = new RegularAccount("Rick", 500.00);
	    assertEquals(500.00, anotherAcct.getBalance(), 1e-12);
	  }

	  @Test
	  public void testGetID() {
	    BankAccount anAcct = new RegularAccount("Zac", 1000.00);
	    assertEquals("Zac", anAcct.getID());
	    BankAccount anotherAcct = new RegularAccount("Rick", 500.00);
	    assertEquals("Rick", anotherAcct.getID());
	    assertFalse(anAcct.withdraw(0.00));
	  }

	  @Test
	  public void testDeposit() {
	    BankAccount anAcct = new RegularAccount("Zac", 1000.00);
	    assertEquals(1000.00, anAcct.getBalance(), 1e-12);
	    anAcct.deposit(0.52);
	    assertEquals(1000.52, anAcct.getBalance(), 1e-12);
	  }

	  @Test
	  public void testWithdrawWhenThereIsEnough() {
	    BankAccount anAcct = new RegularAccount("Chris", 100.00);
	    assertTrue(anAcct.withdraw(70.00));
	    assertEquals(30.00, anAcct.getBalance(), 1e-12);
	  }

	  @Test
	  public void testWithdrawWithNegativeAmount() {
	    BankAccount anAcct = new RegularAccount("Chris", 100.00);
	    assertFalse(anAcct.withdraw(-70.00));
	    assertEquals(100.00, anAcct.getBalance(), 1e-12);
	  }

	  @Test
	  public void testWithdrawWhenBalanceIsLow() {
	    BankAccount anAcct = new RegularAccount("Chris", 99.99);
	    assertFalse(anAcct.withdraw(100.00));
	    assertEquals(99.99, anAcct.getBalance(), 1e-12);
	  }

	  @Test
	  public void testToString() {
	    BankAccount a = new RegularAccount("Alice", 543.1);
	    assertTrue(a.toString().contains("Alice"));
	    assertTrue(a.toString().contains("543.10"));
	  }

	  @Test
	  public void testCompareTo() {
	    BankAccount a = new RegularAccount("Alice", 543.21);
	    BankAccount z = new RegularAccount("Zac", 123.45);

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
	    BankAccount a = new RegularAccount("Alice", 543.21);
	    BankAccount z = new RegularAccount("Zac", 123.45);
	    BankAccount anotherA = new RegularAccount("Alice", 123.45);
	    assertTrue(a.equals(a));
	    assertTrue(a.equals(anotherA));
	    assertTrue(anotherA.equals(a));
	    assertTrue(z.equals(z));
	    assertFalse(a.equals(z));
	    assertFalse(z.equals(a));
	    assertFalse(a.equals(543.21));
	  }
	  
	  @Test
	  public void negativeAmountDepositTest() {
		  BankAccount a = new RegularAccount("Alice", 543.21);
		  a.deposit(-1.00);
		  assertEquals(543.21, a.getBalance(), 0.000000001);
		  System.out.println(a.toString());
	  }

}
