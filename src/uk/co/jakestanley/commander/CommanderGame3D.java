package uk.co.jakestanley.commander;

import org.lwjgl.opengl.Display;
import uk.co.jakestanley.commander.rendering.DisplayManager;
import uk.co.jakestanley.commander.rendering.Renderer;
import uk.co.jakestanley.commander.rendering.gui.GuiRenderer;
import uk.co.jakestanley.commander.rendering.world.threedimensional.ThreeDimensionalRenderer;
import uk.co.jakestanley.commander.rendering.world.threedimensional.Loader;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.threedimensional.shaders.ShaderProgram;
import uk.co.jakestanley.commander.rendering.world.threedimensional.shaders.StaticShader;
import uk.co.jakestanley.commander.rendering.world.threedimensional.textures.ModelTexture;

/**
 * Created by jp-st on 10/11/2015.
 */
public class CommanderGame3D extends CommanderGame {

    public static ThreeDimensionalRenderer worldRenderer;
    public static Renderer guiRenderer;

    public static Loader loader;
    public static ShaderProgram shader;
    public static RawModel testModel;
    public static ModelTexture texture;
    public static TexturedModel testTexturedModel;

    public CommanderGame3D(boolean debug){
        super(debug, RENDER_IN_3D);

        // initialise the renderer objects
        worldRenderer = new ThreeDimensionalRenderer(20, 20, 800, 600);
        guiRenderer = new GuiRenderer(Main.getGame().getDisplayWidth(), Main.getGame().getDisplayHeight());

    }

    protected void initSpecifics() { // TODO CONSIDER may not be needed

    }

    protected void initRenderers() {
        DisplayManager.createDisplay();
        loader = new Loader(); // requires the OpenGL context
        shader = new StaticShader();

        // testing testing
        float[] vertices = {
                -0.5f,  0.5f,   0f, // v0
                -0.5f,  -0.5f,  0f, // v1
                0.5f,   -0.5f,  0f, // v2
                0.5f,    0.5f,  0f  // v3
        };

        int[] indices = { // must be in counter clockwise order
                0, 1, 3, // upper left triangle
                2, 3, 1  // lower right triangle
        };

        float[] textureCoordinates = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        testModel = loader.loadToVAO(vertices, indices, textureCoordinates); // load vertices // TODO make better - consider having an untextured model for low poly?
        texture = new ModelTexture(loader.loadTexture("basic"));
        testTexturedModel = new TexturedModel(testModel, texture);
    }

    @Override
    protected void updateInput() {
        Main.getGame().getInputController().update();
    }

    @Override
    protected void updateRenderers() {
        worldRenderer.update();
    }

    public void render(){
        shader.start();
        worldRenderer.render(testTexturedModel);
        shader.stop();
        DisplayManager.updateDisplay(); // last part of update loop
    }

    public boolean hasCloseCondition() {
        return Display.isCloseRequested();
    }

    public void close() {
        shader.cleanup();
        loader.cleanup();
        DisplayManager.closeDisplay();
    }

}
