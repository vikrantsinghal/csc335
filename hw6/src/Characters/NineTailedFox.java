/**
 * A weird creature that says something menacing and
 * fights the user's character.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Characters;


public class NineTailedFox extends HostileMOB {
	private String description;
	private String reply;

	public NineTailedFox(String name) {
		super(name, 100, 40);

		description = "I am the Nine Tailed Fox, a tailed beast. I breathe fire and chakra. I can destroy everything using my tailed beast bomb.";

		reply = "Burn in hell!!! You shall suffer now!! HAHAHAHA!!!";
	}

	public String getDescription() {
		return description;
	}

	public String getReply() {
		return reply;
	}

	@Override
	public void attack(Character c) {
		tailedBeastBomb(c);
	}

	private void tailedBeastBomb(Character c) {
		c.decreaseHealth(getDamage());
	}
}
