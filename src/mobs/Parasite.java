package mobs;

import actions.Action;
import exceptions.IllegalAction;
import exceptions.ImpossibleGoal;
import exceptions.NoAction;
import exceptions.UnnecessaryAction;
import main.Game;
import org.newdawn.slick.Color;
import planner.Random;
import tiles.Tile;

import java.util.ArrayList;

/**
 * Created by stanners on 23/05/2015.
 */
public class Parasite extends Mob {

    public Parasite(int x, int y){
        super(x, y);
        name = "Parasite";
        colour = Color.red;
        canOpen = false;
    }

    @Override
    public void init() {

    }

    public Parasite(int x, int y, int health){ // TODO consider removing
        super(x, y, health);
        name = "Parasite";
        colour = Color.red; // TODO make better colours
        canOpen = false;
    }

    @Override
    protected void generateSpecificStats() {
        // TODO populate with code
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
    public void act() throws NoAction {

        if(planner == null){
            Tile target = Game.map.getBridge().getRandomTile();
//            planner = new Destination(this, target.getX(), target.getY());
            planner = new Random(this);
        }

        try {
            planner.calculate();
            Action action = planner.getNextAction();
            if(action == null){
                throw new NoAction(this);
            }
            action.executeAction();
        } catch (UnnecessaryAction unnecessaryAction) {
            System.err.println("Caught unnecessary action exception");
            unnecessaryAction.printStackTrace();
            act(); // call act again if the action was unnecessary, e.g opening an already open door
        } catch (IllegalAction illegalAction) {
            System.err.println("Caught illegal action exception");
            illegalAction.printStackTrace(); // TODO why is it illegal?
        } catch (ImpossibleGoal impossibleGoal) {
            System.err.println("Caught impossible goal exception");
            impossibleGoal.printStackTrace();
        }


    }

    @Override
    public void populateDataBoxStrings() {
        strings = new ArrayList<String>();
        strings.add("random string: " + Game.random.nextFloat());
    }

    @Override
    public void specificDamage() {
        // TODO specific damage only done to subclasses
    }

    @Override
    public boolean mouseOver(int mouseX, int mouseY) {
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
