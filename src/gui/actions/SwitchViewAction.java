package gui.actions;

import main.GameController;

/**
 * Created by stanners on 13/09/2015.
 */
public class SwitchViewAction extends Action{

    private int view;

    public SwitchViewAction(int view){
        this.view = view;
    }

    @Override
    public void execute() { // TODO handle exception better
        try {
            GameController.guiController.switchView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
