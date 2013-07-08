package model;

import java.util.Random;
import songplayer.*;

/*
* This class represent the bats which would be contained
* within the rooms.
* @author Jimmy, Joshua, Vikrant, Kyle
*/
public class Bat implements Strategy {

	private int roomNumber;
	private int adj1;
	private int adj2;
	private int adj3;
	private String hint;

	public static String baseDir = System.getProperty("user.dir")
			+ System.getProperty("file.separator") + "sounds"
			+ System.getProperty("file.separator");

	/*
	* Constructor
	*/
	public Bat(int rN) {
		roomNumber = rN;
	}

	/*
	* Constructor with parameters to initialize the private
	* instance variables
	*/
	public Bat(int rN, int a, int b, int c) {
		roomNumber = rN;
		adj1 = a;
		adj2 = b;
		adj3 = c;
		hint = "I hear squeaking...";
	}

	/*
	* Returns the number of the room where the Bat is located
	*/
	public int getRoomNumber() {
		return roomNumber;
	}

	/*
	* It returns the hint offered by the Bat, that is,
	* string for squeaking sound
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
	* It performs the action that a bat is supposed to take.
	* Generates a number between 0 and 3 to deal with the probability
	* concept. The probability of moving to another room is 75%. It
	* also plays the sound of squeaking bats.
	*/
	public int performAction() {
		// play sounds
		SongPlayer.playFile(baseDir + "bats.wav");

		int result = 0;
		Random oneInFour = new Random();
		result = oneInFour.nextInt(4);
		if (result == 3)
			return getRoomNumber();
		else
			return moveHunter();
	}

	/*
	* A private method to decide which room the hunter has
	* to be moved to.
	*/
	private int moveHunter() {
		int moveToRoom = getRoomNumber();

		while (moveToRoom == getRoomNumber()) {
			Random generator = new Random();
			moveToRoom = generator.nextInt(20) + 1;
		}

		return moveToRoom;
	}
}
