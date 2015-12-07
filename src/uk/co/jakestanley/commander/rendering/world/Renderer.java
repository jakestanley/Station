package uk.co.jakestanley.commander.rendering.world;

import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.Graphics;
import uk.co.jakestanley.commander.rendering.world.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.shaders.StaticShader;
import uk.co.jakestanley.commander.rendering.world.textures.ModelTexture;
import uk.co.jakestanley.commander.rendering.world.tools.Maths;
import uk.co.jakestanley.commander.rendering.world.caching.RenderCache;
import uk.co.jakestanley.commander.scene.entities.Entity;

import java.util.*;

/**
 * Created by jp-st on 10/11/2015.
 */
public class Renderer { // TODO better inheritance

    private int frameCount;
    private int lastTimeInSeconds;
    private Map<TexturedModel, List<RenderEntity>> renderBatches;

    private StaticShader shader;
    private Matrix4f projectionMatrix;

    public Renderer(StaticShader shader, int type) { // TODO use these values

        // enable face culling
//        GL11.glEnable(GL11.GL_CULL_FACE); // TODO fix as this breaks the floor
//        GL11.glCullFace(GL11.GL_BACK);

        this.shader = shader;

        // projection matrix only needs setting up once
        if(PERSPECTIVE == type){
            projectionMatrix = Maths.createPerspectiveProjectionMatrix(); // only needs to be set up once
        } else {
            projectionMatrix = Maths.createOrthographicProjectionMatrix();
        }

        // initialise render batch map
        renderBatches = new HashMap<TexturedModel, List<RenderEntity>>();

    }

    public void init() {
        // set up the projection matrix
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();

        // initialise frame count for fps calculation
        frameCount = 0;
    }

    public void update() {
        calculateFps();
        GL11.glEnable(GL11.GL_DEPTH_TEST); // tests which triangles are on top and renders them in the correct order
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // clear colour for next frame
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT); // clear depth buffer for next frame
        GL11.glClearColor(0, 0, 0, 1); // TODO set a proper background colour
        lastTimeInSeconds = (int) System.currentTimeMillis() / 1000; // for next fps calculation
    }

    public void addToRenderQueue(RenderEntity entity){
        TexturedModel entityModel = entity.getTexturedModel();
        List<RenderEntity> batch = renderBatches.get(entityModel);
        if(batch != null){ // if batch already exists, add it to this texturedmodel batch
            batch.add(entity);
        } else {
            List<RenderEntity> newBatch = new ArrayList<RenderEntity>();
            newBatch.add(entity);
            renderBatches.put(entityModel, newBatch);
        }
    }

    public void render(StaticShader shader) { // TODO need models/shapes/objects list or something
        for(TexturedModel texturedModel : renderBatches.keySet()){

            // prepare the model and bind vertex attribute arrays
            RawModel rawModel = texturedModel.getRawModel();
            GL30.glBindVertexArray(rawModel.getVaoID());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1); // enable texture mapping // TODO use consts
            GL20.glEnableVertexAttribArray(2);

            // get the texture and load the shine variables
            ModelTexture texture = texturedModel.getTexture();
            shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());

            // bind the texture
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());

            List<RenderEntity> batch = renderBatches.get(texturedModel);
            for (Iterator<RenderEntity> iterator = batch.iterator(); iterator.hasNext(); ) {
                RenderEntity entity = iterator.next();

                // prepare instance
                Matrix4f tMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
                shader.loadTransformationMatrix(tMatrix);

                // actually render
                GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); // start at beginning, accepting unsigned ints, drawing triangles

            }

            // unbind the textured model after rendering all instances of it
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(2);
            GL30.glBindVertexArray(0);

        }

        // clear the batches after the render
        renderBatches.clear();

    }

    public void renderDebugging(Graphics screen) {

    }

    private void calculateFps(){
        if(lastTimeInSeconds == ((int) System.currentTimeMillis() / 1000)){
            frameCount++;
        } else {
            System.out.println("FPS: " + frameCount);
            frameCount = 0;
        }
    }

    public static final int ORTHOGRAPHIC = 301;
    public static final int PERSPECTIVE = 302;

}
