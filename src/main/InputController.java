package main;

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

    private static void processClicks(Input input){

        // TODO CONSIDER map context
        if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
            Point mousePoint = new Point(input.getMouseX(), input.getMouseY());
            if(!leftMouseHeld){
                // TODO take down the first coordinate
                leftMouseHeld = true;
                System.out.println("mouse left clicked");
                GameController.mouseController.setClickPoint(mousePoint);
            }
            GameController.mouseController.setHoverPoint(new Point(input.getMouseX(), input.getMouseY())); // TODO use this in more places
        } else {
            if(leftMouseHeld){
                // TODO take down the release coordinate and send it to some controller. reintroduce RoomController?
                leftMouseHeld = false;
                System.out.println("mouse left released");
                GameController.mouseController.setMouseRelease();
            }
        }

    }

}
