package uk.co.jakestanley.commander.scene.entities.statics;

import lombok.Getter;
import org.newdawn.slick.geom.Rectangle;
import uk.co.jakestanley.commander.scene.entities.PhysicalEntity;
import uk.co.jakestanley.commander.scene.entities.shapes.Box;

/**
 * Created by jp-st on 08/11/2015.
 */
public class Door extends PhysicalEntity {

    // TODO orientation
    private static final int CLOSED_FRAME = 0;
    private static final int OPEN_FRAME = 9;
    private static final float DOOR_HEIGHT = 1f;
    private static final float DOOR_WIDTH = 2f;
    private static final float DOOR_DEPTH = 0.25f;

    @Getter private boolean open;
    @Getter private boolean opening;
    @Getter private boolean closing;
    @Getter private boolean locked;
    private int frame;

    public Door(String id, boolean wide, int x, int y, int z){ // TODO rooms should snap to a certain size, but rendering and movement should be free // TODO 3D
        super(id, x, y, z, DOOR_WIDTH, DOOR_HEIGHT, DOOR_DEPTH); // TODO set these values before the door is instantiated.
        frame = CLOSED_FRAME;
        open = false;
        locked = false;
        if(!wide){
            rotateHorizontally();
        }
    }

    public void init() {

    }

    @Override
    public void update() {
        if(opening && frame < OPEN_FRAME){
            frame++;
        } else if(opening && frame == OPEN_FRAME){
            open = true;
            opening = false;
        } else if(closing && frame > CLOSED_FRAME){
            frame--;
        } else if(closing && frame == CLOSED_FRAME){
            open = false;
            closing = false;
        }
    }

    public void open(){ // TODO CONSIDER opening half way closing door?
        // TODO expand
        open = true;
    }

    public void close(){
        // TODO expand
        open = false;
    }

    public void lock(){
        locked = true;
    }

    public void unlock(){
        locked = false;
    }

}