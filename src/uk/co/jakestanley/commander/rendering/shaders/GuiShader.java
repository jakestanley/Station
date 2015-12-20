package uk.co.jakestanley.commander.rendering.shaders;

import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by jake on 19/12/2015.
 */
public class GuiShader extends ShaderProgram {

    private int location_transformationMatrix;

    public GuiShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    private static final String VERTEX_FILE     = SHADERS_DIRECTORY + "glsl/guiVertexShader.glsl";
    private static final String FRAGMENT_FILE   = SHADERS_DIRECTORY + "glsl/guiFragmentShader.glsl";
}
