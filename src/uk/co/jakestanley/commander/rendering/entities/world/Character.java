package uk.co.jakestanley.commander.rendering.entities.world;

import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.CommanderGame3D;
import uk.co.jakestanley.commander.rendering.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.threedimensional.Loader;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.ObjLoader;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.threedimensional.textures.ModelTexture;

/**
 * Created by jake on 07/12/2015.
 */
public class Character extends Renderable {

    public Character(String identifier){
        super(identifier);
        loadRenderEntities();
    }

    @Override
    protected void loadRenderEntities(){
        Loader loader = CommanderGame3D.loader;
        RawModel model = ObjLoader.loadObjModel("characters/"+identifier, loader, ObjLoader.TEXTURED);
        ModelTexture texture = new ModelTexture(loader.loadTexture("characters/"+identifier)); // TODO untextured model? shaded model?
        TexturedModel texturedModel = new TexturedModel(model, texture);
        RenderEntity renderEntity = new RenderEntity(texturedModel, new Vector3f(0,0,0),0,90f,0,1);

        allRenderEntities.add(renderEntity);
        visibleRenderEntities.add(renderEntity);

    }

}
