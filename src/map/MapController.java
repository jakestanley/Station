package map;

import main.*;
import org.newdawn.slick.Graphics;
import tiles.Tile;
import tiles.VisibleTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 21/07/2015.
 */
public class MapController {

    private boolean refreshCache;
    private int width, height;
    private boolean[] booleans;
    private Tile[][] tiles;
    private ArrayList<Room> rooms;
    private ArrayList<Door> doors;

    public MapController(String mapFileName){

        // Load map
        MapTemplate mapTemplate = MapLoader.loadMap(mapFileName);

        // Initialise variables
        refreshCache = false;
        width = mapTemplate.getWidth();
        height = mapTemplate.getHeight();
        booleans = mapTemplate.getBooleans();
        tiles = new Tile[width][height];
        rooms = new ArrayList<Room>();
        doors = new ArrayList<Door>();

        // Initialise tiles
        initialiseTiles();

    }

    private void initialiseTile(Point point){
        int x = (int) point.getX();
        int y = (int) point.getY();
        tiles[x][y] = new Tile(x, y);
    }

    private void initialiseTiles(){

        int index = 0;
        ArrayList<Point> points = new ArrayList<Point>();

        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[x].length; y++){
                Point point = new Point(x,y);
                initialiseTile(point);

                // Add these coordinates to initial room points
                if(booleans[index]){
                    points.add(point);
                }

                index++;

            }
        }

        // Add all tiles as first room
        createRoom(points);
        createTestRoom();
    }

    private void createTestRoom(){ // TODO remove this method
        // Create additional test room
        ArrayList<Point> testRoomPoints = new ArrayList<Point>();
        testRoomPoints.add(new Point(10,10));
        testRoomPoints.add(new Point(11,10));
        testRoomPoints.add(new Point(12,10));
        testRoomPoints.add(new Point(10,11));
        testRoomPoints.add(new Point(11,11));
        testRoomPoints.add(new Point(12,11));
        testRoomPoints.add(new Point(10,12));
        testRoomPoints.add(new Point(11,12));
        testRoomPoints.add(new Point(12, 12));
        createRoom(testRoomPoints);
    }

    public Room createRoom(ArrayList<Point> points){ // TODO need more variables // TODO remove a room if it no longer has any tiles in update method

        // create room with points
        Room room = new Room(points);

        for (Iterator<Point> iterator = points.iterator(); iterator.hasNext(); ) {
            Point next = iterator.next();
            initialiseTile(next);

            // create new visible tiles
            int x = (int) next.getX();
            int y = (int) next.getY();
            tiles[x][y] = new VisibleTile(x, y, room);

        }

        rooms.add(room);

        // generate doors // TODO CONSIDER should all doors update?
        for(int w = 0; w < width; w++){
            for(int h = 0; h < height; h++){
                Tile currentTile = tiles[w][h];
                if(!currentTile.isVoid()){
                    // TODO don't add doors twice - if we're not checking north and west, or east and south, we'll automatically exclude duplicates
                    if(h > 0){ // if not on north bound
                        Tile northTile = tiles[w][h-1];
                        if(!northTile.isVoid() && currentTile.getRoom() != northTile.getRoom()){
                            doors.add(new Door(currentTile.getPoint(), northTile.getPoint())); // TODO clean up this QD business
                        }
                    }

                    if(w > 0){ // if not on west bound
                        Tile westTile = tiles[w-1][h];
                        if(!westTile.isVoid() && currentTile.getRoom() != westTile.getRoom()){
                            doors.add(new Door(currentTile.getPoint(), westTile.getPoint())); // TODO clean up this QD business
                        }
                    }

                }

            }
        }

        return room;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile getTile(int x, int y){ // TODO resolve inconsistencies between using points and int pairs, and consider using Point here
        return tiles[x][y];
    }

    public Tile getTile(Point point){
        return tiles[(int) point.getX()][(int) point.getY()];
    }

    public Point getSpawnPoint(){ // TODO use the cache!!!
        ArrayList<Point> nonVoidTiles = new ArrayList<Point>();
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[x].length; y++){
                Tile t = tiles[x][y];
                if(!t.isVoid()){
                    nonVoidTiles.add(t.getPoint());
                }
            }
        }
        int size = nonVoidTiles.size();
        if(size < 1){
            return null;
        }
        return nonVoidTiles.get(GameController.random.nextInt(nonVoidTiles.size()));
    }

    public Room getRoom(Point point){
        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room next = iterator.next();
            if(next.hasPoint(point)){ // TODO come up with a better method name
                return next;
            }
        }
        System.out.println("failed to get roomm");
        System.exit(0);
        return null;
    }

    public Door getDoor(Point point1, Point point2){
        for (Iterator<Door> iterator = doors.iterator(); iterator.hasNext(); ) {
            Door door =  iterator.next();
            if( (door.getStartPoint().equals(point1) && door.getEndPoint().equals(point2)) ||
                    (door.getStartPoint().equals(point2) && door.getEndPoint().equals(point1))){
//                System.out.println("Door match");
                return door;
            }
        }
        return null;
    }

    public void renderBackgrounds(Graphics screen){ // TODO consider getting this from somewhere else
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[x].length; y++){
                if(!tiles[x][y].isVoid()){
                    VisibleTile visibleTile = (VisibleTile) tiles[x][y];
                    visibleTile.renderBackground(screen);
                }
            }
        }
    }

    public void renderWalls(Graphics screen){ // TODO cache these in an arraylist and use an iterator if it proves to be quicker. run tests. re-cache on changing tiles
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[x].length; y++){
                if(!tiles[x][y].isVoid()){
                    VisibleTile visibleTile = (VisibleTile) tiles[x][y];
                    visibleTile.renderWalls(screen);
                }
            }
        }
    }

    public void renderDoors(Graphics screen){
        for (Iterator<Door> iterator = doors.iterator(); iterator.hasNext(); ) {
            Door next = iterator.next();
            next.render(screen);
        }
    }

    public ArrayList<Point> getTraversiblePath(Point start, Room end){ // less time allowed for this search
        System.out.println("Get traversible path called");

        int timeout = Values.SEARCH_TIME_LIMIT; // TODO put somewhere more semantic

        ArrayList<ArrayList<Point>> paths = new ArrayList<ArrayList<Point>>();

//        System.out.println("Finding path from [" + start.getX() + ", " + start.getY() + "] to " + end.getTypeString());

        // initialise list of tiles explored this search
        ArrayList<Point> explored = new ArrayList<Point>();

        // initialise the list of routes to explore
        ArrayList<ArrayList<Point>> potentialRoutes = new ArrayList<ArrayList<Point>>();

        // make route and add this tile to it
        ArrayList<Point> firstRoute = new ArrayList<Point>();
        firstRoute.add(start);

        // add this route to the potential routes list (the to do list)
        potentialRoutes.add(firstRoute);

        boolean routeFound = false;
        long startTime = System.currentTimeMillis(); // get the time
        long currentTime = startTime;

        // iterate through the routes trying to find a path
        int potentialRoutesSize = potentialRoutes.size();
        for(int i = 0; i < potentialRoutesSize && !routeFound && ((currentTime - startTime) < timeout) ; i++){ // added 100ms arbitrary time limit to searches. may experience issues with this though
//                System.out.println("Analysing new route");
//            for (Iterator<ArrayList<Tile>> tileIterator = potentialRoutes.iterator(); tileIterator.hasNext() && !routeFound; ) {
            ArrayList<Point> route = potentialRoutes.get(i);

            // get the last tile from the currently analysing route and add it to the explored list
            Point lastTile = route.get(route.size() - 1); // TODO rename variable
            explored.add(lastTile);

            // get tile coordinates
            int x = (int) lastTile.getX();
            int y = (int) lastTile.getY();

            // initialise tiles and put them in an arraylist
            Point northTile = null, eastTile = null, southTile = null, westTile = null;
            ArrayList<Point> potentialTiles = new ArrayList<Point>();

            if(y > 0){
                northTile = new Point(x, y-1);
                potentialTiles.add(northTile);
            }

            if(y < Game.map.getHeight()-1){
                southTile = new Point(x, y+1);
                potentialTiles.add(southTile);
            }

            if(x > 0){
                westTile = new Point(x-1, y);
                potentialTiles.add(westTile);
            }

            if(x < Game.map.getWidth()-1){
                eastTile = new Point(x+1, y);
                potentialTiles.add(eastTile);
            }

            // iterate through the potential tiles
            for (Iterator<Point> pti = potentialTiles.iterator(); pti.hasNext(); ) {
                currentTime = System.currentTimeMillis();
                Point nextTile = pti.next();
//                    System.out.println("Analysing [" + nextTile.getX() + ", " + nextTile.getY() + "]");

                // if the next tile hasn't been explored
                if(!explored.contains(nextTile)){ // TODO TILE TRAVERSER STATIC CLASS

                    // build a new route // TODO CONSIDER is this inefficient if the route is likely to be discarded now?
                    ArrayList<Point> nextPotentialRoute = new ArrayList<Point>();
                    nextPotentialRoute.addAll(route); // add all tiles from currently analysing route
                    nextPotentialRoute.add(nextTile); // add the next tile to that route

                    // if next tile is a an end tile, add to path and mark as route found

                    if(end.hasPoint(nextTile)){
                        paths.add(nextPotentialRoute);
                        routeFound = true;
                    } else if(!getTile(nextTile).isVoid()) { // if the next tile is traversible, add to potential routes. // TODO check there is a door if so
                        potentialRoutes.add(nextPotentialRoute);
                        potentialRoutesSize = potentialRoutes.size(); // recalculate the size for the loop
                    }

                }

            }


        }

        // return the shortest path
        if(paths.isEmpty()){
            System.out.println("Could not find a path from [" + start.getX() + ", " + start.getY() + "] to " + end.getTypeString() + " in reasonable time");
            return new ArrayList<Point>(); // return empty array list // TODO optimise
        } else {
            ArrayList<Point> shortestPath = null;
            int shortestPathSize = 0;
            for (Iterator<ArrayList<Point>> iterator = paths.iterator(); iterator.hasNext(); ) {
                ArrayList<Point> path = iterator.next();
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

}
