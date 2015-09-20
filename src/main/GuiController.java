package main;

import gui.Component;
import gui.HintsBox;
import gui.actions.SwitchViewAction;
import gui.inspectors.BuildInspector;
import gui.inspectors.GeneralInspector;
import gui.inspectors.Inspector;
import gui.inspectors.PlaceInspector;
import gui.widgets.Button;
import gui.widgets.ButtonRow;
import mobs.Mob;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import resources.FontLoader;

import java.awt.*;
import java.util.*;

/**
 * Created by stanners on 21/07/2015.
 */
public class GuiController {

    // component reference constants for hashmap
    public static final String MENU                 = "MENU";
    public static final String HINTS                = "HINTS";
    public static final String INSPECTOR_GENERAL    = "INSPECTOR_GENERAL";
    public static final String INSPECTOR_BUILD      = "INSPECTOR_BUILD";
    public static final String INSPECTOR_PLACE      = "INSPECTOR_PLACE"; // TODO use a better structure

    // font
    private TrueTypeFont font;
    private TrueTypeFont osFont;

    // Accessed objects
    private Door hoverDoor;
    private Room hoverRoom;
    private Mob hoverMob; // TODO re-implement better

    // Components
    private Map<String, Component> components;
    private ArrayList<Component> visible;
    private Stack<Component> focuses;
    private StringBuilder hint;

    public GuiController(){

        // construct maps and lists
        components = new HashMap<String, Component>();
        visible = new ArrayList<Component>();

        // construct gui components

        // build the menu TODO make a separate class for this stuff
        ButtonRow menu = new ButtonRow(null, 0, 0, GameController.display.getWidth(), Button.MAX_HEIGHT);
        menu.addButton(new Button(menu, "GENERAL", new SwitchViewAction(ContextController.GENERAL))); // TODO strings/translations class
        menu.addButton(new Button(menu, "BUILD", new SwitchViewAction(ContextController.BUILD_ROOM)));
        menu.addButton(new Button(menu, "BUY & SELL", new SwitchViewAction(ContextController.BUILD_PLACE)));
        menu.addButton(new Button(menu, "MISSIONS", new SwitchViewAction(ContextController.GENERAL)));
        menu.addButton(new Button(menu, "CREW", new SwitchViewAction(ContextController.GENERAL)));
        menu.addButton(new Button(menu, "SYSTEM", new SwitchViewAction(ContextController.GENERAL)));
        menu.addButton(new Button(menu, "BUDGET", new SwitchViewAction(ContextController.GENERAL)));
//        menu.addButton(new Button(menu, "DANGER ZONE", new SwitchViewAction(VIEW_GENERAL)));

        // construct the inspector and hints box
        Inspector generalInspector          = new GeneralInspector();
        Inspector buildRoomInspector        = new BuildInspector();
        Inspector placeObjectsInspector     = new PlaceInspector();
        hint = new StringBuilder(Values.Strings.HINTS_WILL_APPEAR);
        HintsBox hints = new HintsBox(null, hint);

        // populate the hashmap
        components.put(MENU, menu);
        components.put(INSPECTOR_GENERAL, generalInspector);
        components.put(INSPECTOR_BUILD, buildRoomInspector);
        components.put(INSPECTOR_PLACE, placeObjectsInspector);
        components.put(HINTS, hints);

        // add the initially visible parent elements
        visible.add(menu);
        visible.add(generalInspector);
        visible.add(hints);

//        messages = new MessageBox(null, 0); // TODO figure out what's going on here
        focuses     = new Stack<Component>();

        // initialise object points
        hoverDoor = null;
        hoverRoom = null;
        hoverMob = null;

    }

    public void init(){

        // iterating over a hash map. yeah.
        for(Map.Entry<String, Component> entry : components.entrySet()){
            entry.getValue().init();
        }

        // load font
        font = FontLoader.loadFont("04b03.ttf"); // TODO remove other mention of font
    }

