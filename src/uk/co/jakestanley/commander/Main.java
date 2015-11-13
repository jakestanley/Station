package uk.co.jakestanley.commander;

import lombok.Getter;

/**
 * Created by jp-st on 13/11/2015.
 */
public class Main {

    @Getter private static CommanderGame game;

    public static void main(String[] args){

        boolean debug = false;
        boolean renderIn3d = false;

        for(String argument : args){
            if(argument.equalsIgnoreCase("-debug")){
                debug = true;
            } else if(argument.equalsIgnoreCase("-3d")){
                renderIn3d = true;
            }
        }

        if(renderIn3d){
            System.out.println("Launching 3D game");
            game = new CommanderGame3D(debug);
        } else {
            System.out.println("Launching 2D game");
            game = new CommanderGame2D(debug); // TODO just use fucking slick or get rid
        }

        game.init();
        while(!game.hasCloseCondition()){
            game.update();
            game.render();
        }
        game.close();
    }
}
