package uk.co.jakestanley.commander.rendering.entities;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.TexturedModel;

/**
 * Created by jp-st on 13/11/2015.
 */
@Getter @Setter
public class RenderEntity { // TODO take a world object as its variable?

    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;

    public RenderEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void increaseRotation(float rx, float ry, float rz){
        rotX = rotX + rx;
        rotY = rotY + ry;
        rotZ = rotZ + rz;
    }

    public void increasePosition(float dx, float dy, float dz){ // TODO consider move(args) ?
        position.setX(position.getX() + dx);
        position.setY(position.getY() + dy);
        position.setZ(position.getZ() + dz);

    }
}
