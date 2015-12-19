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

    private static final float MIN_PITCH = 20f;
    private static final float MAX_PITCH = 70f;
    private static final float BASE_SCROLL = 1f;
    private static final float DEFAULT_ROTATE_PITCH = 60f;
    private static final float DEFAULT_ROTATE_ANGLE = 45f;

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
        this.scroll = BASE_SCROLL;
        this.pitch = DEFAULT_ROTATE_PITCH;
        this.angle = DEFAULT_ROTATE_ANGLE;
        this.rotate = rotate;
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
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){ // forward

        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){ // left

        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){ // back

        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){ // right

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

}
