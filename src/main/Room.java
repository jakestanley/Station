package main;

import com.sun.xml.internal.ws.util.StringUtils;
import exceptions.CorridorDimensionsException;
import mobs.Mob;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import tiles.BorderTile;
import tiles.Tile;
import tiles.VisibleTile;

import java.awt.*;
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

    protected ArrayList<Tile> tiles;
    protected ArrayList<Point> points;

    protected int type;
    protected int priority;
    protected float oxygen, integrity, ventRate, refillRate, consumptionRate;
    protected boolean purge, evacuate, support, selected; // support is life support, which is oxygen
    protected String typeString;

    protected ArrayList<String> strings;

    public Room(ArrayList<Point> points, boolean forceVoid){ // TODO , int type
        super(0, 0); // TODO remove the requirement for this
        this.points = points;
        this.integrity = MAX_INTEGRITY;
        this.oxygen = MAX_OXYGEN;

        generateTiles(forceVoid);
    }

    @Override
    public void init() {

    }

    public void select(){ // TODO tidy this up
        this.selected = true;
    }

    public void updateFrame(){

    }

    public void updateTick(){

    }

    public void render(Graphics screen) {

//        System.out.println("Room::render called");

        Color pulseBgColor = null;
        Color pulseBorderColor = null;

        if(evacuate || selected){
            int pulse = Game.pulse.getPulse();
            pulseBgColor        = new Color(255, pulse, pulse);
            pulseBorderColor    = new Color(255 - pulse, 255 - pulse, 255 - pulse);
        }

        for (Iterator<Tile> iterator = tiles.iterator(); iterator.hasNext(); ) {
            VisibleTile next = (VisibleTile) iterator.next();

            if(evacuate){
                next.setBackgroundColour(pulseBgColor);
            }

            if(selected){
                next.setBorderColour(pulseBorderColor);
            } else {
                next.resetBorderColour();
            }

            next.render(screen);

        }

        selected = false;

    }

    public void populateDataBoxStrings(){
        // generating strings for data box // TODO optimise so this only happens when necessary
        strings = new ArrayList<String>();

        strings.add(StringUtils.capitalize(typeString));

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
    }

    public boolean hasPoint(Point point){
        for (Iterator<Point> iterator = points.iterator(); iterator.hasNext(); ) {
            Point next = iterator.next();
            if(next.equals(point)){
                return true;
            }
        }
        return false;
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

    }

    @Override
    public void renderHoverBox(Graphics screen){ // TODO remove?

//        int x = this.x * Display.TILE_WIDTH;
//        int y = this.y * Display.TILE_WIDTH; // TODO make this more optimal
//
        screen.setColor(Color.white);
//        screen.drawRect(x - Display.MARGIN, y - Display.MARGIN, (sx * Display.TILE_WIDTH) + 16, (sy * Display.TILE_WIDTH) + 16); // TODO make the hover box animated. fix the box. make these not hard coded

    }

    public void renderDataBox(Graphics screen){ // TODO draw a hover box and then some stuff
        System.out.println("renderDataBox called");
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
        if(!evacuate){ // TODO CONSIDER is this not an inefficient and expensive operation?
            for (Iterator<Tile> iterator = tiles.iterator(); iterator.hasNext(); ) {
                VisibleTile next = (VisibleTile) iterator.next();
                next.resetBackgroundColour();
            }
        }
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

    public boolean mouseOver(Point mousePoint){

        int mouseX = (int) mousePoint.getX();
        int mouseY = (int) mousePoint.getY();

        boolean over = false;
        for (Iterator<Tile> iterator = tiles.iterator(); iterator.hasNext(); ) {
            Tile next =  iterator.next();
            if(next.mouseOver(mouseX, mouseY)){
                over = true; // TODO tidy and clean this confusing crap up
                selected = true;
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

    public String getTypeString(){
        return typeString;
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

    private void generateTiles(boolean forceVoid){
        tiles = new ArrayList<Tile>();

        for (Iterator<Point> iterator = points.iterator(); iterator.hasNext(); ) {
            Point next =  iterator.next();

            int x = (int) next.getX();
            int y = (int) next.getY();

            // if next tile is not void, or force void is on, add a visible tile
            if(!GameController.mapController.getTile(next).isVoid() || forceVoid){
                VisibleTile tile = new VisibleTile(x, y, this, type);
                GameController.mapController.putTile(next, tile); // switch out from the array // TODO clean up
                tiles.add(tile); // add to the list of tracked tiles for this room
            }

        }

        for (Iterator<Tile> iterator = tiles.iterator(); iterator.hasNext(); ) {
            VisibleTile next = (VisibleTile) iterator.next();
            next.updateWalls();
        }

        System.out.println("Done generating tiles");

    }

    public int getSize() {
        return tiles.size();
    }

    public boolean isEvacuate(){
        return evacuate;
    }

//    public ArrayList<int[]> getEdgeTileCoordinates(){ // TODO rewrite and test
//        ArrayList<int[]> coordinates = new ArrayList<int[]>();
//
//        int maxX = point;
//        int maxY = y + sy;
//
//        // get the top and bottom edges
//        for(int i = x; i < maxX; i++){
//
//            int[] top = new int[2];
//            top[0] = i;
//            top[1] = y;
//
//            int[] bottom = new int[2];
//            bottom[0] = i;
//            bottom[1] = maxY;
//
//            // add both to edge
//            coordinates.add(top);
//            coordinates.add(bottom);
//
//        }
//
//        // get the left and right edges
//        for(int i = y + 1; i < maxY - 1; i++){
//
//            int[] left = new int[2];
//            left[0] = x;
//            left[1] = i;
//
//            int[] right = new int[2];
//            right[0] = maxX;
//            right[1] = i;
//
//            coordinates.add(left);
//            coordinates.add(right);
//
//        }
//
//        return coordinates;
//
//    }

    public float getIntegrity(){
        return integrity;
    }

//    public ArrayList<Tile> getBorderPoints(){ // TODO rewrite and test
//
//        ArrayList<Tile> corridorTiles = new ArrayList<Tile>();
//
//        int maxX = x + sx;
//        int maxY = y + sy;
//
//        int mapHeight = Game.map.getHeight();
//        int mapWidth = Game.map.getWidth();
//
//        System.out.println("max y: " + maxY);
//
//        // get top and bottom rows of tiles
//        Tile tile;
//        for(int i = x; i < maxX; i++){
//            tile = null;
//            if(y > 0){
//                tile = Game.map.tiles[i][y-1];
//                if(tile.isVoid()){
//                    tile = new BorderTile(i, y-1, this);
//                    Game.map.tiles[i][y-1] = tile;
//                    corridorTiles.add(tile);
//                }
//            }
//            tile = null;
//            if(maxY < mapHeight){ // TODO
//                tile = Game.map.tiles[i][maxY];
//                if(tile.isVoid()){
//                    tile = new BorderTile(i, maxY, this);
//                    Game.map.tiles[i][maxY] = tile;
//                    corridorTiles.add(tile);
//                }
//            }
//        }
//
//        // get left and right rows of tiles
//        for(int i = y; i < maxY; i++){
//            tile = null;
//            if(x > 0){
//                tile = Game.map.tiles[x-1][i];
//                if(tile.isVoid()){
//                    tile = new BorderTile(x-1, i, this);
//                    Game.map.tiles[x-1][i] = tile;
//                    corridorTiles.add(tile);
//                }
//            }
//            tile = null;
//            if(maxX < mapWidth){
//                tile = Game.map.tiles[maxX][i];
//                if(tile.isVoid()){
//                    tile = new BorderTile(maxX, i, this);
//                    Game.map.tiles[maxX][i] = tile;
//                    corridorTiles.add(tile);
//                }
//            }
//        }
//
//        return corridorTiles;
//
//    }

}
