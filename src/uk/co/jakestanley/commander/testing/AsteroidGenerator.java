package uk.co.jakestanley.commander.testing;

import lombok.Getter;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.rendering.world.Loader;
import uk.co.jakestanley.commander.rendering.world.entities.RenderEntity;
import uk.co.jakestanley.commander.rendering.world.models.ObjLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jake on 13/12/2015.
 */
public class AsteroidGenerator {

    private int count;
    private Random random;
    private float[] xRotations;
    private float[] yRotations;
    private float[] zRotations;
    @Getter private List<RenderEntity> asteroids;

    public AsteroidGenerator(int count, ObjLoader objLoader, Loader loader){
        this.count = count;
        random = new Random();
        xRotations = new float[count];
        yRotations = new float[count];
        zRotations = new float[count];
        asteroids = new ArrayList<RenderEntity>();
        float offset = (MAX_DISTANCE / 2);
        for (int i = 0; i < count; i++) {
            float x = random.nextFloat() * MAX_DISTANCE - offset;
            float y = random.nextFloat() * MAX_DISTANCE - offset;
            float z = random.nextFloat() * MAX_DISTANCE - offset;
            xRotations[i] = random.nextFloat() * MAX_ROTATION_SPEED;
            yRotations[i] = random.nextFloat() * MAX_ROTATION_SPEED;
            zRotations[i] = random.nextFloat() * MAX_ROTATION_SPEED;
            asteroids.add(new RenderEntity(objLoader.loadTexturedModel("externals/asteroid", loader), new Vector3f(x, y, z), 0, 0, 0, 0.1f));
        }
    }

    public void update(){
        for (int i = 0; i < count; i++) {
            RenderEntity asteroid = asteroids.get(i);
            asteroid.setRotX(asteroid.getRotX() + xRotations[i]);
            asteroid.setRotY(asteroid.getRotY() + yRotations[i]);
            asteroid.setRotZ(asteroid.getRotZ() + zRotations[i]);
        }
    }

    private static final float MAX_DISTANCE = 1250f;
    private static final float MAX_ROTATION_SPEED = 0.25f;

}
