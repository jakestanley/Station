package guicomponents;

import main.Colours;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;

/**
 * Created by stanners on 26/05/2015.
 */
public abstract class GuiComponent {

    protected static final int LINE_WIDTH = 3;

    protected int x, y, width, height;
    protected Color backgroundColour;
    protected ArrayList<GuiWidget> widgets;

    public GuiComponent(){

        this.backgroundColour = Colours.GUI_BACKGROUND;
        this.widgets = new ArrayList<GuiWidget>(); // TODO CONSIDER can a widget contain widgets? PROBABLY.

    }

    public abstract void init();

    public abstract void update();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight() {
        return height;
    }

}
