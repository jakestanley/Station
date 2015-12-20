package uk.co.jakestanley.commander.rendering.shaders;

import org.lwjgl.util.vector.Matrix4f;
import uk.co.jakestanley.commander.rendering.world.entities.Camera;
import uk.co.jakestanley.commander.rendering.world.entities.Light;
import uk.co.jakestanley.commander.rendering.world.tools.Maths;

/**
 * Created by jp-st on 12/11/2015.
 */
public class StaticShader extends ShaderProgram {

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position"); // 0 is where we stored the globalPosition attribute
        bindAttribute(1, "textureCoordinates");
        bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = getUniformLocation("transformationMatrix");
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_viewMatrix = getUniformLocation("viewMatrix");
        location_lightPosition = getUniformLocation("lightPosition");
        location_lightColour = getUniformLocation("lightColour");
        location_shineDamper = getUniformLocation("shineDamper");
        location_reflectivity = getUniformLocation("reflectivity");
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        loadMatrix(location_projectionMatrix, projection);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadLight(Light light){
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColour, light.getColour());
    }

    public void loadShineVariables(float damper, float reflectivity){
        loadFloat(location_shineDamper, damper);
        loadFloat(location_reflectivity, reflectivity);
    }

    private static final String VERTEX_FILE     = SHADERS_DIRECTORY + "glsl/genericVertexShader.glsl";
    private static final String FRAGMENT_FILE   = SHADERS_DIRECTORY + "glsl/genericFragmentShader.glsl";
}
