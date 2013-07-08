package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import Lands.South;
import Model.Room;

public class SouthTest {

	@Test
	public void test() {
		South s = new South();
		s.activateLobby();
		assertTrue(s.getActivity());
		s.deactivateLobby();
		assertFalse(s.getActivity());
		HashMap<Integer, Room> hm = s.getMap();
		Room temp = hm.get(302);
		assertEquals(303, temp.westRoom());
		assertEquals(305, temp.northRoom());
		assertEquals(301, temp.eastRoom());
		assertEquals(-1, temp.southRoom());
		assertEquals("South", temp.getLand());
		assertEquals(302, temp.getLandNumber());
	}

}
