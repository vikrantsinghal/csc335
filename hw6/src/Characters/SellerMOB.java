/**
 * Exchanges items with the player in a formalized
 * way via <i>requests</i>.
 *
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */


package Characters;

import Items.Item;
import Items.ItemInventory;

public class SellerMOB extends Character {

	private boolean isEngaged;

	public SellerMOB(String name) {
		super(name, "merchant");
		inventory = new ItemInventory(); // Need to change this thing later.
		isEngaged = false;
		totalGold = 500;
	}

	public Item tradeItemWithGold(String itemName, int gold) {
		itemName = itemName.toLowerCase();
		Item item = inventory.get(itemName);

		if (item == null || gold < item.getItemValue())
			return null;

		return item;
	}

	// Yet to be completed. Needs thread implementation, which I don't
	// currently remember.
	public void move() {
		if (isEngaged)
			return;
		// else move to random rooms.
	}
}
