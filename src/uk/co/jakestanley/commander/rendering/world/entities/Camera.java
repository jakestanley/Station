package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.Getter;
import lombok.ToString;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.world.tools.Maths;

@Getter @ToString
public class Camera {

    // https://github.com/Ritsu95/3D-Game-Engine-ThinMatrix/blob/master/3D%20Game%20Engine/src/entities/Camera.java // TODO adapted from this code, modify

    private float distance = 100f;
    private float angle = 0;

    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f offset = new Vector3f(0, 0, 0);
    private float pitch;
    private float yaw;
    private float roll = 0;
    private float scroll;
    private boolean rotate;

    private Renderable target;

    public Camera(Renderable target, boolean rotate) {
        this.target = target;
        this.pitch = DEFAULT_ROTATE_PITCH;
        this.angle = DEFAULT_ROTATE_ANGLE;
        this.rotate = rotate;
        scroll = (distance * 0.008f);
    }

    public void move() {
        Main.getGame().getShip().resetVisibleRenderEntities(); // TODO only check when the view has changed?
        getKeyboardInput();
        calculateZoom();
        if(rotate){
            calculatePitch();
            calculateAngleAroundPlayer();
        }
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        yaw = Maths.boundAngle(180 - (target.getRotY() + angle));
    }

    private void calculateCameraPosition(float horizDistance, float verticDistance) {
        float theta = target.getRotY() + angle;
        float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
        position.x = target.getGlobalPosition().x - offsetX + offset.getX();
        position.z = target.getGlobalPosition().z - offsetZ + offset.getZ();
        position.y = target.getGlobalPosition().y + verticDistance;
    }

    private void getKeyboardInput(){
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            switch (getFacing()) {
                case NORTH:
                    offset.x = offset.x - scroll;
                    offset.z = offset.z - scroll;
                    break;
                case SOUTH:
                    offset.x = offset.x + scroll;
                    offset.z = offset.z + scroll;
                    break;
                case EAST:
                    offset.x = offset.x + scroll;
                    offset.z = offset.z - scroll;
                    break;
                case WEST:
                    offset.x = offset.x - scroll;
                    offset.z = offset.z + scroll;
                    break;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            switch (getFacing()) {
                case NORTH:
                    offset.x = offset.x + scroll;
                    offset.z = offset.z - scroll;
                    break;
                case SOUTH:
                    offset.x = offset.x - scroll;
                    offset.z = offset.z + scroll;
                    break;
                case EAST:
                    offset.x = offset.x + scroll;
                    offset.z = offset.z + scroll;
                    break;
                case WEST:
                    offset.x = offset.x - scroll;
                    offset.z = offset.z - scroll;
                    break;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            switch (getFacing()) {
                case NORTH:
                    offset.x = offset.x - scroll;
                    offset.z = offset.z + scroll;
                    break;
                case SOUTH:
                    offset.x = offset.x + scroll;
                    offset.z = offset.z - scroll;
                    break;
                case EAST:
                    offset.x = offset.x - scroll;
                    offset.z = offset.z - scroll;
                    break;
                case WEST:
                    offset.x = offset.x + scroll;
                    offset.z = offset.z + scroll;
                    break;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            switch (getFacing()) {
                case NORTH:
                    offset.x = offset.x + scroll;
                    offset.z = offset.z + scroll;
                    break;
                case SOUTH:
                    offset.x = offset.x - scroll;
                    offset.z = offset.z - scroll;
                    break;
                case EAST:
                    offset.x = offset.x - scroll;
                    offset.z = offset.z + scroll;
                    break;
                case WEST:
                    offset.x = offset.x + scroll;
                    offset.z = offset.z - scroll;
                    break;
            }
        }
    }

    private float calculateHorizontalDistance() {
        return (float) (distance * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distance * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        float zoomLevel = Mouse.getDWheel() * 0.1f; // TODO make constant and reduce scroll speed. set a max/min
        distance -= zoomLevel;
        if(distance > MAX_DISTANCE){
            distance = MAX_DISTANCE;
        } else if(distance < MIN_DISTANCE){
            distance = MIN_DISTANCE;
        }
        scroll = (distance * 0.008f);
    }

    private void calculatePitch() {

        if (Mouse.isButtonDown(1)) {
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch -= pitchChange;
            if(pitch < MIN_PITCH){
                pitch = MIN_PITCH;
            } else if(pitch > MAX_PITCH){
                pitch = MAX_PITCH;
            }
        }
    }

    private void calculateAngleAroundPlayer() {
        if (Mouse.isButtonDown(1)) {
            float angleChange = Mouse.getDX() * 0.3f;
            angle -= angleChange;
            angle = Maths.boundAngle(angle);
            System.out.println("angle: " + angle);
        }
    }

    private int getFacing(){
        return SOUTH;
    }

    private static final float MIN_PITCH = 20f;
    private static final float MAX_PITCH = 70f;
    private static final float MIN_DISTANCE = 80f;
    private static final float MAX_DISTANCE = 500f;
    private static final float DEFAULT_ROTATE_PITCH = 60f;
    private static final float DEFAULT_ROTATE_ANGLE = 45f;

    private static final int NORTH = 10;
    private static final int EAST = 11;
    private static final int SOUTH = 12;
    private static final int WEST = 13;

}
