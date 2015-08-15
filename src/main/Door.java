package main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

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

    private Point start, end;
    private boolean enabled, horizontal, locked, open, bulkhead, destroyed;
    private int sx, sy, ex, ey, timer; // start and end coordinates
    private float integrity;

    private ArrayList<String> strings;

    public Door(Point start, Point end){
        super(0, Display.DOOR_WIDTH + 1); // start from frame zero if starting closed. door has a frame of animation for each pixel

        this.enabled = false;
        this.start = start;
        this.end = end;
        this.open = false;
        this.bulkhead = false;
        this.destroyed = false;
        this.strings = new ArrayList<String>();

        this.integrity = BASE_STRENGTH;

        locked = false;
        horizontal = false;
        if(start.getX() == end.getX()){
            horizontal = true;
        }

        if(horizontal){ // if it's a horizontal door, it will be on the north side
            sx = ((int) start.getX()) * Display.TILE_WIDTH + (Display.DOOR_WIDTH / 2);
            sy = ((int) start.getY()) * Display.TILE_WIDTH;
            ex = sx + Display.DOOR_WIDTH;
            ey = sy;
        } else { // if it's a vertical door, it will be on the west side
            sx = ((int) start.getX()) * Display.TILE_WIDTH;
            sy = ((int) start.getY()) * Display.TILE_WIDTH + (Display.DOOR_WIDTH / 2);
            ex = sx;
            ey = sy + Display.DOOR_WIDTH;
        }

    }

    public Point getStartPoint(){
        return start;
    }

    public Point getEndPoint(){
        return end;
    }

    @Override
    public void init() { // TODO remove this requirement

    }

    public void render(Graphics screen) {

        int voX = GameController.viewController.getViewOffsetX();
        int voY = GameController.viewController.getViewOffsetY();

        screen.setColor(DOOR_BG_COLOUR);
        if (horizontal) {
            screen.drawLine(sx + (voX * Display.TILE_WIDTH), sy + (voY * Display.TILE_WIDTH) - 1, ex + (voX * Display.TILE_WIDTH), ey + (voY * Display.TILE_WIDTH) - 1);
        } else {
            screen.drawLine(sx + (voX * Display.TILE_WIDTH), sy + (voY * Display.TILE_WIDTH), ex + (voX * Display.TILE_WIDTH), ey + (voY * Display.TILE_WIDTH));
        }

        if (bulkhead) { // TODO CONSIDER drawing a thicker door for bulkheads
            screen.setColor(DOOR_BULKHEAD_COLOR);
        } else {
            screen.setColor(DOOR_STANDARD_COLOUR);
        }

        if (!destroyed) {

            if (frame != frames) {
                if (horizontal) {
                    screen.drawLine(sx + (frame) + (voX * Display.TILE_WIDTH), sy + (voY * Display.TILE_WIDTH) - 1, ex + (voX * Display.TILE_WIDTH), ey + (voY * Display.TILE_WIDTH) - 1);
                } else {
                    screen.drawLine(sx + (voX * Display.TILE_WIDTH), sy + (frame) + (voY * Display.TILE_WIDTH), ex + (voX * Display.TILE_WIDTH), ey + (voY * Display.TILE_WIDTH));
                }
            }

            if (open && frame != frames) {
                frame++;
            } else if (!open && frame != 0) {
                frame--;
            }

        }


    }

    public void populateDataBoxStrings(){
        // generating strings for data box // TODO optimise so this only happens when necessary
        strings = new ArrayList<String>();

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
    }

    @Override
    public void update(){
        autoClose();
    }

    public boolean isOpen(){
        if(destroyed){
            return true;
        } else {
            return open;
        }
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

    public boolean mouseOver(Point mousePoint) {

        int mouseX = (int) mousePoint.getX();
        int mouseY = (int) mousePoint.getY();

        int voX = GameController.viewController.getViewOffsetX();
        int voY = GameController.viewController.getViewOffsetY();

        Rectangle detection;

        if(horizontal){
            detection = new Rectangle(sx - 3 + (voX * Display.TILE_WIDTH), sy - 2 + (voY * Display.TILE_WIDTH), (Display.DOOR_WIDTH * 2) + 2, Display.DOOR_WIDTH);
        } else {
            detection = new Rectangle(sx - 2 + (voX * Display.TILE_WIDTH), sy - 3 + (voY * Display.TILE_WIDTH), Display.DOOR_WIDTH, (Display.DOOR_WIDTH * 2) + 2);
        }

        if (mouseX >= detection.getMinX() && mouseX <= detection.getMaxX() &&
            mouseY >= detection.getMinY() && mouseY <= detection.getMaxY()){
            return true;
        }

        return false;
    }

    public void renderHoverBox(Graphics screen) { // TODO animate the hover boxes?

        int voX = GameController.viewController.getViewOffsetX();
        int voY = GameController.viewController.getViewOffsetY();

        screen.setColor(Color.white);
        if(horizontal){ // TODO check these variables and replace hard coded variables
            screen.drawRect(sx - 12 + (voX * Display.TILE_WIDTH), sy - 8 + (voY * Display.TILE_WIDTH), (Display.DOOR_WIDTH * 2) + Display.MARGIN, Display.DOOR_WIDTH); // TODO make the hover box animated. fix the box
        } else {
            screen.drawRect(sx - 8 + (voX * Display.TILE_WIDTH), sy - 12 + (voY * Display.TILE_WIDTH), Display.DOOR_WIDTH, (Display.DOOR_WIDTH * 2) + Display.MARGIN); // TODO make the hover box animated. fix the box
        }

    }

    @Override
    public void renderDataBox(Graphics screen){ // TODO also render data box

        // initialise variables
        int x = dbx;
        int y = dby;

        // iterate through and present strings
        for (Iterator<String> iterator = strings.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            screen.drawString(next, x, y); // TODO make this more efficient
            y = y + Display.TEXT_SPACING;
        }

    }

    public void enable(){
        this.enabled = true;
    }

    public boolean isEnabled(){
        return enabled;
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

    public void damage(){ // TODO possibly another way
        // TODO more functionality
        if(integrity == 0){
            destroyed = true;
        } else {
            integrity = integrity - 10;
        }
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
        if(!bulkhead){
            integrity = integrity + BULKHEAD_STRENGTH;
            bulkhead = true;
        }
        closeLock();
    }

}
