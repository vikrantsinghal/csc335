/**
 * A unique HostileMOB that acts agressively towards users. It also has a cool gun,
 * and seeks to kill Doctor Who. Who knew?
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */


package Characters;

public class Dalek extends HostileMOB {
	private String description;
	private String reply;

	public Dalek(String name) {
		super(name, 100, 35);

		description = "I am a Dalek. I was created on Skaro. My main target is The Doctor. Those who come in my way are EXTERMINATED!!!";

		reply = "It seems that you are in my way. EXTERMINATE!!!";
	}

	@Override
	public void attack(Character c) {
		plasmaBeam(c);
	}

	private void plasmaBeam(Character c) {
		c.decreaseHealth(getDamage());
	}

	public String getReply() {
		return reply;
	}

	public String getDescription() {
		return description;
	}
}
