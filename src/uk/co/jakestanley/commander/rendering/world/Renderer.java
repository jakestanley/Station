package uk.co.jakestanley.commander.rendering.world;

import org.lwjgl.opengl.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Graphics;
import uk.co.jakestanley.commander.rendering.world.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.shaders.StaticShader;
import uk.co.jakestanley.commander.rendering.world.textures.ModelTexture;
import uk.co.jakestanley.commander.rendering.world.tools.Maths;

/**
 * Created by jp-st on 10/11/2015.
 */
public class Renderer { // TODO better inheritance

    private int x, y, width, height; // canvas

    private Matrix4f projectionMatrix;

    public Renderer(int x, int y, int width, int height, StaticShader shader, int type) { // TODO use these values
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // after this the projection matrix will stay there forever
        if(PERSPECTIVE == type){
            projectionMatrix = Maths.createPerspectiveProjectionMatrix(); // only needs to be set up once
        } else {
            projectionMatrix = Maths.createOrthographicProjectionMatrix();
        }
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
        GL11.glClearColor(0, 0, 0, 1); // TODO set a proper background colour
    }

    public void render(RenderEntity entity, StaticShader shader) { // TODO need models/shapes/objects list or something

        TexturedModel texturedModel = null;
        RawModel rawModel;

        if(entity.isTextured()){
            texturedModel = entity.getTexturedModel();
            rawModel = texturedModel.getRawModel();
        } else {
            rawModel = entity.getRawModel();
        }

        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1); // enable texture mapping // TODO use consts
        GL20.glEnableVertexAttribArray(2);

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        ModelTexture texture = texturedModel.getTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        if(entity.isTextured()){
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
        }
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); // start at beginning, accepting unsigned ints, drawing triangles
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public void renderDebugging(Graphics screen) {

    }

    public static final int ORTHOGRAPHIC = 301;
    public static final int PERSPECTIVE = 302;

}
