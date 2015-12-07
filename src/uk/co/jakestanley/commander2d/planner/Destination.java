package uk.co.jakestanley.commander2d.planner;

import uk.co.jakestanley.commander2d.exceptions.ImpossibleGoal;
import uk.co.jakestanley.commander2d.mobs.Mob;

/**
 * Created by stanners on 24/05/2015.
 */
public class Destination extends Planner {

    public Destination(Mob mob, int tx, int ty){ // current location, target location
        super(mob, GOAL_DESTINATION);
    }

    @Override
    public void calculate() throws ImpossibleGoal {
        // TODO breadth first traversal to get path

    }

    @Override
    public boolean achieved() {
        return false;
    }

}
