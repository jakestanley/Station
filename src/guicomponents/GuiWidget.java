package guicomponents;

import org.newdawn.slick.Graphics;

/**
 * Created by jake on 16/08/15.
 */
public abstract class GuiWidget extends GuiComponent {

    protected int index;
    protected GuiContainer parent;

    public GuiWidget(GuiContainer parent, int index){
        super();
        this.parent = parent;
    }

    public void click(){
        parent.widgetClicked(index);
    }

    public abstract void render(Graphics screen, int x, int y);

    protected void setCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

}
