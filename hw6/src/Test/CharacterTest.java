package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Characters.Character;
import Characters.Dalek;
import Characters.GhostRider;
import Characters.HeadlessKnight;
import Characters.HostileMOB;
import Characters.Hulk;
import Characters.MOB;
import Characters.MightyScorpion;
import Characters.NaughtyMOB;
import Characters.NineTailedFox;
import Characters.PissingBaby;
import Characters.SellerMOB;
import Characters.Tarantula;
import Characters.Vader;
import Characters.Voldemort;
import Characters.Warrior;
import Items.Armor;
import Items.Inanimate;
import Items.Item;
import Items.Weapon;
import Model.AccountCollection;
import Model.Room;

public class CharacterTest {

	private AccountCollection acctColl = new AccountCollection();
	private Character character = new MOB("Joe", 0, 0);

	@Test
	public void test() {
		acctColl = new AccountCollection();
		character = acctColl.getCharacter("Josh");
		assertEquals("Josh", character.getName());
		assertEquals("Warrior", character.getOccupation());
		assertEquals(0, character.getExperience());
		assertEquals(0, character.getLevel());
		character.gainExperience(1);
		assertEquals(1, character.getExperience());
		assertEquals(1, character.getLevel());
		character.gainExperience(133);
		assertEquals(134, character.getExperience());
		assertEquals(2, character.getLevel());
		assertEquals(0, character.getRoom());
		assertEquals(5, character.getAttackDmg());
		Room r1 = character.getRoomObject();
		r1.addCharacterToRoom(character);
		r1.removeCharacter(character);
		assertEquals("Lobby", character.getLand());
		character.addGold(10);
		assertEquals(character.getGold(), 20);
		character.addHealth(-10);
		assertEquals(character.getHealth(), 110);
		character.goTo("south");
		assertFalse(character.goTo("west"));
		assertFalse(character.goTo("east"));
		character.goTo("south");
		character.goTo("west");
		assertFalse(character.goTo("north"));
		character.goTo("south");
		character.goTo("south");
		assertFalse(character.goTo("south"));
		character.goTo("north");
		character.goTo("east");
	}

	@Test
	public void itemTests() {
		acctColl = new AccountCollection();
		character = acctColl.getCharacter("Josh");
		String result = character.addItem("ammo box");
		assertEquals(result, "Josh added Ammo box");
		result = character.dropItem("ammo box");
		assertEquals(result, "Josh dropped Ammo box");
		character.addItem("gary the homeless");
		result = character.useItem("gary the homeless");
		assertEquals(result, "You lost 1 pieces of gold!");
		character.addItem("rock");
		result = character.useItem("rock");
		assertEquals(result, "Well that was useless...");
		character.addItem("ammo box");
		result = character.useItem("ammo box");
		assertEquals(result,
				"You found 5 pieces of gold! I wonder what else you can find");
		result = character.useItem("excalibur");
		assertEquals(result, "Can't use that here");
		result = character.useItem("bird");
		assertEquals(result, "Nothing to use");
		character.addItem("glass of water");
		character.addItem("coca-cola");
		result = character.useItem("glass of water");
		assertEquals(result, "Ugh! That was disgusting!");
		result = character.useItem("coca-cola");
		assertEquals(result, "You gained 10 HPs!");

		Item gem = new Inanimate("Gem", 1000);
		gem.setQuestItem(10);
		character.getInventory().add(gem);
		result = character.useItem("Gem");
		assertEquals(result, "Can't use that here");
		result = character.dropItem("Gem");
		assertEquals(result, "Can't drop that!");
		gem = character.getInventory().give("Gem");
		gem.setQuestItem(1);
		character.getInventory().add(gem);
		result = character.useItem("Gem");
		assertEquals(result,
				"You found 10000 pieces of gold! I wonder what else you can find");

		Item useful = new Weapon("super", 20, 10);
		Item armor = new Armor("duper", 20, 10);
		assertFalse(character.checkItemInRoom("super"));
		character.getInventory().add(useful);
		character.getInventory().add(armor);
		result = character.equip("super");
		result = character.equip("duper");
		assertEquals(result, "duper has been equipped");
		character.dropItem("super");
		result = character.equip("super");
		assertEquals(result, "Could not equip super");

		result = character.getUserStats();
		assertEquals(result,
				"Name: Josh\n\nLevel: 0\n\nExp: 0\n\nHealth: 100\n\nGold: 10014");

		Item fish = character.tradeItemWithGold("duper", 5);
		assertEquals(fish, null);
		fish = character.tradeItemWithGold("duper", 50);

		List<Item> list = new ArrayList<Item>();
		list.add(useful);
		list.add(armor);
		fish = character.tradeItemWithItem(list, "excalibur");
		assertEquals(fish, null);
		fish = character.tradeItemWithItem(list,
				"letter of peace and suffering");
		assertFalse(character.getInventory().checkForItem(
				"letter of peace and suffering"));
	}

