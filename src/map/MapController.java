package map;

import main.*;
import map.functionals.Functional;
import map.functionals.Toilet;
import org.lwjgl.Sys;
import org.newdawn.slick.Graphics;
import tiles.Tile;
import tiles.VisibleTile;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Don't let this controller get really messy.
 * Created by stanners on 21/07/2015.
 */
public class MapController {

    private boolean refreshCache;
    private int width, height;
    private boolean[] booleans;
    private Tile[][] tiles;
    private ArrayList<Room> rooms;
    private ArrayList<Door> doors;
    private ArrayList<Point> base;
    private ArrayList<Functional> functionals;

    // hover components
    private Door hoverDoor;
    private Room hoverRoom;

    // Room creation
    private ArrayList<Point> dragSelection;
    private ArrayList<Point> selection;

    public MapController(String mapFileName){ // TODO CONSIDER RoomBuilder class - instead of "RoomController"?

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
        functionals = new ArrayList<Functional>();
        selection = new ArrayList<Point>();
        hoverDoor = null;
        hoverRoom = null;

//        initialiseTestFunctionals(); // TODO

//
//        generateDoors();

    }

    public void init(){
        initialiseTiles(); // set all tiles as void tiles
        createRoom(base, true);
    }

    public Tile getTile(Point point){
        return tiles[(int) point.getX()][(int) point.getY()];
    }

    public Tile getTile(int x, int y){ // TODO resolve inconsistencies between using points and int pairs, and consider using Point here
        return tiles[x][y];
    }

    public void putTile(Point point, Tile tile){
        tiles[(int) point.getX()][(int) point.getY()] = tile;
    }

    public void createRoom(ArrayList<Point> points, boolean force){

        // get rooms affected by the creation of this room
        List<Room> affectedRooms = getAffectedRooms(points);
        System.out.println("affected rooms: " + affectedRooms.size());

        // remove any points from the previous rooms
        for (Iterator<Room> iterator = affectedRooms.iterator(); iterator.hasNext(); ) {
            Room next = iterator.next();
            next.disownPoints(points);
        }

        // create the new room
        Room firstNewRoom = new Room(points, force);
        firstNewRoom.init(); // TODO POTENTIAL BUG check these variable name usages
        rooms.add(firstNewRoom);

        // split existing rooms and create new ones
        while(!affectedRooms.isEmpty()){

            // get disowned points and remove them from the old room
            Room oldRoom = affectedRooms.get(0);
            affectedRooms.remove(0);
            List<Point> disownedPoints = oldRoom.getDisownedPoints();
            oldRoom.disownPoints(disownedPoints);

            // create a new (affected) room from the disowned points
            if(disownedPoints.size() > 0){
                Room nextNewRoom = new Room(disownedPoints, force);
                nextNewRoom.init();
                rooms.add(nextNewRoom);
                affectedRooms.add(nextNewRoom);
            }

        }

        // TODO updates to any other entities

        // remove any rooms that now contain no tiles // TODO CONSIDER should this go before new room initialisation? i dunno...
        cleanupRooms();

        generateDoors(); // wat
//        clearDoors(); // TODO properly. consider making this into a thing that updates walls and doors?
    }

    private void initialiseTestFunctionals() {
        functionals.add(new Toilet(new Point(0,0), 0));
    }

    private void initialiseTiles(){

        // booleans array index
        int index = 0;

        base = new ArrayList<Point>();

        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[x].length; y++){
                Point point = new Point(x,y);
                initialiseTile(point);

                // Add these coordinates to initial room points
                if(booleans[index]){
                    base.add(point);
                }

                index++;

            }
        }

        // Add all tiles as first room
        // createRoom(points, true); // TODO put an appropriate constant somewhere for useVoid

    }

    private void initialiseTile(Point point){

        int x = (int) point.getX();
        int y = (int) point.getY();
        tiles[x][y] = new Tile(x, y);

    }

    private void cleanupRooms(){
        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room next = iterator.next();
            if (next.size() < 1){
                iterator.remove();
            }
        }
    }

