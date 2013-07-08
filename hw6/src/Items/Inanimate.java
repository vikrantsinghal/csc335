/**
 * This class is for generic inanimate items, such as Rocks, rings, boobies, keys, etc.
 * Generally speaking, these items will only return their value, meaning they can be
 * to be exchanged for gold.
 *
 * @author Kyle Almryde
 *
 */

package Items;

@SuppressWarnings("serial")
public class Inanimate extends Item {

	public Inanimate(String itemName, int value) {
		super(itemName, value);
	}

	public int use() {
		return getItemValue();
	}

	public void setQuestItem(int level) {
		setItemValue(getItemValue()*level);
		this.isQuest = !this.isQuest;
	}

    @Override
    public String display() {
        String itemType = getClass().getName().substring(6);
        String result = "\t|--------------------------------|\n\t";
        result += "|\t<" + itemType + ">\n\t";
        result += "|\t    Name: " + getItemName() + "\n\t";
        result += "|\t   Value: $" + getItemValue() + "\n\t";
        result += "|--------------------------------|\n\n";
        return result;
    }

} // End of Inanimate extends Item