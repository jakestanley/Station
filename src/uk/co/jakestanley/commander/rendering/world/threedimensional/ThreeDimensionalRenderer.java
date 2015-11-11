package uk.co.jakestanley.commander.rendering.world.threedimensional;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Graphics;
import uk.co.jakestanley.commander.Main;
import uk.co.jakestanley.commander.rendering.Renderer;
import uk.co.jakestanley.commander.scene.entities.shapes.Box;

/**
 * Created by jp-st on 10/11/2015.
 */
public class ThreeDimensionalRenderer extends Renderer {

    // TODO mostly adapted from here: http://ninjacave.com/lwjglbasics4

    /** position of quad */
    float x = 400, y = 300;
    /** angle of quad rotation */
//    float rotation = 0; // TODO remove

    /** time at last frame */
    long lastFrame;

    /** frames per second */
    int fps;
    /** last fps time */
    long lastFPS;

    public ThreeDimensionalRenderer(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    public void init() {

        // get delta before loop starts
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

    }

    public void update() {
//        int delta = getDelta(); // TODO figure out how delta works and make it consistent across 2D and 3D

//        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) x -= 0.35f * delta;
//        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) x += 0.35f * delta;
//
//        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) y -= 0.35f * delta;
//        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) y += 0.35f * delta;

        updateFPS(); // update FPS Counter

    }

    public void render(Graphics screen) { // TODO need models/shapes/objects list or something
        // Clear The Screen And The Depth Buffer // TODO consider prepare method?
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // R,G,B,A Set The Color To Blue One Time Only
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        // draw quad
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
//        GL11.glRotatef(rotation, 0f, 0f, 1f);
        GL11.glTranslatef(-x, -y, 0);

        Box playerBox = (Box) Main.getSceneController().getMobileEntities().get(0).getShape();

        float renderAtX = x + playerBox.getXLocal();
        float renderAtY = y + playerBox.getZLocal();

        // begin drawing
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(renderAtX, renderAtY);
        GL11.glVertex2f(renderAtX + width, renderAtY);
        GL11.glVertex2f(renderAtX + width, renderAtY + height);
        GL11.glVertex2f(renderAtX, renderAtY + height);
        GL11.glEnd();
        GL11.glPopMatrix();

    }

    public void renderDebugging(Graphics screen) {

    }

    /**
     * Calculate how many milliseconds have passed
     * since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

}
