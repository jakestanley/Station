package actions;

import exceptions.IllegalAction;
import exceptions.UnnecessaryAction;
import main.Door;
import mobs.Mob;

/**
 * Created by stanners on 30/05/2015.
 */
public class AttackDoor extends Action {

    private Door door;

    public AttackDoor(Mob mob, Door door){ // TODO change to Component, and make doors and components related
        super(mob, ATTACK);
        this.door = door;
    }

    @Override
    public void execute() throws IllegalAction, UnnecessaryAction {
        this.door.damage();
    }
}
