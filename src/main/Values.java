package main;

/**
 * Created by stanners on 22/05/2015.
 */
public class Values {

    public static String names[] = {"Jefferson", "Washington", "Bush", "Wilson", "Clinton", "Franklin", "Kennedy",
                                    "Nixon", "Ronald", "Ford", "Carter", "Johnson", "Teddy", "Grant", "Lincoln",
                                    "Adams", "Jackson"};

    public static int SEARCH_TIME_LIMIT = 250;

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
        public static final int META_BORDER = 70;

    }

    public class Attributes {

        // MENTAL STATES
        public static final int MENTAL_INDIFFERENT = 0;
        public static final int MENTAL_STARTLED = 1;
        public static final int MENTAL_WORRIED = 2;
        public static final int MENTAL_SCARED = 3;
        public static final int MENTAL_PANICKED = 4;
        public static final int MENTAL_FRIGHTENED = 5;
        public static final int MENTAL_HORRIFIED = 6;
        public static final int MENTAL_TERROR_STRICKEN = 7;
        public static final int MENTAL_STATE_MAX_LEVEL = 7;

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

        // HINTS
        public static final String CONTROLS_ROOM = "[room] Q: purge | W: evac | E: priority- | R - priority+ | V - oxygen";
        public static final String CONTROLS_DOOR = "[door] Q: open | W: close | E: auto | R: bulkhead";
        public static final String CONTROLS_NAUT = "[naut] jake needs to put naut order controls here"; // for now this is what the station staff will be called
        public static final String CONTROLS_HOSTILE = "[hostile] order controls here. should be ATTACK! or something.";
        public static final String CONTROLS_SHIFT_DOOR = "[all doors] Q: open | W: close | E: auto | R: bulkhead"; // TODO rename to just CONTROLS_SHIFT, OR HINTS_SHIFT?
        public static final String HINTS_WILL_APPEAR = "SPACE: pause | SHIFT: all doors | HOVER: control door or room";

        // MENTAL STATES
        public static final String MENTAL_INDIFFERENT = "indifferent";
        public static final String MENTAL_STARTLED = "startled";
        public static final String MENTAL_WORRIED = "worried";
        public static final String MENTAL_SCARED = "scared";
        public static final String MENTAL_PANICKED = "panicked";
        public static final String MENTAL_FRIGHTENED = "frightened";
        public static final String MENTAL_HORRIFIED = "horrified";
        public static final String MENTAL_TERROR_STRICKEN = "terror-stricken";

        public static final String CORRIDOR        = "corridor";
        public static final String BRIDGE          = "bridge";
        public static final String LIFESUPPORT     = "life support";
        public static final String HANGAR          = "hangar";
        public static final String TELEPORT        = "teleport";
        public static final String CARGOHOLD       = "cargo hold";
        public static final String ESCAPEPOD       = "escape pod";
        public static final String CAFETERIA       = "cafeteria";
        public static final String KITCHEN         = "kitchen";
        public static final String BATHROOM        = "toilet";
        public static final String QUARTERS        = "crew quarters";
        public static final String GENERIC         = "generic"; // TODO ?
        public static final String INVIGORATOR     = "invigorator";

        public static final String UNDEFINED       = "UNDEFINED";


    }

}
