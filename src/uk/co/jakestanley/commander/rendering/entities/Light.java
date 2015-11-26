package uk.co.jakestanley.commander.rendering.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by jake on 26/11/2015.
 */
@Getter @Setter @AllArgsConstructor
public class Light {

    private Vector3f position; // TODO inheritance, 3d world entities
    private Vector3f colour;
}
