package tiles;

import main.Room;
import main.Values;
import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 25/05/2015.
 */
public class VoidTile extends Tile {

    public VoidTile(int x, int y){
        super(x, y, null);
    }

    @Override
    public boolean isTraversable() {
        return false;
    }

    @Override
    public boolean isVoid() {
        return true;
    }

    @Override
    public void render(Graphics screen) {
        // do nothing.
    }

    @Override
    public int getType() {
        return Values.Types.VOID;
    }

    @Override
    public boolean hasPathTo(Room room) {
        return false;
    }
}
