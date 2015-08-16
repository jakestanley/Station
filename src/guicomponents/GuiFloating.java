package guicomponents;

/**
 * Created by jake on 16/08/15.
 */
public abstract class GuiFloating extends GuiContainer {

    public GuiFloating(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    public abstract void init();

    /**
     * Call child onClose method and set to invalid so this will be destroyed
     */
    public void destroy(){
        onClose();
        valid = false;
    }

    protected abstract void onClose();

}
