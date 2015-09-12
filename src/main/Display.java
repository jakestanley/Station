package main;

import gui.widgets.Button;

/**
 * Created by stanners on 22/05/2015.
 */
public class Display { // TODO put these variables in some kind of order/categorisation that makes more sense

    public static int FRAME_RATE = 30;

    // DISPLAY CONSTANTS - OLD SCALING METHOD SYSTEM WAS CONVOLUTED AND STRESSFUL
    public static final boolean DEFAULT_WIDE = true;
    public static final int DEFAULT_WIDTH = 1366; // OLD WIDTH WAS 256
    public static final int DEFAULT_HEIGHT = 768; // OLD HEIGHT WAS 144
    public static final double FULLSCREEN_RIGHT_PANEL_DIVISOR = 2.666;
    public static final double WIDESCREEN_RIGHT_PANEL_DIVISOR = 3.557;

    // INTERFACE CONSTANTS
    public static final int TEXT_PANEL_HEIGHT = 32; // for control hints and mob panels

    public static final int ROOM_BOX_HEIGHT = 256; // TODO change to use percentages or something
    public static final int MOBS_BOX_HEIGHT = 362;
    public static final int MESSAGE_BOX_HEIGHT = 160; // SHOULD GIVE ROOM FOR FIVE MESSAGES

    // MAP CONSTANTS
    public static final int MAP_HEIGHT = 736;
    public static final int TILE_WIDTH = 32;
    public static final int DOOR_WIDTH = 16;
    public static final int MOB_WIDTH = 8; // TODO make this dynamic
    public static final int LINE_WIDTH = 2;
    public static final int GRID_LINES = 1;

    // INTERFACE STUFF
    public static final int MARGIN = 8;
    public static final int TEXT_SPACING = 16;

    // MOB PANEL POSITIONING
    public static final int HEALTH_BAR_HEIGHT = 16;
    public static final int HEALTH_BAR_WIDTH = 8;

    public static final int MOB_TEXT_MARGIN = MARGIN + HEALTH_BAR_WIDTH + MARGIN; // TODO fix so i don't have to divide by two

    private boolean wide;
    private int width, height, leftColumnWidth, rightColumnWidth; // TODO CONSIDER a refresh flag for changing dimensions

    public Display(){
        this.wide = DEFAULT_WIDE;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        init();
    }

    public Display(int width, int height, boolean wide){
        this.wide = wide;
        this.width = width;
        this.height = height;
        init();
    }

    public void init(){
        if(wide){
            rightColumnWidth = (int) Math.floor(width / WIDESCREEN_RIGHT_PANEL_DIVISOR);
        } else {
            rightColumnWidth = (int) Math.floor(width / FULLSCREEN_RIGHT_PANEL_DIVISOR);
        }
        leftColumnWidth = width - rightColumnWidth;
    }

    public int getRoomBoxY(){
        return 0;
    }

    public int getMobsBoxY(){ // for good practice
        return ROOM_BOX_HEIGHT;
    }

    public int getMessageBoxY(){ // assuming the order hasn't changed
        return ROOM_BOX_HEIGHT + MOBS_BOX_HEIGHT;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getLeftColumnWidth(){
        return leftColumnWidth;
    }

    public int getRightColumnWidth(){
        return rightColumnWidth;
    }

    public int getMapWidth(){
        return leftColumnWidth;
    }

    public int getMapHeight(){
        return height - (Button.MAX_HEIGHT * 2); // TODO improve
    }

}
