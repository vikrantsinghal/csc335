/**
 * A quest giving MOB demanding characters to find its head.
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

public class HeadlessKnight extends HostileMOB {
	private String reply;
	private String description;
	private boolean questAvailable;

	public HeadlessKnight(String name) {
		super(name, 100, 30);

		description = "I am a knight who died in a battle in 1397. I was beheaded and now I seek revenge from the human race. Get me the head of Fairy Godmother and I will give you a new Sword made from a titanium and diamond alloy. When I am pissed I might chop your head off too. Beware kid.";

		reply = "Give me the head!! Give it to me!!!";

		questAvailable = true;
	}

	@Override
	public void attack(Character c) {
		slash(c);
	}

	private void slash(Character c) {
		c.decreaseHealth(getDamage());
	}

	public Item giveSpecialItem() {
		return new Weapon("Sword Of Knight", 25, 5000);
	}

	public String getReply() {
		return reply;
	}

	public String getDescription() {
		return description;
	}

	public boolean isQuestAvailable() {
		return questAvailable;
	}

	public void disableQuest() {
		questAvailable = false;
	}
}

