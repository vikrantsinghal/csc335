package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import view.IGameObserver;

/**
 * The GameMaster objects is the model for our game of HuntTheWumpus. The
 * GameMaster is in charge of setting up the game, as well as keeping tabs as to
 * where all the objects are inside of it. The GameMaster is observable, so it
 * is able to update after a user makes a move using either our console or gui.
 *
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Joshua Fry
 * @author Vikrant Singhal
 */
public class GameMaster extends Observable {

	// initialize instance variables,
	// each gameMaster contains a map, hunter, wumpus, and other variables to
	// help determine the state of the game
	private Map map;
	private Hunter hunter;
	private Wumpus wumpus;
	private boolean alive;
	private EmptyRoom arrowRoom;
	private Strategy strategy;
	private int userIn;
	private boolean shooting;
	public int arrowHeight;
	private ArrayList<IGameObserver> observers = new ArrayList<IGameObserver>();

	public GameMaster() {
		setGame();
	}

	/**
	 * Create a random board for the user to play a game of Hunt the Wumpus At
	 * most two rooms have bats, two rooms have pits, one room has a wumpus, and
	 * one room has a hunter
	 */
	public void setGame() {
		// reset objects
		map = new Map();
		hunter = new Hunter(0);
		alive = true;
		arrowRoom = new EmptyRoom(0, 1, 1, 1);
		strategy = null;
		wumpus = new Wumpus(0);
		userIn = 0;
		shooting = false;
		arrowHeight = 460;

		Random generator = new Random();
		ArrayList<Integer> listRandomRooms = new ArrayList<Integer>(6);

		/**
		 * Generate 6 different numbers to represent the rooms in which each
		 * object will be assigned to at the start of game
		 */
		int randomNumber;
		int count = 0;
		while (count < 6) {
			randomNumber = 1 + generator.nextInt(20);
			if (!listRandomRooms.contains(randomNumber)) {
				listRandomRooms.add(randomNumber);
				count++;
			}
		}

		// BatRoom * 2
		for (int i = 0; i < 2; i++) {
			int room = listRandomRooms.get(i);
			EmptyRoom r = map.getRoom(room);
			int a = r.getAdj1();
			int b = r.getAdj2();
			int c = r.getAdj3();
			map.getRoom(room).addObject(new Bat(room, a, b, c));
		}

		// PitRoom * 2
		for (int i = 2; i < 4; i++) {
			int room = listRandomRooms.get(i);
			EmptyRoom r = map.getRoom(room);
			int a = r.getAdj1();
			int b = r.getAdj2();
			int c = r.getAdj3();
			map.getRoom(room).addObject(new Pit(room, a, b, c));
		}

		// Set the location of our wumpus
		int room = listRandomRooms.get(4);
		EmptyRoom r = map.getRoom(room);
		int a = r.getAdj1();
		int b = r.getAdj2();
		int c = r.getAdj3();
		wumpus = new Wumpus(room, a, b, c);
		map.getRoom(room).addObject(wumpus);

		// Set the starting position of our hunter
		room = listRandomRooms.get(5);
		map.getRoom(room).setHunterPresense();
		hunter = new Hunter(room);

	}

	// Set default game basd on the original map
	public void setDefaultGame() {
		// reset objects
		map = new Map();
		hunter = new Hunter(0);
		alive = true;
		arrowRoom = new EmptyRoom(0, 1, 1, 1);
		strategy = null;
		userIn = 0;
		// set to default
		map.getRoom(7).addObject(new Pit(7, 20, 6, 8));
		map.getRoom(1).addObject(new Bat(1, 20, 2, 5));
		map.getRoom(11).addObject(new Bat(11, 10, 12, 18));
		map.getRoom(19).addObject(new Pit(19, 18, 9, 8));
		map.getRoom(16).addObject(new Wumpus(16, 8, 17, 15));
		wumpus = new Wumpus(16);
		map.getRoom(2).setHunterPresense();
		hunter = new Hunter(2);
	}

