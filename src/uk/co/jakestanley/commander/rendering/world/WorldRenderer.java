package uk.co.jakestanley.commander.rendering.world;

import lombok.Getter;
import uk.co.jakestanley.commander.rendering.Renderer;

/**
 * Created by jp-st on 08/11/2015.
 */
@Getter
public abstract class WorldRenderer extends Renderer {

    public WorldRenderer(int x, int y, int width, int height){
        super(x, y, width, height);
    }

}
