import java.util.ArrayList;
/**
 * Write a description of class Player here.
 *
 * @author Jake Kymer
 * @version 3.24.2022
 */
public class Player
{
    private int strength = 50;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private boolean clover = false;
    
    /**
     * Creates a player object with strength an inventory and a weightlimit
     */
    public Player()
    {
        int inventory;
        strength = 50;
        int weightHeld = 0;
        strength = (strength - weightHeld);
    }
    
    /**
     * @return Returns the strength feild from Player.
     */
    public int getStrength()
    {
        return strength;
    }
    
    /**
     * @param 
     * Takes the current rooms item and adds it to Player inventory. If that item is a clover it trips a flag.
     */
    public void takeItem(Item item)
    {
        if (item.getDescription().contains("clover"))
        {
            clover = true;
        }
        inventory.add(item);
    }
    
    /**
     * @return Returns the hasClover boolean.
     */
    public boolean hasClover()
    {
        return clover;
    }
}
