/**
 * Sends a request from one <code>character</code> to
 * another within the same room, specifically for the
 * interaction of trading items
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */


package Model;

import java.io.Serializable;

import Characters.Character;
import Items.Item;

public class GiveRequest implements Serializable{

	private Character sender, receiver;
	private Item item;

	public GiveRequest(Character sender, Item item, Character receiver){
		this.sender = sender;
		this.item = item;
		this.receiver = receiver;
	}

	public Character getReceiver(){
		return receiver;
	}

	public Item getItem(){
		return item;
	}

	public Character getSender(){
		return sender;
	}

	public String getDescription(){
		return sender.getName() + " wants to give you " + item.getItemName();
	}

}
