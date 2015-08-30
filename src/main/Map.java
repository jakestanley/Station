package main;

import exceptions.LongCorridorGeneration;
import exceptions.NoAction;
import exceptions.NoSpawnableArea;
import mobs.Mob;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import tiles.Tile;
import tiles.VisibleTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 22/05/2015.
 */
public class Map extends Loopable { // TODO abstract functionality out of map. this is such a mess of a class

    public static Tile[][] tiles; // TODO need to move tiles into here so I can manage getting to tiles with the AI. should this be static?

    public ArrayList<Tile> visibleTiles;

    private int width, height;
    private int timeout = Values.SEARCH_TIME_LIMIT;
    private ArrayList<Room> rooms;
    private ArrayList<Mob>  mobs;
    private ArrayList<Door> doors;
    private ArrayList<Tile> corridorPointTiles;

    public Map(int width, int height, boolean[] tileBools){
        super(0, 0); // frame doesn't really apply to map, but i guess it could do? TODO consider animated superclass?

//        System.out.println("Initialising map");
//
//        // initialise tile array
//        this.width = width;
//        this.height = height;
//
//        System.out.println("Map size is " + width + "x" + height);
//        tiles = new Tile[width][height];
//
//        int tileIndex = 0;
//
//        // initialise arraylist
//        visibleTiles = new ArrayList<Tile>();
//
//        // initialising all tiles as void tiles
//        for(int x = 0; x < tiles.length; x++){
//            for(int y = 0; y < tiles[x].length; y++){
//
//                if(tileBools[tileIndex]){ // TODO optimise
//                    VisibleTile t = new VisibleTile(x, y, null, 0);
//                    visibleTiles.add(t);
//                } else {
//                    Tile t = new Tile(x, y);
//                    tiles[x][y] = t; // void tile constructor
//                }
//                tileIndex++;
//            }
//        }
//
////        Corridor globalRoom = new Corridor(visibleTiles);
//
//        // initialise component lists
//        rooms = new ArrayList<Room>();
//        mobs = new ArrayList<Mob>();
//        doors = new ArrayList<Door>();
//        corridorPointTiles = new ArrayList<Tile>();
//
//        rooms.add(globalRoom);

    }

    @Override
    public void init(){
        // generate components
        try {
//            generateRooms();
//            generateCorridors();
            initialiseTiles();
//            generateDoors();
            generateMobs();
        } catch(NoSpawnableArea e) {
            System.err.println("Can't spawn mobs as there are no non void tiles. Exiting");
            System.exit(Game.EXIT_BAD);
//        } catch (LongCorridorGeneration longCorridorGeneration) {
//            System.err.println("Couldn't generate corridors. Took too long!");
        }
    }

    @Override
    public void render(Graphics screen) { // TODO make this not ugly as shit. my code is starting to lok awulffe
//
//        for (Iterator<Tile> iterator = visibleTiles.iterator(); iterator.hasNext(); ) {
//            Tile next =  iterator.next();
//
//        }
//
//        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
//            Room next =  iterator.next();
////            next.render(screen);
//        }
//
//        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
//            Mob next = iterator.next();
//            next.render(screen);
//        }
//
//        for (Iterator<Door> iterator = doors.iterator(); iterator.hasNext(); ) {
//            Door next =  iterator.next();
//            next.render(screen);
//        }

//        for (Iterator<Tile> iterator = corridorPointTiles.iterator(); iterator.hasNext(); ) { // TODO consider removing
//            Tile next =  iterator.next();
//            next.render(screen);
//        }

    }

    public void initialiseTiles(){

    }

    @Override
    public void update(){
        // TODO

        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room next =  iterator.next();
            next.update();
        }

