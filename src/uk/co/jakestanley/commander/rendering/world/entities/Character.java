package uk.co.jakestanley.commander.rendering.world.entities;

import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.world.Loader;
import uk.co.jakestanley.commander.rendering.world.models.ObjLoader;
import uk.co.jakestanley.commander.rendering.world.models.TexturedModel;

/**
 * Created by jake on 07/12/2015.
 */
public class Character extends Renderable {

    public Character(String identifier, Vector3f position, float rotX, float rotY, float rotZ){
        super(identifier, position, rotX, rotY, rotZ);
        loadRenderEntities();
    }

    @Override
    protected void loadRenderEntities(){ // TODO scene controller objects should have a getRenderEntity method
        Loader loader = Main.getGame().loader;
        ObjLoader objLoader = Main.getGame().getObjLoader();
        TexturedModel texturedModel = objLoader.loadTexturedModel("characters/stan", loader);
        RenderEntity renderEntity = new RenderEntity(texturedModel, position, rotX, rotY, rotZ, DEFAULT_SCALE);

        allRenderEntities.add(renderEntity);
        visibleRenderEntities.add(renderEntity);

    }

}
