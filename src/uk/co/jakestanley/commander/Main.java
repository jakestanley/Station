package uk.co.jakestanley.commander;

import lombok.Getter;

/**
 * Created by jp-st on 13/11/2015.
 */
public class Main {

    private static final boolean DEFAULT_DEBUG = false;
    private static final int DEFAULT_DISPLAY_WIDTH = 1280;
    private static final int DEFAULT_DISPLAY_HEIGHT = 720;

    private static CommanderGame3D game;

    public static void main(String[] args){

        boolean debug = false;
        int displayWidth = DEFAULT_DISPLAY_WIDTH;
        int displayHeight = DEFAULT_DISPLAY_HEIGHT;

        for(String argument : args){
            if(argument.equalsIgnoreCase("-debug")){
                debug = true;
            }
        }

        System.out.println("Launching 3D game");
        game = new CommanderGame3D(debug, displayWidth, displayHeight);

        game.init();
        while(!game.hasCloseCondition()){
            game.update();
            game.render();
        }
        game.close();
    }
}
