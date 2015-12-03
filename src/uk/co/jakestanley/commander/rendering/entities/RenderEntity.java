package uk.co.jakestanley.commander.rendering.entities;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.TexturedModel;

/**
 * Created by jp-st on 13/11/2015.
 */
@Getter @Setter
public class RenderEntity { // TODO take a world object as its variable?

    protected RawModel rawModel;
    protected TexturedModel texturedModel;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;
    private boolean textured;

    public RenderEntity(RawModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale){ // TODO make sure this works. i've tried adapting this to use an untextured model
        this.rawModel = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.textured = false;
    }

    public RenderEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.texturedModel = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.textured = true;
    }

    /**
     * Constructor for render entities that provide their own models
     * @param position
     * @param rotX
     * @param rotY
     * @param rotZ
     * @param scale
     */
    protected RenderEntity(Vector3f position, float rotX, float rotY, float rotZ, float scale, boolean textured){ // TODO make sure this works. i've tried adapting this to use an untextured model
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.textured = textured;
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
