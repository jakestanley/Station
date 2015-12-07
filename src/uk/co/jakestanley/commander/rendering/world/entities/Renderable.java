package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.Getter;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jake on 07/12/2015.
 */
@Getter
public abstract class Renderable {

    protected static final int DEFAULT_SCALE = 1;

    protected Vector3f position;
    protected float rotX, rotY, rotZ;
    protected String identifier;
    protected List<RenderEntity> allRenderEntities;
    protected List<RenderEntity> visibleRenderEntities;

    public Renderable(String identifier, Vector3f position, float rotX, float rotY, float rotZ){
        this.identifier = identifier;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.allRenderEntities = new ArrayList<RenderEntity>();
        this.visibleRenderEntities = new ArrayList<RenderEntity>();
    }

    public boolean hasVisibleRenderEntities(){
        return (visibleRenderEntities.size() > 0);
    }

    protected abstract void loadRenderEntities();

}
