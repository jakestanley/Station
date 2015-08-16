package guicomponents;

import main.ContextController;
import main.Display;
import main.GameController;

import java.util.ArrayList;

/**
 * Created by jake on 15/08/15.
 */
public abstract class Dialog extends GuiFloating {

    public static boolean PAUSE = true;
    public static boolean NO_PAUSE = false;

    private boolean pause;

    public Dialog(boolean pause, int width, int height){
        super((Display.DISPLAY_WIDTH - width) / 2, (Display.DISPLAY_HEIGHT - height) / 2, width, height);

        // set context
        GameController.contextController.pushContext(ContextController.DIALOG); // TODO remove?

        // set variables
        this.pause = pause;
        this.widgets = new ArrayList<GuiWidget>();

        // pause the game if this is a pausing dialog and we're not in a multiplayer mode
        if(pause && !GameController.multiplayer){
            GameController.pause();
        }
    }

    @Override
    public void update() { // TODO some kind of interaction

    }

    @Override
    protected void onClose() {

        if(pause){ // TODO CONSIDER only try to unpause if this was a pauser. not sure about this as you could have multiple dialogs. need a hierarchy system
            GameController.unPause(); // TODO CONSIDER should it be the job of the Dialog to handle unpausing?
        }

        GameController.guiController.popFocus(); // TODO CONSIDER REVISING
        GameController.contextController.popContext();

    }

    @Override
    protected void setType(){
        this.type = TYPE_DIALOG;
    }

}
