import java.util.ArrayList;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    private int strength = 50;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private boolean clover = false;
    
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        int inventory;
        strength = 50;
        int weightHeld = 0;
        strength = (strength - weightHeld);
    }
    
    public int getStrength()
    {
        return strength;
    }
    
    public void takeItem(Item item)
    {
        if (item.getDescription().contains("clover"))
        {
            clover = true;
        }
        inventory.add(item);
    }
    
    public boolean hasClover()
    {
        return clover;
    }
}