    public void setBackground(Graphics screen){
        screen.setBackground(Colours.GRID_BACKGROUND);
    }

    public void renderGrid(Graphics screen){

        screen.setColor(Colours.GRID_LINES); // TODO fix these values
        screen.setLineWidth(Display.GRID_LINES);

        // draw horizontal lines
        for(int v = 0; v < GameController.display.getMapHeight(); v++){
            screen.drawLine(0, v * Display.TILE_WIDTH, GameController.display.getMapWidth(), v * Display.TILE_WIDTH);
        }

        // draw vertical lines
        for(int h = 0; h < GameController.display.getMapWidth(); h++){
            screen.drawLine(h * Display.TILE_WIDTH, 0, h * Display.TILE_WIDTH, Button.MAX_HEIGHT + GameController.display.getMapHeight() + 16); // TODO improve
        }

    }

    public void updateContent(){

        hoverDoor = GameController.mapController.getHoverDoor();
        hoverRoom = GameController.mapController.getHoverRoom();
//        hoverMob = mobsBox.getMobMouseOver(GameController.mouseController.getMouse()); // TODO uncomment

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

//        for (Iterator<GuiContainer> iterator = containers.iterator(); iterator.hasNext(); ) {
//            GuiContainer next = iterator.next();
//            if(next.isValid()){
//                // if valid, update
//                next.update();
//            } else {
//                // if invalid, destroy and remove from the list
//                iterator.remove();
//            }
//        }
    }

    public void render(Graphics screen){

        setFont(screen); // TODO use more than one font

        for (Iterator<Component> iterator = visible.iterator(); iterator.hasNext(); ) {
            Component next = iterator.next();

            try {
                next.render(screen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public TrueTypeFont getFont(){
        return font;
    }

    public void pushFocus(Component focus){
        focuses.push(focus);
    }

    public void popFocus(){
        if(focuses.size() > 0){
            focuses.pop();
        }
    }

    public Component getFocus() throws EmptyStackException {
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
            hoverMob.renderDataBox(screen);
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

    public void switchView(int view) throws Exception {
        visible.clear();
        setCommonView(); // TODO consider ViewController class
        switch (view){
            case ContextController.GENERAL:
                setGeneralView();
                GameController.contextController.replaceContext(ContextController.GENERAL);
                break;
            case ContextController.BUILD_ROOM:
                setBuildView();
                GameController.contextController.replaceContext(ContextController.BUILD_ROOM);
                break;
            case ContextController.BUILD_PLACE:
                GameController.contextController.replaceContext(ContextController.BUILD_PLACE);
                setPlaceView();
                break;
            default:
                throw new Exception("Bad view int passed to switchView");
        }
    }

    /**
     * For sending clicks to non-map components and informing the caller that a click was registered by a component.
     * @param mouse
     * @return
     */
    public boolean click(Point mouse){
        for (Iterator<Component> iterator = visible.iterator(); iterator.hasNext(); ) {
            Component next = iterator.next();
            if(next.isMouseOver(mouse)){
                System.out.println("gui click");
                next.click(mouse);
                return true;
            }
        }
        return false;
    }

    /**
     * For sending hovers to non-map components and informing the caller that a click was registered by a component.
     * @param mouse
     * @return
     */
    public boolean hover(Point mouse){
        for (Iterator<Component> iterator = visible.iterator(); iterator.hasNext(); ) {
            Component next = iterator.next();
            if(next.isMouseOver(mouse)){
                next.hover(mouse);
                return true;
            }
        }
        return false;
    }

    public void setCommonView(){
        visible.add(components.get(MENU));
        visible.add(components.get(HINTS));
    }

    public void setGeneralView(){
        GameController.mapController.clearSelection();
        visible.add(components.get(INSPECTOR_GENERAL));
    }

    public void setBuildView(){
        visible.add(components.get(INSPECTOR_BUILD));
    }

    public void setPlaceView(){
        GameController.mapController.clearSelection();
        visible.add(components.get(INSPECTOR_PLACE));
    }

}