        for (Iterator<Door> iterator = doors.iterator(); iterator.hasNext(); ) {
            Door next =  iterator.next();
            next.update();
        }

        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob next = iterator.next();
            next.update();
        }

    }

    public boolean hasEvacuatableRoom(){
        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room next = iterator.next();
            if(!next.isEvacuate()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Room> getRooms(){
        return rooms;
    }

    public Room getRoomMouseOver(int mouseX, int mouseY){ // TODO display mobs in room when hovering over
        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room next = iterator.next();
            if(next.mouseOver(new Point())){ // TODO remove. this code will break if run
                return next;
            }
        }
        return null;
    }

    public Door getDoorMouseOver(int mouseX, int mouseY){
        for (Iterator<Door> iterator = doors.iterator(); iterator.hasNext(); ) {
            Door next =  iterator.next();
            if(next.mouseOver(new Point())){ // TODO remove. this code will break if run
                return next;
            }
        }
        return null;
    }

//    private void generateRooms(){
//
//        // initialise local variables
//        int x = -1, y = 0, sx = 0, sy = 0;
//
//        // place the bridge. don't really need the checks first, but doing them for niceness. TODO CONSIDER remove later?
//        Room bridge = null;
//        while(roomClash(x, y, sx, sy)){
//            sx = Values.Dimensions.BRIDGE;
//            sy = sx;
//            x = Game.random.nextInt(width - sx);
//            y = Game.random.nextInt(height - sy);
//        }
//        bridge = new Room(x, y, sx, sy, Values.Types.BRIDGE);
//        rooms.add(bridge);
//
//        x = -1;
//
//        // place the life support
//        Room lifeSupport = null;
//        while(roomClash(x, y, sx, sy)) {
//            if (Game.random.nextBoolean()) { // make life support vertical
//                sx = Values.Dimensions.LIFE_SUPPORT_Y_X;
//                sy = Values.Dimensions.LIFE_SUPPORT_Y_Y;
//            } else { // make life support horizontal
//                sx = Values.Dimensions.LIFE_SUPPORT_X_X;
//                sy = Values.Dimensions.LIFE_SUPPORT_X_Y;
//            }
//            x = Game.random.nextInt(width - sx);
//            y = Game.random.nextInt(height - sy);
//        }
//        lifeSupport = new Room(x, y, sx, sy, Values.Types.LIFESUPPORT);
//        rooms.add(lifeSupport);
//
//        x = -1;
//
//        // place the hangar
//        Room hangar = null;
//        while(roomClash(x, y, sx, sy)){
//            if(Game.random.nextBoolean()){ // make hangar vertical
//                sx = Values.Dimensions.HANGAR_Y_X;
//                sy = Values.Dimensions.HANGAR_Y_Y;
//            } else { // make life support horizontal
//                sx = Values.Dimensions.HANGAR_X_X;
//                sy = Values.Dimensions.HANGAR_X_Y;
//            }
//            x = Game.random.nextInt(width - sx);
//            y = Game.random.nextInt(height - sy);
//        }
//        hangar = new Room(x, y, sx, sy, Values.Types.HANGAR);
//        rooms.add(hangar);
//
//        Room invigorator = null; // TODO remove these, they're useless
//        while(roomClash(x, y, sx, sy)){
//            sx = Values.Dimensions.INVIGORATOR;
//            sy = sx;
//            x = Game.random.nextInt(width - sx);
//            y = Game.random.nextInt(height - sy);
//        }
//        invigorator = new Room(x, y, sx, sy, Values.Types.BRIDGE);
//        rooms.add(invigorator);
//
//        Room invigorator2 = null; // TODO remove these, they're useless
//        while(roomClash(x, y, sx, sy)){
//            sx = Values.Dimensions.INVIGORATOR;
//            sy = sx;
//            x = Game.random.nextInt(width - sx);
//            y = Game.random.nextInt(height - sy);
//        }
//        invigorator2 = new Room(x, y, sx, sy, Values.Types.BRIDGE);
//        rooms.add(invigorator2);
//
//        // TODO add more rooms and particularly corridors. they might be tricky
//        // TODO more dynamic amounts of rooms, etc
//
//    }
//
//    private void generateDoors(){
//
//        for(int w = 0; w < width; w++){
//            for(int h = 0; h < height; h++){
//                Tile currentTile = tiles[w][h];
//                if(!currentTile.isVoid()){
//                    // TODO don't add doors twice - if we're not checking north and west, or east and south, we'll automatically exclude duplicates
//                    if(h > 0){ // if not on north bound
//                        Tile northTile = tiles[w][h-1];
////                        if(northTile.isTraversable() && northTile.getType() != Values.Types.CORRIDOR_X && currentTile.getRoom() != northTile.getRoom()){
//                        if(!northTile.isV() && currentTile.getRoom() != northTile.getRoom() && !(currentTile.getRoom().isCorridor() && northTile.getRoom().isCorridor())){
//                            doors.add(new Door(currentTile, northTile));
//                        }
//                    }
//
//                    if(w > 0){ // if not on west bound
//                        Tile westTile = tiles[w-1][h];
////                        if(westTile.isTraversable() && westTile.getType() != Values.Types.CORRIDOR_Y && currentTile.getRoom() != westTile.getRoom()){
//                        if(westTile.isTraversable() && currentTile.getRoom() != westTile.getRoom() && !(currentTile.getRoom().isCorridor() && westTile.getRoom().isCorridor())){
//                            doors.add(new Door(currentTile, westTile));
//                        }
//                    }
//
//                }
//
//            }
//        }
//
//        System.out.println("Detected " + doors.size() + " doors");
//
//    }

    private void generateMobs() throws NoSpawnableArea {

        int crewCount = 7;
        int hostileCount = 1;

        // generate crew
//        for(int i = 0; i < crewCount; i++){
//            Tile randomTile = getRandomTraversibleTile();
//            if(randomTile == null){
//                throw new NoSpawnableArea();
//            }
//            mobs.add(new Mate(randomTile.getX(), randomTile.getY()));
//        }
//
//        // generate hostiles
//        for(int i = 0; i < hostileCount; i++){
//            Tile randomTile = getRandomTraversibleTile();
//            if(randomTile == null){
//                throw new NoSpawnableArea();
//            }
//            mobs.add(new Parasite(randomTile.getX(), randomTile.getY()));
//        }

    }

    private boolean hasTraversablePath(Tile start, Room end){
//        if(getTraversiblePath(start, end).isEmpty()){
//            return false;
//        } else {
//            return true;
//        }
        return false;
    }

    public void moveMobs(){ // TODO mobs should decide themselves where to move based on some algorithm
        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob mob =  iterator.next();
            if(mob.alive()){ // TODO remove this and just have it in Mob::update if possible
                try {
                    mob.evaluate();
                    mob.act();
                } catch (NoAction noAction) {
                    System.err.println(mob.getName() + " can't find anywhere to go");
                }
            }
        }

    }

