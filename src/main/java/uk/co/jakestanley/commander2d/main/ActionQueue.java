package uk.co.jakestanley.commander2d.main;

import uk.co.jakestanley.commander2d.actions.Action;

import java.util.ArrayList;

/**
 * Created by stanners on 05/08/2015.
 */
public class ActionQueue {

    private ArrayList<Action> queue;

    public ActionQueue(){ // TODO need a release uk.co.jakestanley.commander2d.actions loop, then a get uk.co.jakestanley.commander2d.actions loop
        this.queue = new ArrayList<Action>();
    }

    public void addAction(Action action){
        queue.add(action);
    }

    public ArrayList<Action> getActions(){ // TODO when selecting an action, always pick an appropriate one with the lowest index so tasks don't end up never getting done
        return queue;
    }

    public void cleanActions(){ // TODO add a clean action method to uk.co.jakestanley.commander2d.actions, which checks if an action is still valid
        // TODO method for removing expired or invalid uk.co.jakestanley.commander2d.actions. should be called after uk.co.jakestanley.commander2d.actions are released, but before any new ones are assigned.
    }



}
