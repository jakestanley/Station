package uk.co.jakestanley.commander.rendering.entities;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.rendering.world.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.models.TexturedModel;

import java.util.List;

/**
 * Created by jp-st on 13/11/2015.
 */
@Getter @Setter
public class RenderEntity { // TODO take a world object as its variable?

    protected RawModel rawModel;
    protected List<RawModel> rawModels; // TODO CONSIDER always using a list, or is that too much of an overhead?
    protected TexturedModel texturedModel;
    protected List<TexturedModel> texturedModels;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;
    private int textured;
    private int multiple;

    public RenderEntity(RawModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale){ // TODO make sure this works. i've tried adapting this to use an untextured model
        this.rawModel = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.textured = UNTEXTURED_MODEL;
        this.multiple = SINGLE_MODEL;
    }

    public RenderEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.texturedModel = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.textured = TEXTURED_MODEL;
        this.multiple = SINGLE_MODEL;
    }

    /**
     * Constructor for render entities that provide their own models
     * @param position
     * @param rotX
     * @param rotY
     * @param rotZ
     * @param scale
     */
    protected RenderEntity(Vector3f position, float rotX, float rotY, float rotZ, float scale, int textured, int multiple){ // TODO make sure this works. i've tried adapting this to use an untextured model
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.textured = textured;
        this.multiple = multiple; // TODO rotating a multi-model entity will require maths. you'll have to calculate a center point to rotate around and rotation/transformation matrices for each model i think
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

    public boolean isTextured(){
        return (textured == TEXTURED_MODEL);
    }

    public boolean isSingleModel(){
        return (multiple == SINGLE_MODEL);
    }

    protected static final int UNTEXTURED_MODEL = 901;
    protected static final int TEXTURED_MODEL = 902;
    protected static final int SINGLE_MODEL = 1001;
    protected static final int MULTIPLE_MODEL = 1002;
}
