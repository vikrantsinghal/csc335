/**
 * Test the Armor class, in particular the Check Quality method
 *
 *
 *
 * @author Kyle Almryde
 *
 */
package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Items.Armor;

public class ArmorTest {

	/**
	 * Tests the checkArmorQuality method to make sure its correctly
	 * 
	 * 
	 */
	@Test
	public void testCheckArmorQuality() {
		Armor ar = new Armor("Foo", 100, 50);
		assertFalse(ar.isEquipped());
		ar.setEquipped();
		assertTrue(ar.isEquipped());

		assertEquals(100, ar.getArmorRating());
		assertEquals("Excellent", ar.checkArmorQuality());

		assertEquals(0, ar.takeDamage(10));
		assertEquals(90, ar.getArmorRating());
		assertEquals("Excellent", ar.checkArmorQuality());

		assertEquals(0, ar.takeDamage(15));
		assertEquals(75, ar.getArmorRating());
		assertEquals("Good", ar.checkArmorQuality());

		assertEquals(0, ar.takeDamage(15));
		assertEquals(60, ar.getArmorRating());
		assertEquals("Good", ar.checkArmorQuality());

		assertEquals(0, ar.takeDamage(10));
		assertEquals(50, ar.getArmorRating());
		assertEquals("Poor", ar.checkArmorQuality());

		assertEquals(0, ar.takeDamage(24));
		assertEquals(26, ar.getArmorRating());
		assertEquals("Poor", ar.checkArmorQuality());

		assertEquals(0, ar.takeDamage(24));
		assertEquals(2, ar.getArmorRating());
		assertEquals("Broken", ar.checkArmorQuality());

		assertEquals(98, ar.takeDamage(100));
		assertEquals(0, ar.getArmorRating());
	      assertEquals(100, ar.getArmorBase());
		assertEquals("Useless", ar.checkArmorQuality());

		ar.setEquipped();

	} // End of testCheckArmorQuality
}