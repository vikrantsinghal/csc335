package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Characters.Character;
import Characters.Warrior;
import Model.Account;
import Model.AccountCollection;


public class AccountTest {

	@Test
	public void test() {
		Account a = new Account("Raul", "222");
		assertEquals("Raul", a.getUserName());
		assertEquals("222", a.getPw());
		Warrior c = (Warrior) a.getCh();
		c.addGold(10);
	}

	@Test
	public void accountCollectionTest() {
		AccountCollection acctColl = new AccountCollection();
		Account a = acctColl.getAcct("Josh");
		Account b = acctColl.getAcct("Kyle");
		Character c = acctColl.getCharacter("Josh");
		c.getInventory();
		c.toString();
		assertFalse(acctColl.validUser("J", "1"));
		acctColl.add("J", "1");
		assertFalse(acctColl.validUser("J", "12"));
		assertTrue(acctColl.validUser("J", "1"));
		
	}

}
