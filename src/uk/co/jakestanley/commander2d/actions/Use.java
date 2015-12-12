package uk.co.jakestanley.commander2d.actions;

import uk.co.jakestanley.commander2d.exceptions.IllegalAction;
import uk.co.jakestanley.commander2d.exceptions.UnnecessaryAction;
import uk.co.jakestanley.commander2d.map.functionals.Functional;
import uk.co.jakestanley.commander2d.mobs.Mob;

/**
 * Created by stanners on 03/08/2015.
 */
public class Use extends Action {

    private Functional functional;

    public Use(Mob mob, Functional functional){
        super(mob, USE);
    }

    @Override
    public void execute() throws IllegalAction, UnnecessaryAction {
        // TODO
    }
}