	@Test
	public void scoreTest() {
		acctColl = new AccountCollection();
		character = acctColl.getCharacter("Josh");
		String result = character.getScore();
		assertEquals(result, "You have more stuff to do");
		for (int i = 1; i < 11; i++) {
			character.gainExperience(100 * i);
		}
		result = character.getScore();
		assertEquals(result, "Almost there");
		for (int i = 11; i < 21; i++) {
			character.gainExperience(100 * i);
		}
		result = character.getScore();
		assertEquals(result, "Why are you still playing?");
	}

	@Test
	public void WarriorTest() {
		Warrior w = new Warrior("man");
		w.subHealth(90);
		w.subGold(9);
		w.subGold(2);
		w.subHealth(11);
		assertEquals(0, w.getGold());
		assertEquals(20, w.getHealth());
		w.setName("guy");
		assertEquals("guy", w.getName());
		w.setStreams(null, null);
	}

	@Test
	public void PissingBabyTest() {
		PissingBaby pb = new PissingBaby("Jorge");
		assertEquals("Jorge", pb.getName());
		pb.sendMessage("" + 4);
		assertTrue(pb.isQuestAvailable());
		assertEquals(
				pb.getMessage(),
				"Me llamo Jorge. I am known about the kingdom as 'The Pissing Baby.' Please, leave me be.");
		pb.sendMessage("" + 10);
		assertFalse(pb.isQuestAvailable());
		assertEquals(pb.getMessage(),
				"Hola there, senor. Me llamo Jorge and I hope you enjoy the present.");
		assertEquals("Pissing Baby's Super Soaker", pb.getQuest().getItemName());
		pb.sendMessage("" + 11);
		pb.getMessage();

	}

	@Test
	public void MOBTest() {
		MOB h = new MOB("jack", 20, 10);
		assertEquals("jack", h.getName());
		h.sendMessage("" + 5);
		assertEquals(h.getMessage(),
				"Come back when you an equip a stronger weapon!");
		h.sendMessage("" + 11);
		assertEquals(h.getMessage(), "Prepare for battle!");
		assertTrue(h.isAlive());
		assertEquals(h.performAction(), 10);
		h.reduceHealth(0);
		assertTrue(h.isAlive());
		h.setAlive();
		h.reduceHealth(21);
		assertFalse(h.isAlive());
		h.reduceHealth(0);
	}

	@Test
	public void HostileMOBTest() {
		HostileMOB h = new HostileMOB("jack", 20, 10);
		assertEquals("jack", h.getName());
		assertTrue(h.isAlive());
		h.reduceHealth(0);
		assertTrue(h.isAlive());
		h.setAlive();
		h.reduceHealth(21);
		assertFalse(h.isAlive());
		h.reduceHealth(0);
	}

	@Test
	public void SellerMOBTest() {
		SellerMOB s = new SellerMOB("jack");
		s.getInventory().setDefaultInventory();
		assertEquals("jack", s.getName());
		Item i = s.tradeItemWithGold("potion of mighty recovery", 100);
		s.move();
		assertEquals(100, i.getItemValue());
	}

	@Test
	public void RandomCharacterTest() {
		Character a = new Dalek("d");
		Character b = new GhostRider("g");
		Character c = new HeadlessKnight("h");
		Character d = new Hulk("green");
		Character e = new MightyScorpion("m");
		Character f = new NaughtyMOB("tina");
		Character g = new NineTailedFox("Jim");
		Character h = new Tarantula("t");
		Character i = new Vader("v");
		Character j = new Voldemort("x");
	}
}
