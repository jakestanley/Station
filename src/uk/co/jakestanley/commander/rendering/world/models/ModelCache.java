package uk.co.jakestanley.commander.rendering.world.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides copies of existing models if they have been loaded already. Should reduce processing time
 * Created by jake on 07/12/2015.
 */
public class ModelCache {

    private Map<String, RawModel> rawModelMap;
    private Map<String, TexturedModel> texturedModelMap;

    public ModelCache(){
        rawModelMap = new HashMap<String, RawModel>();
        texturedModelMap = new HashMap<String, TexturedModel>();
    }

    public void storeRawModel(String path, RawModel model){
        rawModelMap.put(path, model);
    }

    public void storeTexturedModel(String path, TexturedModel model){
        texturedModelMap.put(path, model);
    }

    public RawModel getRawModel(String path){
        return rawModelMap.get(path);
    }

    public TexturedModel getTexturedModel(String path){
        return texturedModelMap.get(path);
    }

    // TODO clean up cache periodically

}
