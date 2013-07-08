package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.EmptyRoom;
import model.GameMaster;
import model.Hunter;

import org.junit.Test;

/*
 * Tests for the GameMaster class.
 */
public class GameMasterTest {

	/*
	 * Test for the default version of the game.
	 */
	@Test
	public void testDefaultGame() {
		GameMaster gm = new GameMaster();
		gm.setDefaultGame();
		model.Map m = gm.getMap();
		assertTrue(gm.isAlive());
		Hunter h = gm.getHunter();
		assertEquals(2, h.getCurrentLocation());
		EmptyRoom r = m.getRoom(16);
		assertEquals("I smell something foul\n", r.hint());
		assertTrue(r.hasWumpus());
		r = m.getRoom(1);
		List<Integer> rooms = new ArrayList<Integer>();
		rooms.add(20);
		rooms.add(2);
		rooms.add(5);
		assertEquals(rooms, r.getAdjacentRoom());
		assertEquals("I hear squeaking...\n", r.hint());
		assertEquals(1, r.getRoomNumber());
		assertFalse(r.hasHunter());
		r = m.getRoom(2);
		rooms.clear();
		rooms.add(1);
		rooms.add(3);
		rooms.add(10);
		assertEquals(rooms, r.getAdjacentRoom());
		assertTrue(r.hasHunter());

		// shoot and miss, then hit wumpus
		int[] rooms1 = new int[6];
		rooms1[0] = 3;
		rooms1[1] = 4;
		rooms1[2] = 14;
		assertEquals("\nYou missed\n", gm.shoot(rooms1));
		rooms1[3] = 15;
		rooms1[4] = 16;
		assertEquals("\nYou killed the Wumpus!\n", gm.shoot(rooms1));
		rooms1[4] = 6;
		rooms1[5] = 3;
		assertEquals("\nYou missed\n", gm.shoot(rooms1));
	}

	/*
	 * Testing for code coverage of resetGame method.
	 */
	@Test
	public void testRandomGame() {
		GameMaster gm = new GameMaster();
		gm.resetGame();
	}

	/*
	 * Tests for gameplay on default game setting.
	 */
	@Test
	public void testShoot() {
		GameMaster gm = new GameMaster();
		gm.setDefaultGame();
		int[] rooms = { 3, 2 };

		// Test for shooting the hunter himself.
		assertEquals("\nYou shot your eye out!\n", gm.shoot(rooms));

		gm.moveHunter(3);
		gm.moveHunter(12);
		gm.moveHunter(13);
		gm.moveHunter(17);

		int[] room = { 16 };
		// Test for killing the hunter.
		assertEquals("\nYou killed the Wumpus!\n", gm.shoot(room));

		gm.moveHunter(18);
		gm.moveHunter(19);
		gm.moveHunter(8);

		// Moving to another room and shooting again to test
		// killing of wumpus
		assertEquals("\nYou killed the Wumpus!\n", gm.shoot(room));
		assertFalse(gm.isWumpusAlive());

		// Testing to check if out of arrows.
		assertEquals("\nOut of arrows! You suck!\n", gm.shoot(room));
	}

	/*
	 * Testing moving hunter and his death by various objects inside the rooms.
	 */
	@Test
	public void testMoveToRoom() {
		GameMaster gm = new GameMaster();
		gm.setDefaultGame();
		// Test to check if the room number entered by the user is valid.
		assertEquals("\nInvalid room number. Enter again.\n", gm.moveToRoom(-5));
		// Test to check hunter's current location.
		assertEquals(2, gm.hunterLocation());
		List<Integer> temp = new ArrayList<Integer>(3);
		temp.add(1);
		temp.add(3);
		temp.add(10);
		// check the adjacent rooms and display
		assertEquals(temp, gm.getHunterAdjacentRooms());
		assertEquals("I hear squeaking...\n", gm.displayHints());

		gm.setShooting();
		// Check if the hunter is shooting.
		assertTrue(gm.isShooting());
		gm.setShooting();
		assertFalse(gm.isShooting());

		gm.resetArrow();
		// Testing moving to different rooms.
		assertEquals(460, gm.arrowHeight);
		assertEquals("", gm.moveToRoom(3));
		assertEquals("", gm.moveToRoom(2));
		assertEquals("", gm.moveToRoom(10));
		assertEquals("", gm.moveToRoom(2));

		// Testing moving into rooms with bats.
		assertEquals("\nBATS!!!\n", gm.moveToRoom(1));

		gm.resetGame();
		gm.setDefaultGame();
		gm.moveHunter(15);
		// Testing death by Wumpus.
		assertEquals("\nDeath by wumpus!\n", gm.moveToRoom(16));
		gm.moveHunter(6);
		// Testing death by falling into pit.
		assertEquals("\nDeath by pit!\n", gm.moveToRoom(7));

		gm.moveHunter(16);
		// Testing whether game is over or not.
		assertEquals("Game is over!", gm.display());

	}
}
