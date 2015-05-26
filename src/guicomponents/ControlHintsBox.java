package guicomponents;

import main.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 26/05/2015.
 */
public class ControlHintsBox extends GuiComponent { // TODO CONSIDER renaming to HintsBox ?

    private StringBuilder hint;

    public ControlHintsBox(StringBuilder hint){ // TODO pass some variables here
        super(0, Display.MAP_HEIGHT, Display.CONTROL_HINTS_BOX_WIDTH, Display.TEXT_PANEL_HEIGHT);

        if(hint == null){
            this.hint = new StringBuilder("HINT REFERENCE IS NULL");
        } else {
            this.hint = hint;
        }

    }

    @Override
    public void update() {
        // TODO ??
    }

    @Override
    public void render(Graphics screen) {
        drawBackground(screen);
        drawBorder(screen);
        screen.setColor(Color.white); // TODO change to a non hard coded value here
        screen.drawString(hint.toString(), (x + 2) * Display.BIG_SCALE, (y + 2) * Display.BIG_SCALE); // TODO check hint is not null
    }
}