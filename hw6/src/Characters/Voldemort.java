/**
 * Tom Riddle himself! Gives questions and exchanges <code>Item</code>s with
 * the user. It can also fight the user.
 *
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Characters;

public class Voldemort extends HostileMOB {
	private String description;
	private String reply;

	public Voldemort(String name) {
		super(name, 100, 30);

		description = "I am Lord Voldemort. I seek revenge from Harry Potter and will give you excruciating pain if you come in my way you mudblood!!";

		reply = "You did not heed to my warning it seems. CRUCIO!!!!!!";
	}

	public String getDescription() {
		return description;
	}

	public String getReply() {
		return reply;
	}

	@Override
	public void attack(Character c) {
		crucio(c);
	}

	private void crucio(Character c) {
		c.decreaseHealth(getDamage());
	}
}
