package uk.co.jakestanley.commander.rendering.entities;

import lombok.Getter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by jake on 19/11/2015.
 */
@Getter
public class Camera {

    private Vector3f position;
    private float pitch; // up or down
    private float yaw; // left or right
    private float roll;

    public Camera(){
        position = new Vector3f(0,0,0);
    }

    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){ // TODO eventually move this stuff to the input controller in a cool ass way
            position.z = position.z - 0.02f; // TODO make speed constant
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x = position.x + 0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x = position.x - 0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.z = position.z + 0.02f; // TODO make speed constant
        }
    }

}
