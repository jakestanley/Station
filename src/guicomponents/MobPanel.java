package guicomponents;

import main.Colours;
import main.Display;
import mobs.Mob;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by stanners on 26/05/2015.
 */
public class MobPanel extends GuiComponent {

    private Mob mob;
    private String name = "NAME?";
    private Rectangle detection;

    public MobPanel(int y, Mob mob){ // TODO top and bottom borders, much simpler though
        super(Display.LEFT_COLUMN_WIDTH, y, Display.RIGHT_COLUMN_WIDTH, Display.TEXT_PANEL_HEIGHT); // TODO CONSIDER parent panel?
        System.out.println("Building mob panel");
        this.mob = mob;
        name = mob.getName();
        detection = new Rectangle(Display.LEFT_COLUMN_WIDTH, y, Display.RIGHT_COLUMN_WIDTH, Display.TEXT_PANEL_HEIGHT); // TODO consider set scale for now? hmm
//        System.out.println("Mob panel has dimensions: " + x + ", " + y + ", " + (x + width) + ", " + (y + height));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics screen) {

        int scale = Display.SCALE;

        drawBackground(screen); // TODO optimise. not necessary if unselected

        // DRAW TOP AND BOTTOM BORDERS
        screen.setColor(Colours.GUI_BORDER);
        screen.drawLine(x * scale, y * scale, (x + width) * scale, y * scale);
        screen.drawLine(x * scale, (y + height) * scale, (x + width) * scale, (y + height) * scale);

        // DRAW TEXT
        screen.setColor(Colours.GUI_TEXT);
        screen.drawString(name, (x + 2) * scale, (y + 2) * scale);
    }

    public boolean mouseOver(Input input) { // TODO an abstractly or statically accessible method for this

        float mouseX = input.getMouseX() / Display.SCALE;
        float mouseY = input.getMouseY() / Display.SCALE;

//        System.out.println("MOUSE: " + mouseX + ", " + mouseY);

        if (mouseX > detection.getMinX() && mouseX < detection.getMaxX() &&
                mouseY > detection.getMinY() && mouseY < detection.getMaxY()){

            // set background selected colour
            backgroundColour = Colours.GUI_BACKGROUND_SELECTED;
//            System.out.println("Mouse over");
            return true;
        } else {
            // set regular background colour
            backgroundColour = Colours.GUI_BACKGROUND;
            return false;
        }
    }

    public void setSelected(){
        mob.setSelected();
    }

}
