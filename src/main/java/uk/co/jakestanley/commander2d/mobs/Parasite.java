package uk.co.jakestanley.commander2d.mobs;

import uk.co.jakestanley.commander2d.actions.Action;
import uk.co.jakestanley.commander2d.exceptions.IllegalAction;
import uk.co.jakestanley.commander2d.exceptions.ImpossibleGoal;
import uk.co.jakestanley.commander2d.exceptions.NoAction;
import uk.co.jakestanley.commander2d.exceptions.UnnecessaryAction;
import uk.co.jakestanley.commander2d.main.GameController;
import org.newdawn.slick.Color;
import uk.co.jakestanley.commander2d.planner.Random;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by stanners on 23/05/2015.
 */
public class Parasite extends Mob {

    ArrayList<Point> explored;

    public Parasite(Point point){
        super(point);
        name = "Parasite";
        colour = Color.red;
        canOpen = false;
        explored = new ArrayList<Point>();
    }

    @Override
    public void init() {

    }

    @Override
    protected void generateSpecificStats() {
        // TODO populate with code
    }

    @Override
    public String getGoalString() {
        return "";
    }

    @Override
    public boolean isHostile() {
        return true;
    }

    @Override
    public int getType() {
        return TYPE_PARASITE;
    }

    @Override
    public void evaluate() {

    }

    @Override
    public void act() throws NoAction {

        if(planner == null){
//            Tile target = Game.uk.co.jakestanley.commander2d.map.getBridge().getRandomTile();
//            uk.co.jakestanley.commander2d.planner = new Destination(this, target.getX(), target.getY());
            planner = new Random(this);
        }

        try {
            planner.calculate();
            Action action = planner.getNextAction();
            if(action == null){
                throw new NoAction(this);
            }
            action.execute();
        } catch (UnnecessaryAction unnecessaryAction) {
            System.err.println("Caught unnecessary action exception");
            unnecessaryAction.printStackTrace();
            act(); // call act again if the action was unnecessary, e.g opening an already open door
        } catch (IllegalAction illegalAction) {
            System.err.println("Caught illegal action exception");
            illegalAction.printStackTrace(); // TODO why is it illegal?
        } catch (ImpossibleGoal impossibleGoal) {
//            System.err.println("Caught impossible goal exception"); // TODO comment back in
            impossibleGoal.printStackTrace();
        }


    }

    @Override
    public void populateDataBoxStrings() {
        strings = new ArrayList<String>();
        strings.add("random string: " + GameController.random.nextFloat());
    }

    @Override
    public void specificDamage() { // TODO specific damage only done to subclasses
        // TODO put this in a more appropriate place

        // add this tile to explored uk.co.jakestanley.commander2d.tiles if not already explored
        if(!explored.contains(current)){ // can query this tile and surrounding uk.co.jakestanley.commander2d.tiles, e.g for doors
            explored.add(current);
        }

    }

    public ArrayList<Point> getExplored(){
        return explored;
    }

    public boolean mouseOver(Point mousePoint) {
        return false;
    }

}
