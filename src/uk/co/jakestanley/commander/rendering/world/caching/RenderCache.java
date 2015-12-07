package uk.co.jakestanley.commander.rendering.world.caching;

import uk.co.jakestanley.commander.rendering.world.Renderer;
import uk.co.jakestanley.commander.rendering.world.entities.Camera;
import uk.co.jakestanley.commander.rendering.world.entities.Light;
import uk.co.jakestanley.commander.rendering.world.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.shaders.StaticShader;

import java.util.*;

/**
 * Created by jake on 06/12/2015.
 */
public class RenderCache {

    private List<Light> lights; // not sure about this TODO revise

    private StaticShader shader;
    private Map<TexturedModel, List<RenderEntity>> renderEntities;

    public RenderCache(List<Light> lights){
        this.lights = lights;
        shader = new StaticShader();
        renderEntities = new HashMap<TexturedModel, List<RenderEntity>>();
    }

    public void render(Camera camera){
//        renderer.update(); // TODO change (to prepare, maybe?)
        shader.start();
        loadLights();
        shader.loadViewMatrix(camera);
//        renderer.render(renderEntities);
        shader.stop();
        renderEntities.clear();
    }

    public void processEntity(RenderEntity entity){
        TexturedModel entityModel = entity.getTexturedModel();
        List<RenderEntity> batch = renderEntities.get(entityModel);
        if(batch != null){ // if batch already exists, add it to this texturedmodel batch
            batch.add(entity);
        } else {
            List<RenderEntity> newBatch = new ArrayList<RenderEntity>();
            newBatch.add(entity);
            renderEntities.put(entityModel, newBatch);
        }
    }

    public void cleanup(){
        shader.cleanup();
    }

    private void loadLights(){
        for (Iterator<Light> iterator = lights.iterator(); iterator.hasNext(); ) {
            Light next =  iterator.next();
            shader.loadLight(next);
        }
    }
}
