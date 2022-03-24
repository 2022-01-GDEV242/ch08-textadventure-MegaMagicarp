/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Jake Kymer
 * @version 3.24.2022
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private String roomDescription;
    private Player player;
    private boolean deadMagma;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player();
    }

    /**
     * Play the game outside BlueJ.
     */
    public static void main(String[] args) 
    {
        Game game = new Game();
        game.play();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() 
    {
        Room hallway, bedroom, staircase, unknown_world, volcaino, lake, path, cave,
        deep_cave, nest, temple, treasure_room, lab, kitchen, stairs, hall, Bedroom;
        
        Item bed, dirt, magma, clover, egg, treasure, syringe, nothing;
        // create the items
        bed = new Item(100, "\nBed");
        dirt = new Item(1, "\nDirt");
        magma = new Item(10, "\nMagma");
        clover = new Item(1, "\nFour leaf clover");
        syringe = new Item(5, "\nSyinge");
        treasure = new Item(20,"\nTreasure");
        egg = new Item (10, "\nDragon egg");
        nothing = new Item(99,"\nThere is nothing to pick up");
        // create the rooms
        hallway = new Room("standing in the hall outside your room.", "Being outside of " +
        "your room is unsettleing. The hallway is dark and seems to go on forever", null);
        bedroom = new Room("in your cosy room, it's a bit messy.", 
        "It's your bedroom. You feel safe here.", bed);
        staircase = new Room("climbing down rickity old stairs, they seem like they could " +
        "collaps at any minute.", "Old stairs they dont seem very safe.", null);
        unknown_world = new Room("in an unknown world. You fell through your stairs.", 
        "Nothing seems familiar here you are surrounded by tall mountains. One looks "+
        "to be a volcaino it is due north. There is a lake due south, a cave due east, and a " +
        "path heading west.", dirt);
        volcaino = new Room("standing over a blisteringly hot pool of magma.","Sweat drips from your brow as you stand over a magma pool.", magma);
        lake = new Room("standing in a clearing with a tranquil resivuar. There is lots of unknown plantlife " +
        "surounding the pool.","The water looks chilly, there seems to be an unknown source of ripples coming from the center of the lake.", clover);
        path = new Room("walking along a path that leads into deep woods","You are surrounded buy trees.", null);
        cave = new Room("staring into a chilly cave with water dripping from the celling." ,"There are many stalactights and stalagmites all around the cave. The cave seems to turn at the end.", null);
        deep_cave = new Room("in a raised section after the curve","There is an unknown egg on a ledge.", egg);
        nest = new Room("seeing a huge nest, you wonder if it is home to" +
        "something.", "It's a nest made of sticks and other natural materials.", null);
        temple = new Room("a temple that looks like it was built by an ancent civilization","The temple is very dark inside you can see multiple rooms around you.", null);
        treasure_room = new Room("a well lit treasure room, you are surounded by treasure.","It is more treasure then you have ever seen, you feel like taking a little should be ok.", treasure);
        lab = new Room("in a lab that seems to be used to be used for experiments","You are surrounded by test tubes, one labeled 'teleport' is glowing.", syringe);
        kitchen = new Room("in your kitchen","It feels good to be home.", null);
        stairs = new Room("climbing up the stairs to your room.","Thats weird didnt your stairs collaps out from under you?", null);
        hall = new Room("in the hallway outside your room.","there is a faint glow coming from your room it must be your night light.", null);
        Bedroom = new Room("in your bed room. You brethe a sigh of releaf that was some adventure.","You wake up from your sleep. Man it was all a dream, you check your pockets and find all the stuff you had collected on your adventure, maybe it wasn't a dream after all.", bed);
        
        currentRoom = bedroom;  // start game in bedroom
        
        // initialise room exits
        bedroom.setExit("east", hallway);
        
        hallway.setExit("west", bedroom);
        hallway.setExit("east", staircase);

        staircase.setExit("down", unknown_world);

        unknown_world.setExit("north", volcaino);
        unknown_world.setExit("south", lake);
        unknown_world.setExit("east", cave);
        unknown_world.setExit("west", path);

        volcaino.setExit("south", unknown_world);
        
        lake.setExit("north", unknown_world);
        
        cave.setExit("west", unknown_world);
        cave.setExit("north east", deep_cave);
        
        deep_cave.setExit("south west", cave);
        
        path.setExit("east", unknown_world);
        path.setExit("west", nest);
        
        nest.setExit("north", temple);
        nest.setExit("east", path);
        
        temple.setExit("south", nest);
        temple.setExit("west", treasure_room);
        temple.setExit("east", lab);
        
        lab.setExit("teleport home", kitchen);
        lab.setExit("west", temple);
        
        kitchen.setExit("east", stairs);
        
        stairs.setExit("west", kitchen);
        stairs.setExit("east", hall);
        
        hall.setExit("west", stairs);
        hall.setExit("east", Bedroom);
        
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished && ! deadMagma) 
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() 
    {
        System.out.println();
        System.out.println("Welcome to a mysterious world!");
        System.out.println("This is a new, pretty boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) 
        {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case LOOK:
                lookRoom(command);
                break;
                
            case EAT:
                eat(command);
                break;
                
            case TAKE:
                take(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    /**
     * Adds the item from the room into your inventory. If player tries to pick up magma, the game ends and the player needs to restart. 
     */
    private void take(Command command)
    {
        if (currentRoom.hasItem() == false)
        {
            System.out.println("There is nothing to pick up");
            return;
        }
        if (currentRoom.getItemWeight() == 100)
        {
            System.out.println("Item is too heavy to carry");
        }
        else if(currentRoom.getItemWeight() == 99)
        {
            System.out.println("There is nothing to pick up");
        }
        else
        {
            Item tempItem = currentRoom.getItem();
            if (tempItem.getDescription().contains("Magma"))
            {
                if (player.hasClover())
                {
                    System.out.println("You take the four leaf clover out of your pocket and kiss it hoping it gives you luck. You reach out and try to pick up some magma, but it butns you to a crisp. I guess this worlds clovers aren't lucky.");
                }
                else
                {
                    System.out.println("The magma burns you to a crisp. It was not a good idea to pick up magma. Hmmm maybe if you were lucky...");
                }
                //set end game here
                deadMagma = true;
            }
            else
            {
                player.takeItem(tempItem);
            }
        }
    }
    
    /**
     * prints that you are not hungry right now
     */
    private void eat(Command command) {
        System.out.println("You are not hungry right now.");
    }
    
    /**
     * print the detailed discription of the room you are in
     */
    private void lookRoom(Command command) {
        System.out.println(currentRoom.getDetailedDescription());
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
