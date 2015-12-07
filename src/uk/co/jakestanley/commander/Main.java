package uk.co.jakestanley.commander;

import lombok.Getter;

/**
 * Created by jp-st on 13/11/2015.
 */
public class Main {

    @Getter private static CommanderGame game;

    public static void main(String[] args){

        boolean debug = false;

        for(String argument : args){
            if(argument.equalsIgnoreCase("-debug")){
                debug = true;
            }
        }

        System.out.println("Launching 3D game");
        game = new CommanderGame3D(debug);

        game.init();
        while(!game.hasCloseCondition()){
            game.update();
            game.render();
        }
        game.close();
    }
}
