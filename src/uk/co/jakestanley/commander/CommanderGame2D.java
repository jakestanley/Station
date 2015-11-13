package uk.co.jakestanley.commander;

import org.newdawn.slick.*;
import uk.co.jakestanley.commander.rendering.Renderer;
import uk.co.jakestanley.commander.rendering.gui.GuiRenderer;
import uk.co.jakestanley.commander.rendering.world.twodimensional.TopDownRenderer;
import uk.co.jakestanley.commander.slick.Loop;

/**
 * Created by jp-st on 08/11/2015.
 */
public class CommanderGame2D extends CommanderGame { // TODO put this and CommanderGame3D in their own packages

    // slick components
    private Loop slick;
    private GameContainer gameContainer;
    private Graphics screen;

    // my renderers
    private Renderer worldRenderer;
    private Renderer guiRenderer;

    public CommanderGame2D(boolean debug) {
        super(debug, RENDER_IN_2D);

        // initialise the renderer objects
        worldRenderer = new TopDownRenderer(20, 20, 800, 600);
        guiRenderer = new GuiRenderer(getDisplayWidth(), getDisplayHeight());

    }

    public void initSpecifics() {
        slick = new Loop(this);
        gameContainer = slick.getGameContainer();
        screen = slick.getGraphics();
    }

    public void initRenderers() {
        worldRenderer.init();
        guiRenderer.init();
    }

    protected void updateInput() {
        getInputController().update(gameContainer);
    }

    public void updateRenderers() {
        worldRenderer.update();
        guiRenderer.update();
    }

    public void render() {
        worldRenderer.render(screen);
        guiRenderer.render(screen);
    }

    public boolean hasCloseCondition() {
        return false;
    }

    public void close() {

    }

    // TODO fix as this is broken

}
