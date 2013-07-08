package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.*;

import org.junit.Test;

/**
 * Tests all the objects, and stick them inside EmptyRoom. Test all the methods
 * created in the classes
 * 
 */
public class RoomTests {

	@Test
	public void testBats() {
		// check if a bat can exist inside empty room
		EmptyRoom b = new EmptyRoom(1, 2, 3, 4);
		b.addObject(new Bat(1, 2, 3, 4));
		assertEquals(1, b.getRoomNumber());
		// check adjacent rooms
		assertEquals(2, b.getAdj1());
		assertEquals(3, b.getAdj2());
		assertEquals(4, b.getAdj3());
		// check hint
		assertEquals("I hear squeaking...\n", b.hint());
		// is it a wumpus?
		assertFalse(b.hasWumpus());

		// bat can be initialized
		Bat x = new Bat(0, 1, 2, 3);
		assertEquals(0, x.getRoomNumber());
		assertEquals(1, x.getAdj1());
		assertEquals(2, x.getAdj2());
		assertEquals(3, x.getAdj3());
		// perform stategy on bat
		int a = x.performAction();
		assertTrue(b.hasBat());
		// get rid of bats
		b.setHasBat();
		assertFalse(b.hasBat());
		// put them back in
		b.setHasBat();
		assertTrue(b.hasBat());
	}

	@Test
	public void testPits() {
		// check if pit can exist in EmptyRoom
		EmptyRoom p = new EmptyRoom(1, 2, 3, 4);
		p.addObject(new Pit(1, 2, 3, 4));
		assertEquals(1, p.getRoomNumber());
		// check adjacent rooms
		assertEquals(2, p.getAdj1());
		assertEquals(3, p.getAdj2());
		assertEquals(4, p.getAdj3());
		// check hint
		assertEquals("I feel a draft...\n", p.hint());
		// is there a wumpus?
		assertFalse(p.hasWumpus());
		// pit can be initialized
		Pit x = new Pit(0, 1, 2, 3);
		assertEquals(0, x.getRoomNumber());
		assertEquals(1, x.getAdj1());
		assertEquals(2, x.getAdj2());
		assertEquals(3, x.getAdj3());
		// perform strategy
		assertEquals(-1, x.performAction());
		assertEquals("I feel a draft...", x.getHint());
		assertTrue(p.hasPit());
		// get rid of pit
		p.setHasPit();
		assertFalse(p.hasPit());
		// put pit back in
		p.setHasPit();
		assertTrue(p.hasPit());

	}

	@Test
	public void testWumpus() {
		// put Wumpus in EmptyRoom
		EmptyRoom e = new EmptyRoom(1, 2, 3, 4);
		e.addObject(new Wumpus(1, 2, 3, 4));
		// check hint
		assertEquals("I smell something foul\n", e.hint());
		// has wumpus
		assertTrue(e.hasWumpus());
		// perform strategy
		assertEquals(-1, e.getStrategy().performAction());
	}

	@Test
	public void testEmptyRoom() {
		// look at plain ol' EmptyRoom
		EmptyRoom e = new EmptyRoom(1, 2, 3, 4);
		assertEquals(1, e.getRoomNumber());
		// no hints
		assertEquals("", e.hint());
		assertFalse(e.hasWumpus());

		// initialize a Wumpus
		Wumpus w = new Wumpus(1, 2, 3, 4);
		assertEquals(1, w.getRoomNumber());
		assertTrue(w.isAlive());
		assertEquals(-1, w.performAction());
		assertEquals("I smell something foul", w.getHint());
		// kill the stupid thing
		w.kill();
		assertFalse(w.isAlive());
	}

	@Test
	public void testAll() {
		// check all the set/has methods for the objects inside EmptyRoom
		EmptyRoom a = new EmptyRoom(1, 2, 3, 4);
		a.setWumpusPresence();
		assertTrue(a.hasWumpus());
		a.setWumpusPresence();
		assertFalse(a.hasWumpus());
		a.setHunterPresense();
		assertTrue(a.hasHunter());
		a.setHunterPresense();
		assertFalse(a.hasHunter());
		assertEquals(2, a.getAdj1());
		assertEquals(3, a.getAdj2());
		assertEquals(4, a.getAdj3());
		// check list of adjacent rooms
		List<Integer> rooms = new ArrayList<Integer>();
		rooms.add(2);
		rooms.add(3);
		rooms.add(4);
		assertEquals(rooms, a.getAdjacentRoom());
	}
}
