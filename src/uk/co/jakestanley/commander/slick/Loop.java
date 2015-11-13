package uk.co.jakestanley.commander.slick;

import org.newdawn.slick.*;
import uk.co.jakestanley.commander.CommanderGame;

/**
 * Created by jp-st on 13/11/2015.
 */
public class Loop extends BasicGame {

    private CommanderGame game;

    public Loop(CommanderGame game){
        super(CommanderGame.GAME_NAME);
        this.game = game;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

    }

    public GameContainer getGameContainer(){
        return null;
    }

    public Graphics getGraphics(){
        return null; // TODO fix
    }

    /**
     * Start the Slick game process
     */
    public void start(){
//        try {
//            AppGameContainer container = new AppGameContainer(this);
//            container.setShowFPS(main.Game.debug);
//            container.setDisplayMode(getDisplayWidth(), getDisplayHeight(), false);
//            container.setVSync(true); // jesus h christ this needs to be on
//            container.setTargetFrameRate(60); //Display.FRAME_RATE); // TODO remove the /2 after testing screen scrolling
//            container.start();
//        } catch (SlickException e) {
//            System.out.println("Failed to start the container");
//            e.printStackTrace();
//            System.exit(main.Game.EXIT_BAD); // TODO move these variables
//        }
    }

}
