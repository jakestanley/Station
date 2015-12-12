package uk.co.jakestanley.commander;

import lombok.Getter;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.gui.GuiController;
import uk.co.jakestanley.commander.input.InputController;
import uk.co.jakestanley.commander.rendering.DisplayManager;
import uk.co.jakestanley.commander.rendering.exceptions.DoesNotIntersectException;
import uk.co.jakestanley.commander.rendering.world.Renderer;
import uk.co.jakestanley.commander.rendering.world.SkyboxRenderer;
import uk.co.jakestanley.commander.rendering.world.entities.*;
import uk.co.jakestanley.commander.rendering.gui.GuiRenderer;
import uk.co.jakestanley.commander.rendering.world.Loader;
import uk.co.jakestanley.commander.rendering.world.entities.Character;
import uk.co.jakestanley.commander.rendering.world.entities.boundaries.Floor;
import uk.co.jakestanley.commander.rendering.world.entities.boundaries.Wall;
import uk.co.jakestanley.commander.rendering.world.models.ObjLoader;
import uk.co.jakestanley.commander.rendering.world.shaders.StaticShader;
import uk.co.jakestanley.commander.scene.SceneController;
import uk.co.jakestanley.commander.scene.entities.mobiles.Crewman;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by jp-st on 10/11/2015.
 */
@Getter
public class Game3D {

    private boolean debug;
    private boolean caching;
    private int projection;
    private int displayWidth; // TODO allow these to be changed with arguments
    private int displayHeight;

    private Random random;
    private InputController inputController;
    private MousePicker mousePicker;
    private SceneController sceneController;
    private GuiController guiController;

    public Renderer worldRenderer;
    public SkyboxRenderer skyboxRenderer;
    public GuiRenderer guiRenderer;

    public ObjLoader objLoader; // TODO make private with getters only
    public Loader loader;

    public static StaticShader shader;

    // entities
    private List<Light> lights;
    private List<Wall> walls;
    private List<Renderable> renderables;

    // entities you should probably keep track of
    private Camera camera;
    private RenderEntity floor;
    private Ship ship;
    private Character character;
    private Wall wall;

    public Game3D(boolean debug, boolean caching, int projection, int displayWidth, int displayHeight){
        this.debug = debug;
        this.caching = caching;
        this.projection = projection;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        // initialise game logic objects
        random = new Random();
        objLoader = new ObjLoader(caching);
        inputController = new InputController();
        sceneController = new SceneController();
        sceneController.addMobileEntity(new Crewman("terry", 0f, 0f, 0f)); // TODO put in method and generate names
        guiController = new GuiController();

        // initialise the renderer objects
        guiRenderer = new GuiRenderer(displayWidth, displayHeight);

    }

    protected void init() {

        DisplayManager.createDisplay();
        loader = new Loader(Loader.ENABLE_CACHING); // requires the OpenGL context
        shader = new StaticShader();
        if(Renderer.ORTHOGRAPHIC == projection){
            camera = new Camera(new Vector3f(55,80,155), 35, -45, 0);
        } else {
            camera = new Camera(new Vector3f(55, 90, 155), 60, -45, 0);
        }

        // init renderers
        worldRenderer = new Renderer(shader, Main.getGame().projection);
        worldRenderer.init();
        skyboxRenderer = new SkyboxRenderer(loader, worldRenderer.getProjectionMatrix());
        mousePicker = new MousePicker(camera, worldRenderer.getProjectionMatrix());

        // initialise lights - TODO get from scene controller?
        lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3f(5,30,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(10,30,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(0,100,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(-10,30,0), new Vector3f(1,1,1)));
        lights.add(new Light(new Vector3f(0,30,-10), new Vector3f(1,1,1)));

        walls = new ArrayList<Wall>();

        // initialise visible entities
        renderables = new ArrayList<Renderable>();
        floor = new Floor(100, 50); // use default for now // TODO make a proper entity that extends renderable
        ship = new Ship("gatlinburg", new Vector3f(0,0,0));
        character = new Character("stan", new Vector3f(0, 0, 0), 0, 0, 1, false);

        // add renderables
        renderables.add(ship);
        renderables.add(character);

        // create camera
    }

    public void update(){
        camera.move(); // TODO when i sort everything out, maintain this order
        inputController.update();
        mousePicker.update(); // TODO turn this off until it's needed
        sceneController.update();
        try{
            Vector2f intersection = mousePicker.getIntersection((Floor) floor); // TODO if detecting mouse, or if in build/select mode

            if(Mouse.isButtonDown(0) && wall == null){ // if left mouse button down // TODO move this into BuildController
                // if the mouse is down and a wall hasn't been created yet, start a wall
                wall = new Wall(intersection);
                wall.setEnd(intersection);
            } else if(Mouse.isButtonDown(0)) {
                // if the mouse mutton is down, update the wall end coordinates
                wall.setEnd(intersection);
            } else if(wall != null){
                // if the mouse is not selected and there is a wall set, place the wall and set the reference to null
                // TODO CONSIDER should I update the wall end here? optimise...
                wall.place(); // TODO add to an entity list somewhere
                walls.add(wall);
                wall = null;
            }

        } catch (DoesNotIntersectException d){
            // no intersection
        }
//        try {
//            Vector2f mousePos = mousePicker.getIntersection((Floor) floor);
//            Vector3f cPos = character.getPosition(); // TODO remove after testing
//            Vector3f newPos = new Vector3f(mousePos.getX(), cPos.getZ(), mousePos.getY());
//            character.updatePosition(newPos);
//        } catch (DoesNotIntersectException d){
//            d.printStackTrace(); // TODO put this back in
//        }
        guiController.update();
        worldRenderer.update();
    }

    public void render(){


        shader.start();

        // load the lights
        for (Iterator<Light> iterator = lights.iterator(); iterator.hasNext(); ) {
            Light next =  iterator.next();
            shader.loadLight(next);
        }

        shader.loadViewMatrix(camera);

        // batch up the render entities
        for (Iterator<Renderable> iterator = renderables.iterator(); iterator.hasNext(); ) {
            Renderable renderable = iterator.next();
            for (Iterator<RenderEntity> it = renderable.getVisibleRenderEntities().iterator(); it.hasNext(); ) {
                RenderEntity renderEntity = it.next();
                worldRenderer.addToRenderQueue(renderEntity);
            }
        }

        worldRenderer.addToRenderQueue(floor);
        for (Iterator<Wall> iterator = walls.iterator(); iterator.hasNext(); ) {
            Wall nextWall = iterator.next();
            worldRenderer.addToRenderQueue(nextWall);
        }

        // add under construction wall
        if(wall != null){
            worldRenderer.addToRenderQueue(wall); // TODO use transparency/build shader
        }

        // run the render
        worldRenderer.render(shader);

        shader.stop();

        skyboxRenderer.render(camera);



        DisplayManager.updateDisplay(); // last part of update loop
    }

    public boolean hasCloseCondition() {
        return Display.isCloseRequested();
    }

    public void addRenderable(Renderable renderable){
        renderables.add(renderable);
    }

    public void close() {
        shader.cleanup();
        loader.cleanup();
        DisplayManager.closeDisplay();
    }

    // debugging methods
    public void clearWalls(){
        walls.clear();
    }


}
