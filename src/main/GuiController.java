package main;

import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 21/07/2015.
 */
public class GuiController {

    public GuiController(){
        // TODO this
    }

    public void setBackground(Graphics screen){
        screen.setBackground(Colours.GRID_BACKGROUND);
    }

    public void renderGrid(Graphics screen){

        // DRAW GRID
        screen.setColor(Colours.GRID_LINES);
        screen.setLineWidth(Display.GRID_LINES);

        // draw horizontal lines going down
        for(int v = 0; v < Display.MAP_HEIGHT; v++){
            screen.drawLine(0, v * Display.TILE_WIDTH, Display.MAP_WIDTH, v * Display.TILE_WIDTH);
        }

        // draw vertical lines going across
        for(int h = 0; h < Display.MAP_WIDTH; h++){
            screen.drawLine(h * Display.TILE_WIDTH, 0, h * Display.TILE_WIDTH, Display.MAP_HEIGHT);
        }

    }

}
