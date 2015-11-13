package uk.co.jakestanley.commander.rendering.world.threedimensional.models;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by jp-st on 11/11/2015.
 */
public class Loader {

    // From YouTube: OpenGL 3D Game Tutorial
    private List<Integer> vaoList = new ArrayList<Integer>();
    private List<Integer> vboList = new ArrayList<Integer>();

    public RawModel loadToVAO(float[] positions, int[] indices){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, positions);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public void cleanup(){
        for (Iterator<Integer> iterator = vaoList.iterator(); iterator.hasNext(); ) {
            Integer next = iterator.next();
            GL30.glDeleteVertexArrays(next);
        }
        for (Iterator<Integer> iterator = vboList.iterator(); iterator.hasNext(); ) {
            Integer next =  iterator.next();
            GL15.glDeleteBuffers(next);
        }
    }

    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaoList.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, float[] data){
        int vboID = GL15.glGenBuffers();
        vboList.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer floatBuffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW); // GL_STATIC_DRAW so OpenGL knows we don't want to edit this data
        GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // unbinds current VBO
    }

    /**
     * To unbind VAO when you're finished using it
     */
    private void unbindVAO(){
        GL30.glBindVertexArray(0); // to unbind currently bound VAO
    }

    private void bindIndicesBuffer(int[] indices){
        int vboID = GL15.glGenBuffers();
        vboList.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID); // tells OpenGL to use it as an indices buffer
        IntBuffer intBuffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, intBuffer, GL15.GL_STATIC_DRAW); // (what data is it, data, how is it going to be used? [won't be changed])
    }

    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer intBuffer = BufferUtils.createIntBuffer(data.length);
        intBuffer.put(data);
        intBuffer.flip(); // so it's ready to be read from
        return intBuffer;
    }


    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.length);
        floatBuffer.put(data);
        floatBuffer.flip(); // signifies that the buffer is ready
        return floatBuffer;
    }

}
