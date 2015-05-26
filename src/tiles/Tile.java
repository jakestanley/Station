package tiles;

import main.Display;
import main.Room;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 22/05/2015.
 */
public abstract class Tile { // TODO extend these

    protected Color backgroundColour    = Color.black; // black for generic rooms by default TODO change this depending on room type?
    protected Color borderColour        = Color.yellow; // TODO consider a different border colour. draw borders separately?

    private Room room; // room the tile belongs to
    protected int x, y;

    // void tile constructor
    public Tile(int x, int y, Room room){
        this.x = x;
        this.y = y;
        this.room = room;
    }

    public boolean mouseOver(int mouseX, int mouseY){

        int vx = this.x * Display.TILE_WIDTH;
        int vy = this.y * Display.TILE_WIDTH;

        if((mouseX >= vx) && (mouseX <= (vx + Display.TILE_WIDTH)) && (mouseY >= vy) && (mouseY <= (vy + Display.TILE_WIDTH))){
            return true;
        }

        return false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Room getRoom(){
        return room;
    }

    public abstract void render(Graphics screen);

    public abstract boolean isTraversable();

    public abstract boolean isVoid();

    public abstract int getType();

}