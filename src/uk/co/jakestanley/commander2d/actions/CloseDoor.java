package uk.co.jakestanley.commander2d.actions;

import uk.co.jakestanley.commander2d.exceptions.IllegalAction;
import uk.co.jakestanley.commander2d.exceptions.UnnecessaryAction;
import uk.co.jakestanley.commander2d.main.Door;
import uk.co.jakestanley.commander2d.mobs.Mob;

/**
 * Created by stanners on 24/05/2015.
 */
public class CloseDoor extends Action {

    private Door door;

    public CloseDoor(Mob mob, Door door){
        super(mob, CLOSE_DOOR);
        this.door = door;
    }

    @Override
    public void execute() throws IllegalAction, UnnecessaryAction {
        // TODO throw exception if mob isn't next to the door
        if(door.isOpen()){
            door.toggle();
        } else {
            throw new UnnecessaryAction("Door is already closed. This action does not count.");
        }
    }
}
