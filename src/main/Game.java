package main;

/**
 * Created by stanners on 22/05/2015.
 */
public class Game {

    public static final int EXIT_GOOD   = 0;
    public static final int EXIT_BAD    = 1;

    public static boolean debug = false;
    public static boolean pause = false;
    public static boolean mobs = true;
    public static boolean won = false;
    public static boolean paused = false;
    public static boolean multiplayer = false;

    public static int MAX_TICK = 30; // if this is lower than the highest animation loop length, shit will get fucked up

    public static void main(String args[]){

        // Process arguments
        for(int i = 0; i < args.length; i++){
            if(args[i].equalsIgnoreCase("-debug")){
                Game.debug = true;
            } else if(args[i].equalsIgnoreCase("-mobsOff")){
                Game.mobs = false; // TODO fix
            }
        }

        // Initialise GameController
        GameController gameController = new GameController("Space Commander: Battlecruiser", new Display());

    }

}
