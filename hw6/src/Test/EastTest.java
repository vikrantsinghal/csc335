package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import Lands.East;
import Model.Room;

public class EastTest {

	@Test
	public void test() {
		East e = new East();
		e.activateLobby();
		assertTrue(e.getActivity());
		e.deactivateLobby();
		assertFalse(e.getActivity());
		HashMap<Integer, Room> hm = e.getMap();
		Room temp = hm.get(201);
		assertEquals(204, temp.westRoom());
		assertEquals(-1, temp.northRoom());
		assertEquals(-1, temp.eastRoom());
		assertEquals(202, temp.southRoom());
		assertEquals("East", temp.getLand());
		assertEquals(201, temp.getLandNumber());
	}

}
