package uk.co.jakestanley.commander;

import org.newdawn.slick.*;
import uk.co.jakestanley.commander.rendering.Renderer;
import uk.co.jakestanley.commander.rendering.gui.GuiRenderer;
import uk.co.jakestanley.commander.rendering.world.twodimensional.TopDownRenderer;

/**
 * Created by jp-st on 08/11/2015.
 */
public class CommanderGame2D extends BasicGame { // TODO put this and CommanderGame3D in their own packages

    public static Renderer worldRenderer;
    public static Renderer guiRenderer;

    public CommanderGame2D() {
        super(Strings.GAME_TITLE);

        // initialise the renderer objects
        worldRenderer = new TopDownRenderer(20, 20, 800, 600);
        guiRenderer = new GuiRenderer(Main.getDisplayWidth(), Main.getDisplayHeight());

        // Initialise the game framework
        try {
            AppGameContainer container = new AppGameContainer(this);
            container.setShowFPS(main.Game.debug);
//            container.setDisplayMode(display.getWidth(), display.getHeight(), false); // TODO set new values
            container.setDisplayMode(Main.getDisplayWidth(), Main.getDisplayHeight(), false);
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
        Main.getSceneController().init();
        Main.getGuiController().init();
        worldRenderer.init();
        guiRenderer.init();
//        inputController.init(); // TODO make it so
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        Main.getSceneController().update();
        Main.getInputController().update(gameContainer); // TODO fix this shitty order
        Main.getGuiController().update();
        worldRenderer.update();
        guiRenderer.update();
    }

    public void render(GameContainer gameContainer, Graphics screen) throws SlickException {
        worldRenderer.render(screen);
        guiRenderer.render(screen);
    }

}
