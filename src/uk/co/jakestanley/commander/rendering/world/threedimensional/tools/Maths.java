package uk.co.jakestanley.commander.rendering.world.threedimensional.tools;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

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

}
