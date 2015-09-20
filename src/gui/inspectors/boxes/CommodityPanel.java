package gui.inspectors.boxes;

import gui.Component;
import gui.widgets.Button;
import gui.widgets.ButtonRow;
import main.Colours;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by stanners on 20/09/2015.
 */
public class CommodityPanel extends Component {

    protected static final int HEIGHT           = 64;
    protected static final int TEXT_X           = HEIGHT + 4;
    protected String topString;
    protected String bottomString;
    protected ButtonRow menu;
    protected Image image;
    protected int xImageMargin, yImageMargin;

    public CommodityPanel(Component parent, String topString, String bottomString, Image image){ // TODO click actions
        super(parent, Colours.GUI_BACKGROUND, Colours.GUI_FOREGROUND, Colours.GUI_BORDER, Colours.GUI_TEXT,
                parent.getX(), -1, parent.getWidth(), HEIGHT, 1);
        this.topString = topString;
        this.bottomString = bottomString;
        this.image = image;
        xImageMargin = (int) Math.ceil((HEIGHT - image.getWidth()) / 2);
        yImageMargin = (int) Math.ceil((HEIGHT - image.getHeight()) / 2);

    }

    @Override
    public void init() {

        // buttons margin
        int yMargins = (height - Button.MAX_HEIGHT) / 2;

        // build the button row TODO do it better
        menu = new ButtonRow(this, x + (getWidth() / 2) + yMargins, y + yMargins, (width / 2) - (2 * yMargins), height - (2 * yMargins) ); // TODO CONSIDER using a different margin
        menu.addButton(new Button(menu, "Buy", null)); // TODO change buttons for different kinds of panels
        menu.addButton(new Button(menu, "Info", null));

        addChild(menu);
    }

    @Override
    public void draw(Graphics screen) {
        screen.setColor(Colours.GUI_TEXT);
        screen.drawString(topString, getX() + TEXT_X, rect.getCenterY() - (screen.getFont().getHeight("sdsd") / 2)); // TODO CONSIDER caching these values?
        screen.drawImage(image, getX() + xImageMargin, getY() + yImageMargin);
    }

    @Override
    public void update() {

    }

    @Override
    public void clickAction() {

    }

    @Override
    public void hoverAction() {

    }

}
