package actions;

import exceptions.IllegalAction;
import exceptions.UnnecessaryAction;
import mobs.Mob;

/**
 * Created by stanners on 24/05/2015.
 */
public abstract class Action {

    public static final int ACTION_MOVE = 0;
    public static final int ACTION_OPEN_DOOR = 1;
    public static final int ACTION_CLOSE_DOOR = 2;
    public static final int ACTION_WAIT = 3;

    public Mob mob;
    public int type;

    public Action(Mob mob, int type){
        this.mob = mob;
        this.type = type;
    }

    public abstract void executeAction() throws IllegalAction, UnnecessaryAction;

}
