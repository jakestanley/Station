package uk.co.jakestanley.commander.rendering;

import lombok.Getter;
import uk.co.jakestanley.commander.interfaces.Loopable;
import uk.co.jakestanley.commander.interfaces.Renderable;

/**
 * Created by jp-st on 08/11/2015.
 */
@Getter
public abstract class Renderer implements Loopable, Renderable {

    // canvas values; drawable space
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public Renderer(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        // TODO something
    }

}
