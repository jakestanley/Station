package uk.co.jakestanley.commander;

import org.newdawn.slick.*;
import uk.co.jakestanley.commander.gui.GuiController;
import uk.co.jakestanley.commander.input.InputController;
import uk.co.jakestanley.commander.rendering.Renderer;
import uk.co.jakestanley.commander.rendering.gui.GuiRenderer;
import uk.co.jakestanley.commander.rendering.world.IsometricRenderer;
import uk.co.jakestanley.commander.rendering.world.WorldRenderer;
import uk.co.jakestanley.commander.scene.SceneController;

/**
 * Created by jp-st on 08/11/2015.
 */
public class CommanderGame extends BasicGame {

    public static boolean debug;

    public static SceneController sceneController;
    public static GuiController guiController;
    public static Renderer worldRenderer;
    public static Renderer guiRenderer;
    public static InputController inputController;

    public CommanderGame(boolean debug){
        super(Strings.GAME_TITLE);
        this.debug = debug;

        // Print some configuration information
        if(this.debug){
            System.out.println("Launching in developer mode");
        } else {
            System.out.println("Launching in normal mode");
        }

        // Initialise the game objects
        sceneController = new SceneController(); // TODO fix order
        guiController = new GuiController(); // TODO gets information data for the gui
        worldRenderer = new IsometricRenderer(20, 20, 800, 600);
        guiRenderer = new GuiRenderer();
        inputController = new InputController();

        // Initialise the game framework
        try {
            AppGameContainer container = new AppGameContainer(this);
            container.setShowFPS(main.Game.debug);
//            container.setDisplayMode(display.getWidth(), display.getHeight(), false); // TODO set new values
            container.setDisplayMode(1024, 768, false);
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
        screen.drawString("Hello world", 10, 10);
        worldRenderer.render(screen);
        guiRenderer.render(screen);
    }

    public static void main(String[] args) {

        // Process arguments
        boolean debug = false;
        for(String arg : args){
            if(arg.equalsIgnoreCase("-debug")){
                debug = true;
            }
        }

        // Instantiate the game
        new CommanderGame(debug);

    }

}
