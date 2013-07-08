/**
 * Is hungry and will inform the user of that. It will also fight
 * a user as it inherits from HostileMOB.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */


package Characters;

import java.util.Random;

import Items.Item;

public class Hulk extends HostileMOB {
	private String description;
	private String reply;
	private boolean questAvailable;

	public Hulk(String name) {
		super(name, 100, 20);

		description = "I am the legendary Hulk. I'll beat the crap out of you. Also I am hungry. Give me a Ham Burger and I will give you some health";

		reply = "Where is my Ham Burger?";

		questAvailable = true;
	}

	@Override
	public void attack(Character c) {
		beatDown(c);
	}

	private void beatDown(Character c) {
		c.decreaseHealth(getDamage());
	}

	public void giveHealth(Character c) {
		c.addHealth(20);
	}

	public String getDescription() {
		return description;
	}

	public String getReply() {
		return reply;
	}

	public boolean isQuestAvailable() {
		return questAvailable;
	}

	public void disableQuest() {
		questAvailable = false;
	}
}
