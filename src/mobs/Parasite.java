package mobs;

import actions.Action;
import exceptions.IllegalAction;
import exceptions.ImpossibleGoal;
import exceptions.NoAction;
import exceptions.UnnecessaryAction;
import main.GameController;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import planner.Random;

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
//            Tile target = Game.map.getBridge().getRandomTile();
//            planner = new Destination(this, target.getX(), target.getY());
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
    public void renderHoverBox(Graphics screen) {

    }

    @Override
    public void specificDamage() { // TODO specific damage only done to subclasses
        // TODO put this in a more appropriate place

        // add this tile to explored tiles if not already explored
        if(!explored.contains(current)){ // can query this tile and surrounding tiles, e.g for doors
            explored.add(current);
        }

    }

    public ArrayList<Point> getExplored(){
        return explored;
    }

    @Override
    public boolean mouseOver(Point mousePoint) {
        return false;
    }

    @Override
    public void qPress() {

    }

    @Override
    public void wPress() {

    }

    @Override
    public void ePress() {

    }

    @Override
    public void rPress() {

    }

    @Override
    public void vPress() {

    }

}
