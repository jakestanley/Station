package uk.co.jakestanley.commander.scene.entities.mobiles;

import uk.co.jakestanley.commander.scene.entities.shapes.Box;

/**
 * Created by jp-st on 08/11/2015.
 */
public class Crewman extends Mobile {

    public static final float WIDTH = 0.5f;
    public static final float DEPTH = 0.5f;
    public static final float HEIGHT = 1.0f;


    public static final float HORIZONTAL_SPEED = 1.0f;
    public static final float VERTICAL_SPEED = 0.5f;

    public Crewman(String id, float xLocal, float zLocal, float yLocal) {
        super(id, new Box(xLocal, zLocal, yLocal, WIDTH, DEPTH, HEIGHT), HORIZONTAL_SPEED, VERTICAL_SPEED); // TODO referenced values
    }

}
