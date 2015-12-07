package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Game3D;
import uk.co.jakestanley.commander.Main;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Created by jake on 19/11/2015.
 */
@Getter
@AllArgsConstructor
public class Camera {

    private Vector3f position;
    private float pitch; // up or down
    private float yaw; // left or right
    private float roll;
    private int facing; // TODO set accordingly
    private int direction;
    private int cooldown;
    private boolean rotating;

    public Camera(){
        position = new Vector3f(0,0,0);
        facing = NORTH;
        rotating = false;
        direction = RIGHT;
        cooldown = 0;
//        Game3D.ship.resetVisibleRenderEntities();
    }

    public Camera(Vector3f position, float pitch, float yaw, float roll){
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        cooldown = 0;
//        Game3D.ship.resetVisibleRenderEntities();
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

    private void rotateLeft(){
        yaw += YAW_SPEED;
        position = calculateNewPosition(YAW_SPEED);
    }

    private void rotateRight(){
        yaw -= YAW_SPEED;
        position = calculateNewPosition(-YAW_SPEED);
    }

    private Vector3f calculateNewPosition(float rot){ // fuck yea
//        Matrix2f rotationMatrix = new Matrix2f();
//        rotationMatrix.m00 = (float) Math.cos(rotation);
//        rotationMatrix.m10 = (float) -Math.sin(rotation);
//        rotationMatrix.m01 = (float) Math.sin(rotation);
//        rotationMatrix.m11 = (float) Math.cos(rotation); // TODO save this from above
//
//        float newX = (position.getX() * rotationMatrix.m00) + (position.getZ() * rotationMatrix.m01);
//        float newZ = (position.getX() * rotationMatrix.m10) + (position.getZ() * rotationMatrix.m11);
//        Vector3f newPosition = new Vector3f(newX, position.getY(), newZ);


//        int centerX = 0;
//        int centerZ = 0;
//        float x = -rotation;
//        float point2x = position.getX();
//        float point2z = position.getZ();
//        double newX = centerX + (point2x-centerX)*Math.cos(x) - (point2z-centerZ)*Math.sin(x);
//        double newZ = centerZ + (point2x-centerX)*Math.sin(x) + (point2z-centerZ)*Math.cos(x);
//
//        Vector3f newPosition = new Vector3f((float) newX, position.getY(), (float) newZ);
        Point2D origin = new Point2D.Double(position.getX(), position.getZ());
        Point2D result = new Point2D.Double();
        AffineTransform rotation = new AffineTransform();
        double angleInRadians = (rot * Math.PI / 180);
        rotation.rotate(angleInRadians, 0, 0);
        rotation.transform(origin, result);
        Vector3f newPos = new Vector3f((float) result.getX(), position.getY(), (float) result.getY());
        return newPos;


//        return newPosition;
    }

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    private static final int COOLDOWN_TICKS = 12;
    private static final int LEFT = 4;
    private static final int RIGHT = 5;
    private static final float SCROLL_SPEED = 1f;
    private static final float YAW_SPEED = 10f;

}
