/**
 * This is a collection class which handels the containment and management
 * of Item based objects.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class ItemInventory implements Serializable {

	private ArrayList<Item> inventory;

	public ItemInventory() {
		inventory = new ArrayList<Item>();
	}

	public void setDefaultInventory() {
		inventory.add(new Weapon("Excalibur", 50, 1000));
		inventory
				.add(new Potion("Potion of Mighty Recovery", "Health", 25, 100));
		inventory.add(new Inanimate("Letter of Peace and Suffering", -10));
		inventory.add(new Portal("Magic Rock", 1000));
		Item i = this.get("Letter of Peace and Suffering");
		i.setQuestItem(0);
		i = this.get("excalibur");
		i.setQuestItem(0);
	}


    /**
     *  This method offers a fast way to populate a room with random items,
     *  which are not necessarily loot worthy.
     *
     * @param int level --
     *                  The desired level of items in the room
     *
     *        int nWeapons --
     *                  The desired number of weapons in the room
     *
     *        int nPotions --
     *                  The desired number of Potions in the room
     *
     *        int nItems --
     *                  The desired number of Inanimates in the room
     * @return
     */
	public void setDefaultRoomInventory(int level, int nWeapons, int nPotions,
			int nItems) {

		if (level >= 1 || level <= 5) {
		    add_random_item(nWeapons);

		} else if (level >= 6 || level <= 10) {
			// inventory.add(low_Medium_level());

		} else if (level >= 11 || level <= 15) {
			// inventory.add(mediumlevel());

		} else if (level >= 16 || level <= 20) {
			// inventory.add(medium_High_level());

		} else if (level >= 21 || level <= 25) {
			// inventory.add(highlevel());

		} else if (level >= 26 || level <= 30) {
			// inventory.add(chuckNorris_level());
		}
	} // End of setDefaultRoomInventory

	public boolean hasPotion(){
		for (Item item : inventory){
			if (item instanceof Potion){
				return true;
			}
		}
		return false;
	}

	public String getFirstPotionName(){
		for (Item item : inventory){
			if (item instanceof Potion){
				return item.getItemName();
			}
		}
		return null;
	}

	/**
	 * This private method handles actually building the objects in question It
	 * utilizes a random number generator which accesses a supplied list
	 * randomly selecting an item name (with replacement), then generating stats
	 * provided by the user.
	 *
	 *
	 * @return
	 */
	private void itemFeatureSelection(int nItems, int range) {
		String name;
        int pool = 0;
        int value = 0;
        int basal = range - 5;
        String itemType = "";
        String[] itemList = {"Weapon", "Armor", "Item", "Portal", "Potion"};
        String[] descriptor = {"Sexy ", "Long ", "Dirty ", "Voluptuous ", "Rusty ",
                               "Broken ", "Glowing ", "Smelly ", "Wailing ",
                               "Magnificent ", "Rubber ", "Old ", "Weak ",
                               "Superior ", "Violent ", "Stupid ", "Blue-footed "};

        String[] objectList = {"Leggings", "Shield", "Blade", "Axe", "Boobies",
                            "Lingerie", "Stone", "Ring", "Shoe", "Mirror",
                            "Glock", "Dildo", "Cock Ring", "George Bush", "Soda Can",
                            "Monkey butt"};

		Random randomGenerator = new Random();

		for (int i = 0; i < nItems; i++) {
			name = descriptor[randomGenerator.nextInt(descriptor.length)];
            name += objectList[randomGenerator.nextInt(objectList.length)];
			value = randomGenerator.nextInt(range);
			itemType = itemList[randomGenerator.nextInt(itemList.length)];

			while (pool < basal) {
				pool = randomGenerator.nextInt(range);
			}

			if (itemType.equals("Weapon")) {
				inventory.add(new Weapon(name, pool, value));
			} else if (itemType.equals("Potion")) {
				inventory.add(new Potion(name, "Health", pool, value));
			} else if (itemType.equals("Item")) {
				inventory.add(new Inanimate(name, value));
            } else if (itemType.equals("Armor")) {
                inventory.add(new Armor(name, pool, value));
            } else if (itemType.equals("Portal")) {
                inventory.add(new Portal(name, value));
			}
		}
	} // End of itemFeatureSelection

    public void add_random_item(int nItems) {
        itemFeatureSelection(nItems, 150);
    }


    /**
     * Return the entire inventory so we can do stuff to it as needed
     *
     *
     * @return ArrayList<Item> inventory --
     *              An ArrayList containing Item objects
     */
    public ArrayList<Item> getInventory() {
        return inventory;

    } // End of getInventory



    /**
     *  This checks the inventory to see whether the requested
     *  item exists in there or not.
     *
     * @param String check -- The name of the item your looking for
     *
     * @return True if the item was found, false otherwise.
     */
	public boolean checkForItem(String check) {
		for (Item item : inventory) {
			String itemName = item.getItemName().toLowerCase();
			String checkName = check.toLowerCase();

			if (itemName.equals(checkName))
				return true;
		}
		return false;
	} // End of


	/**
	 * Get an item from the inventory by its String Name, rather than the object
	 * itself. The 'key' to access the object like a HashMap. This is more a
	 * method of convenience/optional. Note: It does NOT remove the item from
	 * the inventory, it simply returns the object
	 *
	 * @param String
	 *            key -- The name (Rusty Sword, Excalibur, Boobies..., item
	 *            (Weapon, Potion, Inanimate)
	 *
	 * @return Item
	 */
	public Item get(String key) {
		String checkName = key.toLowerCase();

		for (Item item : inventory) {
			String itemName = item.getItemName().toLowerCase();

			if (itemName.equals(checkName))
				return item;
		}
		System.out.println("Item " + key + " was not found!");

		return null;
	} // End of get


	/**
	 * Give an item from the inventory by referencing its String Name, rather
	 * than the object itself. The 'key' to access the object like a HashMap.
	 * This is more a method of convenience/optional.
	 *
	 * @param String
	 *            key -- The name (Rusty Sword, Excalibur, Boobies..., item
	 *            (Weapon, Potion, Inanimate)
	 *
	 * @return
	 */
	public Item give(String check) {
		Item item;
		String itemName;
		String checkName = check.toLowerCase();

		for (int i = 0; i < inventory.size(); i++) {
			item = inventory.get(i);
			itemName = item.getItemName().toLowerCase();

			if (itemName.equals(checkName))
				return inventory.remove(i);
		}
		System.out.println("Item " + check + " was not found!");
		return null;
	} // End of give



	/**
	 * This is a single parameter version of the add method This method assumes
	 * the user does not know the name of the Item with which they wish to add.
	 * This method generally assumes that the user is a server simply retriving
	 * objects and adding them to user inventories. It will assign by default
	 *
	 *
	 * @param Item
	 *            thing -- The Item object that the user wishes to add to the
	 *            inventory.
	 */
	public void add(Item thing) {
		inventory.add(thing);
	} // End of add



	/**
	 * Lets say the player or whoever is holding the inventory decides they want
	 * to intentionally destroy and item as opposed to dropping it, then this
	 * method is called. In essence, it simply doesnt catch the returned map
	 * value object.
	 */
	public boolean trash(String check) {
		Item item;
		String itemName;
		String checkName = check.toLowerCase();

		for (int i = 0; i < inventory.size(); i++) {
			item = inventory.get(i);
			itemName = item.getItemName().toLowerCase();

			if (itemName.equals(checkName)) {
				inventory.remove(i);
				return true;
			}
		}
		System.out.println("Item " + check + " was not found!");
		return false;
	} // End of trash


    /**
     * Removes the seleceted Item from the inventory
     *
     * @param Item item -- The item in question
     */
	public void removeItem(Item item){
		inventory.remove(item);
	} // End of removeItem


	/**
	 * Gives the size of the inventory (as defined by the number of items)
	 *
	 * @return int
	 */
	public int size() {
		return inventory.size();
	} // End of size

    /**
     * Displays just the names of the items. This way the rooms can display
     * just the name of the item, and the player can know the details of the
     * item.
     *
     * @return String representation of the items in a room.
     */
    public String displayItems() {
        String result = "";
        for (Item item : inventory) {
            result +=  "\t+\t" + item.getItemName() + "\n";
        }
        // result += "+================================+\n\n";
        return result;

    } // End of displayItems


	/**
	 * Lists all items currently in the inventory. In addition, it organizes the
	 * output such that each class of item is grouped together
	 *
	 * @return
	 *
	 * @TODO I would like to sort all the weapons (for example) into
	 *       alphabetical order within their own class. Then display based on
	 *       their size. 3 Individual ArrayLists might be an option, though I
	 *       wonder if a little overkill.
	 */
	public String listItemDetails() {
        String tmp = "";
        String itemType = "";
        String result = "\t*------------------------------------------*\n";
              result += "\t*              Your Inventory              *\n";
              result += "\t*------------------------------------------*\n";

		for (Item item : inventory) {
		    itemType = item.getClass().getName().substring(6);
			result += "\t*\t(" + itemType + ")\n\t";
            result += "*\t    Name: " + item.getItemName() + "\n\t";
			result += "*\t   Value: $" + item.getItemValue() + "\n\t";
			if (item instanceof Weapon) {
				result += "*\t  Damage: " + ((Weapon) item).getWeaponDamage() + "\n\t";
			} else if (item instanceof Potion) {
				result += "*\tRestores: " + ((Potion) item).getPotionPool() + "\n\t";
                result += "*\t    Type: " + ((Potion) item).getPotionType() + "\n\t";
			} else if (item instanceof Armor) {
                result += "*\t  Rating: " + ((Armor) item).getArmorRating() + "\n\t";
                result += "*\t Quality: " + ((Armor) item).checkArmorQuality() + "\n\t";
			}
            result += "*\n";
		}
        result += "\t*------------------------------------------*\n";
		return result;
	} // End of listItemDetails

}
