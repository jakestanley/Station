package uk.co.jakestanley.commander.rendering.entities.world;

import lombok.Getter;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.CommanderGame3D;
import uk.co.jakestanley.commander.rendering.entities.Camera;
import uk.co.jakestanley.commander.rendering.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.ObjLoader;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.threedimensional.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.threedimensional.textures.ModelTexture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jake on 05/12/2015.
 */
@Getter
public class Ship { // TODO a superclass

    private RenderEntity top;
    private RenderEntity front;
    private RenderEntity rear;
    private RenderEntity port;
    private RenderEntity starboard;
    private RenderEntity ballast;

    private String identifier;

    private List<RenderEntity> allRenderEntities;
    private List<RenderEntity> visibleRenderEntities;

    /**
     * identifier should be all lower case and should correspond with res directory structure
     * @param identifier
     */
    public Ship(String identifier){ // TODO make a center point - TODO a render at coordinate
        this.identifier = identifier; // TODO generate floor property here?
        allRenderEntities = new ArrayList<RenderEntity>();
        visibleRenderEntities = new ArrayList<RenderEntity>();
        loadModels();
    }

    private void loadModels(){

        RawModel topModel = ObjLoader.loadObjModel("ships/"+identifier+"/Top", CommanderGame3D.loader, ObjLoader.TEXTURED);
        RawModel frontModel = ObjLoader.loadObjModel("ships/"+identifier+"/Front", CommanderGame3D.loader, ObjLoader.TEXTURED);
        RawModel rearModel = ObjLoader.loadObjModel("ships/"+identifier+"/Rear", CommanderGame3D.loader, ObjLoader.TEXTURED);
        RawModel portModel = ObjLoader.loadObjModel("ships/"+identifier+"/Port", CommanderGame3D.loader, ObjLoader.TEXTURED);
        RawModel starboardModel = ObjLoader.loadObjModel("ships/"+identifier+"/Starboard", CommanderGame3D.loader, ObjLoader.TEXTURED);
        RawModel ballastModel = ObjLoader.loadObjModel("ships/"+identifier+"/Ballast", CommanderGame3D.loader, ObjLoader.TEXTURED);

        ModelTexture topTexture = new ModelTexture(CommanderGame3D.loader.loadTexture("test/yellow"));
        ModelTexture frontTexture = new ModelTexture(CommanderGame3D.loader.loadTexture("ships/"+identifier+"/Front"));
        ModelTexture rearTexture = new ModelTexture(CommanderGame3D.loader.loadTexture("ships/"+identifier+"/Rear"));
        ModelTexture portTexture = new ModelTexture(CommanderGame3D.loader.loadTexture("ships/"+identifier+"/Port"));
        ModelTexture starboardTexture = new ModelTexture(CommanderGame3D.loader.loadTexture("ships/"+identifier+"/Starboard"));
        ModelTexture ballastTexture = new ModelTexture(CommanderGame3D.loader.loadTexture("test/green"));

        TexturedModel topTexturedModel = new TexturedModel(topModel, topTexture);
        TexturedModel frontTexturedModel = new TexturedModel(frontModel, frontTexture);
        TexturedModel rearTexturedModel = new TexturedModel(rearModel, rearTexture);
        TexturedModel portTexturedModel = new TexturedModel(portModel, portTexture);
        TexturedModel starboardTexturedModel = new TexturedModel(starboardModel, starboardTexture);
        TexturedModel ballastTexturedModel = new TexturedModel(ballastModel, ballastTexture);

        Vector3f renderAt = new Vector3f(0,0,0); // TODO set
        float rotX = 0;
        float rotY = 0;
        float rotZ = 0;

        top = new RenderEntity(topTexturedModel, renderAt, rotX, rotY, rotZ, 1);
        front = new RenderEntity(frontTexturedModel, renderAt, rotX, rotY, rotZ, 1);
        rear = new RenderEntity(rearTexturedModel, renderAt, rotX, rotY, rotZ, 1);
        port = new RenderEntity(portTexturedModel, renderAt, rotX, rotY, rotZ, 1);
        starboard = new RenderEntity(starboardTexturedModel, renderAt, rotX, rotY, rotZ, 1);
        ballast = new RenderEntity(ballastTexturedModel, renderAt, rotX, rotY, rotZ, 1);

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

    public void resetRenderEntities(){
        visibleRenderEntities = new ArrayList<RenderEntity>();
        switch (CommanderGame3D.camera.getFacing()){
            case Camera.NORTH:
                visibleRenderEntities.add(front);
                visibleRenderEntities.add(starboard);
                visibleRenderEntities.add(ballast);
                break;
            case Camera.EAST:
                visibleRenderEntities.add(rear);
                visibleRenderEntities.add(starboard);
                visibleRenderEntities.add(ballast);
                break;
            case Camera.SOUTH:
                visibleRenderEntities.add(rear);
                visibleRenderEntities.add(port);
                visibleRenderEntities.add(ballast);
                break;
            case Camera.WEST:
                visibleRenderEntities.add(front);
                visibleRenderEntities.add(port);
                visibleRenderEntities.add(ballast);
                break;
        }
    }

    public boolean hasSetRenderEntities() {
         return visibleRenderEntities != null;
    }

}
