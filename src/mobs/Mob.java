package mobs;

import exceptions.NoAction;
import main.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import planner.Planner;
import main.Room;
import tiles.Tile;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 23/05/2015.
 */
public abstract class Mob extends Loopable implements Interactable { // TODO make abstract as its not to be instantiated directly

    public static final int TYPE_MATE = 1;
    public static final int TYPE_PARASITE = 2;

    public static final int MAX_HEALTH = 100; // TODO an increased health/resistance stat?
    public static final int BASE_MIN_OXYGEN = 20; // TODO make this different for different crew members? crew members could be randomly generated and their abilities could vary.
    public static final int BASE_MIN_HEALTH = 20; // TODO CONSIDER that mobs need a certain amount of health to move/act?
    public static final float BASE_RESILIENCE = 0; // percentage of damage ignored
    public static final float MAX_RESILIENCE = 0.9f; // max possible resilience
//    public static final boolean huep = 2; // TODO CONSIDER a "haste" flag.

    private boolean alive;
    private Tile previousTile, tile;
    protected Room room;
    private int tx, ty, fear;

    protected String name;

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

    public Mob(int tx, int ty){
        super(0, Display.TILE_WIDTH-1); // TODO change to non static values
        this.alive = true;
        this.tx = tx;
        this.ty = ty;

        this.tile = Game.map.tiles[tx][ty]; // TODO consider not even having a tile variable. need to know basis?
        this.previousTile = tile;
        this.room = tile.getRoom();
        this.fear = Values.Attributes.MENTAL_INDIFFERENT;
        if(this.tile.isVoid()){
            System.err.println("Mob spawned in a void tile");
        }

        generateBaseStats();
        generateSpecificStats();

        planner = null;

    }

    public Mob(int tx, int ty, int health){ // TODO flesh out this constructor
        super(0, Display.TILE_WIDTH - 1);
        this.tx = tx;
        this.ty = ty;
        if(health > MAX_HEALTH){
            this.health = 100;
        } else {
            this.health = health;
        }
    }

    private void generateBaseStats(){ // TODO make this more user based
        // TODO HASTE stat
        health         = MAX_HEALTH;
        minOpOxygen    = BASE_MIN_OXYGEN;
        minOpHealth    = BASE_MIN_HEALTH;
        resilience     = BASE_RESILIENCE;
    }
    protected abstract void generateSpecificStats();

    @Override
    public void render(Graphics screen, int viewOffsetX, int viewOffsetY) { // TODO optimise

//        if(frame != frames){
//            frame++;
//        }
//
//        int ax = 0;
//        int ay = 0;
//
//        if(tile.getX() > previousTile.getX()){ // if going right
//            ax = -(frames - frame);
//        } else if(tile.getX() < previousTile.getX()){ // if going left
//            ax = (frames - frame);
//        } else if(tile.getY() > previousTile.getY()){ // if going down
//            ay = -(frames - frame);
//        } else if(tile.getY() > previousTile.getY()){ // if going up
//            ay = (frames - frame);
//        }

        screen.setColor(colour); // TODO change to other method of colouring
        int margin = (Display.TILE_WIDTH - Display.MOB_WIDTH) / 2;

        Rectangle rect = new Rectangle((tx * Display.TILE_WIDTH) + margin + (viewOffsetX * Display.TILE_WIDTH), (ty * Display.TILE_WIDTH) + margin + viewOffsetY * Display.TILE_WIDTH, Display.MOB_WIDTH, Display.MOB_WIDTH);
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

    public void moveTo(int tx, int ty){

        Room newRoom = Game.map.tiles[tx][ty].getRoom();

        // if entering a new room
        if(this.room != newRoom){
            this.room = newRoom; // set new room
        }
        this.tx = tx;
        this.ty = ty;

        // setting new tile
        previousTile = this.tile;
        this.tile = Game.map.tiles[tx][ty];

        frame = 0;

    }

    public abstract String getGoalString();

    public abstract boolean isHostile();

    public abstract int getType();

    public abstract void evaluate(); // selects a new goal if necessary

    public abstract void act() throws NoAction;

    public abstract void populateDataBoxStrings(); // todo before commit.

    public int getX(){
        return tx;
    }

    public int getY(){
        return ty;
    }

    public Tile getTile(){
        return tile;
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

        Room room = getTile().getRoom();
        float roomOxygen = room.getOxygen();

        if(getType() == Mob.TYPE_PARASITE) {
//            System.out.println("Doing parasite damage. Room oxygen: " + roomOxygen + ", min oxygen: " + minOpOxygen);
        }

        if(room.getOxygen() < minOpOxygen){
//            System.out.println("taking oxygen dep dmg");
            health = health - damage; // TODO make it less black and white?
        }


             // TODO CONSIDER is -- mutator right for floats?

        // TODO mob should process damage done to itself via looking at the room its in, and what is in the room with it.
        // if there's a parasite there, it's likely to take damage from that.
        // if there's no oxygen, it's likely to take damage there also, so it can get real bad real quick.
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

    public void renderHoverBox(Graphics screen){ // TODO make more responsive

//        System.out.println("Render mob hover box called");

        screen.setColor(Colours.HOVER_SELECT);

        int margin = (Display.TILE_WIDTH - Display.MOB_WIDTH) / 2;

        Rectangle rect = new Rectangle((tx * Display.TILE_WIDTH) + margin - 4, (ty * Display.TILE_WIDTH) + margin - 4, Display.MOB_WIDTH + 8, Display.MOB_WIDTH + 8); // TODO replace these hard coded values

        screen.draw(rect);

    }

    @Override
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
