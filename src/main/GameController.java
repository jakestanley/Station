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

    public static final int MAX_TICK = 30; // TODO should be 30

    public static int tick = 0;
    public static boolean debug;
    public static boolean disableMobs;

    public static Random            random;
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

        if(debug){
            System.out.println("Launching in developer mode");
        } else {
            System.out.println("Launching in normal mode");
        }

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

        // Initialise meta components
        random          = new Random();

        // Initialise controllers
        viewController  = new ViewController();
        mapController   = new MapController("the_tortuga.csv");
        mapController.init();
        mobController   = new MobController();
        guiController   = new GuiController();
        actionQueue     = new ActionQueue();
        mouseController = new MouseController();

    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        InputController.processInput(gameContainer); // TODO update logic

        guiController.updateContent();

        if(tick > MAX_TICK){
            tick = 0;
            mapController.updateDoors();
            mobController.updateMobs();
            mobController.executeMobEvaluations();
            mobController.executeMobActions();
        }

        tick++;

    }

    @Override
    public void render(GameContainer gameContainer, Graphics screen) throws SlickException {
        guiController.setBackground(screen);
        guiController.renderGrid(screen);

        mapController.renderBackgrounds(screen);
        mapController.renderWalls(screen);
        mapController.renderDoors(screen);
        mapController.renderFunctionals(screen);

        mobController.renderMobs(screen);

        mapController.renderHoverBoxes(screen);

        guiController.renderBackgrounds(screen);
        guiController.renderContent(screen);



    }
}