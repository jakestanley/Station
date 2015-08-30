package mobs;

import actions.Action;
import exceptions.IllegalAction;
import exceptions.ImpossibleGoal;
import exceptions.UnnecessaryAction;
import io.Inputtable;
import main.Game;
import main.GameController;
import main.Values;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import planner.Evacuate;
import planner.Planner;
import planner.Random;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by stanners on 23/05/2015.
 */
public class Mate extends Mob implements Inputtable {

    private float energy; // tiredness/wakefulness
    private float morale; // TODO CONSIDER personal events, relational events, local events, ship wide events, global events

    public Mate(Point point){
        super(point);
        name = generateName();
        colour = Color.green;
        canOpen = true;
    }

    @Override
    public void init() {

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
    public void evaluate() { // TODO CONSIDER getNextAction
//        System.out.println("Evaluating options");



        // TODO CONSIDER if a more pressing matter is at hand, override the current planner. always do the highest priority. take a piss at 50% piss if doing a low priority task, but don't take a 50% piss if you're on a high importance task

        // if the goal has been reached, clear the planner
        if(planner != null){
            if(planner.achieved()){
                planner = null;
            }
        }

        // run planner override conditions
        // TODO make sure this doesn't make a loop. check destination is safe before checking, e.g don't evacuate if has a destination
        // TODO fear levels and escape to safety threshold

        // if evacuate alarm is on and can evacuate
        if(getRoom().isEvacuate() && Game.map.hasEvacuatableRoom()){ // TODO move this stuff into decision engine, or separate planner static class?
            if(planner == null){
                planner = new Evacuate(this);
            } else if (Planner.GOAL_EVACUATE != planner.getType()){
                planner = new Evacuate(this);
            }
        }

        // if still has no planner, generate a planner
        if(planner == null){
            // TODO
        }
    }

    @Override
    public void act() {

        // mates just wander aimlessly for now. need to make them open doors
        if(planner == null){
            planner = new Random(this); // move randomly if there is no planner
        }

        try {
            planner.calculate();
            Action action = planner.getNextAction();
            action.execute();
        } catch (UnnecessaryAction unnecessaryAction) {
            System.err.println("Caught unnecessary action exception");
            act(); // call act again if the action was unnecessary, e.g opening an already open door
        } catch (IllegalAction illegalAction) {
            System.err.println("Caught illegal action exception");
            illegalAction.printStackTrace(); // TODO why is it illegal?
        } catch (ImpossibleGoal impossibleGoal) {
//            System.err.println("Caught impossible goal exception"); // TODO comment back in
        } catch (NullPointerException nullPointerException){
//            System.out.println("No action available for " + name);
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
    public void specificDamage() {
        // TODO specific damage done only to subclasses
    }

    public String generateName(){
        return Values.names[GameController.random.nextInt(Values.names.length)];
    }

    @Override
    public boolean mouseOver(Point mousePoint) {
        return false;
    }

    @Override
    public String getGoalString(){
        if(planner == null){
            return "no goal";
        } else {
            return planner.getGoalString();
        }
    }

    @Override
    public void refresh() {

    }

    @Override
    public void input(int i, char c) {
        // TODO
    }
}
