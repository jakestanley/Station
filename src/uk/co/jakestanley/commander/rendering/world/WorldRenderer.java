package uk.co.jakestanley.commander.rendering.world;

import lombok.Getter;
import uk.co.jakestanley.commander.rendering.Renderer;

/**
 * Created by jp-st on 08/11/2015.
 */
@Getter
public abstract class WorldRenderer extends Renderer {

    // canvas values; drawable space
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public WorldRenderer(int x, int y, int width, int height){
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}
