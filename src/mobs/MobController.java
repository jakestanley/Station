package mobs;

import exceptions.NoAction;
import exceptions.NoSpawnableArea;
import main.GameController;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 21/07/2015.
 */
public class MobController {

    private ArrayList<Mob> mobs; // TODO mobs should have a position, not a tile. i.e if the tile changes, the mob should not get stuck to the old tile.

    public MobController(){
        mobs = new ArrayList<Mob>();
    }

    public void init(){
        generateMobs();
    }

    private void generateMobs(){
        // specify target crew count
        int crewCount = 30;
//        int hostileCount = 2;

        try {

            // generate crew
            for (int i = 0; i < crewCount; i++) {
                spawnMob(true);
            }

            // generate hostiles
//            for (int i = 0; i < hostileCount; i++) {
//                spawnMob(false);
//            }

        } catch(NoSpawnableArea nsa){

            System.out.println("MapController returned null when trying to get a spawnable area for a mob");
            nsa.printStackTrace();

        }

    }

    private void spawnMob(boolean friendly) throws NoSpawnableArea {
        Point spawn = GameController.mapController.getSpawnPoint();
        if(spawn == null){
            throw new NoSpawnableArea();
        }
        if(friendly){
            mobs.add(new Mate(spawn));
        } else {
            mobs.add(new Parasite(spawn));
        }
    }

    public ArrayList<Mob> getMobs(){ // TODO CONSIDER is this necessary?
        return mobs;
    }

    public void updateMobs(){
        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob next = iterator.next();
            next.update();
        }
    }

    public void executeMobEvaluations(){
        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob next = iterator.next();
            if(next.alive()){
                next.evaluate();
            }
        }
    }

    public void executeMobActions(){
        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob next = iterator.next();
            if(next.alive()){
                try {
                    next.act();
                } catch (NoAction noAction) {
                    System.err.println(next.getName() + " can't find anywhere to go");
                }

            }
        }
    }

    public void renderMobs(Graphics screen){
        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob next = iterator.next();
            next.render(screen);
        }
    }

}
