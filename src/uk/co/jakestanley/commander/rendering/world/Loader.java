package uk.co.jakestanley.commander.rendering.world;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import uk.co.jakestanley.commander.rendering.world.models.RawModel;
import uk.co.jakestanley.commander.rendering.world.textures.ModelTexture;
import uk.co.jakestanley.commander.rendering.world.textures.TextureCache;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jp-st on 11/11/2015.
 */
public class Loader {

    private boolean caching;
    private TextureCache textureCache;

    // From YouTube: OpenGL 3D Game Tutorial
    private List<Integer> vaoList = new ArrayList<Integer>();
    private List<Integer> vboList = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    public Loader(boolean caching){
        this.caching = caching;
        if(caching){
            textureCache = new TextureCache();
        }
    }

    public RawModel loadToVAO(float[] positions, int[] indices, float[] textureCoordinates, float[] normals){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions); // 2nd value is coordinate size, which is 3 here
        storeDataInAttributeList(1, 2, textureCoordinates); // 2nd value here is texture mapping values
        storeDataInAttributeList(2, 3, normals);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    /**
     * Untextured model with no normals
     * @param positions
     * @param indices
     * @return
     */
    public RawModel loadToVAO(float[] positions, int[] indices){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions); // 2nd value is coordinate size, which is 3 here
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public int loadTexture(String path){ // TODO expand Loader into an abstract class with subclasses for managing loaded components? - could iterate through loaders and start/cleanup/etc

        if(ENABLE_CACHING == caching){
            Integer cachedTextureId = textureCache.getModelId(path);
            if(cachedTextureId != null){
                return cachedTextureId;
            }
        }

        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/textures/" + path + ".png"));
        } catch (IOException e) {
            System.err.println("Failed to load file");
            e.printStackTrace();
        }
        int textureID = texture.getTextureID();

        if(caching){
            textureCache.store(path, textureID);
        }

        textures.add(textureID);
        return textureID;
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
        for (Iterator<Integer> iterator = textures.iterator(); iterator.hasNext(); ) {
            Integer next = iterator.next();
            GL11.glDeleteTextures(next);
        }
    }

    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaoList.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, int size, float[] data){
        int vboID = GL15.glGenBuffers();
        vboList.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer floatBuffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW); // GL_STATIC_DRAW so OpenGL knows we don't want to edit this data
        GL20.glVertexAttribPointer(attributeNumber, size, GL11.GL_FLOAT, false, 0, 0);
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

    public static final boolean ENABLE_CACHING = true;
    public static final boolean DISABLE_CACHING = false;

}
