package planner;

import actions.Action;
import exceptions.ImpossibleGoal;
import main.Game;
import mobs.Mob;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 24/05/2015.
 */
public abstract class Planner {

    public static final int GOAL_DESTINATION = 0; // TODO CONSIDER naming renaming GOAL to TARGET?
    public static final int GOAL_RANDOM = 1;
    public static final int GOAL_WAIT = 2;
    public static final int GOAL_EVACUATE = 3;
    public static final int GOAL_SAFETY = 4;

    public static final String[] goals = {"destination", "random", "wait", "evacuate", "safety"}; // TODO other stuff this way rather than comparisons

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

        // remove invalid points
        for (Iterator<Point> iterator = moves.iterator(); iterator.hasNext(); ) {
            Point next = iterator.next();
            int nextX = (int) next.getX();
            int nextY = (int) next.getY();
            if(nextX < 0 || nextY < 0){ // prevents array out of bounds exception
                iterator.remove();
            } else if(!Game.map.tiles[nextX][nextY].isTraversable()){
                iterator.remove();
            }
        }

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
//        if(action.type == Action.OPEN_DOOR){
//            actionString = actionString + "open door";
//        } else if(action.type == Action.MOVE){
//            actionString = actionString + "move";
//        } else if(action.type == Action.WAIT){
//            actionString = actionString + "wait";
//        }

        return action;

    }

    public int getType(){
        return type;
    }

    public String getGoalString(){
        return goals[type];
    }

    public abstract void calculate() throws ImpossibleGoal;
    public abstract boolean achieved(); // TODO consider is this stupid?

}
