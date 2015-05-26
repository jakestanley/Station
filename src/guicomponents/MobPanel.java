package guicomponents;

import main.Colours;
import main.Display;
import mobs.Mob;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by stanners on 26/05/2015.
 */
public class MobPanel extends GuiComponent {

    private Mob mob;
    private int index;
    private String name = "NAME?";
    private Rectangle detection;

    public MobPanel(int y, int index, Mob mob){ // TODO top and bottom borders, much simpler though
        super(Display.LEFT_COLUMN_WIDTH, y, Display.RIGHT_COLUMN_WIDTH, Display.TEXT_PANEL_HEIGHT); // TODO CONSIDER parent panel?
//        System.out.println("Building mob panel");
        this.index = index;
        this.mob = mob;
        name = mob.getName();
        detection = new Rectangle(Display.LEFT_COLUMN_WIDTH, y, Display.RIGHT_COLUMN_WIDTH, Display.TEXT_PANEL_HEIGHT); // TODO consider set scale for now? hmm
//        System.out.println("Mob panel has dimensions: " + x + ", " + y + ", " + (x + width) + ", " + (y + height));
//        System.out.println("y render: " + y);
        System.out.println("mob panel index: " + index);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics screen) {

        // DRAW BACKGROUND
        drawBackground(screen); // TODO optimise. not necessary if unselected


        renderHealthBar(screen);
        int scale = Display.BIG_SCALE; // TODO fix it so i don't need to adjust scale here

        // DRAW TOP AND BOTTOM BORDERS
        screen.setColor(Colours.GUI_BORDER);
        screen.drawLine(x * scale, y * scale, (x + width) * scale, y * scale);
        screen.drawLine(x * scale, (y + height) * scale, (x + width) * scale, (y + height) * scale);

        // DRAW TEXT
        screen.setColor(Colours.GUI_TEXT);
        screen.drawString(name, (x + Display.MOB_TEXT_MARGIN) * scale, (y + 2) * scale); // TODO 2 should be a variable like MARGIN or HEALTH_BAR_MARGIN?
    }

    public boolean mouseOver(Input input) { // TODO an abstractly or statically accessible method for this

        float mouseX = input.getMouseX() / Display.BIG_SCALE;
        float mouseY = input.getMouseY() / Display.BIG_SCALE;

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

    private void renderHealthBar(Graphics screen){

//        screen.scale(Display.BIG_SCALE, Display.BIG_SCALE); // TODO fix so i don't have to keep doing this

        // TODO fix ugliness

        // get health values
        float mobHealth = mob.getHealth(); // TODO to int
        // casting to integer for rough representation on bar as precision is not necessary for this visual element
        int mobHealthBarMod = (int) (Mob.MAX_HEALTH - mobHealth) / 5; // TODO ensure not below zero and optimise

        // initialising rectangles // TODO change these static values, such as 2 and index * n
        Rectangle baseRect = new Rectangle((x + 2) * Display.BIG_SCALE, (y + 2) * Display.BIG_SCALE, Display.HEALTH_BAR_WIDTH, Display.HEALTH_BAR_HEIGHT);
        Rectangle healthRect = new Rectangle((x + 2) * Display.BIG_SCALE, (y + 2) * Display.BIG_SCALE + mobHealthBarMod, Display.HEALTH_BAR_WIDTH, Display.HEALTH_BAR_HEIGHT - mobHealthBarMod);

        // render base rect
        screen.setColor(Color.darkGray); // TODO access this from some value resource
        screen.fill(baseRect);

        // render health bar fills
        // TODO CONSIDER this colour hard coding. red and yellow for low health? use text colour to indicate allegience instead?
        if(mob.getType() == Mob.TYPE_MATE){
            screen.setColor(Color.yellow);
            screen.fill(healthRect); // actual health bar angle
        } else {
            screen.setColor(Color.red);
            screen.fill(healthRect); // actual health bar angle
        }

//        screen.scale(Display.SMALL_SCALE, Display.SMALL_SCALE);

        // TODO render the gradient properly and accurately with non static/hard coded values
        // TODO clean all this shit up to be honest
    }

}
