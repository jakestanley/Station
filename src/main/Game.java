package main;

import guicomponents.ControlHintsBox;
import guicomponents.GuiComponent;
import mobs.Mob;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.ResourceLoader;
import tiles.Tile;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by stanners on 22/05/2015.
 */
public class Game extends BasicGame {

    public static final int NO_SELECTION = 0;
    public static final int ROOM_SELECTION = 1;
    public static final int DOOR_SELECTION = 2; // TODO CONSIDER that this won't work until i sort out the overcrowding? revise.

    public static boolean debug = false;
    public static boolean pause = false;
    public static boolean won = false;
    public static int MAX_TICK = 30; // if this is lower than the highest animation loop length, shit will get fucked up
    public static Map map;
    public static Random random;

    // TODO sort and categorise variables
    private static boolean mouseOverRoom = true, shift = false;
    public int mouseX, mouseY, selection;
    private int tick;

    // ROOM STUFF
    private Room hoverRoom;
    private Door hoverDoor;
    private ArrayList<Mob> mobs;

    // INTERFACE STUFF
    private TrueTypeFont font;
    private ArrayList<GuiComponent> guiComponents;

    private StringBuilder hint; // for control hints box

    public Game(String gameName, String[] args){
        super(gameName);

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
            container.setDisplayMode(Display.DISPLAY_WIDTH * Display.SCALE, Display.DISPLAY_HEIGHT * Display.SCALE, false);
            container.setVSync(true); // jesus h christ this needs to be on
            container.setTargetFrameRate(Display.FRAME_RATE); // TODO remove the /2 after testing screen scrolling
            container.start();

        } catch (SlickException e) {
            System.out.println("Failed to start the container");
            e.printStackTrace();
            System.exit(0);
        }

    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

        hoverRoom = null;
        hoverDoor = null;
        mobs = new ArrayList<Mob>(); // TODO make better. this shouldn't need to be here

        initComponents();

