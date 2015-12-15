package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.Getter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Main;

import java.security.Key;

@Getter
public class Camera {

    private float distanceFromPlayer = 200f;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f offset = new Vector3f(0, 0, 0);
    private float pitch = 20;
    private float yaw = 0;
    private float roll;

    private Renderable player;

    public Camera(Renderable player) {
        this.player = player;
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
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
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
            offset.x -= 1f;
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
        float zoomLevel = Mouse.getDWheel() * 0.1f;
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch() {
        if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundPlayer() {
        if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
            float angleChange = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }

}
