package uk.co.jakestanley.commander.scene.entities.shapes;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jp-st on 08/11/2015.
 */
public abstract class Shape { // TODO base the coordinates on the top-left-bottom-most

    protected boolean isBox;

    @Getter @Setter private float xLocal;
    @Getter @Setter private float zLocal; // TODO consider deleting
    @Getter @Setter private float yLocal;

    public Shape(float xLocal, float zLocal, float yLocal){
        this.xLocal = xLocal;
        this.zLocal = zLocal;
        this.yLocal = yLocal;
    }

    public boolean isBox(){
        return isBox;
    }

    /**
     * Returns true if entire shape is contained within this shape (edge inclusive) // TODO consider changing edge behaviour
     * @return
     */
    public abstract boolean containsShape(Shape shape);

    /**
     * Returns true if any part of target shape is within this shape (edge inclusive) // TODO consider changing edge behaviour
     * @return
     */
    public abstract boolean intersectsShape(Shape shape);

}
