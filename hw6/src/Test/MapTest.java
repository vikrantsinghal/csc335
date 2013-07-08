package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Items.Inanimate;
import Items.Item;
import Lands.Map;
import Model.Room;

public class MapTest {

	@Test
	public void test() {
		Map m = new Map();
		Room temp = m.getLocation(305);
		assertEquals(308, temp.northRoom());
		temp = m.getLocation(200);
		assertEquals(0, temp.westRoom());
		temp = m.getLocation(201);
		assertEquals(202, temp.southRoom());
		temp = m.getLocation(403);
		assertEquals(406, temp.eastRoom());
		temp = m.getLocation(105);
		assertEquals(102, temp.northRoom());

		Item i = new Inanimate("Rock", 0);
		m.addItem(302, i);
		assertTrue(m.getLocation(302).roomInventory()
				.checkForItem(i.getItemName()));
		m.removeItem(302, "Rock");
		assertFalse(m.getLocation(302).roomInventory()
				.checkForItem(i.getItemName()));
	}
}
