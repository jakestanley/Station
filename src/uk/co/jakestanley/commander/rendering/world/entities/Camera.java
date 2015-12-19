package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.Getter;
import lombok.ToString;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.world.tools.Maths;

import java.security.Key;

@Getter @ToString
public class Camera {

    // https://github.com/Ritsu95/3D-Game-Engine-ThinMatrix/blob/master/3D%20Game%20Engine/src/entities/Camera.java // TODO adapted from this code, modify

    private static final float MIN_PITCH = 20f;
    private static final float MAX_PITCH = 70f;
    private static final float BASE_SCROLL = 1f;

    private float distanceFromPlayer = 100f;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f offset = new Vector3f(0, 0, 0);
    private float pitch = 20;
    private float yaw = 0;
    private float roll;
    private float scroll;

    private Renderable player;

    public Camera(Renderable player) {
        this.player = player;
        this.scroll = BASE_SCROLL;
    }

    public void move() {
        Main.getGame().getShip().resetVisibleRenderEntities(); // TODO only check when the view has changed?
        getKeyboardInput();
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        yaw = Maths.boundAngle(180 - (player.getRotY() + angleAroundPlayer));

//        System.out.println("Yaw: " + yaw);
//        System.out.println("Pitch: " + pitch);
    }

    private void calculateCameraPosition(float horizDistance, float verticDistance) {
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getGlobalPosition().x - offsetX + offset.getX();
        position.z = player.getGlobalPosition().z - offsetZ + offset.getZ();
        position.y = player.getGlobalPosition().y + verticDistance;
    }

    private void getKeyboardInput(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){ // forward
//            float modX = (float) (scroll * Math.sin(yaw));
//            float modZ = (float) (scroll * Math.sin(yaw));
//            float newX = modX + offset.getX();
//            float newZ = modZ + offset.getZ();
//            offset.setX(newX);
//            offset.setZ(newZ);

            System.out.println("mod yaw: " + yaw % 360);

//            System.out.println("modX: " + modX + ", modZ: " + modZ + ", yaw: " + yaw);



        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){ // left

        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){ // back

        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){ // right

        }
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        float zoomLevel = Mouse.getDWheel() * 0.1f; // TODO make constant and reduce scroll speed. set a max/min
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch() {
        if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
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
        if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
            float angleChange = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
            angleAroundPlayer = Maths.boundAngle(angleAroundPlayer);
            System.out.println("angle: " + angleAroundPlayer);
        }
    }

}
