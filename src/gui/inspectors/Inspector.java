package gui.inspectors;

import gui.Component;
import gui.widgets.Button;
import main.Colours;
import main.Display;
import main.GameController;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 12/09/2015.
 */
public class Inspector extends Component { // not abstract in case you want to make generic inspectors

    // TODO CONSIDER making the colours the same for inspectors

    public static final Color GUI_BACKGROUND    = Colours.GUI_BACKGROUND;
    public static final Color GUI_FOREGROUND    = Colours.GUI_FOREGROUND;
    public static final Color GUI_BORDER        = Colours.GUI_BORDER;
    public static final Color GUI_TEXT          = Colours.GUI_TEXT;

    public static final Integer DEFAULT = 0;
    public static final Integer BUILD_ROOM = 1;
    public static final Integer PLACE_OBJECTS = 2;

    public Inspector(){
        super(null, GUI_BACKGROUND, GUI_FOREGROUND, GUI_BORDER, GUI_TEXT,
                    GameController.display.getLeftColumnWidth(), Button.MAX_HEIGHT, GameController.display.getRightColumnWidth(), GameController.display.getHeight() - Button.MAX_HEIGHT - Display.TEXT_PANEL_HEIGHT, 1); // TODO change 1 to appropriate border width
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void clickAction() {

    }

    @Override
    public void hoverAction() {

    }

    @Override
    public void draw(Graphics screen) {

    }

}
