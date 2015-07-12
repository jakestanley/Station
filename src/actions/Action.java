package actions;

import exceptions.IllegalAction;
import exceptions.UnnecessaryAction;
import mobs.Mob;

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
//    public static final int ESCAPE = 5;
//    public static final int RANDOM = 6;

    public Mob mob;
    public int type;

    public Action(Mob mob, int type){
        this.mob = mob;
        this.type = type;
    }

    public abstract void executeAction() throws IllegalAction, UnnecessaryAction;

    public class Strings {
        public static final String MOVE = "moving";
        public static final String OPEN_DOOR = "opening door";
        public static final String CLOSE_DOOR = "closing door";
        public static final String WAIT = "waiting";
        public static final String ATTACK = "attacking"; // TODO for hostile mobs, repeat until component is destroyed
        public static final String REPAIR = "repairing";
//        public static final String ESCAPE = "escaping";
//        public static final String RANDOM = "random";
    }

}
