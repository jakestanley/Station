package main;

import exceptions.NoAction;
import exceptions.NoSpawnableArea;
import mobs.Mate;
import mobs.Mob;
import mobs.Parasite;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import tiles.Tile;
import tiles.VoidTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 22/05/2015.
 */
public class Map extends Loopable {

    public static Tile[][] tiles; // TODO need to move tiles into here so I can manage getting to tiles with the AI. should this be static?

    private int width, height;
    private ArrayList<Room> rooms;
    private ArrayList<Mob> mobs;
    private ArrayList<Door> doors;
    private ArrayList<Tile> corridorPointTiles;

    public Map(){
        super(0, 0); // frame doesn't really apply to map, but i guess it could do? TODO consider animated superclass?

        System.out.println("Initialising map");

        // initialise tile array
        width = Display.MAP_WIDTH / Display.TILE_WIDTH;
        height = Display.MAP_HEIGHT / Display.TILE_WIDTH;
        System.out.println("Map size is " + width + "x" + height);
        tiles = new Tile[width][height];

        // initialising all tiles as void tiles
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[x].length; y++){
                tiles[x][y] = new VoidTile(x, y); // void tile constructor
            }
        }

        // initialise component lists
        rooms = new ArrayList<Room>();
        mobs = new ArrayList<Mob>();
        doors = new ArrayList<Door>();
        corridorPointTiles = new ArrayList<Tile>();

    }

    @Override
    public void init(){
        // generate components
        try {
//            buildStockMap();
            generateRooms();
            generateCorridors();
            generateDoors();
            generateMobs();
        } catch(NoSpawnableArea e) {
            System.err.println("Can't spawn mobs as there are no non void tiles. Exiting");
            System.exit(0);
        }
    }

    @Override
    public void render(Graphics screen){
        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room next =  iterator.next();
            next.render(screen);
        }

        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob next = iterator.next();
            next.render(screen);
        }

        for (Iterator<Door> iterator = doors.iterator(); iterator.hasNext(); ) {
            Door next =  iterator.next();
            next.render(screen);
        }

        for (Iterator<Tile> iterator = corridorPointTiles.iterator(); iterator.hasNext(); ) {
            Tile next =  iterator.next();
            next.render(screen);
        }

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

    public Room getRoomMouseOver(int mouseX, int mouseY){ // TODO display mobs in room when hovering over
        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room next = iterator.next();
            if(next.mouseOver(mouseX, mouseY)){
                return next;
            }
        }
        return null;
    }

    public Door getDoorMouseOver(int mouseX, int mouseY){
        for (Iterator<Door> iterator = doors.iterator(); iterator.hasNext(); ) {
            Door next =  iterator.next();
            if(next.mouseOver(mouseX, mouseY)){
                return next;
            }
        }
        return null;
    }

    private void generateRooms(){ // TODO ensure rooms don't clash

        // initialise local variables
        int x = -1, y = 0, sx = 0, sy = 0;

//        buildStockMap(); // TODO after map generation is working properly
        // TODO consider avoiding the very edges if possible? should the map be larger?

        // place the bridge. don't really need the checks first, but doing them for niceness. TODO CONSIDER remove later?
        Room bridge = null;
        while(roomClash(x, y, sx, sy)){
            sx = Values.Dimensions.BRIDGE;
            sy = sx;
            x = Game.random.nextInt(width - sx);
            y = Game.random.nextInt(height - sy);
        }
        bridge = new Room(x, y, sx, sy, Values.Types.BRIDGE);
        rooms.add(bridge);

        x = -1;

        // place the life support
        Room lifeSupport = null;
        while(roomClash(x, y, sx, sy)) {
            if (Game.random.nextBoolean()) { // make life support vertical
                sx = Values.Dimensions.LIFE_SUPPORT_Y_X;
                sy = Values.Dimensions.LIFE_SUPPORT_Y_Y;
            } else { // make life support horizontal
                sx = Values.Dimensions.LIFE_SUPPORT_X_X;
                sy = Values.Dimensions.LIFE_SUPPORT_X_Y;
            }
            x = Game.random.nextInt(width - sx);
            y = Game.random.nextInt(height - sy);
        }
        lifeSupport = new Room(x, y, sx, sy, Values.Types.LIFESUPPORT);
        rooms.add(lifeSupport);

        x = -1;

        // place the hangar
        Room hangar = null;
        while(roomClash(x, y, sx, sy)){
            if(Game.random.nextBoolean()){ // make hangar vertical
                sx = Values.Dimensions.HANGAR_Y_X;
                sy = Values.Dimensions.HANGAR_Y_Y;
            } else { // make life support horizontal
                sx = Values.Dimensions.HANGAR_X_X;
                sy = Values.Dimensions.HANGAR_X_Y;
            }
            x = Game.random.nextInt(width - sx);
            y = Game.random.nextInt(height - sy);
        }
        hangar = new Room(x, y, sx, sy, Values.Types.HANGAR);
        rooms.add(hangar);

        x = -1;

        // place the hangar
        Room junction1 = null;
        while(roomClash(x, y, sx, sy)){
            sx = 1;
            sy = 1;
            x = Game.random.nextInt(width - sx);
            y = Game.random.nextInt(height - sy);
        }
        junction1 = new Room(x, y, sx, sy, Values.Types.GENERIC);
        rooms.add(junction1);

        // place the hangar
        Room junction2 = null;
        while(roomClash(x, y, sx, sy)){
            sx = 1;
            sy = 1;
            x = Game.random.nextInt(width - sx);
            y = Game.random.nextInt(height - sy);
        }
        junction2 = new Room(x, y, sx, sy, Values.Types.GENERIC);
        rooms.add(junction2);

        // place the hangar
        Room junction3 = null;
        while(roomClash(x, y, sx, sy)){
            sx = 1;
            sy = 1;
            x = Game.random.nextInt(width - sx);
            y = Game.random.nextInt(height - sy);
        }
        junction3 = new Room(x, y, sx, sy, Values.Types.GENERIC);
        rooms.add(junction3);

        // TODO add more rooms and particularly corridors. they might be tricky
        // TODO more dynamic amounts of rooms, etc



    }

    private void buildStockMap(){
        rooms.add(new Room(4, 4, 3, 3,  Values.Types.GENERIC)); // TODO appropriate values that are multiples of tile width
        rooms.add(new Room(7, 5, 3, 1,  Values.Types.CORRIDOR_X)); // TODO remove CORRIDOR_X and CORRIDOR_Y and replace with just CORRIDOR
        rooms.add(new Room(10, 5, 1, 1, Values.Types.GENERIC));
        rooms.add(new Room(10, 6, 1, 3, Values.Types.CORRIDOR_Y));
        rooms.add(new Room(9, 9, 4, 4,  Values.Types.BRIDGE));
    }

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

    private void generateCorridors() {

        // GENERATE CORRIDOR POINTS (probably not necessary, it should be easy to get these.
        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room next = iterator.next();
            if (next.getType() != Values.Types.CORRIDOR_X && next.getType() != Values.Types.CORRIDOR_Y) {
                corridorPointTiles.addAll(next.getBorderPoints());
            }
        }

        // GENERATE CORRIDORS FOR EACH ROOM
