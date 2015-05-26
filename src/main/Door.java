package main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 24/05/2015.
 */
public class Door extends Loopable implements Interactable {

    // TODO CONSIDER how isLocked and isOpen work together in various conditions

    public static final int     BASE_STRENGTH = 100;
    public static final int     BULKHEAD_STRENGTH = 100;
    public static final int     DOOR_TIMER = 3;
    public static final Color   DOOR_STANDARD_COLOUR = Color.lightGray;
    public static final Color   DOOR_BULKHEAD_COLOR = Color.darkGray;
    public static final Color   DOOR_BG_COLOUR = Color.black; // TODO CONSIDER black for background?

    private Tile start, end;
    private boolean horizontal, locked, open, bulkhead, destroyed;
    private int sx, sy, ex, ey, timer; // start and end coordinates
    private float integrity;

    public Door(Tile start, Tile end){
        super(0, Display.DOOR_WIDTH + 1); // start from frame zero if starting closed. door has a frame of animation for each pixel

        this.start = start;
        this.end = end;
        this.open = false;
        this.bulkhead = false;
        this.destroyed = false;

        this.integrity = BASE_STRENGTH;

        locked = false;
        horizontal = false;
        if(start.getX() == end.getX()){
            horizontal = true;
        }

        if(horizontal){ // if it's a horizontal door, it will be on the north side
            sx = start.getX() * Display.TILE_WIDTH + (Display.DOOR_WIDTH / 2);
            sy = start.getY() * Display.TILE_WIDTH;
            ex = sx + Display.DOOR_WIDTH;
            ey = sy;
        } else { // if it's a vertical door, it will be on the west side
            sx = start.getX() * Display.TILE_WIDTH;
            sy = start.getY() * Display.TILE_WIDTH + (Display.DOOR_WIDTH / 2);
            ex = sx;
            ey = sy + Display.DOOR_WIDTH;
        }

    }

    public Tile getStartTile(){
        return start;
    }

    public Tile getEndTile(){
        return end;
    }

    @Override
    public void init() {

    }

    @Override
    public void render(Graphics screen) {

        screen.setColor(DOOR_BG_COLOUR);
        if(horizontal){
            screen.drawLine(sx, sy, ex, ey);
        } else {
            screen.drawLine(sx, sy, ex, ey);
        }

        if(bulkhead){ // TODO CONSIDER drawing a thicker door for bulkheads
            screen.setColor(DOOR_BULKHEAD_COLOR);
        } else {
            screen.setColor(DOOR_STANDARD_COLOUR);
        }

        if(frame != frames){
            if(horizontal){
                screen.drawLine(sx + (frame), sy, ex, ey);
            } else {
                screen.drawLine(sx, sy + (frame), ex, ey);
            }
        }

        if(open && frame != frames){
            frame++;
        } else if(!open && frame != 0){
            frame--;
        }

    }

    @Override
    public void update(){
        autoClose();
    }

    public boolean isOpen(){
        return open;
    }

    public boolean isLocked(){
        return locked;
    }

    public void toggle(){
        open = !open;
        if(open){
            timer = DOOR_TIMER;
        } else {
            timer = 0;
        }
    }

    private void autoClose(){
        if(!locked){
            if(timer == 0 && open){
                open = false;
            }
            timer--;
        }
    }

    public boolean mouseOver(int mouseX, int mouseY) {

        Rectangle detection;

        if(horizontal){
            detection = new Rectangle(sx - 3, sy - 2, (Display.DOOR_WIDTH * 2) + 2, Display.DOOR_WIDTH);
        } else {
            detection = new Rectangle(sx - 2, sy - 3, Display.DOOR_WIDTH, (Display.DOOR_WIDTH * 2) + 2);
        }

        if (mouseX >= detection.getMinX() && mouseX <= detection.getMaxX() &&
            mouseY >= detection.getMinY() && mouseY <= detection.getMaxY()){
            return true;
        }

        return false;
    }

    @Override
    public void renderHoverBox(Graphics screen) { // TODO animate the hover boxes?
        screen.setColor(Color.white);
        if(horizontal){
            screen.drawRect(sx - 3, sy - 2, (Display.DOOR_WIDTH * 2) + 2, Display.DOOR_WIDTH); // TODO make the hover box animated. fix the box
        } else {
            screen.drawRect(sx - 2, sy - 3, Display.DOOR_WIDTH, (Display.DOOR_WIDTH * 2) + 2); // TODO make the hover box animated. fix the box
        }

    }

    @Override
    public void renderDataBox(Graphics screen){ // TODO also render data box

        // initialising variables
        ArrayList<String> strings = new ArrayList<String>();
        int x = (Display.DISPLAY_WIDTH * Display.SCALE) - 200;
        int y = 60;

        if(!destroyed){
            strings.add("Integrity: " + integrity + "%");
        } else {
            strings.add("Integrity: DESTROYED");
        }

        // get strings
        if(open){
            strings.add("Status: OPEN");
        } else {
            strings.add("Status: CLOSED");
        }

        if(locked){
            strings.add("Lock: LOCKED");
        } else {
            strings.add("Lock: UNLOCKED");
        }

        if(bulkhead){
            strings.add("Bulkhead: ENGAGED");
        } else {
            strings.add("Bulkhead: READY");
        }

        // set color
        screen.setColor(Color.white);

        // iterate through and present strings
        for (Iterator<String> iterator = strings.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            screen.drawString(next, x, y); // TODO make this more efficient
            y = y + Display.TEXT_SPACING;
        }

    }

    @Override
    public void qPress() {
        openLock();
    }

    @Override
    public void wPress() {
        closeLock();
    }

    @Override
    public void ePress() {
        resetLock();
    }

    @Override
    public void rPress() {
        engageBulkhead(); // permanently locks door and doubles security. useful in some cases, but could be harmful to crew and others
    }

    @Override
    public void vPress() {
        // TODO something with this
    }

    private void openLock(){
        if(!bulkhead){
            open = true;
            locked = true;
            timer = 0;
        }
    }

    private void closeLock(){
        open = false;
        locked = true;
        timer = 0;
    }

    private void resetLock(){
        if(!bulkhead){
            locked = false;
            timer = 0;
        }
    }

    private void engageBulkhead(){ // TODO bulkhead engage animation
        integrity = integrity + BULKHEAD_STRENGTH;
        bulkhead = true;
        closeLock();
    }

    private void damage(){ // TODO possibly another way
        // TODO more functionality
        if(integrity == 0){
            destroyed = true;
        }
    }

}
