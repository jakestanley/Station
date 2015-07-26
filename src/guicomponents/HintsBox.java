package guicomponents;

import main.Display;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import resources.Converter;

import java.awt.*;

/**
 * Created by stanners on 26/05/2015.
 */
public class HintsBox extends GuiComponent {

    private StringBuilder hint;

    public HintsBox(StringBuilder hint){ // TODO pass some variables here
        super(0, Display.MAP_HEIGHT, Display.CONTROL_HINTS_BOX_WIDTH, Display.TEXT_PANEL_HEIGHT);
        setMessage(hint);
    }

    public void setMessage(StringBuilder hint){
        if(hint == null){

            // TODO remove this crap for release
            Point mouse = new Point(Mouse.getX(), Mouse.getY()); // TODO change to use proper input
            int mouseX = (int) mouse.getX();
            int mouseY = (int) mouse.getY();
            Point tile = Converter.mouseToTile(mouse, Converter.OFFSET_ADDED);
            int tileX = (int) tile.getX();
            int tileY = (int) tile.getY();
            this.hint = new StringBuilder("Mouse: " + mouseX + ", " + mouseY + "   Tile: " + tileX + ", " + tileY);

            // this.hint = new StringBuilder(""); // TODO add this back in with some relevant idle text for release

        } else {
            this.hint = hint;
        }
    }

    @Override
    public void update() {
        setMessage(null); // TODO remove after testing
    }

    @Override
    public void renderBody(Graphics screen) {

        screen.setColor(Color.white); // TODO change to a non hard coded value here
        screen.drawString(hint.toString(), x + Display.MARGIN, y + Display.MARGIN); // TODO check hint is not null

    }
}