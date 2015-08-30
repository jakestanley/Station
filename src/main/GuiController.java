package main;

import guicomponents.*;
import mobs.Mob;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import resources.FontLoader;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by stanners on 21/07/2015.
 */
public class GuiController {

    private TrueTypeFont        font;
    private HintsBox            hintsBox;

    private InfoBox             infoBox;
    private MobsBox             mobsBox;
    private MessageBox          messageBox;

    // Accessed objects
    private Door hoverDoor;
    private Room hoverRoom;
    private Mob hoverMob; // TODO re-implement

    // Components
    private ArrayList<GuiContainer> containers;
    private Stack<GuiComponent> focuses;

    private StringBuilder hint;

    public GuiController(){

        // initialise component lists
        containers = new ArrayList<GuiContainer>();
        focuses     = new Stack<GuiComponent>();

        // initialise object points
        hoverDoor = null;
        hoverRoom = null;
        hoverMob = null;

        hint = new StringBuilder(Values.Strings.HINTS_WILL_APPEAR);

    }

    public void init(){
        // initialise static gui elements
        hintsBox    = new HintsBox(hint);
        infoBox     = new InfoBox();
        mobsBox     = new MobsBox(GameController.mobController.getMobs()); // TODO reconsider how this works
        messageBox  = new MessageBox();

        // add static gui elements to containers
        containers.add(hintsBox);
        containers.add(infoBox);
        containers.add(mobsBox);
        containers.add(messageBox);

        // load font
        font = FontLoader.loadFont("04b03.ttf"); // TODO remove other mention of font
    }

    public void setBackground(Graphics screen){
        screen.setBackground(Colours.GRID_BACKGROUND);
    }

    public void addContainer(GuiContainer container){
        containers.add(container);
    }

    public void renderGrid(Graphics screen){

        screen.setColor(Colours.GRID_LINES); // TODO fix these values
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

    public void updateContent(){

        hoverDoor = GameController.mapController.getHoverDoor();
        hoverRoom = GameController.mapController.getHoverRoom();

        // UPDATE HINT STRING AND RENDER BOXES
        hint.setLength(0);
//        if(shift){ // TODO check that it's cleared first.
            hint.append(Values.Strings.CONTROLS_SHIFT_DOOR);
//        } else {
//            if(selection == ROOM_SELECTION){
//                hint.append(Values.Strings.CONTROLS_ROOM);
//                hoverRoom.populateDataBoxStrings();
//            } else if(selection == DOOR_SELECTION){
//                hint.append(Values.Strings.CONTROLS_DOOR);
//                hoverDoor.populateDataBoxStrings();
//            } else if(selection == NAUT_SELECTION){
//                hint.append(Values.Strings.CONTROLS_NAUT);
//                hoverMob.populateDataBoxStrings();
//            } else if(selection == HOSTILE_SELECTION){
//                hint.append(Values.Strings.CONTROLS_HOSTILE);
//                hoverMob.populateDataBoxStrings();
//            } else {
//                hint.append(Values.Strings.HINTS_WILL_APPEAR); // TODO rename to all controls, or something
//            }
//        }

        for (Iterator<GuiContainer> iterator = containers.iterator(); iterator.hasNext(); ) {
            GuiContainer next = iterator.next();
            if(next.isValid()){
                // if valid, update
                next.update();
            } else {
                // if invalid, destroy and remove from the list
                iterator.remove();
            }
        }
    }

    public void render(Graphics screen){

        screen.setFont(font);

        for (Iterator<GuiContainer> iterator = containers.iterator(); iterator.hasNext(); ) {
            GuiContainer next =  iterator.next();
            next.render(screen);
        }

        renderComponentsData(screen); // TODO come up with a better way to do this
    }

    public TrueTypeFont getFont(){
        return font;
    }

    public void pushFocus(GuiComponent focus){
        focuses.push(focus);
    }

    public void popFocus(){
        if(focuses.size() > 0){
            focuses.pop();
        }
    }

    public GuiComponent getFocus() throws EmptyStackException {
        try {
            return focuses.peek();
        } catch (EmptyStackException e){
            return null;
        }
    }

    private void renderComponentsData(Graphics screen){ // TODO remove, incorporate, or break off into something else

//        // DRAW STRINGS
        screen.setColor(Colours.GUI_TEXT);
//


//        // DRAW HOVER DOOR INFORMATION
        if(hoverDoor != null){ // render hover door hover box
            hoverDoor.renderDataBox(screen); // TODO move this into update, get hover stuff from gamecontroller or something
        } else if(hoverRoom != null){
            hoverRoom.populateDataBoxStrings();
            hoverRoom.renderDataBox(screen);
        } else if(hoverMob != null){
            hoverMob.renderDataBox(screen);;
        }

    }

    private void renderDebugComponents(Graphics screen){ // TODO

        screen.setColor(Color.white); // TODO non-static colour
        // screen.drawString("Mouse position: " + mouseX + ", " + mouseY, 10, Display.MAP_HEIGHT - 50); // TODO change hard coded value
        // screen.drawString("Tick: " + tick, Display.MARGIN, Display.MAP_HEIGHT - 70); // TODO change to another non-hard coded value

    }

    /**
     * Sets the font. If the font is null, then the default Slick font will be used.
     * @param screen
     */
    private void setFont(Graphics screen){ // TODO call in init
        if(font != null){
            screen.setFont(font);
        }
    }

}
