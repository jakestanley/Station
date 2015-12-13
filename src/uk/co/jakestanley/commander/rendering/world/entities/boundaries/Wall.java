package uk.co.jakestanley.commander.rendering.world.entities.boundaries;

import lombok.Getter;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.world.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.models.TexturedModel;
import uk.co.jakestanley.commander.rendering.world.textures.ModelTexture;
import uk.co.jakestanley.commander.rendering.world.tools.Out;

/**
 * Created by jake on 10/12/2015.
 */
public class Wall extends Boundary {

    private static final float DEFAULT_ROT_X = 0;
    private static final float DEFAULT_ROT_Y = 0;
    private static final float DEFAULT_ROT_Z = 0;
    private static final float DEFAULT_SCALE = 1;

    public static final float DEFAULT_WIDTH = 2.5f;
    public static final float DEFAULT_HEIGHT = 20f;

    @Getter private boolean placed;
    @Getter private float round;
    @Getter private Vector2f start;
    @Getter private Vector2f end;

    public Wall(Vector2f start){ // TODO check if placement is valid
        super(new Vector3f(0, DEFAULT_HEIGHT, 0), DEFAULT_ROT_X, DEFAULT_ROT_Y, DEFAULT_ROT_Z, DEFAULT_SCALE, RenderEntity.UNTEXTURED_MODEL, RenderEntity.SINGLE_MODEL); // TODO remove untextured/textured model attribute
        this.round = DEFAULT_WIDTH;
        this.placed = false;
        Vector2f wallCoordinates = convertToWallCoordinates(start, round);
        this.start = wallCoordinates;
        setEnd(wallCoordinates);
    }

    public Wall(Vector2f start, Vector2f end){
        super(new Vector3f(0, DEFAULT_HEIGHT, 0), DEFAULT_ROT_X, DEFAULT_ROT_Y, DEFAULT_ROT_Z, DEFAULT_SCALE, RenderEntity.UNTEXTURED_MODEL, RenderEntity.SINGLE_MODEL);
        this.round = DEFAULT_WIDTH;
        this.placed = true;
        this.start = convertToWallCoordinates(start, round);
        setEnd(end);
    }

    public void setEnd(Vector2f end){
        this.end = convertToWallCoordinates(end, round);
        this.rawModel = generateWallModel(start, this.end);
        texturedModel = new TexturedModel(rawModel, new ModelTexture(Main.getGame().loader.loadTexture("test/texturemate-greyscale05")));
    }

    public boolean place(){
        if(!placed){
            placed = false;
            return true;
        }
        return false; // can't place if already placed
    }

    private static RawModel generateWallModel(Vector2f start, Vector2f end){ // TODO height, width variables, etc
        System.out.println("Start: [" + start.getX() + ", " + start.getY() + "] - End: [" + end.getX() + ", " + end.getY() + "]");

        Vector2f[] floor2dVertices = new Vector2f[4];
        if(start.getX() == end.getX()){

        } else {

        }
        floor2dVertices[0] = new Vector2f(end.getX() + DEFAULT_WIDTH, end.getY() + DEFAULT_WIDTH); // TODO add default widths back in
        floor2dVertices[1] = new Vector2f(start.getX(), end.getY() + DEFAULT_WIDTH);
        floor2dVertices[2] = new Vector2f(start.getX(), start.getY()); // TODO optimise
        floor2dVertices[3] = new Vector2f(end.getX() + DEFAULT_WIDTH, start.getY());

        Out.print("2D floor vertices", floor2dVertices);
        return generateModel(floor2dVertices, DEFAULT_INDICES, DEFAULT_HEIGHT);
    }

    private static Vector2f convertToWallCoordinates(Vector2f realCoordinates, float round){ // TODO consider putting somewhere else?
        Vector2f wallCoordinates = new Vector2f();
        boolean negativeX = (realCoordinates.getX() < 0);
        boolean negativeY = (realCoordinates.getY() < 0);

        float wallX = round * (Math.round(Math.abs(realCoordinates.getX() / round)));
        float wallY = round * (Math.round(Math.abs(realCoordinates.getY() / round)));

        if(negativeX){
            wallX = -wallX;
        }

        if(negativeY){
            wallY = -wallY;
        }

        wallCoordinates.setX(wallX); // TODO offset if working with negative values
        wallCoordinates.setY(wallY);

        return wallCoordinates;
    }
}
