package planner;

import actions.Action;
import exceptions.ImpossibleGoal;
import mobs.Mob;

import java.util.ArrayList;

/**
 * Created by stanners on 24/05/2015.
 */
public class Wait extends Planner {

    public Wait(Mob mob){
        super(mob, GOAL_WAIT);
    }

    @Override
    public void calculate() throws ImpossibleGoal {
        actions = new ArrayList<Action>();
        actions.add(new actions.Wait(mob));
    }

    @Override
    public boolean achieved() {
        return false;
    }
}
