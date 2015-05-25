package main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by stanners on 22/05/2015.
 */
public class Tile { // TODO extend these

    public static final Color BG_BRIDGE = new Color(150, 0, 0);
    public static final Color BG_META_CORRIDOR_POINT = new Color(254,254,254);

    private Color backgroundColour    = Color.black; // black for generic rooms by default TODO change this depending on room type?
    private Color borderColour        = Color.yellow; // TODO consider a different border colour. draw borders separately?

    private Room room; // room the tile belongs to
    private int x, y, type;
    private boolean traversable = true;

    // void tile constructor
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.type = Values.Types.VOID;
        traversable = false;
    }

    public boolean isTraversable() {
        return traversable;
    }

    public boolean isVoid(){
        if(type == Values.Types.VOID){
            return true;
        } else {
            return false;
        }
    }

    public Tile(Room room, int x, int y, int type){
        this.room = room;
        this.x = x;
        this.y = y;
        this.type = type;

        if(type == Values.Types.BRIDGE){ // TODO make nicer
            backgroundColour = BG_BRIDGE;
        } else if(type == Values.Types.META_CORRIDOR_POINT){
            backgroundColour = BG_META_CORRIDOR_POINT;
            traversable = false;
        }
    }

    public void render(Graphics screen){ // TODO fill rectangles

        // set background colour
        screen.setColor(backgroundColour);

        int x = this.x * Display.TILE_WIDTH;
        int y = this.y * Display.TILE_WIDTH;

        // TODO black room floor fill

        if(type == Values.Types.CORRIDOR_X){
            Rectangle rect = new Rectangle(x, y + (Display.TILE_WIDTH / 4), Display.TILE_WIDTH, Display.TILE_WIDTH - (Display.TILE_WIDTH / 2));
            screen.fill(rect);
            screen.setColor(borderColour);
            screen.draw(rect);
        } else if(type == Values.Types.CORRIDOR_Y){ // TODO this separation of corridors and regular rooms may be redundant
            Rectangle rect = new Rectangle(x + (Display.TILE_WIDTH / 4), y, Display.TILE_WIDTH - (Display.TILE_WIDTH / 2), Display.TILE_WIDTH);
            screen.fill(rect);
            screen.setColor(borderColour);
            screen.draw(rect);
        } else {
            Rectangle rect = new Rectangle(x, y, Display.TILE_WIDTH, Display.TILE_WIDTH);
            screen.fill(rect);
            screen.setColor(borderColour);
            screen.draw(new Rectangle(x, y, Display.TILE_WIDTH, Display.TILE_WIDTH));
        }
    }

    public boolean mouseOver(int mouseX, int mouseY){

//        System.out.println("Moused over tile " + x + ", " + y);

        int x = this.x * Display.TILE_WIDTH;
        int y = this.y * Display.TILE_WIDTH;

        boolean over = false;
        if((mouseX >= x) && (mouseX <= (x + Display.TILE_WIDTH)) && (mouseY >= y) && (mouseY <= (y + Display.TILE_WIDTH))){
            over = true;
        }
        return over;
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

    public int getType(){
        return type;
    }

}