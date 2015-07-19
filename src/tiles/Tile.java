package tiles;

import main.Display;
import main.Game;
import main.Room;

/**
 * Created by stanners on 22/05/2015.
 */
public class Tile { // TODO extend these

    protected static final float MAX_HEALTH = 100;

    protected int x, y;
    protected float health;
    protected Room room; // room the tile belongs to

    // void tile constructor
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
    }

    // regular visible tile superconstructor
    public Tile(int x, int y, Room room){
        this.x = x;
        this.y = y;
        this.room = room;
        this.health = MAX_HEALTH;
    }

    public boolean mouseOver(int mouseX, int mouseY){

        int vx = this.x * Display.TILE_WIDTH + (Game.vc.getViewOffsetX() * Display.TILE_WIDTH);
        int vy = this.y * Display.TILE_WIDTH + (Game.vc.getViewOffsetY() * Display.TILE_WIDTH);

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

    public void setRoom(Room room){
        this.room = room;
    }

    public boolean isVoid(){
        return (getRoom() == null);
    }

    public boolean isTraversable(){ // TODO remove
        return !isVoid();
    }

}