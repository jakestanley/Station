package mobs;

import exceptions.NoSpawnableArea;
import main.GameController;
import tiles.Tile;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by stanners on 21/07/2015.
 */
public class MobController {

    private ArrayList<Mob> mobs; // TODO mobs should have a position, not a tile. i.e if the tile changes, the mob should not get stuck to the old tile.

    public MobController(){
        mobs = new ArrayList<Mob>();
    }

    public ArrayList<Mob> getMobs(){ // TODO CONSIDER is this necessary?
        return mobs;
    }

    private void generateMobs() throws NoSpawnableArea {
        // specify target crew count
        int crewCount = 7;
        int hostileCount = 2;

        // generate crew
        for(int i = 0; i < crewCount; i++){
            Point spawn = GameController.mapController.getSpawnPoint();
            if(spawn == null){
                throw new NoSpawnableArea();
            }
            mobs.add(new Mate(spawn));
        }

        // generate hostiles
        for(int i = 0; i < hostileCount; i++){
            Point spawn = GameController.mapController.getSpawnPoint();
            if(spawn == null){
                throw new NoSpawnableArea();
            }
            mobs.add(new Parasite(spawn));
        }

    }

}