	// get the games current map
	public Map getMap() {
		return map;
	}

	// get the games current hunter
	public Hunter getHunter() {
		return hunter;
	}

	// gives the current location of the games hunter
	public int hunterLocation() {
		return hunter.getCurrentLocation();
	}

	// get the current status of hunter
	public boolean isAlive() {
		return alive;
	}

	// a notification method if you need to know if Wumpus is dead
	public boolean isWumpusAlive() {
		return wumpus.isAlive();
	}

	//

	// returns the current room, hints for adjacent rooms,
	// and the next action for user to make
	public String display() {
		if (!isAlive()) {
			return "Game is over!";
		} else {
			String result = "";
			int room = this.hunterLocation();
			// display room location, action prompt, and adjacent rooms
			int adj1 = map.getRoom(room).getAdj1();
			int adj2 = map.getRoom(room).getAdj2();
			int adj3 = map.getRoom(room).getAdj3();
			result += ("\nYou are in room " + room + "\n" + displayHints()
					+ "\n" + "Enter room number or path to shoot arrow" + "\n"
					+ adj1 + "   " + adj2 + "   " + adj3);
			return result;
		}
	}

	// adjusts the game to new hunter location
	public void moveHunter(int newRoom) {
		int prevRoom = hunter.getCurrentLocation();
		map.getRoom(prevRoom).setHunterPresense();
		map.getRoom(newRoom).setHunterPresense();
		hunter.moveToLocation(newRoom);
	}

	/**
	 * The game is given a user entry, or a user selection given by the selected
	 * button text. This method will determine what the hunter does
	 *
	 * @param the
	 *            room you are going to
	 *
	 */
	public String moveToRoom(int r) {
		String result = "";

		userIn = hunter.getCurrentLocation();
		int room = r;

		int adj1 = map.getRoom(userIn).getAdj1();
		int adj2 = map.getRoom(userIn).getAdj2();
		int adj3 = map.getRoom(userIn).getAdj3();

		// if invalid entry
		if ((room != adj1) && (room != adj2) && (room != adj3)) {
			result += "\nInvalid room number. Enter again.\n";
			return result;
		}

		// go to first room
		if (room == adj1)
			this.moveHunter(adj1);

		// go to second room
		if (room == adj2)
			this.moveHunter(adj2);

		// go to third room
		if (room == adj3)
			this.moveHunter(adj3);

		// new room has Wumpus
		if (map.getRoom(room).hasWumpus()) {
			strategy = map.getRoom(room).getStrategy();
			if (strategy.performAction() == -1) {
				result += ("\nDeath by wumpus!\n");
				gameOver();
				return result;
			}
		}

		// new room has Bats
		if (map.getRoom(room).hasBat()) {
			result += ("\nBATS!!!\n");
			strategy = map.getRoom(room).getStrategy();
			this.moveHunter(strategy.performAction());
			// new room has wumpus
			if (map.getRoom(hunter.getCurrentLocation()).hasWumpus()) {
				wumpus.performAction();
				result += ("\nDeath by wumpus!\n");
				gameOver();
				return result;
			}
			// new room has pit
			if (map.getRoom(hunter.getCurrentLocation()).hasPit()) {
				result += ("\nDeath by pit!\n");
				gameOver();
				return result;
			}
			return result;
		}

		// new room has Pit
		if (map.getRoom(room).hasPit()) {
			strategy = map.getRoom(room).getStrategy();
			if (strategy.performAction() == -1) {
				result += ("\nDeath by pit!\n");
				gameOver();
				return result;
			}
		}
		return result;
	}

	// you are dead, want to play again?
	public void gameOver() {
		alive = false;
	}

	// start a brand new game;
	public GameMaster resetGame() {
		this.setGame();
		return this;
	}

