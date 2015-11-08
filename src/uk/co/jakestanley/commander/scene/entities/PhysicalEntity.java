package uk.co.jakestanley.commander.scene.entities;

import lombok.ToString;
import uk.co.jakestanley.commander.scene.entities.shapes.Box;
import uk.co.jakestanley.commander.scene.entities.shapes.Shape;

/**
 * Created by jp-st on 08/11/2015.
 */
@ToString(callSuper = true)
public class PhysicalEntity extends Entity {

    private Shape shape;

    public PhysicalEntity(String id, Shape shape){
        super(id);
        this.shape = shape;
    }

    /**
     * Returns true if entire entity is contained within this entity (edge inclusive) // TODO consider changing edge behaviour
     * @return
     */
    public boolean containsEntity(PhysicalEntity target){
        return true;
    }

    /**
     * Returns true if any part of target entity is within this entity (edge inclusive) // TODO consider changing edge behaviour
     * @return
     */
    public boolean insersectsEntity(PhysicalEntity target){
        return true;
    }

}