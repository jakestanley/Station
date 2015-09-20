package resources;

import main.Game;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stanners on 20/09/2015.
 */
public class ImageController {

    private Map<String, Image> images;

    public ImageController(){
        this.images = new HashMap<String, Image>();
    }

    public void init(){
        try {
            loadObjectImages();
        } catch (SlickException e){
            System.err.println("Failed to load object images");
            e.printStackTrace();
            System.exit(Game.EXIT_BAD);
        }
    }

    public void loadObjectImages() throws SlickException {

        // Reactor object
        images.put(Keys.REACTOR_OBJECT, new Image("res/img/functionals/Reactor3x3.png"));
        images.put(Keys.TABLE_2x1, new Image("res/img/functionals/Table2x1.png"));

    }

    public Image getImage(String key){
        return images.get(key);
    }

    public static class Keys {

        public static final String REACTOR_OBJECT   = "REACTOR_OBJECT";
        public static final String TABLE_2x1        = "TABLE_2x1";

    }

}
