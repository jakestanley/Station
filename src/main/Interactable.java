package main;

import org.newdawn.slick.Graphics;

import java.awt.*;

/**
 * Created by stanners on 24/05/2015.
 */
public interface Interactable {

    int dbx = GameController.display.getMapWidth() + Display.MARGIN;
    int dby = Display.MARGIN;

    boolean mouseOver(Point mousePoint);

    void populateDataBoxStrings();

    void renderHoverBox(Graphics screen);

    void renderDataBox(Graphics screen);

}
