/**
 * A room which holds items and characters and knows
 * which land has been placed in. It can interact with
 * the <code>Server</code> through the
 * <code>ClientThreadConnection</code>
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Model;

import java.io.Serializable;
import java.util.ArrayList;

import Characters.Character;
import Characters.Warrior;
import Items.Item;
import Items.ItemInventory;

public class Room implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6033000182551368958L;
	private String land;
	private int landNumber;
	private String gameOutput;
	private int northRoom;
	private int eastRoom;
	private int southRoom;
	private int westRoom;
	private String roomText;
	protected ItemInventory roomInventory;
	private ArrayList<Character> roomCharacters;
	private boolean hasButton = false;
	private boolean hasSwitch = false;
	private boolean hasBed = false;

	public Room(String l, int i, int north, int east, int south, int west) {
		land = l;
		landNumber = i;
		northRoom = north;
		eastRoom = east;
		southRoom = south;
		westRoom = west;
		roomText = "";
		setGameOutput("");
		roomInventory = new ItemInventory();
		roomCharacters = new ArrayList<Character>();
	}

	public boolean hasButton() {
		return hasButton;
	}

	public boolean hasSwitch() {
		return hasSwitch;
	}

	public boolean hasBed() {
		return hasBed;
	}

	public void setButton(boolean b) {
		hasButton = b;
	}

	public void setSwitch(boolean s) {
		hasSwitch = s;
	}

	public void setBed(boolean d) {
		hasBed = d;
	}

	public int getLandNumber() {
		return landNumber;
	}

	public String getLand() {
		return land;
	}

	public void setGameText(String entry) {
		roomText = entry;
		setGameOutput(entry + "\n" + this.inventory() + this.roomCharacters());
	}

	public void updateGameText() {
		setGameOutput(roomText + "\n" + this.inventory()
				+ this.roomCharacters());
	}

	public String toString() {
		return getGameOutput();
	}

	public int northRoom() {
		return northRoom;
	}

	public int eastRoom() {
		return eastRoom;
	}

	public int southRoom() {
		return southRoom;
	}

	public int westRoom() {
		return westRoom;
	}

	public void addItem(Item i) {
		roomInventory.add(i);
	}

	public Item removeItem(String s) {
		return roomInventory.give(s);
	}

	public String inventory() {
		String result = "";
		result += "\t+================================+\n";
		result += "\t+       Items in the room        +\n";
		result += "\t+================================+\n";
		result += roomInventory.displayItems();
		return result;
	}

	public String roomCharacters() {
		String result = "";
		result += "\t**********************************\n";
		result += "\t*     Characters in the room     *\n";
		result += "\t**********************************\n";

		for (Character actor : roomCharacters) {
			if (actor instanceof Warrior) {
				// do not show the warrior in the room
			} else {
				result += "\t*\t" + actor.getName() + "[HP: "
						+ actor.getHealth() + "/" + actor.getMaxHealth() + "]";
				if (!actor.isAlive()) {
					result += "-(Dead)-\n";
				} else
					result += "\n";
			}
		}
		// result += "**********************************\n\n";
		return result;
	}

	public ArrayList<Character> getRoomCharacters() {
		return roomCharacters;
	}

	public void addCharacterToRoom(Character ch) {
		roomCharacters.add(ch);
	}

	public void removeCharacter(Character ch) {
		roomCharacters.remove(ch);
	}

	public void sendCharactersMsgs(String message) {
		if (!roomCharacters.isEmpty()) {
			for (Character ch : roomCharacters) {
				ch.sendMessage(message);
			}
		}
	}

	public ItemInventory roomInventory() {
		return roomInventory;
	}

	public Character getCharacter(String character) {
		Character charac = null;
		for (int i = 0; i < roomCharacters.size(); i++) {
			if (roomCharacters.get(i).getName().equalsIgnoreCase(character)) {
				charac = roomCharacters.get(i);
			}
		}
		return charac;
	}

	public String getGameOutput() {
		return gameOutput;
	}

	public void setGameOutput(String gameOutput) {
		this.gameOutput = gameOutput;
	}

}
