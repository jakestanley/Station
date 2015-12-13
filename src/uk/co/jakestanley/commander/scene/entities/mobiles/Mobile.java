package uk.co.jakestanley.commander.scene.entities.mobiles;

import lombok.Getter;
import lombok.Setter;
import uk.co.jakestanley.commander.scene.entities.PhysicalEntity;
import uk.co.jakestanley.commander.scene.entities.shapes.Shape;

/**
 * Created by jp-st on 08/11/2015.
 */
@Getter @Setter
public abstract class Mobile extends PhysicalEntity {

    protected static final int DEFAULT_ORIENTATION = 0;

    protected float hSpeed; // TODO create shape templates // TODO speed hampering by conditions?
    protected float vSpeed;

    public Mobile(String id, float x, float y, float z, float width, float height, float depth, float hSpeed, float vSpeed) {
        super(id, x, y, z, width, height, depth);
        this.hSpeed = hSpeed;
        this.vSpeed = vSpeed;
    }

    public void planTo(float x, float y, float z){ // TODO make better than basic and take obstacles into account. can travel to point
        // TODO plan

    }

    public void moveTo(float x, float y, float z) throws IllegalMoveException { // todo smooth out the movements

        // TODO check you can move to that point first

        // TODO calculate current orientation
        setPreviousX(getCurrentX());
        setPreviousY(getCurrentY());
        setPreviousZ(getCurrentZ());
        setCurrentX(x);
        setCurrentY(y);
        setCurrentZ(z);

        // TODO only look at if it's a valid move
        lookAt(x, y, z); // TODO calculate the orientation?
//        if(distance > hSpeed){
//            distance = hSpeed;
//        }
    }

    public void lookAt(float x, float y, float z){ // z might be overkill
        // TODO maybe move this into 3d entities or something? i need to work out which direction the mobile is facing using previous coordinates, etc
    }

    public boolean canMoveToHorizontal(float x, float y, float z){ // can only move flat on horizontal
        return true; // TODO functionality. make sure there is no collision between here and the target globalPosition
    }

    public boolean canMoveToVertical(float x, float y, float z){ // can only move directly up
        return true;
    }

}