        map = new Map();
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
        }

    }

    @Override
    public void update(GameContainer container, int i) throws SlickException {

        // TODO work out a solid, efficient order

        selection = NO_SELECTION; // wiping for new update

        mouseX = Mouse.getX() / Display.SCALE;
        mouseY = Math.abs((Mouse.getY() / Display.SCALE) - Display.DISPLAY_HEIGHT); // corrects the mouse y coordinate

        hoverDoor = map.getDoorMouseOver(mouseX, mouseY);
        if(hoverDoor != null){
            selection = DOOR_SELECTION;
        } else {
            hoverRoom = map.getRoomMouseOver(mouseX, mouseY);

            if(hoverRoom != null){
                selection = ROOM_SELECTION;
                mobs = getMobsByRoom(hoverRoom); // not in tick block as selection can change before tick
                if(!mouseOverRoom){
//                System.out.println("Mouse is over a room");
                    mouseOverRoom = !mouseOverRoom;
//                System.out.println("Mobs in this room: " + mobs.size());
                }
                // TODO prepare data for room box

            } else if(hoverRoom == null){ // TODO allow hovering over a door. prioritise door hover over room hover as door selection target is significantly smaller than that of rooms
                if(mouseOverRoom){
//                System.out.println("Mouse is no longer over a room");
                    mouseOverRoom = !mouseOverRoom;
                }
            }

        }

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

        updateComponents();

    }

    @Override
    public void render(GameContainer gameContainer, Graphics screen) throws SlickException {
        // SET BACKGROUND AND SCALE
        screen.setBackground(Colours.GRID_BACKGROUND);
        screen.scale(Display.SCALE, Display.SCALE); // TODO this should only happen once per render

        // DRAW GRID
        screen.setColor(Colours.GRID_LINES);
        screen.setLineWidth(1);
        // draw horizontal lines going down
        for(int v = 0; v < Display.MAP_HEIGHT; v++){
            screen.drawLine(0, v*Display.TILE_WIDTH, Display.MAP_WIDTH, v*Display.TILE_WIDTH);
        }
        // draw vertical lines going across
        for(int h = 0; h < Display.MAP_WIDTH; h++){
            screen.drawLine(h * Display.TILE_WIDTH, 0, h * Display.TILE_WIDTH, Display.MAP_HEIGHT);
        }

        // DRAW MAP
        screen.setLineWidth(Display.LINE_WIDTH);
        map.render(screen);

        // DRAW HOVER BOXES
        if(hoverDoor != null){ // render hover door hover box
            hoverDoor.renderHoverBox(screen);
        } else if(hoverRoom != null){
            hoverRoom.renderHoverBox(screen);
        }

        // TODO reorganise this method, it's so damn ugly

        // DRAW STRINGS
        screen.setColor(Color.white);
        screen.scale(0.25f, 0.25f); // TODO this should only happen once per render
        screen.setFont(font);

        if(hoverDoor != null){ // render hover door hover box
            hoverDoor.renderDataBox(screen);
        } else if(hoverRoom != null){
            hoverRoom.renderDataBox(screen);
        }

        int writeX = (Display.TILE_WIDTH + 2) * Display.SCALE;

        // DRAW HOVER ROOM DATA

        // TODO sort

        if (hoverRoom != null) {
            int type = hoverRoom.getType();
//            if (type == Room.CORRIDOR) { // TODO something else here
////                screen.drawString("Room: corridor", 10, 70);
//            } else if (type == Room.TYPE_SQUARE) {
////                screen.drawString("Room: square", 10, 70);
//            }
            // TODO make this scalable and not static - in its current state, it depends on max health being 100
            for(int i = 0; i < mobs.size(); i++){ // and here
                Mob mob = mobs.get(i);

                // get health values
                float mobHealth = mob.getHealth(); // TODO to int
                int mobHealthBarMod = (int) (Mob.MAX_HEALTH - mobHealth) / 5; // casting to integer for rough representation on bar // TODO ensure not below zero

                // initialising rectangles
                Rectangle baseRect = new Rectangle(  writeX + (writeX * i) / 2, writeX,
                                                     Display.HEALTH_BAR_HEIGHT / 5, Display.HEALTH_BAR_HEIGHT);

                Rectangle healthRect = new Rectangle(writeX + (writeX * i) / 2, writeX + mobHealthBarMod,
                                                     Display.HEALTH_BAR_HEIGHT / 5, Display.HEALTH_BAR_HEIGHT - mobHealthBarMod);

                // render base rect
                screen.setColor(Color.darkGray); // TODO access this from some value resource
                screen.fill(baseRect);

                // render fills
                if(mob.getType() == Mob.TYPE_MATE){
                    screen.setColor(Color.yellow);
                    screen.fill(healthRect); // actual health bar angle
                } else {
                    screen.setColor(Color.red);
                    screen.fill(healthRect); // actual health bar angle
                }

                // TODO render the gradient properly and accurately with non static/hard coded values
                // TODO clean all this shit up to be honest

            }
        }

        // SETTING HINT STRING // TODO CONSIDER own method?
        hint.setLength(0);
        if(shift){
            hint.append(Values.Strings.CONTROLS_SHIFT_DOOR);
        } else {
            if(selection == ROOM_SELECTION){
                hint.append(Values.Strings.CONTROLS_ROOM);
            } else if(selection == DOOR_SELECTION){
                hint.append(Values.Strings.CONTROLS_DOOR);
            } else {
                hint.append(Values.String.HINTS_WILL_APPEAR);
            }
        }

        // DRAW DEBUG STRINGS

        screen.setColor(Color.white);
        if(debug){
            screen.drawString("Mouse position: " + mouseX + ", " + mouseY, 10, (Display.MAP_HEIGHT * Display.SCALE) - 50);
            screen.drawString("Tick: " + tick, 10, (Display.MAP_HEIGHT * Display.SCALE) - 70);
        }

        // TODO draw interfaces

        // DRAW COMPONENTS
        renderComponents(screen);

    }

    public ArrayList<Mob> getMobsByRoom(Room room){
        ArrayList<Mob> mobs = new ArrayList<Mob>();
        for (Iterator<Tile> iterator = hoverRoom.getRoomTiles().iterator(); iterator.hasNext(); ) {
            Tile tile = iterator.next();
            for (Iterator<Mob> iterator1 = map.getMobs().iterator(); iterator1.hasNext(); ) {
                Mob mob = iterator1.next();
                if(mob.getX() == tile.getX() && mob.getY() == tile.getY()){ // !reference comparison. TODO values comparison for niceness. this is dirty but it works
                    mobs.add(mob);
                }
            }
        }
        return mobs;
    }

    public void processKeyboardInput(GameContainer container){ // TODO make this more efficient
        Input input = container.getInput();

        if(input.isKeyDown(Input.KEY_RSHIFT) || input.isKeyDown(Input.KEY_LSHIFT)) { // RSHIFT is the best, ergonomically
            shift = true;
        } else {
            shift = false;
        }

        if(input.isKeyPressed(Input.KEY_P)){
            pause = !pause;
        }

        if(input.isKeyPressed(Input.KEY_Q)){
            if(shift){
                for (Iterator<Door> iterator = map.getDoors().iterator(); iterator.hasNext(); ) {
                    Door next =  iterator.next();
                    next.qPress();
                }
            } else {
                if(hoverDoor != null){
                    hoverDoor.qPress();
                } else if(hoverRoom != null) {
                    hoverRoom.qPress();
                }
            }
        }
        if(input.isKeyPressed(Input.KEY_W)){
            if(shift){
                for (Iterator<Door> iterator = map.getDoors().iterator(); iterator.hasNext(); ) {
                    Door next =  iterator.next();
                    next.wPress();
                }
            } else {
                if(hoverDoor != null){
                    hoverDoor.wPress();
                } else if(hoverRoom != null) {
                    hoverRoom.wPress();
                }
            }
        }
        if (input.isKeyPressed(Input.KEY_E)){
            if(shift){
                for (Iterator<Door> iterator = map.getDoors().iterator(); iterator.hasNext(); ) {
                    Door next =  iterator.next();
                    next.ePress();
                }
            } else {
                if(hoverDoor != null){
                    hoverDoor.ePress();
                } else if(hoverRoom != null) {
                    hoverRoom.ePress();
                }
            }
        }
        if(input.isKeyPressed(Input.KEY_R)){
            if(shift){
                for (Iterator<Door> iterator = map.getDoors().iterator(); iterator.hasNext(); ) {
                    Door next =  iterator.next();
                    next.rPress();
                }
            } else {
                if(hoverDoor != null){
                    hoverDoor.rPress();
                } else if(hoverRoom != null){
                    hoverRoom.rPress();
                }
            }
        }
        if(input.isKeyPressed(Input.KEY_V)){ // TODO should shift press be used here?
            if(hoverDoor != null){
                hoverDoor.vPress();
            } else if(hoverRoom != null){
                hoverRoom.vPress();
            }
        }
    }

    private void initComponents(){
        // TODO

        guiComponents = new ArrayList<GuiComponent>();

        // control hints box
        hint = new StringBuilder(Values.Strings.HINTS_WILL_APPEAR);
        guiComponents.add(new ControlHintsBox(hint));

        // TODO room tile data box

        // TODO crew info box

        // TODO message box

    }

    private void updateComponents(){
        for (Iterator<GuiComponent> iterator = guiComponents.iterator(); iterator.hasNext(); ) {
            GuiComponent next = iterator.next();
            next.update();
        }
    }

    private void renderComponents(Graphics screen){
        for (Iterator<GuiComponent> iterator = guiComponents.iterator(); iterator.hasNext(); ) {
            GuiComponent next =  iterator.next();
            next.render(screen);
        }
    }

    public static void main(String args[]){
        random = new Random();
        Game game = new Game("Game Template", args);
    }

}
