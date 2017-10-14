package uk.co.jakestanley.commander2d.map;

/**
 * Created by stanners on 21/07/2015.
 */
public class MapTemplate {

    private int width, height;
    private boolean[] booleans;

    public MapTemplate(int width, int height, boolean[] booleans){
        this.width = width;
        this.height = height;
        this.booleans = booleans;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean[] getBooleans() {
        return booleans;
    }

}
