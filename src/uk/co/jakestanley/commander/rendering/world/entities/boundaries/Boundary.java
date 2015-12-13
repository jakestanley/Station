package uk.co.jakestanley.commander.rendering.world.entities.boundaries;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.world.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.models.RawModel;

/**
 * Created by jake on 10/12/2015.
 */
public class Boundary extends RenderEntity {

    protected Boundary(Vector3f position, float rotX, float rotY, float rotZ, float scale, int textured, int multiple){
        super(position, rotX, rotY, rotZ, scale, textured, multiple); // TODO remove untextured/textured model attribute
    }

    protected static final int[] DEFAULT_FLAT_INDICES = { // TODO figure out how to generate these
            0, 1, 2,
            3, 0, 2
    };

    protected static final int[] DEFAULT_INDICES = {
            0, 1, 2,    3, 0, 2,    4, 5, 6,    7, 4, 6,
            0, 1, 5,    4, 0, 5,    5, 1, 2,    6, 5, 2,
            4, 0, 3,    7, 4, 3,    3, 2, 6,    7, 3, 6
    };

    protected static final float[] DEFAULT_TEXTURE_COORDINATES = {
            0,0,    0,1,    1,1,    1,0,    0,0,    0,1,
            1,1,    1,0,    0,0,    0,1,    1,1,    1,0,
            0,0,    0,1,    1,1,    1,0,    0,0,    0,1,
            1,1,    1,0,    0,0,    0,1,    1,1,    1,0
    };

    /**
     * Convert an array of 2D vectors to a 3d floor
     * @param positions
     * @return
     */
    protected static float[] generateVertexPositions(Vector2f[] positions, float height){ // TODO make this replace the ballast and texture it accordingly

        int positionCount = positions.length;
        float[] vertexPositionsTop = new float[positionCount * 3];
        float[] vertexPositionsBottom = new float[positionCount * 3];

        for (int i = 0; i < positionCount; i++) {

            float x = positions[i].getX();
            float z = positions[i].getY();

            // generate top floor vertices
            int vertexIndex = (i * 3);
            vertexPositionsTop[vertexIndex] = x;
            vertexPositionsTop[vertexIndex+1] = 0; // TODO get y start globalPosition and add height
            vertexPositionsTop[vertexIndex+2] = z;

            // generate bottom floor vertices
            vertexPositionsBottom[vertexIndex] = x;
            vertexPositionsBottom[vertexIndex+1] = -height; // TODO get y start globalPosition
            vertexPositionsBottom[vertexIndex+2] = z;

        }

        float[] vertexPositions = new float[positionCount * 6];

        int i = 0;

        for(; i < vertexPositionsTop.length; i++){
            vertexPositions[i] = vertexPositionsTop[i];
        }

        for (int j = 0; j < vertexPositionsBottom.length; j++){
            vertexPositions[i] = vertexPositionsBottom[j];
            i++;
        }

        return vertexPositions;

    }

    /**
     * Generate a square floor
     * @param width
     * @param height
     * @return
     */
    protected static RawModel generateModel(float width, float depth, float height){
        Vector2f[] floor2dVertices = { new Vector2f(width,depth), new Vector2f(0,depth), new Vector2f(0,0), new Vector2f(width,0)};
        float[] floor3dVertices = generateVertexPositions(floor2dVertices, height);
        return Main.getGame().loader.loadToVAO(floor3dVertices, DEFAULT_INDICES, DEFAULT_TEXTURE_COORDINATES);
    }

    protected static RawModel generateModel(Vector2f[] floor2dVertices, int[] floorIndices, float height){ // TODO remove or change to work dynamically
        float[] floor3dVertices;
        floor3dVertices = generateVertexPositions(floor2dVertices, height);
        return Main.getGame().loader.loadToVAO(floor3dVertices, floorIndices, DEFAULT_TEXTURE_COORDINATES); // TODO
    }

}
