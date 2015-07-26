package main;

import org.newdawn.slick.Graphics;

import java.awt.*;

/**
 * Created by stanners on 24/05/2015.
 */
public interface Interactable {

    int dbx = Display.MAP_WIDTH + Display.MARGIN;
    int dby = Display.MARGIN;

    public boolean mouseOver(Point mousePoint);

    public void populateDataBoxStrings();

    public void renderHoverBox(Graphics screen);

    public void renderDataBox(Graphics screen);

    public void qPress();

    public void wPress();

    public void ePress();

    public void rPress();

    public void vPress();

}
