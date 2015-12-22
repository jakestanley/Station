package uk.co.jakestanley.commander.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import uk.co.jakestanley.commander.rendering.gui.GuiTexture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jake on 20/12/2015.
 */
public abstract class Widget {

    protected Vector2f position;
    protected GuiTexture texture;
    protected Listener listener;
    protected List<Widget> children;

    protected Widget(Vector2f position, GuiTexture texture){
        children = new ArrayList<Widget>();
        this.position = position;
        this.texture = texture;
    }

    protected Widget(Vector2f position, GuiTexture texture, Listener listener){
        children = new ArrayList<Widget>();
        this.position = position;
        this.texture = texture;
        this.listener = listener;
    }

    public boolean click(){
        boolean clicked = false;
        if(isClicked()) {
            clicked = true;
            if (listener != null) {
                listener.onClick();
            } else {
                for (Widget child : children) {
                    if (child.click()) {
                        break; // break on first child click
                    }
                }
            }
        }
        return clicked;
    }

    private boolean isClicked(){
        float mouseX = Mouse.getX();
        float mouseY = Mouse.getY();

        Vector2f widgetStart = getStart();
        Vector2f widgetEnd = getEnd();

        return (
                    (mouseX > widgetStart.getX())
                &&  (mouseX < widgetEnd.getX())
                &&  (mouseY > widgetStart.getY())
                &&  (mouseY < widgetEnd.getY())
        );

    }

    private Vector2f getStart(){
        Vector2f start = new Vector2f(0,0);

        // TODO get the start of the button and convert the coordinates. use guitexture and division
        return start; // TODO
    }

    private Vector2f getEnd(){
        Vector2f end = new Vector2f(1,1);
        // TODO get the end of the button and convert the coordinates. use guitexture and division...
        return end;
    }

}
