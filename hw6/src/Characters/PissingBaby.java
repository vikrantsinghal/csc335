/**
 * Gives quests and sprays urine on the user for
 * damage.
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
import Items.Weapon;

public class PissingBaby extends HostileMOB {

	private String reply;
	private Item quest;
	private boolean questAvailable;

	public PissingBaby(String name) {
		super(name, 100, 15);
		reply = "Me llamo " + name + ". I am known about the kingdom as 'The Pissing Baby.' Please, leave me be.";
		inventory = new ItemInventory();
		quest = new Weapon("Pissing Baby's Super Soaker", 20, 200);
		quest.setQuestItem(5);
		inventory.add(quest);
		questAvailable = true;
	}

	public String getMessage() {
		return reply;
	}

	public void setAvailable() {
		questAvailable = false;
	}

	public boolean isQuestAvailable() {
		return questAvailable;
	}

	public Item getQuest() {
		return quest;
	}

	@Override
	public void attack(Character c) {
		peeAcid(c);
	}

	private void peeAcid(Character c) {
		c.decreaseHealth(getDamage());
	}

	// The Baby wants to know what level you are at. If you are below level 5,
	// nothing happens. In the instance when you are above level 5, the Baby
	// will give you a weapon, but only once.
	public void sendMessage(String entry) {
		if (!isQuestAvailable()) {
			reply = "Don't be greedy, senor. Enjoy the little things... if you know what I mean";
		} else {
			int level = 0;
			try {
				level = Integer.parseInt(entry);
			} catch (NumberFormatException nfe) {
				// error
			}
			if (level >= 5) {
				reply = "Hola there, senor. Me llamo Jorge and I hope you enjoy the present.";
				if (questAvailable) {
					inventory.removeItem(quest);
					setAvailable();
				}
			}
		}
	}
}
