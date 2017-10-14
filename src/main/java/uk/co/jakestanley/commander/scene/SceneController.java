package uk.co.jakestanley.commander.scene;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.geom.Rectangle;
import uk.co.jakestanley.commander.interfaces.Loopable;
import uk.co.jakestanley.commander.scene.entities.Entity;
import uk.co.jakestanley.commander.scene.entities.PhysicalEntity;
import uk.co.jakestanley.commander.scene.entities.mobiles.Crewman;
import uk.co.jakestanley.commander.scene.entities.mobiles.Mobile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jp-st on 08/11/2015.
 */
@Getter @Setter
public class SceneController implements Loopable { // TODO consider moving ship/world stuff to a separate area

    @NonNull public List<Entity> allEntities;
    @NonNull public List<Rectangle> walkableAreas; // TODO CONSIDER am i happy with a 2D walkable area?
    @NonNull public List<PhysicalEntity> physicalEntities;
    @NonNull public List<Mobile> mobiles;

    public SceneController(){
        // TODO initialise area
        allEntities = new ArrayList<Entity>();
        physicalEntities = new ArrayList<PhysicalEntity>();
        mobiles = new ArrayList<Mobile>(); // TODO consider naming this class MobileEntity
    }

    public void init() {
        addMobileEntity(new Crewman("Jeff", 0, 0, 0));
    }

    public void update() {
        for (Iterator<Entity> iterator = allEntities.iterator(); iterator.hasNext(); ) {
            Entity next = iterator.next();
            next.update();
        }
    }

    public void addEntity(Entity entity){
        allEntities.add(entity);
    }

    public void addWalkableArea(Rectangle area){
        walkableAreas.add(area);
    }

    public void addPhysicalEntity(PhysicalEntity entity){
        allEntities.add(entity);
        physicalEntities.add(entity);
    }

    public void addMobileEntity(Mobile mobile){
        allEntities.add(mobile);
        physicalEntities.add(mobile);
        mobiles.add(mobile);
    }

    public List<Vector3f> getNodes(){ // TODO build node graph? // TODO weighting
        return null;
    }

    public void generateNodes(){ // TODO CONSIDER putting this shit in another class?

    }

    public boolean isWalkableArea(float x, float y){ // TODO needs to be walkable and not obstructed. make 3D? hmmm
        for (Iterator<Rectangle> areas = walkableAreas.iterator(); areas.hasNext(); ) {
            Rectangle area =  areas.next();
            if(area.contains(x, y)){ // TODO make more advanced to take widths into account. area.contains() also takes width and height as arguments
                return true;
            }
        }
        return false;
    }

}
