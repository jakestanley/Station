package uk.co.jakestanley.commander2d.exceptions;

/**
 * Created by stanners on 30/05/2015.
 */
public class LongCorridorGeneration extends Exception {

    public LongCorridorGeneration(){
        super("Took way too long to generate corridors");
    }

}
