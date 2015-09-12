package gui.widgets;

import gui.Component;
import org.newdawn.slick.Color;

/**
 * Created by jake on 16/08/15.
 */
public abstract class Widget extends Component {

    protected boolean set; // TODO CONSIDER not sure about this

    public Widget(Component parent,
                  Color bgCol, Color fgCol, Color bdCol, Color tCol,
                  int x, int y, int width, int height){

        super(parent, bgCol, fgCol, bdCol, tCol, x, y, width, height, 1); // TODO change 1 to appropriate border width
        this.set = false;
    }

}
