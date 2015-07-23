package map;

import main.Door;
import main.Room;
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

        // generate doors // TODO CONSIDER should all doors update?
        for(int w = 0; w < width; w++){
            for(int h = 0; h < height; h++){
                Tile currentTile = tiles[w][h];
                if(!currentTile.isVoid()){
                    // TODO don't add doors twice - if we're not checking north and west, or east and south, we'll automatically exclude duplicates
                    if(h > 0){ // if not on north bound
                        Tile northTile = tiles[w][h-1];
                        if(!northTile.isVoid() && currentTile.getRoom() != northTile.getRoom() && !(currentTile.getRoom().isCorridor() && northTile.getRoom().isCorridor())){
                            doors.add(new Door(currentTile, northTile));
                        }
                    }

                    if(w > 0){ // if not on west bound
                        Tile westTile = tiles[w-1][h];
                        if(!westTile.isVoid() && currentTile.getRoom() != westTile.getRoom() && !(currentTile.getRoom().isCorridor() && westTile.getRoom().isCorridor())){
                            doors.add(new Door(currentTile, westTile));
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

    public Tile getTile(int x, int y){ // TODO resolve inconsistencies between using points and int pairs
        return tiles[x][y];
    }

    public Room getRoom(Point point){
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

}
