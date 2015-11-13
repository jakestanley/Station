package uk.co.jakestanley.commander.rendering.world.threedimensional.models;

import lombok.Getter;

/**
 * Created by jp-st on 11/11/2015.
 */
@Getter
public class RawModel {

    @Getter private int vaoID;
    @Getter private int vertexCount;

    public RawModel(int vaoID, int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() { // TODO fix Lombok deciding to not fucking work
        return vertexCount;
    }
}
