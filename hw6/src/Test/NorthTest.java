package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import Lands.North;
import Model.Room;

public class NorthTest {

	@Test
	public void test() {
		North n = new North();
		n.activateLobby();
		assertTrue(n.getActivity());
		n.deactivateLobby();
		assertFalse(n.getActivity());
		HashMap<Integer, Room> hm = n.getMap();
		Room temp = hm.get(106);
		assertEquals(105, temp.westRoom());
		assertEquals(103, temp.northRoom());
		assertEquals(-1, temp.eastRoom());
		assertEquals(109, temp.southRoom());
		assertEquals("North", temp.getLand());
		assertEquals(106, temp.getLandNumber());

	}

}
