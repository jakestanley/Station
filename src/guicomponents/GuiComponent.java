package guicomponents;

import main.Colours;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by stanners on 26/05/2015.
 */
public abstract class GuiComponent {

    protected static final int LINE_WIDTH = 3;

    protected int x, y, width, height; // start point and end point
    protected Color backgroundColour;
    private Rectangle rect;
    private boolean valid;

    public GuiComponent(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.valid = true;

        this.backgroundColour = Colours.GUI_BACKGROUND;
        this.rect = new Rectangle(x, y, width, height);
    }

    public boolean isValid(){
        return valid;
    }

    public void destroy(){
        onClose();
    }

    public abstract void update();

    public void renderBackground(Graphics screen){ // TODO CONSIDER you can override if necessary

        screen.setColor(backgroundColour);
        screen.fill(rect);

        screen.setLineWidth(LINE_WIDTH);
        screen.setColor(Colours.GUI_BORDER); // TODO fix borders
        screen.draw(rect);

    }

    public abstract void renderBody(Graphics screen);

    protected abstract void onClose();

}
