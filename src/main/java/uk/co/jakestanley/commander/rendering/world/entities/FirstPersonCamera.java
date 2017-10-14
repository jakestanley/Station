package uk.co.jakestanley.commander.rendering.world.entities;

import java.util.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.util.Log;

public class FirstPersonCamera extends Camera {

    private static final float MOUSE_LOOK_SPEED = 1.0f;
    private static final float MOVEMENT_SPEED = 0.1f;

    public FirstPersonCamera(Vector3f vector3f, int i, int j, int k) {
    	super(vector3f, i, j, k);
    }

    @Override
    public void move() {

    	// mouse
        int mouseDeltaX = Mouse.getDX();
        int mouseDeltaY = Mouse.getDY();

        this.yaw = this.yaw + mouseDeltaX;
        this.pitch = this.pitch - mouseDeltaY;

        // bound the pitch
        if (this.pitch < -65) {
        	this.pitch = -65;
        } else if(this.pitch > 65) {
        	this.pitch = 65;
        }

        // prevent the yaw variable from exceeding max float
        if (this.yaw > 360) {
        	this.yaw = Math.floorMod((int) this.yaw, (int) 360);
        }

        if (this.yaw < -360) {
        	this.yaw = Math.floorMod((int) this.yaw, (int) -360);
        }

        // calculate movement
        Matrix2f rotation = new Matrix2f(); // rotation matrix
        rotation.setZero();
        rotation.m00 = (float) Math.cos(yaw);
        rotation.m10 = (float) Math.sin(yaw); 
        rotation.m01 = (float) -Math.sin(yaw);
        rotation.m11 = (float) Math.cos(yaw);

        // current 2d position as vector
        Vector2f position = new Vector2f(this.getPosition().getX(), this.getPosition().getZ());

        Log.info("Rotation Matrix: " + rotation.toString());

        // calculate direction based on input
        Vector2f direction = new Vector2f(0, 0);

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
        	direction.setY(direction.getY() - 1);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
        	direction.setX(direction.getX() + 1);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
        	direction.setX(direction.getX() - 1);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
        	direction.setY(direction.getY() + 1);
        }
        
        

        Vector2f translationVector = Matrix2f.transform(rotation, direction, null);
        Log.info("Translation Vector: " + translationVector.toString());

        Vector2f newPosition = org.lwjgl.util.vector.Vector2f.add(translationVector, position, null);
        this.getPosition().setX(newPosition.getX());
        this.getPosition().setZ(newPosition.getY());

        //        Vector2f.


        //        if(this.pitch > )

        // bound the yaw and pitch
        Log.info(String.format("new yaw/pitch: %f/%f", yaw, pitch));


        // TODO smoothing

    }

    private void calculateNewPosition() {
    	
    }

}
