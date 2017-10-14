package uk.co.jakestanley.commander.rendering.world.tools;

import org.lwjgl.util.vector.Vector2f;

/**
 * Created by jake on 12/12/2015.
 */
public class Out {

    public static void print(Vector2f[] array){
        for(int i = 0; i < array.length; i++){
            System.out.println(i + ": [" + array[i].getX() + ", " + array[i].getY() + "]");
        }
    }

}
