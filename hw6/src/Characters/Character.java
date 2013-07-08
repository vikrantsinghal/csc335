/**
 * Defines the overall framework of each "character" within the MUB.
 * Every class within this package inherits from this class. Can handle
 * calls to and from the server, lays out basic interactions with the rest
 * of the game world.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */


package Characters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import Items.Armor;
import Items.Chest;
import Items.Inanimate;
import Items.Item;
import Items.ItemInventory;
import Items.Key;
import Items.Portal;
import Items.Potion;
import Items.Weapon;
import Lands.Map;
import Model.Room;

public abstract class Character implements Serializable {
	protected int experience;
	protected int health;
	protected int totalGold;
	protected int level;
	protected int attackDmg;
	protected int roomNumber;
	protected Map myMap;
	protected String name;
	protected String occupation;
	protected String land;
	protected Weapon weapon;
	protected Armor armor;
	protected ItemInventory inventory;
	protected transient ObjectInputStream readFromClient;
	protected transient ObjectOutputStream writeToClient;
	protected boolean isAlive;
	protected int maxHealth;

	public Character(String n, String occp) {
		isAlive = true;
		name = n;
		occupation = occp;
		attackDmg = 1;
		health = 1;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public int attack() {
		if (this.weapon == null) {
			return attackDmg;
		} else
			return (attackDmg + this.weapon.getWeaponDamage());
	}

	/**********************************
	 * Warning: Here be Getter Methods
	 **********************************/

	public String getName() {
		return name;
	}

	public int getExperience() {
		return experience;
	}

	public int getAttackDmg() {
		if (weapon == null) {
			return attackDmg;
		} else
			return this.weapon.getWeaponDamage();
	}

	public int getGold() {
		return totalGold;
	}

	public int getHealth() {
		return health;
	}

	public int getRoom() {
		return roomNumber;
	}

	public String getLand() {
		return land;
	}

	public Room getRoomObject() {
		return myMap.getLocation(roomNumber);
	}

	public String getRoomOutput() {
		Room result = myMap.getLocation(roomNumber);
		return result.getGameOutput();
	}

	public String getOccupation() {
		return occupation;
	}

	public ItemInventory getInventory() {
		return inventory;
	}

	/**********************************
	 * Interactions specific methods
	 **********************************/

	public void gainExperience(int exp) {
		experience += exp;
		if (experience >= level * health)
			levelUp();
	}

	public void levelUp() {
		level++;
		maxHealth += 25;
		health = maxHealth;
		attackDmg += 2;
	}

	public void removeGold(int g) {
		if (g >= getGold())
			totalGold = 0;
		else
			totalGold -= g;
	}

	public void decreaseHealth(int h) {
		if (this.armor != null)
			h = this.armor.takeDamage(h);

		if (h >= health) {
			health = 0;
			isAlive = false;
		} else
			health -= h;
	}

	public boolean goTo(String direction) {
		Room result = myMap.getLocation(roomNumber);
		Room goal = null;
		if (direction.equals("north")) {
			if (result.northRoom() == -1)
				return false;
			goal = myMap.getLocation(result.northRoom());
		}
		if (direction.equals("east")) {
			if (result.eastRoom() == -1)
				return false;
			goal = myMap.getLocation(result.eastRoom());
		}
		if (direction.equals("south")) {
			if (result.southRoom() == -1)
				return false;
			goal = myMap.getLocation(result.southRoom());
		}
		if (direction.equals("west")) {
			if (result.westRoom() == -1)
				return false;
			goal = myMap.getLocation(result.westRoom());
		}
		// result.updateGameText();
		result.removeCharacter(this);
		goal.addCharacterToRoom(this);
		land = goal.getLand();
		roomNumber = goal.getLandNumber();
		return true;
	}

	public String addItem(String s) {

		// first remove item from map
		Item i = myMap.removeItem(roomNumber, s);
		getRoomObject().updateGameText();

		inventory.add(i);
		return name + " added " + i.getItemName();
	}

	public String dropItem(String s) {

		Item i = inventory.give(s);
		if (i.isQuestItem()) {
			inventory.add(i);
			return "Can't drop that!";
		}
		myMap.addItem(roomNumber, i);

		getRoomObject().updateGameText();
		return name + " dropped " + i.getItemName();
	}

	public String useItem(String selection) {
		Item i = inventory.get(selection);
		if (i == null)
			return "Nothing to use";
		else {

			// you cannot use a quest item
			if (i.isQuestItem()) {
				return "Can't use that here";
			}

			// you receive the items value of gold with inanimates
			if (i instanceof Inanimate) {
				int value = i.getItemValue();
				if (value < 0) {
					this.addGold(value);
					return "You lost " + -value + " pieces of gold!";
				} else if (value == 0) {
					inventory.trash(i.getItemName());
					return "Well that was useless...";
				} else {
					inventory.trash(i.getItemName());
					this.addGold(value);
					return "You found "
							+ value
							+ " pieces of gold! I wonder what else you can find";
				}
			}

			// your health is affected when you use potions
			if (i instanceof Potion) {
				int value = ((Potion) i).getPotionPool();
				if (value < 0) {
					inventory.trash(i.getItemName());
					this.addHealth(value);
					return "Ugh! That was disgusting!";
				} else {
					inventory.trash(i.getItemName());
					this.addHealth(value);
					return "You gained " + value + " HPs!";
				}
			}

			// you cannot use a weapon, only equip and attack
			if (i instanceof Weapon) {
				return "Can't use that here";
			}

			// you move to a new location when portal is used
			if (i instanceof Portal) {
				int newRoom = ((Portal) i).getRoom();
				Room result = myMap.getLocation(roomNumber);
				Room goal = myMap.getLocation(newRoom);

				result.removeCharacter(this);
				goal.addCharacterToRoom(this);
				land = ((Portal) i).getLand();
				roomNumber = newRoom;

				inventory.trash(i.getItemName());
				return "You have been transported to the " + this.land + " "
						+ this.roomNumber;
			}

			// you cannot use a key
			if (i instanceof Key) {
				return "Can't use that here";
			}

			// you can use a chest if you have the correct key
			if (i instanceof Chest) {
				for (Item item : inventory.getInventory()) {
					// if this item is a Key
					if (item instanceof Key) {
						Key key = (Key) item;
						// does the key unlock the chest?
						if (((Chest) i).unlock(key)) {
							this.addGold(i.getItemValue());
							inventory.trash(key.getItemName());
							inventory.trash(i.getItemName());
							return "You were able to unlock the chest!! You found "
									+ i.getItemValue() + " pieces of gold!";
						}

					}
				}
				return "You could not open the chest.";
			}

			// you cannot actually use armor
			if (i instanceof Armor) {
				return "Can't use that here";
			}
			return "";
		}
	}

	public boolean checkItemInRoom(String selection) {
		boolean result = false;
		ItemInventory temp = getRoomObject().roomInventory();
		result = temp.checkForItem(selection);
		return result;
	}

	public void addHealth(int hp) {
		if (hp + health >= (maxHealth))
			health = maxHealth;
		else
			health += hp;
	}

	public void addGold(int g) {
		totalGold += g;
	}

	public String equip(String itemToEquip) {
		if (inventory.checkForItem(itemToEquip)) {
			Item item = inventory.get(itemToEquip);
			if (item instanceof Weapon) {
				if (this.weapon != null) {
					this.weapon.setEquipped();
					this.weapon = (Weapon) item;
					((Weapon) item).setEquipped();
					// attackDmg = ((Weapon) item).getWeaponDamage();
				} else {
					this.weapon = (Weapon) item;
					((Weapon) item).setEquipped();
				}
				//
			} else if (item instanceof Armor) {
				if (this.armor != null) {
					this.armor.setEquipped();
					this.armor = (Armor) item;
					((Armor) item).setEquipped();
				} else {
					this.armor = (Armor) item;
					((Armor) item).setEquipped();
				}
			}
			return itemToEquip + " has been equipped";
		} else {
			return "Could not equip " + itemToEquip;
		}
	}

	public String getScore() {
		String result = "";
		if (this.getLevel() < 10) {
			result = "You have more stuff to do";
		} else if (this.getLevel() < 20) {
			result = "Almost there";
		} else
			result = "Why are you still playing?";
		return result;

	}

	/**
	 * This produces a string version of the character's stats
	 *
	 * @return String version of the character's stats.
	 */
	public String getUserStats() {
		String stats = "";
		stats += "   Nm: " + this.name + "\n";
		stats += "  Lvl: " + this.level + "\n";
		stats += "   HP: " + this.health + "/" + maxHealth + "\n";
		stats += "  Atk: " + this.attack() + "\n";
		stats += " Gold: " + this.totalGold + "\n";
		stats += "  Exp: " + this.experience + "\n";
		if (this.weapon == null)
			stats += "  Wpn: NA\n";
		else
			stats += "  Wpn: " + this.weapon.getWeaponDamage() + "\n";

		if (this.armor == null)
			stats += "  Amr: NA\n";
		else
			stats += "  Amr: " + this.armor.getArmorRating() + "/"
					+ this.armor.getArmorBase() + "\n";

		stats += " Land: " + this.land + "\n";
		stats += "   Rm: " + this.roomNumber + "\n";
		stats += "  Occ: " + this.occupation + "\n";
		stats += "  Inv: " + this.inventory.size() + "\n";

		return stats;

	} // End of getUserStats

	public String display() {
		String stats = "";
		stats += "\t|-------------------------------------|\n";
		stats += "\t|   Nm: " + this.name + "\n";
		stats += "\t|   HP: " + this.health + "/" + maxHealth + "\n";
		stats += "\t|  Atk: " + this.attack() + "\n";
		stats += "\t|  Occ: " + this.occupation + "\n";
		stats += "\t|-------------------------------------|\n";

		return stats;
	} // End of display

	public int getLevel() {
		return level;
	}

	public void setStreams(ObjectInputStream in, ObjectOutputStream out) {
		// skip
	}

	public void sendMessage(String message) {
		// skip
	}

	public String getMessage() {
		return null;
	}

	public Item tradeItemWithGold(String itemName, int gold) {
		itemName = itemName.toLowerCase();
		Item item = inventory.get(itemName);

		if (item == null || gold < item.getItemValue())
			return null;

		totalGold += item.getItemValue();

		return item;
	}

	/**
	 * As the name suggests, this method trades an Item with other items that
	 * are provided to the MOB as a list. If the requested item is not there in
	 * the inventory of the MOB, it will return null. In the other case it will
	 * check if the collective value of the items sent to it is greater than or
	 * equal to the value of the requested item. If it is, the it gives those
	 * items to the other character and adds the list of items in its own
	 * inventory.
	 */
	public Item tradeItemWithItem(List<Item> itemList, String item) {
		Item requestedItem;
		int valueOfSentItems = 0;

		requestedItem = inventory.give(item);

		if (requestedItem != null) {
			for (Item i : itemList) {
				valueOfSentItems += i.getItemValue();
			}

			if (valueOfSentItems >= requestedItem.getItemValue()) {
				for (Item i : itemList) {
					inventory.add(i);
				}
				return requestedItem;
			}
		}

		return null;
	}

    public void walkAbout() {
        Timer timer = new Timer(30000, new WalkAboutEvent());
        timer.start();
    }

	private class WalkAboutEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent foo) {
			String[] locationList = { "North", "South", "East", "West" };
			Random randGen = new Random();
			String land = locationList[randGen.nextInt(4)];

			while (!goTo(land)) {
				goTo(land);
			}

		}
	}

	public void startRespawn() {
		Timer timer = new Timer(100000, new RespawnEvent());
		timer.start();
	}

	private class RespawnEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			health = maxHealth;
			isAlive = true;
		}

	}


	public void setName(String name) {
		this.name = name;
	}
}
