package gui.floating.dialog;

import contexts.ContextController;
import gui.floating.Floating;
import gui.widgets.ButtonRow;
import main.Game;
import main.GameController;

/**
 * Created by jake on 15/08/15.
 */
public abstract class Dialog extends Floating {

    public static boolean PAUSE = true;
    public static boolean NO_PAUSE = false;

    private boolean pause;
    private ButtonRow buttonRow;

    public Dialog(boolean pause, int width, int height){
        super((GameController.display.getWidth() - width) / 2, (GameController.display.getHeight() - height) / 2, width, height);

        // set context
        GameController.contextController.pushContext(ContextController.DIALOG); // TODO remove?

        // set variables
        this.pause = pause;

        // pause the game if this is a pausing dialog and we're not in a multiplayer mode
        if(pause && !Game.multiplayer){
            GameController.pause();
        }
    }

    @Override
    protected void onClose() {

        if(pause){ // TODO CONSIDER only try to unpause if this was a pauser. not sure about this as you could have multiple dialogs. need a hierarchy system
            GameController.unPause(); // TODO CONSIDER should it be the job of the Dialog to handle unpausing?
        }

        GameController.guiController.popFocus(); // TODO CONSIDER REVISING
        GameController.contextController.popContext();

    }
}
