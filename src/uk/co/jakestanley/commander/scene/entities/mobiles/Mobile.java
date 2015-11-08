package uk.co.jakestanley.commander.scene.entities.mobiles;

import lombok.Getter;
import lombok.Setter;
import uk.co.jakestanley.commander.scene.entities.PhysicalEntity;

/**
 * Created by jp-st on 08/11/2015.
 */
public class Mobile extends PhysicalEntity {

    @Getter @Setter private float hSpeed;
    @Getter @Setter private float vSpeed;

    public Mobile(String id, float xLocal, float zLocal, float yLocal, float width, float depth, float height, float hSpeed, float vSpeed) {
        super(id, xLocal, yLocal, zLocal, width, depth, height);
        this.hSpeed = hSpeed;
        this.vSpeed = vSpeed;
    }

    public boolean canMovePositiveX(float distance){
        return false; // TODO functionality. make sure there is no collision between here and the target position
    }

    public boolean canMoveNegativeX(float distance){
//        CommanderGame.sceneController.();
        return false; // TODO functionality
    }

    public boolean canMovePositiveZ(float distance){
        return false; // TODO functionality
    }

    public boolean canMoveNegativeZ(float distance){
        return false; // TODO functionality
    }

    public boolean canMovePositiveY(float distance){
        return false; // TODO functionality
    }

    public boolean canMoveNegativeY(float distance){
        return false; // TODO functionality
    }

}
