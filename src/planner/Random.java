package planner;

import actions.Action;
import actions.Move;
import actions.OpenDoor;
import exceptions.ImpossibleGoal;
import main.Door;
import main.Game;
import tiles.Tile;
import mobs.Mob;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * For mobs moving randomly
 * Created by stanners on 24/05/2015.
 */
public class Random extends Planner {

    public Random(Mob mob){
        super(mob, GOAL_RANDOM);
    }

    @Override
    public void calculate() throws ImpossibleGoal {

        // initialising list of list of next actions
        ArrayList<ArrayList<Action>> options = new ArrayList<ArrayList<Action>>();

        ArrayList<Point> moves = Planner.getPossibleMoves(mob.getX(), mob.getY());
        for (Iterator<Point> pointIterator = moves.iterator(); pointIterator.hasNext(); ) {

            // TODO sort
            Point next =  pointIterator.next();
            int tx = (int) next.getX();
            int ty = (int) next.getY(); // TODO replace this quick and dirty business

            Tile currentTile = mob.getTile();
            if(tx < 0 || ty < 0){
                throw new ImpossibleGoal("Tried to access array with sub zero values");
            }

            Tile nextTile = Game.map.tiles[tx][ty]; // TODO fix this workaround

            if((tx >= 0) && (ty >= 0) && nextTile.isTraversable()){ // TODO also check that the border tiles aren't too big, as there are upper limits too

                Door door = Game.map.getDoorByTiles(currentTile, nextTile); // TODO sort

                ArrayList<Action> actions = new ArrayList<Action>();
                if(door == null){ // if there's no door, go straight through
                    actions.add(new Move(mob, tx, ty));
                } else if(door.isOpen()){ // if the door is open, go straight through
                    actions.add(new Move(mob, tx, ty));
                } else if(!door.isOpen() && mob.canOpen() && !door.isLocked()) { // if the door is shut, but the mob can open it, and it's not locked, open it and go through
                    if (!door.isOpen()) { // if there is a door, check if it's locked
                        actions.add(new OpenDoor(mob, door));
                        actions.add(new Move(mob, tx, ty));
                    } else {
                        actions.add(new Move(mob, tx, ty));
                    }
                }
                options.add(actions); // TODO get better names for these list variables

            }
        }

        // Clarify legal options
        int count = options.size();
        if(count > 0){
            actions = options.get(Game.random.nextInt(options.size()));
        } else {
            System.err.println("Cannot move mob as there are no valid moves. FIRST.");
            throw new ImpossibleGoal("Cannot move mob as there are no valid moves. SECOND");
        }

    }

    @Override
    public boolean achieved() {
        return false;
    }
}
