package uk.co.jakestanley.commander2d.actions;

import uk.co.jakestanley.commander2d.exceptions.ActionAssignedException;
import uk.co.jakestanley.commander2d.exceptions.IllegalAction;
import uk.co.jakestanley.commander2d.exceptions.UnnecessaryAction;
import uk.co.jakestanley.commander2d.mobs.Mob;

/**
 * Created by stanners on 24/05/2015.
 */
public abstract class Action {

    public static final int MOVE = 0;
    public static final int OPEN_DOOR = 1;
    public static final int CLOSE_DOOR = 2;
    public static final int WAIT = 3;
    public static final int ATTACK = 4;
    public static final int REPAIR = 5;
//    public static final int ESCAPE = 6;
//    public static final int RANDOM = 7;
    public static final int USE = 8;

    protected boolean valid, complete;
    protected Mob mob; // who is doing the action?
    protected int type;
    protected int priority;

    public Action(int type){
        this.type = type;
        this.valid = true;
        this.complete = false;
    }

    public Action(Mob mob, int type){
        this.mob = mob;
        this.type = type;
        this.valid = true;
        this.complete = false;
    }

    public int getPriority(){
        return priority;
    }

    public void own(Mob mob) throws ActionAssignedException {
        if(this.mob != null){
            this.mob = mob;
        }
    }

    public boolean disown(){
        this.mob.dropAction();
        this.mob = null;
        return true;
    }

    public abstract void execute() throws IllegalAction, UnnecessaryAction;

    public class Strings {
        public static final String MOVE = "moving";
        public static final String OPEN_DOOR = "opening door";
        public static final String CLOSE_DOOR = "closing door";
        public static final String WAIT = "waiting";
        public static final String ATTACK = "attacking"; // TODO for hostile uk.co.jakestanley.commander2d.mobs, repeat until component is destroyed
        public static final String REPAIR = "repairing";
//        public static final String ESCAPE = "escaping";
//        public static final String RANDOM = "random";
        public static final String USE = "using";
    }

}
