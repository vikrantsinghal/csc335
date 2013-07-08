/**
 * Layouts out the basic framework for MOB objects.
 * Allows for basic interactions with the user.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */


package Characters;

public class MOB extends Character {
	private int damageInflicted;
	private String reply;

	public MOB(String name, int h, int damage) {
		super(name, "MOB");
		health = h;
		maxHealth = h;
		attackDmg = damage;
		damageInflicted = damage;
		reply = "Hello traveler";
	}

	public int performAction() {
		return damageInflicted;
	}

	public void reduceHealth(int attack) {
		if (!isAlive()) {
			System.out.println("cannot attack the dead, you idiot!!");
			return;
		}
		if (health >= attack)
			health -= attack;
		else {
			health = 0;
			setAlive();
		}
	}

	public void setAlive() {
		if (health <= 0)
			isAlive = false;
		else
			isAlive = true;
	}

	public void sendMessage(String attackDmg) {
		int attack = 0;
		try {
			attack = Integer.parseInt(attackDmg);
		} catch (NumberFormatException nfe) {
			// error
		}
		if (attack < damageInflicted) {
			reply = "Come back when you an equip a stronger weapon!";
		} else {
			reply = "Prepare for battle!";
		}
	}

	public String getMessage() {
		return reply;
	}
}
