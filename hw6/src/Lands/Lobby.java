package Lands;

import java.io.Serializable;
import java.util.HashMap;

import Characters.HostileMOB;
import Characters.MOB;
import Characters.SellerMOB;
import Items.Chest;
import Items.Inanimate;
import Items.Key;
import Items.Potion;
import Items.Weapon;
import Model.Room;

public class Lobby implements Serializable {

	private HashMap<Integer, Room> roomMap;
	private boolean isActive;

	public Lobby() {
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

		// set up first 5 rooms, including main Lobby, north Lobby, east Lobby,
		// south Lobby, west Lobby
		Room mainLobby, northLobby, eastLobby, southLobby, westLobby;

		// default lobby
		mainLobby = new Room("Lobby", 0, 100, 200, 300, 400);
		mainLobby.addItem(new Inanimate("Ammo box", 5));
		mainLobby.addItem(new Inanimate("Broken crate", 50));
		mainLobby.addItem(new Inanimate("Gary the homeless", -1));
		mainLobby.addItem(new Inanimate("Rock", 0));
		mainLobby.addItem(new Inanimate("Gold Purse", 25));
		mainLobby.addItem(new Potion("Coca-cola", "Drink", 10, 5));
		mainLobby.addItem(new Potion("Glass of water", "Drink", -2, 0));
		mainLobby.addItem(new Weapon("Wooden spoon", 10, 5));
		mainLobby.addItem(new Key("Shiny Red Key", 10404));
		mainLobby.addCharacterToRoom(new MOB("Sir Arthur", 1, 25));
		String lobby = "";
		lobby += "||*||************************************************************************||*||\n";
		lobby += "||*|| In the shadows of a destructive war, villagers meet to stay safe from  ||*||\n";
		lobby += "||*|| the dangers of the world.                                              ||*||\n";
		lobby += "||*||                                                                        ||*||\n";
		lobby += "||*|| To move, use the command 'go ' and the direction you wish to travel.   ||*||\n";
		lobby += "||*||                                                                        ||*||\n";
		lobby += "||*|| There appears to be some music coming from the north...                ||*||\n";
		lobby += "||*||                                                                        ||*||\n";
		lobby += "||*||************************************************************************||*||\n";
		lobby += "||*||North: North Gate\n";
		lobby += "||*||South: South Gate\n";
		lobby += "||*||West: West Gate\n";
		lobby += "||*||East: East Gate\n";
		mainLobby.setGameText(lobby);

		// default north lobby
		northLobby = new Room("Lobby", 100, 108, -1, 0, -1);
		northLobby.addCharacterToRoom(new HostileMOB("No-go", 40, 20));
		northLobby.addItem(new Inanimate("Dead warrior", 30));
		northLobby.addItem(new Chest("Old treasure chest", 1000, 10000));
		String nLobby = "";
		nLobby += "||=============================== North City Gate ==============================||\n";
		nLobby += "|| The smells of the North make your mouth water, the thought of food and drink ||\n";
		nLobby += "|| cross your mind. There is only two choices from here, either follow your     ||\n";
		nLobby += "|| stomach or go back the way you came.                                         ||\n";
		nLobby += "||                                                                              ||\n";
		nLobby += "||                          North: City Center                                  ||\n";
		nLobby += "||                          South: Unclaimed Land                               ||\n";
		nLobby += "||                                                                              ||\n";
		nLobby += "||==============================================================================||\n";
		nLobby += "                      ****    Welcome to the North    ****\n";
		nLobby += " To enter the north land, go north\n";
		nLobby += " South: Main Lobby\n";
		northLobby.setGameText(nLobby);

		// default east lobby
		eastLobby = new Room("Lobby", 200, -1, 208, -1, 0);
		eastLobby.addCharacterToRoom(new SellerMOB("Shady Gravy"));

		String eLobby = "";
        eLobby += "||================================ East City Gate ==============================||\n";
        eLobby += "||                                                                              ||\n";
        eLobby += "||==============================================================================||\n";
        eLobby += "                      ****    Welcome to the East    ****\n";
        eLobby += "                              West: Main Lobby \n";
        eLobby += "                              East: Bob's Cliff \n";
		eastLobby.setGameText(eLobby);

		// default south lobby
		southLobby = new Room("Lobby", 300, 0, -1, 308, -1);
		String sLobby = "";
		sLobby += "||=============================== South City Gate ==============================||\n";
		sLobby += "|| Despite its immense size, the South is mostly uninhabited but for a few      ||\n";
		sLobby += "|| nomadic tribes that scrape out a meager existence in the immense desert that ||\n";
		sLobby += "|| is much of the South. It is rumored that there once was a great civilization ||\n";
		sLobby += "|| that is now buried under the sand. Adventurers speak of ruins from this long ||\n";
		sLobby += "|| forgotten civilization seemingly appear from the desert filled with riches   ||\n";
		sLobby += "|| beyond their wildest dreams, others report horrors the likes of which        ||\n";
		sLobby += "|| nightmares are made, and others still are never heard from again. One thing  ||\n";
		sLobby += "|| is for certain...                                                            ||\n";
		sLobby += "||                                                                              ||\n";
		sLobby += "||  Nothing is as it seems here and there are worse ways to die than by thirst  ||\n";
		sLobby += "||                                                                              ||\n";
		sLobby += "||          So grab your sword, strap on your armor, and march forward!         ||\n";
		sLobby += "||                    Or go back the way you came you sniveling cur!            ||\n";
		sLobby += "||==============================================================================||\n";
		sLobby += "                      ****    Welcome to the South    ****\n";
		sLobby += "                              To enter The Bazar < go south >\n";
		sLobby += "                        		 Return to Main Lobby, go north\n";

		southLobby.setGameText(sLobby);

		// default west lobby
		westLobby = new Room("Lobby", 400, -1, 0, -1, 408);
		String wLobby = "";
		wLobby += "||================================ West City Gate ==============================||\n";
		wLobby += "|| As you cross the threshold of the West City Gate, you feel a sudden chill in ||\n";
		wLobby += "|| the air and a shudder runs down your spine. The chill only grows worse the   ||\n";
		wLobby += "|| closer you approach the threshold. Could it be that the West is simply       ||\n";
		wLobby += "|| colder? Or could it be something to do with the West's more commonly known   ||\n";
		wLobby += "|| name...                                                                      ||\n";
		wLobby += "||                                                                              ||\n";
		wLobby += "||            The land of poor low level character survival rate...             ||\n";
		wLobby += "||                                                                              ||\n";
		wLobby += "||          So grab your sword, strap on your armor, and march forward!         ||\n";
		wLobby += "||                    Or go back the way you came you sniveling cur!            ||\n";
		wLobby += "||==============================================================================||\n";
		wLobby += "                      ****    Welcome to the West    ****\n";
        wLobby += "                              East: Main Lobby \n";
        wLobby += "                              West: The land of the west \n";

		westLobby.setGameText(wLobby);

		// Complete it according to Room's syntax.
		roomMap.put(0, mainLobby);
		roomMap.put(100, northLobby);
		roomMap.put(200, eastLobby);
		roomMap.put(300, southLobby);
		roomMap.put(400, westLobby);
	}

	public HashMap<Integer, Room> getMap() {
		return roomMap;
	}
}
