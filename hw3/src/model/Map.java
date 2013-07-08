package model;

import java.util.HashMap;

/**
 * The Map class holds the objects of EmptyRoom, left blank for the time being
 * 
 * @author Josh, Jimmy, Kyle, and Vikrant
 * 
 */
public class Map {

	private HashMap<Integer, EmptyRoom> rooms = new HashMap<Integer, EmptyRoom>();

	// set up a map with adjoining rooms equal to those in the spec provided
	public Map() {
		addRoom(-1, new EmptyRoom(-1, 0, 0, 0));
		addRoom(1, new EmptyRoom(1, 20, 2, 5));
		addRoom(2, new EmptyRoom(2, 1, 3, 10));
		addRoom(3, new EmptyRoom(3, 4, 2, 12));
		addRoom(4, new EmptyRoom(4, 5, 3, 14));
		addRoom(5, new EmptyRoom(5, 1, 4, 6));
		addRoom(6, new EmptyRoom(6, 5, 7, 15));
		addRoom(7, new EmptyRoom(7, 20, 6, 8));
		addRoom(8, new EmptyRoom(8, 16, 7, 19));
		addRoom(9, new EmptyRoom(9, 20, 10, 19));
		addRoom(10, new EmptyRoom(10, 9, 2, 11));
		addRoom(11, new EmptyRoom(11, 10, 12, 18));
		addRoom(12, new EmptyRoom(12, 11, 3, 13));
		addRoom(13, new EmptyRoom(13, 12, 14, 17));
		addRoom(14, new EmptyRoom(14, 13, 4, 15));
		addRoom(15, new EmptyRoom(15, 6, 16, 14));
		addRoom(16, new EmptyRoom(16, 8, 17, 15));
		addRoom(17, new EmptyRoom(17, 13, 18, 16));
		addRoom(18, new EmptyRoom(18, 11, 19, 17));
		addRoom(19, new EmptyRoom(19, 8, 9, 18));
		addRoom(20, new EmptyRoom(20, 1, 7, 9));
	}

	// allow the map to add a room
	private void addRoom(int i, EmptyRoom r) {
		rooms.put(i, r);
	}

	// retrieve the room at the given index
	public EmptyRoom getRoom(int i) {
		return rooms.get(i);
	}
}