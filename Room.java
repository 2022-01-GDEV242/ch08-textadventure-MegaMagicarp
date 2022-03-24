import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Jake Kymer
 * @version 3.24.2022
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private String detailedDescription;
    private Item item;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, String detailedDescription, Item item) 
    {
        this.description = description;
        this.detailedDescription = detailedDescription;
        this.item = item;
        exits = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Items: " + (item != null? item.getDescription(): "There is nothing here") + "\nExits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) 
        {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * @return returns the detailed description.
     */
    public String getDetailedDescription()
    {
        return detailedDescription;
    }
    
    /**
     * @return Returns the item description
     */
    public String getItemDescription()
    {
        return item.getDescription();
    }
    
    /**
     * @return Returns the item weight.
     */
    public int getItemWeight()
    {
        return item.getWeight();
    }
    
    /**
     * Makes a new Item object and sets the item in the room to be null
     * @return Returns the itemReturn Item.
     */
    public Item getItem()
    {
        Item itemReturn;
        itemReturn = this.item;
        this.item = null;
        return itemReturn;
    }
    
    /**
     * if the room has an item in it, it returns as true, if not it returns as false.
     */
    public boolean hasItem()
    {
        if (item == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}

