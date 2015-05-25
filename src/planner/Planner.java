package planner;

import actions.Action;
import exceptions.ImpossibleGoal;
import mobs.Mob;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by stanners on 24/05/2015.
 */
public abstract class Planner {

    protected static final int GOAL_DESTINATION = 0; // TODO CONSIDER naming renaming GOAL to TARGET?
    protected static final int GOAL_RANDOM = 1;
    protected static final int GOAL_WAIT = 2;

    public int type;
    protected Mob mob;
    protected ArrayList<Action> actions;

    public Planner(Mob mob, int type){
        actions = new ArrayList<Action>();
        this.mob = mob;
        this.type = type;
    }

    public static ArrayList<Point> getPossibleMoves(int cx, int cy) {
        ArrayList<Point> moves = new ArrayList<Point>(5);
        moves.add(new Point(cx - 1, cy));
        moves.add(new Point(cx, cy - 1));
        moves.add(new Point(cx, cy)); // don't go anywhere. this shouldn't be relevant unless random or waiting
        moves.add(new Point(cx, cy + 1));
        moves.add(new Point(cx + 1, cy));
        return moves;
    }

    // TODO make sure the mob can't do an illegal action
    // TODO make sure that possibilities are always possible so the mob doesn't get stuck in an impossible loop

    public Action getNextAction(){ // get the next action and remove it from the list // TODO handle null action

        if(actions.isEmpty()) {
            return null;
        }

        Action action = actions.get(0);
        actions.remove(0); // TODO check that this pushes down the stack

//        String actionString = mob.getName() + " picked next action: "; // TODO something with this
//
//        if(action.type == Action.ACTION_OPEN_DOOR){
//            actionString = actionString + "open door";
//        } else if(action.type == Action.ACTION_MOVE){
//            actionString = actionString + "move";
//        } else if(action.type == Action.ACTION_WAIT){
//            actionString = actionString + "wait";
//        }

        return action;

    }

    public abstract void calculate() throws ImpossibleGoal;
    public abstract boolean achieved(); // TODO consider is this stupid?

}
