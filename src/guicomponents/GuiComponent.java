package guicomponents;

import main.Colours;
import main.Display;
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

        this.rect = new Rectangle(x * Display.SCALE, y * Display.SCALE, width * Display.SCALE, height * Display.SCALE);
    }

    public abstract void update();

    public abstract void render(Graphics screen);

    protected void drawBackground(Graphics screen){
        screen.setColor(Colours.GUI_BACKGROUND);
        screen.fill(rect);
    }

    protected void drawBorder(Graphics screen){
    // TODO make it so this doesn't draw a border on the screen edges. should be easy to detect screen edge using x
        screen.setColor(Colours.GUI_BORDER);
        screen.draw(rect);
    }



}
