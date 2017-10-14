package uk.co.jakestanley.commander2d.planner;

import uk.co.jakestanley.commander2d.mobs.Mob;

/**
 * Created by stanners on 03/08/2015.
 */
public class DecisionEngine { // TODO CONSIDER that this is static

    public void evaluate(Mob mob){ // TODO return a uk.co.jakestanley.commander2d.planner
        // TODO panic action, e.g no action is available, e.g critical oxygen, critical health


    }

    public static class BaselinePriorities { // TODO CONSIDER that resilience should actually affect the critical oxygen/health thresholds, rather than these values

        public static final int CRITICAL_OXYGEN_ESCAPE = 100; // run to oxygen
        public static final int CRITICAL_HEALTH_ESCAPE = 100; // run to heal
//        public static final int

    }

}
