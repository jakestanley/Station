package gui.inspectors;

import gui.Component;
import gui.actions.ShowComponentAction;
import gui.inspectors.boxes.CommodityList;
import gui.inspectors.boxes.CommodityPanel;
import gui.widgets.Button;
import gui.widgets.ButtonRow;
import main.GameController;
import resources.ImageController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stanners on 12/09/2015.
 */
public class PlaceInspector extends Inspector {

    private CommodityList internalComponentList;
    private CommodityList externalComponentList;
    private CommodityList commodityList;
    private ButtonRow menu;

    public PlaceInspector(){
        super();
    }

    @Override
    public void init() {

        // TODO split this stuff up into separate methods

        // initialise the button row
        menu = new ButtonRow(this, getX(), getY(), this.getWidth(), this.getHeight());

        // initialise the lists
        internalComponentList   = new CommodityList(this, getX(), getY() + Button.MAX_HEIGHT); // TODO a better way to do this height stuff
        externalComponentList   = new CommodityList(this, getX(), getY() + Button.MAX_HEIGHT); // TODO should take types of objects as arguments
        commodityList           = new CommodityList(this, getX(), getY() + Button.MAX_HEIGHT); // TODO should take types of objects as arguments

        // generate the panels
        CommodityPanel reactorPanel = new CommodityPanel(internalComponentList, "Reactor", "Reactor info", GameController.imageController.getImage(ImageController.Keys.REACTOR_OBJECT));
        CommodityPanel tablePanel   = new CommodityPanel(internalComponentList, "Table", "Table info", GameController.imageController.getImage(ImageController.Keys.TABLE_2x1));
        internalComponentList.addChild(reactorPanel);
        internalComponentList.addChild(tablePanel);

        // hide the lists initially
        internalComponentList.hide();
        externalComponentList.hide();
        commodityList.hide();

        List<Component> lists = new ArrayList<Component>();
        lists.add(internalComponentList);
        lists.add(externalComponentList);
        lists.add(commodityList);

        // initialise the button menu
        menu.addButton(new Button(menu, "Internals",    new ShowComponentAction(internalComponentList, lists)));
        menu.addButton(new Button(menu, "Externals",    new ShowComponentAction(externalComponentList, lists)));
        menu.addButton(new Button(menu, "Commodities",  new ShowComponentAction(commodityList, lists)));


//        try {
        addChild(menu);
        addChild(internalComponentList);
        addChild(externalComponentList);
        addChild(commodityList);
//        } catch (ComponentChildSizeException e){
//            System.err.println("Component child size exception thrown");
//            e.printStackTrace();
//        }

        internalComponentList.init();
        externalComponentList.init();
        commodityList.init();

    }

    @Override
    public void update() {

    }

    @Override
    public void clickAction() {

    }

}
