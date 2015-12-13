package uk.co.jakestanley.commander.rendering.world.tools;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by jake on 12/12/2015.
 */
public class Out {

    public static void print(String identifier, Vector2f vector){
        System.out.println(identifier + " is a 2D vector: [" + vector.getX() + ", " + vector.getY() + "]");
    }

    public static void print(String identifier, Vector3f vector){
        System.out.println(identifier + " is a 3D vector: [" + vector.getX() + ", " + vector.getY() + ", " + vector.getZ() + "]");
    }

    public static void print(String identifier, Vector2f[] array){
        System.out.println(identifier + " is an array of 2D vectors: ");
        for(int i = 0; i < array.length; i++){
            System.out.println(i + ": [" + array[i].getX() + ", " + array[i].getY() + "]");
        }
    }

    public static void print(String identifier, Vector3f[] array){
        System.out.println(identifier + " is an array of 3D vectors: ");
        for(int i = 0; i < array.length; i++){
            System.out.println(i + ": [" + array[i].getX() + ", " + array[i].getY() + ", " + array[i].getZ() + "]");
        }
    }

}
