package tiles;

import main.Display;
import main.Game;
import main.Room;
import main.Values;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by stanners on 25/05/2015.
 */
public class BorderTile extends Tile {

    public BorderTile(int x, int y, Room room){
        super(x, y, room);
        backgroundColour = Color.white;
    }

    @Override
    public boolean isTraversable() {
        return false;
    }

    @Override
    public boolean isVoid() { // TODO check
        return true;
    }

    @Override
    public void render(Graphics screen) {
        if(Game.debug){

            int x = this.x * Display.TILE_WIDTH;
            int y = this.y * Display.TILE_WIDTH;

            // get rectangle shape
            Rectangle rect = new Rectangle(x, y, Display.TILE_WIDTH, Display.TILE_WIDTH);

            // draw background
            screen.setColor(backgroundColour);
            screen.fill(rect);

        }
    }

    @Override
    public int getType() {
        return Values.Types.META_BORDER;
    }

}
