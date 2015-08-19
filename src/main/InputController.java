package main;

import guicomponents.*;
import guicomponents.Dialog;
import guicomponents.dialogs.Dialog_CreateRoom;
import guicomponents.widgets.*;
import guicomponents.widgets.TextField;
import map.MapController;
import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.awt.*;

/**
 * Actions container input by sending it to relevant controllers. It is not meant to perform heavy business logic.
 * Created by stanners on 21/07/2015.
 */
public class InputController implements KeyListener {

    private boolean leftMouseHeld = false; // TODO consider making this un-static

    public InputController(){

    }

    public void processInput(GameContainer container){ // TODO replace
        Input input = container.getInput(); // TODO tidy up and remove
        MapController mc = GameController.mapController;
        GuiController gc = GameController.guiController; // TODO sort
        ContextController cc = GameController.contextController;

        // get focused component and context for deciding where input should be sent
        GuiComponent focus  = gc.getFocus();
        int context         = cc.getContext();

        if(focus != null && GuiComponent.TYPE_TEXTFIELD == focus.getType()){
            TextField field = (TextField) focus;
            if(input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_ESCAPE)){
                gc.popFocus();
            }
        }

        // if in construction context
        if(GameController.contextController.getContext() == ContextController.CONSTRUCTION){
            if(input.isKeyDown(Input.KEY_ENTER)){
                Dialog_CreateRoom dialog = new Dialog_CreateRoom();
                GameController.guiController.addContainer(dialog);
                GameController.guiController.pushFocus(dialog);
            }

            if(input.isKeyDown(Input.KEY_ESCAPE)){
                mc.clearSelection();
            }
        }


        processViewControllerControls(input);
        processClicks(input);
    }

    private void processViewControllerControls(Input input){ // TODO make this more efficient. any other controls?

        ViewController vc = GameController.viewController;

        // move the view
        if(input.isKeyDown(Input.KEY_UP)){
            vc.increaseViewOffsetY();
        }
        if(input.isKeyDown(Input.KEY_DOWN)){
            vc.decreaseViewOffsetY();
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            vc.increaseViewOffsetX();
        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            vc.decreaseViewOffsetX();
        }

    }

    private void processClicks(Input input){

        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            Point mousePoint = new Point(input.getMouseX(), input.getMouseY());
            if (!leftMouseHeld) {
                leftMouseHeld = true;
                GameController.mouseController.setClickPoint(mousePoint);
            }
        } else {
            if (leftMouseHeld) {
                leftMouseHeld = false;
                GameController.mouseController.setMouseRelease();
            }
        }

        GameController.mouseController.setHoverPoint(new Point(input.getMouseX(), input.getMouseY())); // TODO use this in more places
    }

    @Override
    public void keyPressed(int i, char c) { // TODO CONSIDER using this to handle key presses instead?

        // this is getting messy TODO clean up
        GuiController gc = GameController.guiController;

        // get focus
        GuiComponent focus = gc.getFocus();

        // if a text field is selected, send the input there
        if(focus != null && GuiComponent.TYPE_TEXTFIELD == focus.getType()){

            System.out.println("fhuef");
            TextField field = (TextField) focus;

            if(Input.KEY_ENTER == i || Input.KEY_ESCAPE == i){
                gc.popFocus();
            } else if(Input.KEY_DELETE == i) {
                System.out.println("backspace");
                field.deleteLetter();
            } else {
                field.addLetter(Character.toString(c));
            }
        }

    }

    @Override
    public void keyReleased(int i, char c) {

    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
