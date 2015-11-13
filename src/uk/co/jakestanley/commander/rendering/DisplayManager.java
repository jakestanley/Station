package uk.co.jakestanley.commander.rendering;

import lombok.Getter;
import main.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.Display;
import uk.co.jakestanley.commander.Main;

/**
 * Created by jp-st on 13/11/2015.
 */
public class DisplayManager {

    @Getter private static int displayWidth = 1024; // TODO consider moving into a separate display class
    @Getter private static int displayHeight = 768;

    public static void createDisplay(){

        ContextAttribs attributes = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
            Display.create(new PixelFormat(), attributes);
            Display.setTitle(Main.getGame().GAME_NAME);
        } catch (LWJGLException e){
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, displayWidth, displayHeight);

    }

    public static void updateDisplay(){
        Display.sync(60);
        Display.update();
    }

    public static void closeDisplay(){
        Display.destroy();
    }

}
