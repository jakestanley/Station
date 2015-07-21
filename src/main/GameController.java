package main;

import map.MapController;
import mobs.MobController;
import org.newdawn.slick.*;

/**
 * Created by stanners on 21/07/2015.
 */

public class GameController extends BasicGame {

    public static boolean debug;
    public static boolean disableMobs;

    public static GuiController guiController;
    public static MapController mapController;
    public static MobController mobController;

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
        // Initialise controllers
        guiController = new GuiController();
        mapController = new MapController("the_tortuga.csv");
        mobController = new MobController();
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics screen) throws SlickException {
        guiController.setBackground(screen);
        guiController.renderGrid(screen);

        mapController.renderWalls(screen);
        mapController.renderBackgrounds(screen);
    }
}