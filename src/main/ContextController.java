package main;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by jake on 15/08/15.
 */
public class ContextController {

    public static final int CONSTRUCTION    = 0;
    public static final int DIALOG          = 1;

    private Stack<Integer> contexts; // TODO CONSIDER context stack

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

    public int getContext(){
        return contexts.peek();
    }


}
