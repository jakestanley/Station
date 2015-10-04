package map.functionals;

import map.Placeable;
import mobs.Mob;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


/**
 * Created by stanners on 27/07/2015.
 */
public abstract class Functional extends Placeable {

    protected float integrity;
    protected boolean ownable, usable;

    protected Mob owner;

    public Functional(int x, int y, int width, int height, Image image, boolean isPlaced, boolean isBlocking, boolean isVisible){
        super(x, y, width, height, image, isPlaced, isBlocking, isVisible);

        // initialise variables
        ownable = false;
        owner = null;

    }

    public boolean isOwnable(){
        return ownable;
    }

    public boolean isUsable(){
        return usable;
    }

    public abstract void use(Mob mob); // TODO CONSIDER is this called every tick? should i use an abandon method?

    public abstract void render(Graphics screen);

    @Override
    public abstract void init();

    @Override
    public abstract void update();

}
