package actions;

import exceptions.IllegalAction;
import main.Door;
import main.Game;
import main.Tile;
import mobs.Mob;

/**
 * Created by stanners on 24/05/2015.
 */
public class Move extends Action { // TODO validate

    private int tx, ty;

    public Move(Mob mob, int tx, int ty){ // where to?
        super(mob, ACTION_MOVE);
        this.tx = tx;
        this.ty = ty;
    }

    @Override
    public void executeAction() throws IllegalAction {

        // TODO check for void tiles here?

        Tile tile1, tile2;
        tile1 = mob.getTile();
        tile2 = Game.map.tiles[tx][ty];

//        System.out.println("Moving from [" + tile1.getX() + ", " + tile1.getY() + "] to [" + tile2.getX() + ", " + tile2.getY() + "]");

        Door door = Game.map.getDoorByTiles(tile1, tile2);
        if(door != null){
            if(!door.isOpen()){
                throw new IllegalAction("Tried to move to tile but there was a closed door in the way.");
            }
        }
        mob.moveTo(tx, ty);
    }
}
