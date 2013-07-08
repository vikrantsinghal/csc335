/**
 * Is a quest-giving MOB which offers a unique weapon to the player
 * if they do it a favor. It is also capable of combat interactions.
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

public class GhostRider extends HostileMOB {
	private String description;
	private String reply;
	private boolean questAvailable;

	public GhostRider(String name) {
		super(name, 100, 20);

		description = "I am Ghost Rider. I move around on my flying bike and shoot people with my Sperm Blaster. I can give you a Sperm Blaster if you want to, but for that, I need you to bring me the Rocket Charger for my bike. I have infinite blasters with me. I can still kill you even if I give you my blaster. NOW RUN!!!!";

		reply = "Yes? Bring me the damned Turbo Charger!!";

		questAvailable = true;
	}

	@Override
	public void attack(Character c) {
		blastOff(c);
	}

	private void blastOff(Character c) {
		c.decreaseHealth(getDamage());
	}

	public Item giveSpecialItem() {
		return new Weapon("Sperm Blaster", 20, 3000);
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
