package uk.co.jakestanley.commander;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.rendering.DisplayManager;
import uk.co.jakestanley.commander.rendering.Renderer;
import uk.co.jakestanley.commander.rendering.entities.Light;
import uk.co.jakestanley.commander.rendering.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.gui.GuiRenderer;
import uk.co.jakestanley.commander.rendering.entities.Camera;
import uk.co.jakestanley.commander.rendering.world.threedimensional.ThreeDimensionalRenderer;
import uk.co.jakestanley.commander.rendering.world.threedimensional.Loader;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.ObjLoader;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.threedimensional.shaders.StaticShader;
import uk.co.jakestanley.commander.rendering.world.threedimensional.textures.ModelTexture;
import uk.co.jakestanley.commander.scene.SceneController;
import uk.co.jakestanley.commander.scene.entities.mobiles.Crewman;

/**
 * Created by jp-st on 10/11/2015.
 */
public class CommanderGame3D extends CommanderGame {

    public static SceneController sceneController;

    public static ThreeDimensionalRenderer worldRenderer;
    public static Renderer guiRenderer;

    public static Loader loader;
    public static StaticShader shader;

    // learning
    public static RawModel testModel;
    public static ModelTexture texture;
    public static TexturedModel testTexturedModel;
    public static RenderEntity testRenderEntity;
    public static Light testLight;
    public static Camera camera;

    public CommanderGame3D(boolean debug){
        super(debug, RENDER_IN_3D);

        // initialise game logic objects
        sceneController = new SceneController();
        sceneController.addMobileEntity(new Crewman("terry", 0f, 0f, 0f)); // TODO put in method and generate names

        // initialise the renderer objects
        guiRenderer = new GuiRenderer(Main.getGame().getDisplayWidth(), Main.getGame().getDisplayHeight());

    }

    protected void initSpecifics() { // TODO CONSIDER may not be needed

    }

    protected void initRenderers() {

        DisplayManager.createDisplay();
        loader = new Loader(); // requires the OpenGL context
        shader = new StaticShader();
        worldRenderer = new ThreeDimensionalRenderer(20, 20, 800, 600, shader);

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

        float[] cubeVertices = {
                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,0.5f,-0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                -0.5f,-0.5f,0.5f,
                -0.5f,0.5f,0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,-0.5f,0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f

        };

        float[] cubeTextureCoordinates = {

                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0


        };

        int[] cubeIndices = {  // TODO remove this crap
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22

        };

//        testModel = loader.loadToVAO(cubeVertices, cubeIndices, cubeTextureCoordinates); // load vertices // TODO make better - consider having an untextured model for low poly?

        // TODO use a const or something and make sure that the models and textures have the same names
        testModel = ObjLoader.loadObjModel("stan", loader, true);
        texture = new ModelTexture(loader.loadTexture("stan")); // TODO untextured model? shaded model?
        testTexturedModel = new TexturedModel(testModel, texture);
        testRenderEntity = new RenderEntity(testTexturedModel, new Vector3f(0,0,-25),0,0,0,1);
        testLight = new Light(new Vector3f(0,0,0), new Vector3f(1,1,1));
//
//        testModel = ObjLoader.loadObjModel("robot", loader, false);
//        testRenderEntity = new RenderEntity(testModel, new Vector3f(0, 0, -25), 0, 0, 0, 1); // untextured


        camera = new Camera();
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
        testRenderEntity.increasePosition(0,0,0); // TODO put these somewhere more accessible. clean up old 2D stuff in new branch and track only one kind of rendering
        testRenderEntity.increaseRotation(0,0,2f);
        camera.move(); // TODO when i sort everything out, maintain this order
        shader.start();
//        shader.loadLight(testLight);
        shader.loadViewMatrix(camera);
        worldRenderer.render(testRenderEntity, shader);
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
