package io;

import gui.Component;
import gui.widgets.TextField;
import main.*;
import map.MapController;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.awt.*;

/**
 * Actions container input by sending it to relevant controllers. It is not meant to perform heavy business logic.
 * Created by stanners on 21/07/2015.
 */
public class InputController implements KeyListener {

    private boolean leftMouseHeld = false; // TODO consider making this un-static TODO CONSIDER removing

    public InputController(){

    }

    public void processInput(GameContainer container){ // TODO replace
        Input input = container.getInput(); // TODO tidy up and remove
        MapController mc = GameController.mapController;
        GuiController gc = GameController.guiController; // TODO sort
        ContextController cc = GameController.contextController;

        // get focused component and context for deciding where input should be sent
        Component focus  = gc.getFocus();
        int context         = cc.getContext();

        // ESCAPE to have overriding functionality

//        if(focus != null && Component.TYPE_TEXTFIELD == focus.getType()){
//            TextField field = (TextField) focus;
//            if(input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_ESCAPE)){
//                gc.popFocus();
//            }
//        } else {
//            // do regular controls
//        }

        // if in construction context
        if(GameController.contextController.getContext() == ContextController.BUILD_ROOM){
            if(input.isKeyDown(Input.KEY_ENTER)){
                mc.createRoomFromSelection();
            }

            if(input.isKeyDown(Input.KEY_ESCAPE)){
                mc.clearSelection();
            }
        }


        processViewControllerControls(input);
        processClicks(input);
    }

    // DEPRECATED TODO USE keyPressed, etc
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

        GameController.mouseController.setMousePoint(new Point(input.getMouseX(), input.getMouseY())); // TODO use this in more places
    }

    @Override
    public void keyPressed(int i, char c) { // TODO CONSIDER using this to handle key presses instead?

        // this is getting messy TODO clean up
        GuiController gc = GameController.guiController;

        // get focus
        Component focus = gc.getFocus();

        // if a text field is selected, send the input there
        if(focus != null){ //&& Component.TYPE_TEXTFIELD == focus.getType()){

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
        } else { // add more depth to this
            processGenericControls(i, c);
//
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

    // SEPERATING CONTROL SCHEMES INTO METHODS. TODO CONSIDER mapping in future?

    private void processGenericControls(int i, char c){

        // NEED A HIERARCHY FOR PASSING INPUT AROUND

        // PROCESS DOOR CONTROLS
        Door door = GameController.mapController.getHoverDoor();
        Room room = GameController.mapController.getHoverRoom(); // TODO
        if(door != null){
            door.input(i, c);
        } else if(room != null){ // Only operate room controls if a door isn't selected
            room.input(i, c);
        }

    }

    private void processBuildControls(int i, char c){
        // TODO code...
    }

}
