package uk.co.jakestanley.commander2d.gui.floating;

import uk.co.jakestanley.commander2d.gui.Component;
import uk.co.jakestanley.commander2d.main.Colours;

/**
 * Created by jake on 16/08/15.
 */
public abstract class Floating extends Component {

    private boolean valid;

    public Floating(int x, int y, int width, int height){
        super(null, Colours.GUI_BACKGROUND, Colours.GUI_FOREGROUND, Colours.GUI_BORDER, Colours.GUI_TEXT, x, y, width, height, 1);
        valid = true;
//        super(x, y, width, height); // TODO
    }

    /**
     * Call child onClose method and set to invalid so this will be destroyed
     */
    public void destroy(){
        onClose();
        valid = false;
    }

    protected abstract void onClose();

}
