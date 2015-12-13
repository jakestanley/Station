package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.rendering.exceptions.ChildIsSelfException;
import uk.co.jakestanley.commander.rendering.exceptions.ParentIsSelfException;
import uk.co.jakestanley.commander.rendering.world.tools.Maths;
import uk.co.jakestanley.commander.rendering.world.tools.Out;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jake on 07/12/2015.
 */
@Getter
public abstract class Renderable {

    protected static final int DEFAULT_SCALE = 1;

    protected Renderable parent;
    @Setter protected float rotX, rotY, rotZ;
    @Setter protected Vector3f globalPosition;
    @Setter protected Vector3f localPosition;
    @Setter protected Vector3f localOffset;
    protected boolean hidden;
    protected String identifier;
    protected List<RenderEntity> allRenderEntities;
    protected List<RenderEntity> visibleRenderEntities;
    protected List<Renderable> children; // under the influence of this object's globalPosition

    public Renderable(String identifier, Vector3f position, float rotX, float rotY, float rotZ, boolean hidden){
        this.hidden = hidden;
        this.identifier = identifier;
        this.globalPosition = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.allRenderEntities = new ArrayList<RenderEntity>();
        this.visibleRenderEntities = new ArrayList<RenderEntity>();
        this.children = new ArrayList<Renderable>();
    }

    public boolean hasParent(){ // !isOrphaned
        return parent != null;
    }

    public boolean isOrphaned(){
        return parent == null;
    }

    public void setParent(Renderable parent) throws ParentIsSelfException {
        if(parent == this){
            throw new ParentIsSelfException();
        }
        try {
            this.parent = parent;
            parent.setChild(this);
            setLocalOffset();
        } catch (ChildIsSelfException c){
            c.printStackTrace();
            unsetParent();
        }
    }

    public void unsetParent(){
        parent.unsetChild(this);
        parent = null;
    }

    public void setChild(Renderable child) throws ChildIsSelfException {
        if(child == this){
            throw new ChildIsSelfException();
        }
        children.add(child);
    }

    public void unsetChild(Renderable child){
        for (Iterator<Renderable> iterator = children.iterator(); iterator.hasNext(); ) {
            Renderable next = iterator.next();
            if(next == child){
                iterator.remove();
            }
        }
    }

    public void show(){
        hidden = false;
    }

    public void hide(){
        hidden = true;
    }

    public boolean hasVisibleRenderEntities(){
        return (visibleRenderEntities.size() > 0);
    }

    public void setPosition(Vector3f position){ // TODO update local coordinates - if has parent, maybe update relatively, or update as child, when parent is updated
        for (RenderEntity next : allRenderEntities) {
            next.setPosition(position);
        }
        globalPosition = position;
        for (Renderable child : children) {
            child.updateGlobalPosition();
        }
    }

    public void increasePosition(Vector3f position){
        for (RenderEntity next : allRenderEntities) {
            Vector3f newPosition = Maths.addVectors(next.getPosition(), position);
            next.setPosition(newPosition);
        }
        globalPosition = Maths.addVectors(globalPosition, position);
        for (Renderable child : children){
            child.updateGlobalPosition(); // TODO OPTIMISATION consider
        }
    }

    public void setRotation(Vector3f rotation){
        for (RenderEntity next : allRenderEntities) {
            next.setRotX(rotation.getX());
            next.setRotY(rotation.getY());
            next.setRotZ(rotation.getZ());
        }
    }

    public void increaseRotation(Vector3f rotation){
        for (RenderEntity next : allRenderEntities) {
            next.setRotX(next.getRotX() + rotation.getX()); // TODO change this so it uses vectors
            next.setRotY(next.getRotY() + rotation.getY());
            next.setRotZ(next.getRotZ() + rotation.getZ());
        }
        // TODO update child positions
    }

    public void increaseLocalPosition(Vector3f inc){
        localPosition = Maths.addVectors(localPosition, inc);
    }

    public void setLocalRotation(){
        // TODO
    }

    public void increaseLocalRotation(){
        // TODO
    }

    protected abstract void loadRenderEntities();

    private void setLocalOffset(){
        localPosition = new Vector3f(0,0,0);
        if(parent != null){
            Vector3f localOffset = new Vector3f();
            Vector3f parentPosition = parent.getGlobalPosition();
            localOffset.x = globalPosition.getX() - parentPosition.getX();
            localOffset.y = globalPosition.getY() - parentPosition.getY();
            localOffset.z = globalPosition.getZ() - parentPosition.getZ();
            this.localOffset= localOffset;
        }
        updateGlobalPosition();
    }

    private void updateGlobalPosition(){
        if(parent != null){
            globalPosition = Maths.addVectors(parent.getGlobalPosition(), localOffset, localPosition);
            for (RenderEntity next : allRenderEntities) {
                next.setPosition(globalPosition);
            }
        }
    }

    private void updateLocalRotation(){
        // TODO set local rotation
    }

}
