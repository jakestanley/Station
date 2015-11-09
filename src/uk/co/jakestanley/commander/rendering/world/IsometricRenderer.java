package uk.co.jakestanley.commander.rendering.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.command.Command;
import uk.co.jakestanley.commander.CommanderGame;
import uk.co.jakestanley.commander.rendering.Renderer;
import uk.co.jakestanley.commander.scene.entities.shapes.Box;

/**
 * Created by jp-st on 08/11/2015.
 */
public class IsometricRenderer extends WorldRenderer {

    public IsometricRenderer(int x, int y, int width, int height){
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

        screen.drawString("IsometricRenderer::render(Graphics) called", 160, 80);
    }

    private void renderDebugging(Graphics screen){
        screen.setColor(Color.red); // TODO use colours resource
        screen.fillRect(x, y, x + width, y + height); // TODO change to an actual isometric renderer
        screen.setColor(Color.lightGray);
        Box playerBox = (Box) CommanderGame.sceneController.getMobileEntities().get(0).getShape();
        screen.fillRect( // TODO fix thiiis
                x + playerBox.getXLocal(), y + playerBox.getZLocal(), // TODO make each component have its own render method. this method should contain very little code.
                playerBox.getXLocal() + playerBox.getWidth(), playerBox.getZLocal() + playerBox.getDepth()); // TODO more enhanced scaling, etc. need to read up on this stuff and really plan
    }
}
