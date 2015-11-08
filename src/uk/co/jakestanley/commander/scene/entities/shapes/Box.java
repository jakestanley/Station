package uk.co.jakestanley.commander.scene.entities.shapes;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jp-st on 08/11/2015.
 */
public class Box extends Shape {

    @Getter @Setter private float width; // x axis
    @Getter @Setter private float depth; // z axis
    @Getter @Setter private float height; // y axis

    public Box(float xLocal, float zLocal, float yLocal, float width, float depth, float height){
        super(xLocal, zLocal, yLocal);
        this.isBox = true;
        this.width = width;
        this.depth = depth;
        this.height = height;
    }

    @Override
    public boolean containsShape(Shape shape) {
        try{
            if(shape.isBox()){
                Box box = (Box) shape; // TODO



            } else {
                Polygon polygon = (Polygon) shape; // TODO



            }
        } catch(ClassCastException e){
            // this shouldn't happen
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean intersectsShape(Shape shape) {
        return false;
    }

}
