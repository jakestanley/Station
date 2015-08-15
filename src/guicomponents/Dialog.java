package guicomponents;

import main.ContextController;
import main.GameController;
import org.newdawn.slick.Graphics;

/**
 * Created by jake on 15/08/15.
 */
public class Dialog extends GuiComponent {

    private boolean pause;

    public Dialog(boolean pause){
        super(40, 40, 300, 300);

        GameController.contextController.setContext(ContextController.DIALOG);

        this.pause = pause;

        if(pause){
            GameController.pause();
        }
    }

    @Override
    public void update() { // TODO some kind of interaction

    }

    @Override
    public void renderBody(Graphics screen) {
        screen.drawString("hello", x+10, y+20);
    }

    @Override
    protected void onClose() {
        if(pause){ // TODO CONSIDER only try to unpause if this was a pauser. not sure about this as you could have multiple dialogs. need a hierarchy system
            GameController.unPause();
        }
    }
}
