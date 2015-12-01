package uk.co.jakestanley.commander.rendering.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by jake on 19/11/2015.
 */
@Getter
@AllArgsConstructor
public class Camera {

    private static final float SCROLL_SPEED = 0.5f;

    private Vector3f position;
    private float pitch; // up or down
    private float yaw; // left or right
    private float roll;

    public Camera(){
        position = new Vector3f(0,0,0);
    }

    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){ // TODO eventually move this stuff to the input controller in a cool ass way
            position.z = position.z - SCROLL_SPEED; // TODO make speed constant
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x = position.x + SCROLL_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x = position.x - SCROLL_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z = position.z + SCROLL_SPEED; // TODO make speed constant
        }
    }

}
