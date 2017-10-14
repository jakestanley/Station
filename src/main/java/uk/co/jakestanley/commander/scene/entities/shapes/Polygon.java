package uk.co.jakestanley.commander.scene.entities.shapes;

import lombok.Getter;

import java.util.List;

/**
 * Created by jp-st on 08/11/2015.
 */
public class Polygon extends Shape {

    @Getter private List<?> lines;

    public Polygon(float xLocal, float zLocal, float yLocal, List<?> lines){
        super(xLocal, zLocal, yLocal);
        this.isBox = false;
        this.lines = lines;
        // TODO change lines to an appropriate variable for a complex shape
        // TODO make a shape which is basically a shitload of lines
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
