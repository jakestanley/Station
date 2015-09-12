package gui;

import main.Colours;
import main.Display;
import main.GameController;
import mobs.Mob;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 26/05/2015.
 */
public class MobsBox extends Component {

    private ArrayList<Mob> friendlies;
    private ArrayList<Mob> hostiles;
    private ArrayList<MobPanel> panels;
    private Mob selectedMob;

    public MobsBox(Component parent, int y){
        super(parent, Colours.GUI_BACKGROUND, Colours.GUI_FOREGROUND, Colours.GUI_BORDER, Colours.GUI_TEXT,
                parent.getX(), y, parent.getWidth(), Display.TEXT_PANEL_HEIGHT, 1); // TODO change 1 to appropriate border width
    }

    @Override
    public void init() {
        // TODO CONSIDER that this might not actually be used
        ArrayList<Mob> mobs = GameController.mobController.getMobs();
        buildPanels(mobs);
    }

    @Override
    public void update() {

    }

    @Override
    public void action() {

    }

    @Override
    public void draw(Graphics screen) {

    }
//
//    @Override
//    public void renderContent(Graphics screen) {
//
//
//    }

    public Mob getMobMouseOver(Point mouse){

        if(mouse != null) {
            System.out.println("getting mouse over");
            int mouseX = (int) mouse.getX();
            int mouseY = (int) mouse.getY();
            selectedMob = null;

            for (Iterator<MobPanel> iterator = panels.iterator(); iterator.hasNext(); ) {
                MobPanel next = iterator.next();
                if (next.mouseOver(mouseX, mouseY)) {
                    selectedMob = next.getMob();
                }
            }

        }

        return selectedMob;
    }

    private void buildPanels(ArrayList<Mob> mobs){

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
            children.add(new MobPanel(this, y + (pos * Display.TEXT_PANEL_HEIGHT), pos, next));
            pos++;
        }

        for (Iterator<Mob> iterator = hostiles.iterator(); iterator.hasNext(); ) {
            Mob next = iterator.next();
            children.add(new MobPanel(this, y + (pos * Display.TEXT_PANEL_HEIGHT), pos, next));
            pos++;
        }
    }

}