//    public void testDoors(){ // TODO remove
//        for (Iterator<Door> iterator = doors.iterator(); iterator.hasNext(); ) {
//            Door next =  iterator.next();
//            next.toggle();
//        }
//    }

    public ArrayList<Mob> getMobs(){
        return mobs;
    }

    public ArrayList<Mob> getMobsInRoom(Room room){
        ArrayList<Mob> roomMobs = new ArrayList<Mob>();
        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob mob = iterator.next();
//            if(mob.getTile().getRoom() == room){
//                roomMobs.add(mob);
//            }
        }
        return roomMobs;
    }

    public ArrayList<Mob> getMobsInRoomByType(Room room, int type){
//        ArrayList<Mob> mobs = new ArrayList<Mob>();
//        for (Iterator<Mob> iterator = getMobsInRoom(room).iterator(); iterator.hasNext(); ) {
//            Mob next =  iterator.next();
//            if(next.getType() == type && next.getTile().getRoom() == room){
//                mobs.add(next);
//            }
//        }
        return null;
    }

    public Room getBridge(){ // TODO ensure only one bridge per station/ship
        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room next =  iterator.next();
            if(next.getType() == Values.Types.BRIDGE){
                return next;// TODO consider renaming the values.tileroom subclass to just "types"
            }
        }
        return null; // TODO ensure this is checked before accessing. also ensure checks for room accessibility. a generated station with an inaccessible bridge is not a game.
    }

    public void damageMobs(){
        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob next = iterator.next();
            if(next.alive()){
                next.specificDamage();
            }
        }
    }

