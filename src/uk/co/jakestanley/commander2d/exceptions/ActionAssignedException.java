package uk.co.jakestanley.commander2d.exceptions;

/**
 * Created by stanners on 05/08/2015.
 */
public class ActionAssignedException extends Exception {

    public ActionAssignedException(){
        System.out.println("Mob tried owning an action that was already owned");
    }

}
