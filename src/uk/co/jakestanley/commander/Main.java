package uk.co.jakestanley.commander;

import lombok.Getter;

/**
 * Created by jp-st on 13/11/2015.
 */
public class Main {

    private static final boolean DEFAULT_CACHING = true;
    private static final boolean DEFAULT_DEBUG = false;
    private static final int DEFAULT_DISPLAY_WIDTH = 1280;
    private static final int DEFAULT_DISPLAY_HEIGHT = 720;

    @Getter private static Game3D game;

    public static void main(String[] args){

        boolean caching = DEFAULT_CACHING;
        boolean debug = DEFAULT_DEBUG;
        int displayWidth = DEFAULT_DISPLAY_WIDTH;
        int displayHeight = DEFAULT_DISPLAY_HEIGHT;

        for(String argument : args){
            if(argument.equalsIgnoreCase("-debug")){
                System.out.println("Launching in debug mode");
                debug = true;
            } else if(argument.equalsIgnoreCase("-cacheoff")){
                System.out.println("Caching disabled");
                caching = false;
            }
        }

        System.out.println("Launching 3D game");
        game = new Game3D(debug, caching, displayWidth, displayHeight);

        game.init();
        while(!game.hasCloseCondition()){
            game.update();
            game.render();
        }
        game.close();
    }
}
