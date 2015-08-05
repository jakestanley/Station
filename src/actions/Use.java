package actions;

import exceptions.IllegalAction;
import exceptions.UnnecessaryAction;
import map.functionals.Functional;
import mobs.Mob;

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