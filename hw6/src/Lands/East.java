package Lands;

import java.io.Serializable;
import java.util.HashMap;

import Characters.GhostRider;
import Characters.HostileMOB;
import Characters.MOB;
import Characters.NaughtyMOB;
import Characters.PastorPete;
import Characters.PissingBaby;
import Items.Armor;
import Items.Key;
import Items.Potion;
import Items.Weapon;
import Model.Room;

public class East implements Serializable {

	private HashMap<Integer, Room> roomMap;
	private boolean isActive;

	public East() {
		roomMap = new HashMap<Integer, Room>();
		setDefaultRooms();

		isActive = false;
	}

	public void activateLobby() {
		isActive = true;
	}

	public void deactivateLobby() {
		isActive = false;
	}

	public boolean getActivity() {
		return isActive;
	}

	private void setDefaultRooms() {

		// set up the north land
		Room e1, e2, e3, e4, e5, e6, e7, e8, e9;
		e1 = new Room("East", 201, -1, -1, 202, 204);
		e2 = new Room("East", 202, 201, -1, 203, 205);
		e3 = new Room("East", 203, 202, -1, -1, 206);
		e4 = new Room("East", 204, -1, 201, 205, 207);
		e5 = new Room("East", 205, 204, 202, 206, 208);
		e6 = new Room("East", 206, 205, 203, -1, 209);
		e7 = new Room("East", 207, -1, 204, 208, -1);
		e8 = new Room("East", 208, 207, 205, 209, 200);
		e9 = new Room("East", 209, 208, 206, -1, -1);

		String n1s = "----------------------------------------\n\nTavern Buduckkadunk "
				+ "\nYou can relax and buy some shiz n potions here. A place where wenches bring stale ale and the men smell"
				+ " horrid.  You love it. "
				+ "\nThere are beds, you can rest your eyes here."
				+ "\nSouth: The Road Way of Death" + "\nWest: Monkey Beach\n\n";
		e1.addCharacterToRoom(new GhostRider("The Pirate Sperm Rider"));
		e1.addItem(new Armor("Pirates Thong", 5, 952));
		e1.addItem(new Potion("Grog", "Drink", 20, 5));
		e1.addItem(new Key("Stinky Key", 100));
		e1.addItem(new Weapon("Sombre Sabre", 93, 451));
		e1.setBed(true);
		e1.setGameText(n1s);

		String n2s = "----------------------------------------\n\nThe Road Way of Death"
				+ "\nThe name of this road seems to be deceptive as there are children playing and laughing everywhere"
				+ ", but all they seem to be doing is selling these white rocks with cracks in them... business seems to be good."
				+ "\nNorth: Tavern Buduckkadunk"
				+ "\nSouth: The Arena of Fun"
				+ "\nWest: Big Bertha \n\n";
		e2.addCharacterToRoom(new PissingBaby("BabyJimmy"));
		e2.addCharacterToRoom(new PissingBaby("BabyJoshua"));
		e2.addCharacterToRoom(new PissingBaby("BabyVikrant"));
		e2.addCharacterToRoom(new PissingBaby("BabyKyle"));
		e2.addItem(new Potion("Cracked Rock", "Feel the power", 999, 10000));
		e2.addItem(new Weapon("Stick of Truth", 85, 2413));
		e2.setGameText(n2s);

		String n3s = "----------------------------------------\n\nThe Arena of Fun"
				+ "\nIf your idea of fun is death and cotton candy, then this Arena is the perfect place for you."
				+ "\nNorth: The Road Way of Death " + "\nWest: ZJ Alley \n\n";
		e3.addItem(new Potion("Vodka", "Drink", 22, 5));
		e3.addItem(new Weapon("Bloody Stump", 150, 24313));
		e3.addItem(new Armor("Bloody Legs", 22, 9522));

		e3.setGameText(n3s);
		String n4s = "----------------------------------------\n\nMonkey Beach"
				+ "\nIt's a beautiful beach with clear waters and a nice breeze.  There is one problem,"
				+ " there are so many monkeys.. SOOOOO MANY MONKEYS!!!"
				+ "\nEast: Tavern Buduckkadunk" + "\nSouth: Big Bertha"
				+ "\nWest: Drunken Old Man's Shack \n\n";
		e4.addCharacterToRoom(new MOB("Blue Monkey", 10, 15));
		e4.addCharacterToRoom(new MOB("Green Monkey", 10, 15));
		e4.addCharacterToRoom(new MOB("Drunk Monkey", 10, 15));
		e4.addCharacterToRoom(new MOB("Shy Monkey", 10, 15));
		e4.addCharacterToRoom(new MOB("Gay Monkey", 10, 15));
		e4.addCharacterToRoom(new MOB("Retarded Monkey", 10, 15));
		e4.addCharacterToRoom(new MOB("Horny Monkey", 10, 15));
		e4.addCharacterToRoom(new MOB("Sly Monkey", 10, 15));
		e4.addCharacterToRoom(new MOB("Monkey Man", 10, 15));
		e4.addCharacterToRoom(new MOB("Old Monkey", 10, 15));
		e4.addCharacterToRoom(new MOB("Super Monkey", 100, 15));
		e4.addCharacterToRoom(new MOB("Ultra Monkey", 125, 15));
		e4.addCharacterToRoom(new MOB("Super Ultra Monkey", 150, 15));
		e4.addItem(new Potion("Monkey Soup", "Delicious", 50, 999));
		e4.setGameText(n4s);

		String n5s = "----------------------------------------\n\nBig Bertha"
				+ "\nYou really don't know what or who Big Bertha is.  All you see mass of stuff that resembles a blob. "
				+ "In a certain light though, you can fully grasp the beauty of Big Bertha and can truly understand why "
				+ "people flock to it from all across the land."
				+ "\nMysterious, there seems to be the same button and switch here as Bob's Cliff."
				+ "\nNorth: Monkey Beach" + "\nSouth: ZJ Alley"
				+ "\nEast: The Road Way of Death" + "\nWest: Bob's Cliff\n\n";
		e5.addItem(new Potion("Piece of Big Bertha", "Gunk", -10, 999));
		e5.addItem(new Weapon("Ewwww", 89, 451));
		e5.addItem(new Potion("JackDaniels", "Drink", 20, 5));
		e5.setButton(true);
		e5.setSwitch(true);
		e5.setGameText(n5s);

		String n6s = "----------------------------------------\n\nZJ Alley"
				+ "\nMany have asked what ZJ meant, but only ones with great weatlh know the true meaning of ZJ. "
				+ "Some have speculated it stood for Zazh Jumz, the former King, but the services offered in the alley "
				+ "point to other unsightful meanings" + "\nNorth: Big Bertha"
				+ "\nEast: The Arena of Fun"
				+ "\nWest: The House of Chuck Norris\n\n";
		e6.addItem(new Potion("SmirnOff", "Drink", 87, 5));
		e6.addItem(new Weapon("Sombre Sabre", 666, 451));
		e6.setGameText(n6s);

		String n7s = "----------------------------------------\n\nDrunken Old Man's Shack"
				+ "\nThis place smells of alcohol and other fumes that sting the nostrels."
				+ "\nThere is a a stained sleeping mat full of filth, but you can use it to rest."
				+ "\nEast: Monkey Beach" + "\nSouth: Bob's Cliff\n\n";
		e7.addItem(new Potion("Captain Morgans", "Drink", 45, 5));
		e7.setBed(true);
		e7.setGameText(n7s);

		String n8s = "----------------------------------------\n\nBob's Cliff"
				+ "\nBoB is a jerk... and his cliff isn't that great."
				+ "\nFor some reason, this cliff has a button and a switch."
				+ "\nNorth: Drunken Old Man's Shack"
				+ "\nSouth: The House of Chuck Norris" + "\nEast: Big Bertha"
				+ "\nWest: East Gate";
		e8.addItem(new Potion("Absolut", "Drink", 22, 5));
		e8.addCharacterToRoom(new NaughtyMOB("Bob"));
		e8.setButton(true);
		e8.setSwitch(true);
		e8.setGameText(n8s);

		String n9s = "----------------------------------------\n\nThe House of Chuck Norris"
				+ "\nMany warshippers gather here to honor a legendary hero that was said to have lost his "
				+ "virginity before his father and was able to round house kick himself through time. You feel "
				+ "great power here."
				+ "\nThere are beds in back if you wish to rest."
				+ "\nNorth: Bob's Cliff" + "\nEast: ZJ Alley\n\n";
		e9.addCharacterToRoom(new PastorPete("ChuckLover", "Overseer"));
		e9.addCharacterToRoom(new HostileMOB("Chuck Norris", 99999, 1000));
		e9.addItem(new Potion("Chucks Pizz", "Drink", 9999, 99999));
		e9.addItem(new Armor("Leggings of Hell", 9999, 99999));
		e9.addItem(new Weapon("Legs of Infinity", 9999, 99999));
		e9.setBed(true);
		e9.setGameText(n9s);

		roomMap.put(201, e1);
		roomMap.put(202, e2);
		roomMap.put(203, e3);
		roomMap.put(204, e4);
		roomMap.put(205, e5);
		roomMap.put(206, e6);
		roomMap.put(207, e7);
		roomMap.put(208, e8);
		roomMap.put(209, e9);
	}

	public HashMap<Integer, Room> getMap() {
		return roomMap;
	}

}