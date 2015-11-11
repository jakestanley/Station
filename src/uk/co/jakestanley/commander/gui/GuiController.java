package uk.co.jakestanley.commander.gui;

import lombok.Getter;
import lombok.NonNull;
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

        Shape playerShape = Main.getSceneController().getMobileEntities().get(0).getShape();
        messages.add("Player X: " + playerShape.getXLocal());
        messages.add("Player Z: " + playerShape.getZLocal());
        messages.add("Player Y: " + playerShape.getYLocal());
    }

}