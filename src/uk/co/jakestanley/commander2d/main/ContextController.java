package uk.co.jakestanley.commander2d.main;

import java.util.Stack;

/**
 * Created by jake on 15/08/15.
 */
public class ContextController { // TODO contexts need to be more than just ints

    public static final int GENERAL         = 0;
    public static final int BUILD_ROOM      = 1;
    public static final int BUILD_PLACE     = 2;
    public static final int DIALOG          = 3;
    public static final int TEXT_INPUT      = 4;

    private Stack<Integer> contexts;

    public ContextController(int context){
        contexts = new Stack<Integer>();
        contexts.push(context);
    }

    public void pushContext(int context){
        contexts.push(context);
    }

    public void popContext(){
        contexts.pop();
    }

    /**
     * Remove previous context and add a new one. For when the previous context can be discarded.
     * @param context
     */
    public void replaceContext(int context){
        popContext();
        pushContext(context);
    }

    public int getContext(){
        return contexts.peek();
    }


}
