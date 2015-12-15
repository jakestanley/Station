package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.world.tools.Maths;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Created by jake on 19/11/2015.
 */
@Getter
@AllArgsConstructor
public class Camera {

    private Vector3f offset;
    private Vector3f position;
    private float pitch = 20; // up or down
    private float previousYaw = 0, yaw = 0, pYaw = 0; // left or right
    private float roll;
    private float scrollSpeed;
    private float zoom = 5;
    private int facing; // TODO set accordingly
    private int rotateDirection, zoomDirection;
    private int rotationCooldown, zoomCooldown;
    private boolean rotating, zooming;

    // redoing the camera stuff
    private Renderable target;
    private float distance = 50;
    private float angle = 45;

    public Camera(Renderable target){
        this.target = target;
    }

    public Camera(Renderable target, float pitch, float yaw, float roll){
        this.target = target;
        this.pitch = pitch;
        this.yaw = DEFAULT_ROT_Y_OFFSET;
        this.roll = roll;
        offset = new Vector3f(0,0,0);
    }

    public Camera(Renderable target, Vector3f offset, float pitch, float yaw, float roll){
        this.target = target;
        this.offset = offset;
        this.pitch = pitch;
        this.yaw = DEFAULT_ROT_Y_OFFSET;
        this.roll = roll;
        this.zoom = DEFAULT_ZOOM;
        scrollSpeed = BASE_SCROLL_SPEED;
        distance = DEFAULT_DISTANCE;
        rotationCooldown = 0;
        zoomCooldown = 0;
        rotating = false;
        zooming = false;
    }

    public void init(){
        updatePosition();
        updateScrollSpeed();
    }

