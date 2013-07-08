/**
 * Always injures and robs a player any interaction, however it
 * does so without alerting the player until long after the fact.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */


package Characters;

public class NaughtyMOB extends HostileMOB {
	private String reply;
	private String description;

	public NaughtyMOB(String name) {
		super(name, 100, 10);

		description = "I am " + name + ", the infamous naughty ghost that swarms around in the dungeon. Beware, I come and drop your pants and smack your butt. Slight carelessness can cost your health and gold.";

		reply = "Your pants are down. Your health is down. Your gold is down. Guess who??????";
	}

	@Override
	public void attack(Character c) {
		stealAndSmack(c);
	}

	public void stealAndSmack(Character c) {
		c.removeGold(100);
		this.addGold(100);
		c.decreaseHealth(getDamage());
	}

	public String getDescription() {
		return description;
	}

	public String getReply() {
		return reply;
	}

}
