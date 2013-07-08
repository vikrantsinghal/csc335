package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * The EmptyRoom is the basic cave room in which a hunter may enter. What exists
 * in that room depends on the objects that get assigned to it in GameMaster
 * 
 * @author Josh, Jimmy, Kyle, and Vikrant
 * 
 */
public class EmptyRoom {

	// instance variables of EmptyRoom
	protected final int roomNumber;
	private boolean hasWumpus;
	private boolean hasHunter;
	private boolean hasBat;
	private boolean hasPit;
	private int adjacent1;
	private int adjacent2;
	private int adjacent3;
	private Collection<Object> objects;
	private Strategy strategy;

	public EmptyRoom(int rmNum, int a, int b, int c) {
		this.setStrategy(null);
		roomNumber = rmNum;
		hasWumpus = false;
		hasHunter = false;
		hasBat = false;
		hasPit = false;
		adjacent1 = a;
		adjacent2 = b;
		adjacent3 = c;
		objects = new ArrayList<Object>(3);

	} // End of RoomType

	// retrieve the adjacents rooms
	public int getAdj1() {
		return adjacent1;
	}

	public int getAdj2() {
		return adjacent2;
	}

	public int getAdj3() {
		return adjacent3;
	} // end adjacent rooms

	/**
	 * Determines the state of the object being added. The Wumpus, Bat, and Pit
	 * are the objects that each room could possess. Also give a strategy to the
	 * room
	 * 
	 * @param character
	 */
	public void addObject(Object e) {
		if (e instanceof Wumpus) {
			this.setWumpusPresence();
			this.setStrategy(new Wumpus(this.getRoomNumber()));
		}
		if (e instanceof Bat) {
			this.setHasBat();
			this.setStrategy(new Bat(this.getRoomNumber()));
		}
		if (e instanceof Pit) {
			this.setHasPit();
			this.setStrategy(new Pit(this.getRoomNumber()));
		}
		objects.add(e);
	}

	/**
	 * Returns the string reprsentation of the room's hint
	 * 
	 * @return String -- The individual room's hint
	 */
	public String hint() {
		String result = "";
		Collection<Object> temp = objects;
		Iterator<Object> i = temp.iterator();
		while (i.hasNext()) {
			Object a = i.next();
			Bat b = null;
			Pit p = null;
			Wumpus w = null;
			if (a instanceof Bat) {
				b = ((Bat) a);
				result += ((Bat) b).getHint() + "\n";
			}
			if (a instanceof Pit) {
				p = ((Pit) a);
				result += ((Pit) p).getHint() + "\n";
			}
			if (a instanceof Wumpus) {
				w = ((Wumpus) a);
				result += ((Wumpus) w).getHint() + "\n";
			}
		}

		return result;

	} // End of hint

	/**
	 * Check to see if the room has a wumpus occupying it
	 * 
	 * @return Boolean -- True, if there is a wumpus, false otherwise
	 */
	public boolean hasWumpus() {
		return hasWumpus;

	} // End of hasWumpus

	/**
	 * Check to see if the room has a hunter occupying it
	 * 
	 * @return Boolean -- True, if there is a hunter, false otherwise
	 */
	public boolean hasHunter() {
		return hasHunter;

	} // End of hasHunter

	/**
	 * Returns this Rooms number
	 * 
	 * @return Int -- the room number
	 */
	public int getRoomNumber() {
		return this.roomNumber;
	} // End of getRoomNumber

	/**
	 * Returns the room numbers of the Adjacent rooms
	 * 
	 * @return
	 * 
	 * 
	 * @return int
	 */
	public List<Integer> getAdjacentRoom() {
		List<Integer> rooms = new ArrayList<Integer>();
		rooms.add(adjacent1);
		rooms.add(adjacent2);
		rooms.add(adjacent3);
		return rooms;

	} // End of getAdjactentRoom

	/**
	 * Set whenever the Wumpus enters or leaves the room
	 * 
	 */
	public void setWumpusPresence() {
		hasWumpus = !hasWumpus;

	} // End of setWumpusPresence

	/**
	 * Set whenever the Hunter enters of leaves the room
	 */
	public void setHunterPresense() {
		hasHunter = !hasHunter;

	} // End of setHunterPresense

	// let us know if there is a bat in the room
	public boolean hasBat() {
		return hasBat;
	}

	// reverse the current state of bat in the room
	public void setHasBat() {
		hasBat = !hasBat;
	}

	// let us know if the room is a pit
	public boolean hasPit() {
		return hasPit;
	}

	// reverse the current state of room as a pit
	public void setHasPit() {
		hasPit = !hasPit;
	}

	// return the strategy assigned in the room
	public Strategy getStrategy() {
		return strategy;
	}

	// give the room a strategy
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Returns the room numbers of the Adjacent rooms
	 * 
	 * @return int
	 */
	public List<Integer> getAdjacentRooms() {
		List<Integer> rooms = new ArrayList<Integer>();
		rooms.add(adjacent1);
		rooms.add(adjacent2);
		rooms.add(adjacent3);
		return rooms;

	} // End of getAdjactentRoom

}