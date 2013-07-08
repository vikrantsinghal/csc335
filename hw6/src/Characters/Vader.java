/**
 * The Dark Lord of the Sith..Gives questions and exchanges
 * <code>Item</code>s with the user. It can also fight the user.
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

public class Vader extends HostileMOB {
	private String description;
	private String reply;
	private boolean questAvailable;

	public Vader(String name) {
		super(name, 100, 60);

		description = "I am Darth Vader. You shall die a painful death if you mess with me. I will slice every organ in your body with my Lightsaber. However, you can do me a favor. Go and bring me Luke Skywalker's hair and you get my Lightsaber.";

		reply = "Where is Luke's head?";

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
		attackWithLighsaber(c);
	}

	private void attackWithLighsaber(Character c) {
		c.decreaseHealth(getDamage());
	}

	public Item giveSpecialItem() {
		return new Weapon("Lightsaber", 60, 6000);
	}

	public boolean isQuestAvailable() {
		return questAvailable;
	}

	public void disableQuest() {
		questAvailable = false;
	}
}
