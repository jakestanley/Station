package main;

import io.InputController;
import map.MapController;
import map.MouseController;
import mobs.MobController;
import org.newdawn.slick.*;

import java.util.Random;

/**
 * Created by stanners on 21/07/2015.
 */

public class GameController extends BasicGame {

    public static final int MAX_TICK = 6; // TODO should be 30

    public static int tick = 0;

    public static Random            random;
    public static InputController   inputController;
    public static ContextController contextController;
    public static ViewController    viewController;
    public static GuiController     guiController;
    public static MapController     mapController;
    public static MobController     mobController;
    public static ActionQueue       actionQueue;
    public static MouseController   mouseController;

    public GameController(String gameName){
        super(gameName);

        if(Game.debug){
            System.out.println("Launching in developer mode");
        } else {
            System.out.println("Launching in normal mode");
        }

        // initialise game objects

        // Initialise meta components
        random          = new Random();

        // Initialise controllers
        inputController     = new InputController();
        contextController   = new ContextController(ContextController.CONSTRUCTION); // TODO change to generic after adding context buttons
        viewController      = new ViewController();
        mapController       = new MapController("the_tortuga.csv");
        mobController       = new MobController();
        guiController       = new GuiController();
        actionQueue         = new ActionQueue();
        mouseController     = new MouseController();

        // Initialise the game framework
        try {
            AppGameContainer container = new AppGameContainer(this);
            container.setShowFPS(Game.debug);
            container.setDisplayMode(Display.DISPLAY_WIDTH, Display.DISPLAY_HEIGHT, false);
            container.setVSync(true); // jesus h christ this needs to be on
            container.setTargetFrameRate(Display.FRAME_RATE); // TODO remove the /2 after testing screen scrolling
            container.start();
        } catch (SlickException e) {
            System.out.println("Failed to start the container");
            e.printStackTrace();
            System.exit(Game.EXIT_BAD);
        }

    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

        // sort the input controller out
        Input input = gameContainer.getInput();
        input.addKeyListener(inputController);
        input.addMouseListener(mouseController);
        inputController.setInput(input);

        // post construction initialisation
        mapController.init();
        mobController.init();
        guiController.init();

    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        inputController.processInput(gameContainer); // TODO remove and work with the listener

        // if the game is paused, don't run any game logic. useful for dialog boxes also
        if(!Game.paused){

            // TODO update animation frames

            // execute new actions, etc
            if(tick > MAX_TICK){
                tick = 0;
                mapController.updateRooms();
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

        // render debug text
        if(Game.debug){
            mapController.renderRoomData(screen);
        }

    }

    public static void pause(){
        Game.paused = true;
    }

    public static void unPause(){ // TODO CONSIDER that other objects may require the game to still be paused, e.g multiple dialog boxes
        Game.paused = false;
    }

}