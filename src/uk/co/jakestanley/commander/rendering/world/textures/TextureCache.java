package uk.co.jakestanley.commander.rendering.world.textures;

import uk.co.jakestanley.commander.rendering.world.models.RawModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jake on 07/12/2015.
 */
public class TextureCache {

    private Map<String, ModelTexture> modelTextureMap;
    private Map<String, Integer> modelIdMap;

    public TextureCache(){
        modelTextureMap = new HashMap<String, ModelTexture>();
        modelIdMap = new HashMap<String, Integer>();
    }

    public Integer getModelId(String path){
        return modelIdMap.get(path);
    }

    public ModelTexture getModelTexture(String path){
        return modelTextureMap.get(path);
    }

    public void store(String path, int modelId){
        modelIdMap.put(path, modelId);
    }

    public void store(String path, ModelTexture modelTexture){
        modelTextureMap.put(path, modelTexture);
    }



    // TODO cleanup cache periodically

}
