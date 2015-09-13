package gui;

import main.Colours;
import main.Display;
import main.GameController;
import mobs.Mob;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by stanners on 26/05/2015.
 */
public class MobPanel extends Component { // TODO CONSIDER does it though?

    private Mob mob;
    private int index;
    private String name = "NAME?";
    private Rectangle detection;

    public MobPanel(Component parent, int y, int index, Mob mob){ // TODO top and bottom borders, much simpler though
        super(parent, Colours.GUI_BACKGROUND, Colours.GUI_FOREGROUND, Colours.GUI_BORDER, Colours.GUI_TEXT,
                parent.getX(), y, parent.getWidth(), Display.TEXT_PANEL_HEIGHT, 1); // TODO change 1 to appropriate border width
//        System.out.println("Building mob panel");
        this.index = index;
        this.mob = mob;
        name = mob.getName();
        detection = new Rectangle(GameController.display.getLeftColumnWidth(), y, GameController.display.getRightColumnWidth(), Display.TEXT_PANEL_HEIGHT);
//        System.out.println("Mob panel has dimensions: " + x + ", " + y + ", " + (x + width) + ", " + (y + height));
//        System.out.println("y render: " + y);
//        System.out.println("mob panel index: " + index);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void action() {
        System.out.println("selected " + mob.getName());
    }

    @Override
    public void draw(Graphics screen) {
        renderHealthBar(screen);
//        int scale = Display.BIG_SCALE; // TODO fix it so i don't need to adjust scale here
//

        // DRAW TEXT
        screen.setColor(Colours.GUI_TEXT);
        screen.drawString(name, x + Display.MOB_TEXT_MARGIN, y + 8); // TODO 2 should be a variable like MARGIN or HEALTH_BAR_MARGIN?
        screen.drawString(mob.getGoalString(), x + Display.MOB_TEXT_MARGIN + 120, y + 8); // TODO add non statics

    }

//    @Override
//    public void renderContent(Graphics screen) {
//
//
//    }

    public boolean mouseOver(int mouseX, int mouseY) { // TODO an abstractly or statically accessible method for this TODO today

//        System.out.println("MOUSE: " + mouseX + ", " + mouseY);

        if (mouseX > detection.getMinX() && mouseX < detection.getMaxX() &&
                mouseY > detection.getMinY() && mouseY <= detection.getMaxY()){

            // set background selected colour
            bgCol = Colours.GUI_FOREGROUND;
//            System.out.println("Mouse over");
            return true;
        } else {
            // set regular background colour
            bgCol = Colours.GUI_BACKGROUND;
            return false;
        }
    }

    public Mob getMob(){
        return mob;
    }

    private void renderHealthBar(Graphics screen){

//        screen.scale(Display.BIG_SCALE, Display.BIG_SCALE); // TODO fix so i don't have to keep doing this

        // TODO fix ugliness

        // casting to integer for rough representation on bar as precision is not necessary for this visual element
        int mobHealthBarMod = (int) ((Mob.MAX_HEALTH - mob.getHealth()) / 6); // TODO ensure not below zero and optimise
        // TODO solve this work around. not sure why /6 works, but it does.

        // initialising rectangles // TODO change these static values, such as 2 and index * n
        Rectangle baseRect = new Rectangle(x + Display.MARGIN, y + Display.MARGIN, Display.HEALTH_BAR_WIDTH, Display.HEALTH_BAR_HEIGHT);
        Rectangle healthRect = new Rectangle(x + Display.MARGIN, y + Display.MARGIN + mobHealthBarMod, Display.HEALTH_BAR_WIDTH, Display.HEALTH_BAR_HEIGHT - mobHealthBarMod);

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
