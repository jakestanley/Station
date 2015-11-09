package uk.co.jakestanley.commander.input;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import uk.co.jakestanley.commander.CommanderGame;

/**
 * Created by jp-st on 09/11/2015.
 */
public class InputController implements KeyListener {

    public InputController(){

    }

    public void update(GameContainer gc){ // TODO should return some list of actions or something for the scene controller
        Input input = gc.getInput();
        if(input.isKeyDown(Input.KEY_W)){
            CommanderGame.sceneController.getMobileEntities().get(0).movePositiveZ(); // TODO change this to a fancy get player method
        }
        if(input.isKeyDown(Input.KEY_S)){
            CommanderGame.sceneController.getMobileEntities().get(0).moveNegativeZ(); // TODO set next value actually
        }
        if(input.isKeyDown(Input.KEY_D)){
            CommanderGame.sceneController.getMobileEntities().get(0).movePositiveX();
        }
        if(input.isKeyDown(Input.KEY_A)){
            CommanderGame.sceneController.getMobileEntities().get(0).moveNegativeX();
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
