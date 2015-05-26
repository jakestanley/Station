package guicomponents;

import main.Colours;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by stanners on 26/05/2015.
 */
public abstract class GuiComponent {

    protected int x, y, width, height; // start point and end point
    private Rectangle rect;

    public GuiComponent(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.rect = new Rectangle(x, y, width, height);
    }

    public abstract void update();

    public abstract void render(Graphics screen);

    protected void drawBackground(Graphics screen){
        screen.setColor(Colours.GUI_BACKGROUND);
        screen.fill(rect);
    }

    protected void drawBorder(Graphics screen){
        screen.setColor(Colours.GUI_BORDER);
        screen.draw(rect);
    }



}
