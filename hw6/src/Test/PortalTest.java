/**
 * Test the Portal item, make sure its moving folks around as expected
 *
 *
 *
 * @author Kyle Almryde
 *
 */
package Test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Characters.Character;
import Items.Item;
import Items.Portal;
import Model.AccountCollection;

public class PortalTest {
    private AccountCollection acctColl = new AccountCollection();
    private Character character;

    @Test
    public void testCheckArmorQuality() {
        acctColl = new AccountCollection();
        character = acctColl.getCharacter("Kyle");
        Item portal = new Portal("Magic Rock", 10000);
        assertEquals("Kyle", character.getName());
        assertEquals("Warrior", character.getOccupation());
        character.getInventory().add(portal);

        System.out.println(character.getUserStats());
        System.out.println(portal.display());
        System.out.println(character.getRoom());

        character.useItem("Magic Rock");
        System.out.println(character.getUserStats());
        System.out.println(character.getRoom());
    }

}