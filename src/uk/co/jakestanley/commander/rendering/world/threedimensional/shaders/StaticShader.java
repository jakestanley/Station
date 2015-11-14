package uk.co.jakestanley.commander.rendering.world.threedimensional.shaders;

import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by jp-st on 12/11/2015.
 */
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE     = "src/uk/co/jakestanley/commander/rendering/world/threedimensional/shaders/glsl/VertexShader.glsl";
    private static final String FRAGMENT_FILE   = "src/uk/co/jakestanley/commander/rendering/world/threedimensional/shaders/glsl/FragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;

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
        location_transformationMatrix = getUniformLocation("transformationMatrix");
        location_projectionMatrix = getUniformLocation("projectionMatrix");
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        loadMatrix(location_projectionMatrix, matrix);
    }
}
