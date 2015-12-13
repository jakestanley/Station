package uk.co.jakestanley.commander.rendering.world.tools;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.rendering.world.entities.Camera;

/**
 * Created by jp-st on 13/11/2015.
 */
public class Maths {

    // view matrix variables
    public static final float NEAR_PLANE = 0.01f;
    public static final float FAR_PLANE = 5000f;
    public static final float ORTHOGRAPHIC_NEAR_PLANE = 1000f;
    public static final float ORTHOGRAPHIC_FAR_PLANE = 2800f;

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

    public static Matrix4f createPerspectiveProjectionMatrix(float fieldOfView) {

        Matrix4f perspectiveProjectionMatrix = new Matrix4f();

        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(fieldOfView / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        perspectiveProjectionMatrix.m00 = x_scale;
        perspectiveProjectionMatrix.m11 = y_scale;
        perspectiveProjectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        perspectiveProjectionMatrix.m23 = -1;
        perspectiveProjectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        perspectiveProjectionMatrix.m33 = 0;

        return perspectiveProjectionMatrix;

    }

    public static Matrix4f createOrthographicProjectionMatrix(){

        Matrix4f orthographicProjectionMatrix = new Matrix4f();

        float left = 0.0f;
        float right = Display.getWidth();
        float top = Display.getHeight();
        float bottom = 0.0f;
        float near = ORTHOGRAPHIC_NEAR_PLANE;
        float far = ORTHOGRAPHIC_FAR_PLANE;

        orthographicProjectionMatrix.m00 = 2.0f / (right - left);
        orthographicProjectionMatrix.m01 = 0.0f;
        orthographicProjectionMatrix.m02 = 0.0f;
        orthographicProjectionMatrix.m03 = 0.0f;

        orthographicProjectionMatrix.m10 = 0.0f;
        orthographicProjectionMatrix.m11 = 2.0f / (top - bottom);
        orthographicProjectionMatrix.m12 = 0.0f;
        orthographicProjectionMatrix.m13 = 0.0f;

        orthographicProjectionMatrix.m20 = 0.0f;
        orthographicProjectionMatrix.m21 = 0.0f;
        orthographicProjectionMatrix.m22 = -2.0f / (far - near);
        orthographicProjectionMatrix.m23 = 0.0f;

        orthographicProjectionMatrix.m30 = -(right + left  ) / (right - left  );
        orthographicProjectionMatrix.m31 = -(top   + bottom) / (top   - bottom);
        orthographicProjectionMatrix.m32 = -(far   + near  ) / (far   - near  );
        orthographicProjectionMatrix.m33 = 1.0f;

        orthographicProjectionMatrix = orthographicProjectionMatrix.scale(new Vector3f(10,10,10));

        return orthographicProjectionMatrix;

    }

    public static Vector3f addVectors(Vector3f target, Vector3f adder){
        Vector3f sum = new Vector3f();
        sum.setX(target.getX() + adder.getX());
        sum.setY(target.getY() + adder.getY());
        sum.setZ(target.getZ() + adder.getZ());
        return sum;
    }

    public static Vector3f addVectors(Vector3f target, Vector3f adder1, Vector3f adder2){
        Vector3f sum = new Vector3f();
        sum.setX(target.getX() + adder1.getX() + adder2.getX());
        sum.setY(target.getY() + adder1.getY() + adder2.getY());
        sum.setZ(target.getZ() + adder1.getZ() + adder2.getZ());
        return sum;
    }

    public static Vector3f scaleVector(Matrix3f scalingMatrix, Vector3f vector){
        Vector3f product = new Vector3f();
        product.setX((scalingMatrix.m00 * vector.getX()) + (scalingMatrix.m10 * vector.getX()) + (scalingMatrix.m20 * vector.getX()));
        product.setY((scalingMatrix.m01 * vector.getY()) + (scalingMatrix.m11 * vector.getY()) + (scalingMatrix.m21 * vector.getY()));
        product.setZ((scalingMatrix.m02 * vector.getX()) + (scalingMatrix.m12 * vector.getZ()) + (scalingMatrix.m22 * vector.getZ()));
        return product;
    }

}
