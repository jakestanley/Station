package main;

import org.newdawn.slick.Graphics;

/**
 * Created by stanners on 24/05/2015.
 */
public interface Interactable {

    public boolean mouseOver(int mouseX, int mouseY);

    public void renderHoverBox(Graphics screen);

    public void renderDataBox(Graphics screen);

    public void qPress();

    public void wPress();

    public void ePress();

    public void rPress();

    public void vPress();

}
