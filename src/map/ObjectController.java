package map;

import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by stanners on 20/09/2015.
 */
public class ObjectController {

    private List objects; // TODO CONSIDER what about the functionals? are they the same or different?
    private Placeable placing;

    public ObjectController(){
        objects = new ArrayList<Placeable>();
        placing = null;
    }

    public void setPlacing(Placeable placing){
        this.placing = placing;
    }

    public void addPlaceable(Placeable p){
        p.setPlaced();
        objects.add(p);
    }

    public void render(Graphics screen){
        System.out.println("rendering objects");

        // render placed objects
        for (Iterator iterator = objects.iterator(); iterator.hasNext(); ) {
            Placeable next = (Placeable) iterator.next();
            next.render(screen);
        }

        // render not yet placed object
        if(placing != null){
            // TODO render not yet placed object
        }

    }

    public void update(){

    }

}