package main;

/**
 * Created by jake on 15/08/15.
 */
public class ContextController {

    public static final int CONSTRUCTION    = 0;
    public static final int DIALOG          = 1;

    private int context;

    public ContextController(int context){
        this.context = context;
    }

    public void setContext(int context){
        this.context = context;
    }

    public int getContext(){
        return context;
    }


}
