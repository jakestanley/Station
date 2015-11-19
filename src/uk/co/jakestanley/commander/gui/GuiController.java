package uk.co.jakestanley.commander.gui;

import lombok.Getter;
import lombok.NonNull;
import org.newdawn.slick.geom.Rectangle;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.interfaces.Loopable;
import uk.co.jakestanley.commander.scene.entities.shapes.Shape;

import java.util.ArrayList;
import java.util.List;
@Getter
public class GuiController implements Loopable {

    @NonNull private List<String> messages; // TODO messages, panels, etc

    public GuiController(){
        messages = new ArrayList<String>();
    }

    public void init() {

    }

    public void update() {
        messages = new ArrayList<String>();

        Rectangle playerBox = Main.getGame().getSceneController().getMobiles().get(0).getBox();
        messages.add("Player X: " + playerBox.getX());
        messages.add("Player Z: " + playerBox.getY());
//        messages.add("Player Y: " + playerShape.getYLocal());
    }

}