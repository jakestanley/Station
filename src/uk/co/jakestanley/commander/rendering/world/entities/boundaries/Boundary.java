package uk.co.jakestanley.commander.rendering.world.entities.boundaries;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.rendering.world.entities.RenderEntity;

/**
 * Created by jake on 10/12/2015.
 */
public class Boundary extends RenderEntity {

    protected Boundary(Vector3f position, float rotX, float rotY, float rotZ, float scale, int textured, int multiple){
        super(position, rotX, rotY, rotZ, scale, textured, multiple); // TODO remove untextured/textured model attribute
    }

    protected static final int[] DEFAULT_INDICES = {
            0, 1, 2,    3, 0, 2,    4, 5, 6,    7, 4, 6,
            0, 1, 5,    4, 0, 5,    5, 1, 2,    6, 5, 2,
            4, 0, 3,    7, 4, 3,    3, 2, 6,    7, 3, 6
    };

    /**
     * Convert an array of 2D vectors to a 3d floor
     * @param positions
     * @return
     */
    protected static float[] generateVertexPositions(Vector2f[] positions, float height){ // TODO make this replace the ballast and texture it accordingly
        int positionCount = positions.length;
        float[] vertexPositions = new float[positions.length * 3];
//        float[] vertexPositions = new float[positions.length * 6];
        for (int i = 0; i < positionCount * 3; i += 3) {

            float x = positions[i/3].getX();
            float z = positions[i/3].getY();

            // generate top floor vertices
            vertexPositions[i] = x;
            vertexPositions[i+1] = height;
            vertexPositions[i+2] = z;

            // generate bottom floor vertices
//            vertexPositions[(positionCount * 2) + i] = x;
//            vertexPositions[(positionCount * 2) + i + 1] = DEFAULT_HEIGHT; // TODO get ballast height
//            vertexPositions[(positionCount * 2) + i + 2] = z;

        }

//        System.out.println(vertexPositions.toString());

        return vertexPositions;
    }

}
