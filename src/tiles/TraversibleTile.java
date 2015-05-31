package tiles;

import main.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;

/**
 * Created by stanners on 25/05/2015.
 */
public class TraversibleTile extends Tile { // TODO CONSIDER will there be other kinds of visible tile?

    private int type;

    public TraversibleTile(int x, int y, Room room, int type){
        super(x, y, room);
        this.type = type;

        if(Values.Types.BRIDGE == type){
            backgroundColour = Colours.Tiles.BG_BRIDGE;
            originalBackgroundColour = Colours.Tiles.BG_BRIDGE;
        } else if(Values.Types.LIFESUPPORT == type){
            backgroundColour = Colours.Tiles.BG_LIFESUPPORT;
            originalBackgroundColour = Colours.Tiles.BG_LIFESUPPORT;
        } else if(Values.Types.HANGAR == type){
            backgroundColour = Colours.Tiles.BG_HANGAR;
            originalBackgroundColour = Colours.Tiles.BG_HANGAR;
        }
    }

    @Override
    public boolean isTraversable() {
        return true;
    }

    @Override
    public boolean isVoid() {
        return false;
    }

    public boolean isCorridor(){
        if(Values.Types.CORRIDOR_Y == type || Values.Types.CORRIDOR_X == type){ // TODO ultimately consolidate CORRIDOR_Y and CORRIDOR_X into CORRIDOR
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void render(Graphics screen) {

        // get dimensions
        int x = this.x * Display.TILE_WIDTH;
        int y = this.y * Display.TILE_WIDTH;

        // get appropriate rectangle shape
        if(isCorridor()){ // TODO render corridor appropriately instead
            renderCorridor(screen, x, y);
        } else {
            Rectangle rect = new Rectangle(x, y, Display.TILE_WIDTH, Display.TILE_WIDTH);

            // draw background
            screen.setColor(backgroundColour);
            screen.fill(rect);

            // draw border
            screen.setColor(borderColour);
            screen.draw(rect);
        }



    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public boolean hasPathTo(Room target){ // see if a tile is connected to a room
        return false; // TODO
    }

    private void renderCorridor(Graphics screen, int dx, int dy){ // TODO change depending on whether or not there is an attached door

        boolean hasNorth = false, hasEast = false, hasSouth = false, hasWest = false;

        if(y > 0){
            hasNorth = !Game.map.tiles[x][y-1].isVoid();
        }

        if(y < Game.map.getHeight()-1){
            hasSouth = !Game.map.tiles[x][y+1].isVoid();
        }

        if(x > 0){
            hasWest = !Game.map.tiles[x-1][y].isVoid();
        }

        if(x < Game.map.getWidth()-1){
            hasEast = !Game.map.tiles[x+1][y].isVoid();
        }

        Rectangle rect = null;

        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>(); // TODO CONSIDER removing

        if(hasNorth && hasSouth && !hasEast && !hasWest){ // render corridor X (vertical line)
            rect = new Rectangle(dx + (Display.TILE_WIDTH / 4), dy, Display.TILE_WIDTH - (Display.TILE_WIDTH / 2), Display.TILE_WIDTH);
        } else if(hasEast && hasWest && !hasNorth && !hasSouth){
            rect = new Rectangle(dx, dy + (Display.TILE_WIDTH / 4), Display.TILE_WIDTH, Display.TILE_WIDTH - (Display.TILE_WIDTH / 2));
        } else { // draw a junction room?
            rect = new Rectangle(dx, dy, Display.TILE_WIDTH, Display.TILE_WIDTH);
        }

        // draw background
        screen.setColor(backgroundColour);
        screen.fill(rect);

        // draw border
        screen.setColor(borderColour);
        screen.draw(rect);


    }

}
