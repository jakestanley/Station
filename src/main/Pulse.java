package main;

/**
 * Created by stanners on 12/07/2015.
 */
public class Pulse {

    private static final int PULSE_FLOOR = 20;
    private static final int PULSE_CEILING = 100;
    private static final int PULSE_INCREMENTS = 5;
    private int pulse;
    private boolean pulseUp;

    public Pulse() {
        pulse = PULSE_FLOOR;
        pulseUp = true;
    }

    public int getPulse() {
        return pulse;
    }

    public void update() {
        if (pulseUp) {
            if (pulse < PULSE_CEILING) {
                pulse = pulse + PULSE_INCREMENTS;
            } else {
                pulseUp = false;
                pulse = pulse - PULSE_INCREMENTS;
            }
        } else {
            if (pulse > PULSE_FLOOR) {
                pulse = pulse - PULSE_INCREMENTS;
            } else {
                pulseUp = true;
                pulse = pulse + PULSE_INCREMENTS;
            }

        }
    }
}
