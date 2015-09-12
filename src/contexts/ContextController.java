package contexts;

import java.util.Stack;

/**
 * Created by jake on 15/08/15.
 */
public class ContextController { // TODO contexts need to be more than just ints

    public static final int BUILD_ROOM      = 0;
    public static final int BUILD_PLACE     = 1;
    public static final int DIALOG          = 2;
    public static final int TEXT_INPUT      = 3;

    private Stack<Integer> contexts;

    public ContextController(int context){
        contexts = new Stack<Integer>();
        contexts.push(context);
    }

    public void pushContext(int context){
        contexts.push(context);
    }

    /**
     * Remove previous context and add a new one. For when the previous context can be discarded.
     * @param context
     */
    public void replaceContext(int context){
        popContext();
        pushContext(context);
    }

    public void popContext(){
        contexts.pop();
    }

    public int getContext(){
        return contexts.peek();
    }


}
