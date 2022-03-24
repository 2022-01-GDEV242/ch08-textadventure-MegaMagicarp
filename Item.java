/**
 * The Item class has two parramiters an int for weight and a String for the description.
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
     * creates an Item object.
     */
    public Item(int weight, String description)
    {
        this.weight = weight;
        this.description = description;
        pickedUp = false;
    }
    
    /**
     * when called changes pickedUp to be true.
     */
    public void setPickedUp()
    {
        pickedUp = true;
    }
    
    /**
     * @return Returns the weight of the item
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * @return Returns the decription of the item.
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * @return Returns the pickedUp boolean.
     */
    public boolean getPickedUp()
    {
        return pickedUp;
    }
}