package uk.co.jakestanley.commander.rendering.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by jp-st on 09/11/2015.
 */
public class GuiRenderer { // TODO make it impossible to draw outside the renderable area

    private int width;
    private int height;

    public GuiRenderer(int width, int height){ // TODO consider that i may need a different renderer for slick and lwjgl
        this.width = width;
        this.height = height;
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
//        for (Iterator<String> iterator = Main.getGame().getGuiController().getMessages().iterator(); iterator.hasNext(); ) {
//            String next = iterator.next();
//            screen.drawString(next, drawX, drawY);
//            drawY = drawY + 10;
//        }
    }
}
