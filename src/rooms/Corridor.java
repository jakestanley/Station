package rooms;

import main.Game;
import main.Room;
import main.Values;
import tiles.Tile;
import tiles.VisibleTile;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 12/07/2015.
 */
public class Corridor extends Room {

    public Corridor(ArrayList<Tile> cTiles){ // TODO complete this one for corridors
        super();

        this.tiles = new ArrayList<Tile>();

        this.integrity = MAX_INTEGRITY;
        this.oxygen = MAX_OXYGEN;
        this.strings = new ArrayList<String>();

        purge = false; // oxygen purge on/off
        evacuate = false; // evacuation alarm on/off
        support = true;

        priority = DEFAULT_PRIORITY;
        int tileCount = tiles.size();

        ventRate           = BASE_PURGE_RATE / tileCount; // TODO consider isn't this just tiles.length?
        refillRate         = BASE_REFILL_RATE / tileCount;
        consumptionRate    = BASE_CONSUMPTION_RATE / tileCount;

        // TODO fix these
        ventRate = 1; //        ventRate           = BASE_PURGE_RATE / (sx * sy);
        refillRate = 1; //        refillRate         = BASE_REFILL_RATE / (sx * sy);
        consumptionRate = 1; //        consumptionRate    = BASE_CONSUMPTION_RATE / (sx * sy);

        this.type = Values.Types.CORRIDOR; // TODO set type. corridor x/y no longer relevant
        typeString = Values.Strings.rooms[this.type]; // TODO consider maintenance shaft as another type? then this would need to be in an if/else

        generateTiles(cTiles);

    }

    @Override
    public boolean mouseOver(int mouseX, int mouseY) { // TODO
        return false;
    }

    public void generateTiles(ArrayList<Tile> cTiles){
        System.out.println("Initialising tiles cor");
        for (Iterator<Tile> iterator = cTiles.iterator(); iterator.hasNext(); ) {
            Tile next = iterator.next();
            int x = next.getX();
            int y = next.getY();
            VisibleTile tTile = new VisibleTile(x, y, this, type);
            Game.map.tiles[x][y] = tTile; // switch out from the array
            tiles.add(tTile);
        }
    }

}
