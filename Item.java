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
    /**
     * Constructor for objects of class object
     */
    public Item(int weight, String description)
    {
        this.weight = weight;
        this.description = description;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public String getDescription()
    {
        return description;
    }
}