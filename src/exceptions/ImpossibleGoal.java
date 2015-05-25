package exceptions;

/**
 * Created by stanners on 24/05/2015.
 */
public class ImpossibleGoal extends Exception {

    public ImpossibleGoal(String reason){
        super("Planner cannot be achieved: " + reason);
    }

}
