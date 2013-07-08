/**
 * Gives quests and performs complex interactions related to
 * those quests with regards to the user.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Characters;

import Items.Armor;
import Items.Item;
import Items.ItemInventory;
import Items.Key;

public class PastorPete extends Character {

	private String message;
	private String reply;
	private boolean questAvailable;
	private boolean homelessQuest;

	public PastorPete(String n, String occp) {
		super(n, occp);
		health = 200;
		totalGold = 5000;
		level = 20;
		roomNumber = 103;
		land = "North";
		inventory = new ItemInventory();
		Item churchKey = new Key("Church Key", 10313);
		churchKey.setItemValue(1);
		churchKey.setQuestItem(20);
		inventory.add(churchKey);
		inventory.add(new Armor("God's Toga", 500, 10000));
		questAvailable = true;
		homelessQuest = true;

		message = "Hello there my son, what a beautiful day to be in the presense of the Lord!";
		reply = "You need a haircut";
	}

	public String getMessage() {
		return message;
	}

	public String getReply(int l) {
		if (homelessQuest) {
			return "Do you know Gary?";
		}
		else if (questAvailable) {
			if (l > level) {
				questAvailable = false;
				return "I have some random key, it is of no use to me.";
			}
			return reply;
		} return "You still need to get a haircut";
	}

	public boolean isQuestAvailable() {
		return questAvailable;
	}

	public boolean homelessAvailable() {
		return homelessQuest;
	}

	public void setHomelessAvailable() {
		homelessQuest = false;
	}

	public Item getKey() {
		return inventory.get("Church Key");
	}

	public Item getToga() {
		return inventory.get("God's Toga");
	}


}
