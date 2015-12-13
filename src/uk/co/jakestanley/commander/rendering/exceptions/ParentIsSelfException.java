package uk.co.jakestanley.commander.rendering.exceptions;

/**
 * Created by jake on 13/12/2015.
 */
public class ParentIsSelfException extends Exception {

    public ParentIsSelfException(){
        super("Cannot set parent to self");
    }

}
