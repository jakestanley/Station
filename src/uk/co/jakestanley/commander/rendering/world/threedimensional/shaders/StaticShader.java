package uk.co.jakestanley.commander.rendering.world.threedimensional.shaders;

/**
 * Created by jp-st on 12/11/2015.
 */
public class StaticShader extends Shader {

    private static final String VERTEX_FILE     = "src/uk/co/jakestanley/commander/rendering/world/threedimensional/shaders/glsl/VertexShader";
    private static final String FRAGMENT_FILE   = "src/uk/co/jakestanley/commander/rendering/world/threedimensional/shaders/glsl/FragmentShader";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
