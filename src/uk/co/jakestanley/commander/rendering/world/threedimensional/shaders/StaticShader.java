package uk.co.jakestanley.commander.rendering.world.threedimensional.shaders;

import org.lwjgl.util.vector.Matrix4f;
import uk.co.jakestanley.commander.rendering.entities.Camera;
import uk.co.jakestanley.commander.rendering.world.threedimensional.tools.Maths;

/**
 * Created by jp-st on 12/11/2015.
 */
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE     = "src/uk/co/jakestanley/commander/rendering/world/threedimensional/shaders/glsl/VertexShader.glsl";
    private static final String FRAGMENT_FILE   = "src/uk/co/jakestanley/commander/rendering/world/threedimensional/shaders/glsl/FragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position"); // 0 is where we stored the position attribute
        bindAttribute(1, "textureCoordinates");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
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
}