//    public Door getDoorByTiles(Tile tile1, Tile tile2){
//        for (Iterator<Door> iterator = getDoors().iterator(); iterator.hasNext(); ) {
//            Door door =  iterator.next();
//            if( (door.getStartPoint() == tile1 && door.getEndPoint() == tile2) ||
//                    (door.getStartPoint() == tile2 && door.getEndPoint() == tile1)){
////                System.out.println("Door match");
//                return door;
//            }
//        }
//        return null;
//    }

    public ArrayList<Door> getDoors(){
        return doors;
    }

    public boolean won(){ // TODO this class name is now really inappropriate and i need a good shuffle round...
        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob next =  iterator.next();
            if(next.getType() == Mob.TYPE_PARASITE && next.alive()){
                return false;
            }
        }
        return true;
    }

    public void printScore(){

        int crewTotal = 0;
        int crewSurvived = 0;
        int roomTotal = rooms.size();
        int roomSurvived = 0;

        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob next = iterator.next();
            if(next.getType() == Mob.TYPE_MATE){
                crewTotal++;
                if(next.alive()){
                    crewSurvived++;
                }
            }
        }

        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room next =  iterator.next();
            if(next.getIntegrity() > 0){
                roomSurvived++;
            }
        }

        Double score = new Double(crewSurvived + roomSurvived) / (crewTotal + roomTotal) * 100;


        System.out.println("Crew survived: " + crewSurvived + " out of " + crewTotal);
        System.out.println("Station integrity: " + roomSurvived + " out of " + roomTotal);
        System.out.println("Score: " + score.intValue() + "%");

    }

    private Tile getRandomTraversibleTile() {
        ArrayList<Tile> nonVoidTiles = new ArrayList<Tile>();
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[x].length; y++){
                Tile t = tiles[x][y];
                if(!t.isVoid()){
                    nonVoidTiles.add(t);
                }
            }
        }
        int size = nonVoidTiles.size();
        if(size < 1){
            return null;
        }
        return nonVoidTiles.get(Game.random.nextInt(nonVoidTiles.size()));
    }

    private void moveMobsRandomly(){
        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob mob = iterator.next();

            if(mob.alive()){
                // TODO remember cannot move out of bounds or onto void tiles (more coming soon)
                // only horizontal and vertical moves are allowed

                Point point = mob.getPoint(); // TODO convert to using point
                int mobX = (int) point.getX();
                int mobY = (int) point.getY();

                ArrayList<Point> moves = new ArrayList<Point>(9);
                moves.add(new Point(mobX - 1, mobY));
                moves.add(new Point(mobX, mobY - 1));
                moves.add(new Point(mobX, mobY));
                moves.add(new Point(mobX, mobY + 1));
                moves.add(new Point(mobX + 1, mobY));

                ArrayList<Point> valid = new ArrayList<Point>();
                for (Iterator<Point> pointIterator = moves.iterator(); pointIterator.hasNext(); ) {
                    Point next =  pointIterator.next();
                    int moveX = (int) next.getX();
                    int moveY = (int) next.getY(); // TODO replace this quick and dirty business
                    if((moveX >= 0) && (moveY >= 0) && (!Game.map.tiles[moveX][moveY].isVoid())){ // TODO also check that the border tiles aren't too big, as there are upper limits too
                        valid.add(next);
                    }
                }

                // Clarify legal options
                int count = valid.size();
                if(count > 0){
                    Point p = valid.get(GameController.random.nextInt(valid.size()));
                    mob.moveTo(p);
                } else {
                    System.err.println("Cannot move mob as there are no valid moves.");
                }
            }

        }



    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
