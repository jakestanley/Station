package gui.widgets;

import gui.Component;
import main.Colours;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by jake on 16/08/15.
 */
public class TextField extends Widget {

    public static final Color BG = Color.darkGray;
    public static final Color FG = Color.white;
    public static final int BUFFER = 5;
    public static final int MAX_HEIGHT = 36;
    public static final String PLACEHOLDER = "Enter room name";

    private boolean clicked;
    private String text;

    public TextField(Component parent, int x, int y){ // TODO use actions instead of ints
        super(parent,
                BG, FG, Colours.GUI_BORDER, Colours.GUI_TEXT,
                x, y, parent.getWidth() - (2 * BUFFER), MAX_HEIGHT);
        this.text = PLACEHOLDER;
        this.clicked = false;
    }

    @Override
    public void init() {

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

//    @Override // TODO
//    public void render(Graphics screen, int x, int y) {
//
//        if(!set){
//            setCoordinates(x, y);
//        }
//
//        int textWidth   = GameController.guiController.getFont().getWidth(text);
//        int textHeight  = GameController.guiController.getFont().getHeight(text);
//
//        screen.setColor(BG);
//        screen.fillRect(x, y, width, height);
//        screen.setColor(FG);
//        screen.drawString(text, x + (width - textWidth) / 2, y + (height - textHeight) / 2); // primitive, but it will look OK
//
//        parent.getWidth();
//    }

    public void addLetter(String letter){ // this feels so primitive

        // if clicking for the first time, clear the placeholder text
        if(!clicked){
            text = "";
            clicked = true;
        } else {
            text = text + letter;
        }
    }

    public void deleteLetter(){ // TODO better way of handling text
        if(text.length() > 0){
            text.substring(0,text.length()-1);
        }
    }

    public String getContent(){
        return text;
    }
}
