package uk.co.jakestanley.commander;

import org.newdawn.slick.*;
import uk.co.jakestanley.commander.gui.GuiController;
import uk.co.jakestanley.commander.input.InputController;
import uk.co.jakestanley.commander.rendering.Renderer;
import uk.co.jakestanley.commander.rendering.gui.GuiRenderer;
import uk.co.jakestanley.commander.rendering.world.threedimensional.ThreeDimensionalRenderer;
import uk.co.jakestanley.commander.rendering.world.twodimensional.TopDownRenderer;
import uk.co.jakestanley.commander.scene.SceneController;

/**
 * Created by jp-st on 08/11/2015.
 */
public class CommanderGame2D extends BasicGame { // TODO put this and CommanderGame3D in their own packages

    public static boolean debug;
    public static SceneController sceneController;
    public static GuiController guiController;
    public static Renderer worldRenderer;
    public static Renderer guiRenderer;
    public static InputController inputController;

    public static int displayWidth = 1024; // TODO consider moving into a separate display class
    public static int displayHeight = 768;

    public CommanderGame2D() {
        super(Strings.GAME_TITLE);
        this.debug = Main.debug;

        // Print some configuration information
        if(this.debug){
            System.out.println("Launching in developer mode");
        } else {
            System.out.println("Launching in normal mode");
        }

        // Initialise the game objects
        sceneController = Main.sceneController; // TODO fix order
        guiController = Main.guiController; // TODO gets information data for the gui
        worldRenderer = new TopDownRenderer(20, 20, 800, 600);
        guiRenderer = new GuiRenderer(displayWidth, displayHeight);
        inputController = Main.inputController;

        // Initialise the game framework
        try {
            AppGameContainer container = new AppGameContainer(this);
            container.setShowFPS(main.Game.debug);
//            container.setDisplayMode(display.getWidth(), display.getHeight(), false); // TODO set new values
            container.setDisplayMode(displayWidth, displayHeight, false);
            container.setVSync(true); // jesus h christ this needs to be on
            container.setTargetFrameRate(60); //Display.FRAME_RATE); // TODO remove the /2 after testing screen scrolling
            container.start();
        } catch (SlickException e) {
            System.out.println("Failed to start the container");
            e.printStackTrace();
            System.exit(main.Game.EXIT_BAD);
        }

    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        // TODO consider whether this should be the order
        sceneController.init();
        guiController.init();
        worldRenderer.init();
        guiRenderer.init();
//        inputController.init(); // TODO make it so
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        sceneController.update();
        inputController.update(gameContainer); // TODO fix this shitty order
        guiController.update();
        worldRenderer.update();
        guiRenderer.update();
    }

    public void render(GameContainer gameContainer, Graphics screen) throws SlickException {
        worldRenderer.render(screen);
        guiRenderer.render(screen);
    }

}
