package uk.co.jakestanley.commander2d.tiles;

import uk.co.jakestanley.commander2d.main.Display;
import uk.co.jakestanley.commander2d.main.GameController;
import uk.co.jakestanley.commander2d.main.Room;
import uk.co.jakestanley.commander2d.main.ViewController;

import java.awt.*;

/**
 * Created by stanners on 22/05/2015.
 */
public class Tile {

    protected static final float MAX_HEALTH = 100;

    protected boolean isVoid, isSelected;
    protected int x, y;
    protected Room room; // room the tile belongs to

    // void tile constructor
    public Tile(int x, int y){
        this.isVoid = true;
        this.isSelected = false;
        this.x = x;
        this.y = y;
    }

    // regular visible tile superconstructor // TODO OPTIMISE these constructors
    public Tile(int x, int y, Room room){
        this.isSelected = false;
        this.x = x;
        this.y = y;
        this.room = room;
    }

    public void setSelected(boolean selected){
        isSelected = selected;
    }

    public boolean mouseOver(int mouseX, int mouseY){

        ViewController vc = GameController.viewController;

        int vx = this.x * Display.TILE_WIDTH + (vc.getViewOffsetX() * Display.TILE_WIDTH);
        int vy = this.y * Display.TILE_WIDTH + (vc.getViewOffsetY() * Display.TILE_WIDTH);

        return (mouseX >= vx) && (mouseX <= (vx + Display.TILE_WIDTH)) && (mouseY >= vy) && (mouseY <= (vy + Display.TILE_WIDTH));

    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Point getPoint(){
        return new Point(x, y);
    }

    public Room getRoom(){
        return room;
    }

    public void setRoom(Room room){
        this.room = room;
    }

    public boolean isVoid(){
        return isVoid;
    }

}