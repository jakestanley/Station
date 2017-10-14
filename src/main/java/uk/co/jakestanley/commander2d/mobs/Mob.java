package uk.co.jakestanley.commander2d.mobs;

import uk.co.jakestanley.commander2d.exceptions.NoAction;
import uk.co.jakestanley.commander2d.main.*;
import uk.co.jakestanley.commander2d.optimisation.Cacher;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import uk.co.jakestanley.commander2d.planner.Planner;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 23/05/2015.
 */
public abstract class Mob extends Loopable implements Interactable, Cacher { // TODO make abstract as its not to be instantiated directly

    public static final int TYPE_MATE = 1;
    public static final int TYPE_PARASITE = 2;

    public static final int MAX_HEALTH = 100; // TODO an increased health/resistance stat?
    public static final int BASE_MIN_OXYGEN = 20; // TODO make this different for different crew members? crew members could be randomly generated and their abilities could vary.
    public static final int BASE_MIN_HEALTH = 20; // TODO CONSIDER that uk.co.jakestanley.commander2d.mobs need a certain amount of health to move/act?
    public static final float BASE_RESILIENCE = 0; // percentage of damage ignored
    public static final float MAX_RESILIENCE = 0.9f; // max current// sible resilience
//    public static final boolean huep = 2; // TODO CONSIDER a "haste" flag.

    // TODO currentAction and currentPlanner?

    protected String name;
    protected Point previous, current;
    private boolean alive; // TODO sort private/public/protected orders
    private int fear; // TODO CONSIDER removing replacing tx,ty with a point.

    public boolean canOpen() {
        return canOpen;
    }

    protected boolean canOpen; // TODO use this for calculating
    protected Color colour;

    // stats
    protected boolean haste = false; // opening a door doesn't use a turn
    protected int minOpHealth, minOpOxygen; // TODO more variables
    protected float health, resilience;
    protected ArrayList<String> strings;

    // problem solving and path finding
    protected Planner planner;

    public Mob(Point point){
        super(0, Display.TILE_WIDTH-1); // TODO change to non static values
        alive = true;
        current = point;
        previous = point;
        fear = Values.Attributes.MENTAL_INDIFFERENT;

        // Check for spawn in void tile
        if(GameController.mapController.getTile(current).isVoid()){
            System.err.println("Mob spawned in a void tile");
        }

        generateBaseStats();
        generateSpecificStats();

        planner = null;

    }

    public Room getRoom(){
        return GameController.mapController.getRoom(current); // TODO CONSIDER does caching the room pointer really save that much uk.co.jakestanley.commander2d.resources?
    }

    private void generateBaseStats(){ // TODO make this more user based // TODO sort the order of these private and public methods
        // TODO HASTE stat
        health         = MAX_HEALTH;
        minOpOxygen    = BASE_MIN_OXYGEN;
        minOpHealth    = BASE_MIN_HEALTH;
        resilience     = BASE_RESILIENCE;
    }
    protected abstract void generateSpecificStats();

    @Override
    public void render(Graphics screen) { // TODO optimise

        int voX = GameController.viewController.getViewOffsetX();
        int voY = GameController.viewController.getViewOffsetY();

        int tx = (int) current.getX();
        int ty = (int) current.getY();
//        if(frame != frames){
//            frame++;
//        }
//
//        int ax = 0;
//        int ay = 0;
//
//        if(current
//.getX() > previous.getX()){ // if going right
//            ax = -(frames - frame);
//        } else if(current
//.getX() < previous.getX()){ // if going left
//            ax = (frames - frame);
//        } else if(current
//.getY() > previous.getY()){ // if going down
//            ay = -(frames - frame);
//        } else if(current
//.getY() > previous.getY()){ // if going up
//            ay = (frames - frame);
//        }

        screen.setColor(colour); // TODO change to other method of colouring
        int margin = (Display.TILE_WIDTH - Display.MOB_WIDTH) / 2;

        Rectangle rect = new Rectangle((tx * Display.TILE_WIDTH) + margin + (voX * Display.TILE_WIDTH), (ty * Display.TILE_WIDTH) + margin + voY * Display.TILE_WIDTH, Display.MOB_WIDTH, Display.MOB_WIDTH);
        ShapeFill fill = new GradientFill((tx * Display.TILE_WIDTH) + margin + 1, (ty * Display.TILE_WIDTH) + margin + 1, colour, Display.MOB_WIDTH - 1, Display.MOB_WIDTH - 1, colour);
        screen.fill(rect, fill);

    }

