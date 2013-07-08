/**
 * A Key is an Item used to open things: Doors, Chests, Portals,
 * Blouses... that sort of thing. All keys can open only one lock
 * though there may be many things the same lock. Likewise, many
 * there are many keys but only one will unlock...The Boobies.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Items;

public class Key extends Item {
    private int cypher; // I am using a String as the key's "value".

    /**
     * The constructor
     *
     * @param String keyName -- The name of the key "Bedroom key"
     *        String key -- The opening "code" to unlock doors.
     */
    public Key(String keyName, int key) {
        super(keyName, 0);  // For all intents and purposes, Keys do not have a monetary
                            // value.

        this.cypher = key;  // For lack of a better term, key is what the Key object will
    }                       // return when its is 'opening' a door or other item that is locked.


    /**
     * Use the key, in this case, return its cypher code
     *
     *
     * @return String, the Key's cypher which is referenced against a lock
     *           if they match up, the door is unlocked, if not, then its not
     *           the right key.
     */
    public int use() {
        return this.cypher;

    } // End of use


    @Override
    public void setQuestItem(int level) {
        // TODO Auto-generated method stub

    }


    @Override
    public String display() {
        String itemType = getClass().getName().substring(6);
        String result = "\t|--------------------------------|\n\t";
        result += "|\t<" + itemType + ">\n\t";
        result += "|\t    Name: " + getItemName() + "\n\t";
        result += "|\t   Value: $" + getItemValue() + "\n\t";
        result += "|\t  Cypher: " + use() + "\n\t";
        result += "|--------------------------------|\n\n";
        return result;
    }



} // End of Key