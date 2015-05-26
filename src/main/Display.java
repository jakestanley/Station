package main;

/**
 * Created by stanners on 22/05/2015.
 */
public class Display {

    public static int SCALE = 4;
    public static int FRAME_RATE = 30;

    // DISPLAY CONSTANTS // TODO CONSIDER a column based system?
    public static final int DISPLAY_WIDTH = 256;
    public static final int DISPLAY_HEIGHT = 144;

    // INTERFACE CONSTANTS
    public static final int CONTROL_HINTS_BOX_HEIGHT = 8;
    public static final int CONTROL_HINTS_BOX_WIDTH = 160;

    public static final int MESSAGE_BOX_HEIGHT = 40; // SHOULD GIVE ROOM FOR FIVE MESSAGES
    public static final int MESSAGE_BOX_WIDTH = 96; // TODO make these more responsive, possibly by using that column system?

    // MAP CONSTANTS
    public static final int MAP_WIDTH = 160;
    public static final int MAP_HEIGHT = 136;
    public static final int TILE_WIDTH = 8;
    public static final int DOOR_WIDTH = 4;
    public static final int MOB_WIDTH = 2; // TODO make this dynamic
    public static final int LINE_WIDTH = 2;

    // INTERFACE STUFF
    public static final int HEALTH_BAR_HEIGHT = 20;
    public static final int TEXT_SPACING = 16;

}
