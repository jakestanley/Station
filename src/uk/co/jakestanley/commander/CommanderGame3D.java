package uk.co.jakestanley.commander;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.rendering.DisplayManager;
import uk.co.jakestanley.commander.rendering.Renderer;
import uk.co.jakestanley.commander.rendering.entities.Light;
import uk.co.jakestanley.commander.rendering.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.entities.world.Floor;
import uk.co.jakestanley.commander.rendering.entities.world.Ship;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    public static RawModel testModel2;
    public static RawModel testModel3;
    public static RawModel testModel4;
    public static RawModel testModel5;
    public static ModelTexture texture;
    public static ModelTexture texture2;
    public static ModelTexture texture3;
    public static ModelTexture texture4;
    public static ModelTexture texture5;
    public static TexturedModel testTexturedModel;
    public static TexturedModel testTexturedModel2;
    public static TexturedModel testTexturedModel3;
    public static TexturedModel testTexturedModel4;
    public static TexturedModel testTexturedModel5;
    public static RenderEntity testRenderEntity;
    public static RenderEntity testRenderEntity2;
    public static RenderEntity testRenderEntity3;
    public static RenderEntity testRenderEntity4;
    public static RenderEntity testRenderEntity5;
    public static RenderEntity floor;
    public static List<Light> lights;
    public static Camera camera;
    public static Ship ship;

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
        testModel = ObjLoader.loadObjModel("stan_b", loader, true);
        texture = new ModelTexture(loader.loadTexture("paint_smooth_b")); // TODO untextured model? shaded model?
        testTexturedModel = new TexturedModel(testModel, texture);
        testRenderEntity = new RenderEntity(testTexturedModel, new Vector3f(0,0,-25),0,0,0,1);

        lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3f(5,30,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(10,30,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(0,100,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(-10,30,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(0,30,-10), new Vector3f(1,1,1)));

        testModel2 = ObjLoader.loadObjModel("stan_b", loader, ObjLoader.TEXTURED);
        texture2 = new ModelTexture(loader.loadTexture("paint_smooth_b")); // TODO untextured model? shaded model?
        testTexturedModel2 = new TexturedModel(testModel2, texture2);
        testRenderEntity2 = new RenderEntity(testTexturedModel2, new Vector3f(0,0,0),0,90f,0,1);

//        testModel3 = ObjLoader.loadObjModel("stan_b", loader, ObjLoader.TEXTURED);
//        texture3 = new ModelTexture(loader.loadTexture("paint_smooth_b")); // TODO untextured model? shaded model?
//        testTexturedModel3 = new TexturedModel(testModel3, texture3);
//        testRenderEntity3 = new RenderEntity(testTexturedModel3, new Vector3f(25,0,-75),0,180f,0,1);
//
//        testModel4 = ObjLoader.loadObjModel("Gatlinburg_Top", loader, ObjLoader.TEXTURED);
//        texture4 = new ModelTexture(loader.loadTexture("Gatlinburg_Top")); // TODO untextured model? shaded model?
//        testTexturedModel4 = new TexturedModel(testModel4, texture4);
//        testRenderEntity4 = new RenderEntity(testTexturedModel4, new Vector3f(20,20,-75),0,0,0,3);
//
//        testModel5 = ObjLoader.loadObjModel("Gatlinburg_Starboard", loader, ObjLoader.TEXTURED);
//        texture5 = new ModelTexture(loader.loadTexture("Gatlinburg")); // TODO untextured model? shaded model?
//        testTexturedModel5 = new TexturedModel(testModel5, texture5);
//        testRenderEntity5 = new RenderEntity(testTexturedModel5, new Vector3f(20,20,-75),0,0,0,3);

        floor = new Floor(100, 50); // use default for now
        ship = new Ship();

//
//        testModel = ObjLoader.loadObjModel("robot", loader, false);
//        testRenderEntity = new RenderEntity(testModel, new Vector3f(0, 0, -25), 0, 0, 0, 1); // untextured


//        camera = new Camera(new Vector3f(0,0,0), 0, 0, 0);
        camera = new Camera(new Vector3f(55,80,155), 35, -45, 0);
//        camera = new Camera();
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
//        testRenderEntity2.increasePosition(0,0,-0.06f);
        testRenderEntity.increaseRotation(0,0,0);
        camera.move(); // TODO when i sort everything out, maintain this order
        shader.start();
        for (Iterator<Light> iterator = lights.iterator(); iterator.hasNext(); ) {
            Light next =  iterator.next();
            shader.loadLight(next);
        }
        shader.loadViewMatrix(camera);
//        worldRenderer.render(testRenderEntity, shader);
        worldRenderer.render(testRenderEntity2, shader);
//        worldRenderer.render(testRenderEntity3, shader);
//        worldRenderer.render(testRenderEntity4, shader);
//        worldRenderer.render(testRenderEntity5, shader);
        worldRenderer.render(floor, shader);

        // render ship
        for (Iterator<RenderEntity> it = ship.getVisibleRenderEntities().iterator(); it.hasNext(); ) {
            RenderEntity renderEntity = it.next();
            worldRenderer.render(renderEntity, shader);
        }



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
