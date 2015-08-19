package map;

import exceptions.NoDialogException;
import guicomponents.*;
import guicomponents.Dialog;
import main.ContextController;
import main.Display;
import main.Game;
import main.GameController;
import org.lwjgl.Sys;
import resources.Converter;

import java.awt.*;

/**
 * Created by stanners on 26/07/2015.
 */
public class MouseController {

    private boolean dragMode;
    private Point clickPoint, hoverPoint;

    public MouseController(){
        dragMode = false;
    } // TODO check context

    public void setClickPoint(Point mousePoint){

        ContextController cc    = GameController.contextController;
        MapController mc        = GameController.mapController;

        // Clear map hover objects
        mc.clearHoverObjects();

        // Get focus and click it if it exists
        GuiComponent focus = GameController.guiController.getFocus(); // TODO make less bad
        if(focus != null){
            focus.click(mousePoint);
            return;
        }

        int context = cc.getContext(); // TODO make default context -1

        if((cc.getContext() == ContextController.CONSTRUCTION) && isMouseOverMap(mousePoint)){ // If in construction context and mouse is over map

            if(mc.setHoverDoor(mousePoint)){ // if hover door was set
                mc.getHoverDoor().enable(); // enable the door
            } else { // if door is null, process tile click
                dragMode = true;
                clickPoint = Converter.mouseToTile(mousePoint, Converter.OFFSET_ADDED);
                hoverPoint = clickPoint;
                mc.setDragSelection(clickPoint, clickPoint);
            }
        }

        // Menus should be clickable in any context except dialogs, as arbitrarily, they should block.

    }

    public void setHoverPoint(Point mousePoint){

        // Clear map hover objects
        GameController.mapController.clearHoverObjects();

        if(isMouseOverMap(mousePoint) && (GameController.contextController.getContext() == ContextController.CONSTRUCTION)){

            if(!dragMode && GameController.mapController.setHoverDoor(mousePoint)) { // if hover door was set

            } else if(dragMode){
                hoverPoint = Converter.mouseToTile(mousePoint, Converter.OFFSET_ADDED);
                GameController.mapController.setDragSelection(clickPoint, hoverPoint);
            }
        }
    }

    public void setMouseRelease() { // TODO CONSIDER MouseController ?
        if(dragMode){
            GameController.mapController.releaseDrag();
            dragMode = false;
        }
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
