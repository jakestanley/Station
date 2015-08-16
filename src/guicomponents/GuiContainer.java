package guicomponents;

import main.Colours;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.awt.*;

/**
 * For gui components whose size and position is known before initialisation
 * Created by jake on 16/08/15.
 */
public abstract class GuiContainer extends GuiComponent {

    protected boolean valid; // only containers can be valid and invalid
    private Rectangle rect;

    public GuiContainer(int x, int y, int width, int height){
        super();
        this.valid = true;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rect = new Rectangle(x, y, width, height);
    }

    public boolean isValid(){
        return valid;
    }

    public void render(Graphics screen){ // TODO CONSIDER you can override if necessary

        renderBackground(screen);
        renderWidgets(screen);
        renderContent(screen);

    }

    public void renderBackground(Graphics screen){

        screen.setColor(backgroundColour);
        screen.fill(rect); // TODO adjust this to be appropriate for GuiWidgets

        screen.setLineWidth(LINE_WIDTH);
        screen.setColor(Colours.GUI_BORDER); // TODO fix borders
        screen.draw(rect);

    }

    public void renderWidgets(Graphics screen){

        for(int index = 0; index < widgets.size(); index++){
            GuiWidget widget = widgets.get(index);
            int x = this.x + ((width / widgets.size()) * index) + ((width / widgets.size()) / 2) - (widget.getWidth() / 2);
            int y = this.y + height - (height / 4); // TODO make this more standard.
            widget.render(screen, x, y);
        }

    }

    public abstract void renderContent(Graphics screen);

    /**
     * A child widget was clicked
     */
    public abstract void widgetClicked(int index);

    public void click(Point mouse){
        // TODO act upon
    }

}
