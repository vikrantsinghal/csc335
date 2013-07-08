/**
 * This test suite ensures the ItemInventory class is working as expected.
 * This class does not examine
 *
 *
 * @author Kyle Almryde
 *
 */

package Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Items.Armor;
import Items.Inanimate;
import Items.ItemInventory;
import Items.Potion;
import Items.Weapon;


public class ItemInventoryTest {

	@Test
	public void test() {
		Potion p = new Potion("p", "drink", 20, 10);
		assertEquals(20, p.use());
		p.setQuestItem(0);
		p.setQuestItem(0);

		Weapon w = new Weapon("w", 20, 10);
		assertEquals(20, w.use());
		w.setQuestItem(2);
		w.setEquipped();
		assertEquals(40, w.use());
		w.setEquipped();
		w.setQuestItem(1);

	}

    /**
     * This will test whether or not the item Inventory
     * will properly display all the items in it.
     *
     */
    @Test
    public void testListItems() {
        System.out.println("---testListItems---");
        ItemInventory inventory = new ItemInventory();
        inventory.setDefaultInventory();
        System.out.println(inventory.listItemDetails());
        assertEquals("Excalibur", inventory.get("Excalibur").getItemName());
    } // End of testListItems


    /**
     * Make sure the 'give' method is retrieving the item object
     * from the inventory and removing it.
     */
    @Test
    public void testgive() {
        System.out.println("---testgive---");
        ItemInventory ii = new ItemInventory();
        ii.add(new Inanimate("Boobies", 80081322));
        ii.add(new Weapon("Sword", 20, 100));
        ii.add(new Potion("Soda", "Drink", 5, 10));
        assertEquals(3, ii.size());
        assertEquals("Boobies", ii.give("Boobies").getItemName());
        assertEquals(2, ii.size());
        assertNotNull(ii);
        assertNull(ii.give("Boobies"));
    } // End of testgive


    /**
     * Makes sure Item is removed
     *
     *
     */
    @Test
    public void testtrash() {
        System.out.println("---testTrash---");
        ItemInventory ii = new ItemInventory();
        ii.add(new Inanimate("Boobies", 80081322));
        ii.add(new Weapon("Sword", 20, 100));
        ii.add(new Potion("Soda", "Drink", 5, 10));
        System.out.println(ii.listItemDetails());
        assertTrue(ii.trash("SwoRd"));
        assertFalse(ii.checkForItem("Sword"));
        System.out.println(ii.listItemDetails());
    } // End of testtrash


    /**
     * Test to ensure that checkForItem is working properly
     *
     *
     */
    @Test
    public void testItemGetters() {
        System.out.println("---testItemGetters---");
        ItemInventory ii = new ItemInventory();
        ii.add(new Inanimate("Boobies", 80081322));
        ii.add(new Weapon("Sword", 20, 100));
        ii.add(new Potion("Soda", "Drink", 5, 10));
        ii.add(new Armor("Breast Plate", 100, 50));

        System.out.println(ii.listItemDetails());

        assertTrue(ii.checkForItem("Soda"));
        assertFalse(ii.checkForItem("Water"));

    } // End of testItemGetters


    /**
     *  Description
     *
     *
     */
   @Test
   public void test_add_random_Items() {
        System.out.println("---test_add_random_Items---");
        ItemInventory inventory = new ItemInventory();
        inventory.add_random_item(6);
        System.out.println(inventory.listItemDetails());
        System.out.println(inventory.displayItems());

   } // End of test_add_lowLevel_Weapons
}