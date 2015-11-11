package uk.co.jakestanley.commander.rendering.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.Renderer;

import java.util.Iterator;

/**
 * Created by jp-st on 09/11/2015.
 */
public class GuiRenderer extends Renderer { // TODO make it impossible to draw outside the renderable area

    public GuiRenderer(int width, int height){ // TODO consider that i may need a different renderer for slick and lwjgl
        super(0, 0, width, height);
    }

    public void init() {

    }

    public void update() {

    }

    public void render(Graphics screen) {
        // TODO CONSIDER can i specify a list of things to draw or something more efficient?
        screen.setColor(Color.white);
        int drawX = 10;
        int drawY = 10;
        for (Iterator<String> iterator = Main.getGuiController().getMessages().iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            screen.drawString(next, drawX, drawY);
            drawY = drawY + 10;
        }
    }
}
