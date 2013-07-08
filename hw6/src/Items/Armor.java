/**
 *  This class represents the basic structure of Armor which can
 *  be worn by any type of character. Armor is worn in order to
 *  protect the wearer of ***PHYSICAL DAMAGE*** It provides this
 *  protection by absorbing damage delieverd to the wearer. It is
 *  therefore important that the wearer be sure to equip said armor.
 *
 * @author Kyle Almryde
 * @author Jimmy Chav
 * @author Josh Fry
 * @author Vikrant Singhal
 */

package Items;

public class Armor extends Item {
    private int armorPool;  // The maximum amount of damage the armor
                            // can absorb befoer it becomes useless

    private boolean equipped; // A boolean to indicate if its equipped or not
    private String quality;  // This is for the 'look' command. Depending on the current
                             // value of the armor's current pool compared to its base value,
                             // the quality will either be "Excellent", "Good", "Damaged",
                             // or "Poor".

    private int armorBase;  // This is the same as armorPool save that armorPool can
                            // can be reduced through damage. Assuming the Character
                            // wanted to repair their armor, this value would be
                            // referenced in order to reset the armor's original value

    /**
     *  The constructor for an Armor object.
     *
     * @param String armorName -- Name of the armor
     *
     *        int armorRating -- How much punishment it can take
     *
     *        int value -- How much the armor is worth
     */
    public Armor(String armorName, int armorRating, int value) {
        super(armorName, value);
        this.armorBase = armorRating;
        this.armorPool = armorRating;
        this.quality = "Excellent";
        this.equipped = false;
        this.isQuest = false;
    }


    /**
     *  Get the current value of the armor. This is not the armorBase
     *  but the armorPool.
     *
     * @return int The return value is the current armorPool;
     */
    public int getArmorRating() {
        return armorPool;

    } // End of getArmorRating



    public int getArmorBase() {
        return this.armorBase;
    }

    /**
     * Determine the current quality of this piece of armor.
     * Quality is determined by the percentage less than the
     * armorBase value in increments of 25, ie if the base is 100,
     * and the current pool is 75, the armor would be considered
     * "Good" quality. Likewise, if the base was 100, and the
     * current pool was 90, it would maitain its "Excellent" status.
     *
     * @return A String, "Excellent", "Good", "Poor", "Broken", or "Useless"
     */
    public String checkArmorQuality() {
        int excellent = armorBase - (armorBase * 0);
        int good = (int) (armorBase - (armorBase * 0.25));
        int poor = (int) (armorBase - (armorBase * 0.50));
        int broken = (int) (armorBase - (armorBase * 0.75));

       if (armorPool == 0) {
           this.quality = "Useless";
        } else if (armorPool <= broken) {
            this.quality = "Broken";
        } else if (armorPool <= poor) {
            this.quality = "Poor";
        } else if (armorPool <= good) {
            this.quality = "Good";
        } else {
            this.quality = "Excellent";
        }
        return this.quality;
    } // End of checkArmorQuality



    /**
     * This applies damage to the armor, specifically the
     * pool. Should the amount of damage received be greater
     * than the armor itself, that damage gets rolled over and
     * applied to the character.
     *
     * @param int damage -- A value to be subtracted from the armorPool
     *
     * @return int -- Any residual damage over that of the armorPools rating
     *              typically this value will be zero, but it could be something
     *              greater than 0, however it will never be a negative value.
     */
    public int takeDamage(int damage) {
        int splash = 0;  // In case the damage Splashes over...
        if (armorPool - damage < 0) {
            splash = (damage - armorPool);
            this.armorPool = 0;
        } else {
            this.armorPool -= damage;
        }
        return splash;
    }


    public void setEquipped() {
        if (this.equipped)
            this.equipped = false;
        else
            this.equipped = true;
    }


    public boolean isEquipped() {
            return this.equipped;
    }


    @Override
    public String display() {
        String itemType = getClass().getName().substring(6);
        String result = "\t|--------------------------------|\n\t";
        result += "|\t<" + itemType + ">\n\t";
        result += "|\t    Name: " + getItemName() + "\n\t";
        result += "|\t   Value: $" + getItemValue() + "\n\t";
        result += "|\t  Rating: " + getArmorRating() + "\n\t";
        result += "|\t Quality: " + checkArmorQuality() + "\n\t";
        result += "|\tEquipped: " + isEquipped() + "\n\t";
        result += "|--------------------------------|\n\n";
        return result;
    }


    @Override
    public void setQuestItem(int level) {
        // TODO Auto-generated method stub

    }



} // End of Armor