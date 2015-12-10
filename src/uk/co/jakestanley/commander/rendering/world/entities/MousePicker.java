package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.*;
import uk.co.jakestanley.commander.rendering.exceptions.DoesNotIntersectException;
import uk.co.jakestanley.commander.rendering.world.tools.Maths;
import org.lwjgl.opengl.Display;

/**
 * Created by jake on 08/12/2015.
 */
public class MousePicker { // TODO make this abstract and use it for picking other things, e.g placed objects, walls, etc

    private Camera camera;
    @Setter private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    @Getter private Vector3f currentRay;

    private Floor floor;

    public MousePicker(Camera camera, Matrix4f projectionMatrix){
        this.camera = camera;
        this.projectionMatrix = projectionMatrix;
        this.viewMatrix = Maths.createViewMatrix(camera);
    }

    public void update(){
        viewMatrix = Maths.createViewMatrix(camera); // TODO CONSIDER can i reuse the existing view matrix?
        currentRay = calculateMouseRay();
    }

    /**
     * Call this after the update method
     * @param floor
     * @return
     */
    public Vector2f getIntersection(Floor floor) throws DoesNotIntersectException { // TODO make this work abstractly with render entities

        this.floor = floor;

//        System.out.println("Camera position: " + camera.getPosition().toString());
//        System.out.println("Ray position: " + currentRay.toString());
//        System.out.println("Floor position" + floor.getPosition().toString());

        Vector3f intersectionPoint = null;

        if(intersectionInRange(0, RAY_RANGE, currentRay)){
            intersectionPoint = binarySearch(0, 0, RAY_RANGE, currentRay);
            return new Vector2f(intersectionPoint.getX(), intersectionPoint.getZ());
        }

        throw new DoesNotIntersectException("Ray does not intersect floor");

    }

    private Vector3f calculateMouseRay(){
        float mouseX = Mouse.getX();
        float mouseY = Mouse.getY();
        Vector2f normalisedCoords = getNormalisedDeviceCoordinates(mouseX, mouseY);
        Vector4f clipCoordinates = new Vector4f(    normalisedCoords.getX(),
                                                    normalisedCoords.getY(),
                                                    -1, -1);
        Vector4f eyeCoordinates = convertToEyeSpaceCoordinates(clipCoordinates);
        Vector3f worldRay = convertToWorldCoordinates(eyeCoordinates);
        return worldRay;
    }

    private Vector4f convertToEyeSpaceCoordinates(Vector4f clipCoordinates){
        Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
        Vector4f eyeCoordinates = Matrix4f.transform(invertedProjection, clipCoordinates, null);
        return new Vector4f(eyeCoordinates.getX(), eyeCoordinates.getY(), -1f, 0f);
    }

    private Vector3f convertToWorldCoordinates(Vector4f eyeCoords){
        Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
        Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
        Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        mouseRay.normalise();
        return mouseRay;
    }

    private Vector2f getNormalisedDeviceCoordinates(float mouseX, float mouseY){
        float x = (2f * mouseX) / Display.getWidth() - 1f;
        float y = (2f * mouseY) / Display.getHeight() - 1f;
        return new Vector2f(x, y);
    }

    private boolean intersectionInRange(float start, float end, Vector3f ray){ // currently only works for floor

        Vector3f startPoint = getPointOnRay(ray, start);
        Vector3f endPoint = getPointOnRay(ray, end);

        return (!pointIsUnder(startPoint, floor) && pointIsUnder(endPoint, floor));

    }

    private Vector3f getPointOnRay(Vector3f ray, float distance){
        float d = distance;
        Vector3f cPos = camera.getPosition();
        Vector3f start = new Vector3f(cPos.getX(), cPos.getY(), cPos.getZ());
        Vector3f rayScaled = new Vector3f(ray.getX() * d, ray.getY() * d, ray.getZ() * d);
        return Vector3f.add(start, rayScaled, null);
    }

    private boolean pointIsUnder(Vector3f point, Floor floor){ // TODO adjust to use render entities
        return (point.getY() < floor.getPosition().getY());
    }

    private Vector3f binarySearch(int count, float start, float end, Vector3f ray){
        float half = start + ((end - start) / 2f);
        if(count >= MAX_SEARCH_RECURSION){
            return getPointOnRay(ray, half);
        }
        if(intersectionInRange(start, half, ray)){
            return binarySearch(count+1, start, half, ray);
        } else {
            return binarySearch(count+1, half, end, ray);
        }
    }

    private static final float FLOOR_INTERSECTION_TOLERANCE = 1f; // TODO use this for efficiency
    private static final int MAX_SEARCH_RECURSION = 200;
    private static final float RAY_RANGE = 600;

}
