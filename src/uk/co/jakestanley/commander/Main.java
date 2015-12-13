package uk.co.jakestanley.commander;

import lombok.Getter;
import uk.co.jakestanley.commander.rendering.world.Renderer;
import uk.co.jakestanley.commander.rendering.world.entities.Camera;
import uk.co.jakestanley.commander2d.main.Display;

/**
 * Created by jp-st on 13/11/2015.
 */
public class Main {

    private static final boolean DEFAULT_CACHING = true;
    private static final boolean DEFAULT_DEBUG = false;
    private static final boolean DEFAULT_FULLSCREEN = false;
    private static final int DEFAULT_DISPLAY_WIDTH = 1280;
    private static final int DEFAULT_DISPLAY_HEIGHT = 720;

    @Getter private static Game3D game;

    public static void main(String[] args){

        boolean caching = DEFAULT_CACHING;
        boolean debug = DEFAULT_DEBUG;
        boolean fullscreen = DEFAULT_FULLSCREEN;
        int projection = Renderer.PERSPECTIVE;
        int displayWidth = DEFAULT_DISPLAY_WIDTH;
        int displayHeight = DEFAULT_DISPLAY_HEIGHT;
        boolean badResolutionArgs = false;

        for(int i = 0; i < args.length; i++){
            if(args[i].equalsIgnoreCase("-debug")){
                System.out.println("Launching in debug mode");
                debug = true;
            } else if(args[i].equalsIgnoreCase("-cacheoff")){
                System.out.println("Caching disabled");
                caching = false;
            } else if(args[i].equalsIgnoreCase("-orthographic")){
                System.out.println("Setting projection mode to orthographic");
                projection = Renderer.ORTHOGRAPHIC;
            } else if(args[i].equalsIgnoreCase("-width") || args[i].equals("-w")){
                try {
                    displayWidth = Integer.parseInt(args[i+1]);
                } catch(ArrayIndexOutOfBoundsException a){
                    badResolutionArgs = true;
                }
            } else if(args[i].equalsIgnoreCase("-height") || args[i].equals("-h")){
                try {
                    displayHeight = Integer.parseInt(args[i+1]);
                } catch(ArrayIndexOutOfBoundsException a){
                    badResolutionArgs = true;
                }
            } else if(args[i].equalsIgnoreCase("-fullscreen")){
                fullscreen = true;
            }
        }

        if(badResolutionArgs){
            System.err.println("Failed to parse resolution arguments. Using default resolution.");
            displayWidth = DEFAULT_DISPLAY_WIDTH;
            displayHeight = DEFAULT_DISPLAY_HEIGHT;
        }

        if(Renderer.ORTHOGRAPHIC == projection){
            System.out.println("Running game in ORTHOGRAPHIC mode");
        } else {
            System.out.println("Running game in PERSPECTIVE mode");
        }

        System.out.println("Launching 3D game");
        game = new Game3D(debug, caching, projection, displayWidth, displayHeight);

        game.init();
        while(!game.hasCloseCondition()){
            game.update();
            game.render();
        }
        game.close();
    }
}
