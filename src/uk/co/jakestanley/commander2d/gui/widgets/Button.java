package uk.co.jakestanley.commander2d.gui.widgets;

import uk.co.jakestanley.commander2d.gui.Component;
import uk.co.jakestanley.commander2d.gui.actions.Action;
import uk.co.jakestanley.commander2d.main.Colours;
import uk.co.jakestanley.commander2d.main.GameController;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by jake on 16/08/15.
 */
public class Button extends Widget {

    public static final Color BG = Color.gray;
    public static final Color FG = Color.white;
    public static final int MAX_WIDTH = 72;
    public static final int MAX_HEIGHT = 36;
    public static final float DARKEN_SCALE = 0.1f;

    private boolean selected;
    private String text;
    private int index;
    private Action action;

    /**
     * No position or dimensions constructor for when another component determines size
     * @param parent
     */
    public Button(Component parent, String text, Action action){
        super(parent, BG, FG, Colours.GUI_BORDER, Colours.GUI_TEXT,
                0, 0, 0, MAX_HEIGHT);
        this.text = text;
        this.action = action;
        this.selected = false;
    }

    @Override
    public void init(){
        // TODO consider setting values
    }

    @Override
    public void update() {
        // TODO
    }

    @Override
    public void clickAction() {
        System.out.println("clicked " + text);
        selected = true;
        action.execute();
        // TODO something with parent
    }

    public void clearSelection(){
        selected = false;
    }

    @Override
    public void hoverAction() {

    }

    public void setIndex(int index){
        this.index = index;
    }

    public void draw(Graphics screen) {

        int textWidth   = GameController.guiController.getFont().getWidth(text);
        int textHeight  = GameController.guiController.getFont().getHeight(text);

        if(!selected){
            screen.setColor(BG);
        } else {
            screen.setColor(new Color(BG).darker(DARKEN_SCALE));
        }
        screen.fillRect(x, y, width, height);
        screen.setColor(Colours.GUI_BORDER);
        screen.drawRect(x, y, width, height);
        screen.setColor(FG);
        screen.drawString(text, x + (width - textWidth) / 2, y + (height - textHeight) / 2); // primitive, but it will look OK
    }

}
