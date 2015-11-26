package uk.co.jakestanley.commander.rendering.world.threedimensional.models;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import uk.co.jakestanley.commander.rendering.world.threedimensional.Loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jake on 21/11/2015. Adapted from ThinMatrix's tutorials (linked below)
 * https://www.youtube.com/watch?v=YKFYtekgnP8
 */
public class ObjLoader {

    public static RawModel loadObjModel(String name, Loader loader, boolean textured){

        // get a file pointer
        FileReader file = null;
        String path = "res/models/"+name+".obj";
        try {
            file = new FileReader(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("Failed to load " + path);
            e.printStackTrace();
        }

        // read from the obj file
        BufferedReader reader = new BufferedReader(file); // TODO make safer
        String line;

        // initialise lists for sorting the vertex data
        List<Vector3f>  vertices = new ArrayList<Vector3f>();
        List<Vector2f>  textures = new ArrayList<Vector2f>(); // texture coordinates
        List<Vector3f>  normals  = new ArrayList<Vector3f>(); // for lighting
        List<Integer>   indices   = new ArrayList<Integer>();

        // initialise arrays for the VAO loader
        float[] verticesArray = null;
        float[] texturesArray = null;
        float[] normalsArray = null;
        int[]   indicesArray = null;

        try {
            while(true){ // TODO improve
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if(line.startsWith("v ")){ // if line is a vertex position
                    Vector3f vertex = new Vector3f( Float.parseFloat(currentLine[1]),
                                                    Float.parseFloat(currentLine[2]),
                                                    Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);

                } else if(line.startsWith("vt ")){ // if line is a texture coordinate
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]),
                                                    Float.parseFloat(currentLine[2]));
                    textures.add(texture);

                } else if(line.startsWith("vn ")){ // if line is a normal
                    Vector3f normal = new Vector3f( Float.parseFloat(currentLine[1]),
                                                    Float.parseFloat(currentLine[2]),
                                                    Float.parseFloat(currentLine[3]));
                    normals.add(normal);

                } else if(line.startsWith("f ")){ // if line is a face, it means we've moved on to the next section of the file
                    texturesArray = new float[vertices.size() * 2]; // initialise arrays now we know the size
                    normalsArray = new float[vertices.size() * 3];
                    break; // break the vertex parsin "while(true)" loop
                }
            }

            while(line != null){ // while we're still getting lines

                if(!line.startsWith("f ")){ // if this isn't a face line
                    line = reader.readLine();
                    continue; // restart loop
                }

                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, textures, normals, texturesArray, normalsArray, textured);
                processVertex(vertex2, indices, textures, normals, texturesArray, normalsArray, textured);
                processVertex(vertex3, indices, textures, normals, texturesArray, normalsArray, textured);
                line = reader.readLine();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size()*3];
        indicesArray = new int[indices.size()];

        // convert the vertices list into an array
        int vertexPointer = 0;
        for (Vector3f vertex:
             vertices) {
            // 1, 2, 3, <line break> 4, 5, 6 <line break> etc, etc
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        // convert the indices list into an array
        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }

        // TODO save this data for faster loading later

        return loader.loadToVAO(verticesArray, indicesArray, texturesArray, normalsArray);

    }

    private static void processVertex(String[] vertexData,
                                      List<Integer> indeces, List<Vector2f> textures, List<Vector3f> normals,
                                      float[] texturesArray, float[] normalsArray, boolean textured){
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indeces.add(currentVertexPointer);
        if(textured){
            Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1])-1);
            texturesArray[currentVertexPointer*2] = currentTexture.x;
            texturesArray[(currentVertexPointer*2)+1] = 1 - currentTexture.y;
        }
        Vector3f currentNormal = normals.get(Integer.parseInt(vertexData[2])-1);
        normalsArray[currentVertexPointer*3] = currentNormal.x;
        normalsArray[(currentVertexPointer*3)+1] = currentNormal.y;
        normalsArray[(currentVertexPointer*3)+2] = currentNormal.z;


    }

}
