package uk.co.jakestanley.commander.rendering.world;

import uk.co.jakestanley.commander.rendering.entities.Camera;
import uk.co.jakestanley.commander.rendering.entities.Light;
import uk.co.jakestanley.commander.rendering.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.shaders.StaticShader;

import java.util.*;

/**
 * Created by jake on 06/12/2015.
 */
public class MasterRenderer {

    private List<Light> lights;

    private StaticShader shader;
    private ThreeDimensionalRenderer renderer;
    private Map<TexturedModel, List<RenderEntity>> renderEntities;

    public MasterRenderer(List<Light> lights){
        this.lights = lights;
        shader = new StaticShader();
//        renderer = new ThreeDimensionalRenderer(0, 0, CommanderGame3D.getDisplayWidth(), CommanderGame3D.getDisplayHeight(), shader);
        renderEntities = new HashMap<TexturedModel, List<RenderEntity>>();
    }

    public void render(Camera camera){
        renderer.update(); // TODO change (to prepare, maybe?)
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
