/**
 * Stings and it stings hard. It is in the users best interest
 * to kill it. Can perform combat interactions.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */


package Characters;

public class MightyScorpion extends HostileMOB {
	private String description;
	private String reply;

	public MightyScorpion(String name) {
		super(name, 100, 20);

		description = "I am the Mighty Scorpion. I sting and I sting hard. My venom can be lethal, but it can't affect me. I usually like to eat away parts of people's bodies. i will do the same to you too.";

		reply = "I crushed your body with my tail and stung you. Now bleed and suffer.";
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
}
