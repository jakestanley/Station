package mobs;

import actions.Action;
import exceptions.IllegalAction;
import exceptions.ImpossibleGoal;
import exceptions.UnnecessaryAction;
import main.Game;
import main.Values;
import org.newdawn.slick.Color;
import planner.Random;

import java.util.ArrayList;

/**
 * Created by stanners on 23/05/2015.
 */
public class Mate extends Mob {

    public Mate(int x, int y){
        super(x, y);
        name = generateName();
        colour = Color.green;
        canOpen = true;
    }

    @Override
    public void init() {

    }

    public Mate(int x, int y, int health){
        super(x, y, health);
        name = generateName();
        colour = Color.green;
        canOpen = true;
    }

    @Override
    protected void generateSpecificStats() {

    }

    @Override
    public boolean isHostile() {
        return false;
    }

    @Override
    public int getType() {
        return TYPE_MATE;
    }

    @Override
    public void act() {

        // mates just wander aimlessly for now. need to make them open doors
        if(planner == null){
            planner = new Random(this);
        }

        try {
            planner.calculate();
            Action action = planner.getNextAction();
            action.executeAction();
        } catch (UnnecessaryAction unnecessaryAction) {
            System.err.println("Caught unnecessary action exception");
            unnecessaryAction.printStackTrace();
            act(); // call act again if the action was unnecessary, e.g opening an already open door
        } catch (IllegalAction illegalAction) {
            System.err.println("Caught illegal action exception");
            illegalAction.printStackTrace(); // TODO why is it illegal?
        } catch (ImpossibleGoal impossibleGoal) {
//            System.err.println("Caught impossible goal exception"); // TODO comment back in
        } catch (NullPointerException nullPointerException){
            System.out.println("No action available for " + name);
        }

    }

    @Override
    public void populateDataBoxStrings() {
        strings = new ArrayList<String>();
        strings.add("random string: " + Game.random.nextFloat());
    }

    @Override
    public void specificDamage() {
        // TODO specific damage done only to subclasses
    }

    public String generateName(){
        return Values.names[Game.random.nextInt(Values.names.length)];
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
