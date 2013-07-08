/**
 * Holds items and gold that can only be accessed if the
 * correct <code>Key</code> is provided.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Items;

public class Chest extends Item {
    private int lock;
    private boolean isLocked;
    private ItemInventory inventory;
    private boolean isEmpty;


    public Chest(String name, int value, int combination) {
        super(name,value);
        this.isLocked = true;
        this.isEmpty = false;
        this.lock = combination;
        this.inventory = new ItemInventory();
        isQuest = false;
    }

    public boolean unlock(Key akey) {
        if (akey.use() == this.lock) {
            this.isLocked = false;
            return true;
        } else
            return false;
    }

    public boolean isLocked() {
        return this.isLocked;
    }


    public boolean isEmpty() {
        if (isLocked())
            return false;
        else if (inventory.size() == 0)
            return true;
        else
            return false;
    }

	@Override
	public void setQuestItem(int level) {
		isQuest = true;
	}

    @Override
    public String display() {
        String itemType = getClass().getName().substring(6);
        String result = "\t|--------------------------------|\n\t";
        result += "|\t<" + itemType + ">\n\t";
        result += "|\t    Name: " + getItemName() + "\n\t";
        result += "|\t   Value: $" + getItemValue() + "\n\t";
        result += "|\t   Empty: " + isEmpty() + "\n\t";
        result += "|\t  Locked: " + isLocked() + "\n\t";
        result += "|--------------------------------|\n\n";
        return result;
    }

} // End of Chest