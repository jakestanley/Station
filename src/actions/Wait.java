package actions;

import exceptions.IllegalAction;
import exceptions.UnnecessaryAction;
import mobs.Mob;

/**
 * Created by stanners on 24/05/2015.
 */
public class Wait extends Action {

    public Wait(Mob mob){
        super(mob, WAIT);
    }

    @Override
    public void executeAction() throws IllegalAction, UnnecessaryAction {
        // TODO do nothing
    }
}
