package uk.co.jakestanley.commander2d.gui;

import uk.co.jakestanley.commander2d.main.Colours;
import uk.co.jakestanley.commander2d.main.Display;
import uk.co.jakestanley.commander2d.main.GameController;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;
import uk.co.jakestanley.commander2d.resources.Converter;

import java.awt.*;

/**
 * Created by stanners on 26/05/2015.
 */
public class HintsBox extends uk.co.jakestanley.commander2d.gui.Component {

    private StringBuilder hint;

    public HintsBox(uk.co.jakestanley.commander2d.gui.Component parent, StringBuilder hint){ // TODO pass some variables here
        super(parent,
                Colours.GUI_BACKGROUND, Colours.GUI_FOREGROUND, Colours.GUI_BORDER, Colours.GUI_TEXT,
                0, GameController.display.getHeight() - Display.TEXT_PANEL_HEIGHT, GameController.display.getWidth(), GameController.display.getHeight(), 1); // TODO change 1 to appropriate border width
//        super(0, Display.MAP_HEIGHT, Display.CONTROL_HINTS_BOX_WIDTH, Display.TEXT_PANEL_HEIGHT);
        setMessage(hint);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        setMessage(null); // TODO remove after testing
    }

    @Override
    public void clickAction() {

    }

    @Override
    public void hoverAction() {

    }

    @Override
    public void draw(Graphics screen) {
        screen.drawString(hint.toString(), x + 8, y + 8);
    }

//    @Override
//    public void renderContent(Graphics screen) {
//        screen.setColor(Color.white); // TODO change to a non hard coded value here
//        screen.drawString(hint.toString(), x + Display.MARGIN, y + Display.MARGIN); // TODO check hint is not null
//    }

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

}