package main;

import com.sun.xml.internal.ws.util.StringUtils;
import guicomponents.dialogs.Dialog_CreateRoom;
import io.Inputtable;
import mobs.Mob;
import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import tiles.BorderTile;
import tiles.Tile;
import tiles.VisibleTile;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by stanners on 22/05/2015.
 */
public class Room implements Interactable, Inputtable { // TODO make abstract

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

    protected List<Point> points;
    private int x; // TODO remove?
    private int y;

    protected int type;
    protected int priority;
    protected float oxygen, integrity, ventRate, refillRate, consumptionRate;
    protected boolean purge, evacuate, support, selected, force; // support is life support, which is oxygen
    protected String typeString;
    protected String name;

    protected ArrayList<String> strings;
    private List<Point> disownedPoints; // TODO CONSIDER Does this even need to be a member variable?

    public Room(List<Point> points, boolean force){ // TODO , int type
        this.points = points;
        this.integrity = MAX_INTEGRITY;
        this.oxygen = MAX_OXYGEN;
        this.force = force;
        purge = false; // oxygen purge on/off
        evacuate = false; // evacuation alarm on/off
        support = true;
        selected = false;
        calculateRates();
    }

    public void init(){
        initialiseTiles();
    }

    private void initialiseTiles(){
        Color roomColour = new Color(GameController.random.nextInt(255), GameController.random.nextInt(255), GameController.random.nextInt(255)); // TODO change
        for (Iterator<Point> iterator = points.iterator(); iterator.hasNext(); ) {
            Point next = iterator.next();
            if(!GameController.mapController.getTile(next).isVoid() || force){
                GameController.mapController.putTile(next, new VisibleTile((int) next.getX(), (int) next.getY(), this, roomColour)); // TODO properly
            }
        }
    }

    private void calculateRates(){
        ventRate           = BASE_PURGE_RATE / points.size(); // TODO consider isn't this just tiles.length?
        refillRate         = BASE_REFILL_RATE / points.size();
        consumptionRate    = BASE_CONSUMPTION_RATE / points.size();
    }

//    public Room(int x, int y, int sx, int sy, int type){ // TODO room type
//
//        super(0, 0); // for now TODO reconsider
//

//
//        priority = DEFAULT_PRIORITY;
//

////        System.out.println("Consumption rate: " + consumptionRate);
//
//        this.type = type;
//
//        // SET ROOM TYPE STRING
//        if(Values.Types.CORRIDOR_X == type || Values.Types.CORRIDOR_Y == type){
//            typeString = Values.Strings.rooms[Values.Types.CORRIDOR];
//        } else {
//            typeString = Values.Strings.rooms[type];
//        }
//
//        try {
//            generateTiles(sx, sy);
//        } catch (CorridorDimensionsException e) { // TODO a bit drastic, but let's nip these errors in the bud early
//            System.err.println("Failed to generate a room. Exiting");
//            e.printStackTrace();
//            System.exit(0);
//        }
//    }

    public void setName(String name){
        this.name = name;
    }

    public void select(){ // TODO tidy this up
        this.selected = true;
    }

    public int size(){
        return points.size();
    }

    public void disownPoints(List<Point> points){
        System.out.println("disowning " + points.size() + " points");
        for (Iterator<Point> iterator = this.points.iterator(); iterator.hasNext(); ) {
            Point next =  iterator.next();
            if(next != null && points != null && points.contains(next)){ // TODO long term fix haha
                iterator.remove();
            }
        }
        calculateRates(); // re-calculate
    }

    public void updateFrame(){

    }

    public void updateTick(){

    }

//    public void render(Graphics screen) { // TODO
//
////        System.out.println("Room::render called");
//
//        Color pulseBgColor = null;
//        Color pulseBorderColor = null;
//
//        if(evacuate || selected){
//            int pulse = Game.pulse.getPulse();
//            pulseBgColor        = new Color(255, pulse, pulse);
//            pulseBorderColor    = new Color(255 - pulse, 255 - pulse, 255 - pulse);
//        }
//
//        for (Iterator<Point> iterator = tiles.iterator(); iterator.hasNext(); ) {
//            Point next = iterator.next();
//            VisibleTile tile = (VisibleTile) GameController.mapController.getTile(next);
//
//            if(evacuate){
//                tile.setBackgroundColour(pulseBgColor);
//            }
//
//            if(selected){
//                tile.setBorderColour(pulseBorderColor);
//            } else {
//                tile.resetBorderColour();
//            }
//
//            tile.render(screen);
//
//        }
//
//        selected = false;
//
//    }