	/**
	 * The shooting function will look at your arrow path. If one of the room
	 * correctly hits a wumpus
	 *
	 * @param rooms
	 * @return
	 */
	public String shoot(int[] rooms) {

		String result = "";
		// check if hunter can shoot
		if (!hunter.shoot()) {
			result += ("\nOut of arrows! You suck!\n");
			gameOver();
			return result;
		}

		arrowRoom = map.getRoom(this.hunterLocation());

		// you lost an arrow, so hopefully you hit something..
		// this assumes you hit a wumpus or yourself...continue...

		for (int i = 0; i < rooms.length; i++) {

			// shoot into room1
			if (rooms[i] == arrowRoom.getAdj1()) {
				// arrow is now in Room 1
				arrowRoom = map.getRoom(rooms[i]);
				// did you hit the Wumpus?
				if (arrowRoom.hasWumpus()) {
					result += ("\nYou killed the Wumpus!\n");
					wumpus.kill();
					gameOver();
					break;
				}
				// did you shoot yourself?
				if (rooms[i] == this.hunterLocation() && i != 0) {
					result += ("\nYou shot your eye out!\n");
					gameOver();
					break;
				}

			}

			// shoot into room2
			else if (rooms[i] == arrowRoom.getAdj2()) {
				// arrow is now in Room 2
				arrowRoom = map.getRoom(rooms[i]);
				// did you hit the Wumpus?
				if (arrowRoom.hasWumpus()) {
					result += ("\nYou killed the Wumpus!\n");
					wumpus.kill();
					gameOver();
					break;
				}
				// did you shoot yourself?
				if (rooms[i] == this.hunterLocation() && i != 0) {
					result += ("\nYou shot your eye out!\n");
					gameOver();
					break;
				}
			}

			// shoot into room3
			else if (rooms[i] == arrowRoom.getAdj3()) {
				// arrow is now in Room 3
				arrowRoom = map.getRoom(rooms[i]);
				// did you hit the Wumpus?
				if (arrowRoom.hasWumpus()) {
					result += ("\nYou killed the Wumpus!\n");
					wumpus.kill();
					gameOver();
					break;
				}
				// did you shoot yourself?
				if (rooms[i] == this.hunterLocation() && i != 0) {
					result += ("\nYou shot your eye out!\n");
					gameOver();
					break;
				}
			}

			// you shot into an invalid adjacent room cuz you suck
			else {
				result += "\nYou missed\n";
				break;
			}
		}

		// ...continuing, because the arrow went into darkness
		// you shot the arrow, it didn't hit the wumpus, yourself,
		// or an imaginary room so...
		if (alive == true)
			result = "\nYou missed\n";
		return result;
	}

	// display a list of all the rooms adjacent to hunter room
	public List<Integer> getHunterAdjacentRooms() {
		return map.getRoom(hunterLocation()).getAdjacentRooms();
	}

	// give a string representation of the hints for the current location
	public String displayHints() {
		String result = "";
		int room = this.hunterLocation();
		// display room location, action prompt, and adjacent rooms
		int adj1 = map.getRoom(room).getAdj1();
		int adj2 = map.getRoom(room).getAdj2();
		int adj3 = map.getRoom(room).getAdj3();
		String adj1h = map.getRoom(adj1).hint();
		String adj2h = map.getRoom(adj2).hint();
		String adj3h = map.getRoom(adj3).hint();
		// mix up the order the hints appear
		Random generator = new Random(3);
		if (generator.nextInt() == 0)
			result = adj1h + adj2h + adj3h;
		else if (generator.nextInt() == 1)
			result = adj2h + adj3h + adj1h;
		else
			result = adj3h + adj1h + adj2h;
		return result;
	}

	// used for the consolePanelView and guiPanelView
	public void addObserver(IGameObserver observer) {
		observers.add(observer);
	}

	// update the current status
	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).update(this);
		}
	}

	// set arrow shooting to opposite state
	public void setShooting() {
		shooting = !shooting;
	}

	// is the arrow shooting?
	public boolean isShooting() {
		return shooting;
	}

	// reset arrow to original height
	public void resetArrow() {
		arrowHeight = 460;
	}
}
