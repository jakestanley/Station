package uk.co.jakestanley.commander.scene.graph;

import lombok.ToString;
import org.lwjgl.util.vector.Vector3f;

import java.util.*;

/**
 * A shoddy implementation of an undirected graph
 */
@ToString
public class Graph {

    private List<Vector3f> nodes; // TODO generate nodes from walkable area
    private List<int[]> edges; // TODO use weightings

    public Graph(){
        nodes = new ArrayList<Vector3f>();
        edges = new ArrayList<int[]>();
    }

    /**
     * Add a node and return its index position
     * @param node
     * @return
     */
    public int addNode(Vector3f node){
        nodes.add(node);
        int index = nodes.size() - 1;
        return index;
    }

    public int getNodeIndex(Vector3f node) throws NodeNotInGraphException {
        for (int i = 0; i < nodes.size(); i++) {
            if(nodes.get(i).equals(node)){
                return i;
            }
        }
        throw new NodeNotInGraphException(node);
    }

    /**
     * Add an undirected edge
     * @param a
     * @param b
     */
    public void addEdge(int a, int b){
        int[] one = {a,b};
        int[] two = {b,a};
        edges.add(one);
        edges.add(two);
    }

    public boolean hasEdge(int a, int b){
        for (Iterator<int[]> iterator = edges.iterator(); iterator.hasNext(); ) {
            int[] edge =  iterator.next();
            if((edge[0] == a && edge[1] == b) || (edge[1] == a && edge[0] == b)){
                return true;
            }
        }
        return false;
    }

    public boolean hasEdge(Vector3f a, Vector3f b) throws NodeNotInGraphException {
        return hasEdge(getNodeIndex(a), getNodeIndex(b));
    }

    public void removeEdge(int a, int b){
        for (Iterator<int[]> iterator = edges.iterator(); iterator.hasNext(); ) {
            int[] edge = iterator.next();
            if((edge[0] == a && edge[1] == b) || (edge[1] == a && edge[0] == b)){
                iterator.remove();
            }
        }
    }

}
