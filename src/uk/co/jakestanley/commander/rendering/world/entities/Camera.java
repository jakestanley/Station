package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
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

    private Vector3f offset, pOffset;
    private Vector3f position;
    private float pitch = 20; // up or down
    private float yaw = 0, pYaw = 0; // left or right
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

    public void move(){
        calculateZoom();
        calculatePitch();
        calculateAngleAroundTarget();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
    }

    private void calculateZoom(){
        if(zooming){
            float zoomLevel = zoom * 1f;
            if(ZOOM_DIRECTION_OUT == zoomDirection){
                distance += zoomLevel;
            } else {
                distance -= zoomLevel;
            }
            System.out.println("distance: " + distance);
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

    public Camera(Renderable target, float pitch, float yaw, float roll){
        this.target = target;
        this.pitch = pitch;
        this.yaw = DEFAULT_ROT_Y_OFFSET;
        this.roll = roll;
    }

    public Camera(Renderable target, Vector3f offset, float pitch, float yaw, float roll){
        this.target = target;
        this.offset = offset;
        this.pitch = pitch;
        this.yaw = DEFAULT_ROT_Y_OFFSET;
        this.roll = roll;
        this.zoom = DEFAULT_ZOOM;
        scrollSpeed = BASE_SCROLL_SPEED;
        pOffset = new Vector3f(0,0,0);
        distance = DEFAULT_DISTANCE;
        rotationCooldown = 0;
        zoomCooldown = 0;
        rotating = false;
        zooming = false;
        updateScrollSpeed();
    }

    public void init(){
        updatePosition();
    }

    public void update() {
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
                    pOffset.x = pOffset.x - scrollSpeed;
                    pOffset.z = pOffset.z - scrollSpeed;
                    break;
                case SOUTH:
                    pOffset.x = pOffset.x + scrollSpeed;
                    pOffset.z = pOffset.z + scrollSpeed;
                    break;
                case EAST:
                    pOffset.x = pOffset.x + scrollSpeed;
                    pOffset.z = pOffset.z - scrollSpeed;
                    break;
                case WEST:
                    pOffset.x = pOffset.x - scrollSpeed;
                    pOffset.z = pOffset.z + scrollSpeed;
                    break;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            switch (facing) {
                case NORTH:
                    pOffset.x = pOffset.x + scrollSpeed;
                    pOffset.z = pOffset.z - scrollSpeed;
                    break;
                case SOUTH:
                    pOffset.x = pOffset.x - scrollSpeed;
                    pOffset.z = pOffset.z + scrollSpeed;
                    break;
                case EAST:
                    pOffset.x = pOffset.x + scrollSpeed;
                    pOffset.z = pOffset.z + scrollSpeed;
                    break;
                case WEST:
                    pOffset.x = pOffset.x - scrollSpeed;
                    pOffset.z = pOffset.z - scrollSpeed;
                    break;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            switch (facing) {
                case NORTH:
                    pOffset.x = pOffset.x - scrollSpeed;
                    pOffset.z = pOffset.z + scrollSpeed;
                    break;
                case SOUTH:
                    pOffset.x = pOffset.x + scrollSpeed;
                    pOffset.z = pOffset.z - scrollSpeed;
                    break;
                case EAST:
                    pOffset.x = pOffset.x - scrollSpeed;
                    pOffset.z = pOffset.z - scrollSpeed;
                    break;
                case WEST:
                    pOffset.x = pOffset.x + scrollSpeed;
                    pOffset.z = pOffset.z + scrollSpeed;
                    break;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            switch (facing) {
                case NORTH:
                    pOffset.x = pOffset.x + scrollSpeed;
                    pOffset.z = pOffset.z + scrollSpeed;
                    break;
                case SOUTH:
                    pOffset.x = pOffset.x - scrollSpeed;
                    pOffset.z = pOffset.z - scrollSpeed;
                    break;
                case EAST:
                    pOffset.x = pOffset.x - scrollSpeed;
                    pOffset.z = pOffset.z + scrollSpeed;
                    break;
                case WEST:
                    pOffset.x = pOffset.x + scrollSpeed;
                    pOffset.z = pOffset.z - scrollSpeed;
                    break;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            if (!rotating && rotationCooldown == 0 && !zooming) {
                rotateDirection = LEFT;
                rotating = true;
                rotationCooldown = COOLDOWN_TICKS;
                facing++;
                if(facing > 3){
                    facing = 0;
                }
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
                ship.resetVisibleRenderEntities();
            }
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_LBRACKET)){
            if (!zooming && zoomCooldown == 0 && !isMinZoom() && !rotating) {
                zoomDirection = ZOOM_DIRECTION_OUT;
                zoomCooldown = COOLDOWN_TICKS;
                zooming = true;
            }
        } else if(Keyboard.isKeyDown(Keyboard.KEY_RBRACKET)){
            if (!zooming && zoomCooldown == 0 && !isMaxZoom() && !rotating) {
                zoomDirection = ZOOM_DIRECTION_IN;
                zoomCooldown = COOLDOWN_TICKS;
                zooming = true;
            }
        }

        if(zooming){
            if(zoomCooldown == 0){
                System.out.println(zoomCooldown);
                zooming = false;
            }
        }

//        float mod = 1;

        if (rotating && (rotateDirection == LEFT)) {
            rotateLeft();
        } else if (rotating && (rotateDirection == RIGHT)) {
            rotateRight();
        }
        if(rotating){
            float mod = pYaw % 90;
            if (mod == 0) { // stop rotation if at a locked angle
                rotating = false;
            }
        }
    }

    public void updatePosition(){
        Vector3f targetPosition = target.getGlobalPosition();
        position = Maths.addVectors(targetPosition, offset);
    }

    private void calculateCameraPosition(float hDist, float vDist){
        float theta = target.getRotX() + pYaw + 45; // about y axis
        float offsetX = (float) (hDist * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (hDist * Math.cos(Math.toRadians(theta)));
        position.x = target.getGlobalPosition().x + offsetX;
        position.z = target.getGlobalPosition().z + offsetZ;
        position.y = target.getGlobalPosition().y + vDist;
        position = Maths.addVectors(position, pOffset);
    }

    public boolean isMinZoom(){
        return distance == MAX_DISTANCE;
    }

    public boolean isMaxZoom() {
        return distance == MIN_DISTANCE;
    }

    private float calculateHorizontalDistance(){
        return (float) (distance * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float) (distance * Math.sin(Math.toRadians(pitch)));
    }

//    private Vector3f calculateCameraPosition(float horizontalDistance, float verticalDistance){
//        Vector3f camera = new Vector3f();
//        camera.y = target.getGlobalPosition().y + target.getGlobalPosition() + verticalDistance;
//    }

    private void rotateLeft(){
        pYaw -= YAW_SPEED * 0.2f;
//        position = calculateNewPosition(YAW_SPEED);
    }

    private void rotateRight(){
        pYaw += YAW_SPEED * 0.2f;
//        position = calculateNewPosition(-YAW_SPEED);
    }

    private void zoomOut(){
        distance -= ZOOM_INCREMENTS;
    }

    private void zoomIn(){
        distance += ZOOM_INCREMENTS;
    }

    private void updateScrollSpeed(){
        scrollSpeed = (MAX_ZOOM - zoom + 1) * BASE_SCROLL_SPEED; // TODO improve
        System.out.println("New scroll speed is: " + scrollSpeed);
    }

    private Vector3f calculateNewPosition(float rot){ // fuck yea
//        Matrix2f rotationMatrix = new Matrix2f();
//        rotationMatrix.m00 = (float) Math.cos(rotation);
//        rotationMatrix.m10 = (float) -Math.sin(rotation);
//        rotationMatrix.m01 = (float) Math.sin(rotation);
//        rotationMatrix.m11 = (float) Math.cos(rotation); // TODO save this from above
//
//        float newX = (globalPosition.getX() * rotationMatrix.m00) + (globalPosition.getZ() * rotationMatrix.m01);
//        float newZ = (globalPosition.getX() * rotationMatrix.m10) + (globalPosition.getZ() * rotationMatrix.m11);
//        Vector3f newPosition = new Vector3f(newX, globalPosition.getY(), newZ);


//        int centerX = 0;
//        int centerZ = 0;
//        float x = -rotation;
//        float point2x = globalPosition.getX();
//        float point2z = globalPosition.getZ();
//        double newX = centerX + (point2x-centerX)*Math.cos(x) - (point2z-centerZ)*Math.sin(x);
//        double newZ = centerZ + (point2x-centerX)*Math.sin(x) + (point2z-centerZ)*Math.cos(x);
//
//        Vector3f newPosition = new Vector3f((float) newX, globalPosition.getY(), (float) newZ);
        Point2D origin = new Point2D.Double(position.getX(), position.getZ());
        Point2D result = new Point2D.Double();
        AffineTransform rotation = new AffineTransform();
        double angleInRadians = (rot * Math.PI / 180);
        rotation.rotate(angleInRadians, 0, 0);
        rotation.transform(origin, result);
        Vector3f shipPosition = Main.getGame().getShip().getGlobalPosition();
        float newPosX = shipPosition.getX() + (float) result.getX() + position.getX();
        float newPosY = shipPosition.getY() + position.getY() + position.getY();
        float newPosZ = shipPosition.getZ() + (float) result.getY() + position.getZ();


        Vector3f newPos = new Vector3f(newPosX, newPosY, newPosZ);
        return newPos;


//        return newPosition;
    }

    private static Matrix3f createZoomInMatrix(){
        Matrix3f zoom = new Matrix3f();
        zoom.setIdentity();
        zoom.m00 = ZOOM_OUT_X;
        zoom.m11 = ZOOM_OUT_Y;
        zoom.m22 = ZOOM_OUT_Z;
        zoom.invert();
        return zoom;
    }

    private static Matrix3f createZoomOutMatrix(){
        Matrix3f zoom = new Matrix3f();
        zoom.setIdentity();
        zoom.m00 = ZOOM_OUT_X;
        zoom.m11 = ZOOM_OUT_Y;
        zoom.m22 = ZOOM_OUT_Z;
        return zoom;
    }

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    private static final int COOLDOWN_TICKS = 12;
    private static final int LEFT = 4;
    private static final int RIGHT = 5;

    private static final float BASE_SCROLL_SPEED = 0.5f;
    private static final float YAW_SPEED    = 10f;
    private static final int MIN_ZOOM       = 1;
    private static final int DEFAULT_ZOOM   = 7;
    private static final int MAX_ZOOM       = 10;

    private static final float ZOOM_OUT_X = 1.04f;
    private static final float ZOOM_OUT_Y = 1.04f;
    private static final float ZOOM_OUT_Z = 1.04f;
    private static final float ZOOM_INCREMENTS = 2f;
    private static final float DEFAULT_ROT_Y_OFFSET = -45f;

    private static final int ZOOM_DIRECTION_IN = 6;
    private static final int ZOOM_DIRECTION_OUT = 7;
    private static final float DEFAULT_DISTANCE = 168;
    private static final float MIN_DISTANCE = 84;
    private static final float MAX_DISTANCE = 840;
    private static final int MAX_ZOOM_INCREMENTS = 10;

}
