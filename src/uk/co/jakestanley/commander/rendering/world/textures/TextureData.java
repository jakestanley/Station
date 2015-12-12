package uk.co.jakestanley.commander.rendering.world.textures;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by jake on 12/12/2015.
 */
@Getter @AllArgsConstructor
public class TextureData {

    private ByteBuffer buffer;
    private int width;
    private int height;

}
