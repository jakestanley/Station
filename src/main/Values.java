package main;

/**
 * Created by stanners on 22/05/2015.
 */
public class Values {

    public static String names[] = {"Jefferson", "Washington", "Bush", "Wilson", "Clinton", "Franklin", "Kennedy",
                                    "Nixon", "Ronald", "Ford", "Carter", "Johnson", "Teddy", "Grant", "Lincoln",
                                    "Adams", "Jackson"};

    public static int SEARCH_TIME_LIMIT = 10;
    public static int SEARCH_TIME_LIMIT_MAX = 100;
    public static int SEARCH_INCREMENT_TIME = 5; // seconds

    public class Types { // TODO remove unused rooms when necessary

        public static final int VOID                = 0; // TODO add more for edges, etc
        public static final int CORRIDOR_X          = 1; // deprecated TODO remove
        public static final int CORRIDOR_Y          = 2; // deprecated TODO remove
        public static final int BRIDGE              = 3; // TODO bridge must be at the centre, or a reasonable distance from the parasite spawn
        public static final int LIFESUPPORT         = 4; // very important room. safety and wellbeing of the crew alarmingly compromised if this is damaged. some stations tend to have a backup system.
        public static final int HANGAR              = 5; // big room, similar to cargo hold. values are different for names only, really
        public static final int TELEPORT            = 6; // small room with lots of dangerous equipment
        public static final int CARGO_BAY           = 7; // big room full of not very much. slow to drain oxygen from
        public static final int ESCAPE_POD          = 8; // TODO escape pods have to be at the edge and can only have one entrance
        public static final int CANTEEN             = 9; // large room in which many crew congregate
        public static final int KITCHEN             = 10; // TODO consider having cafeteria only
        public static final int BATHROOM            = 11; // TODO consider making this a part of quarters?
        public static final int QUARTERS            = 12; // larger quarters mean happier crew TODO make more appropriate room types
        public static final int GENERIC             = 13; // TODO use in place of corridor?
        public static final int MEDICAL             = 14;
        public static final int CORRIDOR            = 15;
        public static final int WEAPONS_CONTROL     = 16; // can only fire as many weapons as panels are administered at a time. more crew and a bigger weapons control module means more firepower!
        public static final int TURRET_MOUNT        = 17;
        public static final int TORPEDO_MOUNT       = 18;
        public static final int RCS_THRUSTER        = 19;
        public static final int ENGINE_ROOM         = 20;
        public static final int HYPERWARP_ROOM      = 21;
        public static final int REACTOR_ROOM        = 22;
        public static final int ARMOURY             = 23;
        public static final int SURVEILLANCE        = 24;
        public static final int PUB                 = 25; // leisure area
        public static final int SHIELD_GENERATOR    = 26;
        public static final int WARP_FUEL_TANK      = 27; // warp tank has to cool and refuel before it can launch again. it does not charge.
        public static final int REGULAR_FUEL_TANK   = 28; // there is no max speed.
        public static final int ELEVATOR            = 29;

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

    public class Dimensions { // TODO consider revising

        // BRIDGE (SQUARE)
        public static final int BRIDGE = 4; // TODO consider 5x5 or 3x3

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

    public static class Strings {

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

        public static final String UNDEFINED       = "UNDEFINED";

        // ROOMS
        public static final String[] rooms = {
                "void", "_corridor_x", "_corridor_y", "bridge", "life support", "hangar", "teleport", "cargo bay",
                "escape pod", "canteen", "kitchen", "bathroom", "quarters", "generic", "medical", "corridor",
                "weapons control", "turrent mount", "torpedo mount", "_rcs_thruster", "engine room", "hyperwarp room",
                "reactor room", "armoury", "surveillance", "pub", "shield generator", "warp fuel tank",
                "regular fuel tank", "elevator"
        };


    }

}
