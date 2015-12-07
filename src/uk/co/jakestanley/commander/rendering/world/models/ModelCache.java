package uk.co.jakestanley.commander.rendering.world.models;

import uk.co.jakestanley.commander.rendering.world.models.RawModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides copies of existing models if they have been loaded already. Should reduce processing time
 * Created by jake on 07/12/2015.
 */
public class ModelCache {

    private Map<String, RawModel> map;

    public ModelCache(){
        map = new HashMap<String, RawModel>();
    }

    public RawModel get(String path){
        return map.get(path);
    }

    public void store(String path, RawModel model){
        map.put(path, model);
    }

    // TODO cleanup cache periodically

}
