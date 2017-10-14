package uk.co.jakestanley.commander.rendering.world.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import uk.co.jakestanley.commander.Main;

public class PerspectiveCamera extends Camera {

	public PerspectiveCamera(Vector3f vector3f, int i, int j, int k) {
		super(vector3f, i, j, k);
	}

	public void move() {
	        Ship ship = Main.getGame().getShip();
	        if(!ship.hasVisibleRenderEntities()){
	            ship.resetVisibleRenderEntities();
	        }
	        if(cooldown > 0){
	            cooldown--;
	        }
	        
	        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
	            switch (facing) {
	                case NORTH:
	                    position.x = position.x - SCROLL_SPEED;
	                    position.z = position.z - SCROLL_SPEED;
	                    break;
	                case SOUTH:
	                    position.x = position.x + SCROLL_SPEED;
	                    position.z = position.z + SCROLL_SPEED;
	                    break;
	                case EAST:
	                    position.x = position.x + SCROLL_SPEED;
	                    position.z = position.z - SCROLL_SPEED;
	                    break;
	                case WEST:
	                    position.x = position.x - SCROLL_SPEED;
	                    position.z = position.z + SCROLL_SPEED;
	                    break;
	            }
	        }
	        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
	            switch (facing) {
	                case NORTH:
	                    position.x = position.x + SCROLL_SPEED;
	                    position.z = position.z - SCROLL_SPEED;
	                    break;
	                case SOUTH:
	                    position.x = position.x - SCROLL_SPEED;
	                    position.z = position.z + SCROLL_SPEED;
	                    break;
	                case EAST:
	                    position.x = position.x + SCROLL_SPEED;
	                    position.z = position.z + SCROLL_SPEED;
	                    break;
	                case WEST:
	                    position.x = position.x - SCROLL_SPEED;
	                    position.z = position.z - SCROLL_SPEED;
	                    break;
	            }
	        }
	        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
	            switch (facing) {
	                case NORTH:
	                    position.x = position.x - SCROLL_SPEED;
	                    position.z = position.z + SCROLL_SPEED;
	                    break;
	                case SOUTH:
	                    position.x = position.x + SCROLL_SPEED;
	                    position.z = position.z - SCROLL_SPEED;
	                    break;
	                case EAST:
	                    position.x = position.x - SCROLL_SPEED;
	                    position.z = position.z - SCROLL_SPEED;
	                    break;
	                case WEST:
	                    position.x = position.x + SCROLL_SPEED;
	                    position.z = position.z + SCROLL_SPEED;
	                    break;
	            }
	        }
	        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
	            switch (facing) {
	                case NORTH:
	                    position.x = position.x + SCROLL_SPEED;
	                    position.z = position.z + SCROLL_SPEED;
	                    break;
	                case SOUTH:
	                    position.x = position.x - SCROLL_SPEED;
	                    position.z = position.z - SCROLL_SPEED;
	                    break;
	                case EAST:
	                    position.x = position.x - SCROLL_SPEED;
	                    position.z = position.z + SCROLL_SPEED;
	                    break;
	                case WEST:
	                    position.x = position.x + SCROLL_SPEED;
	                    position.z = position.z - SCROLL_SPEED;
	                    break;
	            }
	        }
	        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
	            if (!rotating && cooldown == 0) {
	                direction = LEFT;
	                rotating = true;
	                cooldown = COOLDOWN_TICKS;
	                facing++;
	                if(facing > 3){
	                    facing = 0;
	                }
	                ship.resetVisibleRenderEntities();
	                rotateLeft();
	            }
	        } else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
	            if (!rotating && cooldown == 0) {
	                direction = RIGHT;
	                rotating = true;
	                cooldown = COOLDOWN_TICKS;
	                facing--;
	                if(facing < 0){
	                    facing = 3;
	                }
	                ship.resetVisibleRenderEntities();
	                rotateRight();
	            }
	        }
	        float mod = (yaw + 45) % 90;
	        if (mod == 0) { // stop rotation if at a locked angle
	            rotating = false;
	        } else if (rotating && (direction == LEFT)) {
	            rotateLeft();
	        } else if (rotating && (direction == RIGHT)) {
	            rotateRight();
	        }

	    }
	
}
