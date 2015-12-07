package uk.co.jakestanley.commander2d.map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by stanners on 20/09/2015.
 */
public class Placeable {

    public static final int ORIENTATION_NORTH = 0;
    public static final int ORIENTATION_EAST = 1;
    public static final int ORIENTATION_SOUTH = 2;
    public static final int ORIENTATION_WEST = 3;
    public static final int INITIAL_ORIENTATION = ORIENTATION_NORTH;

    protected int x, y, width, height, orientation;
    protected boolean placed, blocking, visible;
    protected Image image;

    public Placeable(int x, int y, int width, int height, Image image, boolean placed, boolean blocking, boolean visible){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.placed = placed;
        this.blocking = blocking;
        this.visible = visible;
        orientation = INITIAL_ORIENTATION;
    }

    public boolean isPlaced(){
        return placed;
    }

    public boolean isBlocking(){
        return blocking;
    }

    public boolean isVisible(){ // TODO more
        return visible;
    }

    public void init(){

    }

    public void render(Graphics screen){
        screen.drawImage(image, x, y);
    }

    public void update(){

    }

    public void setPlaced() {
        placed = true;
    }
}
