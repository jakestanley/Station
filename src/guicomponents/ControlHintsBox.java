package guicomponents;

import main.Display;
import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 26/05/2015.
 */
public class ControlHintsBox extends GuiComponent {

    public ControlHintsBox(){ // TODO pass some variables here
        super(0, Display.MAP_HEIGHT, Display.CONTROL_HINTS_BOX_WIDTH, Display.CONTROL_HINTS_BOX_HEIGHT);
    }

    @Override
    public void update() {
        // TODO
    }

    @Override
    public void render(Graphics screen) {
        drawBackground(screen);
        drawBorder(screen);
    }
}
