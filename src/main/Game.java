package main;

import guicomponents.*;
import mobs.Mob;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.ResourceLoader;
import tiles.Tile;
import tiles.VisibleTile;

import java.awt.*;
import java.awt.Font;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by stanners on 22/05/2015.
 */
public class Game extends BasicGame {

    public static final int EXIT_GOOD   = 0;
    public static final int EXIT_BAD    = 1;

    public static final int NO_SELECTION = 0;
    public static final int ROOM_SELECTION = 1;
    public static final int DOOR_SELECTION = 2; // TODO CONSIDER that this won't work until i sort out the overcrowding? revise.
    public static final int NAUT_SELECTION = 3;
    public static final int HOSTILE_SELECTION = 4;

    public static boolean debug = false;
    public static boolean pause = false;
    public static boolean won = false;
    public static int MAX_TICK = 30; // if this is lower than the highest animation loop length, shit will get fucked up
    public static GameContainer container;
    public static Map map;
    public static Random random;
    public static Pulse pulse;
    public static ViewController vc;

    // TODO sort and categorise variables
    private static boolean mouseOverRoom = true, shift = false;
    public int mouseX, mouseY, selection;
    private int tick;

    // ROOM STUFF
    private Room hoverRoom;
    private Door hoverDoor;
    private Mob hoverMob;
    private ArrayList<Mob> mobs; // TODO remove this as it may be redundant and confusing

    // FONTS
    private TrueTypeFont font;

    // GUI COMPONENTS
    private HintsBox hintsBox;
    private InfoBox infoBox;
    private MobsBox mobsBox;
    private MessageBox messageBox;
    private ArrayList<GuiComponent> guiComponents;

    private StringBuilder hint; // for control hints box

    public Game(String gameName, String[] args){
        super(gameName);

        vc = new ViewController(); // TODO rename to ScrollController?

        for(int i = 0; i < args.length; i++){
            System.out.println(args[i]);
            if(args[i].equalsIgnoreCase("-debug")){
                debug = true;
            }
        }

        if(debug){
            System.out.println("Launching in debug mode");
        } else {
            System.out.println("Launching in user mode");
        }

        try {

            AppGameContainer container = new AppGameContainer(this);
            container.setShowFPS(debug);
            container.setDisplayMode(Display.DISPLAY_WIDTH, Display.DISPLAY_HEIGHT, false);
            container.setVSync(true); // jesus h christ this needs to be on
            container.setTargetFrameRate(Display.FRAME_RATE); // TODO remove the /2 after testing screen scrolling
            container.start();

        } catch (SlickException e) {
            System.out.println("Failed to start the container");
            e.printStackTrace();
            System.exit(Game.EXIT_BAD);
        }

    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

        this.container = gameContainer;

        mobs = new ArrayList<Mob>(); // TODO make better. this shouldn't need to be here

        String shipPath = "res/ships/the_tortuga.csv"; // TODO make this non-static

        BufferedReader br = null;

        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(shipPath));
            String line = br.readLine();
            if(line != null){
                String[] strings = line.split(cvsSplitBy);
                System.out.println("Strings.length: " + strings.length);
                int x = Integer.parseInt(strings[0]);
                int y = Integer.parseInt(strings[1]);
                boolean[] tileBools = new boolean[x*y];
                for(int i = 0; i < (strings.length - 2); i++){
                    boolean isTraversible = (Integer.parseInt(strings[i+2]) != 0);
                    tileBools[i] = isTraversible;
                }
                map = new Map(x, y, tileBools);

            } else {
                System.err.println("Couldn't get ship design from file");
                System.exit(Game.EXIT_BAD);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");
        map.init();

        mouseX = 0;
        mouseY = 0;
        tick = 0;


        // initialising font from .ttf
        try {
//            InputStream inputStream	= ResourceLoader.getResourceAsStream("res/visitor2.ttf");
            InputStream inputStream	= ResourceLoader.getResourceAsStream("res/fonts/04b03.ttf");
            Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont2 = awtFont2.deriveFont(16f); // set font size
            font = new TrueTypeFont(awtFont2, false);

        } catch (Exception e) {
            e.printStackTrace();
        } // TODO set font again

    }

    @Override
    public void update(GameContainer container, int i) throws SlickException {

        // TODO work out a solid, efficient order
        pulse.update();

        // process hover checks
        processHoverChecks();

        // process keyboard input
        processKeyboardInput(container);

        // TODO some stuff with the room object. maybe move the room functionality over to its own method?

        if(!pause) {
            tick++;
        }

        if(tick > MAX_TICK){
            tick = 0;
        }

        if(tick == 0 && !won && !pause){ // TODO CONSIDER is the minute actions block in the right place, or should it be before everything else in the update method body?
            // MOVE AND DO ACTIONS
//            map.randomiseMobs();

            map.update(); // TODO move more stuff into this method

            // PROCESS DAMAGE TO MOBS
//            map.damageMobs();

            // CHECK GAME CONDITIONS // TODO more condition checks
            if(map.won()){
                won = true;
                System.out.println("All the parasites are dead. A winner is you!");
                // TODO tote up the score
                map.printScore();
            }

//            map.testDoors();

            // MOVE MOBS TO NEW TILES IF APPLICABLE
            map.moveMobs(); // TODO movements are random. give them intelligence // TODO consider placing this and damage in the same style, e.g map mobs

            // PRINT SOME STUFF

        }

    }

