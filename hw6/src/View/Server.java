/**
 * ChatServer will be set up to add and remove clients, as well as broadcast a
 * user message to other clients on the server
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.util.Vector;

import Characters.Character;
import Characters.PastorPete;
import Characters.Warrior;
import Items.Inanimate;
import Items.Item;
import Model.AccountCollection;
import Model.Command;
import Model.GetRequest;
import Model.GiveRequest;
import Model.Room;
import Model.SpeakCommand;

public class Server implements Runnable, Serializable {

	private static final long serialVersionUID = 9152455536874965439L;

	private ServerSocket myServerSocket;
	public static String baseDir = System.getProperty("user.dir")
			+ File.separator;

	public static final String fileNameWhereAccountsAreStored = baseDir
			+ "AccountCollection.object";
	// Collection of current clients connected to server
	private Vector<ClientThreadConnection> clients = new Vector<ClientThreadConnection>();
	public final static int PORT_NUMBER = 4030;
	private Character character = null;
	private AccountCollection acctColl = new AccountCollection();
	private Vector<GiveRequest> giveRequests = new Vector<GiveRequest>();
	private Vector<GetRequest> getRequests = new Vector<GetRequest>();
	private Command command;
	private boolean shouldShutDown = false;

	/**
	 * At the start, we need to set up a new server and start a new thread
	 */
	public static void main(String args[]) {
		Server server = new Server(PORT_NUMBER);
		Thread serverThread = new Thread(server);
		serverThread.start();
	}

	/**
	 * Set up a new server with the given port number, initialize a ghost client
	 * in case of zero users on server
	 *
	 * @param port
	 *            number we are connecting to
	 */
	public Server(int port) {

		try {
			// set up server socket
			myServerSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * When we run, we want the ChatServer to add new users
	 */
	@Override
	public void run() {

		restoreObjects();

		try {

			// go through the loop
			while (!shouldShutDown) {
				Socket intoServer = myServerSocket.accept();
				ClientThreadConnection aThreadForOneClient = new ClientThreadConnection(
						intoServer, this);
				// adds a client to collection when connection is accepted
				clients.add(aThreadForOneClient);
				Thread thread = new Thread(aThreadForOneClient);
				thread.start();
			}
		} catch (SocketException se) {

		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (myServerSocket != null)
					myServerSocket.close();
			} catch (IOException ioEx) {
			}

		}

	}

	public void saveObjects() {
		try {
			FileOutputStream outFile = new FileOutputStream(
					fileNameWhereAccountsAreStored);
			ObjectOutputStream outputStream = new ObjectOutputStream(outFile);
			outputStream.writeObject(acctColl);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void restoreObjects() {
		FileInputStream inFile = null;
		try {
			inFile = new FileInputStream(fileNameWhereAccountsAreStored);
			ObjectInputStream inputStream = new ObjectInputStream(inFile);
			try {
				acctColl = (AccountCollection) inputStream.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inFile != null)
				try {
					inFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	/**
	 * Sends out a message to all clients connected to server
	 *
	 * @param message
	 */
	public synchronized void broadcast(String message) {
		for (ClientThreadConnection c : clients) {
			c.sendMessage(message);
		}
	}

	// doesnt work right now
	// i dont know what you have to reference a room, im assuming you have a
	// room number

	/*
	 * Why dont we reference them by a String with a naming convention like
	 * <region>_<room number> ie north_01
	 */
	public synchronized void roomBroadcast(String user, String message) {
		character = acctColl.getCharacter(user);
		int roomNumber = character.getRoom();
		for (ClientThreadConnection c : clients) {
			Character thisChar = acctColl.getCharacter(c.getUserName());
			if (thisChar.getRoom() == roomNumber) {
				thisChar.sendMessage("### " + user + ": " + message);
			}
		}
		// done, you find all the accounts where the character is in a room
		// number, and send them all a message
	}

	public synchronized void notifyRoom(String userName, String message) {
		character = acctColl.getCharacter(userName);
		int roomNumber = character.getRoom();
		for (ClientThreadConnection c : clients) {
			Character thisChar = acctColl.getCharacter(c.getUserName());
			if (thisChar.getRoom() == roomNumber) {
				c.sendMessage(message);
			}
		}
	}

	public void shutDown(ClientThreadConnection c) {
		saveObjects();
		for (ClientThreadConnection d : clients) {
			if (d != c) {
				d.sendMessage("Server has shut down");
				d.quit();
			}
		}
		c.sendMessage("[Server Shut Down: All users disconnected]");
		c.quit();
		try {
			myServerSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		shouldShutDown = true;
	}

	public String tell(String user, String receiver, String message) {
		String result = "\nCould not send message to " + receiver;
		character = acctColl.getCharacter(user);
		for (ClientThreadConnection c : clients) {
			if (receiver.equals(c.getUserName())) {
				c.sendMessage("### [whisper] " + user + ": " + message);
				result = "\nMessage sent";
			}
		}
		return result;
	}

	public void speak(String user, String otherChar) {
		character = acctColl.getCharacter(user);
		Room current = character.getRoomObject();
		for (Character c : current.getRoomCharacters()) {
			if (otherChar.equals(c.getName().toLowerCase())) {
				command = new SpeakCommand(character, c);
				command.execute();
			}
		}
	}

	public Character setCharacterToStream(String userName,
			ObjectInputStream in, ObjectOutputStream out) {
		// stream is set in the character, this is somewhat better but how the
		// code is organized, game data is stored in the account yah i know, it
		// was frustrating.. oh well you can use it.
		character = acctColl.getCharacter(userName);
		character.setStreams(in, out);
		return character;
	}

	public void closeCharacterStream(String user) {
		Warrior character = (Warrior) acctColl.getCharacter(user);
		character.closeStreams();
	}

	/**
	 * Removes client after disconnecting from server
	 *
	 * @param c
	 */
	public void removeClient(ClientThreadConnection c) {
		clients.remove(c);
	}

	public boolean goTo(String user, String direction) {
		character = acctColl.getCharacter(user);
		int oldRoom = character.getRoom();
		if (!character.goTo(direction)) {
			return false;
		} else {
			for (ClientThreadConnection c : clients) {
				Character thisChar = acctColl.getCharacter(c.getUserName());
				if (character.getRoom() == thisChar.getRoom()
						&& !character.equals(thisChar))
					thisChar.sendMessage("[" + user + " has entered the room]");
				if (oldRoom == thisChar.getRoom()
						&& !character.equals(thisChar))
					thisChar.sendMessage("[" + user + " has left the room]");
			}
			lookAtRoom(user);
			return true;
		}
	}

	public void updateAcctColl(AccountCollection ac) {
		acctColl = ac;
	}

	public String updateUserStats(String user) {
		character = acctColl.getCharacter(user);
		return character.getUserStats();
	}

	public boolean isTakenName(String userName) {
		return acctColl.isTakenName(userName);
	}

	public String getItem(String user, String selection) {
		character = acctColl.getCharacter(user);
		if (character.checkItemInRoom(selection)) {
			notifyRoom(user, "[" + user + " has picked up " + selection + "]");
			return character.addItem(selection);
		} else
			return "No items to pick up";

	}

	public String dropItem(String user, String selection) {
		character = acctColl.getCharacter(user);
		if (character.getInventory().checkForItem(selection))
			return character.dropItem(selection);
		else
			return "No items to drop";
	}

	public String useItem(String user, String selection) {
		String result = "";
		character = acctColl.getCharacter(user);
		result = character.useItem(selection);
		return result;
	}

	public void equip(String userName, String item) {
		character = acctColl.getCharacter(userName);
		character.sendMessage(character.equip(item));
		character.sendMessage("*** " + character.getUserStats());
	}

	public void addAccount(String username, String password) {
		acctColl.add(username, password);
	}

	public boolean isAccountValid(String username, String password) {
		return acctColl.validUser(username, password);
	}

	public void lookAtRoom(String user) {
		character = acctColl.getCharacter(user);
		character.getRoomObject().updateGameText();

		// add clients in room
		String result = "";
		result += "\t##################################\n";
		result += "\t*       Users in the room        *\n";
		result += "\t##################################\n";

		for (ClientThreadConnection c : clients) {
			if (c.isValidAccount()) {
				Character temp = acctColl.getCharacter(c.getUserName());
				int tempRoom = temp.getRoom();
				if (tempRoom == character.getRoom()) {
					result += "\t*\t" + c.getUserName() + "\n";
				}
			}
		}
		character.sendMessage(character.getRoomOutput() + result);
	}

	// Target can be either another user, mob, or item
	public void lookAt(String user, String target) {

        Item item = null;
        String result = "\n";
        String objectType = "";
        boolean inRoom = false;
        Character mobAI = null;
        Room room = character.getRoomObject();
        character = acctColl.getCharacter(user);

        try {
            mobAI = room.getCharacter(target);
        } catch (Exception e){}

        try {
            if (character.getInventory().checkForItem(target)) {
                item = character.getInventory().get(target);
                inRoom = true;
            } else if (room.roomInventory().checkForItem(target)) {
                item = room.roomInventory().get(target);
            }

        } catch (Exception e){}


        if (item != null && mobAI == null) {
            objectType = item.getClass().getName().substring(6);
            if (inRoom) {
                result += item.display();
            } else {
                result += "\t|--------------------------------|\n\t";
                result += "|   (" + objectType + ")\n\t";
                result += "|    Name: " + item.getItemName() + "\n\t";
                result += "|--------------------------------|\n\n";
            }
        } else if (item == null && mobAI != null){
            result += mobAI.display();
        } else {
            result += target + " could not be found...";
        }
    	character.sendMessage(result);
	}

	public void displayInventory(String user) {
		character = acctColl.getCharacter(user);
		character.sendMessage(character.getInventory().listItemDetails());
	}

	public String listAllClients() {
		String result = "Users on Server\n";
		for (ClientThreadConnection c : clients) {
			result += c.getUserName() + "\n";
		}
		return result;
	}

	public void getScore(String user) {
		character = acctColl.getCharacter(user);
		character.sendMessage("\n" + character.getScore());
	}

	public void addGiveRequest(String user, String item, String target) {
		Character sender = acctColl.getAcct(user).getCh();
		try {
			Character receiver = acctColl.getAcct(target).getCh();
			Item tradeItem = sender.getInventory().get(item);
			if ((sender.getRoom() == receiver.getRoom()) && tradeItem != null) {
				giveRequests.add(new GiveRequest(sender, tradeItem, receiver));
				sender.sendMessage("Item request sent");
				receiver.sendMessage("You received an item request");
			} else
				sender.sendMessage("Player is not in the same room or player doesn't have item");
		} catch (Exception e) {
			sender.sendMessage("Invalid character name or User is not online");
		}
	}

	public void displayRequests(String userName) {
		Character userChar = acctColl.getAcct(userName).getCh();
		String haveRequests = new String();

		for (int i = 0; i < giveRequests.size(); i++) {
			if (userChar == giveRequests.get(i).getReceiver()) {
				haveRequests += "\n" + giveRequests.get(i).getDescription();
			}
		}
		for (int j = 0; j < getRequests.size(); j++) {
			if (userChar == getRequests.get(j).getSender()) {
				haveRequests += "\n" + getRequests.get(j).getDescription();
			}
		}
		if (!haveRequests.isEmpty())
			userChar.sendMessage(haveRequests);
		else
			userChar.sendMessage("You have no requests at this time");
	}

	public void acceptReceive(String send, String item, String receive) {
		Character receiver = acctColl.getAcct(receive).getCh();
		try {
			Character sender = acctColl.getAcct(send).getCh();
			Item tradeItem = sender.getInventory().get(item);
			if (sender.getRoom() == receiver.getRoom()) {
				for (int i = 0; i < giveRequests.size(); i++) {
					if (sender == giveRequests.get(i).getSender()
							&& receiver == giveRequests.get(i).getReceiver()
							&& tradeItem == giveRequests.get(i).getItem()) {
						receiver.getInventory().add(tradeItem);
						sender.getInventory().removeItem(tradeItem);
						receiver.sendMessage("You received " + item + " from "
								+ send + ". You place it into your inventory");
						sender.sendMessage("You gave " + receive + " " + item);
						giveRequests.remove(i);
						break;
					} else if (send.equalsIgnoreCase(giveRequests.get(i)
							.getSender().getName())
							&& receive.equalsIgnoreCase(giveRequests.get(i)
									.getReceiver().getName())
							&& item.equalsIgnoreCase(giveRequests.get(i)
									.getItem().getItemName())) {
						receiver.sendMessage(send + " no longer has " + item
								+ ", request removed");
						sender.sendMessage("You no longer have " + item + ". "
								+ item + " was not sent, request removed.");
						giveRequests.remove(i);
						break;
					}
				}
			} else
				receiver.sendMessage("You must be in the same room as " + send
						+ " to get " + item);
		} catch (Exception e) {
			receiver.sendMessage("Invalid character name or User is not logged on");
		}
	}

	public void addGetRequest(String user, String item, String target) {
		Character receiver = acctColl.getAcct(user).getCh();
		try {
			Character sender = acctColl.getAcct(target).getCh();
			Item tradeItem = sender.getInventory().get(item);
			if ((sender.getRoom() == receiver.getRoom()) && tradeItem != null) {
				getRequests.add(new GetRequest(receiver, tradeItem, sender));
				sender.sendMessage("You received an item request");
				receiver.sendMessage("Item request sent");
			} else
				receiver.sendMessage("Player is not in the same room or player doesn't have item");
		} catch (Exception e) {
			receiver.sendMessage("Invalid character name or User is not logged on");
		}
	}

	public void acceptToGive(String receive, String item, String send) {

		Character sender = acctColl.getAcct(send).getCh();
		try {
			Character receiver = acctColl.getAcct(receive).getCh();
			Item tradeItem = sender.getInventory().get(item);
			if (sender.getRoom() == receiver.getRoom()) {
				for (int i = 0; i < getRequests.size(); i++) {
					if (sender == getRequests.get(i).getSender()
							&& receiver == getRequests.get(i).getReceiver()
							&& tradeItem == getRequests.get(i).getItem()) {
						receiver.getInventory().add(tradeItem);
						sender.getInventory().removeItem(tradeItem);
						receiver.sendMessage("You received " + item + " from "
								+ send + ". You place it into your inventory");
						sender.sendMessage("You gave " + receive + " " + item);
						getRequests.remove(i);
						break;

					} else if (send.equalsIgnoreCase(getRequests.get(i)
							.getSender().getName())
							&& receive.equalsIgnoreCase(getRequests.get(i)
									.getReceiver().getName())
							&& item.equalsIgnoreCase(getRequests.get(i)
									.getItem().getItemName())) {
						receiver.sendMessage(send + " no longer has " + item
								+ ", request removed");
						sender.sendMessage("You no longer have " + item + ". "
								+ item + " was not sent, request removed.");
						getRequests.remove(i);
						break;
					}
				}
			} else
				sender.sendMessage("You must be in the same room as " + receive
						+ " to give " + item);
		} catch (Exception e) {
			sender.sendMessage("Invalid character name or User is not logged on");
		}
	}

	public void declineToGive(String receive, String item, String send) {
		Character sender = acctColl.getAcct(send).getCh();
		boolean requestFound = false;
		for (int i = 0; i < getRequests.size(); i++) {
			if (send.equalsIgnoreCase(getRequests.get(i).getSender().getName())
					&& receive.equalsIgnoreCase(getRequests.get(i)
							.getReceiver().getName())
					&& item.equalsIgnoreCase(getRequests.get(i).getItem()
							.getItemName())) {
				try {
					Character receiver = acctColl.getAcct(receive).getCh();
					receiver.sendMessage(send + " has declined to give " + item);
				} catch (Exception e) {
				}
				sender.sendMessage("You have declined to give " + receive + " "
						+ item);
				getRequests.remove(i);
				requestFound = true;
				break;
			}
		}

		if (requestFound == false)
			sender.sendMessage("Request not found");
	}

	public void declineItem(String send, String item, String receive) {
		Character receiver = acctColl.getAcct(receive).getCh();
		boolean requestFound = false;

		for (int i = 0; i < giveRequests.size(); i++) {
			if (send.equalsIgnoreCase(giveRequests.get(i).getSender().getName())
					&& receive.equalsIgnoreCase(giveRequests.get(i)
							.getReceiver().getName())
					&& item.equalsIgnoreCase(giveRequests.get(i).getItem()
							.getItemName())) {
				receiver.sendMessage("You have declined to receive " + item
						+ " from " + send);
				try {
					Character sender = acctColl.getAcct(send).getCh();
					sender.sendMessage(receive + " has declined to receive "
							+ item);
				} catch (Exception e) {
				}
				giveRequests.remove(i);
				requestFound = true;
				break;
			}
		}

		if (requestFound == false)
			receiver.sendMessage("Request not found");
	}

	public String fight(String initiator, String opponent) {
		String fightReport = "";
		Random random = new Random();
		Character player = acctColl.getCharacter(initiator);
		Character mob = null;
		try {
			Room current = player.getRoomObject();
			for (Character c : current.getRoomCharacters()) {
				if (opponent.equals(c.getName().toLowerCase())) {
					mob = c;
					break;
				}
			}
			while (player.isAlive() && mob.isAlive()) {
				if (player == mob) {
					player.sendMessage("Stupid, you shouldn't fight yourself. You aren't Tyler Durden");
					break;
				}
				if (mob instanceof PastorPete) {
					player.sendMessage("You cannot attack a pastor!");
					break;
				}
				// If player's health is 50% or less,
				if (player.getHealth() <= (player.getMaxHealth() * .5)) {
					if (player.getInventory().hasPotion()) {
						String itemName = player.getInventory()
								.getFirstPotionName();
						useItem(initiator, itemName);
						fightReport += "\nYou used " + itemName + ".";
					}
				}

				// if player's health is 25% or less...
				if (player.getHealth() <= (player.getMaxHealth() * .25)) {

					// see if they will run like a bitch
					if (random.nextInt() % 4 == 0) {
						fightReport += "\nYou ran away like a little piglet from "
								+ opponent;
						notifyRoom(initiator, "[" + initiator
								+ " ran away from " + opponent + "]");
						break;
					}
				}

				if (random.nextInt() % 2 == 0) {
					int attack = mob.attack();
					player.decreaseHealth(attack);
					fightReport += "\n" + opponent + " attacked you for "
							+ attack;
				} else {
					int attack = player.attack();
					mob.decreaseHealth(attack);
					fightReport += "\nYou attacked " + opponent + " for "
							+ attack;
				}

				if (!mob.isAlive()) { // We should only need to call
										// getInventory once...
					mob.startRespawn();
					player.gainExperience(mob.getMaxHealth() * 2);
					mob.setIsAlive(false);
					notifyRoom(initiator, "[" + initiator + " has killed "
							+ opponent + "]");
					try {
						for (int i = 0; i < mob.getInventory().size(); i++) {
							player.getInventory().add(
									mob.getInventory().getInventory().get(i));
							fightReport += "\nYou looted "
									+ mob.getInventory().getInventory().get(i)
											.getItemName();
							notifyRoom(initiator, "["
									+ initiator
									+ " has looted "
									+ mob.getInventory().getInventory().get(i)
											.getItemName() + "]");
						}
					} catch (Exception e) {
						fightReport += "\n" + opponent
								+ " does not have any items.";
					}

				}
			}
		} catch (Exception e) {
			fightReport += "\nCharacter not in room or Invalid name.";
		}

		if (!player.isAlive()) {
			notifyRoom(initiator, "[" + initiator + " has been killed by "
					+ opponent + "]");
			player.setHealth(player.getMaxHealth());
			player.setIsAlive(true);
			player.getRoomObject().removeCharacter(player);
			player.setRoomNumber(0);
			player.getRoomObject().addCharacterToRoom(player);
			fightReport += "\nYou died, spawning in lobby";
		}

		try {
			if (!mob.isAlive())
				fightReport += "\n" + opponent + " is dead.";
		} catch (Exception e) {
		}

		player.sendMessage("*** " + player.getUserStats());
		return fightReport;
	}

	public void pressButton(String userName) {
		Character character = acctColl.getCharacter(userName);
		Room room = character.getRoomObject();
		if (room.hasButton()) {
			character.decreaseHealth(1);
			character.sendMessage("This button shocked you, Owww");
		} else
			character.sendMessage("This room doesn't have a button");
	}

	public void pullSwitch(String userName) {
		Character character = acctColl.getCharacter(userName);
		Room room = character.getRoomObject();
		if (room.hasSwitch()) {
			character.getInventory().add(new Inanimate("Boob", 1));
			character.sendMessage("You got a boob");
		} else
			character.sendMessage("This room doesn't have a switch");
	}

	public void rest(String userName) {
		saveObjects();
		Character character = acctColl.getCharacter(userName);
		Room room = character.getRoomObject();
		if (room.hasBed()) {
			character.addHealth(character.getMaxHealth());
			character.sendMessage("You slept like baby, you baby");
		} else
			character
					.sendMessage("There isn't a bed here, you aren't an animal. Find a bed.");
	}

	public void saveGame() {
		saveObjects();
	}

}