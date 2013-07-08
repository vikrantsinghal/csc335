/**
 * HostileMOB
 *
 * The HostileMOB class is an extention of the character class.
 * This class is the primary 'aggressive' MOB unit to use. They
 * are "outfitted" with methods that allow for more combat oriented
 * iterations with users characters.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Characters;

public class HostileMOB extends Character {

	private int damageInflicted;
	private boolean isEngaged;

	public HostileMOB(String name, int health, int damage) {
		super(name, "HostileMOB");
		this.health = health;
		maxHealth = health;
		attackDmg = damage;
		damageInflicted = damage;
		isEngaged = false;
	}

	public boolean isEngaged() {
		return isEngaged;
	}

	public void setEngaged(boolean engaged) {
		isEngaged = engaged;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void attack(Character c) {
		// Different Implementations
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

	public int getDamage() {
		return damageInflicted;
	}
}
