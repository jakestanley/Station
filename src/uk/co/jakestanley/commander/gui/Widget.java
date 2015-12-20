package uk.co.jakestanley.commander.gui;

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

    public boolean click(Vector2f mouse){
        boolean clicked = false;
        if(isClicked(mouse)) {
            clicked = true;
            if (listener != null) {
                listener.onClick();
            } else {
                for (Widget child : children) {
                    if (child.click(mouse)) {
                        break; // break on first child click
                    }
                }
            }
        }
        return clicked;
    }

    private boolean isClicked(Vector2f mouse){
        // TODO check if this component was clicked
        return true;
    }

}
