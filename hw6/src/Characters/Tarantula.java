/**
 * Gives quests and exchanges <code>Item</code>s with
 * the user. It can also fight the user.
 *
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */



package Characters;

import java.util.Random;

import Items.Item;
import Items.Weapon;

public class Tarantula extends HostileMOB {
	private String description;
	private String reply;
	private boolean questAvailable;

	public Tarantula(String name) {
		super(name, 100, 20);

		description = "I am a large and hairy Tarantula. I'll push you in my web and sting you and eat you. However, if you get me some Heroin, I can give you a bottle of my venom.";

		reply = "Where is the heroin?";
		questAvailable = true;
	}

	public String getDescription() {
		return description;
	}

	public String getReply() {
		return reply;
	}

	@Override
	public void attack(Character c) {
		sting(c);
	}

	private void sting(Character c) {
		c.decreaseHealth(getDamage());
	}

	public void disableQuest() {
		questAvailable = false;

	}

	public boolean isQuestAvailable() {
		return questAvailable;
	}

	public Item giveSpecialItem() {
		return new Weapon("Tarantula's Venom", 20, 20000);
	}
}
