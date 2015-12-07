package uk.co.jakestanley.commander2d.exceptions;

/**
 * Created by stanners on 24/05/2015.
 */
public class IllegalAction extends Exception {

    public IllegalAction(String reason){
        super("Attempted illegal action: " + reason);
    }

}
