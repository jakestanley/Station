package uk.co.jakestanley.commander2d.actions;

import uk.co.jakestanley.commander2d.exceptions.IllegalAction;
import uk.co.jakestanley.commander2d.exceptions.UnnecessaryAction;
import uk.co.jakestanley.commander2d.mobs.Mob;

/**
 * Created by stanners on 24/05/2015.
 */
public class Wait extends Action {

    public Wait(Mob mob){
        super(mob, WAIT);
    }

    @Override
    public void execute() throws IllegalAction, UnnecessaryAction {
        // TODO do nothing
    }
}
