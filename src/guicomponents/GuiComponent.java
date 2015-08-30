package guicomponents;

import main.Colours;
import org.newdawn.slick.Color;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by stanners on 26/05/2015.
 */
public abstract class GuiComponent {

    public static final int TYPE_DIALOG     = 0;
    public static final int TYPE_STATIC     = 1;
    public static final int TYPE_BUTTON     = 2;
    public static final int TYPE_TEXTFIELD  = 3;

    protected static final int LINE_WIDTH = 3;

    protected int x, y, width, height, type;
    protected Color backgroundColour;

    protected ArrayList<GuiWidget> widgets;
    protected ArrayList<GuiWidget> buttons;

    public GuiComponent(){

        this.backgroundColour = Colours.GUI_BACKGROUND;
        this.widgets = new ArrayList<GuiWidget>();
        this.buttons = new ArrayList<GuiWidget>();
        setType();

    }

    public abstract void init();

    public abstract void update();

    public abstract void click(Point mouse);

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

    public boolean isMouseOver(Point mouse){
        int mouseX = (int) mouse.getX();
        int mouseY = (int) mouse.getY();
        return (x <= mouseX) && (mouseX <= x + width) && (y <= mouseY) && (mouseY <= y + height);
    }

    public int getType(){
        return type;
    }

    protected abstract void setType();

}