    public void update() {

        // calculate stuff
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateZoom();
        calculatePitch();
        calculateAngleAroundTarget();
        calculateCameraPosition(horizontalDistance, verticalDistance);

        if(rotating){
            yaw = 180 - 135 - (target.getRotY() + (angle*2));
        }

        Ship ship = Main.getGame().getShip();
        if(!ship.hasVisibleRenderEntities()){
            ship.resetVisibleRenderEntities();
        }

        if(rotationCooldown > 0){
            rotationCooldown--;
        }

        if(zoomCooldown > 0){
            zoomCooldown--;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            switch (facing) {
                case NORTH:
                    offset.x = offset.x - scrollSpeed;
                    offset.z = offset.z - scrollSpeed;
                    break;
                case SOUTH:
                    offset.x = offset.x + scrollSpeed;
                    offset.z = offset.z + scrollSpeed;
                    break;
                case EAST:
                    offset.x = offset.x + scrollSpeed;
                    offset.z = offset.z - scrollSpeed;
                    break;
                case WEST:
                    offset.x = offset.x - scrollSpeed;
                    offset.z = offset.z + scrollSpeed;
                    break;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            switch (facing) {
                case NORTH:
                    offset.x = offset.x + scrollSpeed;
                    offset.z = offset.z - scrollSpeed;
                    break;
                case SOUTH:
                    offset.x = offset.x - scrollSpeed;
                    offset.z = offset.z + scrollSpeed;
                    break;
                case EAST:
                    offset.x = offset.x + scrollSpeed;
                    offset.z = offset.z + scrollSpeed;
                    break;
                case WEST:
                    offset.x = offset.x - scrollSpeed;
                    offset.z = offset.z - scrollSpeed;
                    break;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            switch (facing) {
                case NORTH:
                    offset.x = offset.x - scrollSpeed;
                    offset.z = offset.z + scrollSpeed;
                    break;
                case SOUTH:
                    offset.x = offset.x + scrollSpeed;
                    offset.z = offset.z - scrollSpeed;
                    break;
                case EAST:
                    offset.x = offset.x - scrollSpeed;
                    offset.z = offset.z - scrollSpeed;
                    break;
                case WEST:
                    offset.x = offset.x + scrollSpeed;
                    offset.z = offset.z + scrollSpeed;
                    break;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            switch (facing) {
                case NORTH:
                    offset.x = offset.x + scrollSpeed;
                    offset.z = offset.z + scrollSpeed;
                    break;
                case SOUTH:
                    offset.x = offset.x - scrollSpeed;
                    offset.z = offset.z - scrollSpeed;
                    break;
                case EAST:
                    offset.x = offset.x - scrollSpeed;
                    offset.z = offset.z + scrollSpeed;
                    break;
                case WEST:
                    offset.x = offset.x + scrollSpeed;
                    offset.z = offset.z - scrollSpeed;
                    break;
            }
        }
        boolean justFuckingStartedRotating = false;
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            if (!rotating && rotationCooldown == 0 && !zooming) {
                rotateDirection = LEFT;
                rotating = true;
                rotationCooldown = COOLDOWN_TICKS;
                facing++;
                if(facing > 3){
                    facing = 0;
                }
                justFuckingStartedRotating = true;
                ship.resetVisibleRenderEntities();
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            if (!rotating && rotationCooldown == 0 && !zooming) {
                rotateDirection = RIGHT;
                rotating = true;
                rotationCooldown = COOLDOWN_TICKS;
                facing--;
                if(facing < 0){
                    facing = 3;
                }
                justFuckingStartedRotating = true;
                ship.resetVisibleRenderEntities();
            }
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_LBRACKET)){
            if (!zooming && zoomCooldown == 0 && !isMinZoom() && !rotating) {
                zoom--;
                zoomDirection = ZOOM_DIRECTION_OUT;
                zoomCooldown = COOLDOWN_TICKS;
                zooming = true;
            }
        } else if(Keyboard.isKeyDown(Keyboard.KEY_RBRACKET)){
            if (!zooming && zoomCooldown == 0 && !isMaxZoom() && !rotating) {
                zoom++;
                zoomDirection = ZOOM_DIRECTION_IN;
                zoomCooldown = COOLDOWN_TICKS;
                zooming = true;
            }
        }

        if(zooming){
            if(zoomCooldown == 0){
                System.out.println(zoomCooldown);
                zooming = false;
                updateScrollSpeed();
            }
        }

        if(rotating && !justFuckingStartedRotating){
            float mod = (Math.abs(angle)) % 45;
            if (mod == 0) { // stop rotation if at a locked angle
                System.out.println("Stopping rotation");
                rotating = false;
            }
        }
    }

    public boolean isMinZoom(){
        return distance >= MAX_DISTANCE;
    }

    public boolean isMaxZoom() {
        return distance <= MIN_DISTANCE;
    }

    private float calculateHorizontalDistance(){
        return (float) (distance * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float) (distance * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom(){
        if(zooming){
            float zoomLevel = zoom * 1f;
            if(ZOOM_DIRECTION_OUT == zoomDirection){
                distance += zoomLevel;
            } else {
                distance -= zoomLevel;
            }
//            System.out.println("distance: " + distance);
        }
    }

    private void calculatePitch(){

    }

    private void calculateAngleAroundTarget(){
        if(rotating){
            if(LEFT == rotateDirection){
                angle -= 1f;
            } else {
                angle += 1f;
            }
        }
    }

    private void calculateCameraPosition(float hDist, float vDist){
        if(rotating){
            if(LEFT == rotateDirection){
                pYaw -= YAW_SPEED * 0.2f;
            } else {
                pYaw += YAW_SPEED * 0.2f;
            }
        }
        float theta = target.getRotX() + pYaw + 45; // about y axis
        float offsetX = (float) (hDist * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (hDist * Math.cos(Math.toRadians(theta)));
        position.x = target.getGlobalPosition().x + offsetX;
        position.z = target.getGlobalPosition().z + offsetZ;
        position.y = target.getGlobalPosition().y + vDist;
        position = Maths.addVectors(position, offset);

    }

    public void updatePosition(){
        Vector3f targetPosition = target.getGlobalPosition();
        position = Maths.addVectors(targetPosition, offset);
    }

    private void zoomOut(){
        distance -= ZOOM_INCREMENTS;
    }

    private void zoomIn(){
        distance += ZOOM_INCREMENTS;
    }

    private void updateScrollSpeed(){
        scrollSpeed = (MAX_ZOOM - zoom) * BASE_SCROLL_SPEED; // TODO improve. 1 is so it doesn't ever hit zero
        if(scrollSpeed < MIN_SCROLL_SPEED){
            scrollSpeed = MIN_SCROLL_SPEED;
        }
        System.out.println("New scroll speed is: " + scrollSpeed);
    }

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    private static final int COOLDOWN_TICKS = 12;
    private static final int LEFT = 4;
    private static final int RIGHT = 5;

    private static final float BASE_SCROLL_SPEED = 0.5f;
    private static final float MIN_SCROLL_SPEED = 0.25f;
    private static final float YAW_SPEED    = 10f;
    private static final int MIN_ZOOM       = 1;
    private static final int DEFAULT_ZOOM   = 7;
    private static final int MAX_ZOOM       = 10;

    private static final float ZOOM_INCREMENTS = 0.5f;
    private static final float DEFAULT_ROT_Y_OFFSET = -45f;

    private static final int ZOOM_DIRECTION_IN = 6;
    private static final int ZOOM_DIRECTION_OUT = 7;
    private static final float DEFAULT_DISTANCE = 168;
    private static final float MIN_DISTANCE = 84;
    private static final float MAX_DISTANCE = 840;
    private static final int MAX_ZOOM_INCREMENTS = 10;

}
