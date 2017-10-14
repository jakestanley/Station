package uk.co.jakestanley.commander.scene.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.newdawn.slick.geom.Rectangle;
import uk.co.jakestanley.commander.scene.entities.shapes.Box;

/**
 * Created by jp-st on 08/11/2015.
 */
@Getter @Setter @ToString(callSuper = true)
public abstract class PhysicalEntity extends Entity {

    //    protected float orientation;
    protected Rectangle box; // TODO cube
    protected float width, height, depth;
    protected float previousX, previousY, previousZ;
    protected float currentX, currentY, currentZ; // should be the center

    public PhysicalEntity(String id, float x, float y, float z, float width, float height, float depth){ // represents collision box
        super(id);
//        this.orientation = orientation; // TODO set another way? need a rotation matrix for the model. assume all models face north?
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.currentX = x;
        this.currentY = y;
        this.currentZ = z;
        this.previousX = x;
        this.previousY = y;
        this.previousZ = z;
    }

    public abstract void update();

    /**
     * Returns true if entire entity is contained within this entity (edge inclusive) // TODO consider changing edge behaviour
     * @return
     */
    public boolean containsEntity(PhysicalEntity target){
        // TODO functionality
        return true;
    }

    /**
     * Returns true if any part of target entity is within this entity (edge inclusive) // TODO consider changing edge behaviour
     * @return
     */
    public boolean insersectsEntity(PhysicalEntity target){
        // TODO functionality
        return true;
    }

    public Rectangle getBox(){
        if(box == null){
            box = new Rectangle(currentX, currentZ, width, depth); // TODO decide whether i'm calculating based on centre or corner
        }
        return box;
    }

    /**
     * Rotates the collision box 90 degrees
     */
    protected void rotateHorizontally(){
        float newWidth = depth;
        float newDepth = width;
        width = newWidth;
        depth = newDepth;
    }

}