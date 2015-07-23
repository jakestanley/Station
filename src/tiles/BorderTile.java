package tiles;

import main.Room;

/**
 * Created by stanners on 25/05/2015.
 */
public class BorderTile extends Tile {

    public BorderTile(int x, int y, Room room){ // TODO consider remove
        super(x, y);
    }

    @Override
    public boolean isVoid() { // TODO check
        return true;
    }

}
