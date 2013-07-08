/**
 * The Portal is a special object which magically transports the user to a
 * random location on the map.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Items;

import java.util.Random;

public class Portal extends Item {
	private int roomNumber;
	private String land;
	private String[] locationList = { "North", "South", "East", "West" };
	private Random randGen = new Random();

	public Portal(String name, int value) {
		super(name, value);
		this.land = locationList[randGen.nextInt(4)];

		if (land.equals("North"))
			this.roomNumber = 100 + randGen.nextInt(10);
		else if (land.equals("South"))
			this.roomNumber = 300 + randGen.nextInt(10);
		else if (land.equals("East"))
			this.roomNumber = 200 + randGen.nextInt(10);
		else if (land.equals("West"))
			this.roomNumber = 400 + randGen.nextInt(10);
	}

	public Portal(String name, int value, String local, int room) {
		super(name, value);
		this.land = local;
		this.roomNumber = room;
	}

	public int getRoom() {
		return roomNumber;
	}

	public String getLand() {
		return land;
	}

	@Override
	public void setQuestItem(int level) {

	}



    @Override
    public String display() {
        String itemType = getClass().getName().substring(6);
        String result = "\t|--------------------------------|\n\t";
        result += "|\t<" + itemType + ">\n\t";
        result += "|\t    Name: " + getItemName() + "\n\t";
        result += "|\t   Value: $" + getItemValue() + "\n\t";
        result += "|\t    Land: " + getLand() + "\n\t";
        result += "|\t    Room: " + getRoom() + "\n\t";
        result += "|--------------------------------|\n\n";
        return result;
    }
} // End of Portal extends Item