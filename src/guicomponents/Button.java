package guicomponents;

import main.GameController;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by jake on 16/08/15.
 */
public class Button extends GuiWidget {

    public static final Color BG = Color.gray;
    public static final Color FG = Color.white;
    public static final int MAX_WIDTH = 72;
    public static final int MAX_HEIGHT = 36;

    private int position;
    private int total;
    private int index;
    private String text;

    public Button(GuiContainer parent, int position, int total, String text, int index){
        super(parent, index);
        this.width = MAX_WIDTH;
        this.height = MAX_HEIGHT;
        this.position   = position;
        this.total      = total;
        this.text       = text;
    }

    @Override
    public void init(){
        // TODO consider setting values
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics screen, int x, int y) {

        setCoordinates(x, y);

        int textWidth   = GameController.guiController.getFont().getWidth(text);
        int textHeight  = GameController.guiController.getFont().getHeight(text);

        screen.setColor(BG);
        screen.fillRect(x, y, width, height);
        screen.setColor(FG);
        screen.drawString(text, x + (width - textWidth) / 2, y + (height - textHeight) / 2); // primitive, but it will look OK
    }

}
