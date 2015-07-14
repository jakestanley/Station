package tiles;

import main.Colours;
import main.Display;
import main.Room;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 22/05/2015.
 */
public abstract class Tile { // TODO extend these

    protected static final float MAX_HEALTH = 100;

    protected Color backgroundColour        = Colours.Tiles.BG_DEFAULT; // black for generic rooms by default
    protected Color borderColour            = Colours.Tiles.BORDER; // TODO consider a different border colour. draw borders separately?
    protected Color borderColourNormal      = Colours.Tiles.BORDER;
    protected Color backgroundColourNormal  = Colours.Tiles.BG_DEFAULT;

    protected int function;
    protected float health;

    private Room room; // room the tile belongs to
    protected int x, y;

    // void tile constructor
    public Tile(int x, int y, Room room, int function){
        this.x = x;
        this.y = y;
        this.room = room;
        this.function = function;
        this.health = MAX_HEALTH;
    }

    public boolean mouseOver(int mouseX, int mouseY, int viewOffsetX, int viewOffsetY){

        int vx = this.x * Display.TILE_WIDTH + (viewOffsetX * Display.TILE_WIDTH);
        int vy = this.y * Display.TILE_WIDTH + (viewOffsetY * Display.TILE_WIDTH);

        if((mouseX >= vx) && (mouseX <= (vx + Display.TILE_WIDTH)) && (mouseY >= vy) && (mouseY <= (vy + Display.TILE_WIDTH))){
            return true;
        }

        return false;
    }

    public void setBackgroundColour(Color color){
        backgroundColour = color;
    }

    public void resetBackgroundColour(){
        backgroundColour = backgroundColourNormal;
    }

    public void resetBorderColour(){
        borderColour = borderColourNormal;
    }

    public void setBorderColour(Color borderColour) {
        this.borderColour = borderColour;
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

    public abstract void render(Graphics screen, int viewOffsetX, int viewOffsetY);

    public abstract boolean isTraversable();

    public abstract boolean isVoid();

    public abstract int getType();

    public abstract boolean hasPathTo(Room room); // bit dodgy, but this is for TraversibleTile only

    public static class Functions { // throwing shit at the wall here to see what sticksA

        public static final int MEDICAL = 1;
        public static final int TURRET_CONTROL = 2;

    }

}