    public void populateDataBoxStrings(){
        // generating strings for data box // TODO optimise so this only happens when necessary
        strings = new ArrayList<String>();

//        strings.add(StringUtils.capitalize(typeString));

        strings.add("Crew: " + GameController.mapController.getMobsInRoom(this).size()); // TODO
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

    public void update(){

        // count mobs in room
        int mobCount = GameController.mapController.getMobsInRoom(this).size();

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
//        int x = dbx;
//        int y = dby;

        x = 10;
        y = 10;

        // iterate through and present strings
        for (Iterator<String> iterator = strings.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            if(next == null){
                next = "nullstr";
            }
            screen.drawString(next, x, y); // TODO make this more efficient
            y = y + Display.TEXT_SPACING;
        }

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
            for (Iterator<Point> iterator = points.iterator(); iterator.hasNext(); ) {
                Point point = iterator.next();
                VisibleTile next = (VisibleTile) GameController.mapController.getTile(point);
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
        for (Iterator<Point> iterator = points.iterator(); iterator.hasNext(); ) {
            Point point =  iterator.next();
            if(GameController.mapController.getTile(point).mouseOver(mouseX, mouseY)){
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
        return GameController.mapController.getTile(points.get(GameController.random.nextInt(points.size())));
    }

    public List<Point> getPoints(){
        return points;
    }

//    private void generateTiles(int sx, int sy) throws CorridorDimensionsException {
//
//        tiles = new ArrayList<Point>();
//
//        if(isCorridor()){ // if its a corridor // TODO clean up
//            System.out.println("generating corridor tiles");
//            if(sx != 1 && sy != 1){ // If corridor dimensions do not match, throw exception
//                throw new CorridorDimensionsException(sx, sy);
//            }
//
//            if(sx == 1){ // if sizeX = 1, do a vertical corridor
//                for(int ly = 0; ly < sy; ly++){
//                    Tile tile = new VisibleTile(x, y + ly, this, type);
//                    Game.map.tiles[x][y + ly] = tile; // switch out from the array
//                    tiles.add(tile); // add to the list of tracked tiles for this room
////                    tiles.add(new Tile(this, x, y + ly*Display.TILE_WIDTH, Tile.TYPE_CORRIDOR_Y));
//                }
//            } else { // else do a horizontal corridor
//                for(int lx = 0; lx < sx; lx++){
//                    Tile tile = new VisibleTile(x + lx, y, this, type);
//                    Game.map.tiles[x + lx][y] = tile; // switch out from the array
//                    tiles.add(tile); // add to the list of tracked tiles for this room
////                    tiles.add(new Tile(this, x + lx*Display.TILE_WIDTH, y, Tile.TYPE_CORRIDOR_X));
//                }
//            }
//
//        } else { // if its a regular tile
//            for(int lx = 0; lx < sx; lx++){ // TODO tile generation should be dynamic and tiles should look different
//                for(int ly = 0; ly < sy; ly++){ // l is local
//
//                    Tile tile = new VisibleTile(x + lx, y + ly, this, type);
//                    Game.map.tiles[x + lx][y + ly] = tile; // switch out from the array
//                    tiles.add(tile); // add to the list of tracked tiles for this room
////                    tiles.add(new Tile(this, x + (lx*Display.TILE_WIDTH), y + (ly*Display.TILE_WIDTH), Tile.TYPE_SQUARE)); // TODO do the multiplication in the render method only
//
//                }
//            }
//
//        }
//
//    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEvacuate(){
        return evacuate;
    }

    public float getIntegrity(){
        return integrity;
    }

    @Override
    public void input(int i, char c) {
        switch (i) {
            case Input.KEY_Q:
                togglePurge();
                break;
            case Input.KEY_W:
                toggleEvacuate(); // the alarm
                break;
            case Input.KEY_E:
                decreaseRepairPriority();
                break;
            case Input.KEY_R:
                increaseRepairPriority();
                break;
            case Input.KEY_V:
                toggleSupport(); // TODO RECONSIDER naming here
                break;
            default:
                break;
        }
    }

    public List<Point> getDisownedPoints() {

        // initialise a to do list
        List<Point> todoPoints = new ArrayList<Point>();

        // make a list of the unexplored points
        List<Point> unexploredPoints = new ArrayList<Point>();
        unexploredPoints.addAll(points);

        // make sure there are actually points left in the list
        if(unexploredPoints.size() > 0){

            todoPoints.add(unexploredPoints.get(0));
            unexploredPoints.remove(0);

            // while there are points left to explore
            while(!todoPoints.isEmpty()){

                Point point = todoPoints.get(0);
                todoPoints.remove(0);

                // get values of currently analysing point
                int pX = (int) point.getX();
                int pY = (int) point.getY();

                // iterate through unexplored points getting
                for (Iterator<Point> iterator = unexploredPoints.iterator(); iterator.hasNext(); ) {
                    Point next = iterator.next();

                    int nX = (int) next.getX();
                    int nY = (int) next.getY();

                    // if next point is adjacent, remove it from the unexplored points and add it to the to do list
                    if( (Math.abs(pX - nX) == 1 && nY == pY ) || (Math.abs(pY - nY) == 1 && nX == pX) ){
                        if(!todoPoints.contains(next)){
                            todoPoints.add(next);
                            iterator.remove();
                        }
                    }

                }

            }

        }

        // remaining unexplored points will be the disowned points
        return unexploredPoints;

    }
}
