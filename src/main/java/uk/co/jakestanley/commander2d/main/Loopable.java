package uk.co.jakestanley.commander2d.main;

import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 24/05/2015.
 */
public abstract class Loopable {

    protected int frame;
    protected int frames; // max amount of frames for this class

    public Loopable(int frame, int frames){
        this.frame = frame;
        this.frames = frames;
    }

    public abstract void init();

    public abstract void render(Graphics screen);

    public abstract void update();

}
