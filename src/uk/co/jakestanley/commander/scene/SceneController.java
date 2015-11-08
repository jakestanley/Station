package uk.co.jakestanley.commander.scene;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import uk.co.jakestanley.commander.interfaces.Loopable;
import uk.co.jakestanley.commander.scene.entities.PhysicalEntity;

import java.util.List;

/**
 * Created by jp-st on 08/11/2015.
 */
public class SceneController implements Loopable { // TODO consider moving ship/world stuff to a separate area

    @NonNull @Getter @Setter public PhysicalEntity area;
    @NonNull @Getter @Setter public List<PhysicalEntity> allEntities;
    @NonNull @Getter @Setter public List<PhysicalEntity> mobileEntities;

    public SceneController(){

    }

    public void init() {

    }

    public void update() {

    }
}
