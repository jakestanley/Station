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
            System.exit(0);
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
//    private void buildStockMap(){
//        rooms.add(new Room(4, 4, 3, 3,  Values.Types.GENERIC)); // TODO appropriate values that are multiples of tile width
//        rooms.add(new Room(7, 5, 3, 1,  Values.Types.CORRIDOR_X)); // TODO remove CORRIDOR_X and CORRIDOR_Y and replace with just CORRIDOR
//        rooms.add(new Room(10, 5, 1, 1, Values.Types.GENERIC));
//        rooms.add(new Room(10, 6, 1, 3, Values.Types.CORRIDOR_Y));
//        rooms.add(new Room(9, 9, 4, 4,  Values.Types.BRIDGE));
//    }
//
//    private void buildStaticShip(){
//        rooms.add(new Room(4, 1, 3, 3, Values.Types.BRIDGE)); // bridge
//        rooms.add(new Room(5, 4, 1, 1, Values.Types.GENERIC)); // corridor
//    }

    private boolean roomClash(int x, int y, int sx, int sy){
        if(x < 0){
            return true;
        }

        Rectangle room1 = new Rectangle(x, y, sx, sy);

        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room existing = iterator.next();

            Rectangle room2 = new Rectangle(existing.getX(), existing.getY(), existing.getSx(), existing.getSy());

            if(room1.intersects(room2) || room2.intersects(room1)){ // TODO test
                return true;
            }

            // TODO test for intersections and return true where possible
        }

        return false;
    }

    private void generateCorridors() throws LongCorridorGeneration {

        long startTime = System.currentTimeMillis();
        long restartTime = startTime;

        // build a list of rooms that aren't corridors
        ArrayList<Room> nonCorridorRooms = new ArrayList<Room>();
        ArrayList<Tile> corridorTiles = new ArrayList<Tile>(); // TODO CONSIDER getting coordinate arrays instead?
        nonCorridorRooms.addAll(rooms);
        for (int i = 0; i < nonCorridorRooms.size(); i++) {
            if(nonCorridorRooms.get(i).isCorridor()){
                nonCorridorRooms.remove(i);
            }
        }

        // generate corridor points. this should be handled in Room
        for (Iterator<Room> iterator = nonCorridorRooms.iterator(); iterator.hasNext(); ) {
            Room next = iterator.next();
            if (next.getType() != Values.Types.CORRIDOR_X && next.getType() != Values.Types.CORRIDOR_Y) {
                corridorPointTiles.addAll(next.getBorderPoints());
            }
        }

        // generate a list of room pairs
        ArrayList<Room[]> roomPairs = new ArrayList<Room[]>();
        for (Iterator<Room> startRoomIterator = nonCorridorRooms.iterator(); startRoomIterator.hasNext(); ) {
            Room start = startRoomIterator.next();
            for (Iterator<Room> endRoomIterator = nonCorridorRooms.iterator(); endRoomIterator.hasNext(); ) {
                Room end = endRoomIterator.next();
                if(start != end){
                    Room[] roomPair = new Room[2];
                    roomPair[0] = start;
                    roomPair[1] = end;
                    roomPairs.add(roomPair);
                }
            }
        }

        // GENERATE CORRIDORS FOR EACH ROOM
        // TODO tidy up after seeing if the basics work
        // iterate through non corridor rooms, making arbitrary connections
        while(!roomPairs.isEmpty()){ // while still unmatched pairs

            // initialise shortest paths list
            ArrayList<ArrayList<Tile>> shortestPaths = new ArrayList<ArrayList<Tile>>();

            // iterate over the list of room pairs and build up the shortest routes
            for (Iterator<Room[]> roomPairIterator = roomPairs.iterator(); roomPairIterator.hasNext(); ) {
                Room[] roomPair =  roomPairIterator.next();
                Room start = roomPair[0];
                Room end = roomPair[1];
                if(hasTraversablePath(start.getRandomTile(), end)){ // if this pair has been matched, remove it
                    roomPairIterator.remove();
                } else {
                    ArrayList<Tile> path = getCorridorPath(start, end);
                    if (path.isEmpty()) {
                        System.out.println("Could not find a path connecting these rooms"); // TODO give rooms names
                    } else {
                        shortestPaths.add(path);
//                        for (Iterator<Tile> iterator = path.iterator(); iterator.hasNext(); ) {
//                            Tile next = iterator.next();
//                            if (!corridorTiles.contains(next) && next.isVoid()) { // remove void tiles and repeated tiles
//                                corridorTiles.add(next);
//                            }
//                        }
                    }
                }
            }

            if(!shortestPaths.isEmpty()){
                ArrayList<Tile> shortestPath = shortestPaths.get(0);
                for (Iterator<ArrayList<Tile>> iterator = shortestPaths.iterator(); iterator.hasNext(); ) {
                    ArrayList<Tile> next = iterator.next();
                    if(next.size() < shortestPath.size()){
                        shortestPath = next;
                    }
                }

                // TODO change iteration to build only one room
                ArrayList<Tile> currentCorridorTiles = new ArrayList<Tile>();
                for (Iterator<Tile> iterator = shortestPath.iterator(); iterator.hasNext(); ) {
                    Tile next = iterator.next();
                    if(next.isVoid()){
                        corridorTiles.add(next);
                        currentCorridorTiles.add(next);

//                        rooms.add(corridor);
//                        rooms.add(new Room(next.getX(), next.getY(), 1, 1, Values.Types.CORRIDOR_X)); // TODO remove
                    }
                }

//                Corridor corridor = new Corridor(currentCorridorTiles); // TODO expand
//                rooms.add(corridor);

                long curTime = System.currentTimeMillis();
                if((curTime - restartTime)/1000 > Values.SEARCH_INCREMENT_TIME){
                    System.out.println("Time limit reached. Increasing timeout");
                    if(timeout > Values.SEARCH_TIME_LIMIT_MAX){
                        throw new LongCorridorGeneration();
                    } else {
                        timeout = timeout + Values.SEARCH_TIME_LIMIT;
                        restartTime = System.currentTimeMillis();
                    }
                }

            }

        }

        long endTime = System.currentTimeMillis();

        System.out.println("Corridor generation took " + (endTime - startTime)/1000 + " second. Max timeout: " + timeout + "ms");

        // iterate through corridor tiles building rooms
//        while(!corridorTiles.isEmpty()){ // TODO optimise
//
//            System.out.println("Remaining corridor tiles: " + corridorTiles.size());
//
//            Tile firstTile = corridorTiles.get(0);
//
//            int drawX = firstTile.getX();
//            int drawY = firstTile.getY();
//
//            if(corridorTiles.size() > 1) {
//
//                int roomX = 1;
//                int roomY = 1;
//
//                Tile secondTile = corridorTiles.get(1);
//
//                // see what direction the corridor tiles are going in and draw rooms accordingly
//                if(firstTile.getX() == secondTile.getX() && (Math.abs(firstTile.getY()) - Math.abs(secondTile.getY())) == 1){ // if x is the same, and y is one more or less, draw a vertical corridor
//                    while(corridorTiles.get(0).getX() == corridorTiles.get(1).getX() && (Math.abs(corridorTiles.get(0).getY()) - Math.abs(corridorTiles.get(1).getY())) == 1){ // while this condition persists, increment and delete
//                        roomX++; // increment room Y size
//                        corridorTiles.remove(0);
//                    }
//                } else if(firstTile.getY() == secondTile.getY() && (Math.abs(firstTile.getX()) - Math.abs(secondTile.getX())) == 1){ // if y is the same, and x is one more or less, draw a horizontal corridor
//                    while(corridorTiles.get(0).getY() == corridorTiles.get(1).getY() && (Math.abs(corridorTiles.get(0).getX()) - Math.abs(corridorTiles.get(1).getX())) == 1){
//                        roomY++; // increment room X size
//                        corridorTiles.remove(0);
//                    }
//                }
//
//                rooms.add(new Room(drawX, drawY, roomX, roomY, Values.Types.CORRIDOR_X));
//                corridorTiles.remove(0);
//
//            } else {
//                // draw a simple junction
//                rooms.add(new Room(drawX, drawY, 1, 1, Values.Types.CORRIDOR_X)); // TODO change to just "CORRIDOR"
//                corridorTiles.remove(0);
//            }
//
//
//
//        }


    }
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

    // uses breadth first traversal to find a route from a random corridor point on here to a random corridor point
    private ArrayList<Tile> getCorridorPath(Room start, Room end){ // TODO optimise

        System.out.println("Get corridor path called");

        ArrayList<ArrayList<Tile>> paths = new ArrayList<ArrayList<Tile>>();

        // iterate through all starting tiles finding the shortest route
        for (Iterator<int[]> iterator = start.getEdgeTileCoordinates().iterator(); iterator.hasNext(); ) {

            // get an edge tile
            int[] edgeTileCoordinate = iterator.next();
            Tile startTile = tiles[edgeTileCoordinate[0]][edgeTileCoordinate[1]];

//            System.out.println("Finding path from " + start.getTypeString() + " [" + edgeTileCoordinate[0] + ", " + edgeTileCoordinate[1] + "] to " + end.getTypeString());

            // initialise list of tiles explored this search
            ArrayList<Tile> explored = new ArrayList<Tile>();

            // initialise the list of routes to explore
            ArrayList<ArrayList<Tile>> potentialRoutes = new ArrayList<ArrayList<Tile>>();

            // make route and add this tile to it
            ArrayList<Tile> firstRoute = new ArrayList<Tile>();
            firstRoute.add(startTile);

            // add this route to the potential routes list (the to do list)
            potentialRoutes.add(firstRoute);

            boolean routeFound = false;
            long startTime = System.currentTimeMillis(); // get the time
            long currentTime = startTime;

            // iterate through the routes trying to find a path
            int potentialRoutesSize = potentialRoutes.size();
            for(int i = 0; i < potentialRoutesSize && !routeFound && ((currentTime - startTime) < timeout); i++){ // added 100ms arbitrary time limit to searches. may experience issues with this though
//                System.out.println("Analysing new route");
//            for (Iterator<ArrayList<Tile>> tileIterator = potentialRoutes.iterator(); tileIterator.hasNext() && !routeFound; ) {
                ArrayList<Tile> route = potentialRoutes.get(i);

                // get the last tile from the currently analysing route and add it to the explored list
                Tile lastTile = route.get(route.size() - 1);
                explored.add(lastTile);

                // get tile coordinates
                int x = lastTile.getX();
                int y = lastTile.getY();

                // initialise tiles and put them in an arraylist
                Tile northTile = null, eastTile = null, southTile = null, westTile = null;
                ArrayList<Tile> potentialTiles = new ArrayList<Tile>();

                if(y > 0){
                    northTile = Game.map.tiles[x][y-1];
                    potentialTiles.add(northTile);
                }

                if(y < Game.map.getHeight()-1){
                    southTile = Game.map.tiles[x][y+1];
                    potentialTiles.add(southTile);
                }

                if(x > 0){
                    westTile = Game.map.tiles[x-1][y];
                    potentialTiles.add(westTile);
                }

                if(x < Game.map.getWidth()-1){
                    eastTile = Game.map.tiles[x+1][y];
                    potentialTiles.add(eastTile);
                }

                // iterate through the potential tiles
                for (Iterator<Tile> pti = potentialTiles.iterator(); pti.hasNext(); ) {
                    currentTime = System.currentTimeMillis();
                    Tile nextTile = pti.next();
//                    System.out.println("Analysing [" + nextTile.getX() + ", " + nextTile.getY() + "]");

                    // if the next tile hasn't been explored
                    if(!explored.contains(nextTile)){ // TODO TILE TRAVERSER STATIC CLASS

                        // build a new route // TODO CONSIDER is this inefficient if the route is likely to be discarded now?
                        ArrayList<Tile> nextPotentialRoute = new ArrayList<Tile>();
                        nextPotentialRoute.addAll(route); // add all tiles from currently analysing route
                        nextPotentialRoute.add(nextTile); // add the next tile to that route

                        // if next tile is a void tile, add it to the route list

                        if(nextTile.isVoid()){
                            potentialRoutes.add(nextPotentialRoute);
                            potentialRoutesSize = potentialRoutes.size(); // recalculate the size for the loop
//                        } else if(hasTraversablePath(nextTile, end)){
//                            paths.add(nextPotentialRoute);
//                            routeFound = true;
                        } else if(nextTile.getRoom() == end){
                            paths.add(nextPotentialRoute);
                            routeFound = true;
                        }
                    }

                }

            }

        }

        if(paths.isEmpty()){
            System.out.println("Could not find a path from " + start.getTypeString() + " to " + end.getTypeString() + " in reasonable time");
            return new ArrayList<Tile>(); // return empty array list // TODO optimise
        } else {
            ArrayList<Tile> shortestPath = null;
            int shortestPathSize = 0;
            for (Iterator<ArrayList<Tile>> iterator = paths.iterator(); iterator.hasNext(); ) {
                ArrayList<Tile> path = iterator.next();
                if(shortestPath == null){
                    shortestPath = path;
                    shortestPathSize = path.size();
                } else {
                    if(path.size() < shortestPathSize){ // OPTIMISE
                        shortestPath = path;
                        shortestPathSize = path.size();
                    }
                }
            }
            return shortestPath;
        }

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
