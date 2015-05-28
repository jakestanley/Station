package guicomponents;

import main.Colours;
import main.Display;
import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 27/05/2015.
 */
public class InfoBox extends GuiComponent {

    public InfoBox(){
        super(Display.LEFT_COLUMN_WIDTH, 0, Display.RIGHT_COLUMN_WIDTH, Display.ROOM_BOX_HEIGHT);
    }

    @Override
    public void update() {
        // TODO
    }

    @Override
    public void render(Graphics screen) { // TODO CONSIDER what to show by default
        drawBackground(screen);
        drawBorder(screen);

        screen.setColor(Colours.GUI_TEXT);
        screen.drawString("Test", x + Display.MARGIN, y + Display.MARGIN);

    }
}
