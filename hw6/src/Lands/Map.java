package Lands;

import java.io.Serializable;
import java.util.HashMap;

import Items.Item;
import Model.Room;

public class Map implements Serializable {

	private HashMap<String, HashMap<Integer, Room>> map;
	private Lobby lobby;
	private North north;
	private East east;
	private South south;
	private West west;

	public Map() {
		map = new HashMap<String, HashMap<Integer, Room>>();
		lobby = new Lobby();
		north = new North();
		east = new East();
		south = new South();
		west = new West();
		setMap();
	}

	public Room getLocation(int room) {
		String land = "";
		if (room % 100 == 0)
			land = "Lobby";
		else if (room < 200)
			land = "North";
		else if (room < 300)
			land = "East";
		else if (room < 400)
			land = "South";
		else
			land = "West";

		HashMap<Integer, Room> temp = map.get(land);
		Room result = (Room) temp.get(room);
		return result;
	}

	/*
	 * This will be the starting 5 rooms to which a user can travel
	 */
	public void setMap() {

		map.put("Lobby", lobby.getMap());
		map.put("North", north.getMap());
		map.put("East", east.getMap());
		map.put("South", south.getMap());
		map.put("West", west.getMap());
	}

	public Item removeItem(int roomNumber, String s) {
		return this.getLocation(roomNumber).removeItem(s);
	}

	public void addItem(int roomNumber, Item i) {
		this.getLocation(roomNumber).addItem(i);
	}
}
