package exceptions;

/**
 * Created by stanners on 12/09/2015.
 */
public class ComponentChildSizeException extends Exception {

    public ComponentChildSizeException(){
        super("Tried to add a child element that was outside the boundaries of the parent");
    }

}
