package uk.co.jakestanley.commander.rendering.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import uk.co.jakestanley.commander.CommanderGame;
import uk.co.jakestanley.commander.scene.entities.Entity;
import uk.co.jakestanley.commander.scene.entities.shapes.Box;

import java.util.ArrayList;

/**
 * Created by jp-st on 08/11/2015.
 */
public class TopDownRenderer extends WorldRenderer {

    public TopDownRenderer(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    public void init() {
        // TODO
    }

    public void update() {

    }

    public void render(Graphics screen) {

        // render debug data if this is
        if(CommanderGame.debug){
            renderDebugging(screen);
        }

    }

    private void renderDebugging(Graphics screen){
        screen.setColor(Color.red); // TODO use colours resource
        screen.fillRect(x, y, x + width, y + height); // this is the canvas TODO change to an actual isometric renderer

        Box playerBox = (Box) CommanderGame.sceneController.getMobileEntities().get(0).getShape();

        float renderAtX = x + playerBox.getXLocal();
        float renderAtY = -(y + playerBox.getZLocal());


        System.out.println("coordinates: " + renderAtX + ", " + renderAtY + ", size: " + playerBox.getWidth() + " by " + playerBox.getDepth());

        screen.setColor(Color.lightGray);
        screen.fillRect( // TODO fix thiiis - x, y, width, height
                renderAtX, renderAtY, // TODO make each component have its own render method. this method should contain very little code.
                playerBox.getWidth() * 10, playerBox.getDepth() * 10); // TODO more enhanced scaling, etc. need to read up on this stuff and really plan
    }
}
