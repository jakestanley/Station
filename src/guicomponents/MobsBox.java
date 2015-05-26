package guicomponents;

import main.Display;
import mobs.Mob;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 26/05/2015.
 */
public class MobsBox extends GuiComponent {

    private ArrayList<Mob> friendlies;
    private ArrayList<Mob> hostiles;
    private ArrayList<MobPanel> panels;
    private Mob hoverMob = null;
    private Input input;

    public MobsBox(ArrayList<Mob> mobs, Input input){
        super(Display.LEFT_COLUMN_WIDTH, Display.getMobsBoxY(), Display.RIGHT_COLUMN_WIDTH, Display.MOBS_BOX_HEIGHT);
        System.out.println("mob count: " + mobs.size());
        this.input = input;
        buildPanels(mobs);
    }

    @Override
    public void update() {

        hoverMob = null;

        for (Iterator<MobPanel> iterator = panels.iterator(); iterator.hasNext(); ) {
            MobPanel next = iterator.next();
            if(next.mouseOver(input)){
                next.setSelected();
            }
        }

        // TODO get more data functionality

    }

    @Override
    public void render(Graphics screen) {
        drawBackground(screen);
        drawBorder(screen);

        // draw panels
        for (Iterator<MobPanel> iterator = panels.iterator(); iterator.hasNext(); ) {
            MobPanel next = iterator.next();
            next.render(screen);
        }

    }

    private void buildPanels(ArrayList<Mob> mobs){

        // TODO maintain consistent order on this list.

        friendlies = new ArrayList<Mob>();
        hostiles = new ArrayList<Mob>();
        panels = new ArrayList<MobPanel>();

        // sort into two groups
        for (Iterator<Mob> iterator = mobs.iterator(); iterator.hasNext(); ) {
            Mob next = iterator.next();
            if (next.isHostile()){
                hostiles.add(next);
            } else {
                friendlies.add(next);
            }
        }

        int pos = 0;

        for (Iterator<Mob> iterator = friendlies.iterator(); iterator.hasNext(); ) {
            Mob next = iterator.next();
            panels.add(new MobPanel(y + (pos * Display.TEXT_PANEL_HEIGHT), next));
            pos++;
        }

        for (Iterator<Mob> iterator = hostiles.iterator(); iterator.hasNext(); ) {
            Mob next = iterator.next();
            panels.add(new MobPanel(y + (pos * Display.TEXT_PANEL_HEIGHT), next));
            pos++;
        }
    }
}
