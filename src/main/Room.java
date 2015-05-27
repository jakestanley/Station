package main;

import exceptions.CorridorDimensionsException;
import mobs.Mob;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import tiles.BorderTile;
import tiles.Tile;
import tiles.TraversibleTile;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 22/05/2015.
 */
public class Room extends Loopable implements Interactable { // TODO make abstract

    public static final float MAX_OXYGEN = 100;
    public static final float MAX_INTEGRITY = 100; // TODO crew members have the job of repairing integrity
    public static final float BASE_PURGE_RATE = 25; // per tile. TODO RECONSIDER as arbitrary
    public static final float BASE_REFILL_RATE = 5; // per tile. TODO RECONSIDER as arbitrary
    public static final float BASE_CONSUMPTION_RATE = 1; // per tile per mob
    public static final float BASE_REPAIR_RATE = 1;// TODO repair action for crew

    public static final int LOWEST_PRIORITY = 0;
    public static final int DEFAULT_PRIORITY = 2;
    public static final int HIGHEST_PRIORITY = 4;

    public static final String[] priorities = {"MINIMUM", "LOW", "NORMAL", "HIGH", "URGENT"};

    private ArrayList<Tile> tiles;
    private int x;
    private int y;

    private int sx;
    private int sy;
    private int type;
    private int priority;
    private float oxygen, integrity, ventRate, refillRate, consumptionRate;
    private boolean purge, evacuate, support; // support is life support, which is oxygen

