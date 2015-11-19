package uk.co.jakestanley.commander.rendering.world.twodimensional;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.world.WorldRenderer;
import uk.co.jakestanley.commander.scene.entities.shapes.Box;

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
        if(Main.getGame().isDebug()){
            renderDebugging(screen);
        }

    }

    private void renderDebugging(Graphics screen){
        screen.setColor(Color.red); // TODO use colours resource
        screen.fillRect(x, y, x + width, y + height); // this is the canvas TODO change to an actual isometric renderer

        Rectangle playerBox = Main.getGame().getSceneController().getMobiles().get(0).getBox();

        float renderAtX = x + playerBox.getX();
        float renderAtY = -(y + playerBox.getY());

//        System.out.println("coordinates: " + renderAtX + ", " + renderAtY + ", size: " + playerBox.getWidth() + " by " + playerBox.getDepth());

//        screen.setColor(Color.lightGray);
//        screen.fillRect( // TODO fix thiiis - x, y, width, height
//                renderAtX, renderAtY, // TODO make each component have its own render method. this method should contain very little code.
//                playerBox.getWidth() * 100, playerBox.getDepth() * 100); // TODO more enhanced scaling, etc. need to read up on this stuff and really plan. scaaaaale
    }
}
