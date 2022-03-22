/**
 * Write a description of class object here.
 *
 * @author Jake Kymer
 * @version 3/21/22
 */
public class Item
{
    private int weight;
    private String description;
    private boolean pickedUp;
    /**
     * Constructor for objects of class object
     */
    public Item(int weight, String description)
    {
        this.weight = weight;
        this.description = description;
        pickedUp = false;
    }
    
    public void setPickedUp()
    {
        pickedUp = true;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public boolean getPickedUp()
    {
        return pickedUp;
    }
}