package uk.co.jakestanley.commander;

import lombok.Getter;
import uk.co.jakestanley.commander.gui.GuiController;
import uk.co.jakestanley.commander.input.InputController;
import uk.co.jakestanley.commander.scene.SceneController;

/**
 * Created by jp-st on 13/11/2015.
 */
public abstract class CommanderGame {

    public static final String GAME_NAME = "Commander";
    protected static final boolean RENDER_IN_3D = true;
    protected static final boolean RENDER_IN_2D = false;

    @Getter private static boolean debug = false;
    @Getter private static boolean renderIn3d = false;
    @Getter private static SceneController sceneController;
    @Getter private static GuiController guiController;
    @Getter private static InputController inputController;
    @Getter private static int displayWidth = 1024; // TODO allow these to be changed with arguments
    @Getter private static int displayHeight = 768;

    public CommanderGame(boolean debug, boolean renderIn3d){

        this.debug = debug;
        this.renderIn3d = renderIn3d;

        sceneController = new SceneController();
        guiController = new GuiController();
        inputController = new InputController();

        if(debug){
            System.out.println("Launching in developer mode");
        } else {
            System.out.println("Launching in normal mode");
        }

    }

    public void init(){
        getSceneController().init();
        getGuiController().init();
//        getInputController().init(); //  TODO InputController::init();
        initSpecifics();
        initRenderers();
    }

    protected abstract void initSpecifics();

    protected abstract void initRenderers();

    public void update(){
        getSceneController().update();
        updateInput();
        getGuiController().update();
        updateRenderers();
    }

    protected abstract void updateInput();

    protected abstract void updateRenderers();

    public abstract void render();

    public abstract boolean hasCloseCondition();

    public abstract void close();

}
