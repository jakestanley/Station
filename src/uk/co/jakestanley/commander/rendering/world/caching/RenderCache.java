package uk.co.jakestanley.commander.rendering.world.caching;

import uk.co.jakestanley.commander.rendering.world.entities.Camera;
import uk.co.jakestanley.commander.rendering.world.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.shaders.StaticShader;

import java.util.*;

/**
 * Created by jake on 06/12/2015.
 */
public class RenderCache { // TODO remove

    private StaticShader shader;
    private Map<TexturedModel, List<RenderEntity>> renderEntities;

    public RenderCache(StaticShader shader){
        this.shader = shader;
        renderEntities = new HashMap<TexturedModel, List<RenderEntity>>();
    }

    public void render(Camera camera){
//        renderer.update(); // TODO change (to prepare, maybe?)
        shader.start();
//        loadLights();
        shader.loadViewMatrix(camera);
//        renderer.render(renderEntities);
        shader.stop();
        renderEntities.clear();
    }

    public void processEntity(RenderEntity entity){

    }
}
