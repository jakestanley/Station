package uk.co.jakestanley.commander.rendering.world.threedimensional;

import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.Graphics;
import uk.co.jakestanley.commander.rendering.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.threedimensional.shaders.StaticShader;
import uk.co.jakestanley.commander.rendering.world.threedimensional.tools.Maths;

/**
 * Created by jp-st on 10/11/2015.
 */
public class ThreeDimensionalRenderer { // TODO better inheritance

    private int x, y, width, height; // canvas

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix;

    public ThreeDimensionalRenderer(int x, int y, int width, int height, StaticShader shader) { // TODO use these values
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // after this the projection matrix will stay there forever
        createProjectionMatrix(); // only needs to be set up once
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void init() {

    }

    public void update() {
        GL11.glEnable(GL11.GL_DEPTH_TEST); // tests which triangles are on top and renders them in the correct order
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // clear colour for next frame
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT); // clear depth buffer for next frame
        GL11.glClearColor(1, 0, 0, 1); // TODO set a proper background colour
    }

    public void render(RenderEntity entity, StaticShader shader) { // TODO need models/shapes/objects list or something

        TexturedModel texturedModel = null;
        RawModel rawModel = null;

        if(entity.isTextured()){
            texturedModel = entity.getTexturedModel();
            rawModel = texturedModel.getRawModel();
        } else {
            rawModel = entity.getRawModel();
        }

        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1); // enable texture mapping // TODO use consts

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        if(entity.isTextured()){
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
        }
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); // start at beginning, accepting unsigned ints, drawing triangles
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public void renderDebugging(Graphics screen) {

    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
}
