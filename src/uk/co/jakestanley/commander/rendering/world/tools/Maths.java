package uk.co.jakestanley.commander.rendering.world.tools;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.rendering.world.entities.Camera;

/**
 * Created by jp-st on 13/11/2015.
 */
public class Maths {

    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale){ // only good for uniform scale
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity(); // 1s on diagonal
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix); // apply pitch to source matrix output source matrix
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix); // apply yaw
        Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0,0,1), viewMatrix, viewMatrix); // apply roll TODO test this
        Vector3f cameraPosition = camera.getPosition();
        Vector3f negativeCameraPosition = new Vector3f(-cameraPosition.getX(), -cameraPosition.getY(), -cameraPosition.getZ()); // moving world in opposite direction
        Matrix4f.translate(negativeCameraPosition, viewMatrix, viewMatrix);
        return viewMatrix;
    }

}
