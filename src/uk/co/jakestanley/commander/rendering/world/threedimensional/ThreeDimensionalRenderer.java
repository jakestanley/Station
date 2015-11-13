package uk.co.jakestanley.commander.rendering.world.threedimensional;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.Graphics;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.RawModel;

/**
 * Created by jp-st on 10/11/2015.
 */
public class ThreeDimensionalRenderer { // TODO better inheritance

    private int x, y, width, height; // canvas

    public ThreeDimensionalRenderer(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void init() {

    }

    public void update() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(1, 0, 0, 1); // TODO set a proper background colour
    }

    public void render(RawModel model) { // TODO need models/shapes/objects list or something
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); // start at beginning, accepting unsigned ints, drawing triangles
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public void renderDebugging(Graphics screen) {

    }

}
