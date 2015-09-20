package map;

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
    protected boolean isPlaced, isBlocking, isVisible;
    protected Image image;

    public Placeable(int x, int y, int width, int height, Image image, boolean isPlaced, boolean isBlocking, boolean isVisible){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.isPlaced = isPlaced;
        this.isBlocking = isBlocking;
        this.isVisible = isVisible;
        orientation = INITIAL_ORIENTATION;
    }

    public boolean isPlaced(){
        return isPlaced;
    }

    public boolean isBlocking(){
        return isBlocking;
    }

    public boolean isVisible(){ // TODO more
        return isVisible;
    }

    public void init(){

    }

    public void render(Graphics screen){
        // TODO set relevant stuff
        screen.drawImage(image, x, y);
    }

    public void update(){

    }

}
