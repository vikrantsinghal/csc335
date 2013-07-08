package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import Lands.West;
import Model.Room;

public class WestTest {

	@Test
	public void test() {
		West w = new West();
		w.activateLobby();
		assertTrue(w.getActivity());
		w.deactivateLobby();
		assertFalse(w.getActivity());
		HashMap<Integer, Room> hm = w.getMap();
		Room temp = hm.get(405);
		assertEquals(402, temp.westRoom());
		assertEquals(406, temp.northRoom());
		assertEquals(408, temp.eastRoom());
		assertEquals(404, temp.southRoom());
		assertEquals("West", temp.getLand());
		assertEquals(405, temp.getLandNumber());
	}

}