    public Room(int x, int y, int sx, int sy, int type){ // TODO room type

        super(0, 0); // for now TODO reconsider

        this.x = x;
        this.y = y;
        this.sx = sx; // TODO oxygen decay time should vary depending on room size
        this.sy = sy;

        this.integrity = MAX_INTEGRITY;
        this.oxygen = MAX_OXYGEN;

        purge = false; // oxygen purge on/off
        evacuate = false; // evacuation alarm on/off
        support = true;

        priority = DEFAULT_PRIORITY;

        ventRate           = BASE_PURGE_RATE / (sx * sy);
        refillRate         = BASE_REFILL_RATE / (sx * sy);
        consumptionRate    = BASE_CONSUMPTION_RATE / (sx * sy);
        System.out.println("Consumption rate: " + consumptionRate);

        this.type = type;

        try {
            generateTiles(sx, sy);
        } catch (CorridorDimensionsException e) { // TODO a bit drastic, but let's nip these errors in the bud early
            System.err.println("Failed to generate a room. Exiting");
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void render(Graphics screen){

        for (Iterator<Tile> iterator = tiles.iterator(); iterator.hasNext(); ) {
            Tile next =  iterator.next();
            next.render(screen);
        }
    }

    @Override
    public void update(){

        // count mobs in room
        int mobCount = Game.map.getMobsInRoom(this).size();

        // subtract mob oxygen consumption from oxygen level
        float consumptionRate = (mobCount * this.consumptionRate);
        oxygen = oxygen - consumptionRate;

        if(purge){
            // subtract purge rate from oxygen level if purging is on
            oxygen = oxygen - ventRate;
        }

        if(support){
            // TODO if life support module functional, refill. else, don't.
            // add to oxygen with refill rate
            oxygen = oxygen + refillRate;
        }

        // clean up if over or under the acceptable boundary
        if(oxygen > MAX_OXYGEN){
            oxygen = MAX_OXYGEN;
        } else if(oxygen < 0){
            oxygen = 0;
        }

        // TODO process integrity. how is the ship damaged?

    }

    @Override
    public void renderHoverBox(Graphics screen){ // TODO make this not shit

        int x = this.x * Display.TILE_WIDTH;
        int y = this.y * Display.TILE_WIDTH; // TODO make this more optimal

        screen.setColor(Color.white);
        screen.drawRect(x - Display.MARGIN, y - Display.MARGIN, (sx * Display.TILE_WIDTH) + 16, (sy * Display.TILE_WIDTH) + 16); // TODO make the hover box animated. fix the box. make these not hard coded
    }

    @Override
    public void renderDataBox(Graphics screen){ // TODO draw a hover box and then some stuff

        // neater separation of concerns with text generation and presentation iteration loop

        // initialising variables
        ArrayList<String> strings = new ArrayList<String>();
        int x = Display.MAP_WIDTH - 200; // TODO change hard coded value
        int y = 60; // TODO don't use a hard coded value

        // get strings
        strings.add("Crew: " + Game.map.getMobsInRoomByType(this, Mob.TYPE_MATE).size());
        strings.add("Hostiles: " + Game.map.getMobsInRoomByType(this, Mob.TYPE_PARASITE).size()); // TODO CONSIDER renaming parasites to hostiles
        strings.add("Oxygen: " + oxygen + "%");
        strings.add("Integrity: " + integrity + "%");

        if(purge){
            strings.add("Purge: ON");
        } else {
            strings.add("Purge: OFF");
        }

        if(evacuate){
            strings.add("Alarm: ON");
        } else {
            strings.add("Alarm: OFF");
        }

        strings.add("Repair: " + priorities[priority]);

        if(support){
            strings.add("Oxygen: ON");
        } else {
            strings.add("Oxygen: OFF");
        }

        // set color
        screen.setColor(Color.white);

        // iterate through and present strings
        for (Iterator<String> iterator = strings.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            screen.drawString(next, x, y); // TODO make this more efficient
            y = y + Display.TEXT_SPACING;
        }

    }

    @Override
    public void qPress() { // ON Q PRESS, TOGGLE PURGING
        togglePurge();
    }

    @Override
    public void wPress() {
        toggleEvacuate(); // the alarm
    }

    @Override
    public void ePress() {
        decreaseRepairPriority();
    }

    @Override
    public void rPress() {
        increaseRepairPriority();
    }

    @Override
    public void vPress() {
        toggleSupport(); // TODO RECONSIDER naming here

    }

    private void togglePurge(){
        purge = !purge;
    }

    private void toggleSupport(){
        support = !support;
    }

    private void toggleEvacuate(){
        evacuate = !evacuate; // TODO print instructions on the bottom
    }

    private void decreaseRepairPriority(){
        if(priority != LOWEST_PRIORITY){
            priority--;
        }
    }

    private void increaseRepairPriority(){
        if(priority != HIGHEST_PRIORITY){
            priority++;
        }
    }

    public boolean mouseOver(int mouseX, int mouseY){
        boolean over = false;
        for (Iterator<Tile> iterator = tiles.iterator(); iterator.hasNext(); ) {
            Tile next =  iterator.next();
            if(next.mouseOver(mouseX, mouseY)){
                return true;
            }
        }
        return over;
    }

    public float getOxygen(){
        return oxygen;
    }

    public int getType(){
        return type;
    }

    /**
     * Gets a random tile from this room. TODO remove after testing or make into something more useful
     * @return
     */
    public Tile getRandomTile(){ // TODO fix after testing
        return tiles.get(Game.random.nextInt(tiles.size()));
    }

    public ArrayList<Tile> getRoomTiles(){
        return tiles;
    }

    private void generateTiles(int sx, int sy) throws CorridorDimensionsException {

        tiles = new ArrayList<Tile>();

        if(type == Values.Types.CORRIDOR_X || type == Values.Types.CORRIDOR_Y){ // if its a corridor // TODO clean up
            if(sx != 1 && sy != 1){ // If corridor dimensions do not match, throw exception
                throw new CorridorDimensionsException(sx, sy);
            }

            if(sx == 1){ // if sizeX = 1, do a vertical corridor
                for(int ly = 0; ly < sy; ly++){
                    Tile tile = new TraversibleTile(x, y + ly, this, type);
                    Game.map.tiles[x][y + ly] = tile; // switch out from the array
                    tiles.add(tile); // add to the list of tracked tiles for this room
//                    tiles.add(new Tile(this, x, y + ly*Display.TILE_WIDTH, Tile.TYPE_CORRIDOR_Y));
                }
            } else { // else do a horizontal corridor
                for(int lx = 0; lx < sx; lx++){
                    Tile tile = new TraversibleTile(x + lx, y, this, type);
                    Game.map.tiles[x + lx][y] = tile; // switch out from the array
                    tiles.add(tile); // add to the list of tracked tiles for this room
//                    tiles.add(new Tile(this, x + lx*Display.TILE_WIDTH, y, Tile.TYPE_CORRIDOR_X));
                }
            }

        } else { // if its a regular tile
            for(int lx = 0; lx < sx; lx++){ // TODO tile generation should be dynamic and tiles should look different
                for(int ly = 0; ly < sy; ly++){ // l is local

                    Tile tile = new TraversibleTile(x + lx, y + ly, this, type);
                    Game.map.tiles[x + lx][y + ly] = tile; // switch out from the array
                    tiles.add(tile); // add to the list of tracked tiles for this room
//                    tiles.add(new Tile(this, x + (lx*Display.TILE_WIDTH), y + (ly*Display.TILE_WIDTH), Tile.TYPE_SQUARE)); // TODO do the multiplication in the render method only

                }
            }

        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSx() {
        return sx;
    }

    public int getSy() {
        return sy;
    }


    public float getIntegrity(){
        return integrity;
    }

    public ArrayList<Tile> getBorderPoints(){

        ArrayList<Tile> corridorTiles = new ArrayList<Tile>();

        int maxX = x + sx;
        int maxY = y + sy;

        int mapHeight = Game.map.getHeight();
        int mapWidth = Game.map.getWidth();

        System.out.println("max y: " + maxY);

        // get top and bottom rows of tiles
        Tile tile;
        for(int i = x; i < maxX; i++){
            tile = null;
            if(y > 0){
                tile = Game.map.tiles[i][y-1];
                if(tile.isVoid()){
                    tile = new BorderTile(i, y-1, this);
                    Game.map.tiles[i][y-1] = tile;
                    corridorTiles.add(tile);
                }
            }
            tile = null;
            if(maxY < mapHeight){ // TODO
                tile = Game.map.tiles[i][maxY];
                if(tile.isVoid()){
                    tile = new BorderTile(i, maxY, this);
                    Game.map.tiles[i][maxY] = tile;
                    corridorTiles.add(tile);
                }
            }
        }

        // get left and right rows of tiles
        for(int i = y; i < maxY; i++){
            tile = null;
            if(x > 0){
                tile = Game.map.tiles[x-1][i];
                if(tile.isVoid()){
                    tile = new BorderTile(x-1, i, this);
                    Game.map.tiles[x-1][i] = tile;
                    corridorTiles.add(tile);
                }
            }
            tile = null;
            if(maxX < mapWidth){
                tile = Game.map.tiles[maxX][i];
                if(tile.isVoid()){
                    tile = new BorderTile(maxX, i, this);
                    Game.map.tiles[maxX][i] = tile;
                    corridorTiles.add(tile);
                }
            }
        }

        return corridorTiles;

    }

}
