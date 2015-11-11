package uk.co.jakestanley.commander.input;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import uk.co.jakestanley.commander.Main;

/**
 * Created by jp-st on 09/11/2015.
 */
public class InputController implements KeyListener {

    public InputController(){
        // TODO improve
    }

    public void update(GameContainer gc){ // TODO should return some list of actions or something for the scene controller
        Input input = gc.getInput();
        if(input.isKeyDown(Input.KEY_W)){ // TODO send the input data to the SceneController? don't explicitly command its logic, perhaps?
            Main.getSceneController().getMobileEntities().get(0).movePositiveZ(); // TODO change this to a fancy get player method
        }
        if(input.isKeyDown(Input.KEY_S)){
            Main.getSceneController().getMobileEntities().get(0).moveNegativeZ(); // TODO set next value actually
        }
        if(input.isKeyDown(Input.KEY_D)){
            Main.getSceneController().getMobileEntities().get(0).movePositiveX();
        }
        if(input.isKeyDown(Input.KEY_A)){
            Main.getSceneController().getMobileEntities().get(0).moveNegativeX();
        } // TODO up and down
    }

    public void keyPressed(int i, char c) {

    }

    public void keyReleased(int i, char c) {

    }

    public void setInput(Input input) {

    }

    public boolean isAcceptingInput() {
        return false;
    }

    public void inputEnded() {

    }

    public void inputStarted() {

    }
}
