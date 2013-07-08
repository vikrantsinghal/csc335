/**
 * Handles the interactions between user's and MOBs
 * when a <speak> command is given.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Model;

import java.io.Serializable;

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
import Characters.PastorPete;
import Characters.PissingBaby;
import Characters.Tarantula;
import Characters.Vader;
import Characters.Voldemort;
import Items.Item;

@SuppressWarnings("serial")
public class SpeakCommand implements Command, Serializable {

	private Character player;
	private Character ai;

	public SpeakCommand(Character user, Character other) {
		player = user;
		ai = other;
	}

	@Override
	public void execute() {

		// Speak to a generic MOB
		if (ai instanceof MOB) {
			ai.sendMessage("" + player.getAttackDmg());
			player.sendMessage(ai.getMessage());
			// need some type of implementation
			player.gainExperience(5);
		}

		// Quest of the PissingBaby
		else if (ai instanceof PissingBaby) {
			ai.sendMessage("" + player.getLevel());
			player.sendMessage(ai.getMessage());

			// see if the message quest is satisfied
			if (!((PissingBaby) ai).isQuestAvailable()) {
				Item i = ((PissingBaby) ai).getQuest();
				if (!player.getInventory().checkForItem(i.getItemName()))
					player.getInventory().add(i);
				player.gainExperience(50);
			}
		}

		// Speaking to Pastor Pete
		else if (ai instanceof PastorPete) {
			player.sendMessage(ai.getMessage());
			String reply = ((PastorPete) ai).getReply(player.getLevel());
			player.sendMessage(reply);

			// see if the homeless quest is available
			if (((PastorPete) ai).homelessAvailable()) {
				// if the player has gary the homeless
				if (player.getInventory().checkForItem("Gary the homeless")) {
					// trash gary the homeless, make quest unavailable, and add
					// item
					player.getInventory().trash("Gary the homeless");
					((PastorPete) ai).setHomelessAvailable();
					Item i = ((PastorPete) ai).getToga();
					player.getInventory().add(i);
					player.sendMessage("You just received God's Toga!");
					player.gainExperience(50);
				}
			}

			// see if the main quest is satisfied
			if (!((PastorPete) ai).isQuestAvailable()) {
				Item i = ((PastorPete) ai).getKey();
				if (!player.getInventory().checkForItem(i.getItemName()))
					player.getInventory().add(i);
				player.gainExperience(100);
			}
		}

		// Talking to a NaughtyMOB
		else if (ai instanceof NaughtyMOB) {
			((NaughtyMOB) ai).attack(player);
			player.sendMessage(((NaughtyMOB) ai).getDescription());
			player.sendMessage(((NaughtyMOB) ai).getReply());
		}

		// Talking to Dalek
		else if (ai instanceof Dalek) {
			player.sendMessage(((Dalek) ai).getDescription());
			player.sendMessage(((Dalek) ai).getReply());
			((Dalek) ai).attack(player);
			player.sendMessage("You have been attacked by the Dalek and you have lost "
					+ ((HostileMOB) ai).getDamage() + " health points!");
		}

		// Talking to GhostRider
		else if (ai instanceof GhostRider) {
			player.sendMessage(((GhostRider) ai).getDescription());

			if (((GhostRider) ai).isQuestAvailable()) {
				player.sendMessage(((GhostRider) ai).getReply());
				if (player.getInventory().checkForItem("Turbo Charger")) {
					player.getInventory().trash("Turbo Charger");
					((GhostRider) ai).disableQuest();
					Item i = ((GhostRider) ai).giveSpecialItem();
					player.getInventory().add(i);
					player.sendMessage("Well done kid. So here is your blaster. Take it and run away.");
				}
				else
					player.sendMessage("What the hell did you come here for? Do you even know the meaning of a fair deal?!");

			} else {
				player.sendMessage("I already have a charger now. But I can shoot you nevertheless!");
				((GhostRider) ai).attack(player);
				player.sendMessage("You have ben attacked by the Ghost Rider!!");
			}
		}

		// Talking to HeadlessKnight
		else if (ai instanceof HeadlessKnight) {
			player.sendMessage(((HeadlessKnight) ai).getDescription());

			if (((HeadlessKnight) ai).isQuestAvailable()) {
				player.sendMessage(((HeadlessKnight) ai).getReply());
				if (player.getInventory().checkForItem("Fairy Godmother's Head")) {
					player.getInventory().trash("Fairy Godmother's Head");
					((HeadlessKnight) ai).disableQuest();
					Item i = ((HeadlessKnight) ai).giveSpecialItem();
					player.getInventory().add(i);
					player.sendMessage("Perfect. Your wish has been granted. Here is your sword. Impale as many as you can.");
				}
				else
					player.sendMessage("You don't have the head. Hmmm...go and get it! NOW!!");

			} else {
				player.sendMessage("Is it so hard to stay away you irritating piece of undigested meat! Tkae this!!!");
				((HeadlessKnight) ai).attack(player);
				player.sendMessage("You have ben attacked by the Headless Knight!");
			}
		}

		// Talking to Hulk
		else if (ai instanceof Hulk) {
			player.sendMessage(((Hulk) ai).getDescription());

			if (((Hulk) ai).isQuestAvailable()) {
				player.sendMessage(((Hulk) ai).getReply());
				if (player.getInventory().checkForItem("Ham Burger")) {
					player.getInventory().trash("Ham Burger");
					((Hulk) ai).disableQuest();
					((Hulk) ai).giveHealth(player);
					player.sendMessage("Good job. I feel great! May your health increase!");
				}
				else
					player.sendMessage("You don't have it? Are you here to mock the hungry??!!");

			} else {
				player.sendMessage("You want to feed me more? So generous. But I'm not that generous. I'm gonna punch you in the face now!");
				((Hulk) ai).attack(player);
				player.sendMessage("You have been attacked by the Incredible Hulk!!");
			}
		}

		// Talking to MightyScorpion
		else if (ai instanceof MightyScorpion) {
			player.sendMessage(((MightyScorpion) ai).getDescription());
			player.sendMessage(((MightyScorpion) ai).getReply());
			((MightyScorpion) ai).attack(player);
			player.sendMessage("You have been attacked by the Mighty Scorpion!!");
		}

		// Talking to NineTailedFox
		else if (ai instanceof NineTailedFox) {
			player.sendMessage(((NineTailedFox) ai).getDescription());
			player.sendMessage(((NineTailedFox) ai).getReply());
			((NineTailedFox) ai).attack(player);
			player.sendMessage("You have been attacked by the Nine Tailed Fox!!");
		}

		// Talking to Tarantula
		else if (ai instanceof Tarantula) {
			player.sendMessage(((Tarantula) ai).getDescription());

			if (((Tarantula) ai).isQuestAvailable()) {
				String reply = ((Tarantula) ai).getReply();
				player.sendMessage(reply);
				// check if the user has heroin
				if (player.getInventory().checkForItem("Heroin")) {
					player.getInventory().trash("Heroin");
					((Tarantula) ai).disableQuest();
					Item i = ((Tarantula) ai).giveSpecialItem();
					player.getInventory().add(i);
					player.sendMessage("I...feel...good...mmmmm...on a high bro...");
				}
				else
					player.sendMessage("You don't have it? Come to me when you possess it!!!");

			} else {
				player.sendMessage("You want to kill by OD?! You nasty scum! Feel the sting now!!!");
				((Tarantula) ai).attack(player);
				player.sendMessage("You have been attacked by the Tarantula!!");
			}
		}

		// Talking to Vader
		else if (ai instanceof Vader) {
			player.sendMessage(((Vader) ai).getDescription());

			if (((Vader) ai).isQuestAvailable()) {
				String reply = ((Vader) ai).getReply();
				player.sendMessage(reply);
				if (player.getInventory().checkForItem("Luke Skywalker's Hair")) {
					player.getInventory().trash("Luke Skywalker's Hair");
					((Vader) ai).disableQuest();
					Item i = ((Vader) ai).giveSpecialItem();
					player.getInventory().add(i);
					player.sendMessage("This is awesome. You got the right hair! Luke..my son..your hair smells so nice!! Here kid, take your Lightsaber!!");
				}
				else
					player.sendMessage("If you don't have it then why waste my time you idiot?!!");
			} else {
				player.sendMessage("You think you can get more hair? They are not real!! You needed my lightsaber? More? You greedy dawg!! Take this!!!");
				((Vader) ai).attack(player);
				player.sendMessage("You have been attacked by Darth Vader!!");
			}
		}

		// Talking to Voldemort
		else if (ai instanceof Voldemort) {
			player.sendMessage(((Voldemort) ai).getDescription());
			player.sendMessage(((Voldemort) ai).getReply());
			((Voldemort) ai).attack(player);
			player.sendMessage("You have been attacked by Voldemort!!");
		}

		// Speak to a HostileMOB
		else if (ai instanceof HostileMOB) {
			int playerHealth = player.getHealth();
			int aiHealth = ((HostileMOB) ai).getDamage();
			if (playerHealth > aiHealth) {
				player.decreaseHealth(aiHealth);
				player.sendMessage("Woah, you were attacked. You lost "
						+ aiHealth + " HPs.");
			} else {
				player.sendMessage("Your health is really low...");
			}
		}
	}

}
