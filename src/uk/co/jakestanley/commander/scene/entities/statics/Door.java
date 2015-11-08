package uk.co.jakestanley.commander.scene.entities.statics;

import uk.co.jakestanley.commander.scene.entities.PhysicalEntity;

/**
 * Created by jp-st on 08/11/2015.
 */
public class Door extends PhysicalEntity {

    public Door(String id, int xLocal, int yLocal, int zLocal){ // TODO rooms should snap to a certain size, but rendering and movement should be free
        super(id, xLocal, yLocal, zLocal, 0.5f, 1, 2); // TODO set these values before the door is instantiated.
    }

}