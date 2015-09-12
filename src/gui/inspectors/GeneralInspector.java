package gui.inspectors;

import exceptions.ComponentChildSizeException;
import gui.InfoBox;
import gui.MessageBox;
import gui.MobsBox;

/**
 * Created by stanners on 12/09/2015.
 */
public class GeneralInspector extends Inspector {

    private InfoBox infoBox; // move these into an inspector
    private MobsBox mobsBox;
    private MessageBox messageBox;

    public GeneralInspector(){
        super();
    }


    @Override
    public void init() {

//        infoBox     = new InfoBox();
        mobsBox     = new MobsBox(this, y); // TODO reconsider how this works
        messageBox  = new MessageBox(this, y);

//        infoBox.init();
        mobsBox.init();
        messageBox.init();

        // TODO do this and build relatively
//        infoBox     = new InfoBox();


//        addChild(infoBox);
        try {
            addChild(mobsBox);
            addChild(messageBox);
        } catch(ComponentChildSizeException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void action() {
        // does nothing
    }

}
