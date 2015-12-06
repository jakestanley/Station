package uk.co.jakestanley.commander.rendering.world.threedimensional.textures;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jp-st on 13/11/2015.
 */
public class ModelTexture {

    @Getter private int textureID;
    @Getter @Setter private float shineDamper = 1;
    @Getter @Setter private float reflectivity = 0;

    public ModelTexture(int textureID){
        this.textureID = textureID;
    }

}
