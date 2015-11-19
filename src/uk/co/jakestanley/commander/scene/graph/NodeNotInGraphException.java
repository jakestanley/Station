package uk.co.jakestanley.commander.scene.graph;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by jake on 15/11/2015.
 */
public class NodeNotInGraphException extends Exception {

    public NodeNotInGraphException(Vector3f node){
        super(node.toString() + " does not exist in the graph");
    }

}
