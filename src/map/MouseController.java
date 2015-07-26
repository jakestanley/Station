package map;

import resources.Converter;
import main.Display;
import main.GameController;

import java.awt.*;

/**
 * Created by stanners on 26/07/2015.
 */
public class MouseController {

    private Point clickPoint, hoverPoint;

    public void setClickPoint(Point mousePoint){
        if(isMouseOverMap(mousePoint)){
            clickPoint = Converter.mouseToTile(mousePoint, Converter.OFFSET_ADDED);
            hoverPoint = clickPoint;
            GameController.mapController.setDragSelection(clickPoint, clickPoint);
        }
    }

    public void setHoverPoint(Point mousePoint){
        if(isMouseOverMap(mousePoint)){
            hoverPoint = Converter.mouseToTile(mousePoint, Converter.OFFSET_ADDED);
            GameController.mapController.setDragSelection(clickPoint, hoverPoint);
        }
    }

    public void setMouseRelease() { // TODO CONSIDER MouseController ?
        GameController.mapController.releaseDrag();
    }

    private boolean isMouseOverMap(Point point){

        int x = (int) point.getX();
        int y = (int) point.getY();
        int xLowerBound = 0; // Display.DISPLAY_START_X; // TODO
        int xUpperBound = Display.LEFT_COLUMN_WIDTH;
        int yLowerBound = Display.TEXT_PANEL_HEIGHT;
        int yUpperBound = Display.DISPLAY_HEIGHT;
        if(x >= xLowerBound && x <= xUpperBound && y >= yLowerBound && y <= yUpperBound){
            return true;
        }
        return false;
    }

}
