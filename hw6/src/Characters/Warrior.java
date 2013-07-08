/**
 * Allows the user to interact with the MUD.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */
package Characters;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Items.ItemInventory;
import Lands.Map;

public class Warrior extends Character implements Serializable {

	public Warrior(String n) {
		super(n, "Warrior");
		name = n;
		experience = 0;
		health = 100;
		maxHealth = 100;
		totalGold = 10;
		level = 0;
		attackDmg = level + 1;
		inventory = new ItemInventory();
		weapon = null;
		armor = null;
		myMap = new Map();
		land = "Lobby";
		roomNumber = 0;
		readFromClient = null;
		writeToClient = null;
	}

	public void gainExperience(int exp) {
		experience += exp;
		if (experience >= level * health)
			levelUp();
	}

	public void subHealth(int hp) {
		health -= hp;
		if (health <= 0) {
			health = 20;
			land = "Lobby";
			roomNumber = 0;
		}
	}

	public void subGold(int g) {
		totalGold -= g;
		if (totalGold <= 0)
			totalGold = 0;
	}

	public void setName(String n) {
		name = n;
	}

	public void setStreams(ObjectInputStream in, ObjectOutputStream out) {
		readFromClient = in;
		writeToClient = out;
	}

	public void closeStreams() {
		readFromClient = null;
		writeToClient = null;
	}

	public void sendMessage(String message) {
		try {
			writeToClient.writeObject(message);
		} catch (IOException e) {
			// error
		}

	}

	public String getMessage() {
		String answer = "";
		try {
			answer = (String) readFromClient.readObject();
		} catch (IOException e) {
			// error
		} catch (ClassNotFoundException e) {
			// error
		}
		return answer;
	}

}
