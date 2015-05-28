package main;

import guicomponents.*;
import mobs.Mob;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
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
    public static final int NAUT_SELECTION = 3;
    public static final int HOSTILE_SELECTION = 4;

    public static boolean debug = false;
    public static boolean pause = false;
    public static boolean won = false;
    public static int MAX_TICK = 30; // if this is lower than the highest animation loop length, shit will get fucked up
    public static GameContainer container;
    public static Map map;
    public static Random random;

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
    private ControlHintsBox hintsBox;
    private RoomBox roomBox;
    private MobsBox mobsBox;
    private MessageBox messageBox;
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
            container.setDisplayMode(Display.DISPLAY_WIDTH, Display.DISPLAY_HEIGHT, false);
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

        this.container = gameContainer;

        mobs = new ArrayList<Mob>(); // TODO make better. this shouldn't need to be here

        map = new Map();
        map.init();

        mouseX = 0;
        mouseY = 0;
        tick = 0;

        initComponents();


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

        // RENDER HINTS TEXT
        hint.setLength(0);
        if(shift){ // TODO check that it's cleared first.
            hint.append(Values.Strings.CONTROLS_SHIFT_DOOR);
        } else {
            if(selection == ROOM_SELECTION){
                hint.append(Values.Strings.CONTROLS_ROOM);
            } else if(selection == DOOR_SELECTION){
                hint.append(Values.Strings.CONTROLS_DOOR);
            } else if(selection == NAUT_SELECTION){
                hint.append(Values.Strings.CONTROLS_NAUT);
            } else if(selection == HOSTILE_SELECTION){
                hint.append(Values.Strings.CONTROLS_HOSTILE);
            } else {
                hint.append(Values.Strings.HINTS_WILL_APPEAR); // TODO rename to all controls, or something
            }
        }

        updateComponents();

    }

    @Override
    public void render(GameContainer gameContainer, Graphics screen) throws SlickException {

        renderBackground(screen);

        renderMap(screen);

        renderHoverBoxes(screen);

        renderComponents(screen);

        renderComponentsData(screen);

        renderDebugComponents(screen);



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
                selection = HOSTILE_SELECTION;
            }
        } else if((hoverDoor = map.getDoorMouseOver(mouseX, mouseY)) != null){
            selection = DOOR_SELECTION;
        } else if((hoverRoom = map.getRoomMouseOver(mouseX, mouseY)) != null){
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

        // INITIALISE COMPONENT LIST
        guiComponents = new ArrayList<GuiComponent>();

        // INITIALISE HINT STRING
        hint = new StringBuilder(Values.Strings.HINTS_WILL_APPEAR);

        // INITIALISE COMPONENTS
        hintsBox = new ControlHintsBox(hint);
        roomBox = new RoomBox();
        mobsBox = new MobsBox(map.getMobs(), Game.container.getInput());
        messageBox = new MessageBox();

        // POPULATE COMPONENT LIST
        guiComponents.add(hintsBox = new ControlHintsBox(hint));
        guiComponents.add(roomBox = new RoomBox());
        guiComponents.add(mobsBox = new MobsBox(map.getMobs(), Game.container.getInput()));
        guiComponents.add(messageBox = new MessageBox());

    }

    private void updateComponents(){
        for (Iterator<GuiComponent> iterator = guiComponents.iterator(); iterator.hasNext(); ) {
            GuiComponent next = iterator.next();
            next.update();
        }
    }

    private void renderBackground(Graphics screen){

        // DRAW BACKGROUND
        screen.setBackground(Colours.GRID_BACKGROUND);

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

    private void renderMap(Graphics screen){
        // SET LINE WIDTH FOR DRAWING ANY LINES
        screen.setLineWidth(Display.LINE_WIDTH);
        map.render(screen); // TODO break down map rendering functionality into this class? code is all over the place
    }

    private void renderRooms(Graphics screen){
        // TODO
    }

    private void renderMobs(Graphics screen){
        // TODO
    }

    private void renderHoverBoxes(Graphics screen){ // TODO tidy, etc

        // DRAW HOVER BOXES
        if(hoverDoor != null){ // render hover door hover box
            hoverDoor.renderHoverBox(screen);
        } else if(hoverRoom != null){
            hoverRoom.renderHoverBox(screen);
        }

        // TODO reorganise this method, it's so damn ugly

        // DRAW STRINGS
        screen.setColor(Color.white);
//        screen.scale(0.25f, 0.25f); // TODO this should only happen once per render
        screen.setFont(font);

        if(hoverDoor != null){ // render hover door hover box
            hoverDoor.renderDataBox(screen);
        } else if(hoverRoom != null){
            hoverRoom.renderDataBox(screen);
        }

        int writeX = Display.TILE_WIDTH + Display.MARGIN;

        // DRAW HOVER ROOM DATA

        // TODO sort

        if (hoverRoom != null) {
            int type = hoverRoom.getType();
//            if (type == Room.CORRIDOR) { // TODO something else here
////                screen.drawString("Room: corridor", 10, 70);
//            } else if (type == Room.TYPE_SQUARE) {
////                screen.drawString("Room: square", 10, 70);
//            }
        }
    }

    private void renderComponents(Graphics screen){ // GUI // TODO CONSIDER renaming? // TODO should be for backgrounds and borders only, so we can turn it off easily in case something goes wrong but we still want the overlaid stuff.

        // render components
        for (Iterator<GuiComponent> iterator = guiComponents.iterator(); iterator.hasNext(); ) {
            GuiComponent next =  iterator.next();
            next.render(screen);
        }
    }

    private void renderComponentsData(Graphics screen){ // TODO is it needed? yes, i need to call the render methods from here, or do i?

        // TODO

    }

    private void renderDebugComponents(Graphics screen){

        // check that we're in production mode
        if(debug){
            // DRAW DEBUG STRINGS
            screen.setColor(Color.white); // TODO non-static colour

            screen.drawString("Mouse position: " + mouseX + ", " + mouseY, 10, Display.MAP_HEIGHT - 50); // TODO change hard coded value
            screen.drawString("Tick: " + tick, Display.MARGIN, Display.MAP_HEIGHT - 70); // TODO change to another non-hard coded value
        }

    }

    public static void main(String args[]){
        random = new Random();
        Game game = new Game("Game Template", args);
    }

}
