package guicomponents.dialogs;

import guicomponents.widgets.Button;
import guicomponents.Dialog;
import guicomponents.widgets.TextField;
import main.GameController;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by jake on 16/08/15.
 */
public class Dialog_CreateRoom extends Dialog {

    public static final int BUTTON_OK       = 0;
    public static final int BUTTON_CANCEL   = 1;
    public static final int TEXT_FIELD_NAME = 2;

    private String name;
    private Color colour;

    private Button buttonOk, buttonCancel;
    private TextField nameInput;

    public Dialog_CreateRoom(){ // TODO create room on OK
        super(Dialog.PAUSE, 300, 200);

        buttonOk = new Button(this, "OK", BUTTON_OK);
        buttonCancel = new Button(this, "Cancel", BUTTON_CANCEL);
        nameInput = new TextField(this, TEXT_FIELD_NAME);

        // add buttons
        buttons.add(buttonOk);
        buttons.add(buttonCancel);

        // add other widgets
        widgets.add(nameInput);
    }

    @Override
    public void init() {

    }

    @Override
    public void renderContent(Graphics screen) {
        screen.drawString("test string", x + 10, y + 10);
    }

    @Override
    public void widgetClicked(int index) {
        if(BUTTON_OK == index){
            GameController.mapController.createRoomFromSelection();
            destroy();
        } else if(BUTTON_CANCEL == index) {
            GameController.mapController.clearSelection();
            destroy();
        } else if(TEXT_FIELD_NAME == index){
            GameController.guiController.pushFocus(nameInput);
        }
    }
}
