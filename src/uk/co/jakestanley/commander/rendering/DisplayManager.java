package uk.co.jakestanley.commander.rendering;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.Display;
import uk.co.jakestanley.commander.Game3D;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.Strings;

/**
 * Created by jp-st on 13/11/2015.
 */
public class DisplayManager {

    public static void createDisplay(){

        int width = Main.getGame().getDisplayWidth();
        int height = Main.getGame().getDisplayHeight();

        ContextAttribs attributes = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create(new PixelFormat(), attributes);
            Display.setTitle(Strings.GAME_TITLE);
        } catch (LWJGLException e){
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, width, height);

    }

    public static void updateDisplay(){
        Display.sync(60);
        Display.update();
    }

    public static void closeDisplay(){
        Display.destroy();
    }

}
