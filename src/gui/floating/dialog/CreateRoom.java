package gui.floating.dialog;

import gui.widgets.Button;
import gui.widgets.TextField;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by jake on 16/08/15.
 */
public class CreateRoom extends Dialog {

    public static final int BUTTON_OK       = 0;
    public static final int BUTTON_CANCEL   = 1;
    public static final int TEXT_FIELD_NAME = 2;

    private String name;
    private Color colour;

    private Button buttonOk, buttonCancel;
    private TextField nameInput;

    public CreateRoom(){ // TODO create room on OK
        super(Dialog.PAUSE, 300, 200); // TODO

//        buttonOk = new Button(this, "OK", BUTTON_OK);
//        buttonCancel = new Button(this, "Cancel", BUTTON_CANCEL);
//        nameInput = new TextField(this, TEXT_FIELD_NAME);

        // add buttons
//        buttons.add(buttonOk);
//        buttons.add(buttonCancel);
//
//        // add other widgets
//        widgets.add(nameInput);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void clickAction() {

    }

    @Override
    public void hoverAction() {

    }

    @Override
    public void draw(Graphics screen) { // TODO draw parent render
        screen.drawString("test string", x + 10, y + 10);
    }

//    @Override
//    public void widgetClicked(int index) {
//        if(BUTTON_OK == index){
//            GameController.mapController.createRoomFromSelection();
//            destroy();
//        } else if(BUTTON_CANCEL == index) {
//            GameController.mapController.clearSelection();
//            destroy();
//        } else if(TEXT_FIELD_NAME == index){
//            GameController.guiController.pushFocus(nameInput);
//        }
//    }
}
