package uk.co.jakestanley.commander.rendering;

import lombok.Getter;
import uk.co.jakestanley.commander.interfaces.Loopable;
import uk.co.jakestanley.commander.interfaces.Renderable;

/**
 * Created by jp-st on 08/11/2015.
 */
public abstract class Renderer implements Loopable, Renderable {

    @Getter private int x;
    @Getter private int y;
    @Getter private int width;
    @Getter private int height;

    public Renderer(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void init();

    public abstract void update();

    public abstract void render();
}
