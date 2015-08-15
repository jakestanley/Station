package main;

import actions.Action;

import java.util.ArrayList;

/**
 * Created by stanners on 05/08/2015.
 */
public class ActionQueue {

    private ArrayList<Action> queue;

    public ActionQueue(){ // TODO need a release actions loop, then a get actions loop
        this.queue = new ArrayList<Action>();
    }

    public void addAction(Action action){
        queue.add(action);
    }

    public ArrayList<Action> getActions(){ // TODO when selecting an action, always pick an appropriate one with the lowest index so tasks don't end up never getting done
        return queue;
    }

    public void cleanActions(){ // TODO add a clean action method to actions, which checks if an action is still valid
        // TODO method for removing expired or invalid actions. should be called after actions are released, but before any new ones are assigned.
    }



}
