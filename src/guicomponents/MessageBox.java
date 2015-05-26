package guicomponents;

import main.Display;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created by stanners on 26/05/2015.
 */
public class MessageBox extends GuiComponent { // TODO consider that update may be redundant

    private ArrayList<String> messages;

    public MessageBox(){
        super(Display.LEFT_COLUMN_WIDTH, Display.getMessageBoxY(), Display.RIGHT_COLUMN_WIDTH, Display.MESSAGE_BOX_HEIGHT);
        messages = new ArrayList<String>();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics screen) {
        drawBackground(screen);
        drawBorder(screen);
    }
}
