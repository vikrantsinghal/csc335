/**
 * ClientConnectionThread will connect a new user to a server and let other
 * users know who has logged on, also will quit user session and log off
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import Characters.Character;

public class ClientThreadConnection implements Runnable, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2612650701042524896L;
	private boolean validAccount;
	// set up the network components
	private Socket socketFromServer;
	private Server server;

	// clients account details
	private String userName;

	// the write to objects
	private ObjectInputStream readFromClient;
	private ObjectOutputStream writeToClient;

	/**
	 * Initialize a thread connection for the new user
	 *
	 * @param socketFromServer
	 *            is a local location
	 * @param server
	 *            with other users
	 */
	// This is where the socket is given to the thread
	public ClientThreadConnection(Socket socketFromServer, Server server) {
		this.socketFromServer = socketFromServer;
		this.server = server;
	}

	@Override
	// This is the method is that is run, it is equivalent to the main()
	// function in the main thread
	public void run() {
		try {
			// grab writing objects
			// we are creating a reference to the inputstream, where we will be
			// listening to the users input
			readFromClient = new ObjectInputStream(
					socketFromServer.getInputStream());
			// we are creating a reference to the output stream, where we will
			// be writing to the client.
			writeToClient = new ObjectOutputStream(
					socketFromServer.getOutputStream());
		} catch (IOException e) {
			// error
			System.out
					.println("Exception thrown while obtaining input & output streams");
			e.printStackTrace();
			return;
		}

		Character character = null;
		do {
			// this will start the authentication process, where the thread will
			// be in charge of making sure we have a valid account
			try {
				writeToClient
						.writeObject("Do you want to Login or create new account? login/new/quit");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try {
				String input;
				String input2;
				try {
					input = (String) readFromClient.readObject();
					if (input.equalsIgnoreCase("login")) {
						writeToClient.writeObject("What is your username?");
						input = (String) readFromClient.readObject();
						userName = input;
						writeToClient.writeObject("What is your password?");
						input2 = (String) readFromClient.readObject();

						// this checks for the user's account
						validAccount = server.isAccountValid(input, input2);
						if (!validAccount)
							// if the user credentials are invalid, indicate to
							// the user
							writeToClient
									.writeObject("Invalid username and password.");
						else {
							// server.broadcast looks like it will go through
							// all the active output streams and write them a
							// message
							server.broadcast("[" + userName + " has connected]");
							// if the user credentials are valid, use the output
							// stream to send a message to the client

							// at this point, we tell the server we have a valid
							// user connected to a hold a reference to the new
							// connection (probably to allow broadcasting and
							// trading)
							character = server.setCharacterToStream(userName,
									readFromClient, writeToClient);
							String userStats = server.updateUserStats(userName);
							writeToClient.writeObject("*** " + userStats);
							writeToClient
									.writeObject("\nFor help with basic commands, type 'help' and press enter\n");
							server.lookAtRoom(userName);

						}
					} else if (input.equalsIgnoreCase("new")) {
						writeToClient
								.writeObject("What do want your username to be?");
						input = (String) readFromClient.readObject();
						while (server.isTakenName(input)) {
							writeToClient
									.writeObject(input
											+ " is already taken, enter a different name.");
							input = (String) readFromClient.readObject();
						}
						userName = input;
						writeToClient
								.writeObject("What do want your password to be?");
						input2 = (String) readFromClient.readObject();
						server.addAccount(input, input2);
						writeToClient.writeObject("Welcome " + input
								+ ", to the world of warcraft\n");
						server.broadcast("[" + userName + " has connected]");
						validAccount = true;
						// this might return the corresponding account to use
						// after
						character = server.setCharacterToStream(userName,
								readFromClient, writeToClient);
						String userStats = server.updateUserStats(userName);
						writeToClient.writeObject("*** " + userStats);
						writeToClient
								.writeObject("\nFor help with basic commands, type 'help' and press enter\n");
						server.lookAtRoom(userName);
					} else if (input.equalsIgnoreCase("quit")) {
						quit();
						break;
					}

				} catch (ClassNotFoundException e) {
					e.printStackTrace();

				}
			} catch (IOException o) {
			}
		} while (!validAccount);

		// we should be garanteed a charcter by this line of code so are you
		// using character or account to pass stream? whatever, the character
		// since its there

		// at this point, we should only be using the account for communication,
		// the program should set the input and output stream in the account,
		// and when you want to communicate you would do something like
		// account.Write(message)
		// set up user variables
		String messageFromClient = null;
		boolean wantToAStayConnected = true;

		while (wantToAStayConnected) {
			server.saveGame();
			try {
				// grab the message
				// when calling readObject, this method will block until a
				// message is received.
				messageFromClient = (String) readFromClient.readObject();

				messageFromClient.toLowerCase();
				// wont get here until message is received
			} catch (IOException e1) {
				// error
				// System.out
				// .println("IOException in ServerThread.run when reading from client");
				return;
			} catch (ClassNotFoundException e1) {
				// error
				System.out
						.println("ClassCastException in ServerThread.run casting object from client to String");
				e1.printStackTrace();
			}

			// we are garanteed there is a message here
			try {

				// stop commands where a global message is sent
				if (messageFromClient.length() >= 4
						&& messageFromClient.substring(0, 4).equalsIgnoreCase(
								"ooc ")) {
					try {
						String clientMessage = messageFromClient.substring(4)
								.trim();
						// broadcasts to all users the user and message sent

						server.broadcast("### [global] " + userName + ": "
								+ clientMessage);
					} catch (Exception e) {
						writeToClient.writeObject("Invalid ooc command");
					}
				}

				// stop commands where a local message is sent
				else if (messageFromClient.length() >= 4
						&& messageFromClient.substring(0, 4).equalsIgnoreCase(
								"say ")) {
					String clientMessage = messageFromClient.substring(4);
					// broadcast only to local users

					server.roomBroadcast(userName, clientMessage);
				}



				// stop commands where a user wants to tell another user a
				// message
				else if (messageFromClient.length() >= 5
						&& messageFromClient.substring(0, 5).equalsIgnoreCase(
								"tell ")) {
					String[] clientMessage = messageFromClient.split(" ");
					try {
						String receiver = clientMessage[1].toLowerCase();
						String message = "";
						for (int i = 2; i < clientMessage.length; i++) {
							message += clientMessage[i] + " ";
						}
						String result = server
								.tell(userName, receiver, message);
						writeToClient.writeObject(result);
					} catch (Exception e) {
						writeToClient.writeObject("Invalid tell command");
					}
				}

				// stop commands where user wants to move
				else if (messageFromClient.length() >= 3
						&& messageFromClient.substring(0, 3).equalsIgnoreCase(
								"go ")) {
					String clientMessage = messageFromClient.substring(3)
							.trim();
					try {
						// now we are going to go somewhere
						if (clientMessage.equals("north"))
							if (!server.goTo(userName, "north"))
								writeToClient
										.writeObject("Can't go that way\n");

						if (clientMessage.equals("east"))
							if (!server.goTo(userName, "east"))
								writeToClient
										.writeObject("Can't go that way\n");

						if (clientMessage.equals("south"))
							if (!server.goTo(userName, "south"))
								writeToClient
										.writeObject("Can't go that way\n");

						if (clientMessage.equals("west"))
							if (!server.goTo(userName, "west"))
								writeToClient
										.writeObject("Can't go that way\n");
					} catch (Exception e) {
						writeToClient.writeObject("Invalid go command");
					}
				}

				// stop commands where user wants to get item
				else if (messageFromClient.length() >= 7
						&& messageFromClient.substring(0, 7).equalsIgnoreCase(
								"pickup ")) {
					try {
						String selection = messageFromClient.substring(7)
								.trim();
						String result = server.getItem(userName, selection);
						writeToClient.writeObject("\n" + result);
					} catch (Exception e) {
						writeToClient.writeObject("Invalid pickup command");
					}
				}

				// stop commands where user wants to use item
				else if (messageFromClient.length() >= 4
						&& messageFromClient.substring(0, 4).equalsIgnoreCase(
								"use ")) {
					try {
						String selection = messageFromClient.substring(4)
								.trim();
						String result = server.useItem(userName, selection);
						writeToClient.writeObject("\n" + result);
					} catch (Exception e) {
						writeToClient.writeObject("Invalid use command");
					}
				}

				// stop commands where user wants to drop an item
				else if (messageFromClient.length() >= 5
						&& messageFromClient.substring(0, 5).equalsIgnoreCase(
								"drop ")) {
					try {
						String selection = messageFromClient.substring(5)
								.trim();
						String result = server.dropItem(userName, selection);
						writeToClient.writeObject("\n" + result);
					} catch (Exception e) {
						writeToClient.writeObject("Invalid drop command");
					}

				}

				// stop commands where user wants to speak to character
				else if (messageFromClient.length() >= 6
						&& messageFromClient.substring(0, 6).equalsIgnoreCase(
								"speak ")) {
					try {
						String otherChar = messageFromClient.substring(6)
								.toLowerCase().trim();
						server.speak(userName, otherChar);
					} catch (Exception e) {
						writeToClient.writeObject("Invalid speak command");
					}
				}

				// stop commands where user wants to equip an item
				else if (messageFromClient.length() >= 6
						&& messageFromClient.substring(0, 6).equalsIgnoreCase(
								"equip ")) {
					String item = messageFromClient.substring(6).toLowerCase()
							.trim();
					server.equip(userName, item);
				}

				// stop commands where user wants to attack

				// stop commands where user wants to see everyone on server
				else if (messageFromClient.equalsIgnoreCase("who")) {
					String result = server.listAllClients();
					writeToClient.writeObject("\n" + result);
				}

				// stop commands where user wants to look at room
				else if (messageFromClient.equalsIgnoreCase("look")) {
					server.lookAtRoom(userName);
				}

				else if (messageFromClient.length() >= 7
						&& messageFromClient.substring(0, 7).equalsIgnoreCase(
								"lookat ")) {
					String item = messageFromClient.substring(7).toLowerCase()
							.trim();
					server.lookAt(userName, item);
				}

				// stop commands where user wants to see score
				else if (messageFromClient.equalsIgnoreCase("score")) {
					server.getScore(userName);
				}

				// stop commands where user wants to see inventory
				else if (messageFromClient.equalsIgnoreCase("inventory")) {
					server.displayInventory(userName);
				}

				else if (messageFromClient.equalsIgnoreCase("push button")) {
					server.pressButton(userName);
				}

				else if (messageFromClient.equalsIgnoreCase("pull switch")) {
					server.pullSwitch(userName);
				}

				else if (messageFromClient.equalsIgnoreCase("rest")) {
					server.rest(userName);
				}

				// stop commands where user needs help or wants to see commands
				else if (messageFromClient.equalsIgnoreCase("commands")
						|| messageFromClient.equalsIgnoreCase("help")) {
					String commands = "\nCommands/Help\n\n";
					commands += "'ooc ' + message to all users\n";
					commands += "'say ' + message to say to users in current room\n";
					commands += "'tell ' + <target user> + message you want to send\n";
					commands += "'speak ' + <target character> you want to speak to\n";
					commands += "'fight ' + <target character> you want to fight with\n";
					commands += "'go ' + direction to travel, north, east, south, or west\n";
					commands += "'pickup ' + item you want to pick up\n";
					commands += "'use ' + item from your inventory\n";
					commands += "'drop ' + item from your inventory\n";
					commands += "'equip ' + item to equip to character\n";
					commands += "'get ' + <target user> + item you want to get\n";
					commands += "'give ' + <target user> + item to give away\n";
					commands += "'accepttogive ' + <target user> + item that you are giving\n";
					commands += "'declinetogive' + <target user> + item that you refuse to give\n";
					commands += "'acceptitem ' + <target user> + item to receive\n";
					commands += "'declineitem' + <target user> + item refused to receive\n";
					commands += "'requests' shows all your current item requests\n";
					commands += "'look' at the current room\n";
					commands += "'lookat' + <target item> + you want more details about\n";
					commands += "'inventory' displays your inventory\n";
					commands += "'score' displays your status as a player\n";
					commands += "'who' displays all users on the server\n";
					commands += "'quit' closes user session\n";
					writeToClient.writeObject(commands);
				}

				else if (messageFromClient.length() >= 5
						&& messageFromClient.substring(0, 5).equalsIgnoreCase(
								"give ")) {
					String[] clientMessage = messageFromClient.split(" ");
					try {
						String item = "";
						for (int i = 2; i < clientMessage.length; i++) {
							item += clientMessage[i] + " ";
						}
						item = item.trim();
						String receiver = clientMessage[1];
						server.addGiveRequest(userName, item, receiver);
					} catch (Exception e) {
						writeToClient.writeObject("Invalid give command");
					}
				}

				else if (messageFromClient.length() >= 11
						&& messageFromClient.substring(0, 11).equalsIgnoreCase(
								"acceptitem ")) {
					String[] tradeMessage = messageFromClient.split(" ");
					try {
						String item = "";
						for (int i = 2; i < tradeMessage.length; i++) {
							item += tradeMessage[i] + " ";
						}
						item = item.trim();
						String sender = tradeMessage[1];
						server.acceptReceive(sender, item, userName);
					} catch (Exception e) {
						writeToClient.writeObject("Invalid acceptitem command");
					}
				}

				else if (messageFromClient.length() >= 4
						&& messageFromClient.substring(0, 4).equalsIgnoreCase(
								"get ")) {
					String[] clientMessage = messageFromClient.split(" ");
					try {
						String item = "";
						for (int i = 2; i < clientMessage.length; i++) {
							item += clientMessage[i] + " ";
						}
						item = item.trim();
						String sender = clientMessage[1];
						server.addGetRequest(userName, item, sender);
					} catch (ArrayIndexOutOfBoundsException e) {
						writeToClient.writeObject("Invalid get command");
					}

				}

				else if (messageFromClient.length() >= 13
						&& messageFromClient.substring(0, 13).equalsIgnoreCase(
								"accepttogive ")) {
					String[] tradeMessage = messageFromClient.split(" ");
					try {
						String item = "";
						for (int i = 2; i < tradeMessage.length; i++) {
							item += tradeMessage[i] + " ";
						}
						item = item.trim();
						String receiver = tradeMessage[1];
						server.acceptToGive(receiver, item, userName);
					} catch (Exception e) {
						writeToClient
								.writeObject("Invalid accepttogive command");
					}

				}

				else if (messageFromClient.length() >= 14
						&& messageFromClient.substring(0, 14).equalsIgnoreCase(
								"declinetogive ")) {
					String[] tradeMessage = messageFromClient.split(" ");
					try {
						String item = "";
						for (int i = 2; i < tradeMessage.length; i++) {
							item += tradeMessage[i] + " ";
						}
						item = item.trim();
						String receiver = tradeMessage[1];
						server.declineToGive(receiver, item, userName);
					} catch (Exception e) {
						writeToClient
								.writeObject("Invalid declinetogive command");
					}
				}

				else if (messageFromClient.length() >= 12
						&& messageFromClient.substring(0, 12).equalsIgnoreCase(
								"declineitem ")) {
					String[] tradeMessage = messageFromClient.split(" ");
					try {
						String item = "";
						for (int i = 2; i < tradeMessage.length; i++) {
							item += tradeMessage[i] + " ";
						}
						item = item.trim();
						String sender = tradeMessage[1];
						server.declineItem(sender, item, userName);
					} catch (Exception e) {
						writeToClient
								.writeObject("Invalid declineitem command");
					}
				}

				else if (messageFromClient.length() >= 6
						&& messageFromClient.substring(0, 6).equalsIgnoreCase(
								"fight ")) {
					try {
						String opponent = messageFromClient.substring(6).trim();
						String result = server.fight(userName, opponent);
						writeToClient.writeObject("\n" + result);
					} catch (Exception e) {
						writeToClient.writeObject("Invalid fight command");
					}
				}

				else if (messageFromClient.equalsIgnoreCase("requests")) {
					server.displayRequests(userName);
				}

				// user wants to quit
				else if (messageFromClient.equalsIgnoreCase("quit")) {
					server.closeCharacterStream(userName);
					// broadcasts to all users that a user has logged off
					server.broadcast("[" + userName + " has logged off]");
					wantToAStayConnected = false;
					quit();
				}

				else if (messageFromClient.equalsIgnoreCase("rage")) {
					server.notifyRoom(userName, userName + " is RAGGING HARD.");
				}

				else if (messageFromClient.equalsIgnoreCase("smile")) {
					server.notifyRoom(userName, userName + " is smiling.");
				}

				else if (messageFromClient.equalsIgnoreCase("laugh")) {
					server.notifyRoom(userName, userName + " is LOLing.");
				}

				else {
					writeToClient.writeObject("Can't compute "
							+ messageFromClient);
				}

				if (!messageFromClient.equalsIgnoreCase("quit")) {
					String userStats = server.updateUserStats(userName);
					writeToClient.writeObject("*** " + userStats);
				}

				if (userName.equalsIgnoreCase("admin")
						&& messageFromClient.equalsIgnoreCase("shutdown")) {
					server.shutDown(this);
				}

			} catch (Exception e) {
				// error
				e.printStackTrace();
				wantToAStayConnected = false;
			}
		}
	}

	public String getUserName() {
		return userName;
	}

	/**
	 * Allows the server to send a message to all clients
	 *
	 * @param message
	 *            that you are sending
	 */
	public void sendMessage(String message) {
		try {
			// either do it
			writeToClient.writeObject(message);
		} catch (IOException e) {
			// or error
			e.printStackTrace();
		}
	}

	public void quit() {
		try {
			writeToClient.writeObject("!!!");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			socketFromServer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			readFromClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writeToClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.removeClient(this);
	}

	public boolean isValidAccount() {
		return validAccount;
	}
}