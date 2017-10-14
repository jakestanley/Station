package uk.co.jakestanley.commander.scene.entities.mobiles;

import uk.co.jakestanley.commander.scene.entities.PhysicalEntity;

/**
 * Created by jake on 14/11/2015.
 */
public class IllegalMoveException extends Exception {

    public IllegalMoveException(PhysicalEntity entity, float x, float y, float z){ // TODO reason
        super("Illegal move: " + entity.getId() + " tried to move to [" + x + ", " + y + ", " + z + "]");
    }
}
