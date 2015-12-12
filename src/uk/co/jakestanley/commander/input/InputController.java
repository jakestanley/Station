package uk.co.jakestanley.commander.input;

import org.lwjgl.input.Keyboard;
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

    public void update(GameContainer gc){ // TODO should return some list of uk.co.jakestanley.commander2d.actions or something for the scene controller.
        Input input = gc.getInput();
        if(input.isKeyDown(Input.KEY_W)){ // TODO send the input data to the SceneController? don't explicitly command its logic, perhaps?
            downW();
        }
        if(input.isKeyDown(Input.KEY_S)){
            downS();
        }
        if(input.isKeyDown(Input.KEY_D)){
            downD();
        }
        if(input.isKeyDown(Input.KEY_A)){
            downA();
        } // TODO up and down
    }

    public void update(){ // TODO use these update methods to abstract behaviour
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            downW();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            downS();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            downD();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            downA();
        }

        if(Main.getGame().isDebug()){
            if(Keyboard.isKeyDown(Keyboard.KEY_DELETE)){
                Main.getGame().clearWalls();
            }
        }
    } // TODO create a separate abstraction layer

    private void downW(){
//        Main.getGame().getSceneController().getMobiles().get(0).movePositiveZ(); // TODO change this to a fancy get player method
    }

    private void downS(){
//        Main.getGame().getSceneController().getMobiles().get(0).moveNegativeZ(); // TODO set next value actually
    }

    private void downD(){
//        Main.getGame().getSceneController().getMobiles().get(0).movePositiveX();
    }

    private void downA(){
//        Main.getGame().getSceneController().getMobiles().get(0).moveNegativeX();
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
