/**
 * Deals damage to user's characters or to MOBs
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */


package Items;

@SuppressWarnings("serial")
public class Weapon extends Item {
	private int damage; // The amount of damage the weapon does
	private boolean equipped; // A boolean to indicate if its equipped or not

	public Weapon(String weaponName, int weaponDamage, int value) {
		super(weaponName, value);
		this.damage = weaponDamage;
		this.equipped = false;
	}

	/**
	 * Get the weapons current damage rating.
	 *
	 * @return int -- The weapons damage
	 */
	public int getWeaponDamage() {
		return damage;
	} // End of getWeaponDamage



	public int use() {
		return damage;
	}

	public void setEquipped() {
		if (equipped)
			equipped = false;
		else
			equipped = true;
	}

	public void setQuestItem(int level) {
		if (!isQuestItem()) {
			isQuest = true;
			damage += 10 * level;

			int newValue = this.getItemValue() + 10 * level;
			this.setItemValue(newValue);

		} else {
			isQuest = false;
			damage -= 10 * level;

			int oldValue = this.getItemValue() - 10 * level;
			this.setItemValue(oldValue);

		}
	}

    @Override
    public String display() {
        String itemType = getClass().getName().substring(6);
        String result = "\t|--------------------------------|\n\t";
        result += "|\t<" + itemType + ">\n\t";
        result += "|\t    Name: " + getItemName() + "\n\t";
        result += "|\t   Value: $" + getItemValue() + "\n\t";
        result += "|\t  Damage: " + getWeaponDamage() + "\n\t";
        result += "|\tEquipped: " + isEquipped() + "\n\t";
        result += "|--------------------------------|\n\n";
        return result;
    }

    public boolean isEquipped() {
           return this.equipped;
    }
}
