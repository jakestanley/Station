package gui.actions.place;

import gui.actions.Action;
import main.GameController;

/**
 * Created by stanners on 20/09/2015.
 */
public class PlaceLargeReactor extends Action {

    @Override
    public void execute() {
        GameController.mapController.setPlaceable();
    }

}
