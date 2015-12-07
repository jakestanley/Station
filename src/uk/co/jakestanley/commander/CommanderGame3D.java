package uk.co.jakestanley.commander;

import lombok.Getter;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.gui.GuiController;
import uk.co.jakestanley.commander.input.InputController;
import uk.co.jakestanley.commander.rendering.DisplayManager;
import uk.co.jakestanley.commander.rendering.Renderer;
import uk.co.jakestanley.commander.rendering.entities.Light;
import uk.co.jakestanley.commander.rendering.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.entities.world.Character;
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
public class CommanderGame3D {

    @Getter private static boolean debug;
    @Getter private static int displayWidth; // TODO allow these to be changed with arguments
    @Getter private static int displayHeight;

    private static InputController inputController;
    private static SceneController sceneController;
    private static GuiController guiController;

    public static ThreeDimensionalRenderer worldRenderer;
    public static Renderer guiRenderer;

    public static Loader loader;
    public static StaticShader shader;

    // learning
    public static RenderEntity floor;
    public static List<Light> lights;
    public static Camera camera;
    public static Character character;
    public static Ship ship;

    public CommanderGame3D(boolean debug, int displayWidth, int displayHeight){
        this.debug = debug;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        // initialise game logic objects
        inputController = new InputController();
        sceneController = new SceneController();
        sceneController.addMobileEntity(new Crewman("terry", 0f, 0f, 0f)); // TODO put in method and generate names
        guiController = new GuiController();

        // initialise the renderer objects
        guiRenderer = new GuiRenderer(displayWidth, displayHeight);

    }

    protected void init() {

        DisplayManager.createDisplay();
        loader = new Loader(); // requires the OpenGL context
        shader = new StaticShader();
        worldRenderer = new ThreeDimensionalRenderer(20, 20, 800, 600, shader);

//        testModel = loader.loadToVAO(cubeVertices, cubeIndices, cubeTextureCoordinates); // load vertices // TODO make better - consider having an untextured model for low poly?

        lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3f(5,30,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(10,30,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(0,100,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(-10,30,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(0,30,-10), new Vector3f(1,1,1)));

        floor = new Floor(100, 50); // use default for now
        character = new Character("stan");
        ship = new Ship("gatlinburg");

//
//        testModel = ObjLoader.loadObjModel("robot", loader, false);
//        testRenderEntity = new RenderEntity(testModel, new Vector3f(0, 0, -25), 0, 0, 0, 1); // untextured


//        camera = new Camera(new Vector3f(0,0,0), 0, 0, 0);
        camera = new Camera(new Vector3f(55,80,155), 35, -45, 0);
//        camera = new Camera();
    }

    public void update(){
        sceneController.update();
        inputController.update();
        guiController.update();
        updateRenderers();
    }

    private void updateRenderers() {
        worldRenderer.update();
    } // TODO revise

    public void render(){

        camera.move(); // TODO when i sort everything out, maintain this order
        shader.start();
        for (Iterator<Light> iterator = lights.iterator(); iterator.hasNext(); ) {
            Light next =  iterator.next();
            shader.loadLight(next);
        }
        shader.loadViewMatrix(camera);

        // render ship
        for (Iterator<RenderEntity> it = ship.getVisibleRenderEntities().iterator(); it.hasNext(); ) {
            RenderEntity next = it.next();
            worldRenderer.render(next, shader);
        }

        // render character
        for (Iterator<RenderEntity> it = character.getVisibleRenderEntities().iterator(); it.hasNext(); ) {
            RenderEntity next = it.next();
            worldRenderer.render(next, shader);
        }

        worldRenderer.render(floor, shader);


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