    @Override
    public void render(GameContainer gameContainer, Graphics screen) throws SlickException {

        screen.setFont(font);

        renderBackground(screen);

        renderTileBackgrounds(screen);

        renderGrid(screen);

        renderWalls(screen);

        renderMap(screen);

    }

    public ArrayList<Mob> getMobsByRoom(Room room){
        ArrayList<Mob> mobs = new ArrayList<Mob>();
        for (Iterator<Point> iterator = hoverRoom.getPoints().iterator(); iterator.hasNext(); ) { // TODO change this to use points
            Point point = iterator.next();
            for (Iterator<Mob> iterator1 = map.getMobs().iterator(); iterator1.hasNext(); ) {
                Mob mob = iterator1.next();
                if(mob.getPoint().equals(point)){
                    mobs.add(mob);
                }
            }
        }
        return mobs;
    }

    public void processHoverChecks(){

        // NULLIFY EXISTING VALUES FROM LAST LOOP
        hoverRoom = null;
        hoverDoor = null;
        hoverMob = null;

        // CLEAR SELECTION VARIABLE
        selection = NO_SELECTION;

        // GET MOUSE COORDINATE VALUES
        mouseX = Mouse.getX();
        mouseY = Math.abs(Mouse.getY() - Display.DISPLAY_HEIGHT); // corrects the mouse y coordinate

        // GET HOVER OBJECTS
        if((hoverMob = mobsBox.getMobMouseOver(mouseX, mouseY)) != null){
            if(Mob.TYPE_MATE == hoverMob.getType()){
                selection = NAUT_SELECTION;
            } else {
                selection = HOSTILE_SELECTION; // crosses? o lol
            }
        } else if((hoverDoor = map.getDoorMouseOver(mouseX, mouseY)) != null){
            selection = DOOR_SELECTION;
        } else if((hoverRoom = map.getRoomMouseOver(mouseX, mouseY)) != null){
            hoverRoom.select();
            selection = ROOM_SELECTION;
        }

    }

    public void processKeyboardInput(GameContainer container){ // TODO make this more efficient. any other controls?
        Input input = container.getInput();

        if(input.isKeyDown(Input.KEY_RSHIFT) || input.isKeyDown(Input.KEY_LSHIFT)) { // RSHIFT is the best, ergonomically
            shift = true;
        } else {
            shift = false;
        }

        if(input.isKeyPressed(Input.KEY_SPACE)){
            pause = !pause;
        }

        if(input.isKeyDown(Input.KEY_UP)){
            vc.increaseViewOffsetY();
        }
        if(input.isKeyDown(Input.KEY_DOWN)){
            vc.decreaseViewOffsetY();
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            vc.increaseViewOffsetX();
        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            vc.decreaseViewOffsetX();
        }

    }



    private void renderBackground(Graphics screen){
        // DRAW BACKGROUND
        screen.setBackground(Colours.GRID_BACKGROUND);
    }

    private void renderTileBackgrounds(Graphics screen){
        for (Iterator<Tile> iterator = map.visibleTiles.iterator(); iterator.hasNext(); ) {
            VisibleTile next = (VisibleTile) iterator.next();
            next.renderBackground(screen);
        }
    }

    private void renderGrid(Graphics screen){
        // DRAW GRID
        screen.setColor(Colours.GRID_LINES);
        screen.setLineWidth(Display.GRID_LINES);

        // draw horizontal lines going down
        for(int v = 0; v < Display.MAP_HEIGHT; v++){
            screen.drawLine(0, v * Display.TILE_WIDTH, Display.MAP_WIDTH, v * Display.TILE_WIDTH);
        }

        // draw vertical lines going across
        for(int h = 0; h < Display.MAP_WIDTH; h++){
            screen.drawLine(h * Display.TILE_WIDTH, 0, h * Display.TILE_WIDTH, Display.MAP_HEIGHT);
        }
    }

    private void renderWalls(Graphics screen){
        for (Iterator<Tile> iterator = map.visibleTiles.iterator(); iterator.hasNext(); ) {
            VisibleTile next = (VisibleTile) iterator.next();
            next.updateWalls(); // TODO put this somewhere else
            next.renderWalls(screen);
        }
    }

    private void renderMap(Graphics screen){
        // SET LINE WIDTH FOR DRAWING ANY LINES
        screen.setLineWidth(Display.LINE_WIDTH);
        map.render(screen); // TODO break down map rendering functionality into this class? code is all over the place
    }

    private void renderMobs(Graphics screen){
        // TODO
    }

    public static void main(String args[]){
//        random = new Random();
//        pulse = new Pulse();
//        Game game = new Game("Game Template", args);

        // Process arguments
        boolean debug = false;
        boolean disableMobs = false;

        for(int i = 0; i < args.length; i++){
            if(args[i].equalsIgnoreCase("-debug")){
                Game.debug = true;
            } else if(args[i].equalsIgnoreCase("-mobsOff")){
//                Game.disableMobs = true; // TODO fix
            }
        }

        // Initialise GameController
        GameController gameController = new GameController("Space Commander: Battlecruiser", debug, disableMobs);

    }

}
