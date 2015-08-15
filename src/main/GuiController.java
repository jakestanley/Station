package main;

import guicomponents.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import resources.FontLoader;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 21/07/2015.
 */
public class GuiController {

    private TrueTypeFont font;
    private ArrayList<GuiComponent> components;

    private HintsBox            hintsBox;
    private InfoBox             infoBox;
    private MobsBox             mobsBox;
    private MessageBox          messageBox;

    private StringBuilder hint;

    public GuiController(){
        components = new ArrayList<GuiComponent>();

        hintsBox = new HintsBox(hint);
        infoBox = new InfoBox();
        mobsBox = new MobsBox(GameController.mobController.getMobs()); // TODO reconsider how this works
        messageBox = new MessageBox();

        components.add(hintsBox);
        components.add(infoBox);
        components.add(mobsBox);
        components.add(messageBox);

        hint = new StringBuilder(Values.Strings.HINTS_WILL_APPEAR);

    }

    public void init(){
        font = FontLoader.loadFont("04b03.ttf");
    }

    public void addDialog(Dialog dialog){
        components.add(dialog);
    }

    public void setBackground(Graphics screen){
        screen.setBackground(Colours.GRID_BACKGROUND);
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
        for (Iterator<GuiComponent> iterator = components.iterator(); iterator.hasNext(); ) {
            GuiComponent next = iterator.next();
            if(next.isValid()){
                // if valid, update
                next.update();
            } else {
                // if invalid, destroy and remove from the list
                next.destroy();
                iterator.remove();
            }
        }
    }

    public void renderBackgrounds(Graphics screen){
        for (Iterator<GuiComponent> iterator = components.iterator(); iterator.hasNext(); ) {
            GuiComponent next =  iterator.next();
            next.renderBackground(screen);
        }
    }

    public void renderContent(Graphics screen){

        setFont(screen);

        for (Iterator<GuiComponent> iterator = components.iterator(); iterator.hasNext(); ) {
            GuiComponent next =  iterator.next();
            next.renderBody(screen);
        }

    }

    private void renderComponentsData(Graphics screen){ // TODO remove, incorporate, or break off into something else

//        // DRAW STRINGS
//        screen.setColor(Colours.GUI_TEXT);
//
//        // DRAW HOVER DOOR INFORMATION
//        if(hoverDoor != null){ // render hover door hover box
//            hoverDoor.renderDataBox(screen); // TODO move this into update, get hover stuff from gamecontroller or something
//        } else if(hoverRoom != null){
//            hoverRoom.renderDataBox(screen);
//        } else if(hoverMob != null){
//            hoverMob.renderDataBox(screen);;
//        }

    }

    private void renderHoverBoxes(Graphics screen){ // TODO tidy, etc

//        // DRAW HOVER BOXES
//        if(hoverDoor != null){ // render hover door hover box
//            hoverDoor.renderHoverBox(screen);
//        } else if(hoverRoom != null){
////            hoverRoom.renderHoverBox(screen, map.getViewOffsetX(), map.getViewOffsetY());
//        } else if(hoverMob != null){
//            hoverMob.renderHoverBox(screen);
//        }

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
