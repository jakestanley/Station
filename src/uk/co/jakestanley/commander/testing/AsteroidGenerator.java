package uk.co.jakestanley.commander.testing;

import lombok.Getter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Main;
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

    private boolean firstRun;
    private int count;
    private ObjLoader objLoader;
    private Loader loader;
    private Random random;
    private float[] xRotations;
    private float[] yRotations;
    private float[] zRotations;
    private float offset;
    @Getter private RenderEntity[] asteroids;

    public AsteroidGenerator(int count, ObjLoader objLoader, Loader loader){
        firstRun = true;
        this.count = count;
        this.objLoader = objLoader;
        this.loader = loader;
        random = new Random();
        xRotations = new float[count];
        yRotations = new float[count];
        zRotations = new float[count];
        offset = (MAX_DISTANCE / 2);
        asteroids = new RenderEntity[count];
    }

    public void init(){
        if(!firstRun){
            System.out.println("Regenerating asteroids");
        } else {
            System.out.println("Generating asteroids");
            firstRun = false;
        }

        Vector3f shipPosition = Main.getGame().getShip().getGlobalPosition(); // to ensure the asteroids are generated around the ship
        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i] = generateAsteroid(i, shipPosition);
        }
    }

    public void update(){
        Vector3f shipPosition = Main.getGame().getShip().getGlobalPosition(); // to ensure the asteroids are generated around the ship
        if(Keyboard.isKeyDown(Keyboard.KEY_R)){
            init();
        }
        for (int i = 0; i < asteroids.length; i++) {
            RenderEntity asteroid = asteroids[i];
            asteroid.setRotX(asteroid.getRotX() + xRotations[i]);
            asteroid.setRotY(asteroid.getRotY() + yRotations[i]);
            asteroid.setRotZ(asteroid.getRotZ() + zRotations[i]);
        }
        // clean up old asteroids
        for (int i = 0; i < asteroids.length; i++) {
            RenderEntity asteroid = asteroids[i];
            if(asteroid.getPosition().getX() < shipPosition.getX() - 1500f){
                asteroids[i] = generateAsteroid(i, shipPosition);
            }
        }
    }

    private RenderEntity generateAsteroid(int i, Vector3f shipPosition){
        float x = random.nextFloat() * MAX_DISTANCE - offset + shipPosition.getX() + 1500f;
        float y = random.nextFloat() * MAX_Z_DISTANCE - offset + shipPosition.getY();
        float z = random.nextFloat() * MAX_DISTANCE - offset + shipPosition.getZ();
        float scale = random.nextFloat() * MAX_SCALE;
        xRotations[i] = random.nextFloat() * MAX_ROTATION_SPEED;
        yRotations[i] = random.nextFloat() * MAX_ROTATION_SPEED;
        zRotations[i] = random.nextFloat() * MAX_ROTATION_SPEED;
        return new RenderEntity(objLoader.loadTexturedModel("externals/asteroid_lp", loader), new Vector3f(x, y, z), 0, 0, 0, scale);
    }

    private static final float MAX_SCALE = 50f;
    private static final float MAX_DISTANCE = 1250f;
    private static final float MAX_Z_DISTANCE = 2500f;
    private static final float MAX_ROTATION_SPEED = 0.4f;

}
