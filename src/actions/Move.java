package actions;

import exceptions.IllegalAction;
import main.Door;
import main.GameController;
import mobs.Mob;

import java.awt.*;

/**
 * Created by stanners on 24/05/2015.
 */
public class Move extends Action { // TODO validate

    private int tx, ty;

    public Move(Mob mob, int tx, int ty){ // where to?
        super(mob, MOVE);
        this.tx = tx;
        this.ty = ty;
    }

    @Override
    public void execute() throws IllegalAction {

        // TODO check for void tiles here?

        Point point1, point2;
        point1 = mob.getPoint();
        point2 = new Point(tx,ty);

        if(GameController.mapController.getTile(point2).isVoid()){
            throw new IllegalAction("Tried to move to a void tile");
        }

//        System.out.println("Moving from [" + tile1.getX() + ", " + tile1.getY() + "] to [" + tile2.getX() + ", " + tile2.getY() + "]");

        Door door = GameController.mapController.getDoor(point1, point2);
        if(door != null){
            if(!door.isOpen()){
                throw new IllegalAction("Tried to move to tile but there was a closed door in the way.");
            }
        }

        mob.moveTo(point2);

    }
}
