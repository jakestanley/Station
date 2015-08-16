package guicomponents.dialogs;

import guicomponents.Button;
import guicomponents.Dialog;
import org.newdawn.slick.Graphics;

/**
 * Created by jake on 16/08/15.
 */
public class Dialog_CreateRoom extends Dialog {

    public static final int BUTTON_OK       = 0;
    public static final int BUTTON_CANCEL   = 1;

    public Dialog_CreateRoom(){
        super(Dialog.PAUSE, 300, 200);
        widgets.add(new Button(this, 1, 1, "OK", BUTTON_OK));
        widgets.add(new Button(this, 1, 1, "Cancel", BUTTON_CANCEL));
    }

    @Override
    public void init() {

    }

    @Override
    public void renderContent(Graphics screen) {

    }

    @Override
    public void widgetClicked(int index) {
        if(BUTTON_OK == index){
            // TODO complete room creation
            destroy();
        } else if(BUTTON_CANCEL == index) {
            // TODO cancel room creation
            destroy();
        }
    }
}
