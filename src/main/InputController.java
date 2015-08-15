package main;

import map.MapController;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import java.awt.*;

/**
 * Actions container input by sending it to relevant controllers. It is not meant to perform heavy business logic.
 * Created by stanners on 21/07/2015.
 */
public class InputController {

    public static boolean leftMouseHeld = false; // TODO consider making this un-static

    public static void processInput(GameContainer container){
        Input input = container.getInput();
        processViewControllerControls(input);
        processClicks(input);
    }

    private static void processViewControllerControls(Input input){ // TODO make this more efficient. any other controls?

        ViewController vc = GameController.viewController;
        MapController mc = GameController.mapController;

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

        // if in construction context
        if(GameController.contextController.getContext() == ContextController.CONSTRUCTION){
            if(input.isKeyDown(Input.KEY_ENTER)){
                mc.createRoomFromSelection();
            }

            if(input.isKeyDown(Input.KEY_ESCAPE)){
                mc.clearSelection();
            }
        }


    }

    private static void processClicks(Input input){

        // if in construction context
        if(GameController.contextController.getContext() == ContextController.CONSTRUCTION) {
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
        }
        
        GameController.mouseController.setHoverPoint(new Point(input.getMouseX(), input.getMouseY())); // TODO use this in more places
    }

}
