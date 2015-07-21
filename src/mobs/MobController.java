package mobs;

import java.util.ArrayList;

/**
 * Created by stanners on 21/07/2015.
 */
public class MobController {

    private ArrayList<Mob> mobs; // TODO mobs should have a position, not a tile. i.e if the tile changes, the mob should not get stuck to the old tile.

    public MobController(){
        mobs = new ArrayList<Mob>();
    }

    public ArrayList<Mob> getMobs(){

        return mobs;
    }

}
