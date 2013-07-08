/**
 * A class to represent Potions of all kinds including...
 * Health Potions, Mana Potions, Experience Potions,
 * Deadly Potions (Poison...), etc
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */


package Items;

import java.util.*; // Gives easy access to Java API's "util" package

@SuppressWarnings("serial")
public class Potion extends Item {
	private int potionPool; // The represents the amount of replenishment value
							// it offers
	private String potionType; // This is to distinguish between Mana and Health
								// Potions.

	public Potion(String potionName, String type, int pool, int value) {
		super(potionName, value);
		this.potionType = type;
		this.potionPool = pool;
	}

	/**
	 * This returns the type of potion: Health or Mana (at this point) It should
	 * facilitate calling 'use' methods on the server's end to be able to access
	 * this information
	 *
	 * @return The potion's type; Health or Mana, this should be taken into
	 *         consideration when accessing its use method.
	 */
	public String getPotionType() {
		return potionType;

	} // End of getPotionType

	/**
	 * This method returns the current total of the potion's Pool.
	 *
	 * @return The current total of the potionPool
	 */
	public int getPotionPool() {
		return potionPool;

	} // End of getPotionPool

	/**
	 * Potions should be considered single-use items, as such when the 'use'
	 * method is called, it will reset its "pool" to 0. This will also reset the
	 * value of the potion to 0 as well because who wants an empty bottle?
	 *
	 * @return the current value of the potionPool.
	 */
	public int use() {
		int pool = potionPool;
		potionPool = 0;
		int value = this.getItemValue();
		this.setItemValue(0);
		return pool;
	}

	@Override
	public void setQuestItem(int level) {
		isQuest = !isQuest;
	}

	@Override
	public String display() {
		String itemType = getClass().getName().substring(6);
		String result = "\t|--------------------------------|\n\t";
		result += "|\t<" + itemType + ">\n\t";
		result += "|\t    Name: " + getItemName() + "\n\t";
		result += "|\t   Value: $" + getItemValue() + "\n\t";
		result += "|\t    Type: " + getPotionType() + "\n\t";
		result += "|\tRestores: " + getPotionPool() + "\n\t";
		result += "|--------------------------------|\n\n";
		return result;
	}

} // End of Potion extends Item