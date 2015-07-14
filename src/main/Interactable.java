package main;

import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 24/05/2015.
 */
public interface Interactable {

    int dbx = Display.MAP_WIDTH + Display.MARGIN;
    int dby = Display.MARGIN;

    public boolean mouseOver(int mouseX, int mouseY, int viewOffsetX, int viewOffsetY);

    public void populateDataBoxStrings();

    public void renderHoverBox(Graphics screen, int viewOffsetX, int viewOffsetY);

    public void renderDataBox(Graphics screen);

    public void qPress();

    public void wPress();

    public void ePress();

    public void rPress();

    public void vPress();

}
