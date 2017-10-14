package uk.co.jakestanley.commander2d.gui.inspectors.boxes;

import uk.co.jakestanley.commander2d.gui.Component;
import uk.co.jakestanley.commander2d.main.Colours;
import uk.co.jakestanley.commander2d.main.Display;
import org.newdawn.slick.Graphics;

import java.util.Iterator;

/**
 * Created by stanners on 20/09/2015.
 */
public class CommodityList extends Component {

    public CommodityList(Component parent, int x, int y) {
        super(parent,
                Colours.GUI_BACKGROUND, Colours.GUI_FOREGROUND, Colours.GUI_BORDER, Colours.GUI_TEXT,
                x, y, parent.getWidth(), Display.MOBS_BOX_HEIGHT, 1);
    }

    @Override
    public void addChild(Component c) {// throws ComponentChildSizeException {
        int y = getY() + (children.size() * CommodityPanel.HEIGHT);
        c.setY(y);
        children.add(c);
    }

    @Override
    public void init() {
        for (Iterator<Component> iterator = children.iterator(); iterator.hasNext(); ) {
            Component next = iterator.next();
            next.init();
        }
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

    @Override
    public void draw(Graphics screen) {
        screen.drawString("testing commodity list", getX(), getY());
    }
}
