package model;

import songplayer.SongPlayer;

/*
* This class represents the Pit type of object that will be
* contained inside a room. It implements the strategy pattern.
* It kills the hunter as soon as the hunter enters the room
* having the pit.
* @author Jimmy, Joshua, Vikrant, Kyle
*/
public class Pit implements Strategy {

	private int roomNumber;
	private int adj1;
	private int adj2;
	private int adj3;
	private String hint;

	public static String baseDir = System.getProperty("user.dir")
			+ System.getProperty("file.separator") + "sounds"
			+ System.getProperty("file.separator");

	/*
	* Contsructor
	*/
	public Pit(int rN) {
		roomNumber = rN;
	}

	/*
	* Second constructor which initializes the private instance
	* variables inside this class.
	*/
	public Pit(int rN, int a, int b, int c) {
		roomNumber = rN;
		adj1 = a;
		adj2 = b;
		adj3 = c;
		hint = "I feel a draft...";
	}

	/*
	* Returns the number of the room where the pit is located.
	*/
	public int getRoomNumber() {
		return roomNumber;
	}

	/*
	* It returns the hint offered by the Pit, that is,
	* string for draft.
	*/
	public String getHint() {
		return hint;
	}

	/*
	* It returns first adjacent room's number
	*/
	public int getAdj1() {
		return adj1;
	}

	/*
	* It returns second adjacent room's number
	*/
	public int getAdj2() {
		return adj2;
	}

	/*
	* It returns third adjacent room's number
	*/
	public int getAdj3() {
		return adj3;
	}

	/*
	* It performs the action that a Pit is supposed to take.
	* it returns -1 to represents killing of the hunter. It also plays
	* the sound file which presents the draft.
	*/
	public int performAction() {
		// play sounds
		SongPlayer.playFile(baseDir + "fall.wav");

		return -1;
	}

}
