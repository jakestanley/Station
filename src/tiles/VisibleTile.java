package tiles;

import main.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by stanners on 19/07/2015.
 */
public class VisibleTile extends Tile {

    public static int WALL_THICKNESS = 2;

    private boolean hasNorthWall = false;
    private boolean hasEastWall = false;
    private boolean hasSouthWall = false;
    private boolean hasWestWall = false; // TODO optimise
    protected int function;

    private int type;

    protected Color borderColour            = Colours.Tiles.BORDER; // TODO consider a different border colour. draw borders separately?
    protected Color borderColourNormal      = Colours.Tiles.BORDER;
    protected Color backgroundColour        = Colours.Tiles.BG_DEFAULT; // black for generic rooms by default
    protected Color backgroundColourNormal  = Colours.Tiles.BG_DEFAULT;

    public VisibleTile(int x, int y, Room room){
        super(x,y,room); // TODO

        this.isVoid = false; // TODO get function from room

        backgroundColour = Colours.Tiles.BG_BRIDGE; // TODO come up with an efficient way to get the colours
        backgroundColourNormal = Colours.Tiles.BG_BRIDGE;

    }

    public VisibleTile(int x, int y, Room room, int function){
        super(x,y,room); // TODO

        this.isVoid = true;
        this.function = function;

        if(Values.Types.BRIDGE == type){
            backgroundColour = Colours.Tiles.BG_BRIDGE;
            backgroundColourNormal = Colours.Tiles.BG_BRIDGE;
        } else if(Values.Types.LIFESUPPORT == type){
            backgroundColour = Colours.Tiles.BG_LIFESUPPORT;
            backgroundColourNormal = Colours.Tiles.BG_LIFESUPPORT;
        } else if(Values.Types.HANGAR == type){
            backgroundColour = Colours.Tiles.BG_HANGAR;
            backgroundColourNormal = Colours.Tiles.BG_HANGAR;
        }

    }

    public int getType() {
        return type;
    }

    public boolean hasPathTo(Room room) {
        return false;
    } // TODO remove?

    public void updateFrame(){ // what to update every frame
        updateWalls();
    }

    public void updateTick(){ // what to update every game tick

    }

    public void render(Graphics screen){
//        System.out.println("VisibleTile::render called");
        renderBackground(screen);
        renderWalls(screen);
    }

    public void renderBackground(Graphics screen){

        int drawX = this.x * Display.TILE_WIDTH + (GameController.viewController.getViewOffsetX() * Display.TILE_WIDTH);
        int drawY = this.y * Display.TILE_WIDTH + (GameController.viewController.getViewOffsetY() * Display.TILE_WIDTH);

        Rectangle rect = new Rectangle(drawX, drawY, Display.TILE_WIDTH, Display.TILE_WIDTH);

        screen.setColor(backgroundColour);
        screen.fill(rect);

    }

    public void renderWalls(Graphics screen) {

        updateWalls();
        ViewController vc = GameController.viewController;

        screen.setLineWidth(WALL_THICKNESS);
        screen.setColor(Color.white);

        int drawX = this.x * Display.TILE_WIDTH + (vc.getViewOffsetX() * Display.TILE_WIDTH);
        int drawY = this.y * Display.TILE_WIDTH + (vc.getViewOffsetY() * Display.TILE_WIDTH);

        if(hasNorthWall){
            Line northWall = new Line(drawX, drawY, drawX + Display.TILE_WIDTH, drawY);
            screen.draw(northWall);
        }

        if(hasSouthWall){
            Line southWall = new Line(drawX, drawY + Display.TILE_WIDTH, drawX + Display.TILE_WIDTH, drawY + Display.TILE_WIDTH);
            screen.draw(southWall);
        }

        if(hasEastWall) {
            Line eastWall = new Line(drawX + Display.TILE_WIDTH, drawY, drawX + Display.TILE_WIDTH, drawY + Display.TILE_WIDTH);
            screen.draw(eastWall);
        }

        if(hasWestWall){
            Line westWall = new Line(drawX, drawY, drawX, drawY + Display.TILE_WIDTH);
            screen.draw(westWall);
        }

    }

    public void setBackgroundColour(Color color){
        backgroundColour = color;
    }

    public void resetBackgroundColour(){
        backgroundColour = backgroundColourNormal;
    }

    public void setBorderColour(Color borderColour) {
        this.borderColour = borderColour;
    }

    public void resetBorderColour(){
        borderColour = borderColourNormal;
    }

    public void updateWalls(){ // TODO CONSIDER moving this into somewhere else or a separate wall class. This should only be updated when walls change
        if(y > 0){
            Tile northTile = GameController.mapController.getTile(x, y - 1);
            hasNorthWall = northTile.isVoid() || (northTile.getRoom() != this.getRoom());
        }

        if(y < GameController.mapController.getHeight()-1){
            Tile southTile = GameController.mapController.getTile(x, y + 1);
            hasSouthWall = southTile.isVoid() || (southTile.getRoom() != this.getRoom());
            // screen.draw(rect);
        }

        if(x > 0){
            Tile westTile = GameController.mapController.getTile(x-1, y);
            hasWestWall = westTile.isVoid() || (westTile.getRoom() != this.getRoom());
            // screen.draw(rect);
        }

        if(x < GameController.mapController.getWidth()-1){
            Tile eastTile = GameController.mapController.getTile(x+1, y);
            hasEastWall = eastTile.isVoid() || (eastTile.getRoom() != this.getRoom());
            // screen.draw(rect);
        }
    }

}
