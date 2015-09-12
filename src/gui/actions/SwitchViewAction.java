package gui.actions;

import main.GameController;

/**
 * Created by stanners on 13/09/2015.
 */
public class SwitchViewAction extends Action{

    private String view;

    public SwitchViewAction(String view){
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
