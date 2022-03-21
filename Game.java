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
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }

    /**
     * Play the game outside BlueJ.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room hallway, bedroom, staircase, unknown_world, volcaino, lake, path, cave,
        deep_cave, nest, temple, treasure_room, lab, kitchen, stairs, hall, Bedroom;
      
        // create the rooms
        hallway = new Room("standing in the hall outside your room.", "Being outside of your room is unsettleing."
        + " The hallway is dark and seems to go on forever");
        bedroom = new Room("in your cosy room, it's a bit messy.", "It's your bedroom. You feel safe here.");
        staircase = new Room("climbing down rickity old stairs, they seem like they could collaps "
        + "at any minute.", "Old stairs they dont seem very safe.");
        unknown_world = new Room("in an unknown world. You fell through your stairs.", 
        "Nothing seems familiar here you are surrounded by tall mountains. One looks "+
        "to be a volcaino it is due north. There is a lake due south, a cave due east, and a path heading west.");
        volcaino = new Room("standing over a blisteringly hot pool of magma.","");
        lake = new Room("standing in a clearing with a tranquil resivuar. There is lots of unknown plantlife " +
        "surounding the pool.","");
        path = new Room("walking along a path that leads into deep woods","");
        cave = new Room("staring into a chilly cave with water dripping from the celling. You "
        + "hear a faint noise from deep in the cave","");
        deep_cave = new Room("in a raised section after the curve","");
        nest = new Room("seeing a huge nest, you wonder if it is home to something","");
        temple = new Room("","");
        treasure_room = new Room("","");
        lab = new Room("","");
        kitchen = new Room("","");
        stairs = new Room("","");
        hall = new Room("","");
        Bedroom = new Room("","");
        
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
    public void play() {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
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
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
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
