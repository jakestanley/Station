package guicomponents;

import main.Colours;
import main.Display;
import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 27/05/2015.
 */
public class InfoBox extends GuiStatic {

    public InfoBox(){
        super(Display.LEFT_COLUMN_WIDTH, 0, Display.RIGHT_COLUMN_WIDTH, Display.ROOM_BOX_HEIGHT);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        // TODO
    }

    @Override
    public void renderContent(Graphics screen) {
        screen.setColor(Colours.GUI_TEXT); // TODO
    }

    @Override
    public void widgetClicked(int index) {

    }
}
