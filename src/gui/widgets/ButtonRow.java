package gui.widgets;

import gui.Component;
import main.Colours;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Iterator;

/**
 * Created by stanners on 12/09/2015.
 */
public class ButtonRow extends Component {

    public static final Color GUI_BACKGROUND    = Colours.GUI_BACKGROUND;
    public static final Color GUI_FOREGROUND    = Colours.GUI_FOREGROUND;
    public static final Color GUI_BORDER        = Colours.GUI_BORDER;
    public static final Color GUI_TEXT          = Colours.GUI_TEXT;

    public ButtonRow(Component parent, int x, int y, int width, int height){
        super(parent, GUI_BACKGROUND, GUI_FOREGROUND, GUI_BORDER, GUI_TEXT, x, y, width, height, 1); // TODO change 1 to appropriate border width
    }

    @Override
    public void init() {
        // add buttons
    }

    @Override
    public void update() {
        // do nothing
    }

    @Override
    public void clickAction() {
        for (Iterator<Component> iterator = children.iterator(); iterator.hasNext(); ) {
            Button next = (Button) iterator.next();
            next.clearSelection();
        }
    }

    @Override
    public void hoverAction() {

    }

    @Override
    public void draw(Graphics screen) {

    }

    public void addButton(Button button){ // resize row and add new button
        children.add(button);
        int buttonWidth = (int) Math.ceil(getWidth() / children.size());
        int xOffset = (getWidth() - (buttonWidth * children.size())) / 2;
        for (int i = 0; i < children.size(); i++) {
            // update new button height and width
            Component c = (Component) children.get(i);
            c.setWidth(buttonWidth);
            c.setX(xOffset + getX() + (i * buttonWidth));
        }
    }

}
