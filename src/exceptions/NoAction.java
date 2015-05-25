package exceptions;

import mobs.Mob;

/**
 * Created by stanners on 24/05/2015.
 */
public class NoAction extends Exception {

    public NoAction(Mob mob){
        super(mob.getName() + " can't figure out where to go");
    }

}
