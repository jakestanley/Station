package map.functionals;

import mobs.Mob;

import java.awt.*;

/**
 * Created by stanners on 27/07/2015.
 */
public abstract class Functional {

    public static final int ORIENTATION_NORTH = 0;
    public static final int ORIENTATION_EAST = 1;
    public static final int ORIENTATION_SOUTH = 2;
    public static final int ORIENTATION_WEST = 3;

    protected Point location;
    protected int orientation;
    protected boolean isBlocker, isOwnable, isVisible, isUsable;

    protected Mob owner;

    public Functional(Point location, int orientation){
        this.location = location;
        this.orientation = orientation;

        // initialise variables
        isBlocker = false;
        isOwnable = false;
        isVisible = true; // by default
        owner = null;

    }

    public boolean isBlocker(){
        return isBlocker;
    }

    public boolean isOwnable(){
        return isOwnable;
    }

    public boolean isVisible(){
        return isVisible;
    }

    public boolean isUsable(){
        return isUsable;
    }

    public abstract void use(Mob mob); // TODO CONSIDER is this called every tick? should i use an abandon method?

    public abstract void update();

    public abstract void render();

}
