package uk.co.jakestanley.commander.rendering.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by jake on 19/12/2015.
 */
@Getter
@AllArgsConstructor
public class GuiTexture {

    private int textureId;
    private Vector2f position;
    private Vector2f scale;

}
