package uk.co.jakestanley.commander;

import lombok.Getter;
import org.lwjgl.opengl.Display;
import uk.co.jakestanley.commander.gui.GuiController;
import uk.co.jakestanley.commander.input.InputController;
import uk.co.jakestanley.commander.scene.SceneController;

/**
 * Created by jp-st on 10/11/2015.
 */
public class Main {

    // TODO stick non rendering values in here

    @Getter private static CommanderGame2D game2D; // uses slick
    @Getter private static CommanderGame3D game3D; // uses lwjgl only

    @Getter private static boolean           debug = false;
    @Getter private static SceneController   sceneController;
    @Getter private static GuiController     guiController;
    @Getter private static InputController   inputController;

    @Getter private static int displayWidth = 1024; // TODO consider moving into a separate display class
    @Getter private static int displayHeight = 768;

    public static void main(String[] args) {

        // Process arguments
        boolean threeD = false;
        for(String arg : args){
            if(arg.equalsIgnoreCase("-debug")){
                debug = true;
            } else if(arg.equalsIgnoreCase("-3d")){
                threeD = true;
            }
        }

        // Print some configuration information
        if(debug){
            System.out.println("Launching in developer mode");
        } else {
            System.out.println("Launching in normal mode");
        }

        // initialise the game objects
        sceneController = new SceneController(); // TODO fix order
        guiController = new GuiController(); // TODO gets information data for the gui
        inputController = new InputController();

        // Instantiate the game controllers
        if(!threeD){
            start2dGame();
        } else {
            start3dGame();
        }

    }

    public static void start2dGame(){
        System.out.println("Starting 2D game");
        CommanderGame2D game2D = new CommanderGame2D();
    }

    public static void start3dGame(){
        System.out.println("Starting 3D game");
        CommanderGame3D game3D = new CommanderGame3D();
        game3D.init();
        while (!Display.isCloseRequested()) {
            game3D.update(); // TODO make sure both games use the same update method
            game3D.render();
        }
        Display.destroy();
    }

    public static boolean isGame2D(){
        return (game2D != null);
    }

    public static boolean isGame3D(){
        return (game3D != null);
    }

}
