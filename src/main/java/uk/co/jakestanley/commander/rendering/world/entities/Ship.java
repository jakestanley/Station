package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.Getter;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Game3D;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.world.Loader;
import uk.co.jakestanley.commander.rendering.world.models.ObjLoader;
import uk.co.jakestanley.commander.rendering.world.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.textures.ModelTexture;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jake on 05/12/2015.
 */
@Getter
public class Ship extends Renderable { // TODO a superclass

    private RenderEntity top;
    private RenderEntity front;
    private RenderEntity rear;
    private RenderEntity port;
    private RenderEntity starboard;
    private RenderEntity ballast;

    /**
     * identifier should be all lower case and should correspond with res directory structure
     * @param identifier
     */
    public Ship(String identifier, Vector3f position){ // TODO make a center point - TODO a render at coordinate
        super(identifier, position, 0, 0, 0, false);
        loadRenderEntities();
    }

    @Override
    protected void loadRenderEntities(){

        Loader loader = Main.getGame().loader;
        ObjLoader objLoader = Main.getGame().getObjLoader();

        RawModel topModel = objLoader.loadObjModel("ships/"+identifier+"/Top", loader, ObjLoader.TEXTURED);
        RawModel frontModel = objLoader.loadObjModel("ships/"+identifier+"/Front", loader, ObjLoader.TEXTURED);
        RawModel rearModel = objLoader.loadObjModel("ships/"+identifier+"/Rear", loader, ObjLoader.TEXTURED);
        RawModel portModel = objLoader.loadObjModel("ships/"+identifier+"/Port", loader, ObjLoader.TEXTURED);
        RawModel starboardModel = objLoader.loadObjModel("ships/"+identifier+"/Starboard", loader, ObjLoader.TEXTURED);
        RawModel ballastModel = objLoader.loadObjModel("ships/"+identifier+"/Ballast", loader, ObjLoader.TEXTURED);

        ModelTexture topTexture = new ModelTexture(loader.loadTexture("ships/"+identifier+"/Top"));
        ModelTexture frontTexture = new ModelTexture(loader.loadTexture("ships/"+identifier+"/Front"));
        ModelTexture rearTexture = new ModelTexture(loader.loadTexture("ships/"+identifier+"/Rear"));
        ModelTexture portTexture = new ModelTexture(loader.loadTexture("ships/"+identifier+"/Port"));
        ModelTexture starboardTexture = new ModelTexture(loader.loadTexture("ships/"+identifier+"/Starboard"));
        ModelTexture ballastTexture = new ModelTexture(loader.loadTexture("test/green"));

        TexturedModel topTexturedModel = new TexturedModel(topModel, topTexture);
        TexturedModel frontTexturedModel = new TexturedModel(frontModel, frontTexture);
        TexturedModel rearTexturedModel = new TexturedModel(rearModel, rearTexture);
        TexturedModel portTexturedModel = new TexturedModel(portModel, portTexture);
        TexturedModel starboardTexturedModel = new TexturedModel(starboardModel, starboardTexture);
        TexturedModel ballastTexturedModel = new TexturedModel(ballastModel, ballastTexture);

        float rotX = 0;
        float rotY = 0;
        float rotZ = 0;

        top = new RenderEntity(topTexturedModel, position, rotX, rotY, rotZ, 1);
        front = new RenderEntity(frontTexturedModel, position, rotX, rotY, rotZ, 1);
        rear = new RenderEntity(rearTexturedModel, position, rotX, rotY, rotZ, 1);
        port = new RenderEntity(portTexturedModel, position, rotX, rotY, rotZ, 1);
        starboard = new RenderEntity(starboardTexturedModel, position, rotX, rotY, rotZ, 1);
        ballast = new RenderEntity(ballastTexturedModel, position, rotX, rotY, rotZ, 1);

        allRenderEntities.add(top);
        allRenderEntities.add(front);
        allRenderEntities.add(rear);
        allRenderEntities.add(port);
        allRenderEntities.add(starboard);
        allRenderEntities.add(ballast);

        // set specular values // TODO put somewhere else. tidy this shit up
        for (Iterator<RenderEntity> iterator = allRenderEntities.iterator(); iterator.hasNext(); ) {
            RenderEntity next =  iterator.next();
            ModelTexture texture = next.getTexturedModel().getTexture();
            texture.setShineDamper(10); // TODO set
            texture.setReflectivity(1);
        }

    }

    public void resetVisibleRenderEntities(){
        visibleRenderEntities = new ArrayList<RenderEntity>();
//        visibleRenderEntities.add(ballast);
        if(Main.getGame().getWorldRenderer().isMaxZoom()){
            visibleRenderEntities.add(top);
            visibleRenderEntities.add(front);
            visibleRenderEntities.add(starboard);
            visibleRenderEntities.add(port);
            visibleRenderEntities.add(rear);
        } else {
            switch (Main.getGame().getCamera().getFacing()){
                case Camera.NORTH:
                    visibleRenderEntities.add(front);
                    visibleRenderEntities.add(starboard);
                    break;
                case Camera.EAST:
                    visibleRenderEntities.add(rear);
                    visibleRenderEntities.add(starboard);
                    break;
                case Camera.SOUTH:
                    visibleRenderEntities.add(rear);
                    visibleRenderEntities.add(port);
                    break;
                case Camera.WEST:
                    visibleRenderEntities.add(front);
                    visibleRenderEntities.add(port);
                    break;
            }
        }
    }

}
