package uk.co.jakestanley.commander.rendering.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import uk.co.jakestanley.commander.rendering.shaders.GuiShader;
import uk.co.jakestanley.commander.rendering.world.Loader;
import uk.co.jakestanley.commander.rendering.world.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.tools.Maths;

import java.util.List;

/**
 * Created by jp-st on 09/11/2015.
 */
public class GuiRenderer { // TODO make it impossible to draw outside the renderable area

    private RawModel quad;
    private GuiShader shader;
    private Loader loader;

    public GuiRenderer(Loader loader){
        this.loader = loader;
        quad = loader.loadToVAO(quadPositions);
        shader = new GuiShader();
    }

    public void init() {

    }

    public void update() {

    }

    public void render(List<GuiTexture> guis) {
        // TODO CONSIDER can i specify a list of things to draw or something more efficient?

        shader.start();

        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        // render
        for(GuiTexture gui : guis){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTextureId());
            Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
            shader.loadTransformation(matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // clean up
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);

        shader.stop();

    }

    public void cleanup(){
        shader.cleanup();
    }

    private static final float[] quadPositions = {
            -1,  1,
            -1, -1,
             1,  1,
             1, -1 };

}
