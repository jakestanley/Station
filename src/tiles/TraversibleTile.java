package tiles;

import main.Colours;
import main.Display;
import main.Room;
import main.Values;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

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
        } else if(Values.Types.LIFESUPPORT == type){
            backgroundColour = Colours.Tiles.BG_LIFESUPPORT;
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

    @Override
    public void render(Graphics screen) {

        // get dimensions
        int x = this.x * Display.TILE_WIDTH;
        int y = this.y * Display.TILE_WIDTH;

        Rectangle rect;

        // get appropriate rectangle shape
        if(type == Values.Types.CORRIDOR_X){
            rect = new Rectangle(x, y + (Display.TILE_WIDTH / 4), Display.TILE_WIDTH, Display.TILE_WIDTH - (Display.TILE_WIDTH / 2));
        } else if(type == Values.Types.CORRIDOR_Y){ // TODO this separation of corridors and regular rooms may be redundant
            rect = new Rectangle(x + (Display.TILE_WIDTH / 4), y, Display.TILE_WIDTH - (Display.TILE_WIDTH / 2), Display.TILE_WIDTH);
        } else {
            rect = new Rectangle(x, y, Display.TILE_WIDTH, Display.TILE_WIDTH);
        }

        // draw background
        screen.setColor(backgroundColour);
        screen.fill(rect);

        // draw border
        screen.setColor(borderColour);
        screen.draw(rect);

    }

    @Override
    public int getType() {
        return type;
    }

    public boolean hasPathTo(Room target){ // see if a tile is connected to a room
        return false; // TODO
    }

}
