package uk.co.jakestanley.commander.gui;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.geom.Rectangle;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.interfaces.Loopable;
import uk.co.jakestanley.commander.rendering.gui.GuiRenderer;
import uk.co.jakestanley.commander.rendering.gui.GuiTexture;
import uk.co.jakestanley.commander.rendering.world.Loader;
import uk.co.jakestanley.commander.scene.entities.shapes.Shape;

import java.util.ArrayList;
import java.util.List;
@Getter
@NoArgsConstructor
public class GuiController implements Loopable {

    private Loader loader;
    private GuiRenderer renderer;
    private List<String> messages; // TODO messages, panels, etc
    private List<Widget> widgets;
    private List<GuiTexture> textures;

    public GuiController(Loader loader){
        this.loader = loader;
        widgets = new ArrayList<Widget>();
        textures = new ArrayList<GuiTexture>();
        renderer = new GuiRenderer(loader);
    }

    public void init() {
        GuiTexture guiTexture = new GuiTexture(loader.loadTexture("ui/buttonEngage"), new Vector2f(-0.9f, 0.9f), new Vector2f(0.1f, 0.05f));
        textures.add(guiTexture);
    }

    public void update() {
        messages = new ArrayList<String>();

//        Rectangle playerBox = Main.getGame().getSceneController().getMobiles().get(0).getBox();
//        messages.add("Player X: " + playerBox.getX());
//        messages.add("Player Z: " + playerBox.getY());
//        messages.add("Player Y: " + playerShape.getYLocal());
    }

    public void render(){
        renderer.render(textures);
    }

    public boolean isClicked(Vector2f mouse){
        for(Widget widget : widgets){
            if(widget.click(mouse)){
                return true;
            }
        }
        return false;
    }

}