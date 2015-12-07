package uk.co.jakestanley.commander2d.planner;

import uk.co.jakestanley.commander2d.actions.Action;
import uk.co.jakestanley.commander2d.exceptions.ImpossibleGoal;
import uk.co.jakestanley.commander2d.mobs.Mob;

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
        actions.add(new uk.co.jakestanley.commander2d.actions.Wait(mob));
    }

    @Override
    public boolean achieved() {
        return false;
    }
}
