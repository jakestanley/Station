package uk.co.jakestanley.commander.rendering.exceptions;

/**
 * Created by jake on 13/12/2015.
 */
public class ChildIsSelfException extends Exception {

    public ChildIsSelfException(){
        super("Cannot set child to self");
    }

}
