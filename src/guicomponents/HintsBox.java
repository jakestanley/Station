package guicomponents;

import main.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 26/05/2015.
 */
public class HintsBox extends GuiComponent {

    private StringBuilder hint;

    public HintsBox(StringBuilder hint){ // TODO pass some variables here
        super(0, Display.MAP_HEIGHT, Display.CONTROL_HINTS_BOX_WIDTH, Display.TEXT_PANEL_HEIGHT);
        setMessage(hint);
    }

    public void setMessage(StringBuilder hint){
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
    public void renderBody(Graphics screen) {

        screen.setColor(Color.white); // TODO change to a non hard coded value here
        screen.drawString(hint.toString(), x + Display.MARGIN, y + Display.MARGIN); // TODO check hint is not null

    }
}