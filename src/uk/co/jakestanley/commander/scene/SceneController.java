package uk.co.jakestanley.commander.scene;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import uk.co.jakestanley.commander.interfaces.Loopable;
import uk.co.jakestanley.commander.scene.entities.Entity;
import uk.co.jakestanley.commander.scene.entities.PhysicalEntity;
import uk.co.jakestanley.commander.scene.entities.mobiles.Crewman;
import uk.co.jakestanley.commander.scene.entities.mobiles.Mobile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jp-st on 08/11/2015.
 */
@Getter @Setter
public class SceneController implements Loopable { // TODO consider moving ship/world stuff to a separate area

    @NonNull public PhysicalEntity area;
    @NonNull public List<Entity> allEntities;
    @NonNull public List<PhysicalEntity> physicalEntities;
    @NonNull public List<Mobile> mobileEntities;

    public SceneController(){
        // TODO initialise area
        allEntities = new ArrayList<Entity>();
        physicalEntities = new ArrayList<PhysicalEntity>();
        mobileEntities = new ArrayList<Mobile>(); // TODO consider naming this class MobileEntity
    }

    public void init() {
        addMobileEntity(new Crewman("Jeff", 0, 0, 0));
    }

    public void update() {

    }

    public void addEntity(Entity entity){
        allEntities.add(entity);
    }

    public void addPhysicalEntity(PhysicalEntity entity){
        physicalEntities.add(entity);
        allEntities.add(entity);
    }

    public void addMobileEntity(Mobile mobile){
        mobileEntities.add(mobile);
        physicalEntities.add(mobile);
        allEntities.add(mobile);
    }
}
