package Lands;

import java.io.Serializable;
import java.util.HashMap;

import Characters.Dalek;
import Characters.GhostRider;
import Characters.HeadlessKnight;
import Characters.HostileMOB;
import Characters.Hulk;
import Characters.MightyScorpion;
import Characters.NineTailedFox;
import Characters.Tarantula;
import Characters.Vader;
import Characters.Voldemort;
import Items.Armor;
import Items.Chest;
import Items.Inanimate;
import Items.Key;
import Items.Portal;
import Items.Potion;
import Items.Weapon;
import Model.Room;


public class West implements Serializable {

	private HashMap<Integer, Room> roomMap;
	private boolean isActive;

	public West() {
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
		Room w1, w2, w3, w4, w5, w6, w7, w8, w9;
		w1 = new Room("West", 401, 402, 404, -1, -1);
		w2 = new Room("West", 402, 403, 405, 401, -1);
		w3 = new Room("West", 403, -1, 406, 402, -1);
		w4 = new Room("West", 404, 405, 407, -1, 401);
		w5 = new Room("West", 405, 406, 408, 404, 402);
		w6 = new Room("West", 406, -1, 409, 405, 403);
		w7 = new Room("West", 407, 408, -1, -1, 404);
		w8 = new Room("West", 408, 409, 400, 407, 405);
		w9 = new Room("West", 409, -1, -1, 408, 406);

		// Room 1
		String n1s = "Welcome to the room of cockroaches. This is supposed to be a 4500 A.D world, but natural selection favors the roaches.\n"
				+ "Here you can find weapons like insect killers, naphthalene balls and a bug-zapper."
				+ "Also you might want a small armor because the world is dangerous, you never know what will kick you in the butt."
				+ "But yeah, beware of the Tarantulas. They'll trap you and chew you till you your throat can't let out more screams."
				+ "\nYou can go:"
				+ "\nNorth"
				+ "\nEast\n";
		w1.addItem(new Weapon("Insect Killer", 10, 1000));
		w1.addItem(new Weapon("Naphthalene", 15, 1500));
		w1.addItem(new Weapon("Bug Zapper", 20, 2000));
		w1.addItem(new Armor("Bug Repellent", 15, 500));
		w1.addCharacterToRoom(new Tarantula("Dirty Spider"));
		w1.addItem(new Inanimate("Head Of Fairy Godmother", 3000));
		w9.addItem(new Key("Magic Key", 6656));
		w1.setGameText(n1s);

		// Room 2
		String n2s = "Aah! This is room number 2. Oh what is that stench? That must be from the filthy room 1! And you?\n"
				+ "You smell like you just met a spider! Eeewww! Leggedy leggedy creatures! Anyway. Let's get to the point.\n"
				+ "This room is just a plain room. Although there is a chest you might want to unlock.\n"
				+ "There are plasma radiators in it. Well of course you'd want that! Oh and yeah! Before you die!\n"
				+ "There are creatures who would torture you for fun. Stay away from them if you can."
				+ "\nYou can go:"
				+ "\nNorth\nSouth"
				+ "\nEast\n";
		w2.addItem(new Chest("Hairy Chest", 7000, 9999));
		w2.setGameText(n2s);

		// Room 3
		String n3s = "Oh look at that! Hah! Another guy who wants to be destroyed by the daleks! You wanna fight them?\n"
				+ "Perhaps you need a weapon. Go pickup a Degenrator Beam to atomize the daleks before they vaporize you!\n"
				+ "This is 4500 A.D kid! You just wanna stay here or wanna go and see the world? Get some money to but beautiful things!\n"
				+ "Am I going too fast? No you moron! You have to quick to survive in this world!"
		+ "\nYou can go:"
		+ "\nSouth"
		+ "\nEast\n";
		w3.addItem(new Weapon("Degenerator Beam", 30, 4000));
		w3.addCharacterToRoom(new Dalek("Emperor Dalek"));
		w3.addItem(new Inanimate("Luke Skywalker's Hair", 5000));
		w3.setGameText(n3s);

		// Room 4
		String n4s = "Hey kid! You see the ultra-sonic jets flying around? I can see from your face that you travelled too ahead of your time!\n"
				+ "There are no mobile objects that you used to call 'cars'!! Silly thing right? Things with wheels moving on the ground!\n"
				+ "That was too slow, haha! Vader is a nice guy. I'm forced to say that though! If you can defeat him, do it!\n"
				+ "You goal should be to kill him, because if you do, you'll be a hard one to fight! They'll call you a badarse!!"
				+ "\nYou can go:"
				+ "\nNorth"
				+ "\nEast\nWest\n";
		w4.addItem(new Potion("Sperm of Chewbecca", "Health Potion", 25, 800));
		w4.addItem(new Potion("Tangy Water", "Poison", -10, 100));
		w4.addItem(new Armor("Armani Space Suit", 30, 4000));
		w4.addItem(new Portal("Worm Hole", 200, "West", 407));
		w4.addCharacterToRoom(new GhostRider("Rider"));
		w4.addItem(new Inanimate("Head Of Fairy Godmother", 3000));
		w4.setGameText(n4s);

		// Room 5
		String n5s = "Woow! Did you fall into the time vortex? No you would have been dead in that case! You broke the barrier of the universe.\n"
				+ "All that can be said is: welcome to exospace. It's not your universe. So what do you plan to do now? You can take an exit.\n"
				+ "Also you can collect some weapons that are only found HERE! Yes, this is exospace kid! Beware of the creatures that can\n"
				+ "move across the universe. To get the key for the chest move to room 408."
				+ "\nYou can go:"
				+ "\nNorth\nSouth"
				+ "\nEast\nWest\n";
		w5.addItem(new Chest("Unusual Chest", 8000, 8746));
		w5.addItem(new Weapon("Vegeta's Acid", 40, 9000));
		w5.addItem(new Weapon("Claw of Pikachu", 30, 6000));
		w5.addItem(new Weapon("Nazi's Gas", 25, 5000));
		w5.addItem(new Key("Hairy Key", 9999));
		w5.addItem(new Inanimate("Ham Burger", 2000));
		w5.addCharacterToRoom(new NineTailedFox("Kyuubi"));
		w5.setGameText(n5s);

		// Room 6
		String n6s = "What is that? I sthat a flying cockroach? Cockroaches in the first room don't do anything, but here they do. Probably!\n"
				+ "And need some candy? Yeah? Okay, here is or was some heroin. Take if you don't have it already! If you are lucky,\n"
				+ "The taratunla won't kill you. Anyway whassup? Oh. There is seomthing that you could find only in exospace. Did you get it?\n"
				+ "Go on this room has not too much in it. But remember, EVERYBODY LIES. EVERYBODY DIES."
				+ "\nYou can go:"
				+ "\nSouth"
				+ "\nEast\nWest\n";
		w6.addItem(new Inanimate("Heroin", 10000));
		w6.addCharacterToRoom(new Hulk("The Incredible Hulk"));
		w6.addItem(new Armor("Stormtrooper's Suit", 40, 10000));
		w6.addItem(new Weapon("Old Laser", 15, 4000));
		w6.addItem(new Weapon("Getsug Tenshou", 30, 5000));
		w6.addItem(new Potion("Rice Water", "Disgusting Health Potion", 10, 5));
		w6.setGameText(n6s);

		// Room 7
		String n7s = "Look at that! Tanks and lasers! What the hell is going on? War? Damn! Run before you get caught in the crossfire!\n"
				+ "Or if you want to stay, pick up some grenades, you'll need them later! And if you have time, pick up something good too!\n"
				+ "And you seem hungry. You need some pasta? You'll be an idiot to stay be sitting in a battlefield and having pasta!!\n"
				+ "And what is Voldemort doing here?"
				+ "\nYou can go:"
				+ "\nNorth"
				+ "\nWest\n";
		w7.addItem(new Weapon("Grenades", 30, 4000));
		w7.addItem(new Weapon("Used Rocket Shells", 30, 3000));
		w7.addItem(new Potion("Pasta", "Healthy Food", 15, 20));
		w7.addCharacterToRoom(new HostileMOB("Crossfire Laser", 30, 0));
		w7.addCharacterToRoom(new HostileMOB("CrossFire Shells", 40, 0));
		w7.addCharacterToRoom(new Voldemort("Lord Voldemort"));
		w7.setGameText(n7s);

		// Room 8
		String n8s = "You move quickly. You trevelled to different planets at each step. Look kid. Let me tell you the truth.\n"
				+ "Each door is a worm hole. A tunnel. It is extremely dangerous to play in here. People hardly survive if they\n"
				+ "fall into the trap of this Universal Trapdoor. Now since you are here, be sure to survive. You won't like to die like idiots.\n"
				+ "Knights are all over the place. They travel in time. They are there to seek revenge. Fight them. If you are the chosen one that is.."
		+ "\nYou can go:"
		+ "\nNorth\nSouth"
		+ "\nEast\nWest\n";
		w8.addCharacterToRoom(new HeadlessKnight("Knight of Wales"));
		w8.addCharacterToRoom(new HeadlessKnight("Knight of Serbia"));
		w8.addItem(new Chest("Gringotts", 10000, 6656));
		w8.addItem(new Weapon("Fleas", 15, 1000));
		w8.addItem(new Weapon("Jigsaw's Helmet", 30, 5000));
		w8.addItem(new Inanimate("Turbo Charger", 3000));
		w8.addItem(new Potion("Tasty Sewage", "Health Potion", 20, 5));
		w8.setGameText(n8s);

		// Room 9
		String n9s = "The final room. Or universe? Remember the rules taught before? Haha! Transported evrywhere like a bitch! You sure are a sucker!\n"
				+ "Fight fight fight! Thirsty for blood! Hate evryone! Destroy everything you find! Seek revenge kid! From whoever who did that to you!\n"
				+ "You see the Death Star? Go and find him! Finish him because he is dangerous! Go! GO!"
				+ "\nYou can go:"
				+ "\nSouth"
				+ "\nWest\n";
		w9.addCharacterToRoom(new Vader("Darth Vader"));
		w9.addCharacterToRoom(new MightyScorpion("Deathly Stinger"));
		w9.addItem(new Weapon("Bill Gates' Money", 10, 10000));
		w9.addItem(new Potion("Orange Juice", "Health Potion", 10, 10));
		w9.addItem(new Weapon("Brass Knucks", 5, 500));
		w9.addItem(new Weapon("Rasengan", 35, 10000));
		w9.addItem(new Weapon("Chidori", 35, 10000));
		w9.addItem(new Weapon("Kamehameha", 35, 10000));
		w9.addItem(new Key("Unusual Key", 8746));
		w9.setGameText(n9s);

		roomMap.put(401, w1);
		roomMap.put(402, w2);
		roomMap.put(403, w3);
		roomMap.put(404, w4);
		roomMap.put(405, w5);
		roomMap.put(406, w6);
		roomMap.put(407, w7);
		roomMap.put(408, w8);
		roomMap.put(409, w9);
	}

	public HashMap<Integer, Room> getMap() {
		return roomMap;
	}
}
