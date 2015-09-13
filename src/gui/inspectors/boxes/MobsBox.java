package gui.inspectors.boxes;

import gui.Component;
import gui.Scrollable;
import main.Colours;
import main.Display;
import main.GameController;
import mobs.Mob;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stanners on 26/05/2015.
 */
public class MobsBox extends Component implements Scrollable {

    private int listOffset;
    private int listVisible;
    private ArrayList<Mob> friendlies;
    private ArrayList<Mob> hostiles;
    private ArrayList<MobPanel> panels;
    private Mob selectedMob;

    public MobsBox(Component parent, int y){
        super(parent, Colours.GUI_BACKGROUND, Colours.GUI_FOREGROUND, Colours.GUI_BORDER, Colours.GUI_TEXT,
                parent.getX(), y, parent.getWidth(), Display.MOBS_BOX_HEIGHT, 1); // TODO change 1 to appropriate border width
        listOffset = 0;
        listVisible = getHeight() / Display.MOBS_BOX_HEIGHT; // TODO use this or change it
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
    public void clickAction() {

    }

    @Override
    public void hoverAction() {

    }

    @Override
    public void draw(Graphics screen) {

    }

    private void buildPanels(ArrayList<Mob> mobs){

        friendlies = new ArrayList<Mob>();
        hostiles = new ArrayList<Mob>();

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
            int panelY = y + (pos * Display.TEXT_PANEL_HEIGHT);
            System.out.println("creating mob panel y: " + panelY);
            children.add(new MobPanel(this, panelY, pos, next));
            pos++;
        }

//        for (Iterator<Mob> iterator = hostiles.iterator(); iterator.hasNext(); ) {
//            Mob next = iterator.next();
//            children.add(new MobPanel(this, y + (pos * Display.TEXT_PANEL_HEIGHT), pos, next));
//            pos++;
//        }

    }

    @Override
    public void scrollUp() {
        if(listOffset > 0){
            listOffset--;
        }
    }

    @Override
    public void scrollDown(){

    }
}