    @Override
    public void update() {

        baseDamage();
        specificDamage();

        // death check
        if(health <= 0){
            health = 0;
            die();
        }

        populateDataBoxStrings();

    }

    public void moveTo(Point position){ // TODO always create new points?

//        Room newRoom = GameController.mapController.getRoom(position); // TODO replace with points

//        // if entering a new room // TODO remove
//        if(this.room != newRoom){
//            this.room = newRoom; // set new room
//        }

        // setting new position
        previous = this.current;
        this.current = position;

        frame = 0;

    }

    public abstract String getGoalString();

    public abstract boolean isHostile();

    public abstract int getType();

    public abstract void evaluate(); // selects a new goal if necessary

    public abstract void act() throws NoAction;

    public abstract void populateDataBoxStrings(); // todo before commit.

    public Point getPoint(){
        return current;
    }

    public float getHealth(){
        return health;
    }

    public boolean alive(){
        return alive;
    }

    public void baseDamage(){ // damage done to all types of mob

        // calculate base damage done
        float damage = 1 - resilience;

        float roomOxygen = getRoom().getOxygen(); // TODO set room

//        if(getType() == Mob.TYPE_PARASITE) { // TODO resolve later
////            System.out.println("Doing parasite damage. Room oxygen: " + roomOxygen + ", min oxygen: " + minOpOxygen);
//        }

        if(getRoom().getOxygen() < minOpOxygen){
//            System.out.println("taking oxygen dep dmg");
            health = health - damage; // TODO make it less black and white?
        }


             // TODO CONSIDER is -- mutator right for floats?

        // TODO mob should process damage done to itself via looking at the room its in, and what is in the room with it.
        // if there's a parasite there, it's likely to take damage from that.
        // if there's no oxygen, it's likely to take damage there also, so it can get real bad real quick.
    }

    public void refresh(){ // TODO remove

    }

    public abstract void specificDamage();

    public String getName(){
        return name;
    }

    public void fearIntensify(){
        if(fear != Values.Attributes.MENTAL_STATE_MAX_LEVEL){
            fear++;
        }
    }

    public void fearReduce(){
        if(fear > 0){
            fear--;
        }
    }

    public int getFear(){
        return fear;
    }

    public void dropAction(){
        // TODO
    }

    public void renderHoverBox(Graphics screen){ // TODO make more responsive

//        System.out.println("Render mob hover box called");

        int tx = (int) current.getX();
        int ty = (int) current.getY();

        screen.setColor(Colours.HOVER_SELECT);

        int margin = (Display.TILE_WIDTH - Display.MOB_WIDTH) / 2;

        Rectangle rect = new Rectangle((tx * Display.TILE_WIDTH) + margin - 4, (ty * Display.TILE_WIDTH) + margin - 4, Display.MOB_WIDTH + 8, Display.MOB_WIDTH + 8); // TODO replace these hard coded values

        screen.draw(rect);

    }

    public void renderDataBox(Graphics screen) {

        // initialising variables
        int x = dbx;
        int y = dby;

        // iterate through and present strings
        for (Iterator<String> iterator = strings.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            screen.drawString(next, x, y); // TODO make this more efficient
            y = y + Display.TEXT_SPACING;
        }

    }

    private void die(){
        if(alive){
            System.out.println(name + " died");
            alive = false;
            colour = Color.lightGray;
        }
    }

}
