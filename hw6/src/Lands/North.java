package Lands;

import java.io.Serializable;
import java.util.HashMap;

import Characters.HostileMOB;
import Characters.MOB;
import Characters.NineTailedFox;
import Characters.PastorPete;
import Characters.PissingBaby;
import Characters.SellerMOB;
import Items.Armor;
import Items.Chest;
import Items.Inanimate;
import Items.Key;
import Items.Portal;
import Items.Potion;
import Items.Weapon;
import Model.Room;

public class North implements Serializable {

	private HashMap<Integer, Room> roomMap;
	private boolean isActive;

	public North() {
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
		Room n1, n2, n3, n4, n5, n6, n7, n8, n9;
		n8 = new Room("North", 108, 105, 109, 100, 107);
		n7 = new Room("North", 107, 104, 108, -1, -1);
		n9 = new Room("North", 109, 106, -1, -1, 108);
		n4 = new Room("North", 104, 101, 105, 107, -1);
		n6 = new Room("North", 106, 103, -1, 109, 105);
		n1 = new Room("North", 101, -1, 102, 104, -1);
		n3 = new Room("North", 103, -1, -1, 106, 102);
		n2 = new Room("North", 102, -1, 103, 105, 101);
		n5 = new Room("North", 105, 102, 106, 108, 104);

		// rooms 101, 102, and 103
		String n1s = "----------------------------------------\n\nStairway to 'Eaven'\n'Welcome to ye "
				+ "'Eaven! If thou is hungry and thirsty, feast upon our delicious menu! Fried "
				+ "polar pellets?' Ugh, polar bear poop? No thanks, I wonder what else is going on here\n"
				+ "East: City Edge\nSouth: The Random Alley";
		n1.addItem(new Potion("Mighty Potion of Drunkards", "drink", 30, 10));
		n1.addItem(new Potion("Leftover Pint of Beer", "drink", -5, 1));
		n1.addItem(new Potion("Mighty Potion of Drunkards", "drink", 30, 10));
		n1.addItem(new Potion("Leftover Pint of Beer", "drink", -5, 1));
		n1.addCharacterToRoom(new MOB("Kathy", 200, 30));
		n1.setGameText(n1s);

		String n2s = "----------------------------------------\n\nCity Edge\nThere seems to be danger about... some "
				+ "men with taser guns are discussing the body laying on the ground. Better stay away, tasers "
				+ "fricking hurt! There are a lot of people standing by watching, try and ask for more information\n"
				+ "East: Olde Cathedral\nSouth: Festival\nWest: Stairway to 'Eaven";
		n2.addCharacterToRoom(new MOB("Eathen", 30, 5));
		n2.addCharacterToRoom(new MOB("Orlando", 50, 6));
		n2.addCharacterToRoom(new MOB("Henry Dixon", 100, 10));
		n2.addCharacterToRoom(new HostileMOB("Dick", 50, 50));
		n2.addCharacterToRoom(new HostileMOB("Rick", 50, 50));
		n2.addItem(new Weapon("Golf club", 40, 60));
		n2.setGameText(n2s);

		String n3s = "----------------------------------------\n\nOlde Cathedral\nHuh, this church looked " +
				"much smaller from a distance. Whoever built this church had some serious manpower... " +
				"possibly some extraterristial assistance, you know, aliens or something. Maybe you can " +
				"explore the church and find some clues.\nSouth: Tree Huggers Club\nWest: City Edge";
		n3.addCharacterToRoom(new PastorPete("Pete", "Pastor"));
		n3.addItem(new Chest("Church Treasure", 30000, 10313));
		n3.addItem(new Inanimate("Candle", 0));
		n3.addItem(new Portal("Hidden passage", 25));
		n3.addItem(new Armor("White robe", 30, 30));
		SellerMOB shady = new SellerMOB("Shady");
		shady.addGold(10000);
		shady.getInventory().add(new Potion("Kool-Aid", "drink", 100, 50));
		shady.getInventory().add(new Inanimate("Buddy Holly", 50));
		n3.addCharacterToRoom(shady);
		n3.setGameText(n3s);

		// rooms 104, 105, and 106
		String n4s = "----------------------------------------\n\nThe Random Alley\nThere isn't much room in this "
				+ "alley way... There appears to be some things on the ground, its hard to tell without "
				+ "any light! Hello?? You see something...\nNorth: Stairway to 'Eaven\nEast: Festival\nSouth: "
				+ "Festival Games";
		n4.addCharacterToRoom(new HostileMOB("Black figure", 300, 10));
		n4.addItem(new Potion("Random bottle", "poison", 1, 0));
		n4.addItem(new Inanimate("Diamond in the rough", 2));
		n4.addItem(new Inanimate("Bag", 15));
		n4.addItem(new Inanimate("Sock", 0));
		n4.addItem(new Inanimate("Weird hose", 50));
		n4.addItem(new Armor("Hoody", 2, 15));
		n4.addItem(new Chest("Locked box", 555, 10404));
		n4.addItem(new Key("Rusty key", 10000));
		n4.setGameText(n4s);

		String n5s = "----------------------------------------\n\nFestival\nWow, you have never seen a party like "
				+ "this before. Men drinking with their ladies, ladies drinking with their babies, and "
				+ "babies drinking with the sheep. Silly sheep, it seems everyone is focused on the festival. "
				+ "Maybe you should look around for some more information about this insanely awesome shindig.\n"
				+ "North: City Edge\nEast: Tree Huggers Club\nSouth: City Center\nWest: The Random Alley";
		n5.addCharacterToRoom(new HostileMOB("Drunk Drew", 50, 20));
		n5.addCharacterToRoom(new HostileMOB("Blacked-out Barry", 3, 10));
		n5.addCharacterToRoom(new MOB("Zelda", 100, 15));
		n5.addItem(new Potion("Leftover Pint of Beer", "drink", -5, 1));
		n5.addItem(new Potion("Empty Pint of Beer", "drink", 1, 0));
		n5.addItem(new Inanimate("Beach Volleyball", 2));
		n5.addItem(new Inanimate("Checkered flag", 35));
		n5.addItem(new Inanimate("Sock", 0));
		n5.addItem(new Inanimate("Heroin", 10000));
		n5.addItem(new Armor("Liederhosen", 20, 30));
		n5.setGameText(n5s);

		String n6s = "----------------------------------------\n\nTree Huggers Club\n'Hello there my friend. "
				+ "Welcome to the group of nature lovers, the caregivers to the world!' Woah, these people are "
				+ "weird... A little too high on life. There seems to be another path.\nNorth: Olde Cathedral\n"
				+ "South: Lookout point\nWest: Festival";
		n6.addCharacterToRoom(new MOB("Joseph", 50, 5));
		n6.addCharacterToRoom(new MOB("Willow", 50, 5));
		n6.addItem(new Potion("Glass of Mango Tea", "drink", 10, 5));
		n6.addItem(new Potion("Cup with Brown Liquid", "drink", 100, 50));
		n6.addItem(new Potion("Leftover Pint of Beer", "drink", -5, 1));
		n6.addItem(new Inanimate("Piece of tree", 10));
		n6.addItem(new Weapon("Glass Water Bowl", 45, 100));
		n6.setGameText(n6s);

		// rooms 107, 108, and 109
		String n7s = "----------------------------------------\n\nFestival Games\n'Any takers? 5 coins, "
				+ "3 balls! Take a shot, anybody? You, traveler!' He is looking at you, no time for "
				+ "games. You walk towards a booth with some burly men standing around, drinking beer "
				+ "and arm wrestling.\nNorth: The Random Alley\nEast: City Center";
		n7.addCharacterToRoom(new PissingBaby("Jorge"));
		n7.addCharacterToRoom(new MOB("Bruce", 100, 15));
		n7.addItem(new Potion("Leftover Pint of Beer", "drink", -5, 1));
		n7.addItem(new Potion("Empty Pint of Beer", "drink", 1, 0));
		n7.addItem(new Inanimate("Trash bag", 2));
		n7.addItem(new Inanimate("Square rock", 30));
		n7.addItem(new Weapon("Chicken wing bone", 5, 1));
		n7.addItem(new Inanimate("Sock", 0));
		n7.setGameText(n7s);

		String n8s = "----------------------------------------\n\nCity Center\nThe sound of kids playing and merchants "
				+ "shouting catches your attention. You walk into an open center where people are gathering "
				+ "to meet, it appears to be a fest. 'Get your smoked squirrel meat! Fresh smoked, just caught.' "
				+ "'Who wants to play some hack sack?' You see a man selling supplies ahead, might be useful.\n"
				+ "North: Festival\nEast: Lookout point\nSouth: Back to City Gate\nWest: Festival Games";
		n8.addCharacterToRoom(new HostileMOB("Will-kick-your-ass", 50, 5));
		n8.addCharacterToRoom(new HostileMOB("Will-punch-your-throat", 50, 5));
		n8.addItem(new Potion("Pint of Beer", "drink", 10, 5));
		n8.addItem(new Potion("Mighty Potion of Drunkards", "drink", 30, 10));
		n8.addItem(new Potion("Leftover Pint of Beer", "drink", -5, 1));
		n8.addItem(new Inanimate("Broken chair", 0));
		n8.addItem(new Inanimate("Pillow case", 10));
		n8.addItem(new Weapon("Mini Pistol", 5, 20));
		n8.addItem(new Inanimate("Sleeping child", 100));
		n8.setGameText(n8s);

		String n9s = "----------------------------------------\n\nLookout Point\nYou hear music playing "
				+ "in the distance, but there is another sound, more distinct, what is that? You walk "
				+ "towards a clearing in the trees and you see the blue shimmer of water far below. "
				+ "There is a cliff, better be careful.\nNorth: Tree Huggers Club\nWest: City Center";
		n9.addCharacterToRoom(new NineTailedFox("Eevee"));
		n9.addItem(new Potion("Mighty Potion of Drunkards", "drink", 30, 10));
		n9.addItem(new Potion("Mighty Potion of ???", "poison", -30, 75));
		n9.addItem(new Potion("Coca-cola", "drink", 10, 5));
		n9.addItem(new Inanimate("Sleeping woman", 0));
		n9.addItem(new Inanimate("Nickel", 1));
		n9.addItem(new Inanimate("Basket", 5));
		n9.setGameText(n9s);

		roomMap.put(101, n1);
		roomMap.put(102, n2);
		roomMap.put(103, n3);
		roomMap.put(104, n4);
		roomMap.put(105, n5);
		roomMap.put(106, n6);
		roomMap.put(107, n7);
		roomMap.put(108, n8);
		roomMap.put(109, n9);
	}

	public HashMap<Integer, Room> getMap() {
		return roomMap;
	}
}
