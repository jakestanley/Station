package resources;

import main.Display;
import main.GameController;

import java.awt.*;

/**
 * Created by stanners on 26/07/2015.
 */
public class Converter {

    public static final boolean OFFSET_ADDED = true;
    public static final boolean OFFSET_IGNORED = false;

    /**
     * Static and public as it may be useful that way
     * @param point
     * @return
     */
    public static Point mouseToTile(Point point, boolean offset){
        int x = (int) Math.floor(point.getX() / Display.TILE_WIDTH);
        int y = (int) Math.floor(point.getY() / Display.TILE_WIDTH);

        if(OFFSET_ADDED == offset){
            x = x - GameController.viewController.getViewOffsetX();
            y = y - GameController.viewController.getViewOffsetY();
        }

        return new Point(x, y);
    }

    public static int singleToTile(int s){
        int t = (int) Math.floor(s / Display.TILE_WIDTH);
        return t;
    }

}
