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
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private String roomDescription;
    private String bedroomDescription = "It's your bedroom. you feel safe here.";
    private String hallwayDescription = "Being outside of your room is unsettleing."
    + " The hallway is dark and seems to go on forever";
    private String stairsDescription = "Old stairs they dont seem very safe.";
    private String unknownDescription = "Nothing seems familiar here you are surroun"
    + "ded by tall mountains. One looks to be a volcaino it is due north. There is" +
    " a lake due south, a cave due east, and a path heading west.";
    private String lakeDescription;
    private String caveDescription;
    private String volcainoDescription;
    private String pathDescription;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
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
        deep_cave;
      
        // create the rooms
        hallway = new Room("The hall outside your room.");
        bedroom = new Room("In your cosy room, it's a bit messy.");
        staircase = new Room("rickity old stairs, they seem like they could collaps "
        + "at any minute.");
        unknown_world = new Room("An unknown world under your stairs.");
        volcaino = new Room("A blisteringly hot pool of magma.");
        lake = new Room("A tranquil resivuar. There is lots of unknown plantlife " +
        "surounding the pool.");
        path = new Room("A path that leads into deep woods");
        cave = new Room("A chilly cave with water dripping from the celling. You "
        + "hear a faint noise from deep in the cave");
        deep_cave = new Room("A raised section after the curve");
        
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
        cave.setExit("north", deep_cave);

        currentRoom = bedroom;  // start game in bedroom
        
        
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
        while (! finished) 
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
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
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
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    private void lookRoom(Command command)
    {
        System.out.println(roomDescription);
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) 
        {
            System.out.println("There is no door!");
        }
        else 
        {
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
        if(command.hasSecondWord()) 
        {
            System.out.println("Quit what?");
            return false;
        }
        else 
        {
            return true;  // signal that we want to quit
        }
    }
}
