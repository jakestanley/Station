package uk.co.jakestanley.commander.rendering.world.models;

import lombok.Getter;
import uk.co.jakestanley.commander.rendering.world.textures.ModelTexture;

/**
 * Created by jp-st on 13/11/2015.
 */
public class TexturedModel {

    @Getter private RawModel rawModel;
    @Getter private ModelTexture texture;

    public TexturedModel(RawModel rawModel, ModelTexture texture){
        this.rawModel = rawModel;
        this.texture = texture;
    }
}
