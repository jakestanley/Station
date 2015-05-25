package main;

/**
 * Created by stanners on 22/05/2015.
 */
public class Values {

    public static String names[] = {"Jefferson", "Washington", "Bush", "Wilson", "Clinton", "Franklin", "Kennedy",
                                    "Nixon", "Ronald", "Ford", "Carter", "Johnson", "Teddy", "Grant", "Lincoln",
                                    "Adams", "Jackson"};

    public class Types {

        public static final int VOID            = 0; // TODO add more for edges, etc
        public static final int CORRIDOR_X      = 1;
        public static final int CORRIDOR_Y      = 2;
        public static final int BRIDGE          = 3; // TODO bridge must be at the centre, or a reasonable distance from the parasite spawn
        public static final int LIFESUPPORT     = 4; // very important room. safety and wellbeing of the crew alarmingly compromised if this is damaged. some stations tend to have a backup system.
        public static final int HANGAR          = 5; // big room, similar to cargo hold. values are different for names only, really
        public static final int TELEPORT        = 6; // small room with lots of dangerous equipment
        public static final int CARGOHOLD       = 7; // big room full of not very much. slow to drain oxygen from
        public static final int ESCAPEPOD       = 8; // TODO escape pods have to be at the edge and can only have one entrance
        public static final int CAFETERIA       = 9; // large room in which many crew congregate
        public static final int KITCHEN         = 10; // TODO consider having cafeteria only
        public static final int BATHROOM        = 11; // TODO consider making this a part of quarters?
        public static final int QUARTERS        = 12; // TODO make more appropriate room types
        public static final int GENERIC         = 13;
        public static final int INVIGORATOR     = 14;

        // META DEBUG TYPES
        public static final int META_CORRIDOR_POINT = 70;

    }

    public class Dimensions {

        // BRIDGE (SQUARE)
        public static final int BRIDGE = 4;

        // INVIGORATOR (SQUARE)
        public static final int INVIGORATOR = 2;

        // CAFETERIA (SQUARE)
        public static final int CAFETERIA = 6;

        // HANGAR X
        public static final int HANGAR_X_X = 8;
        public static final int HANGAR_X_Y = 4;

        // HANGAR Y
        public static final int HANGAR_Y_X = 4;
        public static final int HANGAR_Y_Y = 8;

        // LIFE SUPPORT X
        public static final int LIFE_SUPPORT_X_X = 2;
        public static final int LIFE_SUPPORT_X_Y = 1;

        // LIFE SUPPORT Y
        public static final int LIFE_SUPPORT_Y_X = 1;
        public static final int LIFE_SUPPORT_Y_Y = 2;

    }

    public class Strings {

        public static final String CONTROLS_ROOM = "Q: purge | W: evac | E: priority- | R - priority+ | V - oxygen";
        public static final String CONTROLS_SHIFT_ROOM = ""; // TODO shift controls for room
        public static final String CONTROLS_DOOR = "Q: open | W: close | E: auto | R: bulkhead";
        public static final String CONTROLS_SHIFT_DOOR = "Q: open | W: close | E: auto | R: bulkhead [shift: all]";

    }

}
