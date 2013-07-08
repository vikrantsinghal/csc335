/**
 * Defines the basic framework for what will represent Items
 * in the MUD.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */


package Items;
import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Item implements Serializable {

	private String itemName;  // Excalibur, Potion of Mighty Recovery, Worn stone
    private int value;        // This represents the items monetary worth
    protected boolean isQuest;  // This way we can have a little more control over
                              // dialog and quest specific items.

	public Item(String name, int worth) {
		itemName = name;
		value = worth;
		isQuest = false;
	}


    /**
     * Checks whether or not the item is a quest item or not.
     * If the item is a quest item, then return true, otherwise
     * returns false
     *
     * @return boolean
     *
     */
    public boolean isQuestItem() {
        return isQuest;
    }


    /**
     * Get the name of the item
     *
     * @return A String consisting of the item's Name
     */
    public String getItemName() {
        return itemName;
    }



	public int getItemValue() {
		return value;
	}


    public void setItemValue(int newValue) {
        value = newValue;
    }


    public abstract String display();


    // /**
    //  * Use this item. Seriously, whats the point otherwise?
    //  * It assumes a return type of Integer, but it can be
    //  * Overridden to be frankly anything, we just have to
    //  * make sure we account for that change.
    //  *
    //  * @return something...probably an int
    //  */
    // public abstract int use();


    /**
     * Sets the isQuest attribute to true, then increases all item stats by a
     * factor of 10 (but that can be changed!!)
     *
     * @param int level -- The desired level you wish the item to be. The higher
     *                      the level, the more powerful / valuable the item will be.
     */
    public abstract void setQuestItem(int level);

}
