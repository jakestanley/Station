package mobs;

import exceptions.NoAction;
import main.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import planner.Planner;
import tiles.Tile;

/**
 * Created by stanners on 23/05/2015.
 */
public abstract class Mob extends Loopable { // TODO make abstract as its not to be instantiated directly

    public static final int TYPE_MATE = 1;
    public static final int TYPE_PARASITE = 2;

    public static final int MAX_HEALTH = 100; // TODO an increased health/resistance stat?
    public static final int BASE_MIN_OXYGEN = 20; // TODO make this different for different crew members? crew members could be randomly generated and their abilities could vary.
    public static final int BASE_MIN_HEALTH = 20; // TODO CONSIDER that mobs need a certain amount of health to move/act?
    public static final float BASE_RESILIENCE = 0; // percentage of damage ignored
    public static final float MAX_RESILIENCE = 0.9f; // max possible resilience
//    public static final boolean huep = 2; // TODO CONSIDER a "haste" flag.

    private boolean alive, renderHoverBox = false;
    private Tile tile;
    private Room room;
    private int tx, ty;

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

    // problem solving and path finding
    protected Planner planner;

    public Mob(int tx, int ty){
        super(0, 4); // TODO change to non static values
        this.alive = true;
        this.tx = tx;
        this.ty = ty;

        this.tile = Game.map.tiles[tx][ty]; // TODO consider not even having a tile variable. need to know basis?
        this.room = tile.getRoom();
        if(this.tile.isVoid()){
            System.err.println("Mob spawned in a void tile");
        }

        generateBaseStats();
        generateSpecificStats();

        planner = null;

    }

    public Mob(int tx, int ty, int health){ // TODO flesh out this constructor
        super(0, 4);
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
    public void render(Graphics screen){ // TODO optimise
        screen.setColor(colour);
        int margin = (Display.TILE_WIDTH - Display.MOB_WIDTH) / 2;

        // TODO make this more responsive/dynamic based on scale
        Rectangle rect = new Rectangle((tx * Display.TILE_WIDTH) + margin, (ty * Display.TILE_WIDTH) + margin, Display.MOB_WIDTH, Display.MOB_WIDTH);
        ShapeFill fill = new GradientFill((tx * Display.TILE_WIDTH) + margin + 1, (ty * Display.TILE_WIDTH) + margin + 1, colour, Display.MOB_WIDTH - 1, Display.MOB_WIDTH - 1, colour);
        screen.fill(rect, fill);

        if(renderHoverBox){
            renderHoverBox(screen);
        }

    }

    @Override
    public void update() {
        baseDamage();
        specificDamage();
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
        this.tile = Game.map.tiles[tx][ty];

    }

    public abstract boolean isHostile();

    public abstract int getType();

    public abstract void act() throws NoAction;

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
        if(health < 0){
            health = 0;
            die();
        }
        // TODO mob should process damage done to itself via looking at the room its in, and what is in the room with it.
        // if there's a parasite there, it's likely to take damage from that.
        // if there's no oxygen, it's likely to take damage there also, so it can get real bad real quick.
    }

    public abstract void specificDamage();

    public String getName(){
        return name;
    }

    public void setSelected(){
        renderHoverBox = true;
    }

    private void renderHoverBox(Graphics screen){ // TODO make more responsive

//        System.out.println("Render mob hover box called");

        screen.setColor(Colours.HOVER_SELECT);

        int margin = (Display.TILE_WIDTH - Display.MOB_WIDTH) / 2;

        // TODO make this more responsive/dynamic based on scale
        Rectangle rect = new Rectangle((tx * Display.TILE_WIDTH) + margin - 1, (ty * Display.TILE_WIDTH) + margin - 1, Display.MOB_WIDTH + 2, Display.MOB_WIDTH + 2);

        screen.draw(rect);
        renderHoverBox = false; // reset after
    }

    private void die(){
        System.out.println(name + " died");
        alive = false;
        colour = Color.lightGray;
    }

}