//        for (int i = 0; i < rooms.size(); i++) {

// TODO tidy up after seeing if the basics work
//        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) { // primitive for now
            Room start = rooms.get(0); //            Room start = rooms.get(i); // Room start = iterator.next();
            Room end = rooms.get(1); //            Room end = null; // TODO room connections should be random
//            if (rooms.get(i + 1) != null) { // if(iterator.hasNext()){
//                end = rooms.get(i + 1); // end = iterator.next();
                ArrayList<Tile> path = getCorridorPath(start, end);
                if (path.isEmpty()) {
                    System.out.println("Could not find a path connecting these rooms"); // TODO give rooms names
                } else {
                    for (Iterator<Tile> tileIterator = path.iterator(); tileIterator.hasNext(); ) {
                        Tile next = tileIterator.next();
                        int tileX = next.getX();
                        int tileY = next.getY();
                        rooms.add(new Room(tileX, tileY, 1, 1, Values.Types.CORRIDOR_X)); // TODO change
                    }
                }
            }

//        }
//    }

    private void generateDoors(){

        for(int w = 0; w < width; w++){
            for(int h = 0; h < height; h++){
                Tile currentTile = tiles[w][h];
                if(currentTile.isTraversable()){
                    // TODO don't add doors twice - if we're not checking north and west, or east and south, we'll automatically exclude duplicates
                    if(h > 0){ // if not on north bound
                        Tile northTile = tiles[w][h-1];
                        if(northTile.isTraversable() && northTile.getType() != Values.Types.CORRIDOR_X && currentTile.getRoom() != northTile.getRoom()){
                            doors.add(new Door(currentTile, northTile));
                        }
                    }

                    if(w > 0){ // if not on west bound
                        Tile westTile = tiles[w-1][h];
                        if(westTile.isTraversable() && westTile.getType() != Values.Types.CORRIDOR_Y && currentTile.getRoom() != westTile.getRoom()){
                            doors.add(new Door(currentTile, westTile));
                        }
                    }

                }

            }
        }

        System.out.println("Detected " + doors.size() + " doors");

    }

    private void generateMobs() throws NoSpawnableArea {

        int crewCount = 7;
        int hostileCount = 1;

        // generate crew
        for(int i = 0; i < crewCount; i++){
            Tile randomTile = getRandomTraversibleTile();
            if(randomTile == null){
                throw new NoSpawnableArea();
            }
            mobs.add(new Mate(randomTile.getX(), randomTile.getY()));
        }

        // generate hostiles
        for(int i = 0; i < hostileCount; i++){
            Tile randomTile = getRandomTraversibleTile();
            if(randomTile == null){
                throw new NoSpawnableArea();
            }
            mobs.add(new Parasite(randomTile.getX(), randomTile.getY()));
        }

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

            // iterate through the routes trying to find a path
            int potentialRoutesSize = potentialRoutes.size();
            for(int i = 0; i < potentialRoutesSize && !routeFound; i++){
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
                    Tile nextTile = pti.next();

                    // if the next tile hasn't been explored
                    if(!explored.contains(nextTile)){

                        // build a new route // TODO CONSIDER is this inefficient if the route is likely to be discarded now?
                        ArrayList<Tile> nextPotentialRoute = new ArrayList<Tile>();
                        nextPotentialRoute.addAll(route); // add all tiles from currently analysing route
                        nextPotentialRoute.add(nextTile); // add the next tile to that route

                        // if next tile is a void tile, add it to the route list
                        int tileType = nextTile.getType();
                        if(Values.Types.VOID == tileType || Values.Types.META_BORDER == tileType){
                            potentialRoutes.add(nextPotentialRoute);
                            potentialRoutesSize = potentialRoutes.size(); // recalculate the size for the loop
                        } else if(nextTile.hasPathTo(end)){
                            paths.add(nextPotentialRoute);
                            routeFound = true;
                        } else if(nextTile.getRoom() == end){
                            paths.add(nextPotentialRoute);
                            routeFound = true;
                        }
                    }

                }


            }



        }

        for (Iterator<ArrayList<Tile>> iterator = paths.iterator(); iterator.hasNext(); ) {
            ArrayList<Tile> next =  iterator.next();
            // TODO find the shortest path and set that as the return
        }

        if(paths.get(0) == null){
            return new ArrayList<Tile>(); // return empty array list // TODO optimise
        } else {
            return paths.get(0); // TODO return the shortest path instead of any
        }


    }

    public void moveMobs(){ // TODO mobs should decide themselves where to move based on some algorithm
        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob mob =  iterator.next();
            if(mob.alive()){ // TODO remove this and just have it in Mob::update if possible
                try {
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
            if(mob.getTile().getRoom() == room){
                roomMobs.add(mob);
            }
        }
        return roomMobs;
    }

    public ArrayList<Mob> getMobsInRoomByType(Room room, int type){
        ArrayList<Mob> mobs = new ArrayList<Mob>();
        for (Iterator<Mob> iterator = getMobsInRoom(room).iterator(); iterator.hasNext(); ) {
            Mob next =  iterator.next();
            if(next.getType() == type && next.getTile().getRoom() == room){
                mobs.add(next);
            }
        }
        return mobs;
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

    public Door getDoorByTiles(Tile tile1, Tile tile2){
        for (Iterator<Door> iterator = getDoors().iterator(); iterator.hasNext(); ) {
            Door door =  iterator.next();
            if( (door.getStartTile() == tile1 && door.getEndTile() == tile2) ||
                    (door.getStartTile() == tile2 && door.getEndTile() == tile1)){
//                System.out.println("Door match");
                return door;
            }
        }
        return null;
    }

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
                if(t.isTraversable()){
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

                int mobX = mob.getX();
                int mobY = mob.getY();

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
                    if((moveX >= 0) && (moveY >= 0) && (Game.map.tiles[moveX][moveY].isTraversable())){ // TODO also check that the border tiles aren't too big, as there are upper limits too
                        valid.add(next);
                    }
                }

                // Clarify legal options
                int count = valid.size();
                if(count > 0){
                    Point p = valid.get(Game.random.nextInt(valid.size()));
                    mob.moveTo((int) p.getX(), (int) p.getY());
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
