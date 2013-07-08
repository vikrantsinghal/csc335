package model;

import songplayer.SongPlayer;

/**
 * Wumpus class
 * @author Jimmy, Vikrant, Kyle, Joshua
 *
 */
public class Wumpus implements Strategy {

	private int[] adjacentRooms;
	private int currentRoom;
	private String hint;
	private boolean alive;

	/**
	 * returns the base directory
	 */
	public static String baseDir = System.getProperty("user.dir")
			+ System.getProperty("file.separator") + "sounds"
			+ System.getProperty("file.separator");

/**
 * sets the wumpus room
 * @param rN the room to change to
 */
	public Wumpus(int rN) {
		currentRoom = rN;
	}

	/**
	 * Wumpus constructor that holds a hint string
	 * @param home the Wumpus start location
	 * @param adRoom1 the adjacent room 1
	 * @param adRoom2 the adjacent room 2
	 * @param adRoom3 the adjacent room 3
	 */
	public Wumpus(int home, int adRoom1, int adRoom2, int adRoom3) {
		adjacentRooms = new int[3];
		adjacentRooms[0] = adRoom1;
		adjacentRooms[1] = adRoom2;
		adjacentRooms[2] = adRoom3;
		currentRoom = home;
		hint = "I smell something foul";
		alive = true;
	}

/**
 * @return gets current room number
 */
	public int getRoomNumber() {
		return currentRoom;
	}

/**
 * @return  string "I smell something foul"
 */
	public String getHint() {
		return hint;
	}

/**
 * Plays the sound when the wumpus kills hunter
 * and returns -1
 */
	public int performAction() {
		// play sounds
		SongPlayer.playFile(baseDir + "WilhelmScream.wav");

		return -1;
	}

/**
 * sees if wumpus is alive
 * @return true if alive
 */
	public boolean isAlive() {
		return alive;
	}

/**
 * plays song when hunters kills wumpus
 * sets boolean alive to false
 */
	public void kill() {
		// play sounds
		SongPlayer.playFile(baseDir + "wumpusdies.wav");
		alive = false;
	}

}
