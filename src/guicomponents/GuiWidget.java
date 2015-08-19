package guicomponents;

import org.newdawn.slick.Graphics;

import java.awt.*;

/**
 * Created by jake on 16/08/15.
 */
public abstract class GuiWidget extends GuiComponent {

    protected boolean set;
    protected int index;
    protected GuiContainer parent;

    public GuiWidget(GuiContainer parent, int index){
        super();
        this.set = false;
        this.parent = parent;
        this.index = index;
    }

    public void click(Point mouse){ // TODO REVISE doesn't actually use mouse
        parent.widgetClicked(index);
    }

    public abstract void render(Graphics screen, int x, int y);

    protected void setCoordinates(int x, int y){
        System.out.println(toString());
        this.x = x;
        this.y = y;
        set = true;
    }

}
