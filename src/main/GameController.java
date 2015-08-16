package main;

import map.MouseController;
import map.MapController;
import mobs.MobController;
import org.newdawn.slick.*;

import java.util.Random;

/**
 * Created by stanners on 21/07/2015.
 */

public class GameController extends BasicGame {

    public static final int MAX_TICK = 6; // TODO should be 30

    public static int tick = 0;
    public static boolean debug;
    public static boolean disableMobs;
    public static boolean paused;
    public static boolean multiplayer = false;

    public static Random            random;
    public static ContextController contextController;
    public static ViewController    viewController;
    public static GuiController     guiController;
    public static MapController     mapController;
    public static MobController     mobController;
    public static ActionQueue       actionQueue;
    public static MouseController   mouseController;

    public GameController(String gameName, boolean debug, boolean disableMobs){
        super(gameName);
        this.debug = debug;
        this.disableMobs = disableMobs;
        this.paused = false;

        if(debug){
            System.out.println("Launching in developer mode");
        } else {
            System.out.println("Launching in normal mode");
        }

        // initialise game objects

        // Initialise meta components
        random          = new Random();

        // Initialise controllers
        contextController = new ContextController(ContextController.CONSTRUCTION);
        viewController  = new ViewController();
        mapController   = new MapController("the_tortuga.csv");
        mobController   = new MobController();
        guiController   = new GuiController();
        actionQueue     = new ActionQueue();
        mouseController = new MouseController();

        // Initialise the game framework
        try {
            AppGameContainer container = new AppGameContainer(this);
            container.setShowFPS(debug);
            container.setDisplayMode(Display.DISPLAY_WIDTH, Display.DISPLAY_HEIGHT, false);
            container.setVSync(true); // jesus h christ this needs to be on
            container.setTargetFrameRate(Display.FRAME_RATE); // TODO remove the /2 after testing screen scrolling
            container.start();
        } catch (SlickException e) {
            System.out.println("Failed to start the container");
            e.printStackTrace();
            System.exit(0);
        }

    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

        // post construction initialisation
        mapController.init();
        mobController.init();
        guiController.init();

    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        InputController.processInput(gameContainer); // TODO update logic

        // if the game is paused, don't run any game logic. useful for dialog boxes also
        if(!paused){

            // TODO update animation frames

            // execute new actions, etc
            if(tick > MAX_TICK){
                tick = 0;
                mapController.updateDoors();
                mobController.updateMobs();
                mobController.executeMobEvaluations(); // TODO use DecisionEngine
                mobController.executeMobActions();
            }

            tick++;

        }

        mapController.updateTiles(); // for the selection only really


        // get and display data last
        guiController.updateContent(); // TODO last?

    }

    @Override
    public void render(GameContainer gameContainer, Graphics screen) throws SlickException {
        guiController.setBackground(screen);
        guiController.renderGrid(screen); // TODO CONSIDER that it looks a lot better with the grid off.

        mapController.renderBackgrounds(screen);
        mapController.renderWalls(screen);
        mapController.renderDoors(screen);
        mapController.renderFunctionals(screen);

        mobController.renderMobs(screen);

        mapController.renderHoverBoxes(screen);

        guiController.render(screen);

    }

    public static void pause(){
        paused = true;
    }

    public static void unPause(){ // TODO CONSIDER that other objects may require the game to still be paused, e.g multiple dialog boxes
        paused = false;
    }

}