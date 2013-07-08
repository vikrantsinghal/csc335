package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import Lands.Lobby;
import Model.Room;

public class LobbyTest {

	@Test
	public void test() {
		Lobby l = new Lobby();
		l.activateLobby();
		assertTrue(l.getActivity());
		l.deactivateLobby();
		assertFalse(l.getActivity());
		HashMap<Integer, Room> hm = l.getMap();
		Room temp = hm.get(0);
		assertEquals(100, temp.northRoom());
		assertEquals(200, temp.eastRoom());
		assertEquals(300, temp.southRoom());
		assertEquals(400, temp.westRoom());
		assertEquals("Lobby", temp.getLand());
		assertEquals(0, temp.getLandNumber());
	}
}
