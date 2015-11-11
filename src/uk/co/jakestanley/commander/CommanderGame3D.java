package uk.co.jakestanley.commander;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import uk.co.jakestanley.commander.rendering.Renderer;
import uk.co.jakestanley.commander.rendering.gui.GuiRenderer;
import uk.co.jakestanley.commander.rendering.world.threedimensional.ThreeDimensionalRenderer;
import uk.co.jakestanley.commander.rendering.world.twodimensional.TopDownRenderer;

/**
 * Created by jp-st on 10/11/2015.
 */
public class CommanderGame3D {

    public static ThreeDimensionalRenderer worldRenderer;
    public static Renderer guiRenderer;

    public CommanderGame3D(){

        // initialise the renderer objects
        worldRenderer = new ThreeDimensionalRenderer(20, 20, 800, 600);
        guiRenderer = new GuiRenderer(Main.getDisplayWidth(), Main.getDisplayHeight());

        // set up the display
        try {
            Display.setDisplayMode(new DisplayMode(Main.getDisplayWidth(), Main.getDisplayHeight()));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    public void init(){

        // initialise OpenGL
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Main.getDisplayWidth(), 0, Main.getDisplayHeight(), 1, -1); // TODO make this use the canvas
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        // initialise objects
        Main.getSceneController().init();
        Main.getGuiController().init();
        worldRenderer.init();
        guiRenderer.init();

    }

    public void update()
    {
        Main.getInputController().updateLwjgl();
    }

    public void render(){
        worldRenderer.render(null); // Graphics not required here
        Display.update();
        Display.sync(60); // cap fps to 60fps
    }

}
