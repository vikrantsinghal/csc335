/**
 * Gets a request from one <code>character</code> to
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

public class GetRequest implements Serializable{

	private Character sender, receiver;
	private Item item;

	public GetRequest(Character receiver, Item item, Character sender ){
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
		return receiver.getName() + " wants " + item.getItemName() + " from you.";
	}

}
