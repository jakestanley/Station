package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.Game3D;
import uk.co.jakestanley.commander.Main;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Created by jake on 19/11/2015.
 */
@Getter
@AllArgsConstructor
public abstract class Camera {

    protected Vector3f start;
    protected Vector3f position;
    protected float pitch; // up or down
    protected float yaw; // left or right
    protected float roll;
    protected float zoom;
    protected int facing; // TODO set accordingly
    protected int direction;
    protected int cooldown;
    protected boolean rotating;

    public Camera(){
        position = new Vector3f(0,0,0);
        facing = NORTH;
        rotating = false;
        direction = RIGHT;
        cooldown = 0;
//        Game3D.ship.resetVisibleRenderEntities();
    }

    public Camera(Vector3f position, float pitch, float yaw, float roll){
        this.zoom = DEFAULT_ZOOM;
        this.start = position;
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        cooldown = 0;
//        Game3D.ship.resetVisibleRenderEntities();
    }

    public abstract void move();

    protected void rotateLeft(){
        yaw += YAW_SPEED;
        position = calculateNewPosition(YAW_SPEED);
    }

    protected void rotateRight(){
        yaw -= YAW_SPEED;
        position = calculateNewPosition(-YAW_SPEED);
    }

    private Vector3f calculateNewPosition(float rot){ // fuck yea
//        Matrix2f rotationMatrix = new Matrix2f();
//        rotationMatrix.m00 = (float) Math.cos(rotation);
//        rotationMatrix.m10 = (float) -Math.sin(rotation);
//        rotationMatrix.m01 = (float) Math.sin(rotation);
//        rotationMatrix.m11 = (float) Math.cos(rotation); // TODO save this from above
//
//        float newX = (position.getX() * rotationMatrix.m00) + (position.getZ() * rotationMatrix.m01);
//        float newZ = (position.getX() * rotationMatrix.m10) + (position.getZ() * rotationMatrix.m11);
//        Vector3f newPosition = new Vector3f(newX, position.getY(), newZ);


//        int centerX = 0;
//        int centerZ = 0;
//        float x = -rotation;
//        float point2x = position.getX();
//        float point2z = position.getZ();
//        double newX = centerX + (point2x-centerX)*Math.cos(x) - (point2z-centerZ)*Math.sin(x);
//        double newZ = centerZ + (point2x-centerX)*Math.sin(x) + (point2z-centerZ)*Math.cos(x);
//
//        Vector3f newPosition = new Vector3f((float) newX, position.getY(), (float) newZ);
        Point2D origin = new Point2D.Double(position.getX(), position.getZ());
        Point2D result = new Point2D.Double();
        AffineTransform rotation = new AffineTransform();
        double angleInRadians = (rot * Math.PI / 180);
        rotation.rotate(angleInRadians, 0, 0);
        rotation.transform(origin, result);
        Vector3f newPos = new Vector3f((float) result.getX(), position.getY(), (float) result.getY());
        return newPos;


//        return newPosition;
    }

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    protected static final int COOLDOWN_TICKS = 12;
    protected static final int LEFT = 4;
    protected static final int RIGHT = 5;
    protected static final float SCROLL_SPEED = 1f;
    protected static final float YAW_SPEED = 10f;

    protected static final float DEFAULT_ZOOM = 1f;
    protected static final float MIN_ZOOM = 0.2f;
    protected static final float MAX_ZOOM = 2.0f;
    protected static final float ZOOM_INCREMENT = 0.1f;

}
