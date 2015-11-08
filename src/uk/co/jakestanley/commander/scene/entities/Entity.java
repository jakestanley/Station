package uk.co.jakestanley.commander.scene.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by jp-st on 08/11/2015.
 */
@ToString
public class Entity {

    private String id;

    public Entity(String id){
        this.id = id;
    }

}
