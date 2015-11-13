package uk.co.jakestanley.commander.rendering.world.threedimensional.shaders;

/**
 * Created by jp-st on 12/11/2015.
 */
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE     = "src/uk/co/jakestanley/commander/rendering/world/threedimensional/shaders/glsl/VertexShader.glsl";
    private static final String FRAGMENT_FILE   = "src/uk/co/jakestanley/commander/rendering/world/threedimensional/shaders/glsl/FragmentShader.glsl";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position"); // 0 is where we stored the position attribute
    }
}
