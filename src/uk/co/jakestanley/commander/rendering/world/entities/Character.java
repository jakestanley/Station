package uk.co.jakestanley.commander.rendering.world.entities;

import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.world.Loader;
import uk.co.jakestanley.commander.rendering.world.models.ObjLoader;
import uk.co.jakestanley.commander.rendering.world.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.textures.ModelTexture;

/**
 * Created by jake on 07/12/2015.
 */
public class Character extends Renderable {

    public Character(String identifier, Vector3f position){
        super(identifier, position);
        loadRenderEntities();
    }

    @Override
    protected void loadRenderEntities(){
        Loader loader = Main.getGame().loader;
        RawModel model = ObjLoader.loadObjModel("characters/"+identifier, loader, ObjLoader.TEXTURED);
        ModelTexture texture = new ModelTexture(loader.loadTexture("characters/"+identifier)); // TODO untextured model? shaded model?
        TexturedModel texturedModel = new TexturedModel(model, texture);
        RenderEntity renderEntity = new RenderEntity(texturedModel, position,0,90f,0,1);

        allRenderEntities.add(renderEntity);
        visibleRenderEntities.add(renderEntity);

    }

}
