package uk.co.jakestanley.commander.scene.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.co.jakestanley.commander.interfaces.Loopable;

/**
 * Created by jp-st on 08/11/2015.
 */
@ToString
public abstract class Entity implements Loopable {

    @Getter protected String id;

    public Entity(String id){
        this.id = id;
    }

}
