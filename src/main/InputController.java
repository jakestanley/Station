package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/**
 * Created by stanners on 21/07/2015.
 */
public class InputController {

    public static void processInput(GameContainer container){
        processViewControllerControls(container);
    }

    private static void processViewControllerControls(GameContainer container){ // TODO make this more efficient. any other controls?

        ViewController vc = GameController.viewController;
        Input input = container.getInput();

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


}
