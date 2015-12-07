package uk.co.jakestanley.commander.rendering.world.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jake on 07/12/2015.
 */
@Getter
public abstract class Renderable {

    protected String identifier;
    protected List<RenderEntity> allRenderEntities;
    protected List<RenderEntity> visibleRenderEntities;

    public Renderable(String identifier){
        this.identifier = identifier;
        this.allRenderEntities = new ArrayList<RenderEntity>();
        this.visibleRenderEntities = new ArrayList<RenderEntity>();
    }

    public boolean hasVisibleRenderEntities(){
        return (visibleRenderEntities.size() > 0);
    }

    protected abstract void loadRenderEntities();

}
