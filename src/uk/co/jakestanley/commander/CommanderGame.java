package uk.co.jakestanley.commander;

import main.*;
import org.newdawn.slick.*;
import uk.co.jakestanley.commander.scene.SceneController;

/**
 * Created by jp-st on 08/11/2015.
 */
public class CommanderGame extends BasicGame {

    public static boolean debug;

    public static SceneController sceneController;
    public static GuiController guiController;

    public CommanderGame(boolean debug){
        super(Strings.GAME_TITLE);
        this.debug = debug;

        // Print some configuration information
        if(this.debug){
            System.out.println("Launching in developer mode");
        } else {
            System.out.println("Launching in normal mode");
        }

        // Initialise the game framework
        try {
            AppGameContainer container = new AppGameContainer(this);
            container.setShowFPS(main.Game.debug);
//            container.setDisplayMode(display.getWidth(), display.getHeight(), false); // TODO set new values
            container.setDisplayMode(1024, 768, false);
            container.setVSync(true); // jesus h christ this needs to be on
            container.setTargetFrameRate(Display.FRAME_RATE); // TODO remove the /2 after testing screen scrolling
            container.start();
        } catch (SlickException e) {
            System.out.println("Failed to start the container");
            e.printStackTrace();
            System.exit(main.Game.EXIT_BAD);
        }

        // Initialise the game objects
        sceneController = new SceneController();
        guiController = new GuiController();

    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    public void render(GameContainer gameContainer, Graphics screen) throws SlickException {
        screen.drawString("Hello world", 10, 10);
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
