package uk.co.jakestanley.commander.rendering.shaders;

import org.lwjgl.util.vector.Matrix4f;
import uk.co.jakestanley.commander.rendering.world.entities.Camera;
import uk.co.jakestanley.commander.rendering.world.tools.Maths;

/**
 * Created by jake on 12/12/2015.
 */
public class SkyboxShader extends ShaderProgram {

    private int location_projectionMatrix;

    private int location_viewMatrix;
    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        loadMatrix(location_viewMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_viewMatrix = getUniformLocation("viewMatrix");
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
    }

    private static final String VERTEX_FILE = SHADERS_DIRECTORY + "glsl/skyboxVertexShader.glsl";
    private static final String FRAGMENT_FILE = SHADERS_DIRECTORY + "glsl/skyboxFragmentShader.glsl";

}
