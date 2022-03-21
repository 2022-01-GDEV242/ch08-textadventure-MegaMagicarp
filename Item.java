
/**
 * Write a description of class object here.
 *
 * @author Jake Kymer
 * @version (a version number or a date)
 */
public class Item
{
    private int weight;
    private String description;
    /**
     * create an item and set its weight and description.
     */
    public Item(int weight, String description)
    {
        this.weight = weight;
        this.description = description;
    }
    
    /**
     * @return Returns the weight of the item.
     */
    public int getWeight(){
        return weight;
    }
    
    /**
     * @return Returns the item description.
     */
    public String getDescription(){
        return description;
    }
}
