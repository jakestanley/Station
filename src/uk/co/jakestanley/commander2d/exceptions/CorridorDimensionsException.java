package uk.co.jakestanley.commander2d.exceptions;

/**
 * Created by stanners on 22/05/2015.
 */
public class CorridorDimensionsException extends Exception {

    public CorridorDimensionsException(int x, int y){
        super("Corridor dimensions incompatible. At least one of value must equal 1. You tried [" + x + ", " + y + "]");
    }

}
