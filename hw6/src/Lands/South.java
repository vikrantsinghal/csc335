/**
 * It defines the Southern region of the game world map. <Item>s,
 * <Character>s, and room descriptions are included here.
 *
 * Is a CRAZY place that makes no sense and is seemingly random.
 * It intentionally leaves <Room> 307 inaccessable from typical means
 * of in-game movement. Depends on a <Portal> to get there, which is
 * a randon room selection, so the chances are relatively low.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Lands;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

import Items.*;
import Model.Room;
import Characters.*;

public class South implements Serializable {

	private HashMap<Integer, Room> roomMap;
	private boolean isActive;

	public South() {
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

        Random rg = new Random();
		// set up the north land
		Room s1, s2, s3, s4, s5, s6, s7, s8, s9;
                            // Room, N,  E,  S,  W
		s1 = new Room("South", 301, 300, 305, 302, 308);
        s2 = new Room("South", 302, 301, 306, -1, 309);
		s3 = new Room("South", 303, 308, 304, 309, -1);
        s4 = new Room("South", 304, 305, -1, 306, 303);
		s5 = new Room("South", 305, -1, -1, 304, 301);
        s6 = new Room("South", 306, 309, 305, 303, -1);
        s7 = new Room("South", 307, 301, 304, 302, 309);
        s8 = new Room("South", 308, -1, 301, 303, -1);
        s9 = new Room("South", 309, 303, 302, -1, -1);

        String n1s = "\n\n";
        n1s += "||========================== South Room 1: The Bazaar ==========================||\n";
        n1s += "||               Feast your eyes on the wonders of the South!                   ||\n";
        n1s += "||                                                                              ||\n";
        n1s += "||                 Everything you could ever want is here!                      ||\n";
        n1s += "||          Weapons, armor, potions, PORTALS, You want it, we got it!           ||\n";
        n1s += "||              Only the finest equipment the South has to offer!               ||\n";
        n1s += "||                                                                              ||\n";
        n1s += "||==============================================================================||\n";
        n1s += "North: To leave this place\n";
        SellerMOB barak = new SellerMOB("Barak");
        barak.addGold(rg.nextInt(100000));
        barak.getInventory().add_random_item(rg.nextInt(30));
        s1.addCharacterToRoom(barak);
        s1.roomInventory().add_random_item(rg.nextInt(10));
        s1.addItem(new Portal("Glittering Stone", 10));
        s1.setGameText(n1s);

        String n2s = "\n\n";
        n2s += "||========================== South Room 2: Supply Tent =========================||\n";
        n2s += "||  The smell of smoke, sex, and blood is thick in the air within this tent...  ||\n";
        n2s += "||                                                                              ||\n";
        n2s += "|| Be careful, there is no telling who, or what might be in here, and it doesnt ||\n";
        n2s += "|| look like it sells the kind of 'goods' your looking for...unless your into   ||\n";
        n2s += "||                            that sort of thing...                             ||\n";
        n2s += "||                                                                              ||\n";
        n2s += "||==============================================================================||\n";

        s2.roomInventory().add_random_item(rg.nextInt(10));
        s2.addCharacterToRoom(new MOB("A Strange Patron", rg.nextInt(50), rg.nextInt(50)));
        s2.addCharacterToRoom(new HostileMOB("Creepy Guy", rg.nextInt(150), rg.nextInt(50)));
        s2.addCharacterToRoom(new NaughtyMOB("Screaming child"));
        s2.addCharacterToRoom(new HostileMOB("Drunken Fool", rg.nextInt(200), rg.nextInt(50)));
		s2.setGameText(n2s);

		String n3s = "\n\n";
        n3s += "||========================== South room 3: Dirty Alley =========================||\n";
        n3s += "|| Its not the trash thats strewn about that really gets to you, its the smell  ||\n";
        n3s += "|| and the fact that the whores and vagrants here seem to be enjoying the place ||\n";
        n3s += "|| just fine...                                                                 ||\n";
        n3s += "||                                                                              ||\n";
        n3s += "|| Maybe now would be a good time to, you know, LEAVE!! Unless of your...       ||\n";
        n3s += "||                                 Chicken                                      ||\n";
        n3s += "||==============================================================================||\n";
        s3.addCharacterToRoom(new NaughtyMOB("Mary Jane"));
		s3.roomInventory().add_random_item(rg.nextInt(10));
        s3.setGameText(n3s);

        String n4s = "\n\n";
        n4s += "||===================== South Room 4: The Tropical Forest ======================||\n";
        n4s += "||        The air is humid and thick making it difficult to breathe.            ||\n";
        n4s += "||                                                                              ||\n";
        n4s += "||  Does it seem odd to you that you were just in an arid desert as far as the  ||\n";
        n4s += "||            eye could see? Where the hell did this forest come from...        ||\n";
        n4s += "||  Oh well, Im sure there is a proper explanation for this...just be careful  ||\n";
        n4s += "||                there are strange critters running about.                     ||\n";
        n4s += "||                                                                              ||\n";
        n4s += "||==============================================================================||\n";
        s4.addCharacterToRoom(new NineTailedFox("Vulpix"));
        s4.addCharacterToRoom(new Hulk("Tree-Breaker"));
        s4.addCharacterToRoom(new Tarantula("Harry Leggs"));
		s4.roomInventory().add_random_item(rg.nextInt(10));
        s4.setGameText(n4s);

        String n5s = "\n\n";
        n5s += "||========================= South Room 5: Winter Tundra ========================||\n";
        n5s += "||The wind wipes at your face as wicked cold chills your to the core. Why didnt ||\n";
        n5s += "||  you bring a jacket? It would be a shame if you got sick now this far ahead  ||\n";
        n5s += "|| dont you think? I guess its not necessary to mention that you are once again ||\n";
        n5s += "||    in a Winter Tundra which was supposed to be an arid desert... by the way  ||\n";
        n5s += "||         maybe you should leave that Mighty Scorpion alone for now...Ok?      ||\n";
        n5s += "||                                                                              ||\n";
        n5s += "||==============================================================================||\n";
        s5.addItem(new Key("Mom's Key", 112112));
        s5.addCharacterToRoom(new HostileMOB("Your Mom", rg.nextInt(150), rg.nextInt(150)));
        s5.setGameText(n5s);

        String n6s = "\n\n";
        n6s += "||====================== South Room 6: Raging Inferno ==========================||\n";
        n6s += "|| Were having a Heat Wave! A tropical Heat Wave! The temperature is rising it  ||\n";
        n6s += "||  I suppose its not surprising considering we are in a desert...were having a ||\n";
        n6s += "||                             HEAT WAVE  AHHHHHHHH                             ||\n";
        n6s += "||==============================================================================||\n";
		s6.roomInventory().add_random_item(rg.nextInt(10));
        s6.addItem(new Chest("Mom's box", 555, 112112));
        s6.setGameText(n6s);

        String n7s = "\n\n";
        n7s += "||============================ South Room 7: Madness ===========================||\n";
        n7s += "||  Against all odds, you have found the room of madness, if you leave now you  ||\n";
        n7s += "||  may never find your way back again...this is a place of madness after all   ||\n";
        n7s += "||                                                                              ||\n";
        n7s += "||  The great treasure of this strange desert is before you, but you must first ||\n";
        n7s += "||  defeat its guardian ... if you dare ... in single combat. Should you be     ||\n";
        n7s += "||  deemed worthy, you will surely be rewarded with the God's favor, and their  ||\n";
        n7s += "||                                     Prize                                    ||\n";
        n7s += "||                                                                              ||\n";
        n7s += "||  They say madness is a symbol of the god's favor...tell me something, do the ||\n";
        n7s += "||                                Gods favor you?                               ||\n";
        n7s += "||==============================================================================||\n";
        s7.addItem(new Inanimate("Immaculate Boobies", 99999999));
        s7.addCharacterToRoom(new HostileMOB("Blue-Footed Boob", 99999, 1500));
		s7.roomInventory().add_random_item(rg.nextInt(10));
        s7.setGameText(n7s);

        String n8s = "\n\n";
        n8s += "||======================== South Room 8: Misty Mountaintop =====================||\n";
        n8s += "||           You find yourself high above the clouds atop a misty mountain      ||\n";
        n8s += "|| How you got here is beyond you at this point, werent you just in the desert? ||\n";
        n8s += "|| One minute you can be trudging through trash and refuse, the next your on    ||\n";
        n8s += "|| of a mountain! Something is fishy around here...what strange magic has beset ||\n";
        n8s += "||   this place.....                                                            ||\n";
        n8s += "||                                                                              ||\n";
        n8s += "||==============================================================================||\n";
        s8.addCharacterToRoom(new MOB("Angry Caterpillar Monkey", 1000, 2));
        s8.addCharacterToRoom(new MOB("Ugly Harpy", 100, 30));
        s8.addCharacterToRoom(new MOB("Ferocious Butterfly", 250, 50));
		s8.roomInventory().add_random_item(rg.nextInt(10));
        s8.setGameText(n8s);

        String n9s = "\n\n";
        n9s += "||========================== South Room 9: Arid Desert =========================||\n";
        n9s += "||                              This is the desert                              ||\n";
        n9s += "||                          There is nothing out here.                          ||\n";
        n9s += "||                                  Nothing.                                    ||\n";
        n9s += "||==============================================================================||\n";
        s9.setGameText(n9s);

		roomMap.put(301, s1);
		roomMap.put(302, s2);
		roomMap.put(303, s3);
		roomMap.put(304, s4);
		roomMap.put(305, s5);
		roomMap.put(306, s6);
		roomMap.put(307, s7);
		roomMap.put(308, s8);
		roomMap.put(309, s9);
	}

	public HashMap<Integer, Room> getMap() {
		return roomMap;
	}
}
