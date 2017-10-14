package uk.co.jakestanley.commander.rendering.world.entities.boundaries;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.world.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.textures.ModelTexture;

/**
 * Created by jake on 03/12/2015.
 */
public class Floor extends Boundary {

    private static final Vector2f[] DEFAULT_VERTICES = { new Vector2f(2.5f,2.5f), new Vector2f(-2.5f,2.5f), new Vector2f(-2.5f,-2.5f), new Vector2f(2.5f,-2.5f)};

    private static final float DEFAULT_X = -60.9282f;
    private static final float DEFAULT_Y = 0;
    private static final float DEFAULT_Z = -27.44542f; // TODO make these non-hardcoded
    private static final float DEFAULT_ROT_X = 0;
    private static final float DEFAULT_ROT_Y = 0;
    private static final float DEFAULT_ROT_Z = 0;
    private static final float DEFAULT_SCALE = 1;

    private static final float DEFAULT_HEIGHT = 5f; // TODO make as much stuff as programmatic and procedural as possible. floor could use a model after all

    private float width, height;

    /**
     * Constructor to generate the default floor (testing only, pretty much)
     */
    public Floor(){
        super(new Vector3f(DEFAULT_X, DEFAULT_Y, DEFAULT_Z), DEFAULT_ROT_X, DEFAULT_ROT_Y, DEFAULT_ROT_Z, DEFAULT_SCALE, RenderEntity.UNTEXTURED_MODEL, RenderEntity.SINGLE_MODEL);
        rawModel = generateModel(DEFAULT_VERTICES, DEFAULT_INDICES, DEFAULT_HEIGHT); // generate default floor model
        texturedModel = new TexturedModel(rawModel, new ModelTexture(Main.getGame().loader.loadTexture("test/mean")));
    }

    public Floor(float width, float depth){ // TODO render offset for this and other uk.co.jakestanley.commander2d.map components for loading from file, for example.
        super(new Vector3f(DEFAULT_X, DEFAULT_Y, DEFAULT_Z), DEFAULT_ROT_X, DEFAULT_ROT_Y, DEFAULT_ROT_Z, DEFAULT_SCALE, RenderEntity.TEXTURED_MODEL, RenderEntity.SINGLE_MODEL);
        // TODO just translate model
        this.width = width;
        this.height = depth;
        rawModel = generateModel(width, depth, DEFAULT_HEIGHT);
        texturedModel = new TexturedModel(rawModel, new ModelTexture(Main.getGame().loader.loadTexture("test/texturemate-greyscale03")));
    }

    public Floor(Vector2f[] vertices, int[] indices){
        super(new Vector3f(DEFAULT_X, DEFAULT_Y, DEFAULT_Z), DEFAULT_ROT_X, DEFAULT_ROT_Y, DEFAULT_ROT_Z, DEFAULT_SCALE, RenderEntity.TEXTURED_MODEL, RenderEntity.SINGLE_MODEL); // TODO more arguments
        rawModel = generateModel(vertices, indices, height);
    }

    public boolean containsPoint(Vector2f point){
        float minX = getPosition().getX();
        float maxX = minX + width;
        float minY = getPosition().getY();
        float maxY = minY + height;
        float pointX = point.getX();
        float pointY = point.getY() + height/2 + 2; // TODO fix

        return ((pointX > minX) && (pointX < maxX) && (pointY > minY) && (pointY < maxY));
    }

    private static int[] generateIndicesArray(){
        // TODO make this work
        return null;
    }

}
