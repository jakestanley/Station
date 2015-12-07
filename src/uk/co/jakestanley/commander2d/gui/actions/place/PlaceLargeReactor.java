package uk.co.jakestanley.commander2d.gui.actions.place;

import uk.co.jakestanley.commander2d.gui.actions.Action;
import uk.co.jakestanley.commander2d.main.GameController;

/**
 * Created by stanners on 20/09/2015.
 */
public class PlaceLargeReactor extends Action {

    @Override
    public void execute() {
        GameController.mapController.setPlaceable();
    }

}
