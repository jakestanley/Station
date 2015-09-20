package gui.actions;

import gui.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created by stanners on 20/09/2015.
 */
public class ShowComponentAction extends Action {

    private Component toShow;
    private List<Component> toHide;

    public ShowComponentAction(Component toShow, List<Component> toHide){
        this.toShow = toShow;
        this.toHide = toHide;
    }

    @Override
    public void execute() {
        for (Iterator<Component> iterator = toHide.iterator(); iterator.hasNext(); ) {
            Component next = iterator.next();
            next.hide();
        }
        toShow.show();
    }
}
