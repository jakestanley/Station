package map;

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

    private int width, height;
    private boolean[] booleans;
    private Tile[][] tiles;
    private ArrayList<Room> rooms;

    public MapController(String mapFileName){

        // Load map
        MapTemplate mapTemplate = MapLoader.loadMap(mapFileName);

        // Initialise variables
        width = mapTemplate.getWidth();
        height = mapTemplate.getHeight();
        booleans = mapTemplate.getBooleans();
        tiles = new Tile[width][height];
        rooms = new ArrayList<Room>();

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
        testRoomPoints.add(new Point(12,12));
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

    public void renderWalls(Graphics screen){
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[x].length; y++){
                if(!tiles[x][y].isVoid()){
                    VisibleTile visibleTile = (VisibleTile) tiles[x][y];
                    visibleTile.renderWalls(screen);
                }
            }
        }
    }

}
