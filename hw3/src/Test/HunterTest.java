package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.Hunter;

import org.junit.Test;

/**
 * Test our hunter, check to see if he is able to shoot arrows, or not shoot
 * arrows, and also move hunter to locations
 * 
 */
public class HunterTest {

	@Test
	public void test() {
		// create a new hunter
		Hunter h = new Hunter(1);
		// 3 arrows
		assertEquals(3, h.getRemainingArrows());
		assertTrue(h.shoot());
		// 2 arrows
		assertEquals(2, h.getRemainingArrows());
		assertEquals(1, h.getCurrentLocation());
		h.shoot();
		// 1 arrow
		assertEquals(1, h.getRemainingArrows());
		h.shoot();
		// 0 arrows
		h.shoot();
		// you can't shoot buddy..
		assertFalse(h.shoot());
		// but I'll let you move
		h.moveToLocation(2);
		assertEquals(2, h.getCurrentLocation());
	}
}