//    public Room createRoom(ArrayList<Point> points, boolean useVoid){ // TODO need more variables // TODO remove a room if it no longer has any tiles in update method
//
//        // create room with points
//        Room room = new Room(points);
//
//        for (Iterator<Point> iterator = points.iterator(); iterator.hasNext(); ) {
//            Point next = iterator.next();
//
//            if(useVoid || !getTile(next).isVoid()){ // if using void tiles is permitted, or it's not a void tile
//
//                // initialise the tile
//                initialiseTile(next);
//
//                // create new visible tiles
//                int x = (int) next.getX();
//                int y = (int) next.getY();
//                tiles[x][y] = new VisibleTile(x, y, room); // TODO CONSIDER change to Point?
//            }
//
//        }
//
//        rooms.add(room);
//
//        generateDoors();
//
//        return room;
//    }

    public void generateDoors(){

        MapController mc = GameController.mapController;

        clearDoors();

        // generate new doors // TODO CONSIDER should all doors update? maybe cache could only update select tiles (e.g by association checks?)
        // TODO optimise this if necessary to take points arraylist as arguments and only update for the new room?
        for(int w = 0; w < width; w++){
            for(int h = 0; h < height; h++){
                Tile currentTile = tiles[w][h];
                if(!currentTile.isVoid()){
                    // TODO don't add doors twice - if we're not checking north and west, or east and south, we'll automatically exclude duplicates
                    if(h > 0){ // if not on north bound
                        Tile northTile = tiles[w][h-1];
                        if(!northTile.isVoid() && currentTile.getRoom() != northTile.getRoom()){ // && (mc.getDoor(currentTile.getPoint(), northTile.getPoint()) == null)){
                            doors.add(new Door(currentTile.getPoint(), northTile.getPoint())); // TODO clean up this QD business
                        }
                    }

                    if(w > 0){ // if not on west bound
                        Tile westTile = tiles[w-1][h];
                        if(!westTile.isVoid() && currentTile.getRoom() != westTile.getRoom()){ // && (mc.getDoor(currentTile.getPoint(), westTile.getPoint()) == null)){
                            doors.add(new Door(currentTile.getPoint(), westTile.getPoint())); // TODO clean up this QD business
                        }
                    }

                }

            }
        }
    }

    public int getWidth(){
        return width;
    }

    public int getHeight() {
        return height;
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

    public void updateTiles(){ // TODO CONSIDER putting this somewhere else
        for (Iterator<Point> iterator = selection.iterator(); iterator.hasNext(); ) {
            getTile(iterator.next()).setSelected(true);
        }
    }

    public void updateDoors(){ // TODO sort these methods
        for (Iterator<Door> iterator = doors.iterator(); iterator.hasNext(); ) {
            Door next = iterator.next();
            next.update();
        }
    }

    public Room getRoom(Point point){
        return getTile(point).getRoom();
    }

    /**
     * Returns door object based on two tile points
     * @param point1
     * @param point2
     * @return
     */
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

    public void clearHoverObjects(){
        hoverDoor = null;
    }

    public boolean setHoverRoom(Point mousePoint){ // TODO tidy up and optimise
        for (Iterator<Room> iterator = rooms.iterator(); iterator.hasNext(); ) {
            Room next = iterator.next();
            if(next.mouseOver(mousePoint)){
                hoverRoom = next;
                return true;
            }
        }
        return false;
    }

    public Room getHoverRoom(){
        return hoverRoom;
    }

    public boolean setHoverDoor(Point mousePoint){
        for (Iterator<Door> iterator = doors.iterator(); iterator.hasNext(); ) {
            Door next = iterator.next();
            if(next.mouseOver(mousePoint)){
                hoverDoor = next;
                return true;
            }
        }
        return false;
    }

    public Door getHoverDoor(){
        return hoverDoor;
    }

    /**
     * Remove doors if they no longer exist
     */
    public void clearDoors(){

        MapController mc = GameController.mapController; // TODO put this somewhere better. need to be able to access this more cleanly and easily

        for (Iterator<Door> iterator = doors.iterator(); iterator.hasNext(); ) {
            Door next =  iterator.next();
            if(mc.getRoom(next.getStartPoint()) == mc.getRoom(next.getEndPoint())){
                iterator.remove();
            }
        }
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
            if(next.isEnabled()){ // if enabled, draw the door.
                next.render(screen);
            }
        }
    }

    public void renderFunctionals(Graphics screen){
        for (Iterator<Functional> functional = functionals.iterator(); functional.hasNext(); ) {
            Functional next = functional.next();
            next.render(screen);
        }
    }

    public void renderHoverBoxes(Graphics screen){
        if(hoverDoor != null){
            hoverDoor.renderHoverBox(screen);
        }
    }

    public List<Room> getAffectedRooms(ArrayList<Point> points){

        List<Room> affectedRooms = new ArrayList<Room>();

        for (Iterator<Point> iterator = points.iterator(); iterator.hasNext(); ) {
            Point next = iterator.next();
            Room room = getRoom(next);
            if(!affectedRooms.contains(room) && room != null){
                affectedRooms.add(room);
            }
        }

        return affectedRooms;

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

    /**
     * False if both points are in the same room. I could have written less code here, but I want it to appear readable
     * @param point1
     * @param point2
     * @return
     */
    public boolean getWall(Point point1, Point point2){
        if(getRoom(point1) == getRoom(point2)){
            return false;
        } else {
            return true;
        }
    }

    public void setDragSelection(Point dragStart, Point dragEnd){

        int startX  = (int) dragStart.getX();
        int endX    = (int) dragEnd.getX();
        int startY  = (int) dragStart.getY();
        int endY    = (int) dragEnd.getY();

        if(startX > endX){
            startX  = (int) dragEnd.getX();
            endX    = (int) dragStart.getX();
        }

        if(startY > endY){
            startY  = (int) dragEnd.getY();
            endY    = (int) dragStart.getY();
        }
        
        this.dragSelection = new ArrayList<Point>();

        for(int x = startX; x <= endX; x++){ // TODO RESEARCH is there a Point class that uses ints? could i create my own? it would have to override equals()
            for(int y = startY; y <= endY; y++){
                if(x > 0 && x < getWidth() && y > 0 && y < getHeight()) {

                    // Positive point values
                    int px = x;
                    int py = y;

                    dragSelection.add(new Point(px, py));
                    getTile(px, py).setSelected(true);
                }
            }
        }

    }

    public void releaseDrag(){
        // add selected tiles to selection
        selection.addAll(dragSelection);
    }

    /**
     * Commit selection to a new room
     */
    public void createRoomFromSelection(){

        if(!isContiguous(selection)){
            System.err.println("Selection is not contiguous"); // TODO make better. maybe throw an exception?
            return;
        }

        if(selection.size() > 0){
            createRoom(selection, false);
            clearSelection();
        }
    }

    /**
     * Clear selected tiles
     */
    public void clearSelection(){
        for (Iterator<Point> iterator = selection.iterator(); iterator.hasNext(); ) {
            Point next = iterator.next();
            getTile(next).setSelected(false);
        }
        selection = new ArrayList<Point>();
    }

    public void renderRoomData(Graphics screen) {
        screen.drawString("rooms: " + rooms.size(), 20, 20);
    }

    private boolean isContiguous(List<Point> points){

        boolean contiguous = true;

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
        if(unexploredPoints.size() > 0){
            contiguous = false;
        }
        return contiguous;
    }
}
