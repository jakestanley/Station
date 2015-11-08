package uk.co.jakestanley.commander.scene.entities.mobiles;

/**
 * Created by jp-st on 08/11/2015.
 */
public class Crewman extends Mobile {

    public static final float HORIZONTAL_SPEED = 1.0f;
    public static final float VERTICAL_SPEED = 0.5f;

    public Crewman(String id, float xLocal, float yLocal, float zLocal) {
        super(id, xLocal, yLocal, zLocal, 0.5f, 0.5f, 1.0f, HORIZONTAL_SPEED, VERTICAL_SPEED); // TODO referenced values
    }

}
