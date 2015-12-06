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
import java.util.List;

/**
 * Created by jake on 05/12/2015.
 */
public class Ship { // TODO a superclass

    private RenderEntity top;
    private RenderEntity front;
    private RenderEntity rear;
    private RenderEntity port;
    private RenderEntity starboard;
    private RenderEntity ballast;

    @Getter
    private List<RenderEntity> renderEntities;

    public Ship(){ // TODO make a center point - TODO a render at coordinate
        renderEntities = new ArrayList<RenderEntity>();
        loadModels();
    }

    private void loadModels(){

        RawModel topModel = ObjLoader.loadObjModel("ships/Gatlinburg_Top", CommanderGame3D.loader, ObjLoader.TEXTURED);
        RawModel frontModel = ObjLoader.loadObjModel("ships/Gatlinburg_Front", CommanderGame3D.loader, ObjLoader.TEXTURED);
        RawModel rearModel = ObjLoader.loadObjModel("ships/Gatlinburg_Rear", CommanderGame3D.loader, ObjLoader.TEXTURED);
        RawModel portModel = ObjLoader.loadObjModel("ships/Gatlinburg_Port", CommanderGame3D.loader, ObjLoader.TEXTURED);
        RawModel starboardModel = ObjLoader.loadObjModel("ships/Gatlinburg_Starboard", CommanderGame3D.loader, ObjLoader.TEXTURED);
        RawModel ballastModel = ObjLoader.loadObjModel("ships/Gatlinburg_Ballast", CommanderGame3D.loader, ObjLoader.TEXTURED);

        ModelTexture topTexture = new ModelTexture(CommanderGame3D.loader.loadTexture("test/yellow"));
        ModelTexture frontTexture = new ModelTexture(CommanderGame3D.loader.loadTexture("ships/AO_2_Front"));
        ModelTexture rearTexture = new ModelTexture(CommanderGame3D.loader.loadTexture("ships/AO_2_Rear"));
        ModelTexture portTexture = new ModelTexture(CommanderGame3D.loader.loadTexture("ships/AO_2_Port"));
        ModelTexture starboardTexture = new ModelTexture(CommanderGame3D.loader.loadTexture("ships/AO_2_Starboard"));
        ModelTexture ballastTexture = new ModelTexture(CommanderGame3D.loader.loadTexture("Gatlinburg"));

        TexturedModel topTexturedModel = new TexturedModel(topModel, topTexture);
        TexturedModel frontTexturedModel = new TexturedModel(frontModel, frontTexture);
        TexturedModel rearTexturedModel = new TexturedModel(rearModel, rearTexture);
        TexturedModel portTexturedModel = new TexturedModel(portModel, portTexture);
        TexturedModel starboardTexturedModel = new TexturedModel(starboardModel, starboardTexture);
        TexturedModel ballastTexturedModel = new TexturedModel(ballastModel, ballastTexture);

        Vector3f renderAt = new Vector3f(0,0,0);
        float rotX = 0;
        float rotY = 0;
        float rotZ = 0;

        top = new RenderEntity(topTexturedModel, renderAt, rotX, rotY, rotZ, 1);
        front = new RenderEntity(frontTexturedModel, renderAt, rotX, rotY, rotZ, 1);
        rear = new RenderEntity(rearTexturedModel, renderAt, rotX, rotY, rotZ, 1);
        port = new RenderEntity(portTexturedModel, renderAt, rotX, rotY, rotZ, 1);
        starboard = new RenderEntity(starboardTexturedModel, renderAt, rotX, rotY, rotZ, 1);
        ballast = new RenderEntity(ballastTexturedModel, renderAt, rotX, rotY, rotZ, 1);

//        renderEntities.add(top);

//        renderEntities.add(rear);
//        renderEntities.add(port);



    }

    public void resetRenderEntities(){
        renderEntities = new ArrayList<RenderEntity>();
        switch (CommanderGame3D.camera.getFacing()){
            case Camera.NORTH:
                renderEntities.add(front);
                renderEntities.add(starboard);
                renderEntities.add(ballast);
                break;
            case Camera.EAST:
                renderEntities.add(rear);
                renderEntities.add(starboard);
                renderEntities.add(ballast);
                break;
            case Camera.SOUTH:
                renderEntities.add(rear);
                renderEntities.add(port);
                renderEntities.add(ballast);
                break;
            case Camera.WEST:
                renderEntities.add(front);
                renderEntities.add(port);
                renderEntities.add(ballast);
                break;
        }
    }

    public boolean hasSetRenderEntities() {
         return renderEntities != null;
    }
}
