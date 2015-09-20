package map.functionals;

import main.GameController;
import map.Placeable;
import resources.ImageController;

/**
 * Created by stanners on 20/09/2015.
 */
public class Reactor extends Placeable {

    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;

    public Reactor(int x, int y, boolean isPlaced){
        super(x, y, WIDTH, HEIGHT, GameController.imageController.getImage(ImageController.Keys.REACTOR_OBJECT), isPlaced, true, true);
    }

}
